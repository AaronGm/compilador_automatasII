/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import analizador.LexicoV2;
import java.awt.HeadlessException;

import views.CompilerGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

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
        this.view.getItemAbrir().addActionListener(accionesMenu());
        this.view.getItemGuardar().addActionListener(accionesMenu());
    }
    
    
    private ActionListener accionesMenu() {
        return (ActionEvent evt) -> {
            JMenuItem btnItem = (JMenuItem) evt.getSource();
            
            if (btnItem.equals(view.getItemAbrir())) {
                try{
                    String texto = null;
                    view.getFileChooser().showOpenDialog(view);
                    File file = view.getFileChooser().getSelectedFile();
                    if (file != null) {
                        view.getTpEditor().setText("");
                        FileReader reader = new FileReader(file);
                        try (BufferedReader read = new BufferedReader(reader)) {
                            while ((texto = read.readLine()) != null) {
                                view.getTpEditor().setText(view.getTpEditor().getText() + texto + "\n");
                            }
                        }
                    }
                } catch(HeadlessException | IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            
            if (btnItem.equals(view.getItemGuardar())) {
                try {
                    view.getFileChooser().showSaveDialog(view);
                    File file = view.getFileChooser().getSelectedFile();
                    try (FileWriter fw = new FileWriter(file + ".px")) {
                        fw.write(view.getTpEditor().getText());
                    }
                    JOptionPane.showConfirmDialog(null, "Archivo guardado correctamente!");
                } catch(HeadlessException | IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        };
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        
        if (btn.equals(view.getBtnCompilar())) {
            analizarTextPane(); // Separa las lineas leidas en el panel
            view.getModelTable().setRowCount(0);
            view.clearConsola();
            LexicoV2 lex = new LexicoV2(lineasCodigo);
            lex.analizarLineasCodigo();
            
            lex.getListaTokens().forEach((String[] lista) -> {
                view.getModelTable().addRow(lista);
            });
            
            
//            Lexico lex = new Lexico(lineasCodigo);
////            Sintactico sintactico = new Sintactico();
//            lex.validarTokens();
//            
//            // Agregar tokens a la tabla
//            lex.getListaTokens().forEach(lista -> {
//                view.getModelTable().addRow(lista);
//            });
//            
//            if (lex.getErroresLexicos().size() > 0) {
//                lex.getErroresLexicos().forEach(error -> {
//                    view.setErrConsola(error + "\n");
//                });
//            }
            
            
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
