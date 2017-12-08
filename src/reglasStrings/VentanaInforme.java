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
 * de asociación de manera tabulada *
 * @author Grupo9-IA2017
 */
class VentanaInforme extends JDialog {
   
	/**
	 * Este método constructor nos dice como
	 * será la creación de la instancia de la 
	 * ventana emergente y de su tabla, 
	 * que es donde estáran las reglas generadas.
	 * @param frame
	 */
	public VentanaInforme( JFrame frame ) {
		super(frame, "Reglas generadas", true);        
        
		Vector cero = new Vector();
        cero.add( "Nro" );
        cero.add( "Premisa" );
        cero.add( "Consecuente" );
        cero.add( "Confianza (%)" );
        
        Vector row = new Vector();
        for (int i = 0; i < Itemset.reglas.size(); i++) {
            Vector first = new Vector();
            first.add(i+1);
            first.add( Itemset.reglas.get(i).premisa );
            first.add( Itemset.reglas.get(i).conclusion );
            first.add( Itemset.reglas.get(i).confianza );
            row.add( first );
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
        
        JTable table = new JTable( model );
        table.setAutoCreateRowSorter(true);
        Container c = getContentPane();
        c.setLayout( new FlowLayout() );
        add(new JScrollPane(table));
        this.pack();
        this.show();        
    } 
}