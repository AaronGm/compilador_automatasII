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
    
    public boolean isDataType(String token) {
        boolean isTD = false;
        for (String tipoDato : TIPOS_DATO) {
            if(token.equals(tipoDato)) {
                isTD = true;
                listaTokens.add(new String[] { token, "Tipo Dato" });
            }
        }
        return isTD;
    }
    
    public void esTipoDato(String token) {
        for (String tipoDato : TIPOS_DATO) {
            if(token.equals(tipoDato)) {
                listaTokens.add(new String[] { token, "Tipo Dato" });
            }
        }
    }
    
    public boolean isReservedWord(String token) {
        boolean isReservedWord = false;
        for (String palabraReservada : PALABRAS_RESERVADAS) {
            if (token.equals(palabraReservada)) {
                isReservedWord = true;
                listaTokens.add(new String[] { token, "Palabra reservada" });
            }
        }
        return isReservedWord;
    }
    
    public void esPalabraReservada(String token) {
        for (String palabraReservada : PALABRAS_RESERVADAS) {
            if (token.equals(palabraReservada)) {
                listaTokens.add(new String[] { token, "Palabra reservada" });
            }
        }
    }

    private String nombreTipoDato(String tipoDato) {
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
    
    public void addTipoDato(String token) {
        listaTokens.add(new String[] { token, "Tipo de Dato" });
    }
    
    public ArrayList<String[]> getListaTokens() {
        return listaTokens;
    }
}
