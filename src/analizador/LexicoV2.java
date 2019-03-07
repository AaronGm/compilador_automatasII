/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador;

import analizador.simbolos.CaracterEspecial;
import analizador.simbolos.CaracteresEspecialesPixel;
import analizador.simbolos.PalabrasReservadas;
import analizador.simbolos.PalabrasReservadasPixel;
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
public class LexicoV2 {
    private final ArrayList<String> lineasCodigo;
    private final ArrayList<String[]> listaTokens;
    private final PalabrasReservadasPixel palabrasReservadas;
    private final CaracteresEspecialesPixel caracteresEspecialiesPixel;
    private final HashSet<String> ids;
    private final ArrayList<String> arbolS;
    private VarConst vConst;
    private Map<String, VarConst> mapaVarConst;
    private String nodoArbolS;
    
    public LexicoV2(ArrayList<String> lineasCodigo) {
        this.lineasCodigo = lineasCodigo;
        this.listaTokens = new ArrayList<>();
        this.ids = new HashSet<>();
        this.palabrasReservadas = new PalabrasReservadasPixel(listaTokens);
        this.caracteresEspecialiesPixel = new CaracteresEspecialesPixel(listaTokens);
        this.arbolS = new ArrayList<>();
        this.mapaVarConst = new HashMap<>();
        this.nodoArbolS = "";
    }
    
