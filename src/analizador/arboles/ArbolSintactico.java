/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador.arboles;

/**
 *
 * @author aarongmx
 */
public enum ArbolSintactico {
    
    VARIABLE_STR("var id asign str"),
    VARIABLE_INT("var id asign int"),
    VARIABLE_REAL("var id asign real"),
    FUNCION_OUT("out print_simbol id"),
    CONCATENAR_STRS("str + str"),
    CONCATENAR_STR_ID("str + id"),
    CONCATENAR_ID_STR("id + str"),
    INTERPOLAR_STR("#{id}"),
    ;
    
    private final String expresion;

    private ArbolSintactico(String expresion) {
        this.expresion = expresion;
    }

    public String getExpresion() {
        return expresion;
    }
    
    
    
}
