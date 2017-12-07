/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reglasStrings;

/**
 *
 * @author Blas
 */
public class Regla {
    protected Itemset premisa;
    protected Itemset conclusion;
    protected double confianza;
    protected int soporte;

    public Itemset getPremisa() {
        return premisa;
    }

    public Itemset getConclusion() {
        return conclusion;
    }

    public double getConfianza() {
        return confianza;
    }

    public int getSuporte() {
        return soporte;
    }
}
