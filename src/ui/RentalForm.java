package ui;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import dao.CustomerDAO;
import dao.TukTukDAO;
import model.Customer;
import model.TukTuk;
import java.util.List;
import dao.RentalDAO;
import model.Rental;

public class RentalForm extends JFrame {

    JComboBox<String> cmbCustomer;
    JComboBox<String> cmbTukTuk;

    JDateChooser startDateChooser;
    JDateChooser endDateChooser;
    JTextField txtTotal;

    JButton btnCalculate, btnSave, btnUpdateRental, btnDeleteRental;

    JTable table;
    JScrollPane scrollPane;

    java.util.Map<String, Double> tukTukPrices = new java.util.HashMap<>();

    public RentalForm(){

        setTitle("Rental Management");
        setSize(650,450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));

        // Row 1
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        cmbCustomer = new JComboBox<>();
        cmbCustomer.setPreferredSize(new Dimension(200,25));
        row1.add(new JLabel("Customer:"));
        row1.add(cmbCustomer);

        cmbTukTuk = new JComboBox<>();
        cmbTukTuk.setPreferredSize(new Dimension(200,25));
        row1.add(new JLabel("TukTuk:"));
        row1.add(cmbTukTuk);

        // Row 2
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        startDateChooser = new JDateChooser();
        endDateChooser = new JDateChooser();

        row2.add(new JLabel("Start Date:"));
        row2.add(startDateChooser);
        row2.add(new JLabel("End Date:"));
        row2.add(endDateChooser);

