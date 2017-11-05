/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reglasStrings;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeSet;
import reglasStrings.ItemsetsString;

/**
 *
 * @author Blas
 */
public class AprioriConStrings {

    /** itemset */
    private List<String[]> itemsets ;
    public static TreeSet<String> itemsetst= new TreeSet < String > ();
    private List<String[]> candidatos;
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
    private List < String > tupples;    
    private List < ItemsetsString > efes;     
    static int cont=0;
    
    /** generar apriori itemsets desde un dataser
     * 
     * @param args parametros configuracion: args[0] nombre del data, args[1] el minSup (e.g. 0.8 para 80%)
     */
    
//    public  AprioriConStrings(String[] args) throws Exception{
//        datosConfiguracion(args);
//        ejecutar();
//    }
//	
//	public static void main(String[] args) throws Exception {
//		AprioriConStrings ap = new AprioriConStrings(args);
//        AprioriConStrings ap2 = new AprioriConStrings(args);
//    }
//	
    /** datos entrada: numItems, numTransactions, and sets minSup */
    void datosConfiguracion() throws Exception{        
    	
    	// directorio del dataset
    	transaFile = Ventana.getFile().getAbsolutePath(); // transaFile = "chess.dat";

     	// minSup
     	String valor = Ventana.getTfMinSup().getText(); 
     	if (valor == null | valor.isEmpty()) {
     		minSup= 0.3; //valor por defecto
     		Ventana.getTfMinSup().setText("0.3");}
     	else {minSup = Double.parseDouble(valor);}
     	if (minSup>1 || minSup<0) throw new Exception("minSup: Valor incorrecto.");
 	
     	// minConf
     	valor = Ventana.getTfMinConf().getText(); 
     	if (valor == null | valor.isEmpty()) {
     		minConf= 0.8; //valor por defecto
     		Ventana.getTfMinConf().setText("0.8");}    
     	else {minConf= Double.parseDouble(valor);}
     	if (minConf > 1 || minConf < 0) throw new Exception("minConf: Valor incorrecto.");
     	
     	//tamanio limite de itemsets
     	valor= Ventana.getTfLimiteItemsets().getText();
     	if (valor == null | valor.isEmpty()) {
     		minItemsets=3; //valor por defecto
     		Ventana.getTfLimiteItemsets().setText("3");}
     	else {minItemsets = Double.parseDouble(valor);}
     	
    	// calculamos del dataset el nro de items y de transacciones
    	numItems = 0;
    	numTransactions=0;
    	BufferedReader data_in = new BufferedReader(new FileReader(transaFile));
    	while (data_in.ready()) {    		
    		String line=data_in.readLine();
                
    		if (line.matches("\\s*")) continue; // be friendly con lineas vacias
    		numTransactions++;
    		StringTokenizer t = new StringTokenizer(line," ");
    		while (t.hasMoreTokens()) {
                        String st= t.nextToken();                        
                        itemsetst.add(st);
    		}    		
    	}  
        numItems= itemsetst.size();    	
        outputConfig();
    }

