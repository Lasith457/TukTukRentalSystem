package dao;

import database.DBConnection;
import model.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public void addCustomer(Customer customer) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO customers(name,nic,phone,license_no) VALUES (?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getNic());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getLicenseNo());

            ps.executeUpdate();

            System.out.println("Customer Added Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void updateCustomer(Customer customer) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "UPDATE customers SET name=?, nic=?, phone=?, license_no=? WHERE customer_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getNic());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getLicenseNo());
            ps.setInt(5, customer.getCustomerId());

            ps.executeUpdate();

            System.out.println("Customer Updated Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void deleteCustomer(int customerId) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "DELETE FROM customers WHERE customer_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, customerId);

            ps.executeUpdate();

            System.out.println("Customer Deleted Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public List<Customer> getAllCustomers() {

        List<Customer> customers = new ArrayList<>();

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM customers";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Customer customer = new Customer();

                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setName(rs.getString("name"));
                customer.setNic(rs.getString("nic"));
                customer.setPhone(rs.getString("phone"));
                customer.setLicenseNo(rs.getString("license_no"));

                customers.add(customer);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return customers;

    }
}