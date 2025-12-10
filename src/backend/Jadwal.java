package backend;
/**
 *
 * @author NORA
 */

import java.sql.*;
import java.util.ArrayList;

public class Jadwal {

    private int idJadwal;
    private String namaKegiatan;
    private String tglReservasi;
    private String waktuMulai;
    private String waktuSelesai;
    private String status;

    public Jadwal() {
    }

    public Jadwal(String namaKegiatan, String tglReservasi, String waktuMulai, String waktuSelesai, String status) {
        this.namaKegiatan = namaKegiatan;
        this.tglReservasi = tglReservasi;
        this.waktuMulai = waktuMulai;
        this.waktuSelesai = waktuSelesai;
        this.status = status;
    }

    // ================== GETTER & SETTER ==================
    public int getIdJadwal() {
        return idJadwal;
    }

    public void setIdJadwal(int idJadwal) {
        this.idJadwal = idJadwal;
    }

    public String getNamaKegiatan() {
        return namaKegiatan;
    }

    public void setNamaKegiatan(String namaKegiatan) {
        this.namaKegiatan = namaKegiatan;
    }

    public String getTglReservasi() {
        return tglReservasi;
    }

    public void setTglReservasi(String tglReservasi) {
        this.tglReservasi = tglReservasi;
    }

    public String getWaktuMulai() {
        return waktuMulai;
    }

    public void setWaktuMulai(String waktuMulai) {
        this.waktuMulai = waktuMulai;
    }

    public String getWaktuSelesai() {
        return waktuSelesai;
    }

    public void setWaktuSelesai(String waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // ================== GET ALL ==================
    public static ArrayList<Jadwal> getAll(String keyword) {
        ArrayList<Jadwal> list = new ArrayList<>();
        String sql = "SELECT * FROM jadwal WHERE nama_kegiatan LIKE ? OR status LIKE ?";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Jadwal j = new Jadwal();
                    j.setIdJadwal(rs.getInt("id_jadwal"));
                    j.setNamaKegiatan(rs.getString("nama_kegiatan"));
                    j.setTglReservasi(rs.getString("tgl_reservasi"));
                    j.setWaktuMulai(rs.getString("waktu_mulai"));
                    j.setWaktuSelesai(rs.getString("waktu_selesai"));
                    j.setStatus(rs.getString("status"));
                    list.add(j);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================== GET BY ID ==================
    public static Jadwal getById(int id) {
        Jadwal j = null;
        String sql = "SELECT * FROM jadwal WHERE id_jadwal = ?";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    j = new Jadwal();
                    j.setIdJadwal(rs.getInt("id_jadwal"));
                    j.setNamaKegiatan(rs.getString("nama_kegiatan"));
                    j.setTglReservasi(rs.getString("tgl_reservasi"));
                    j.setWaktuMulai(rs.getString("waktu_mulai"));
                    j.setWaktuSelesai(rs.getString("waktu_selesai"));
                    j.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return j;
    }

    // ================== SAVE (INSERT / UPDATE) ==================
    public void save() {
        if (this.idJadwal == 0) {
            // INSERT
            String sql = "INSERT INTO jadwal (nama_kegiatan, tgl_reservasi, waktu_mulai, waktu_selesai, status) "
                    + "VALUES (?, ?, ?, ?, ?)";

            try (Connection conn = DBHelper.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, this.namaKegiatan);
                ps.setString(2, this.tglReservasi);
                ps.setString(3, this.waktuMulai);
                ps.setString(4, this.waktuSelesai);
                ps.setString(5, this.status);
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        this.idJadwal = rs.getInt(1);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            // UPDATE
            String sql = "UPDATE jadwal SET nama_kegiatan=?, tgl_reservasi=?, waktu_mulai=?, waktu_selesai=?, status=? "
                    + "WHERE id_jadwal=?";

            try (Connection conn = DBHelper.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, this.namaKegiatan);
                ps.setString(2, this.tglReservasi);
                ps.setString(3, this.waktuMulai);
                ps.setString(4, this.waktuSelesai);
                ps.setString(5, this.status);
                ps.setInt(6, this.idJadwal);

                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // ================== DELETE ==================
    public static boolean delete(int idJadwal) {
        String sql = "DELETE FROM jadwal WHERE id_jadwal = ?";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idJadwal);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
