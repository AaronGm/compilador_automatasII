package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author aarongmx
 */
public class CompilerGUI extends JFrame {
    private JPanel pnlRoot;
    private JPanel pnlIzq;
    
    private StyleContext ctx;
        
    private JTextPane txPane;
    private JLabel lbNombreCompiler;
    private JTable tbTokens;
    private JList<String> lsErrores;
    
    private DefaultListModel modelList;
    private DefaultTableModel modelTable;
    
    private JScrollPane scrollTabla;
    private JScrollPane scrollTextPane;
    
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
        setTitle("Compilador K'OZ");
        setMinimumSize(new Dimension(((ancho / 3) * 2) + 300, largo - 60));
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initComponents() {
        pnlRoot = (JPanel) getContentPane();
        pnlIzq = new JPanel(new GridBagLayout());
        txPane = new JTextPane();
        DefaultStyledDocument doc = (DefaultStyledDocument) txPane.getStyledDocument();
        Style style = doc.addStyle("style", null);
        StyleConstants.setForeground(style, Color.red);
        
        txPane.setText("# Mi propio lang\n" +
"var x = \"Hola\"\n" +
"var y = \"Mundo\"\n" +
"var r = 6 + 12\n" +
"var f = 12 ** 2\n" +
"var m = 12 | 2\n" +
"var rela = 20 ** 2\n" +
"const miConstante = 20.3\n" +
"out -> x + y\n" +
"outln -> 20+10\n" +
"\"Esto es un pequeño texto que se me ocurrió\"\n" +
"20 + 10\n" +
"\n" +
"x = \"Bola\"\n" +
"\n" +
"var fs=80+100\n" +
"\n" +
"20 + 20 * (31 - 10)");
        
        lbNombreCompiler = new JLabel("K'OZ Compiler");
        lsErrores = new JList<>();
        tbTokens = new JTable();
        
        tbTokens.setFont(new Font("Arial", Font.TRUETYPE_FONT, 14));
        
        modelList = new DefaultListModel();
        modelTable = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        btnCompilar = new JButton("Compilar");
        btnErrores = new JButton("Ver errores");
        
        initPnlIzq();
        initTablaTokens();
        initTxPane();
    }
    
    private void initPnlIzq() {
        GridBagConstraints c = new GridBagConstraints();
        pnlIzq.add(btnCompilar, c);
        pnlIzq.add(btnErrores, c);
    }
    
    private void initTablaTokens() {
        scrollTabla = new JScrollPane(tbTokens);
        scrollTabla.setPreferredSize(new Dimension(0, 250));
        modelTable.setColumnIdentifiers(new String[] { "Token", "Descripción" });
        tbTokens.setModel(modelTable);
    }
    
    private void initTxPane() {
        scrollTextPane = new JScrollPane(txPane);
        txPane.setFont(inputFont());
        
    }
    
    private void setComponents() {
        pnlRoot.add(BorderLayout.NORTH, lbNombreCompiler);
        pnlRoot.add(BorderLayout.CENTER, scrollTextPane);
        pnlRoot.add(BorderLayout.WEST, pnlIzq);
        pnlRoot.add(BorderLayout.SOUTH, scrollTabla);
    }
    
    
    private Font inputFont() {
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/fonts/Input.ttf"));
            font = font.deriveFont(15f);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(CompilerGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return font;
    }

    public JTextPane getTxPane() {
        return txPane;
    }

    public DefaultListModel getModelList() {
        return modelList;
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
    
    private void initStyles() {
        
    }
    

}
