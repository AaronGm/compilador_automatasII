/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador;

import analizador.simbolos.VarConst;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author aarongmx
 */
public class Sintactico {
    private ArrayList<String> listaLexico;
    private Map<String, VarConst> mapaVarConst;
    // Sintactico -> Concordancia o jerárquia de expresiones
    
    public Sintactico() {}
    
    public Sintactico(ArrayList<String> listaLexico) {
        this.listaLexico = listaLexico;
    }
    
    public void analizarArbol() {
        for (String string : listaLexico) {
            System.out.println(string);
        }
    }

    public void setListaLexico(ArrayList<String> listaLexico) {
        this.listaLexico = listaLexico;
    }

    public ArrayList<String> getListaLexico() {
        return listaLexico;
    }

    public Map<String, VarConst> getMapaVarConst() {
        return mapaVarConst;
    }

    public void setMapaVarConst(Map<String, VarConst> mapaVarConst) {
        this.mapaVarConst = mapaVarConst;
    }
}