    /** ejecucion del programa */
    void ejecutar() throws Exception {
        //tiempo
        long start = System.currentTimeMillis();

        // generamos candidatos de 1
        crearItemsdeUno();     
        
        int itemsetNumber=1; //indice de F
        int nbFrequentSets=0;
        tupples = new ArrayList <  > ();
        efes = new ArrayList <> ();
        
        while (candidatos.size()>0 && itemsetNumber<=minItemsets)
        {
            //vamos verificando que los itemsets cumplan en minSup -- generacion de los Fk
            generarItemsetFrecuentes();

            if(candidatos.size()!=0){
                nbFrequentSets+=candidatos.size();
                String aux1 = "Se encontraron "+candidatos.size()+" itemsets frecuentes de tamanio " + itemsetNumber + " (con soporte mayor o igual al "+(minSup*100)+"%). \n";
                System.out.print(aux1);
                Ventana.setStringVerItemsetsFrecuentes(Ventana.getStringVerItemsetsFrecuentes()+aux1);
                crearItemsetsApartirdeAteriores();
            }

            itemsetNumber++;
        } 

        //display the execution time
        long end = System.currentTimeMillis();

        String aux1 = "Encontrados "+nbFrequentSets+ " itemsets frecuentes con soporte mayor o igual a "+(minSup*100)+"% (absolute "+Math.round(numTransactions*minSup)+") \n";
        System.out.print(aux1);
        Ventana.setStringVerItemsetsFrecuentes(Ventana.getStringVerItemsetsFrecuentes()+aux1);
        Ventana.setStringVerEstadisticas(Ventana.getStringVerEstadisticas()+aux1);
        
//        aux1= "Fin Itemset frecuentes \n";
//        System.out.print(aux1);
//        Ventana.setStringVerItemsetsFrecuentes(Ventana.getStringVerItemsetsFrecuentes()+aux1);
        
        for (int i = 1; i < efes.size(); i++) {
            ItemsetsString itemset = efes.get(i);
            itemset.GenerarRegla(minConf);
        }
        
        aux1= "Cantidad de reglas generadas: "+ItemsetsString.cantReglas+"\n";
        System.out.print(aux1);
        Ventana.setStringVerEstadisticas(Ventana.getStringVerEstadisticas()+aux1);
        
        ItemsetsString.cantReglas=0;
        
        aux1= "Tiempo de ejecucion: "+((double)(end-start)/1000) + " segundos.\n";
        System.out.print(aux1);
        Ventana.setStringVerEstadisticas(Ventana.getStringVerEstadisticas()+aux1);
        
    }

    /** entra el item frecuente y su soporte, se arma una tupla para luego generar las reglas con eso  */
    private void generarTuplas(String[] itemset, int support) {
    	String New, New1;
	New = Arrays.toString(itemset);
                
	New1 = New.substring(0, New.length() - 1) + ", " + support + "]";
                //se van guardando los candidatos frecuentes 
                //aca hay que modificar, hay que crear un objeto cuando termina para generar los H
                //cada elemento de la tupla tiene el itemset acompañado por el soporte
	tupples.add(New1);
	String aux1= New + "  (" + ((support / (double) numTransactions)) + " " + support + ") \n";
	System.out.print(aux1);
	Ventana.setStringVerItemsetsFrecuentes(Ventana.getStringVerItemsetsFrecuentes()+aux1);

    }

    /** outputs a message in Sys.err if not used as library */
    private void log(String message) {
    }

   /** info de la corrida
     */ 
	private void outputConfig() {
		//output config
		//log("Input configuration: "+numItems+" items, "+numTransactions+" transactions, ");
		//log("minsup = "+minSup+"%");
		String aux1= "Datos iniciales: "+numItems+" items, "+numTransactions+" transacciones. \n";
		System.out.print(aux1);
        Ventana.setStringVerEstadisticas(Ventana.getStringVerEstadisticas()+aux1);
	}

	/** candidatos de 1, 
	 * i.e. todos los que estan en el dataset, hay que verificar que no se repitan en una transaccion
	 */
	private void crearItemsdeUno() {
                candidatos = new ArrayList<String[]>();
            Iterator<String> itr = itemsetst.iterator();
        while(itr.hasNext()){
            String[] st = {itr.next()};
            candidatos.add(st);
        }
	}
			