        // Row 3
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));

        txtTotal = new JTextField(10);
        txtTotal.setEditable(false);

        btnCalculate = new JButton("Calculate");
        btnSave = new JButton("Save Rental");
        btnUpdateRental = new JButton("Update Rental");
        btnDeleteRental = new JButton("Delete Rental");

        row3.add(new JLabel("Total:"));
        row3.add(txtTotal);
        row3.add(btnCalculate);
        row3.add(btnSave);
        row3.add(btnUpdateRental);
        row3.add(btnDeleteRental);

        // Table
        String[] columns = {"ID","Customer","TukTuk","Start","End","Total"};

        DefaultTableModel model = new DefaultTableModel(columns,0){
            public boolean isCellEditable(int r,int c){ return false; }
        };

        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(150);  // Customer
        table.getColumnModel().getColumn(2).setPreferredWidth(150);  // TukTuk
        table.getColumnModel().getColumn(3).setPreferredWidth(120);  // Start
        table.getColumnModel().getColumn(4).setPreferredWidth(120);  // End
        table.getColumnModel().getColumn(5).setPreferredWidth(100);  // Total
        scrollPane = new JScrollPane(table);

        scrollPane.setPreferredSize(new Dimension(600,200));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(row1);
        add(Box.createVerticalStrut(10));  // space

        add(row2);
        add(Box.createVerticalStrut(10));  // space

        add(row3);
        add(Box.createVerticalStrut(20));  // more space before table

        add(scrollPane);

        // Load data
        loadCustomers();
        loadTukTuks();
        loadRentals();

        // Actions
        btnCalculate.addActionListener(e -> calculateTotal());
        btnSave.addActionListener(e -> saveRental());
        btnUpdateRental.addActionListener(e -> updateRental());
        btnDeleteRental.addActionListener(e -> deleteRental());

        table.getSelectionModel().addListSelectionListener(e -> fillFormFromTable());

        getContentPane().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                clearFields();
            }
        });

        scrollPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                clearFields();
            }
        });


    }

    public void calculateTotal(){
        try{
            Date start = startDateChooser.getDate();
            Date end = endDateChooser.getDate();

            if(start == null || end == null){
                JOptionPane.showMessageDialog(this,"Select both dates");
                return;
            }

            if(end.before(start)){
                JOptionPane.showMessageDialog(this,"End date must be after start");
                return;
            }

            String tuk = cmbTukTuk.getSelectedItem().toString();
            double price = tukTukPrices.get(tuk);

            long days = TimeUnit.DAYS.convert(end.getTime()-start.getTime(),TimeUnit.MILLISECONDS);
            if(days==0) days=1;

            txtTotal.setText(String.valueOf(days*price));

        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Error calculating");
        }
    }

    public void loadCustomers(){
        cmbCustomer.removeAllItems();
        for(Customer c : new CustomerDAO().getAllCustomers()){
            cmbCustomer.addItem(c.getCustomerId()+" - "+c.getName());
        }
    }

    public void loadTukTuks(){
        cmbTukTuk.removeAllItems();
        tukTukPrices.clear();

        for(TukTuk t : new TukTukDAO().getAllTukTuks()){
            if(!t.getStatus().equalsIgnoreCase("Maintenance")){
                String item = t.getTuktukId()+" - "+t.getPlateNo();
                cmbTukTuk.addItem(item);
                tukTukPrices.put(item,t.getPricePerDay());
            }
        }
    }

    public void loadRentals(){

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        RentalDAO dao = new RentalDAO();
        List<Rental> list = dao.getAllRentals();

        for(Rental r : list){
            model.addRow(new Object[]{
                    r.getRentalId(),
                    r.getCustomerId() + " - " + r.getCustomerName(),
                    r.getTuktukId() + " - " + r.getPlateNo(),
                    r.getStartDate(),
                    r.getEndDate(),
                    r.getTotalAmount()
            });
        }

        fixTableColumns();
    }

    public void fillFormFromTable(){

        int row = table.getSelectedRow();
        if(row == -1) return;

        try{
            String cust = table.getValueAt(row,1).toString();
            String tuk = table.getValueAt(row,2).toString();

            int cid = Integer.parseInt(cust.split(" - ")[0]);
            int tid = Integer.parseInt(tuk.split(" - ")[0]);

            for(int i=0;i<cmbCustomer.getItemCount();i++){
                if(cmbCustomer.getItemAt(i).startsWith(cid+" -")){
                    cmbCustomer.setSelectedIndex(i);
                }
            }

            for(int i=0;i<cmbTukTuk.getItemCount();i++){
                if(cmbTukTuk.getItemAt(i).startsWith(tid+" -")){
                    cmbTukTuk.setSelectedIndex(i);
                }
            }

            startDateChooser.setDate((Date)table.getValueAt(row,3));
            endDateChooser.setDate((Date)table.getValueAt(row,4));
            txtTotal.setText(table.getValueAt(row,5).toString());

        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Error loading row");
        }
    }

    public void clearFields(){
        startDateChooser.setDate(null);
        endDateChooser.setDate(null);
        txtTotal.setText("");
        cmbCustomer.setSelectedIndex(-1);
        cmbTukTuk.setSelectedIndex(-1);
        table.clearSelection();
    }

    public void saveRental(){

        try{

            if(txtTotal.getText().isEmpty()){
                JOptionPane.showMessageDialog(this,"Please calculate total first");
                return;
            }

            String customerItem = cmbCustomer.getSelectedItem().toString();
            String tukTukItem = cmbTukTuk.getSelectedItem().toString();

            int customerId = Integer.parseInt(customerItem.split(" - ")[0]);
            int tukTukId = Integer.parseInt(tukTukItem.split(" - ")[0]);

            java.util.Date start = startDateChooser.getDate();
            java.util.Date end = endDateChooser.getDate();

            java.sql.Date sqlStart = new java.sql.Date(start.getTime());
            java.sql.Date sqlEnd = new java.sql.Date(end.getTime());

            double total = Double.parseDouble(txtTotal.getText());

            Rental r = new Rental();

            r.setCustomerId(customerId);
            r.setTuktukId(tukTukId);
            r.setStartDate(sqlStart);
            r.setEndDate(sqlEnd);
            r.setTotalAmount(total);

            RentalDAO dao = new RentalDAO();

            boolean available = dao.isTukTukAvailable(tukTukId, sqlStart, sqlEnd);

            if(!available){
                JOptionPane.showMessageDialog(this,
                        "This TukTuk is already booked for the selected dates.");
                return;
            }

            dao.addRental(r);

            loadRentals();

            TukTukDAO tukDao = new TukTukDAO();

            loadTukTuks();

            JOptionPane.showMessageDialog(this,"Rental saved successfully");

            clearFields();

        }
        catch(Exception e){

            JOptionPane.showMessageDialog(this,"Error saving rental");

        }
    }

    public void updateRental(){

        try{

            int row = table.getSelectedRow();

            if(row < 0){
                JOptionPane.showMessageDialog(this,"Please select a rental first");
                return;
            }

            int rentalId = Integer.parseInt(table.getValueAt(row,0).toString());

            String customerItem = cmbCustomer.getSelectedItem().toString();
            String tukItem = cmbTukTuk.getSelectedItem().toString();

            int customerId = Integer.parseInt(customerItem.split(" - ")[0]);
            int tukTukId = Integer.parseInt(tukItem.split(" - ")[0]);

            java.util.Date start = startDateChooser.getDate();
            java.util.Date end = endDateChooser.getDate();

            java.sql.Date sqlStart = new java.sql.Date(start.getTime());
            java.sql.Date sqlEnd = new java.sql.Date(end.getTime());

            double total = Double.parseDouble(txtTotal.getText());

            Rental r = new Rental();

            r.setRentalId(rentalId);
            r.setCustomerId(customerId);
            r.setTuktukId(tukTukId);
            r.setStartDate(sqlStart);
            r.setEndDate(sqlEnd);
            r.setTotalAmount(total);

            RentalDAO dao = new RentalDAO();
            dao.updateRental(r);

            loadRentals();

            JOptionPane.showMessageDialog(this,"Rental updated successfully");

        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this,"Error updating rental");
        }
    }

    public void deleteRental(){

        try{

            int row = table.getSelectedRow();

            if(row < 0){
                JOptionPane.showMessageDialog(this,"Please select a rental first");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this rental?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if(confirm != JOptionPane.YES_OPTION){
                return;
            }

            int rentalId = Integer.parseInt(table.getValueAt(row,0).toString());

            RentalDAO dao = new RentalDAO();
            dao.deleteRental(rentalId);

            loadRentals();
            loadTukTuks();

            clearFields();

            JOptionPane.showMessageDialog(this,"Rental deleted successfully");

        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this,"Error deleting rental");
        }
    }

    public void fixTableColumns() {

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(150);  // Customer
        table.getColumnModel().getColumn(2).setPreferredWidth(150);  // TukTuk
        table.getColumnModel().getColumn(3).setPreferredWidth(120);  // Start
        table.getColumnModel().getColumn(4).setPreferredWidth(120);  // End
        table.getColumnModel().getColumn(5).setPreferredWidth(100);  // Total
    }

    public void fixTable(JTable table, int[] widths){
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for(int i=0;i<widths.length;i++){
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }
    }


}