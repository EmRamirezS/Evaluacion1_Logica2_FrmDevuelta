import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FrmDevuelta extends JFrame {
    private JTextField txtMonto;
    private JTextField txtPago;
    private JTable tblDevuelta;
    private JButton btnCalcular;
    private JTextField[] txtExistencia;
    
    private int[] denominaciones = {20000, 10000, 5000, 2000, 1000, 500, 200, 100, 50};
    private String[] tipoDenominacion = {"Billete", "Billete", "Billete", "Billete", "Billete", "Moneda", "Moneda", "Moneda", "Moneda"};

    public FrmDevuelta() {
        setTitle("Calculadora de Devuelta");
        setSize(500, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblMonto = new JLabel("Monto a pagar:");
        lblMonto.setBounds(20, 20, 100, 25);
        add(lblMonto);
        
        txtMonto = new JTextField();
        txtMonto.setBounds(130, 20, 100, 25);
        add(txtMonto);
        
        JLabel lblPago = new JLabel("Pago recibido:");
        lblPago.setBounds(20, 50, 100, 25);
        add(lblPago);
        
        txtPago = new JTextField();
        txtPago.setBounds(130, 50, 100, 25);
        add(txtPago);
        
        btnCalcular = new JButton("Calcular");
        btnCalcular.setBounds(250, 35, 100, 25);
        add(btnCalcular);
        
        JLabel lblExistencia = new JLabel("Existencia de denominaciones:");
        lblExistencia.setBounds(20, 80, 200, 25);
        add(lblExistencia);
        
        txtExistencia = new JTextField[denominaciones.length];
        for (int i = 0; i < denominaciones.length; i++) {
            JLabel lblDenom = new JLabel("$ " + denominaciones[i] + " (");
            lblDenom.setBounds(20, 110 + (i * 25), 80, 25);
            add(lblDenom);
            
            txtExistencia[i] = new JTextField("0");
            txtExistencia[i].setBounds(100, 110 + (i * 25), 50, 25);
            add(txtExistencia[i]);
            
            JLabel lblTipo = new JLabel(tipoDenominacion[i] + ")");
            lblTipo.setBounds(160, 110 + (i * 25), 80, 25);
            add(lblTipo);
        }
        
        String[] columnas = {"Denominación", "Tipo", "Cantidad"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        tblDevuelta = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tblDevuelta);
        scrollPane.setBounds(20, 110 + (denominaciones.length * 25), 450, 150);
        add(scrollPane);
        
        btnCalcular.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calcularDevuelta();
            }
        });
    }

    private void calcularDevuelta() {
        int monto = Integer.parseInt(txtMonto.getText());
        int pago = Integer.parseInt(txtPago.getText());
        int devuelta = pago - monto;
        
        int[] existencia = new int[denominaciones.length];
        for (int i = 0; i < denominaciones.length; i++) {
            existencia[i] = Integer.parseInt(txtExistencia[i].getText());
        }
        
        DefaultTableModel modelo = (DefaultTableModel) tblDevuelta.getModel();
        modelo.setRowCount(0);
        
        for (int i = 0; i < denominaciones.length; i++) {
            int cantidad = Math.min(devuelta / denominaciones[i], existencia[i]);
            if (cantidad > 0) {
                modelo.addRow(new Object[]{"$ " + denominaciones[i], tipoDenominacion[i], cantidad});
                devuelta -= cantidad * denominaciones[i];
            }
        }
    }
}
