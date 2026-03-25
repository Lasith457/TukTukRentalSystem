package ui;

import dao.RentalDAO;
import model.Rental;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import dao.PaymentDAO;
import model.Payment;

public class PaymentForm extends JFrame {

    JComboBox<String> cmbRental;
    JTextField txtAmount;
    JComboBox<String> cmbMethod;
    JDateChooser dateChooser;

    JButton btnSave, btnDelete;

    JTable table;
    JScrollPane scrollPane;

    java.util.Map<String, Double> rentalAmounts = new java.util.HashMap<>();

    public PaymentForm(){

        setTitle("Payment Management");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));

        // Row 1
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT,15,10));
        row1.add(new JLabel("Rental:"));
        cmbRental = new JComboBox<>();
        cmbRental.setPreferredSize(new Dimension(250,25));
        row1.add(cmbRental);

        // Row 2
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT,15,10));
        row2.add(new JLabel("Amount:"));
        txtAmount = new JTextField(10);
        txtAmount.setEditable(false);
        row2.add(txtAmount);

        row2.add(new JLabel("Method:"));
        cmbMethod = new JComboBox<>(new String[]{"Cash", "Card"});
        row2.add(cmbMethod);

        // Row 3
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT,15,10));
        row3.add(new JLabel("Date:"));
        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(120,25));
        row3.add(dateChooser);

        btnSave = new JButton("Save Payment");
        btnDelete = new JButton("Delete Payment");

        row3.add(btnSave);
        row3.add(btnDelete);

        topPanel.add(row1);
        topPanel.add(Box.createVerticalStrut(15));

        topPanel.add(row2);
        topPanel.add(Box.createVerticalStrut(15));

        topPanel.add(row3);
        topPanel.add(Box.createVerticalStrut(25));

        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Rental ID", "Amount", "Method", "Date"};

        DefaultTableModel model = new DefaultTableModel(columns, 0){
            public boolean isCellEditable(int r,int c){
                return false;
            }
        };

        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);

        table.getSelectionModel().addListSelectionListener(e -> fillFormFromTable());

        scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));

        add(scrollPane, BorderLayout.CENTER);


        loadRentals();
        loadPayments();

        cmbRental.addActionListener(e -> {
            String selected = (String) cmbRental.getSelectedItem();
            if(selected != null){
                txtAmount.setText(String.valueOf(rentalAmounts.get(selected)));
            }
        });

        btnSave.addActionListener(e -> savePayment());
        btnDelete.addActionListener(e -> deletePayment());
    }

    public void loadRentals(){
        RentalDAO dao = new RentalDAO();
        List<Rental> list = dao.getAllRentals();

        cmbRental.removeAllItems();
        rentalAmounts.clear();

        for(Rental r : list){
            String item = r.getRentalId() + " - " + r.getCustomerName();
            cmbRental.addItem(item);
            rentalAmounts.put(item, r.getTotalAmount());
        }
    }

    public void savePayment(){
        try{
            String rentalItem = cmbRental.getSelectedItem().toString();
            int rentalId = Integer.parseInt(rentalItem.split(" - ")[0]);

            double amount = Double.parseDouble(txtAmount.getText());
            String method = cmbMethod.getSelectedItem().toString();

            java.sql.Date sqlDate = new java.sql.Date(dateChooser.getDate().getTime());

            Payment p = new Payment();
            p.setRentalId(rentalId);
            p.setAmount(amount);
            p.setPaymentMethod(method);
            p.setPaymentDate(sqlDate);

            new PaymentDAO().addPayment(p);

            loadPayments();
            clearFields();

            JOptionPane.showMessageDialog(this,"Payment saved");

        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Error saving payment");
        }
    }

    public void loadPayments(){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        List<Payment> list = new PaymentDAO().getAllPayments();

        for(Payment p : list){
            model.addRow(new Object[]{
                    p.getPaymentId(),
                    p.getRentalId(),
                    p.getAmount(),
                    p.getPaymentMethod(),
                    p.getPaymentDate()
            });
        }
    }

    public void deletePayment(){
        try{
            int row = table.getSelectedRow();

            if(row < 0){
                JOptionPane.showMessageDialog(this,"Select a payment first");
                return;
            }

            int id = Integer.parseInt(table.getValueAt(row,0).toString());

            new PaymentDAO().deletePayment(id);

            loadPayments();
            clearFields();

            JOptionPane.showMessageDialog(this,"Deleted");

        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Error deleting");
        }
    }

    public void fillFormFromTable(){
        int row = table.getSelectedRow();
        if(row == -1) return;

        try{
            int rentalId = Integer.parseInt(table.getValueAt(row,1).toString());

            for(int i=0;i<cmbRental.getItemCount();i++){
                if(cmbRental.getItemAt(i).startsWith(rentalId + " -")){
                    cmbRental.setSelectedIndex(i);
                    break;
                }
            }

            txtAmount.setText(table.getValueAt(row,2).toString());
            cmbMethod.setSelectedItem(table.getValueAt(row,3).toString());
            dateChooser.setDate((java.util.Date) table.getValueAt(row,4));

        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Error loading data");
        }
    }

    public void clearFields(){
        cmbRental.setSelectedIndex(-1);
        txtAmount.setText("");
        cmbMethod.setSelectedIndex(0);
        dateChooser.setDate(null);
    }
}