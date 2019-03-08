/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import controllers.CompilerController;
import views.CompilerGUI;
import java.awt.EventQueue;


/**
 *
 * @author aarongmx
 */
public class App {
    public static void main(String[] args) {
        Runnable run = () -> {
            CompilerController compilerController = new CompilerController(new CompilerGUI());
        };
        EventQueue.invokeLater(run);
    }
}
