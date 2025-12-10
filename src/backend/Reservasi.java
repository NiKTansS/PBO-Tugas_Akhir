package backend;

import java.sql.*;
import java.util.ArrayList;

public class Reservasi {

    private int id_reservasi;
    private int id_users;
    private int id_lab;
    private int id_jadwal;
    private Date tgl_reservasi;
    private String nama_kegiatan;
    private Time waktu_mulai;
    private Time waktu_selesai;
    private String status;

    // ===================== CONSTRUCTOR ==========================
    public Reservasi() {}

    public Reservasi(int id_users, int id_lab, int id_jadwal, Date tgl_reservasi,
                     String nama_kegiatan, Time waktu_mulai, Time waktu_selesai, String status) {

        this.id_users = id_users;
        this.id_lab = id_lab;
        this.id_jadwal = id_jadwal;
        this.tgl_reservasi = tgl_reservasi;
        this.nama_kegiatan = nama_kegiatan;
        this.waktu_mulai = waktu_mulai;
        this.waktu_selesai = waktu_selesai;
        this.status = status;
    }

    // ===================== GETTER SETTER ==========================
    public int getId_reservasi() {
        return id_reservasi;
    }

    public void setId_reservasi(int id_reservasi) {
        this.id_reservasi = id_reservasi;
    }

    public int getId_users() {
        return id_users;
    }

    public void setId_users(int id_users) {
        this.id_users = id_users;
    }

    public int getId_lab() {
        return id_lab;
    }

    public void setId_lab(int id_lab) {
        this.id_lab = id_lab;
    }

    public int getId_jadwal() {
        return id_jadwal;
    }

    public void setId_jadwal(int id_jadwal) {
        this.id_jadwal = id_jadwal;
    }

    public Date getTgl_reservasi() {
        return tgl_reservasi;
    }

    public void setTgl_reservasi(Date tgl_reservasi) {
        this.tgl_reservasi = tgl_reservasi;
    }

    public String getNama_kegiatan() {
        return nama_kegiatan;
    }

    public void setNama_kegiatan(String nama_kegiatan) {
        this.nama_kegiatan = nama_kegiatan;
    }

    public Time getWaktu_mulai() {
        return waktu_mulai;
    }

    public void setWaktu_mulai(Time waktu_mulai) {
        this.waktu_mulai = waktu_mulai;
    }

    public Time getWaktu_selesai() {
        return waktu_selesai;
    }

    public void setWaktu_selesai(Time waktu_selesai) {
        this.waktu_selesai = waktu_selesai;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    // ===============================================================
    //                           GET ALL
    // ===============================================================
    public static ArrayList<Reservasi> getAll() {
        ArrayList<Reservasi> list = new ArrayList<>();

        String sql = "SELECT * FROM reservasi ORDER BY id_reservasi DESC";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Reservasi r = new Reservasi();
                r.setId_reservasi(rs.getInt("id_reservasi"));
                r.setId_users(rs.getInt("id_users"));
                r.setId_lab(rs.getInt("id_lab"));
                r.setId_jadwal(rs.getInt("id_jadwal"));
                r.setTgl_reservasi(rs.getDate("tgl_reservasi"));
                r.setNama_kegiatan(rs.getString("nama_kegiatan"));
                r.setWaktu_mulai(rs.getTime("waktu_mulai"));
                r.setWaktu_selesai(rs.getTime("waktu_selesai"));
                r.setStatus(rs.getString("status"));

                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ===============================================================
    //                           GET BY ID
    // ===============================================================
    public static Reservasi getById(int id) {

        Reservasi r = null;
        String sql = "SELECT * FROM reservasi WHERE id_reservasi = ?";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    r = new Reservasi();
                    r.setId_reservasi(rs.getInt("id_reservasi"));
                    r.setId_users(rs.getInt("id_users"));
                    r.setId_lab(rs.getInt("id_lab"));
                    r.setId_jadwal(rs.getInt("id_jadwal"));
                    r.setTgl_reservasi(rs.getDate("tgl_reservasi"));
                    r.setNama_kegiatan(rs.getString("nama_kegiatan"));
                    r.setWaktu_mulai(rs.getTime("waktu_mulai"));
                    r.setWaktu_selesai(rs.getTime("waktu_selesai"));
                    r.setStatus(rs.getString("status"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return r;
    }

    // ===============================================================
    //                      INSERT / UPDATE
    // ===============================================================
    public void save() {

        if (this.id_reservasi == 0) {
            // INSERT
            String sql = "INSERT INTO reservasi (id_users, id_lab, id_jadwal, tgl_reservasi, nama_kegiatan, waktu_mulai, waktu_selesai, status) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DBHelper.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, this.id_users);
                ps.setInt(2, this.id_lab);
                ps.setInt(3, this.id_jadwal);
                ps.setDate(4, this.tgl_reservasi);
                ps.setString(5, this.nama_kegiatan);
                ps.setTime(6, this.waktu_mulai);
                ps.setTime(7, this.waktu_selesai);
                ps.setString(8, this.status);

                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    this.id_reservasi = rs.getInt(1);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // UPDATE
            String sql = "UPDATE reservasi SET id_users = ?, id_lab = ?, id_jadwal = ?, tgl_reservasi = ?, nama_kegiatan = ?, waktu_mulai = ?, waktu_selesai = ?, status = ? " +
                         "WHERE id_reservasi = ?";

            try (Connection conn = DBHelper.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, this.id_users);
                ps.setInt(2, this.id_lab);
                ps.setInt(3, this.id_jadwal);
                ps.setDate(4, this.tgl_reservasi);
                ps.setString(5, this.nama_kegiatan);
                ps.setTime(6, this.waktu_mulai);
                ps.setTime(7, this.waktu_selesai);
                ps.setString(8, this.status);
                ps.setInt(9, this.id_reservasi);

                ps.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // ===============================================================
    //                           DELETE
    // ===============================================================
    public static boolean delete(int id_reservasi) {
        String sql = "DELETE FROM reservasi WHERE id_reservasi = ?";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id_reservasi);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
