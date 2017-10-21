package reglas;
import java.io.*;
import java.util.*;

/**  generacion de reglas de Asosciacion, implementando el algoritmo Apriori
 *  para la extraccion de los itemsets frecuentes
 *
 * Los dataset leidos son de enteros (>=0) separados por espacios, una transaccion por linea, ej.
 * 1 2 5 
 * 2 4
 * 2 3
 * 1 2 4
 * 1 3
 */
public class Apriori {


    public static void main(String[] args) throws Exception {
        Apriori ap = new Apriori(args);
    }

    /** itemset */
    private List<int[]> itemsets ;
    /** nombre del archivo .dat */
    private String transaFile; 
    /** cantidad de items en el dataset */
    private int numItems; 
    /** total de transacciones procesadas */
    private int numTransactions; 
    /** minimo soporte para los itemsets */
    private double minSup; 
    /** minimo de confianza para las reglas */
    private static double minConf; 
    /** minimo de itemsets a calcular */
    private static double minItemsets;
    /** genero los Fk */
    private static List < String > tupples = new ArrayList < String > ();
    
    private static List < Itemsets > efes = new ArrayList <> ();
    
    static int cont=1;
    
    /** para ejecutarlo de la comandera  */
    private boolean usedAsLibrary = false;

    /** el algoritmo central */
    public  Apriori(String[] args, Observer ob) throws Exception
    {
    	usedAsLibrary = false;
    	datosConfiguracion(args);
    	ejecutar();
    }

    /** generates the apriori itemsets from a file
     * 
     * @param args configuration parameters: args[0] is a filename, args[1] the min support (e.g. 0.8 for 80%)
     */
    public  Apriori(String[] args) throws Exception
    {
        datosConfiguracion(args);
        ejecutar();
    }

    /** ejecucion del programa */
    private void ejecutar() throws Exception {
        //tiempo
        long start = System.currentTimeMillis();

        // generamos candidatos de 1
        crearItemsdeUno();     
        
        int itemsetNumber=1; //indice de F
        int nbFrequentSets=0;
        
        while (itemsets.size()>0)
        {
            //vamos verificando que los itemsets cumplan en minSup -- generacion de los Fk
            generarItemsetFrecuentes();

            if(itemsets.size()!=0)
            {
                nbFrequentSets+=itemsets.size();
                log("Found "+itemsets.size()+" frequent itemsets of size " + itemsetNumber + " (with support "+(minSup*100)+"%)");;
                createNewItemsetsFromPreviousOnes();
            }

            itemsetNumber++;
        } 

        //display the execution time
        long end = System.currentTimeMillis();
        log("Tiempo de ejecucion: "+((double)(end-start)/1000) + " segundos.");
        log("Encontrados "+nbFrequentSets+ " itemset frecuentes "+(minSup*100)+"% (absolute "+Math.round(numTransactions*minSup)+")");
        log("Fin Itemset frecuentes");
	GenerarRegla();
    }

    /** entra el item frecuante y si soporte, sale una tupla de ahí  */
    private void foundFrequentItemSet(int[] itemset, int support) {
    	String New, New1;
	 	New = Arrays.toString(itemset);
                
		New1 = New.substring(0, New.length() - 1) + ", " + support + "]";
                //se van guardando los candidatos frecuentes 
                //aca hay que modificar, hay que crear un objeto cuando termina para generar los H
                //cada elemento de la tupla tiene el itemset acompañado por el soporte
		tupples.add(New1);
	 	System.out.println(New + "  (" + ((support / (double) numTransactions)) + " " + support + ")");
    }

    /** outputs a message in Sys.err if not used as library */
    private void log(String message) {
    }

    /** datos entrada: numItems, numTransactions, and sets minSup */
    private void datosConfiguracion(String[] args) throws Exception
    {        
        // dataset
        if (args.length!=0) transaFile = args[0];
        else transaFile = "dat2.dat"; // default

    	// minSup
    	if (args.length>=2) minSup=(Double.valueOf(args[1]).doubleValue());    	
    	else minSup = .3;// by default
    	if (minSup>1 || minSup<0) throw new Exception("minSup: bad value");
	
	// minCong
	if (args.length >= 3) minConf = (Double.valueOf(args[2]).doubleValue());
	else minConf = .7;
	if (minConf > 1 || minConf < 0) throw new Exception("minConf: bad value");
    	
        //limite de itemsets
        
    	
    	// calculamos del dataset el nro de items y de transacciones
    	numItems = 0;
    	numTransactions=0;
    	BufferedReader data_in = new BufferedReader(new FileReader(transaFile));
    	while (data_in.ready()) {    		
    		String line=data_in.readLine();
    		if (line.matches("\\s*")) continue; // be friendly with empty lines
    		numTransactions++;
    		StringTokenizer t = new StringTokenizer(line," ");
    		while (t.hasMoreTokens()) {
    			int x = Integer.parseInt(t.nextToken());
    			//log(x);
    			if (x+1>numItems) numItems=x+1;
    		}    		
    	}  
    	
        outputConfig();

    }

