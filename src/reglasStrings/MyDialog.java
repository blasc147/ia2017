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

/**
 *
 * @author Blas
 */
class MyDialog extends JDialog {
    public MyDialog( JFrame frame ) {
        super( frame, "Reglas generadas", true );
        Vector cero = new Vector();
        cero.add( "Name" );
        cero.add( "Roll No" );
        cero.add( "Grade" );
        Vector first = new Vector();
        first.add( "Bhupendra" );
        first.add( "100" );
        first.add( "A+" );
        Vector row = new Vector();
        row.add( first );
        row.add(cero);
        JTable table = new JTable( row, cero );
        Container c = getContentPane();
        c.setLayout( new FlowLayout() );
        //c.add( table );
        add(new JScrollPane(table));
        this.pack();
        this.show();
    }
}