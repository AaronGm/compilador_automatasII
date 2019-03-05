/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador.simbolos;

/**
 *
 * @author aarongmx
 */
public interface CaracterEspecial {
    String MAS = "+";
    String MENOS = "-";
    String PRINT = "->";
    String EXPONENTE = "^";
    String RAIZ = "_";
    String PUNTO = ".";
    String MAY_QUE = ">";
    String MAYI_QUE = ">=";
    String MEN_QUE = "<";
    String MENI_QUE = "<=";
    String ASIGNACION_NOR = "=";
    String ASIGNACION_INF = ":=";
    
    String[] caracteresEspeciales = {
        MAS,
        MENOS,
        PRINT,
        EXPONENTE,
        RAIZ,
        PUNTO,
        MAY_QUE,
        MAYI_QUE,
        MEN_QUE,
        MENI_QUE
    };
}