   /** info de la corrida
     */ 
	private void outputConfig() {
		//output config info to the user
		 log("Input configuration: "+numItems+" items, "+numTransactions+" transactions, ");
		 log("minsup = "+minSup+"%");
	}

	/** candidatos de 1, 
	 * i.e. todos los que estan en el dataset, hay que verificar que no se repitan en una transaccion
	 */
	private void crearItemsdeUno() {
		itemsets = new ArrayList<int[]>();
        for(int i=0; i<numItems; i++)
        {
        	int[] cand = {i};
        	itemsets.add(cand);
        }
	}
			
    /**
     * if m is the size of the current itemsets,
     * generate all possible itemsets of size n+1 from pairs of current itemsets
     * replaces the itemsets of itemsets by the new ones
     */
    private void createNewItemsetsFromPreviousOnes()
    {
    	// by construction, all existing itemsets have the same size
    	int currentSizeOfItemsets = itemsets.get(0).length;
    	log("Generando itemsets de tamaño "+(currentSizeOfItemsets+1)+" a partir de "+itemsets.size()+" itemsets de tamaño "+currentSizeOfItemsets);
    		
    	HashMap<String, int[]> tempCandidates = new HashMap<String, int[]>(); //temporary candidates
    	
        // compare each pair of itemsets of size n-1 - genermaos los candidateos
        for(int i=0; i<itemsets.size(); i++)
        {
            for(int j=i+1; j<itemsets.size(); j++)
            {
                int[] X = itemsets.get(i);
                int[] Y = itemsets.get(j);

                assert (X.length==Y.length);
                
                //make a string of the first n-2 tokens of the strings
                int [] newCand = new int[currentSizeOfItemsets+1];
                for(int s=0; s<newCand.length-1; s++) {
                	newCand[s] = X[s];
                }
                    
                int ndifferent = 0;
                // then we find the missing value
                for(int s1=0; s1<Y.length; s1++)
                {
                	boolean found = false;
                	// is Y[s1] in X?
                    for(int s2=0; s2<X.length; s2++) {
                    	if (X[s2]==Y[s1]) { 
                    		found = true;
                    		break;
                    	}
                	}
                	if (!found){ // Y[s1] is not in X
                		ndifferent++;
                		// we put the missing value at the end of newCand
                		newCand[newCand.length -1] = Y[s1];
                	}
            	
            	}
                
                // we have to find at least 1 different, otherwise it means that we have two times the same set in the existing candidates
                assert(ndifferent>0);
                
                
                if (ndifferent==1) {
                    //Como HashMap no posee un "equals" adecuado para int[] :-(
                    // Tube que creat el hash usando un String :-(
                	// Use Arrays.toString para reutilizar el equals y hashcode de String
                	Arrays.sort(newCand);
                	tempCandidates.put(Arrays.toString(newCand),newCand);
                }
            }
        }
        
        //set the new itemsets
        itemsets = new ArrayList<int[]>(tempCandidates.values());
    	log("Se generaron"+itemsets.size()+" candidatos de tamaño "+(currentSizeOfItemsets+1));

    }



