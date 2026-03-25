package dao;

import database.DBConnection;
import model.Rental;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RentalDAO {

    public void addRental(Rental rental) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO rentals(customer_id,tuktuk_id,start_date,end_date,total_amount) VALUES (?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1,rental.getCustomerId());
            ps.setInt(2,rental.getTuktukId());
            ps.setDate(3,rental.getStartDate());
            ps.setDate(4,rental.getEndDate());
            ps.setDouble(5,rental.getTotalAmount());

            ps.executeUpdate();

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void updateRental(Rental r){

        try{

            Connection con = DBConnection.getConnection();

            String sql = "UPDATE rentals SET customer_id=?, tuktuk_id=?, start_date=?, end_date=?, total_amount=? WHERE rental_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1,r.getCustomerId());
            ps.setInt(2,r.getTuktukId());
            ps.setDate(3,r.getStartDate());
            ps.setDate(4,r.getEndDate());
            ps.setDouble(5,r.getTotalAmount());
            ps.setInt(6,r.getRentalId());

            ps.executeUpdate();

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<Rental> getAllRentals(){

        List<Rental> list = new ArrayList<>();

        try{

            Connection con = DBConnection.getConnection();

            String sql = "SELECT r.*, c.name, t.plate_no " +
                    "FROM rentals r " +
                    "JOIN customers c ON r.customer_id = c.customer_id " +
                    "JOIN tuktuks t ON r.tuktuk_id = t.tuktuk_id";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                Rental r = new Rental();

                r.setRentalId(rs.getInt("rental_id"));
                r.setCustomerId(rs.getInt("customer_id"));
                r.setTuktukId(rs.getInt("tuktuk_id"));
                r.setStartDate(rs.getDate("start_date"));
                r.setEndDate(rs.getDate("end_date"));
                r.setTotalAmount(rs.getDouble("total_amount"));
                r.setCustomerName(rs.getString("name"));
                r.setPlateNo(rs.getString("plate_no"));

                list.add(r);

            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }
    public boolean isTukTukAvailable(int tukTukId, java.sql.Date start, java.sql.Date end){

        try{

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM rentals WHERE tuktuk_id=? AND start_date <= ? AND end_date >= ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, tukTukId);
            ps.setDate(2, end);
            ps.setDate(3, start);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return false;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return true;
    }
    public void deleteRental(int id){

        try{

            Connection con = DBConnection.getConnection();

            String sql = "DELETE FROM rentals WHERE rental_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1,id);

            ps.executeUpdate();

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}