/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador;

import analizador.simbolos.CaracterEspecial;
import analizador.simbolos.PalabrasReservadas;
import analizador.simbolos.VarConst;
import java.util.ArrayList;
import java.util.Arrays;
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
    private String typeID;
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
                    if (lineaCodigo.contains(CaracterEspecial.ASIGNACION_INF) || lineaCodigo.contains(CaracterEspecial.ASIGNACION_NOR)) {
                        
                        String caracterAsignacion = lineaCodigo.contains(CaracterEspecial.ASIGNACION_INF) ? CaracterEspecial.ASIGNACION_INF: CaracterEspecial.ASIGNACION_NOR;
                        String[] separaIdValor = lineaCodigo.split(caracterAsignacion);
                        String definicionVC = separaIdValor[0].trim();
                        String strValor = separaIdValor[separaIdValor.length - 1].trim();
                        boolean isDeclareTipoDato = false;
                        String tipoDato = null;
                        String[] arrDefinicionVC = definicionVC.split(" ");
                        String tipoID = arrDefinicionVC[0];
                        int ultimoEl = arrDefinicionVC.length - 1;
                        
                        if (isID(tipoID)) {
                            addID(tipoID);
                        } else {
                            addRV(tipoID);
                        }

                        if(arrDefinicionVC[0].equals(PalabrasReservadas.VAR) || arrDefinicionVC[0].equals(PalabrasReservadas.CONST)) {
                            // Creación de una variable o constante
                            // Verifica que exista el tipo de dato cuando se usa la notación [var | const] id = valor
                            if(arrDefinicionVC.length == 2 && !isDataType(arrDefinicionVC[ultimoEl]) && caracterAsignacion.equals(CaracterEspecial.ASIGNACION_NOR)) {
                                addErrLex("Falta el tipo de dato al identificador: " + arrDefinicionVC[ultimoEl]);
                            } else {
                                if (arrDefinicionVC.length == 3 && isDataType(arrDefinicionVC[ultimoEl])) {
                                    nameID = arrDefinicionVC[1].trim();
                                    isDeclareTipoDato = true;
                                    tipoDato = arrDefinicionVC[2].trim();
                                    
                                } else if (caracterAsignacion.equals(CaracterEspecial.ASIGNACION_INF)) {
                                    nameID = arrDefinicionVC[1].trim();
                                }
                                addID(nameID);
                                identificadores.add(nameID);
                            }
                             
                            
                        } else {
                            if (arrDefinicionVC[0].startsWith(PalabrasReservadas.VAR)) {
                                addErrLex("La definición de la variable: " + arrDefinicionVC[1] + " es incorrecta...");
                            } else if (arrDefinicionVC[0].startsWith(PalabrasReservadas.CONST)) {
                                addErrLex("La definición de la constante: " + arrDefinicionVC[1] + " es incorrecta...");
                            } else if (!isID(arrDefinicionVC[0])) {
                                addErrLex("El identificador: " + arrDefinicionVC[0] + " debe definirse antes de usarse...");
                            }
                        }
                        
                        if (isDeclareTipoDato) {
                            addTD(tipoDato);
                        }
                        
                        listaTokens.add(new String[] { caracterAsignacion, "Carácter de asignación" });
                        
                        var = new VarConst(nameID, strValor, switchTipoDato(strValor));
                    } else if(lineaCodigo.startsWith(PalabrasReservadas.OUT) || lineaCodigo.startsWith(PalabrasReservadas.OUTLN)) {
                        if(lineaCodigo.contains("(") && lineaCodigo.endsWith(")")) {
                            String lCod = lineaCodigo.replace(")", "").trim();
                            String[] arrOut = lCod.split("\\(");
                            addRV(arrOut[0].trim());
                            listaTokens.add(new String[]{ "(", "Carácter especial" });
                            String contenido = getContentBetween(lineaCodigo, "(", ")");
                            
                            listaTokens.add(new String[]{ ")", "Carácter especial" });
                        }
                    }
                }
            }
        });
    }
    
    private boolean isDataType(String word) {
        boolean isDtType = false;
        for (String tipoDato : PalabrasReservadas.TIPOS_DATO) {
            if (word.equals(tipoDato)) {
                isDtType = true;
            }
        }
        return isDtType;
    }
    
    private boolean buscarComentarios(String palabra) {
        boolean isComment = false;
        if (palabra.startsWith(CaracterEspecial.HASH)) {
            isComment = true;
        }
        return isComment;
    }
    
    private String switchTipoDato(String palabra) {
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
        return tipoDato;
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
    
    private String getContentBetween(String inp, String ini, String fin) {
        inp = inp.replace(fin, "").trim();
        String[] content = inp.split("\\" + ini);
        if (content.length == 2) {
            content = content[1].split("\\" + fin);
        }
        return Arrays.toString(content);
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
    
    private void addErrLex(String inErr) {
        erroresLexicos.add("ERROR::LEX -> " + inErr);
    }
    
    private void addTD(String td) {
        listaTokens.add(new String[] { td, "Tipo de dato" });
    }
    
    private void addID(String id) {
        listaTokens.add(new String[] { id, "Identificador" });
    }
    
    private void addRV(String rv) {
        listaTokens.add(new String[] { rv, "Palabra Reservada" });
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
