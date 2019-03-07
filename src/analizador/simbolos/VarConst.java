/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador.simbolos;

/**
 * Clase para almacenar el nombre y el valor de cada variable
 * @author aarongmx
 */
public class VarConst {
    private String name;
    private Object value;
    private String type;

    public VarConst() {
    }
    
    public VarConst(String name, Object value, String type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "VarConst{" + "name=" + name + ", value=" + value + ", type=" + type + '}';
    }
}

