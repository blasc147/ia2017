package reglasStrings;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.*;

import com.sun.org.apache.xml.internal.utils.StringVector;

import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;


public class Ventana extends JFrame implements ActionListener{

    static private final String newline = "\n";
	private JPanel contentPane;
	private static JTextField tfMinSup;
	private static JTextField tfMinConf;
	private JLabel lblLimiteItemsets;
	private static JTextField tfLimiteItemsets;
	private static JTextArea textArea1;
	private JScrollPane scrollpane1;
    private JButton openButton;
    private JLabel lblArchivoSubido;
    private JButton btnEjecutar;
    private JFileChooser fc;
    private static File file;
    private JButton btnVerReglas;
    private static String stringReglas;
    private JButton btnVerEstadisticas;
    private static String stringVerEstadisticas;
    private JButton btnVerItemsetsFrecuentes;
    private static String stringVerItemsetsFrecuentes;

    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
					Ventana ventana1 = new Ventana();
					ventana1.setVisible(true);
	}
	/**
	 * Create the frame.
	 */
	public Ventana() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Algoritmo a priori");
		setBounds(50, 50, 600, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		fc = new JFileChooser("G:\\eclipse-workspace\\ia2017-master");
		
        openButton = new JButton("Seleccionar archivo");
        openButton.setBounds(120, 113, 150, 23);
        openButton.addActionListener(this);
        contentPane.add(openButton);   
                
		JLabel lblAlgoritmoAPriori = new JLabel("Algoritmo a Priori");
		lblAlgoritmoAPriori.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 30));
		lblAlgoritmoAPriori.setBounds(98, 11, 264, 33);
		contentPane.add(lblAlgoritmoAPriori);
		
		JLabel lblMinSup = new JLabel("Soporte minimo");
		lblMinSup.setBounds(10, 58, 104, 14);
		contentPane.add(lblMinSup);
		
		JLabel lblMinConf = new JLabel("Confianza minima");
		lblMinConf.setBounds(10, 88, 114, 14);
		contentPane.add(lblMinConf);
		
		lblLimiteItemsets = new JLabel("Tama\u00F1o màximo de itemsets");
		lblLimiteItemsets.setBounds(213, 55, 170, 23);
		contentPane.add(lblLimiteItemsets);
		
		JLabel lblDataset = new JLabel("Dataset");
		lblDataset.setBounds(10, 118, 46, 14);
		contentPane.add(lblDataset);
		
		lblArchivoSubido = new JLabel("");
		lblArchivoSubido.setBounds(280, 117, 169, 14);
		contentPane.add(lblArchivoSubido);
		
		tfMinSup = new JTextField();
		tfMinSup.setBounds(120, 55, 64, 20);
		contentPane.add(tfMinSup);
		tfMinSup.setColumns(10);
		
		tfMinConf = new JTextField();
		tfMinConf.setBounds(120, 83, 64, 20);
		contentPane.add(tfMinConf);
		tfMinConf.setColumns(10);
		
		tfLimiteItemsets = new JTextField();
		tfLimiteItemsets.setColumns(10);
		tfLimiteItemsets.setBounds(385, 55, 64, 20);
		contentPane.add(tfLimiteItemsets);
		
		textArea1=new JTextArea();
		textArea1.setEditable(false);
        scrollpane1=new JScrollPane(textArea1);    
        scrollpane1.setBounds(10,200,565,200);
        getContentPane().add(scrollpane1);
		
		btnEjecutar = new JButton("Ejecutar");
		btnEjecutar.setBounds(216, 166, 89, 23);
	    btnEjecutar.addActionListener(this);
		contentPane.add(btnEjecutar);
		
		btnVerEstadisticas = new JButton("Ver Estad\u00ECsticas");
		btnVerEstadisticas.addActionListener(this);
		contentPane.add(btnVerEstadisticas);
		btnVerEstadisticas.setVisible(false);
		
		btnVerReglas = new JButton("Ver Reglas");
		btnVerReglas.addActionListener(this);
		contentPane.add(btnVerReglas);
		btnVerReglas.setVisible(false);
		
		btnVerItemsetsFrecuentes = new JButton("Ver Itemsets Frecuentes");
		btnVerItemsetsFrecuentes.addActionListener(this);
		contentPane.add(btnVerItemsetsFrecuentes);
		btnVerItemsetsFrecuentes.setVisible(false);
	}
	
	
	public void actionPerformed(ActionEvent e) {
  
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(Ventana.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
               file = fc.getSelectedFile();               
               //This is where a real application would open the file.
               textArea1.append("Seleccionado el archivo " + file.getName() + "." + newline);
               lblArchivoSubido.setText(file.getName());
            } else {
            	textArea1.append("Cancelada la seleccion de archivo." + newline);
            }
            textArea1.setCaretPosition(textArea1.getDocument().getLength());
        }
        
        if (e.getSource() == btnEjecutar) {
	        try {
	        	stringReglas= "";
	        	stringVerEstadisticas= "";
	        	stringVerItemsetsFrecuentes= "";
	        	AprioriConStrings ap = new AprioriConStrings();
	        	ap.datosConfiguracion();
	        	ap.ejecutar();
	        	textArea1.setText(stringVerEstadisticas);
	    		btnEjecutar.setBounds(10, 166, 90, 23);
	        	btnVerEstadisticas.setBounds(120, 166, 130, 23);
	    		btnVerEstadisticas.setVisible(true);
	    		btnVerReglas.setBounds(270, 166, 100, 23);
	    		btnVerReglas.setVisible(true);
	    		btnVerItemsetsFrecuentes.setBounds(390, 166, 180, 23);
	    		btnVerItemsetsFrecuentes.setVisible(true);
			} catch (Exception excep) {
				excep.printStackTrace();}  
		}
        
        if (e.getSource() == btnVerEstadisticas) {
	        try {
	        	textArea1.setText(stringVerEstadisticas);
			} catch (Exception excep) {
				excep.printStackTrace();
			}  
		}
        
        if (e.getSource() == btnVerReglas) {
	        try {
	        	textArea1.setText(stringReglas);
			} catch (Exception excep) {
				excep.printStackTrace();
			}  
		}
        
        if (e.getSource() == btnVerItemsetsFrecuentes) {
	        try {
	        	textArea1.setText(stringVerItemsetsFrecuentes);
			} catch (Exception excep) {
				excep.printStackTrace();
			}  
		}
        
	}
	

	public static JTextArea getTextArea1() {
		return textArea1;
	}
	public static void setTextArea1(JTextArea aTextArea1) {
		Ventana.textArea1 = aTextArea1;
	}
	public static JTextField getTfMinSup() {
		return tfMinSup;
	}
	public static void setTfMinSup(JTextField atfMinSup) {
		Ventana.tfMinSup = atfMinSup;
	}
	public static JTextField getTfMinConf() {
		return tfMinConf;
	}
	public static void setTfMinConf(JTextField atfMinConf) {
		Ventana.tfMinConf = atfMinConf;
	}
	public static JTextField getTfLimiteItemsets() {
		return tfLimiteItemsets;
	}
	public static void setTfLimiteItemsets(JTextField atfLimiteDeItemsets) {
		Ventana.tfLimiteItemsets = atfLimiteDeItemsets;
	}
	public static File getFile() {
		return file;
	}
	public static void setFile(File aFile) {
		Ventana.file = aFile;
	}	
	public static String getStringReglas() {
		return stringReglas;
	}	
	public static void setStringReglas(String stringReglas) {
		Ventana.stringReglas = stringReglas;
	}
	public static String getStringVerEstadisticas() {
		return stringVerEstadisticas;
	}
	public static void setStringVerEstadisticas(String stringVerEstadisticas) {
		Ventana.stringVerEstadisticas = stringVerEstadisticas;
	}
	public static String getStringVerItemsetsFrecuentes() {
		return stringVerItemsetsFrecuentes;
	}
	public static void setStringVerItemsetsFrecuentes(String stringVerItemsetsFrecuentes) {
		Ventana.stringVerItemsetsFrecuentes = stringVerItemsetsFrecuentes;
	}	
}