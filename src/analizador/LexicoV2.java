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
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author aarongmx
 */
public class LexicoV2 {
    private final ArrayList<String> lineasCodigo;
    private final ArrayList<String[]> listaTokens;
    private PalabrasReservadasPixel palabrasReservadas;
    private CaracteresEspecialesPixel caracteresEspecialiesPixel;
    private ArrayList<String> ids;
    
    public LexicoV2(ArrayList<String> lineasCodigo) {
        this.lineasCodigo = lineasCodigo;
        this.listaTokens = new ArrayList<>();
        this.ids = new ArrayList<>();
        this.palabrasReservadas = new PalabrasReservadasPixel(listaTokens);
        this.caracteresEspecialiesPixel = new CaracteresEspecialesPixel(listaTokens);
    }
    
    public void analizarLineasCodigo() {
        lineasCodigo.forEach(linea -> {
            String[] tokens = {};
            String caracterSplit;
            
            // Buscamos valores que esten fuera de los criterios de la creación de una variable
            if (palabrasReservadas.esCadena(linea)) {
                listaTokens.add(new String[] { linea, "String" });
            }
            
            if (linea.startsWith("#")) {
                listaTokens.add(new String[] { linea, "Comentario" });
            }
            
            // Buscamos las asignaciones, es decir que exista un signo = ó :=
            if (linea.contains(CaracterEspecial.ASIGNACION_NOR) || linea.contains(CaracterEspecial.ASIGNACION_INF)) {
                
                // Variable para hacer el split de la cadena
                caracterSplit = CaracterEspecial.ASIGNACION_NOR;
                
                // Si la cadena tiene contiene el carácter .=
                if (linea.contains(CaracterEspecial.ASIGNACION_INF)) {
                    caracterSplit = CaracterEspecial.ASIGNACION_INF;
                }
                
                // Dividir la cadena, según el tipo de asignación
                tokens = linea.split(caracterSplit);
                
                for (String token : tokens[0].split(" ")) {
                    if (palabrasReservadas.isDataType(token)) {
                        
                    } else if (palabrasReservadas.isReservedWord(token)) {
                        
                    } else if (!palabrasReservadas.isDataType(token) && !palabrasReservadas.isReservedWord(token)) {
                        analizarVar(tokens[0].split(" "));
                    }
                }
                
                listaTokens.add(new String[] { caracterSplit, "Asignación" });
                
                for (String token : tokens) {
                    token = token.trim();
                    if (palabrasReservadas.esCadena(token)) {
                        listaTokens.add(new String[] { token, "String" });
                    }
                }
                
            }
        });
    }
    
    public void analizarVar(String[] createVar) {
        if (createVar[0].equals(PalabrasReservadas.VAR) || createVar[0].equals(PalabrasReservadas.CONST)) {
            listaTokens.add(new String[] { createVar[1], "ID" });
            ids.add(createVar[1]);
        }
    }
    
    private boolean existsID(String token) {
        return ids.stream().findFirst().equals(token);
    }
    
    
    public ArrayList<String[]> getListaTokens() {
        return listaTokens;
    }
}
