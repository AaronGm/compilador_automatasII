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
public interface PalabrasReservadas {
    String STR = "str";
    String INT = "int";
    String REAL = "real";
    String VAR = "var";
    String CONST = "const";
    String OUT = "out";
    String OUTLN = "outln";
    String IG = "ig";
    String DV = "dv";
    
    String[] PALABRAS_RESERVADAS = {
        VAR,
        CONST,
        OUT,
        OUTLN,
        IG,
        DV
    };
    
    String[] TIPOS_DATO = {
        STR,
        INT,
        REAL
    };
}