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
    
    public boolean isSpecial(String token) {
        boolean isSpecial = false;
        for (String caracterEspecial : CARACTERES_ESPECIALES) {
            if (token.equals(caracterEspecial)) {
                isSpecial = true;
            }
        }
        return isSpecial;
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
    
    public boolean isMath(String token) {
        boolean isMath = false;
        for (String math : MATHS) {
            if (token.equals(math)) {
                isMath = true;
            }
        }
        return isMath;
    }
    
    public void agregarMath(String token) {
        listaTokens.add(new String[] { token, "Carácter Matemático" });
    }
    
    public void agregarEspecial(String token) {
        listaTokens.add(new String[] { token, "Carácter Especial" });
    }
    
    public void abreCierraParentesis(String token) {
        String[] contenidoParentesis = {};
        if (token.endsWith("\\" + PARENTESIS_D)) {
            System.out.println(token);
            
        }
        
    }
    
}