    public void analizarLineasCodigo() {
        lineasCodigo.forEach((String linea) -> {
            
            String[] tokens = {};
            String caracterSplit;
            
            // Buscamos valores que esten fuera de los criterios de la creación de una variable
            if (palabrasReservadas.esCadena(linea)) {
                listaTokens.add(new String[] { linea, "String" });
            }
            
            if (linea.startsWith("#")) {
                listaTokens.add(new String[] { linea, "Comentario" });
            }
            
            // Buscamos las asignaciones en cada línea, es decir que exista un signo = ó :=
            if (linea.contains(CaracterEspecial.ASIGNACION_NOR) || linea.contains(CaracterEspecial.ASIGNACION_INF)) {
                
                // Variable para hacer el split de la cadena
                caracterSplit = CaracterEspecial.ASIGNACION_NOR;
                
                // Si la cadena tiene contiene el carácter .=
                if (linea.contains(CaracterEspecial.ASIGNACION_INF)) {
                    caracterSplit = CaracterEspecial.ASIGNACION_INF;
                }
                
                // Dividir la cadena, según el tipo de asignación
                tokens = linea.split(caracterSplit);
                vConst = new VarConst();
                // Verificamos si el ID ya existe en nuestra tabla de tokens registrados!
                if (existsID(tokens[0].trim())) {
                    agregarID(tokens[0]);
                }
                
                // Verificamos la creación de una variable o una constante
                for (String token : tokens[0].split(" ")) {
                    if (palabrasReservadas.isDataType(token)) {
                        palabrasReservadas.agregaTipoDato(token);
                        vConst.setType(token);
                    } else if (palabrasReservadas.isReservedWord(token)) {
                        palabrasReservadas.agregarPalabraReservada(token);
                    } else if (!palabrasReservadas.isDataType(token) && !palabrasReservadas.isReservedWord(token)) {
                        analizarVar(tokens[0].split(" "));
                    }
                }
                
                // Verifica el tipo de carácter para la signación
                caracteresEspecialiesPixel.esAsignacion(caracterSplit);
                
                for (String token : tokens) {
                    token = token.trim();
                    if (palabrasReservadas.esCadena(token)) {
                        listaTokens.add(new String[] { token, "String" });
                        vConst.setType("str");
                        vConst.setValue(token);
                    }
                    if(palabrasReservadas.esNumeroEntero(token)) {
                        listaTokens.add(new String[] { token, "Int" });
                        vConst.setType("int");
                        vConst.setValue(token);
                    }else if(palabrasReservadas.esNumeroReal(token)) {
                        listaTokens.add(new String[] { token, "Real" });
                        vConst.setType("real");
                        vConst.setValue(token);
                    }
                }
//                System.out.println(nodoArbolS);
                mapaVarConst.put(vConst.getName(), vConst);
                
                mapaVarConst.forEach((id, el) -> {
                    System.out.println(el.toString());
                });
                        
//                System.out.println(vConst.toString());
            } // Fin de creación de variable
            
            // Verifica que se haga una impresión por consola
            if (linea.contains(PalabrasReservadas.OUT) || linea.contains(PalabrasReservadas.OUTLN)) {
                if (linea.contains(CaracterEspecial.PARENTESIS_I) && linea.endsWith(CaracterEspecial.PARENTESIS_D)) {
                    linea = addSpaces(linea, CaracterEspecial.PARENTESIS_I);
                    linea = addSpaces(linea, CaracterEspecial.PARENTESIS_D);
                    linea = addSpaces(linea, CaracterEspecial.MAS);
                    
                    String[] contenidoParentesis;
                    
                    contenidoParentesis = linea.split(" ");
                    
                    String cad = "";
                    for (String item : contenidoParentesis) {
                        item = item.trim();
                        
                        if (item.startsWith("\"") && !item.endsWith("\"")) {
                            cad = item;
                        }
                        
                        if (!item.startsWith("\"") && item.endsWith("\"") && !cad.equals(item)) {
                            cad += " " + item;
                            item = cad;
                        }
                        
                        if (palabrasReservadas.isDataType(item)) {
                            palabrasReservadas.agregaTipoDato(item);
                        } else if (palabrasReservadas.isReservedWord(item)) {
                            palabrasReservadas.agregarPalabraReservada(item);
                        } else if (existsID(item)) {
                            agregarID(item);
                        } else if(caracteresEspecialiesPixel.isSpecial(item)) {
                            caracteresEspecialiesPixel.agregarEspecial(item);
                        }
                        
                        palabrasReservadas.buscaTipoDatoPorValor(item);
                    }
                }
            } // Fin de la verificación léxica para la consola
            
            if (linea.startsWith(PalabrasReservadas.IG) || linea.startsWith(PalabrasReservadas.DV)) {
                for (String token : CaracterEspecial.MATHS) {
                    linea = addSpaces(linea, token);
                }
                
                String[] content = linea.split(" ");
                for (String token : content) {
//                    System.out.println(token);
                    if (palabrasReservadas.isDataType(token)) {
                        palabrasReservadas.agregaTipoDato(token);
                    } else if (palabrasReservadas.isReservedWord(token)) {
                        palabrasReservadas.agregarPalabraReservada(token);
                    } else if (existsID(token)) {
                        agregarID(token);
                    } else if (caracteresEspecialiesPixel.isSpecial(token)) {
                        caracteresEspecialiesPixel.agregarEspecial(token);
                    } else if(!palabrasReservadas.esNumeroEntero(token)) {
                        listaTokens.add(new String[]{ token, "Expresión Matématica" });
                    }

                    palabrasReservadas.buscaTipoDatoPorValor(token);
                }
            }
        });
    }
    
    public void analizarVar(String[] createVar) {
        if (createVar[0].equals(PalabrasReservadas.VAR) || createVar[0].equals(PalabrasReservadas.CONST)) {
            listaTokens.add(new String[] { createVar[1], "ID" });
            ids.add(createVar[1]);
            vConst.setName(createVar[1]);
        }
    }
    
    private boolean existsID(String token) {
        boolean existID = false;
        for (String id : ids) {
            if (token.equals(id)) {
                existID = true;
            }
        }
        return existID;
    }
    
    private void agregarID(String token) {
        listaTokens.add(new String[] { token.trim(), "ID" });
    }
    
    private String addSpaces(String cad, String car) {
        String div = " " + car + " ";
        if (cad.contains(car) && !cad.contains(div)) {
            if (cad.contains(" " + car) && !cad.contains(car +" ")) {
                cad = cad.replace(" " + car, div);
            } else if (cad.contains(car + " ") && !cad.contains(" " + car)) {
                cad = cad.replace(car + " ", div);
            } else {
                cad = cad.replace(car, div);
            }
        }
        return cad;
    }
    
    private void addToNode(String typeNode, String node) {
        nodoArbolS += ":" + typeNode + "|" + node;
    }
    
    public ArrayList<String[]> getListaTokens() {
        return listaTokens;
    }
}
