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
public enum TipoDato {
    STR("str"),
    INT("int"),
    REAL("real"),
    ;
    
    private final String tipoDato;

    private TipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public String getTipoDato() {
        return tipoDato;
    }
    
    public static List<String> getTiposDeDato() {
        List<TipoDato> listaTiposDato = Arrays.asList(TipoDato.values());
        ArrayList<String> strTiposDeDato = new ArrayList<>();
        listaTiposDato.forEach(el -> strTiposDeDato.add(el.getTipoDato()));
        return strTiposDeDato;
    }
    
}