    /**
     * si m es el tamanio de los itemsets actuales,
     * generamos los candidatos de tamanio m+1 a partir de los actuales
     */
    private void crearItemsetsApartirdeAteriores()
    {
    	// en teoria, los itemsets existenetes tienen el mismo tamanio
    	int currentSizeOfItemsets = candidatos.get(0).length;
    	String aux1 = "Generando itemsets de tamanio "+(currentSizeOfItemsets+1)+" a partir de "+candidatos.size()+" itemsets de tamanio "+currentSizeOfItemsets+"\n";
    	System.out.print(aux1);
    	Ventana.setStringVerItemsetsFrecuentes(Ventana.getStringVerItemsetsFrecuentes()+aux1);
    		
    	HashMap<String, String[]> tempCandidates = new HashMap<String, String[]>(); //candidatos temporales
    	
        // compare each pair of itemsets of size n-1 - generamos los candidatos
        for(int i=0; i<candidatos.size(); i++)
        {
            for(int j=i+1; j<candidatos.size(); j++)
            {
                String[] X = candidatos.get(i);
                String[] Y = candidatos.get(j);

                assert (X.length==Y.length);
                
                String [] newCand = new String[currentSizeOfItemsets+1];
                for(int s=0; s<newCand.length-1; s++) {
                	newCand[s] = X[s];
                }
                    
                int ndifferent = 0;
                // se encuentran los valores que faltan
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
                	if (!found){ // Y[s1] no esta en X
                		ndifferent++;
                		// colocamos los valores que falta en newCand
                		newCand[newCand.length -1] = Y[s1];
                	}
            	
            	}
                
                // encontrar al menos 1 diferente, sino significa que tenemos algun candidato repetido
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
        
        //se muetran los itemsets generados
        candidatos = new ArrayList<String[]>(tempCandidates.values());
        aux1= "Se generaron "+candidatos.size()+" candidatos de tamanio "+(currentSizeOfItemsets+1)+"\n";
        System.out.print(aux1);
        Ventana.setStringVerItemsetsFrecuentes(Ventana.getStringVerItemsetsFrecuentes()+aux1);
    }



    /** colocar "true" en trans[i] si el entero i se encuentra en la transaccion */
    private void line2booleanArray(String line, List transa) {
	    StringTokenizer stFile = new StringTokenizer(line, " "); //lectura de una transaccion en el dataset
	    //colocarmos el contenido de esa linea en un arreglo de transacciones
	    while (stFile.hasMoreTokens())
	    {
	    	transa.add(stFile.nextToken());
	    }
    }

    
    /** analizar la frecuencia de los itemsets {@link itemsets},
     *  filtra todos los que no superan el minSupp
     */
    private void generarItemsetFrecuentes() throws Exception{
    	
        String aux1= "Procesando " + candidatos.size()+ " itemsets de tamanio "+candidatos.get(0).length+"\n";
        System.out.print(aux1);
        Ventana.setStringVerItemsetsFrecuentes(Ventana.getStringVerItemsetsFrecuentes()+aux1);
        
        List<String[]> frequentCandidates = new ArrayList<String[]>(); //aca se van a guardar los que cumplan minSup

        boolean match; //bandera para comprobar que el itemset esta en la transaccion
        int count[] = new int[candidatos.size()]; //la cantidad de candidatos, se inicializa en 0

		// hay que obtener el soporte de los candidatos del dataset
		BufferedReader data_in = new BufferedReader(new InputStreamReader(new FileInputStream(transaFile)));
		List<String> transas = new ArrayList<>();
		
		// para cada transaccion
		for (int i = 0; i < numTransactions; i++) {

			// boolean[] trans = extractEncoding1(data_in.readLine());
			String line = data_in.readLine();
			line2booleanArray(line, transas);
			// verificando por cada candidato
			for (int c = 0; c < candidatos.size(); c++) {
				match = true; // bandera
				// si no esta el itemset en la transaccion ponemos a falso
				//int[] cand = candidatesOptimized[c];
				// chequear cada item en el itemset para ver si
				// esta presente en la transaccion
                                List<String> aux = Arrays.asList(candidatos.get(c));
				if (!transas.containsAll(aux)) {
					match = false;
				}
				if (match) { // si hay un match en la transaccion incrementamos
					count[c]++;
//					System.out.println(Arrays.toString(cand)+"el candidato se encuentra en la transaccion "+i+" ("+line+")");
				}
			}
                        transas.clear();

		}
		
		data_in.close();

		for (int i = 0; i < candidatos.size(); i++) {
			// si el count% es mayor que el minSup%, agregamos el candidato a 
			// los candidatos frecuentes
			if ((count[i] / (double) (numTransactions)) >= minSup) {
				generarTuplas(candidatos.get(i),count[i]);
				frequentCandidates.add(candidatos.get(i));
			}
			//else log("-- Remove candidate: "+ Arrays.toString(candidates.get(i)) + "  is: "+ ((count[i] / (double) numTransactions)));
		}
        List copia = new ArrayList(tupples);
        efes.add(new ItemsetsString(copia));
        //vamos guardando los candidatos frecuentes(generacion de los F)
        candidatos = frequentCandidates;
        
    }
}
