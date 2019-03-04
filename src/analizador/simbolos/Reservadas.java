/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador.simbolos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author aarongmx
 */
public enum Reservadas {
    
    VAR("var"),
    CONST("const"),
    OUT("out"),
    OUTLN("outln"),
    DV("dv"),
    IT("it"),
    ;
    
    
    private final String token;

    private Reservadas(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
    
    public static List<String> getTokens() {
        List<Reservadas> listaTokens = Arrays.asList(Reservadas.values());
        ArrayList<String> strListaTokens = new ArrayList<>();
        listaTokens.forEach((tokn) -> strListaTokens.add(tokn.getToken()));
        return strListaTokens; 
    }
    
    
    
    
}
