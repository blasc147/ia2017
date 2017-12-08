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
    protected String premisa;
    protected String conclusion;
    protected double confianza;
    protected int soporte;

    public Regla(String premisa, String conclusion, double confianza) {
        this.premisa = premisa;
        this.conclusion = conclusion;
        this.confianza = confianza;
    }

    public String getPremisa() {
        return premisa;
    }

    public String getConclusion() {
        return conclusion;
    }

    public double getConfianza() {
        return confianza;
    }

    public int getSuporte() {
        return soporte;
    }
}
