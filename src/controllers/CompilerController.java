/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import analizador.Lexico;
import analizador.Sintactico;

import views.CompilerGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JButton;

/**
 *
 * @author aarongmx
 */
public class CompilerController implements ActionListener {

    private final CompilerGUI view;
    private final ArrayList<String> lineasCodigo;

    public CompilerController(CompilerGUI view) {
        this.view = view;
        lineasCodigo = new ArrayList<>();
        this.view.getBtnCompilar().addActionListener(this);
        this.view.getBtnErrores().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        
        if (btn.equals(view.getBtnCompilar())) {
            analizarTextPane(); // Separa las lineas leidas en el panel
            view.getModelTable().setRowCount(0);
            view.clearConsola();
            
            Lexico lex = new Lexico(lineasCodigo);
//            Sintactico sintactico = new Sintactico();
            lex.validarTokens();
            
            // Agregar tokens a la tabla
            lex.getListaTokens().forEach(lista -> {
                view.getModelTable().addRow(lista);
            });
            
            if (lex.getErroresLexicos().size() > 0) {
                lex.getErroresLexicos().forEach(error -> {
                    view.setErrConsola(error + "\n");
                });
            }
            
            
        }

        if (btn.equals(view.getBtnErrores())) {
            System.out.println("Detectando errores");
        }
    }

    /**
     * Descompone linea a linea lo leido en el panel
     */
    private void analizarTextPane() {
        lineasCodigo.clear();
        String text = view.getTxPane().getText();
        // Esta línea es mágica, permite descomponer linea a linea el texto en el textpanel
        StringTokenizer strToken = new StringTokenizer(text, "\n");

        // Si existen tokens separados por salto de línea se agregan al ArrayList
        while (strToken.hasMoreTokens()) {
            lineasCodigo.add(strToken.nextToken().trim());
        }
    }
}
