/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reglasStrings;

import java.awt.Container;
import java.awt.FlowLayout;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**Este clase representa la ventana
 * emergente donde se muestran las reglas
 * de asociaci�n de manera tabulada *
 * @author Grupo9-IA2017
 */

class VentanaInforme extends JDialog implements ChangeListener{
   
	private Container c;
	private JRadioButton rdbtnMuyConfiable;
	private JRadioButton rdbtnConfiable;
	private JRadioButton rdbtnPocoConfiable;
	private JRadioButton rdbtnNoConfiable;
	private ButtonGroup bg;
	private JLabel lblNivelesDeConfiabilidad;
	private JLabel lblNewLabel;
	private JTable table;
	private JTable table2;
	private JTable table3;
	private JTable table4;
	private JScrollPane scrollPane;

	
	/**
	 * Este m�todo constructor nos dice como
	 * ser� la creaci�n de la instancia de la 
	 * ventana emergente y de su tabla, 
	 * que es donde est�ran las reglas generadas.
	 * @param frame
	 */
	public VentanaInforme( JFrame frame ) {
            super(frame, "Reglas generadas", true);        
        
            Vector cero = new Vector();
            cero.add( "Premisa" );
            cero.add( "Consecuente" );
            cero.add( "Confianza (%)" );

            Vector row = new Vector();
            Vector row2 = new Vector();
            Vector row3 = new Vector();
            Vector row4 = new Vector();
            
            for (int i = 0; i < Itemset.reglas.size(); i++) {
                Vector first = new Vector();
                first.add( Itemset.reglas.get(i).premisa );
                first.add( Itemset.reglas.get(i).conclusion );
                first.add( Itemset.reglas.get(i).confianza );
                if(Itemset.reglas.get(i).confianza>=90){
                    row.add( first );
                }else if(Itemset.reglas.get(i).confianza>=70){
                    row2.add( first );
                }else if(Itemset.reglas.get(i).confianza>=50){
                    row3.add( first );
                }else{
                    row4.add( first );
                }
               
                
            }

            TableModel model = new DefaultTableModel(row, cero) {
                public Class getColumnClass(int column) {
                  Class returnValue;
                  if ((column >= 0) && (column < getColumnCount())) {
                    returnValue = getValueAt(0, column).getClass();
                  } else {
                    returnValue = Object.class;
                  }
                  return returnValue;
                }
            };
            TableModel model2 = new DefaultTableModel(row2, cero) {
                public Class getColumnClass(int column) {
                  Class returnValue;
                  if ((column >= 0) && (column < getColumnCount())) {
                    returnValue = getValueAt(0, column).getClass();
                  } else {
                    returnValue = Object.class;
                  }
                  return returnValue;
                }
            };
            TableModel model3 = new DefaultTableModel(row3, cero) {
                public Class getColumnClass(int column) {
                  Class returnValue;
                  if ((column >= 0) && (column < getColumnCount())) {
                    returnValue = getValueAt(0, column).getClass();
                  } else {
                    returnValue = Object.class;
                  }
                  return returnValue;
                }
            };
            TableModel model4 = new DefaultTableModel(row4, cero) {
                public Class getColumnClass(int column) {
                  Class returnValue;
                  if ((column >= 0) && (column < getColumnCount())) {
                    returnValue = getValueAt(0, column).getClass();
                  } else {
                    returnValue = Object.class;
                  }
                  return returnValue;
                }
            };

            table = new JTable( model );
            table2 = new JTable( model2 );
            table3 = new JTable( model3 );
            table4 = new JTable( model4 );
            table.setAutoCreateRowSorter(true);
            table2.setAutoCreateRowSorter(true);
            table3.setAutoCreateRowSorter(true);
            table4.setAutoCreateRowSorter(true);

            
            c = getContentPane();
            c.setLayout( new FlowLayout() );

            scrollPane = new JScrollPane(table);
            add(scrollPane); 
            
        	bg=new ButtonGroup();
            
            rdbtnMuyConfiable = new JRadioButton("Muy Confiable");
            //rdbtnMuyConfiable.setBounds(449, 26, 109, 23);
            rdbtnMuyConfiable.addChangeListener(this);
            getContentPane().add(rdbtnMuyConfiable);
            bg.add(rdbtnMuyConfiable);
            
            rdbtnConfiable = new JRadioButton("Confiable");
            //rdbtnConfiable.setBounds(449, 52, 109, 23);
            rdbtnConfiable.addChangeListener(this);
            getContentPane().add(rdbtnConfiable);
            bg.add(rdbtnConfiable);

            
            rdbtnPocoConfiable = new JRadioButton("Poco confiable");
//          rdbtnPocoConfiable.setBounds(449, 78, 109, 23);
            rdbtnPocoConfiable.addChangeListener(this);
            getContentPane().add(rdbtnPocoConfiable);
            bg.add(rdbtnPocoConfiable);

            
            rdbtnNoConfiable = new JRadioButton("No confiable");
//          rdbtnNoConfiable.setBounds(449, 104, 109, 23);
            rdbtnNoConfiable.addChangeListener(this);
            getContentPane().add(rdbtnNoConfiable);
            bg.add(rdbtnNoConfiable);
            
            lblNivelesDeConfiabilidad = new JLabel("Niveles de confiabilidad");
            lblNivelesDeConfiabilidad.setFont(new Font("Tahoma", Font.BOLD, 12));
            lblNivelesDeConfiabilidad.setBounds(453, 5, 146, 14);
            getContentPane().add(lblNivelesDeConfiabilidad);
            
            lblNewLabel = new JLabel("Aca va el texto");
            lblNewLabel.setBounds(453, 134, 172, 30);
            getContentPane().add(lblNewLabel);
            
            this.pack();
            this.show();        
        }

	@Override
	public void stateChanged(ChangeEvent e) {
		
        if (rdbtnMuyConfiable.isSelected()) {
        	scrollPane.setViewportView(table);
        }
        if (rdbtnConfiable.isSelected()) {
        	scrollPane.setViewportView(table2);

        }
        if (rdbtnPocoConfiable.isSelected()) {
        	scrollPane.setViewportView(table3);

        } 
        
        if (rdbtnNoConfiable.isSelected()) {
        	scrollPane.setViewportView(table4);

        }		
	} 
}