    /** colocar "true" en trans[i] si el entero i se encuentra en la transaccion */
    private void line2booleanArray(String line, boolean[] trans) {
	    Arrays.fill(trans, false);
	    StringTokenizer stFile = new StringTokenizer(line, " "); //lectura de una transaccion en el dataset
	    //colocarmos el contenido de esa linea en un arreglo de transacciones
	    while (stFile.hasMoreTokens())
	    {
	    	
	        int parsedVal = Integer.parseInt(stFile.nextToken());
			trans[parsedVal]=true; //if it is not a 0, assign the value to true
	    }
    }

    
    /** passes through the data to measure the frequency of sets in {@link itemsets},
     *  filtra todos los que no superan el minSupp
     */
    private void generarItemsetFrecuentes() throws Exception
    {
    	
        log("Procesando " + itemsets.size()+ " itemsets de tamaño "+itemsets.get(0).length);

        List<int[]> frequentCandidates = new ArrayList<int[]>(); //aca se van a guardar los que cumplan minSup

        boolean match; //whether the transaction has all the items in an itemset
        int count[] = new int[itemsets.size()]; //la cantidad de candidatos, se inicializa en 0

		// hay que obtener el soporte de los candidatos del dataset
		BufferedReader data_in = new BufferedReader(new InputStreamReader(new FileInputStream(transaFile)));

		boolean[] trans = new boolean[numItems];
		
		// para cada transaccion
		for (int i = 0; i < numTransactions; i++) {

			// boolean[] trans = extractEncoding1(data_in.readLine());
			String line = data_in.readLine();
			line2booleanArray(line, trans);

			// verificando por cada candidato
			for (int c = 0; c < itemsets.size(); c++) {
				match = true; // bandera
				// si no esta el itemset en la transaccion ponemos a falso
				int[] cand = itemsets.get(c);
				//int[] cand = candidatesOptimized[c];
				// chequear cada item en el itemset para ver si
				// esta presente en la transaccion
				for (int xx : cand) {
					if (trans[xx] == false) {
						match = false;
						break;
					}
				}
				if (match) { // si hay un match en la transaccion incrementamos
					count[c]++;
					//log(Arrays.toString(cand)+"el candidato se encuentra en la transaccion "+i+" ("+line+")");
				}
			}

		}
		
		data_in.close();

		for (int i = 0; i < itemsets.size(); i++) {
			// si el count% es mayor que el minSup%, agregamos el candidato a 
			// los candidatos frecuentes
			if ((count[i] / (double) (numTransactions)) >= minSup) {
				foundFrequentItemSet(itemsets.get(i),count[i]);
				frequentCandidates.add(itemsets.get(i));
			}
			//else log("-- Remove candidate: "+ Arrays.toString(candidates.get(i)) + "  is: "+ ((count[i] / (double) numTransactions)));
		}

        //los nuevos candidatos son solo los candidatos frecuentes
        itemsets = frequentCandidates;
        
    }
    
	public static void GenerarRegla() {
                //tupples.remove(tupples.size()-1);
		int b = tupples.size();
                
                System.out.println(tupples);
		if (b == 0) {
			System.exit(0);
		}
		int i,
		j,
		k = 0,
		m = 0;
		String newb = tupples.get(b - 1);
		int a = ((newb.substring(1, newb.length() - 1).split(", ")).length);
                //ArrayList<ArrayList> lista = new ArrayList<>();
                 //ArrayList<Integer> listasup = new ArrayList<>();
		int[][] f = new int[b][a - 1];
		int[] sup = new int[b];
		for (i = 0; i < b; i++) {
			newb = tupples.get(i);
                        //ArrayList<Integer> hola = new ArrayList<>();
			String[] poop = newb.substring(1, newb.length() - 1).split(", ");
			for (j = 0; j < poop.length - 1; j++) {
				f[i][j] = Integer.parseInt(poop[j]);
                                //hola.add(Integer.parseInt(poop[j]));
                                
			}
                        //lista.add(hola);
			sup[i] = Integer.parseInt(poop[j]);
                        //listasup.add(Integer.parseInt(poop[j]));
			if ((j + 1) == a && k == 0) {
				k = i;
			}
			poop = null;
		}
		System.out.println("\nReglas de Asociacion: con una confianza minima de =" + minConf * 100 + "%");
		for (i = k; i < b; i++) {
			for (j = 0; j < k; j++) {
				m += imprimirRegla(f[i], f[j], sup[i], sup[j]);
			}
		}
		if (m == 0) {
			System.out.println("No existen reglas que superen en minimo de confianza " + minConf * 100 + "%");
		}
	}
	public static int imprimirRegla(int[] a, int[] b, int a1, int b1) {
		String win = "(",
		lose = "(";
		int i,
		j,
		k = 0;
		int[] loss = new int[a.length];
		for (i = 0; i < b.length && b[i] != 0; i++) {
			k = 1;
			win = win + b[i] + ",";
			for (j = 0; j < a.length ; j++) {
				if (b[i] == a[j]) {
					k = 0;
					loss[j] = 1;
				}
			}
		}
		win = win.substring(0, win.length() - 1) + ")";
		for (i = 0; i < a.length && a[i]!= 0; i++) {
			if (loss[i] == 0) {
				lose = lose + a[i] + ",";
			}
		}
		lose = lose.substring(0, lose.length() - 1) + ")";
		if (k == 0) {
			double Lol = (double) a1 / b1;
			if (Lol > minConf) {
                            
				System.out.printf(cont+"%s ==> %s :	%.2f%c \n", win, lose, Lol * 100, 37);
                                cont++;
				return 1;
			}
		}
		return 0;
	}
        
}