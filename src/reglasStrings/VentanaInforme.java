package reglasStrings;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.border.BevelBorder;

/**Esta clase representa la ventana
 * emergente donde se muestran el informe
 * para el usuario de las reglas generadas *
 * @author Grupo9-IA2017
 */

public class VentanaInforme extends JDialog implements ChangeListener{

	private final JPanel contentPanel = new JPanel();
	private JRadioButton rdbtnMuyConfiable;
	private JRadioButton rdbtnConfiable;
	private JRadioButton rdbtnPocoConfiable;
	private JRadioButton rdbtnNoConfiable;
	private ButtonGroup bg;
	private JLabel lblNivelesDeConfiabilidad;
	private JLabel lblNewLabel;
	private JTable table1;
	private JTable table2;
	private JTable table3;
	private JTable table4;
	private JScrollPane scrollPane;

	public VentanaInforme() {
		
            setBounds(200, 150, 640, 480);
            getContentPane().setLayout(new BorderLayout());
            contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            getContentPane().add(contentPanel, BorderLayout.CENTER);

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
                if(Itemset.reglas.get(i).confianza>90){
                    row.add( first );
                }else if(Itemset.reglas.get(i).confianza>70){
                    row2.add( first );
                }else if(Itemset.reglas.get(i).confianza>50){
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

        table1 = new JTable( model );
        table2 = new JTable( model2 );
        table3 = new JTable( model3 );
        table4 = new JTable( model4 );
        table1.setAutoCreateRowSorter(true);
        table2.setAutoCreateRowSorter(true);
        table3.setAutoCreateRowSorter(true);
        table4.setAutoCreateRowSorter(true);
		
        scrollPane = new JScrollPane(table1);
        scrollPane.setBounds(10,11,343,419);
        contentPanel.add(scrollPane); 
		
	bg=new ButtonGroup();
		
	rdbtnMuyConfiable = new JRadioButton("Muy Confiable");
	rdbtnMuyConfiable.setBounds(448, 33, 109, 23);
	rdbtnMuyConfiable.addChangeListener(this);
	contentPanel.setLayout(null);
	contentPanel.add(rdbtnMuyConfiable);
        bg.add(rdbtnMuyConfiable);
		
	rdbtnConfiable = new JRadioButton("Confiable");
	rdbtnConfiable.setBounds(448, 59, 109, 23);
	rdbtnConfiable.addChangeListener(this);
	contentPanel.add(rdbtnConfiable);
        bg.add(rdbtnConfiable);
		
	rdbtnPocoConfiable = new JRadioButton("Poco Confiable");
	rdbtnPocoConfiable.setBounds(447, 85, 121, 23);
	rdbtnPocoConfiable.addChangeListener(this);
	contentPanel.add(rdbtnPocoConfiable);
        bg.add(rdbtnPocoConfiable);
		
	rdbtnNoConfiable = new JRadioButton("No Confiable");
	rdbtnNoConfiable.setBounds(448, 111, 109, 23);
	rdbtnNoConfiable.addChangeListener(this);
	contentPanel.add(rdbtnNoConfiable);
        bg.add(rdbtnNoConfiable);
        
        lblNivelesDeConfiabilidad = new JLabel("Niveles de confiabilidad");
        lblNivelesDeConfiabilidad.setBounds(438, 11, 141, 15);
        lblNivelesDeConfiabilidad.setFont(new Font("Tahoma", Font.BOLD, 12));
        contentPanel.add(lblNivelesDeConfiabilidad);
        
        lblNewLabel = new JLabel("");
        lblNewLabel.setBounds(366, 141, 248, 115);
        contentPanel.add(lblNewLabel);
        
        JPanel panel = new JPanel();
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel.setBounds(363, 136, 251, 222);
        contentPanel.add(panel);
        
        this.show();
	}

	@Override
	public void stateChanged(ChangeEvent e) {        
            if (rdbtnMuyConfiable.isSelected()) {
        	scrollPane.setViewportView(table1);
		lblNewLabel.setText("<html>Una regla muy confiable asegura casi en su totalidad (mas de un 90% de probabilidad) que si el cliente adquiere los items del lado de la premisa de la regla, tambien va comprar los del lado del consecuente.</html>");			
            }
            if (rdbtnConfiable.isSelected()) {
        	scrollPane.setViewportView(table2);
		lblNewLabel.setText("<html>Una regla confiable indica que si el cliente adquiere los items del lado de la premisa de la regla, es muy probable (mas del 70%, menos del 90%) que tambien adquiera los items del lado del consecuente.</html>");
            }
            if (rdbtnPocoConfiable.isSelected()) {
        	scrollPane.setViewportView(table3);
		lblNewLabel.setText("<html>Una regla poco confiable tiene una relacion entre los items de la premisa y los del consecuente bastante baja. Solamente entre el 50% y el 70% de las veces que los clientes compren los items de la premisa, tambien van adquirir los del consecuente</html>");
            }        
            if (rdbtnNoConfiable.isSelected()) {
        	scrollPane.setViewportView(table4);
		lblNewLabel.setText("<html>Una regla no confiable presenta una relacion muy debil (menor al 50%) de probabilidad de que un cliente que adquiera los items de la premisa, tambien adquiera los del consecuente</html>");
            }		
        }
}
