/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

/**
 *
 * @author NIKY
 */
import java.sql.*;
import java.util.ArrayList;

public class Users {
    private int id_users;
    private String nama;
    private String kontak;
    private String tipe_users;

    public Users() {}

    public Users(String nama, String kontak, String tipe_users) {
        this.nama = nama;
        this.kontak = kontak;
        this.tipe_users = tipe_users;
    }

    // ================= GETTER & SETTER =====================

    public int getId_users() {
        return id_users;
    }

    public void setId_users(int id_users) {
        this.id_users = id_users;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKontak() {
        return kontak;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }

    public String getTipe_users() {
        return tipe_users;
    }

    public void setTipe_users(String tipe_users) {
        this.tipe_users = tipe_users;
    }

    // ===============================================================
    // GET ALL
    // ===============================================================
    public static ArrayList<Users> getAll() {
        return getAll("");
    }

    public static ArrayList<Users> getAll(String keyword) {
        ArrayList<Users> list = new ArrayList<>();

        String sql = "SELECT * FROM users WHERE nama LIKE CONCAT('%', ?, '%')";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, keyword);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Users u = new Users();
                    u.setId_users(rs.getInt("id_users"));
                    u.setNama(rs.getString("nama"));
                    u.setKontak(rs.getString("kontak"));
                    u.setTipe_users(rs.getString("tipe_users"));
                    list.add(u);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ===============================================================
    // GET BY ID
    // ===============================================================
    public static Users getById(int id) {
        Users user = null;

        String sql = "SELECT * FROM users WHERE id_users = ?";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new Users();
                    user.setId_users(rs.getInt("id_users"));
                    user.setNama(rs.getString("nama"));
                    user.setKontak(rs.getString("kontak"));
                    user.setTipe_users(rs.getString("tipe_users"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    // ===============================================================
    // INSERT / UPDATE
    // ===============================================================
    public void save() {

        if (this.id_users == 0) {
            String sql = "INSERT INTO users (nama, kontak, tipe_users) VALUES (?, ?, ?)";

            try (Connection conn = DBHelper.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, this.nama);
                ps.setString(2, this.kontak);
                ps.setString(3, this.tipe_users);
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        this.id_users = rs.getInt(1);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            String sql = "UPDATE users SET nama = ?, kontak = ?, tipe_users = ? WHERE id_users = ?";

            try (Connection conn = DBHelper.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, this.nama);
                ps.setString(2, this.kontak);
                ps.setString(3, this.tipe_users);
                ps.setInt(4, this.id_users);

                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // ===============================================================
    // DELETE
    // ===============================================================
    public static boolean delete(int id_users) {

        String sql = "DELETE FROM users WHERE id_users = ?";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id_users);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}