package ui;

import ui.LoginForm;
import javax.swing.*;
import java.awt.*;

import dao.CustomerDAO;
import dao.TukTukDAO;
import dao.RentalDAO;
import dao.PaymentDAO;

import model.Rental;
import model.Payment;

public class MainMenu extends JFrame {

    private JDesktopPane desktopPane;
    private JLabel lblTitle; // ✅ FIXED: global variable

    public MainMenu(){

        setTitle("TukTuk Rental System");
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        lblTitle = new JLabel("TukTuk Rental Management System", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblTitle, BorderLayout.NORTH);

        desktopPane = new JDesktopPane();
        add(desktopPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();

        JMenu manageMenu = new JMenu("Manage");
        JMenu systemMenu = new JMenu("System");

        JMenuItem mCustomer = new JMenuItem("Customers");
        JMenuItem mTukTuk = new JMenuItem("TukTuks");
        JMenuItem mRental = new JMenuItem("Rentals");
        JMenuItem mPayment = new JMenuItem("Payments");
        JMenuItem mDashboard = new JMenuItem("Dashboard");
        JMenuItem mExit = new JMenuItem("Exit");
        JMenu reportMenu = new JMenu("Reports");
        JMenuItem mRentalReport = new JMenuItem("Rental Report");
        JMenuItem mLogout = new JMenuItem("Logout");

        manageMenu.add(mCustomer);
        manageMenu.add(mTukTuk);
        manageMenu.add(mRental);
        manageMenu.add(mPayment);
        reportMenu.add(mRentalReport);
        menuBar.add(reportMenu);

        systemMenu.add(mDashboard);
        systemMenu.add(mLogout);
        systemMenu.add(mExit);


        menuBar.add(manageMenu);
        menuBar.add(systemMenu);

        setJMenuBar(menuBar);

        mCustomer.addActionListener(e -> openInternalFrame("Customers", new CustomerForm()));
        mTukTuk.addActionListener(e -> openInternalFrame("TukTuks", new TukTukForm()));
        mRental.addActionListener(e -> openInternalFrame("Rentals", new RentalForm()));
        mPayment.addActionListener(e -> openInternalFrame("Payments", new PaymentForm()));
        mRentalReport.addActionListener(e ->
                report.ReportGenerator.generateRentalReport()
        );
        mDashboard.addActionListener(e -> showDashboard());
        mLogout.addActionListener(e -> logout());
        mExit.addActionListener(e -> System.exit(0));

        showDashboard();

        setVisible(true);
    }

    public void openInternalFrame(String title, JFrame form){

        desktopPane.removeAll();

        lblTitle.setText(title);

        JInternalFrame frame = new JInternalFrame(title, true, true, true, true);
        frame.setLayout(new BorderLayout());

        frame.setContentPane(form.getContentPane());

        frame.setBorder(null);
        ((javax.swing.plaf.basic.BasicInternalFrameUI)frame.getUI()).setNorthPane(null);

        frame.setVisible(true);
        desktopPane.add(frame);

        try {
            frame.setMaximum(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        desktopPane.repaint();
        desktopPane.revalidate();
    }

    public void showDashboard(){

        desktopPane.removeAll();

        lblTitle.setText("TukTuk Rental Management System");

        JPanel dashboardPanel = new JPanel(new GridLayout(2,2,20,20));
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));

        CustomerDAO cDao = new CustomerDAO();
        TukTukDAO tDao = new TukTukDAO();
        RentalDAO rDao = new RentalDAO();
        PaymentDAO pDao = new PaymentDAO();

        int totalCustomers = cDao.getAllCustomers().size();
        int totalTukTuks = tDao.getAllTukTuks().size();

        int activeRentals = 0;
        java.util.Date today = new java.util.Date();

        for(Rental r : rDao.getAllRentals()){
            boolean inDateRange = !today.before(r.getStartDate()) && !today.after(r.getEndDate());
            boolean isPaid = pDao.isPaymentExists(r.getRentalId());

            if(inDateRange && !isPaid){
                activeRentals++;
            }
        }

        double totalRevenue = 0;
        for(Payment p : pDao.getAllPayments()){
            totalRevenue += p.getAmount();
        }

        Font f = new Font("Arial", Font.BOLD, 16);

        JLabel lblCustomers = new JLabel("Customers: " + totalCustomers, JLabel.CENTER);
        JLabel lblTukTuks = new JLabel("TukTuks: " + totalTukTuks, JLabel.CENTER);
        JLabel lblRentals = new JLabel("Active Rentals: " + activeRentals, JLabel.CENTER);
        JLabel lblRevenue = new JLabel("Revenue: " + totalRevenue, JLabel.CENTER);

        lblCustomers.setFont(f);
        lblTukTuks.setFont(f);
        lblRentals.setFont(f);
        lblRevenue.setFont(f);

        dashboardPanel.add(lblCustomers);
        dashboardPanel.add(lblTukTuks);
        dashboardPanel.add(lblRentals);
        dashboardPanel.add(lblRevenue);

        dashboardPanel.setBounds(200,100,500,250);

        desktopPane.add(dashboardPanel);
        desktopPane.repaint();
        desktopPane.revalidate();
    }

    public void logout(){

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Logout",
                JOptionPane.YES_NO_OPTION
        );

        if(confirm == JOptionPane.YES_OPTION){

            this.dispose(); // close main window

            new LoginForm(); // go back to login
        }
    }

    public static void main(String[] args){
        new LoginForm();
    }
}