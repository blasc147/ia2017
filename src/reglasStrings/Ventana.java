package reglasStrings;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

import com.sun.org.apache.xml.internal.utils.StringVector;
import exc.ConfException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class Ventana extends JFrame implements ActionListener, ChangeListener{

    static private final String newline = "\n";
	private JPanel contentPane;
	private static JTextField tfMinSup;
    private static JSpinner spinnerSup;
	private static JTextField tfMinConf;
    private static JSpinner spinnerConf;
	private JLabel lblLimiteItemsets;
	private static JTextField tfLimiteItemsets;
	private static JTextArea textArea1;
	private JScrollPane scrollpane1;
    private JButton openButton;
    private JLabel lblArchivoSubido;
    private JCheckBox checkboxLimiteItemsets;
    private JButton btnEjecutar;
    private JFileChooser fc;
    private static File file;
    private JButton btnVerReglas;
    private static String stringReglas;
    private JButton btnVerEstadisticas;
    private JButton ayuda;
    private static String stringVerEstadisticas;
    private JButton btnVerItemsetsFrecuentes;
    private static String stringVerItemsetsFrecuentes;
    private JButton btnVerInformeDeReglas;
    private JPanel panel_1;
    
	/**
	 * Con este m�todo se ejecuta la aplicaci�n.
	 */
	public static void main(String[] args) {
					Ventana ventana1 = new Ventana();
					ventana1.setVisible(true);
			        ventana1.setResizable(false);
	}
	
	/**
	 * Con este m�todo constructor se crea la 
	 * instancia del objeto Ventana, 
	 * y se le agregan cada uno de sus componentes visuales.
	 */	
	public Ventana() {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Association rules:Apriori");
		this.setBounds(50, 50, 830, 504);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		
		fc = new JFileChooser("G:\\eclipse-workspace\\ia2017-master");
		
        openButton = new JButton("Seleccionar archivo");
        openButton.setBounds(260, 132, 150, 23);
        openButton.addActionListener(this);
        contentPane.setLayout(null);
        
        lblArchivoSubido = new JLabel("");
        lblArchivoSubido.setBounds(445, 137, 169, 14);
        contentPane.add(lblArchivoSubido);
        contentPane.add(openButton);  
                
		JLabel lblAlgoritmoAPriori = new JLabel("");
		lblAlgoritmoAPriori.setIcon(new ImageIcon("C:\\Users\\Blas\\Downloads\\logo.png"));
		lblAlgoritmoAPriori.setBounds(125, 0, 573, 44);
		lblAlgoritmoAPriori.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 30));
		contentPane.add(lblAlgoritmoAPriori);
		
		JLabel lblMinSup = new JLabel("Soporte m\u00EDnimo (%):");
		lblMinSup.setBounds(120, 58, 127, 14);
		lblMinSup.setToolTipText("El valor de minimo soporte debe ser entre 1% - 100%");
        contentPane.add(lblMinSup);
		
		JLabel lblMinConf = new JLabel("Confianza m\u00EDnima (%):");
		lblMinConf.setBounds(396, 58, 164, 14);
		lblMinConf.setToolTipText("El valor de minima confianza debe ser entre 1% - 100%");
		contentPane.add(lblMinConf);
		
		JLabel lblDataset = new JLabel("Dataset (.dat):");
		lblDataset.setBounds(120, 137, 106, 14);
		contentPane.add(lblDataset);
		
		lblLimiteItemsets = new JLabel("Cantidad m\u00E1xima de items por regla:");
		lblLimiteItemsets.setBounds(396, 90, 213, 23);
        lblLimiteItemsets.setEnabled(false);
		contentPane.add(lblLimiteItemsets);
		
		tfLimiteItemsets = new JTextField();
		tfLimiteItemsets.setBounds(649, 91, 44, 20);
		tfLimiteItemsets.setColumns(10);
        tfLimiteItemsets.setEnabled(false);
		contentPane.add(tfLimiteItemsets);
		
		btnEjecutar = new JButton("Ejecutar");
		btnEjecutar.setBounds(381, 177, 89, 23);
        btnEjecutar.addActionListener(this);
		contentPane.add(btnEjecutar);
		
		btnVerEstadisticas = new JButton("Ver Estad\u00ECsticas");
		btnVerEstadisticas.setBounds(120, 177, 130, 23);
		btnVerEstadisticas.addActionListener(this);
		contentPane.add(btnVerEstadisticas);
		btnVerEstadisticas.setVisible(false);
		
		btnVerReglas = new JButton("Ver todas las Reglas");
		btnVerReglas.setBounds(260, 177, 160, 23);
		btnVerReglas.addActionListener(this);
		contentPane.add(btnVerReglas);
		btnVerReglas.setVisible(false);
		
		btnVerItemsetsFrecuentes = new JButton("Ver Itemsets Frecuentes");
		btnVerItemsetsFrecuentes.setBounds(620, 177, 180, 23);
		btnVerItemsetsFrecuentes.addActionListener(this);
		btnVerItemsetsFrecuentes.setVisible(false);
		contentPane.add(btnVerItemsetsFrecuentes);
		
		btnVerInformeDeReglas = new JButton("Ver Informe de Reglas");
		btnVerInformeDeReglas.setBounds(430, 177, 180, 23);
		btnVerInformeDeReglas.addActionListener(this);
		btnVerInformeDeReglas.setVisible(false);
		contentPane.add(btnVerInformeDeReglas);
		
		spinnerSup = new JSpinner();
		spinnerSup.setBounds(277, 55, 48, 20);
 		spinnerSup.setModel(new SpinnerNumberModel(30,1,100,1));
 		contentPane.add(spinnerSup);
 		
 		spinnerConf = new JSpinner();
 		spinnerConf.setBounds(570, 55, 44, 20);
 		spinnerConf.setModel(new SpinnerNumberModel(70,1,100,1));
 		contentPane.add(spinnerConf);
 		
 		checkboxLimiteItemsets = new JCheckBox("Limitar cant. de items por regla");
 		checkboxLimiteItemsets.setBounds(120, 90, 256, 23);
 		checkboxLimiteItemsets.addChangeListener(this);
 		contentPane.add(checkboxLimiteItemsets);
 		
 		panel_1 = new JPanel();
 		panel_1.setBounds(442, 132, 172, 23);
 		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
 		contentPane.add(panel_1);
 		
 		textArea1=new JTextArea();
 		textArea1.setEditable(false);
 		
 		scrollpane1=new JScrollPane(textArea1);
 		scrollpane1.setBounds(15, 211, 794, 243);
 		contentPane.add(scrollpane1);
 		
 		ayuda = new JButton("?");
 		ayuda.setToolTipText("Soporte en linea");
 		ayuda.addActionListener(this);
 		ayuda.setBounds(649, 54, 44, 23);
 		contentPane.add(ayuda);
 		
 		
	}
	
	
	/**Este metodo recibe el evento e 
	 * para saber que bot�n se apret�,
	 * y actuar en consecuencia
	 */
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
	        	Itemset.reglas.clear();
	        	stringReglas= "";
	        	stringVerEstadisticas= "";
	        	stringVerItemsetsFrecuentes= "";
                        if(file==null){
                            JOptionPane.showMessageDialog(null, "Especificar archivo dataset", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        int s =(int)spinnerSup.getValue();
                        int c = (int)spinnerConf.getValue();
                        if(s>c){
                             JOptionPane.showMessageDialog(null, "El soporte minimo no puede ser mayor que la confianza minima", "Error", JOptionPane.ERROR_MESSAGE);
                        }else{
	        	AprioriConStrings ap = new AprioriConStrings();
	        	ap.datosConfiguracion();
	        	ap.ejecutar();
	        	textArea1.setText(stringVerEstadisticas);
	    		btnEjecutar.setBounds(15, 177, 90, 23);
	    		btnVerEstadisticas.setVisible(true);
	    		btnVerReglas.setVisible(true);
	    		btnVerItemsetsFrecuentes.setVisible(true);
	    		btnVerInformeDeReglas.setVisible(true);
                        }
			} catch (ConfException excep) {
				JOptionPane.showMessageDialog(null, "El dataset no puede contener transacciones con items repetidos","Error", JOptionPane.ERROR_MESSAGE);} 
                catch (Exception ex) {  
                Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
            }  
		}
        
            if (e.getSource() == btnVerEstadisticas) {
	        try {
	        	textArea1.setText(stringVerEstadisticas);
			} catch (Exception excep) {
				excep.printStackTrace();
			}  
		}
        
        if (e.getSource() == btnVerReglas) {
            try{
                new VentanaReglas(this);           
            
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        
        if (e.getSource() == btnVerItemsetsFrecuentes) {
	        try {
	        	textArea1.setText(stringVerItemsetsFrecuentes);
			} catch (Exception excep) {
				excep.printStackTrace();
			}  
		}
        
        if (e.getSource() == btnVerInformeDeReglas) {
            try{
                new VentanaInforme();            
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        
        if (e.getSource() == ayuda) {
            try{
                Desktop.getDesktop().browse(new URL("https://www.ibm.com/support/knowledgecenter/es/SSEPGG_8.2.0/com.ibm.im.visual.doc/idmu0mst25.html").toURI());            
            }catch(MalformedURLException ex){
                ex.printStackTrace();
            } catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        
    }	
	
	
    
	/**Este m�todo detecta, con el evento e,
	 * cuando habilitamos y deshabilitamos
	 * el checkbox que nos permite poner
	 * un tama�o l�mite para los itemsets. 
	 */
	public void stateChanged(ChangeEvent e){
		if (checkboxLimiteItemsets.isSelected()==true) {
             lblLimiteItemsets.setEnabled(true);
             tfLimiteItemsets.setEnabled(true);
        }
        else {
        	lblLimiteItemsets.setEnabled(false);
             tfLimiteItemsets.setEnabled(false);
        }
     }

	public static JTextArea getTextArea1() {
		return textArea1;
	}
	public static void setTextArea1(JTextArea aTextArea1) {
		Ventana.textArea1 = aTextArea1;
	}
	
    public static JSpinner getSpinnerSup() {
 		return spinnerSup;
 	}
 	public static void setSpinnerSup(JSpinner spinnerSup) {
 		Ventana.spinnerSup = spinnerSup;
 	}
 	public static JSpinner getSpinnerConf() {
 		return spinnerConf;
 	}
 	public static void setSpinnerConf(JSpinner spinnerConf) {
 		Ventana.spinnerConf = spinnerConf;
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