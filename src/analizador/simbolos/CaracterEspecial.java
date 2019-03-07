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
    String PARENTESIS_I = "(";
    String PARENTESIS_D = ")";
    String HASH = "#";
    
    String[] CARACTERES_ESPECIALES = {
        MAS,
        MENOS,
        PRINT,
        EXPONENTE,
        RAIZ,
        PUNTO,
        MAY_QUE,
        MAYI_QUE,
        MEN_QUE,
        MENI_QUE,
        ASIGNACION_NOR,
        ASIGNACION_INF,
        HASH,
        PARENTESIS_I,
        PARENTESIS_D
    };
    
    String[] MATHS = {
        MAS,
        MENOS,
        RAIZ,
        EXPONENTE,
        MAY_QUE,
        MAYI_QUE,
        MEN_QUE,
        MENI_QUE
    };
    
    String[] ASSIGN = {
        ASIGNACION_INF,
        ASIGNACION_NOR
    };
}
