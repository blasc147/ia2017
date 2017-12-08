/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reglasStrings;

import java.awt.Container;
import java.awt.FlowLayout;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**Este clase representa la ventana
 * emergente donde se muestran las reglas
 * de asociaci�n de manera tabulada *
 * @author Grupo9-IA2017
 */
class VentanaInforme extends JDialog {
   
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

            JTable table = new JTable( model );
            JTable table2 = new JTable( model2 );
            JTable table3 = new JTable( model3 );
            JTable table4 = new JTable( model4 );
            table.setAutoCreateRowSorter(true);
            Container c = getContentPane();
            c.setLayout( new FlowLayout() );
            add(new JScrollPane(table));
            this.pack();
            this.show();        
        } 
        
        
}