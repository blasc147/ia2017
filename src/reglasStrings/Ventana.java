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
    private JButton btnEjecutar;
    private JFileChooser fc;
    private static File file;

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
		setBounds(100, 100, 475, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		fc = new JFileChooser();
		
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
		
		lblLimiteItemsets = new JLabel("Tama\u00F1o m�ximo de itemsets");
		lblLimiteItemsets.setBounds(213, 55, 170, 23);
		contentPane.add(lblLimiteItemsets);
		
		JLabel lblDataset = new JLabel("Dataset");
		lblDataset.setBounds(10, 118, 46, 14);
		contentPane.add(lblDataset);
		
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
        scrollpane1.setBounds(10,200,430,175);
        getContentPane().add(scrollpane1);
		
		btnEjecutar = new JButton("Ejecutar");
		btnEjecutar.setBounds(172, 166, 89, 23);
	    btnEjecutar.addActionListener(this);
		contentPane.add(btnEjecutar);
	}
	
	
	public void actionPerformed(ActionEvent e) {
  
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(Ventana.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
               file = fc.getSelectedFile();               
               //This is where a real application would open the file.
               textArea1.append("Seleccionado el archivo " + file.getName() + "." + newline);
            } else {
            	textArea1.append("Cancelada la seleccion de archivo." + newline);
            }
            textArea1.setCaretPosition(textArea1.getDocument().getLength());
        }
        
        if (e.getSource() == btnEjecutar) {
	        try {
	        	AprioriConStrings ap = new AprioriConStrings();
	        	ap.datosConfiguracion();
	        	ap.ejecutar();
			} catch (Exception excep) {
				excep.printStackTrace();}  
		}
        
	}
	

	public static JTextArea getTextArea1() {
		return textArea1;
	}


	public static void setTextArea1(JTextArea aTextArea1) {
		textArea1 = aTextArea1;
	}


	public static JTextField getTfMinSup() {
		return tfMinSup;
	}


	public static void setTfMinSup(JTextField atfMinSup) {
		tfMinSup = atfMinSup;
	}


	public static JTextField getTfMinConf() {
		return tfMinConf;
	}


	public static void setTfMinConf(JTextField atfMinConf) {
		tfMinConf = atfMinConf;
	}


	public static JTextField getTfLimiteItemsets() {
		return tfLimiteItemsets;
	}


	public static void setTfLimiteItemsets(JTextField atfLimiteDeItemsets) {
		tfLimiteItemsets = atfLimiteDeItemsets;
	}

	public static File getFile() {
		return file;
	}

	public static void setFile(File aFile) {
		file = aFile;
	}

}