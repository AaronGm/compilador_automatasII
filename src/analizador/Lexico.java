/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador;

import analizador.simbolos.CaracteresEspeciales;
import analizador.simbolos.Reservadas;
import analizador.simbolos.TipoDato;
import analizador.simbolos.VarConst;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aarongmx
 */
public class Lexico {

    private final ArrayList<String[]> listaTokens;
    private final ArrayList<String> lineasDeCodigo;
    private VarConst var;
    private final Map<String, Object> listaDeIDSconValor;
    private Object valueID;
    private String nameID;
    private final ArrayList<String> identificadores;
    private final ArrayList<String> erroresLexicos;

    public Lexico(ArrayList<String> lineaCodigo) {
        this.lineasDeCodigo = lineaCodigo;
        listaTokens = new ArrayList<>();
        listaDeIDSconValor = new HashMap<>();
        identificadores = new ArrayList<>();
        erroresLexicos = new ArrayList<>();
    }

    public void validarTokens() {
        // Léxico -> decir que simbolos están presentes en nuestro lenguaje
        lineasDeCodigo.forEach((String lineaCodigo) -> {

            if (!lineaCodigo.isEmpty()) {
                
                if (isString(lineaCodigo)) {
                
                    listaTokens.add(new String[]{lineaCodigo, "String"});
                
                } else if (buscarComentarios(lineaCodigo)) {
                    
                    listaTokens.add(new String[]{lineaCodigo, "Comentario"});
                    
                } else {
                    
                    if(lineaCodigo.contains(CaracteresEspeciales.ASIGNA_T_DATO.getCaracter()) || lineaCodigo.contains(CaracteresEspeciales.ASIGNACION.getCaracter())) {
                        String[] separarValorDeID = null;
                        String caracterAsignacion = "";
                        
                        
                        /**
                         * Verificar se se esta haciendo una asignación de una constante o una variable
                         */
                        if (lineaCodigo.contains(CaracteresEspeciales.ASIGNA_T_DATO.getCaracter())) {
                            caracterAsignacion = CaracteresEspeciales.ASIGNA_T_DATO.getCaracter();
                        } else if (lineaCodigo.contains(CaracteresEspeciales.ASIGNACION.getCaracter())) {
                            caracterAsignacion = CaracteresEspeciales.ASIGNACION.getCaracter();
                        }
                        separarValorDeID = lineaCodigo.split(caracterAsignacion);
                        

                        if (separarValorDeID.length > 1) {
                            valueID = separarValorDeID[1];
                        }

                        String[] creacionVarConst = separarValorDeID[0].split(hayCaracteresEspeciales(separarValorDeID[0]));
                        
                        buscarPalabraReservada(creacionVarConst[0]);
                        
                        if (creacionVarConst.length == 1) {
                            listaTokens.add(new String[] { creacionVarConst[0], "ID" });
                        } else {
                            listaTokens.add(new String[] { creacionVarConst[1], "ID" });
                        }
                        
                        if (creacionVarConst.length == 3) {
                            listaTokens.add(new String[] { creacionVarConst[2], "Tipo de dato" });
                        }
                        
                        listaTokens.add(new String[] { caracterAsignacion, "Carácter de asignación" });
                        
                        
                        switchTipoDato(String.valueOf(valueID));
                        
                    } else if(lineaCodigo.contains(Reservadas.OUT.getToken()) || lineaCodigo.contains(Reservadas.OUTLN.getToken())) {
                        String[] funciones = lineaCodigo.split(CaracteresEspeciales.PRINT.getCaracter());
                        
                        System.out.println(Arrays.toString(funciones));
                        
                    } else {
                        erroresLexicos.add(lineaCodigo);
                    }
                }
            }
        });
    }
    
    private String hayCaracteresEspeciales(String palabra) {
        String caracter = " ";
        for (String token : CaracteresEspeciales.getStrTokens()) {
            if (palabra.equals(token)) {
                caracter = token;
            }
        }
        return caracter;
    }
    
    private void buscarPalabraReservada(String palabra) {
        Reservadas.getTokens().stream().filter((token) -> (palabra.equals(token))).forEachOrdered((token) -> {
            listaTokens.add(new String[] { token, "Palabra reservada" });
        });
    }
    
    private boolean buscarComentarios(String palabra) {
        boolean isComment = false;
        if (palabra.startsWith(CaracteresEspeciales.HASH.getCaracter())) {
            isComment = true;
        }
        return isComment;
    }
    
    private void switchTipoDato(String palabra) {
        palabra = palabra.trim(); // -> Limpiar espacios en blanco inicio y final
        System.out.println(palabra);
        if (isString(palabra)) {
            listaTokens.add(new String[] { palabra, "String" });
        }
        
        if (isNumber(palabra)) {
            listaTokens.add(new String[] { palabra, "Int" });
        } else if (isFloat(palabra)) {
            listaTokens.add(new String[] { palabra, "Real" });
        }
    }
    
    private boolean isString(String palabra) {
        return palabra.startsWith("\"") && palabra.endsWith("\"");
    }

    private boolean isNumber(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isFloat(String cadena) {
        try {
            Float.parseFloat(cadena);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public ArrayList<String[]> getListaTokens() {
        return listaTokens;
    }

    public Map<String, Object> getListaDeIDSconValor() {
        return listaDeIDSconValor;
    }

    public ArrayList<String> getIdentificadores() {
        return identificadores;
    }

    public ArrayList<String> getErroresLexicos() {
        return erroresLexicos;
    }
}
