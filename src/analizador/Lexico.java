/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador;

import analizador.simbolos.CaracteresEspeciales;
import analizador.simbolos.Reservadas;
import analizador.simbolos.VarConst;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
    private final HashSet<String> identificadores;
    private final ArrayList<String> erroresLexicos;
    private final ArrayList<String> expresiones;
    private String expresion;
    
    public Lexico(ArrayList<String> lineaCodigo) {
        this.lineasDeCodigo = lineaCodigo;
        listaTokens = new ArrayList<>();
        listaDeIDSconValor = new HashMap<>();
        identificadores = new HashSet<>();
        erroresLexicos = new ArrayList<>();
        expresiones = new ArrayList<>();
    }

    public void validarTokens() {
        // Léxico -> decir que simbolos están presentes en nuestro lenguaje
        lineasDeCodigo.forEach((String lineaCodigo) -> {

            if (!lineaCodigo.isEmpty()) {
                
                if (isString(lineaCodigo)) {
                    listaTokens.add(new String[]{lineaCodigo, "String"});
                    expresion += lineaCodigo;
                
                } else if (buscarComentarios(lineaCodigo)) {
                    listaTokens.add(new String[]{lineaCodigo, "Comentario"});
                    expresion += lineaCodigo;
                } else {
                    
                    if(lineaCodigo.contains(CaracteresEspeciales.ASIGNA_T_DATO.getCaracter()) || lineaCodigo.contains(CaracteresEspeciales.ASIGNACION.getCaracter())) {
                        String[] separarValorDeID = {};
                        String caracterAsignacion = "";
                        boolean errorCreateId = false;
                        
                        
                        /**
                         * Verificar si se esta haciendo una asignación de una constante o una variable
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
                        String strVarConst = creacionVarConst[0];
                        
                        buscarPalabraReservada(strVarConst);

                        if ((lineaCodigo.startsWith(Reservadas.VAR.getToken()) && strVarConst.equals(Reservadas.VAR.getToken())) || (lineaCodigo.startsWith(Reservadas.CONST.getToken()) && strVarConst.equals(Reservadas.CONST.getToken()))) {
                            identificadores.add(nameID);
                        } else {
                            if (creacionVarConst.length > 1) {
                                nameID = creacionVarConst[0];
                                listaTokens.add(new String[] { nameID, "ID" });
                                errorCreateId = true;
                                
                            }
                            erroresLexicos.add("ERROR::LÉXICO -> El identificador " + nameID + " debe definirse antes de usarse...");
                        }
                        
                        if (!errorCreateId) {
                            if (creacionVarConst.length == 1) {
                                nameID = creacionVarConst[0];
                            } else {
                                nameID = creacionVarConst[1];
                            }

                            listaTokens.add(new String[]{nameID, "ID"});
                        }
                        
                        if (creacionVarConst.length == 3) {
                            listaTokens.add(new String[] { creacionVarConst[2], "Tipo de dato" });
                        } else if (errorCreateId) {
                            listaTokens.add(new String[] { creacionVarConst[1], "Tipo de dato" });
                        }
                        
                        listaTokens.add(new String[] { caracterAsignacion, "Carácter de asignación" });
                        
                        
                        switchTipoDato(String.valueOf(valueID));
                    } else if(lineaCodigo.contains(Reservadas.OUT.getToken()) || lineaCodigo.contains(Reservadas.OUTLN.getToken())) {
                        String[] funciones = lineaCodigo.split(CaracteresEspeciales.PRINT.getCaracter());
                        String funcion = funciones[0].trim();
                        String valorImprimir = funciones[1].trim();
                        
                        String dividirConcatenacion[] = null;
                        
                        if (valorImprimir.contains(CaracteresEspeciales.MAS.getCaracter())) {
                            dividirConcatenacion = valorImprimir.split("\\+");
                        }
                        
                        if (!isString(valorImprimir) && !isNumber(valorImprimir) && !isFloat(valorImprimir) && !isID(valorImprimir)) {
                            if (dividirConcatenacion != null) {
                                for (String item : dividirConcatenacion) {
                                    if (!isID(item) && !isNumber(dividirConcatenacion[0])) {
                                        erroresLexicos.add("ERROR::LÉXICO -> " + item);
                                    }
                                }
                            } else {
                                erroresLexicos.add("ERROR::LÉXICO -> " + lineaCodigo);
                            }
                            
                        }
                        
                        listaTokens.add(new String[] { funcion, "Palabra reservada" });
                        listaTokens.add(new String[] { CaracteresEspeciales.PRINT.getCaracter(), "Carácter Especial" });
                        
                        
                        
                        switchTipoDato(valorImprimir);
                    } else if(lineaCodigo.contains(Reservadas.DV.getToken()) || lineaCodigo.contains(Reservadas.IT.getToken())) {
                        String[] funciones = lineaCodigo.split(" ");
                    
                    } else {
                        erroresLexicos.add(lineaCodigo);
                    }
                }
            }
            expresiones.add(expresion);
        });
        
        identificadores.forEach(System.out::println);
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
        String tipoDato = "";
        if (isString(palabra)) {
            tipoDato = "String";
        } else if (isNumber(palabra)) {
            tipoDato = "Int";
        } else if (isFloat(palabra)) {
            tipoDato = "Real";
        } else {
            erroresLexicos.add("ERROR::LÉXICO -> " + palabra);
        }
        
        listaTokens.add(new String[] { palabra, tipoDato });
    }

    private boolean isID(String palabra) {
        boolean isID = false;
        for (String identificador : identificadores) {
            if (palabra.equals(identificador)) {
                isID = true;
            }
        }
        return isID;
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

    public HashSet<String> getIdentificadores() {
        return identificadores;
    }

    public ArrayList<String> getErroresLexicos() {
        return erroresLexicos;
    }
}
