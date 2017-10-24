/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reglas;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Blas
 */
public class Itemsets {
    
    private List < String > itemsets = new ArrayList <> ();
    
    public static int cantReglas =0;

    public List<String> getItemsets() {
        return itemsets;
    }
 
    public Itemsets(List a){
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
		int a = ((newb.substring(1, newb.length() - 1).split(", ")).length);
                //ArrayList<ArrayList> lista = new ArrayList<>();
                 //ArrayList<Integer> listasup = new ArrayList<>();
		int[][] f = new int[b][a - 1];
		int[] sup = new int[b];
		for (i = 0; i < b; i++) {
			newb = this.itemsets.get(i);
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
				m += imprimirRegla(f[i], f[j], sup[i], sup[j], minConf);
			}
		}
		if (m == 0) {
			System.out.println("No existen reglas que superen en minimo de confianza " + minConf * 100 + "%");
		}
	}
    
    public static int imprimirRegla(int[] a, int[] b, int a1, int b1, double minConf) {
		String win = "(",
		lose = "(";
		int i,
		j,
		k = 0;
		int[] loss = new int[a.length];
                int check = 0;
		for (i = 0; i < b.length && b[i] != 0; i++) {
			k = 1;
			win = win + b[i] + ",";
                        check++;
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
                                check++;
			}
		}
		lose = lose.substring(0, lose.length() - 1) + ")";
		if (k == 0) {
			double Lol = (double) a1 / b1;
			if (Lol >= minConf && Lol<=1 && check<=a.length) {
                                cantReglas++;
				System.out.printf(cantReglas+" %s ==> %s :	%.2f%c \n", win, lose, Lol * 100, 37);
				return 1;
			}
		}
		return 0;
	}
    
    
    
}
