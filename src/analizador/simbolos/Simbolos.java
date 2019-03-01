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
public interface Simbolos {
    String[] PALABRAS_RES = { 
        "var",
        "const",
        "bool",
        "true",
        "false",
        "int",
        "str",
        "real",
        "print",
        "println"
    };
    
    char[] SIMBOLOS_ESPECIALES = {
        '"',
        '\'',
        '+',
        '-',
        '|',
        '*',
        '(',
        ')',
        '[',
        ']',
        '{',
        '}',
        '.',
        ':',
        '=',
        '#',
        ';' // <- Simbolo sagrado :'v
    };
}
