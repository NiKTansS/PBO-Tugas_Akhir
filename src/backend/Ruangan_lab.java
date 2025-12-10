package backend;
/**
 *
 * @author nayla annora
 */

import java.util.ArrayList;
import java.sql.*;

public class Ruangan_lab {

    private int idLab;
    private String namaLab;
    private int kapasitas;

    public Ruangan_lab() {
    }

    public Ruangan_lab(String namaLab, int kapasitas) {
        this.namaLab = namaLab;
        this.kapasitas = kapasitas;
    }

    // ==== GETTER & SETTER ====
    public int getIdLab() {
        return idLab;
    }

    public void setIdLab(int idLab) {
        this.idLab = idLab;
    }

    public String getNamaLab() {
        return namaLab;
    }

    public void setNamaLab(String namaLab) {
        this.namaLab = namaLab;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }

    // ==== GET ALL ====
    public static ArrayList<Ruangan_lab> getAll(String keyword) {
        ArrayList<Ruangan_lab> list = new ArrayList<>();
        String sql = "SELECT * FROM ruangan_lab WHERE nama_lab LIKE ?";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ruangan_lab lab = new Ruangan_lab();
                    lab.setIdLab(rs.getInt("id_lab"));
                    lab.setNamaLab(rs.getString("nama_lab"));
                    lab.setKapasitas(rs.getInt("kapasitas"));
                    list.add(lab);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ==== GET BY ID ====
    public static Ruangan_lab getById(int id) {
        Ruangan_lab lab = null;
        String sql = "SELECT * FROM ruangan_lab WHERE id_lab = ?";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    lab = new Ruangan_lab();
                    lab.setIdLab(rs.getInt("id_lab"));
                    lab.setNamaLab(rs.getString("nama_lab"));
                    lab.setKapasitas(rs.getInt("kapasitas"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lab;
    }

    // ==== SAVE (INSERT / UPDATE) ====
    public void save() {
        if (this.idLab == 0) {
            // INSERT
            String sql = "INSERT INTO ruangan_lab (nama_lab, kapasitas) VALUES (?, ?)";

            try (Connection conn = DBHelper.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, this.namaLab);
                ps.setInt(2, this.kapasitas);
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        this.idLab = rs.getInt(1);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            // UPDATE
            String sql = "UPDATE ruangan_lab SET nama_lab = ?, kapasitas = ? WHERE id_lab = ?";

            try (Connection conn = DBHelper.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, this.namaLab);
                ps.setInt(2, this.kapasitas);
                ps.setInt(3, this.idLab);
                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // ==== DELETE ====
    public static boolean delete(int idLab) {
        String sql = "DELETE FROM ruangan_lab WHERE id_lab = ?";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idLab);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            if (e.getErrorCode() == 1451) {
                System.out.println("Ruangan ini masih dipakai pada tabel reservasi!");
            }

            e.printStackTrace();
            return false;
        }
    }
}
