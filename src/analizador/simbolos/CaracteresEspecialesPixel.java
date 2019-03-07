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
public class CaracteresEspecialesPixel implements CaracterEspecial {
    private ArrayList<String[]> listaTokens;
    
    public CaracteresEspecialesPixel(ArrayList<String[]> listaTokens) {
        this.listaTokens = listaTokens;
    }
    
    public void esAsignacion(String token) {
        for (String asignacion : ASSIGN) {
            if (token.equals(asignacion)) {
                listaTokens.add(new String[] { token, "Asignación" });
            }
        }
    }
    
    public void esMatematico(String token) {
        for (String math : MATHS) {
            if (token.equals(math)) {
                listaTokens.add(new String[] { token, "Carácter Matemático" });
            }
        }
    }
    
    public void abreCierraParentesis(String token) {
        if (token.contains(CaracterEspecial.PARENTESIS_I)) {
            
        }
    }
    
}
