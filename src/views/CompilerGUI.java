package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aarongmx
 */
public class CompilerGUI extends JFrame {
    private JPanel pnlRoot;
    
    private JPanel pnlHead;
    private JPanel pnlDer;
    private JPanel pnlBajo;
    
    private JTextPane tpEditor;
    private JLabel lbNombreCompiler;
    private JTextPane tpConsola;
    
    private JTable tbTokens;
    
    private DefaultTableModel modelTable;
    
    private JScrollPane scrollTabla;
    private JScrollPane scrollEditor;
    private JScrollPane scrollConsola;
    
    private JButton btnCompilar;
    private JButton btnErrores;

    public CompilerGUI() {
        initComponent();
        initComponents();
        setComponents();
    }

    private void initComponent() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize(); 
        int ancho = screen.width;
        int largo = screen.height;
        setTitle("PIXEL Compiler");
        URL iconUrl = getClass().getResource("/assets/img/icon48x48.png");
        ImageIcon icon = new ImageIcon(iconUrl);
        setIconImage(icon.getImage());
        setMinimumSize(new Dimension(((ancho / 3) * 2) + 300, largo - 60));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initComponents() {
        pnlRoot = (JPanel) getContentPane();
        
        pnlHead = new JPanel();
        pnlDer = new JPanel(new BorderLayout());
        pnlBajo = new JPanel(new BorderLayout());
        
        tpEditor = new JTextPane();
        tpEditor.setBorder(new EmptyBorder(24, 24, 24, 24));
        tpEditor.setFont(inputFont());
        
        tpConsola = new JTextPane();
        tpConsola.setEditable(false);
        tpConsola.setFont(addFont("RobotoMono-Medium.ttf", 15));
        tpConsola.setBackground(new Color(0x535459));
        tpConsola.setForeground(Color.white);
        
        lbNombreCompiler = new JLabel("Pixel Lang");
        lbNombreCompiler.setFont(titleFont(21));
        tbTokens = new JTable();
        
        tbTokens.setFont(inputFont());
        
        modelTable = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        btnCompilar = new JButton("Compilar");
        btnCompilar.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/assets/img/play-icon.png")).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
        
        btnErrores = new JButton("Errores");
        btnErrores.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/assets/img/debug.png")).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
        
        scrollTabla = new JScrollPane(tbTokens);
        scrollEditor = new JScrollPane(tpEditor);
        scrollConsola = new JScrollPane(tpConsola);
        
        estilosJScrollPanes(scrollConsola, scrollEditor, scrollTabla);
        initPnlHead();
        initPnlDer();
        initPnlBajo();
        initTablaTokens();
    }
    
    private void initPnlHead() {
        pnlHead.setLayout(new BoxLayout(pnlHead, BoxLayout.X_AXIS));
        pnlHead.setBackground(Color.white);
        pnlHead.setBorder(new EmptyBorder(16, 24, 16, 24));
        
        pnlHead.add(lbNombreCompiler);
        pnlHead.add(Box.createGlue());
        pnlHead.add(btnCompilar);
        pnlHead.add(Box.createHorizontalStrut(16));
        pnlHead.add(btnErrores);
    }
    
    private void initPnlDer() {
        pnlDer.setPreferredSize(new Dimension(460, 0));
        pnlDer.add(BorderLayout.CENTER, tpConsola);
    }
    
    private void initTablaTokens() {
        scrollTabla.setPreferredSize(new Dimension(0, 200));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        modelTable.setColumnIdentifiers(new String[] { "Token", "Descripción" });
        tbTokens.setModel(modelTable);
        tbTokens.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tbTokens.getColumnModel().getColumn(0).setMinWidth(340);
        tbTokens.getColumnModel().getColumn(0).setMaxWidth(340);
    }
    
    private void setComponents() {
        pnlRoot.setBackground(Color.white);
        pnlRoot.add(BorderLayout.NORTH, pnlHead);
        pnlRoot.add(BorderLayout.CENTER, scrollEditor);
        pnlRoot.add(BorderLayout.EAST, pnlDer);
        pnlRoot.add(BorderLayout.SOUTH, pnlBajo);
    }
    
    private void initPnlBajo() {
        pnlBajo.add(BorderLayout.CENTER, scrollTabla);
    }
    
    private Font addFont(String nombre, float tam) {
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/fonts/" + nombre));
            font = font.deriveFont(tam);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(CompilerGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return font;
    }
    
    
    private Font inputFont() {
        return addFont("Input.ttf", 15);
    }
    
    private Font titleFont(float tam) {
        return addFont("RobotoMono-Regular.ttf", tam);
    }

    public JTextPane getTxPane() {
        return tpEditor;
    }

    public JTextPane getTpEditor() {
        return tpEditor;
    }

    public DefaultTableModel getModelTable() {
        return modelTable;
    }

    public JButton getBtnCompilar() {
        return btnCompilar;
    }

    public JButton getBtnErrores() {
        return btnErrores;
    }

    public void setOutConsola(String out) {
        tpConsola.setForeground(Color.white);
        this.tpConsola.setText(out);
    }
    
    public void setErrConsola(String err) {
        tpConsola.setForeground(new Color(0xf88279));
        this.tpConsola.setText(tpConsola.getText() + err);
    }
    
    
    private void estilosJScrollPanes(JScrollPane... scrollpanes) {
        Arrays.asList(scrollpanes).forEach(scrollpane -> {
            scrollpane.setBorder(null);
            scrollpane.getVerticalScrollBar().setBlockIncrement(18);
        });
    }

    
    

}
