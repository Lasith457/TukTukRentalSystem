package ui;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

import dao.TukTukDAO;
import model.TukTuk;

public class TukTukForm extends JFrame {

    JTextField txtPlate, txtModel, txtPrice;
    JComboBox<String> cmbStatus;
    JButton btnAdd, btnUpdate, btnDelete;

    JTable table;
    JScrollPane scrollPane;

    public TukTukForm() {

        setTitle("TukTuk Management");
        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row1.add(new JLabel("Plate No:"));
        txtPlate = new JTextField(15);
        row1.add(txtPlate);

        row1.add(new JLabel("Model:"));
        txtModel = new JTextField(15);
        row1.add(txtModel);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row2.add(new JLabel("Price/Day:"));
        txtPrice = new JTextField(10);
        row2.add(txtPrice);

        row2.add(new JLabel("Status:"));
        cmbStatus = new JComboBox<>(new String[]{"Available","Maintenance"});
        row2.add(cmbStatus);

        JPanel row3 = new JPanel();
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");

        row3.add(btnAdd);
        row3.add(btnUpdate);
        row3.add(btnDelete);

        btnAdd.addActionListener(e -> addTukTuk());
        btnUpdate.addActionListener(e -> updateTukTuk());
        btnDelete.addActionListener(e -> deleteTukTuk());

        String[] columns = {"ID","Plate No","Model","Price/Day","Status"};

        DefaultTableModel model = new DefaultTableModel(columns,0){
            public boolean isCellEditable(int r,int c){
                return false;
            }
        };

        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(150);  // Plate
        table.getColumnModel().getColumn(2).setPreferredWidth(150);  // Model
        table.getColumnModel().getColumn(3).setPreferredWidth(100);  // Price
        table.getColumnModel().getColumn(4).setPreferredWidth(120);  // Status

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(550,150));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(row1);
        add(row2);
        add(row3);
        add(scrollPane);

        loadTukTuks();

        table.getSelectionModel().addListSelectionListener(e -> fillFormFromTable());

        getContentPane().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                clearFields();
            }
        });


    }

    public void loadTukTuks() {

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        List<TukTuk> list = new TukTukDAO().getAllTukTuks();

        for(TukTuk t : list){
            model.addRow(new Object[]{
                    t.getTuktukId(),
                    t.getPlateNo(),
                    t.getModel(),
                    t.getPricePerDay(),
                    t.getStatus()
            });
        }
    }

    public void addTukTuk(){

        try{

            String plate = txtPlate.getText().trim();
            String model = txtModel.getText().trim();
            String priceText = txtPrice.getText().trim();

            if(plate.isEmpty() || model.isEmpty() || priceText.isEmpty()){
                JOptionPane.showMessageDialog(this,"All fields required");
                return;
            }

            double price = Double.parseDouble(priceText);

            TukTuk t = new TukTuk();
            t.setPlateNo(plate);
            t.setModel(model);
            t.setPricePerDay(price);
            t.setStatus(cmbStatus.getSelectedItem().toString());

            new TukTukDAO().addTukTuk(t);

            loadTukTuks();
            clearFields();

            JOptionPane.showMessageDialog(this,"TukTuk added");

        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Invalid price");
        }
    }

    public void fillFormFromTable(){

        int row = table.getSelectedRow();
        if(row == -1) return;

        txtPlate.setText(table.getValueAt(row,1).toString());
        txtModel.setText(table.getValueAt(row,2).toString());
        txtPrice.setText(table.getValueAt(row,3).toString());
        cmbStatus.setSelectedItem(table.getValueAt(row,4).toString());
    }

    public void updateTukTuk(){

        try{

            int row = table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(this,"Select a tuk-tuk first");
                return;
            }

            int id = Integer.parseInt(table.getValueAt(row,0).toString());

            TukTuk t = new TukTuk();
            t.setTuktukId(id);
            t.setPlateNo(txtPlate.getText());
            t.setModel(txtModel.getText());
            t.setPricePerDay(Double.parseDouble(txtPrice.getText()));
            t.setStatus(cmbStatus.getSelectedItem().toString());

            new TukTukDAO().updateTukTuk(t);

            loadTukTuks();
            clearFields();

            JOptionPane.showMessageDialog(this,"Updated");

        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Error updating");
        }
    }

    public void deleteTukTuk(){

        try{

            int row = table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(this,"Select a tuk-tuk first");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,"Delete this tuk-tuk?");
            if(confirm != JOptionPane.YES_OPTION) return;

            int id = Integer.parseInt(table.getValueAt(row,0).toString());

            new TukTukDAO().deleteTukTuk(id);

            loadTukTuks();
            clearFields();

            JOptionPane.showMessageDialog(this,"Deleted");

        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Error deleting");
        }
    }

    public void clearFields(){
        txtPlate.setText("");
        txtModel.setText("");
        txtPrice.setText("");
        cmbStatus.setSelectedIndex(0);
        table.clearSelection();
    }


}