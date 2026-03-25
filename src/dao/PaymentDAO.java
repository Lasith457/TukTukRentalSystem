package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Payment;
import database.DBConnection;

public class PaymentDAO {

    // Add Payment
    public void addPayment(Payment p){

        try{

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO payments (rental_id, amount, payment_method, payment_date) VALUES (?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, p.getRentalId());
            ps.setDouble(2, p.getAmount());
            ps.setString(3, p.getPaymentMethod());
            ps.setDate(4, p.getPaymentDate());

            ps.executeUpdate();

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    // Get All Payments
    public List<Payment> getAllPayments(){

        List<Payment> list = new ArrayList<>();

        try{

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM payments";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){

                Payment p = new Payment();

                p.setPaymentId(rs.getInt("payment_id"));
                p.setRentalId(rs.getInt("rental_id"));
                p.setAmount(rs.getDouble("amount"));
                p.setPaymentMethod(rs.getString("payment_method"));
                p.setPaymentDate(rs.getDate("payment_date"));

                list.add(p);
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }
    public void deletePayment(int id){

        try{

            Connection con = DBConnection.getConnection();

            String sql = "DELETE FROM payments WHERE payment_id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,id);

            ps.executeUpdate();

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    // Check if payment already exists for rental
    public boolean isPaymentExists(int rentalId){

        try{
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM payments WHERE rental_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, rentalId);

            ResultSet rs = ps.executeQuery();

            return rs.next(); // true if payment exists

        } catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
}