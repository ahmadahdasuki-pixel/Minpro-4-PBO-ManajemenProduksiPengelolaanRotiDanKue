package sistemmanajemenproduksi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sistemmanajemenproduksi.database.Koneksi;
import sistemmanajemenproduksi.model.Kue;
import sistemmanajemenproduksi.model.Produk;
import sistemmanajemenproduksi.model.Roti;

public class ProdukDAO {

    // Menambahkan produk ke database
    public boolean tambah(Produk produk) {
        String sql = "INSERT INTO produk "
                + "(id_produk, nama_produk, stok, harga_produk, "
                + "jumlah_produksi, tanggal_produksi, status_produksi, "
                + "jenis_produk, rasa, ukuran_roti, jenis_kue, ukuran_kue) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection conn = Koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, produk.getIdProduk());
            ps.setString(2, produk.getNamaProduk());
            ps.setInt(3, produk.getStok());
            ps.setDouble(4, produk.getHargaProduk());
            ps.setInt(5, produk.getJumlahProduksi());
            ps.setString(6, produk.getTanggalProduksi());
            ps.setString(7, produk.getStatusProduksi());

            if (produk instanceof Roti) {
                Roti roti = (Roti) produk;

                ps.setString(8, "Roti");
                ps.setString(9, roti.getRasa());
                ps.setString(10, roti.getUkuranRoti());
                ps.setNull(11, java.sql.Types.VARCHAR);
                ps.setNull(12, java.sql.Types.VARCHAR);

            } else if (produk instanceof Kue) {
                Kue kue = (Kue) produk;

                ps.setString(8, "Kue");
                ps.setNull(9, java.sql.Types.VARCHAR);
                ps.setNull(10, java.sql.Types.VARCHAR);
                ps.setString(11, kue.getJenisKue());
                ps.setString(12, kue.getUkuranKue());

            } else {
                return false;
            }

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal menambahkan produk: "
                    + e.getMessage());
            return false;
        }
    }

    // Menampilkan semua produk
    public ArrayList<Produk> tampilkanSemua() {
        ArrayList<Produk> daftarProduk = new ArrayList<>();
        String sql = "SELECT * FROM produk ORDER BY id_produk";

        try (
            Connection conn = Koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Produk produk = buatObjekProduk(rs);

                if (produk != null) {
                    daftarProduk.add(produk);
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil data produk: "
                    + e.getMessage());
        }

        return daftarProduk;
    }

    // Mencari produk berdasarkan ID
    public Produk cariById(int idProduk) {
        String sql = "SELECT * FROM produk WHERE id_produk = ?";

        try (
            Connection conn = Koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, idProduk);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return buatObjekProduk(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal mencari produk: "
                    + e.getMessage());
        }

        return null;
    }

    // Mencari produk berdasarkan nama
    public ArrayList<Produk> cariByNama(String namaProduk) {
        ArrayList<Produk> hasil = new ArrayList<>();

        String sql = "SELECT * FROM produk "
                + "WHERE nama_produk LIKE ? ORDER BY id_produk";

        try (
            Connection conn = Koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, "%" + namaProduk + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Produk produk = buatObjekProduk(rs);

                    if (produk != null) {
                        hasil.add(produk);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal mencari produk: "
                    + e.getMessage());
        }

        return hasil;
    }

    // Memperbarui stok produk
    public boolean updateStok(int idProduk, int stokBaru) {
        String sql = "UPDATE produk SET stok = ? WHERE id_produk = ?";

        try (
            Connection conn = Koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, stokBaru);
            ps.setInt(2, idProduk);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal memperbarui stok: "
                    + e.getMessage());
            return false;
        }
    }

    // Menghapus produk berdasarkan ID
    public boolean hapus(int idProduk) {
        String sql = "DELETE FROM produk WHERE id_produk = ?";

        try (
            Connection conn = Koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, idProduk);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal menghapus produk: "
                    + e.getMessage());
            return false;
        }
    }

    // Mengubah satu baris database menjadi objek Roti atau Kue
    private Produk buatObjekProduk(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_produk");
        String nama = rs.getString("nama_produk");
        int stok = rs.getInt("stok");
        double harga = rs.getDouble("harga_produk");
        int jumlah = rs.getInt("jumlah_produksi");
        String tanggal = rs.getString("tanggal_produksi");
        String status = rs.getString("status_produksi");
        String jenis = rs.getString("jenis_produk");

        if ("Roti".equalsIgnoreCase(jenis)) {
            return new Roti(
                    id, nama, stok, harga, jumlah,
                    tanggal, status,
                    rs.getString("rasa"),
                    rs.getString("ukuran_roti")
            );
        }

        if ("Kue".equalsIgnoreCase(jenis)) {
            return new Kue(
                    id, nama, stok, harga, jumlah,
                    tanggal, status,
                    rs.getString("jenis_kue"),
                    rs.getString("ukuran_kue")
            );
        }

        return null;
    }
}