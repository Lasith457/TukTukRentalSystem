package ui;

import javax.swing.*;
import java.awt.*;
import dao.CustomerDAO;
import model.Customer;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CustomerForm extends JFrame {

    JTextField txtName, txtNic, txtPhone, txtLicense;
    JButton btnAdd, btnUpdate, btnDelete;
    JTable table;
    JScrollPane scrollPane;

    public CustomerForm() {

        setTitle("Customer Management");
        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // Row 1
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        row1.add(new JLabel("Name:"));
        txtName = new JTextField(15);
        row1.add(txtName);

        row1.add(new JLabel("NIC:"));
        txtNic = new JTextField(15);
        row1.add(txtNic);

        // Row 2
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        row2.add(new JLabel("Phone:"));
        txtPhone = new JTextField(15);
        row2.add(txtPhone);

        row2.add(new JLabel("License No:"));
        txtLicense = new JTextField(15);
        row2.add(txtLicense);

        // Row 3
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");

        row3.add(btnAdd);
        row3.add(btnUpdate);
        row3.add(btnDelete);

        btnAdd.addActionListener(e -> addCustomer());
        btnUpdate.addActionListener(e -> updateCustomer());
        btnDelete.addActionListener(e -> deleteCustomer());

        // Table
        String[] columns = {"ID","Name","NIC","Phone","License"};

        DefaultTableModel model = new DefaultTableModel(columns, 0){
            public boolean isCellEditable(int r,int c){
                return false;
            }
        };

        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);

        scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        topPanel.setBorder(BorderFactory.createEmptyBorder(20,20,10,20)); // 🔥 more top padding

        topPanel.add(row1);
        topPanel.add(Box.createVerticalStrut(15)); // increased spacing

        topPanel.add(row2);
        topPanel.add(Box.createVerticalStrut(15));

        topPanel.add(row3);
        topPanel.add(Box.createVerticalStrut(25)); // more space before table

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        loadCustomers();

        table.getSelectionModel().addListSelectionListener(e -> fillFormFromTable());

        getContentPane().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                clearFields();
            }
        });


    }

    public void loadCustomers() {

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        model.fireTableDataChanged();
        table.revalidate();
        table.repaint();

        CustomerDAO dao = new CustomerDAO();
        List<Customer> list = dao.getAllCustomers();

        for (Customer c : list) {
            model.addRow(new Object[]{
                    c.getCustomerId(),
                    c.getName(),
                    c.getNic(),
                    c.getPhone(),
                    c.getLicenseNo()
            });
        }
    }

    public void addCustomer() {

        try {

            String name = txtName.getText().trim();
            String nic = txtNic.getText().trim();
            String phone = txtPhone.getText().trim();
            String license = txtLicense.getText().trim();

            if(name.isEmpty() || nic.isEmpty() || phone.isEmpty() || license.isEmpty()){
                JOptionPane.showMessageDialog(this,"All fields are required");
                return;
            }

            if(!phone.matches("\\d+")){
                JOptionPane.showMessageDialog(this,"Phone must be numeric");
                return;
            }

            Customer c = new Customer();
            c.setName(name);
            c.setNic(nic);
            c.setPhone(phone);
            c.setLicenseNo(license);

            new CustomerDAO().addCustomer(c);

            loadCustomers();
            clearFields();

            JOptionPane.showMessageDialog(this,"Customer added successfully");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error adding customer");
        }
    }

    public void fillFormFromTable() {

        int row = table.getSelectedRow();
        if(row == -1) return;

        txtName.setText(table.getValueAt(row,1).toString());
        txtNic.setText(table.getValueAt(row,2).toString());
        txtPhone.setText(table.getValueAt(row,3).toString());
        txtLicense.setText(table.getValueAt(row,4).toString());
    }

    public void updateCustomer() {

        try {

            int row = table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(this,"Select a customer first");
                return;
            }

            int id = Integer.parseInt(table.getValueAt(row,0).toString());

            Customer c = new Customer();
            c.setCustomerId(id);
            c.setName(txtName.getText());
            c.setNic(txtNic.getText());
            c.setPhone(txtPhone.getText());
            c.setLicenseNo(txtLicense.getText());

            new CustomerDAO().updateCustomer(c);

            loadCustomers();
            clearFields();

            JOptionPane.showMessageDialog(this,"Customer updated");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error updating");
        }
    }

    public void deleteCustomer() {

        try {

            int row = table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(this,"Select a customer first");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,"Delete this customer?");
            if(confirm != JOptionPane.YES_OPTION) return;

            int id = Integer.parseInt(table.getValueAt(row,0).toString());

            new CustomerDAO().deleteCustomer(id);

            loadCustomers();
            clearFields();

            JOptionPane.showMessageDialog(this,"Customer deleted");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error deleting");
        }
    }

    public void clearFields() {
        txtName.setText("");
        txtNic.setText("");
        txtPhone.setText("");
        txtLicense.setText("");
        table.clearSelection();
    }


}