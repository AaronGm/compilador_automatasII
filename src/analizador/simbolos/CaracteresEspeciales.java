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
public enum CaracteresEsp {
    
    MAS("+"),
    MENOS("-"),
    GUION_B("_"),
    DIAGONAL("/"),
    DIAGONAL_INV("\\"),
    HASH("#"),
    PAREN_I("("),
    PAREN_D(")"),
    LLAVE_I("{"),
    LLAVE_D("}"),
    COMA(","),
    PUNTO_COMA(";"),
    DOS_PUNTOS(":"),
    CORCH_I("["),
    CORCH_D("]"),
    RAIZ("^"),
    PUNTO("."),
    DOB_COMILLA("\""),
    PRINT("->"),
    POR("*"),
    ASIGNACION("=");
    
    private final String caracter;

    private CaracteresEsp(String caracter) {
        this.caracter = caracter;
    }

    public String getCaracter() {
        return caracter;
    }
    
    public static List<String> getStrTokens() {
        List<CaracteresEsp> listaTokens = Arrays.asList(CaracteresEsp.values());
        ArrayList<String> strListaTokens = new ArrayList<>();
        listaTokens.forEach((tokn) -> strListaTokens.add(tokn.getCaracter()));
        return strListaTokens;
    }
    
    
    
    
}
