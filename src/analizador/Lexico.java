/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador;

import analizador.simbolos.CaracteresEsp;
import analizador.simbolos.Reservadas;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aarongmx
 */
public class Lexico {
    private ArrayList<String[]> listaTokens;
    private final ArrayList<String> lineaCodigo;
    private Map<String, Object> identificadoValor;
    private Object obj;
    private String id;
    private ArrayList<String> identificadores;
    
    public Lexico(ArrayList<String> lineaCodigo) {
        this.lineaCodigo = lineaCodigo;
        listaTokens = new ArrayList<>();
        identificadoValor = new HashMap<>();
        identificadores = new ArrayList<>();
    }
    
    public void validarTokens() {
        // Léxico -> decir que simbolos están presentes en nuestro lenguaje
        ArrayList<String> exp = new ArrayList<>();
        lineaCodigo.forEach((String strLinea) -> {
            System.out.println(strLinea);
            
            if (!strLinea.isEmpty()) {
                if (isString(strLinea)) {
                    listaTokens.add(new String[] { strLinea, "String" });
                    exp.add(strLinea);
                } else if(buscarComentarios(strLinea)) {
                    listaTokens.add(new String[] { strLinea, "Comentario" });
                    exp.add(strLinea);
                } else {
                    String[] splitLinea = strLinea.split(" ");
                    //System.out.println(Arrays.toString(splitLinea));
                    
                    Arrays.asList(splitLinea).forEach((String token) -> {
                        
                        if (!identificadores.isEmpty()) {
                            identificadores.forEach(identif -> {
                                if (token.equals(identif)) {
                                    listaTokens.add(new String[] { token, "Identificador" });
                                    exp.add("ID");
                                }
                            });
                        }
                        
                        
                        Reservadas.getTokens().forEach((String reserv) -> {
                            if (token.equals(reserv)) {
                                listaTokens.add(new String[] { token, "Palabra reservada" });
                                exp.add("RESERV");
                            }
                        });
                        
                        if (splitLinea.length > 1) {
                            boolean isId = false;
                            if (token.equals(splitLinea[1])) {
                                char[] chars = token.toCharArray();
                                for (char c : chars) {
                                    if (Character.isAlphabetic(c) | Character.isDigit(c)) {
                                        isId = true;
                                    }
                                }
                            }
                            if (isId) {
                                identificadores.add(token);
                                identificadoValor.put(token, obj);
                                listaTokens.add(new String[] { token, "Identificador" });
                                exp.add("ID");
                            }
                        }
                        
                                                
                        if (isString(token)) {
                            listaTokens.add(new String[] { token, "String" });
                            obj = String.valueOf(token);
                            exp.add("STR");
                        }

                        CaracteresEsp.getStrTokens().stream().filter((String strToken) -> (token.equals(strToken))).forEachOrdered((String _item) -> {
                            //System.out.println(_item);
                            listaTokens.add(new String[] { token, "Carácter especial" });
                            exp.add("CESP");
                        });

                        if (isNumber(token)) {
                            listaTokens.add(new String[] { token, "Int" });
                            obj = Integer.parseInt(token);
                            exp.add("INT");
                        } else if (isFloat(token)) {
                            listaTokens.add(new String[] { token, "Real" });
                            obj = Float.parseFloat(token);
                            exp.add("REAL");
                        }
                    });
                    //System.out.println(Arrays.toString(exp.toArray()));
                    exp.clear();
                }
            }
        });
    }
    
    private void isId(String palabra) {
        boolean isID = false;
        if (isReservada(palabra) == false) {
            System.out.println(palabra);
            listaTokens.add(new String[] { palabra, "Identificador" });
        }
    }
    
    private boolean buscarComentarios(String palabra) {
        boolean isComment = false;
        if (palabra.startsWith(CaracteresEsp.HASH.getCaracter())) {
            isComment = true;
        }
        return isComment;
    }
    
    private boolean isCharEsp(String palabra) {
        boolean isChar = false;
        for (String caracter : CaracteresEsp.getStrTokens()) {
            if (palabra.equals(caracter)) {
                isChar = true;
            }
        }
        return isChar;
    }
    
    private boolean isReservada(String palabra) {
        boolean isRes = false;
        for (String token : Reservadas.getTokens()) {
            if (palabra.equals(token)) {
                isRes = true;
            }
        }
        return isRes; 
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
        }catch(NumberFormatException e) {
            return false;
        }
    }

    public ArrayList<String[]> getListaTokens() {
        return listaTokens;
    }
    
    
}
