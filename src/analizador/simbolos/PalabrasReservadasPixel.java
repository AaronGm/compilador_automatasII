/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador.simbolos;

import java.util.ArrayList;

/**
 *
 * @author aarongmx
 */
public class PalabrasReservadasPixel implements PalabrasReservadas {

    private ArrayList<String[]> listaTokens;

    public PalabrasReservadasPixel(ArrayList<String[]> listaTokens) {
        this.listaTokens = listaTokens;
    }
    
    public boolean esCadena(String token) {
        return token.startsWith("\"") && token.endsWith("\"");
    }
    
    public boolean esNumeroEntero(String token) {
        boolean esNumeroEntero = false;
        try {
            Integer.parseInt(token);
            esNumeroEntero = true;
        } catch (NumberFormatException e) {
        }
        return esNumeroEntero;
    }
    
    public boolean esNumeroReal(String token) {
        boolean esNumeroReal = false;
        try {
            Float.parseFloat(token);
            esNumeroReal = true;
        } catch (NumberFormatException e) {
        }
        return esNumeroReal;
    }
    
    public boolean isDataType(String token) {
        boolean isTD = false;
        for (String tipoDato : TIPOS_DATO) {
            if(token.equals(tipoDato)) {
                isTD = true;
            }
        }
        return isTD;
    }
    
    public boolean isReservedWord(String token) {
        boolean isReservedWord = false;
        for (String palabraReservada : PALABRAS_RESERVADAS) {
            if (token.equals(palabraReservada)) {
                isReservedWord = true;
            }
        }
        return isReservedWord;
    }
    
    public void agregaTipoDato(String token) {
        listaTokens.add(new String[] { token, "Tipo Dato" });
    }
    
    public void agregarPalabraReservada(String token) {
        listaTokens.add(new String[] { token, "Palabra reservada" });
    }
    
    public void buscaTipoDatoPorValor(String token) {
        if (esCadena(token)) {
            listaTokens.add(new String[]{token, "String"});
        } else if (esNumeroEntero(token)) {
            listaTokens.add(new String[]{token, "Int"});
        } else if (esNumeroEntero(token)) {
            listaTokens.add(new String[]{token, "Real"});
        }
    }

    private String abbrTipoDatoANombreTipoDato(String tipoDato) {
        String nombre;
        switch (tipoDato) {
            case STR:
                nombre = "String";
                break;
            case INT:
                nombre = "Int";
                break;
            default:
                nombre = "Real";
                break;
        }
        return nombre;
    }
    
    public ArrayList<String[]> getListaTokens() {
        return listaTokens;
    }
}
