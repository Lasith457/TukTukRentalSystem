package dao;

import database.DBConnection;
import model.TukTuk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TukTukDAO {

    public void addTukTuk(TukTuk tuktuk) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO tuktuks(plate_no,model,price_per_day,status) VALUES (?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, tuktuk.getPlateNo());
            ps.setString(2, tuktuk.getModel());
            ps.setDouble(3, tuktuk.getPricePerDay());
            ps.setString(4, tuktuk.getStatus());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<TukTuk> getAllTukTuks() {

        List<TukTuk> list = new ArrayList<>();

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM tuktuks";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                TukTuk t = new TukTuk();

                t.setTuktukId(rs.getInt("tuktuk_id"));
                t.setPlateNo(rs.getString("plate_no"));
                t.setModel(rs.getString("model"));
                t.setPricePerDay(rs.getDouble("price_per_day"));
                t.setStatus(rs.getString("status"));

                list.add(t);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void updateTukTuk(TukTuk tuktuk) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "UPDATE tuktuks SET plate_no=?, model=?, price_per_day=?, status=? WHERE tuktuk_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, tuktuk.getPlateNo());
            ps.setString(2, tuktuk.getModel());
            ps.setDouble(3, tuktuk.getPricePerDay());
            ps.setString(4, tuktuk.getStatus());
            ps.setInt(5, tuktuk.getTuktukId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteTukTuk(int id) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "DELETE FROM tuktuks WHERE tuktuk_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void updateStatus(int id, String status){

        try{

            Connection con = DBConnection.getConnection();

            String sql = "UPDATE tuktuks SET status=? WHERE tuktuk_id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1,status);
            ps.setInt(2,id);

            ps.executeUpdate();

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

}