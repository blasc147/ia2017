/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reglasStrings;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Blas
 */
public class ItemsetsString {
    private List < String > itemsets = new ArrayList <> ();
    
    public static int cantReglas =0;

    public List<String> getItemsets() {
        return itemsets;
    }
 
    public ItemsetsString(List a){
        this.itemsets=a;
    }
    
    public void GenerarRegla(double minConf) {
                //tupples.remove(tupples.size()-1);
		int b = this.itemsets.size();
                
		if (b == 0) {
			System.exit(0);
		}
		int i,
		j,
		k = 0,
		m = 0;
		String newb = this.itemsets.get(b - 1);
                //se separa el soporte
		int a = ((newb.substring(1, newb.length() - 1).split(", ")).length);
                //ArrayList<ArrayList> lista = new ArrayList<>();
                 //ArrayList<Integer> listasup = new ArrayList<>();
		String[][] f = new String[b][a - 1];
		int[] sup = new int[b];
		for (i = 0; i < b; i++) {
			newb = this.itemsets.get(i);
                        //ArrayList<Integer> hola = new ArrayList<>();
			String[] poop = newb.substring(1, newb.length() - 1).split(", ");
			for (j = 0; j < poop.length - 1; j++) {
				f[i][j] = poop[j];
                               
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
		
		String aux1= "Reglas de Asociacion partir de F"+(a-1)+": con una confianza minima de " + minConf * 100 + "% \n";
		System.out.print(aux1);
		Ventana.setStringReglas(Ventana.getStringReglas()+aux1);
		
		for (i = k; i < b; i++) {
			for (j = 0; j < k; j++) {
				m += imprimirRegla(f[i], f[j], sup[i], sup[j], minConf);
			}
		}
		if (m == 0) {
			aux1= "No existen reglas que superen en minimo de confianza " + minConf * 100 + "% \n";
			System.out.print(aux1);
			Ventana.setStringReglas(Ventana.getStringReglas()+aux1);
		}
	}
    
    public static int imprimirRegla(String[] a, String[] b, int a1, int b1, double minConf) {
		String win = "(",
		lose = "(";
		int i,
		j,
		k = 0;
		int[] loss = new int[a.length];
                int check = 0;
		for (i = 0; i < b.length && b[i]!=null ; i++) {
			k = 1;
			win = win + b[i] + ",";
                        check++;
			for (j = 0; j < a.length ; j++) {
				if (b[i] == null ? a[j] == null : b[i].equals(a[j])) {
					k = 0;
					loss[j] = 1;
				}
			}
		}
		win = win.substring(0, win.length() - 1) + ")";
		for (i = 0; i < a.length && a[i]!=null ; i++) {
			if (loss[i] == 0) {
				lose = lose + a[i] + ",";
                                check++;
			}
		}
		
		lose = lose.substring(0, lose.length() - 1) + ")";
		if (k == 0) {
			double Lol = (double) a1 / b1;
			if (Lol >= minConf && Lol<=1 && check<=a.length) {
				cantReglas++;
				String aux1 = String.format(cantReglas+" %s ==> %s :	%.2f%c \n", win, lose, Lol * 100, 37);
				System.out.print(aux1);
				Ventana.setStringReglas(Ventana.getStringReglas()+aux1);
				return 1;
			}
		}
		return 0;
	}
}
