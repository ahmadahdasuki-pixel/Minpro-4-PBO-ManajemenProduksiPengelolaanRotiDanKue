# Manajemen Produksi Pengelolaan Roti Dan Kue

## Deskripsi Program
Sistem Manajemen Produksi Roti & Kue adalah aplikasi berbasis konsol berbasis Java yang dirancang untuk mengelola seluruh siklus produksi produk roti dan kue secara komprehensif. Aplikasi ini mengorganisasikan berbagai variabel penting produksi secara terstruktur, mulai dari identitas produk, kuantitas stok, harga jual, tanggal pembuatan, status pengerjaan, hingga karakteristik spesifik seperti rasa roti dan ukuran kue.

Dalam pengembangannya, sistem ini menerapkan arsitektur modern MVC (Model-View-Controller) dan pola desain DAO (Data Access Object) untuk memisahkan logika antarmuka, alur program, dan akses data secara modular. Selain itu, aplikasi telah terintegrasi secara permanen dengan basis data MySQL melalui teknologi JDBC dan PreparedStatement, sehingga keamanan transaksi data dari ancaman SQL Injection serta performa eksekusinya terjamin optimal.

## Penjelasan Structur Package
Proyek ini dibagi menjadi beberapa package modular guna memisahkan tanggung jawab (separation of concerns):

sistemmanajemenproduksi: Berisi kelas utama (Main.java) yang bertindak sebagai titik awal entry point untuk menjalankan aplikasi.

sistemmanajemenproduksi.controller: Berisi kelas pengatur alur logika program ManajemenSistem.java dan antarmuka aturan bisnis KelolaProduksi.java.

sistemmanajemenproduksi.model: Berisi cetak biru objek Blueprint OOP yang mencakup kelas abstrak induk Produk.java serta kelas turunan Roti.java dan Kue.java.

sistemmanajemenproduksi.dao: Berisi kelas ProdukDAO.java yang bertanggung jawab penuh atas komunikasi basis data CRUD dan pencarian.

sistemmanajemenproduksi.database: Berisi kelas Koneksi.java untuk menangani pembukaan dan penutupan koneksi ke server MySQL.

sistemmanajemenproduksi.view: Berisi kelas View.java yang mengelola antarmuka pengguna di konsol serta melakukan validasi input yang ketat.

## Penjelasan Encapsulation dan Inheritence
**Encapsulation (Enkapsulasi)**
Enkapsulasi diterapkan pada kelas-kelas model dengan cara mendeklarasikan atribut kelas menjadi private untuk melindungi data internal, dan menyediakan akses melalui method getter dan setter.

Contoh Code:
```java
public abstract class Produk {
    private int idProduk;
    private String namaProduk;
    private int stok;
    // Atribut lainnya...

    // Getter dan Setter untuk enkapsulasi data
    public int getIdProduk() { return idProduk; }
    public void setIdProduk(int idProduk) { this.idProduk = idProduk; }
    
    public String getNamaProduk() { return namaProduk; }
    public void setNamaProduk(String namaProduk) { this.namaProduk = namaProduk; }
}
```

**Inheritance (Pewarisan)**
Pewarisan diterapkan dengan membuat kelas induk bernama Produk, kemudian kelas Roti dan Kue mewarisi seluruh atribut serta method yang ada di dalam kelas Produk menggunakan kata kunci extends.

Contoh Code:
```java
public class Roti extends Produk {
    private String rasa;
    private String ukuranRoti;

    public Roti(int idProduk, String namaProduk, int stok, double hargaProduk, 
                int jumlahProduksi, String tanggalProduksi, String statusProduksi, 
                String rasa, String ukuranRoti) {
        super(idProduk, namaProduk, stok, hargaProduk, jumlahProduksi, tanggalProduksi, statusProduksi);
        this.rasa = rasa;
        this.ukuranRoti = ukuranRoti;
    }
}
```
## Penjelasan Polymorpishm Dan Abstraction
**Abstraction (Abstraksi)**
Abstraksi diterapkan melalui kelas abstrak Produk yang mendeklarasikan method abstrak tampilkanProduk(). Setiap kelas turunan (Roti dan Kue) wajib mengimplementasikan method tersebut sesuai dengan karakteristik khususnya masing-masing tanpa harus mengekspos detail implementasi internalnya ke kelas lain.

Contoh Code:

```java
// 1. Pada Kelas Abstrak (Produk.java)
public abstract class Produk {
    private int idProduk;
    private String namaProduk;
    // ... atribut dan konstruktor lainnya

    // Method abstrak tanpa tubuh (body), wajib di-override oleh kelas anak
    public abstract void tampilkanProduk();
}

// 2. Implementasi pada Kelas Turunan (Roti.java)
public class Roti extends Produk {
    private String rasa;
    private String ukuranRoti;

    public Roti(int idProduk, String namaProduk, int stok, double hargaProduk, 
                int jumlahProduksi, String tanggalProduksi, String statusProduksi, 
                String rasa, String ukuranRoti) {
        super(idProduk, namaProduk, stok, hargaProduk, jumlahProduksi, tanggalProduksi, statusProduksi);
        this.rasa = rasa;
        this.ukuranRoti = ukuranRoti;
    }

    @Override
    public void tampilkanProduk() {
        System.out.println("ID Produk        : " + getIdProduk());
        System.out.println("Nama Produk      : " + getNamaProduk());
        System.out.println("Jenis            : Roti");
        System.out.println("Rasa             : " + rasa);
        System.out.println("Ukuran Roti      : " + ukuranRoti);
        // ... informasi lainnya
    }
}
```

**Polymorphism (Polimorfisme)**
Polimorfisme diterapkan saat menampilkan atau memproses data. Program dapat memperlakukan objek Roti dan Kue secara seragam menggunakan tipe data induknya (Produk), namun tetap mengeksekusi perilaku spesifik dari masing-masing kelas turunannya secara dinamis saat program berjalan (runtime polymorphism).

Contoh Code:

```java
// Di dalam ManajemenSistem.java saat menampilkan daftar produk
public void tampilkanProduk() {
    ArrayList<Produk> daftarProduk = produkDAO.tampilkanSemua();

    if (daftarProduk.isEmpty()) {
        System.out.println("Belum ada data produksi di database.");
        return;
    }

    for (Produk produk : daftarProduk) {
        System.out.println();
        
        produk.tampilkanProduk(); 
    }
}
```

## Penjelasan Penerapan JDBC
JDBC adalah Application Programming Interface (API) standar berbasis Java yang berfungsi untuk menghubungkan dan mengeksekusi perintah ke sistem basis data relasional, seperti MySQL. Melalui JDBC, aplikasi Java dapat membuka sambungan, mengirimkan query SQL, serta memproses kumpulan data kembali ke dalam memori aplikasi.

Dalam proyek ini, penerapan JDBC dibagi menjadi dua bagian utama, yaitu kelas manajemen koneksi (Koneksi.java) dan kelas akses data (ProdukDAO.java). Berikut adalah penjelasan mendetail beserta contoh kodenya:

**Konfigurasi dan Manajemen Koneksi Database**
Sebelum saya Konek kan Netbins dengan database phpmyadmin saya terlebih dahulu membuat database tersebut atau data dummy nya di php my admin nya agar pada java tidak perlu lagi saya membuat data dummy dengan array list.

Code Create data dummy di phpmyadmin
```mysql
CREATE DATABASE  sistem_produksi_roti_kue;

USE sistem_produksi_roti_kue;

CREATE TABLE  produk (
    id_produk INT PRIMARY KEY,
    nama_produk VARCHAR(100) NOT NULL,
    stok INT NOT NULL,
    harga_produk DOUBLE NOT NULL,
    jumlah_produksi INT NOT NULL,
    tanggal_produksi VARCHAR(10) NOT NULL,
    status_produksi VARCHAR(20) NOT NULL,
    jenis_produk VARCHAR(20) NOT NULL,
    rasa VARCHAR(50),
    ukuran_roti VARCHAR(30),
    jenis_kue VARCHAR(50),
    ukuran_kue VARCHAR(30)
);

INSERT INTO produk (
    id_produk, nama_produk, stok, harga_produk,
    jumlah_produksi, tanggal_produksi, status_produksi,
    jenis_produk, rasa, ukuran_roti, jenis_kue, ukuran_kue
) VALUES
(
    20, 'Roti Keju', 80, 15000,
    50, '20-09-2026', 'Selesai',
    'Roti', 'Keju', 'Medium', NULL, NULL
),
(
    17, 'Brownies Coklat', 50, 25000,
    75, '19-09-2026', 'Diproses',
    'Kue', NULL, NULL, 'Brownies', 'Sedang'
);
```
Selanjutnya setelah saya membuat di phpmyadmin database, lanjut untuk ke java dengan membuat class Koneksi dan TesKoneksi untuk membuat konfigurasi database nya dengan java.

Class Code Koneksi:
```java
package sistemmanajemenproduksi.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {

    private static final String URL =
            "jdbc:mysql://localhost:3306/sistem_produksi_roti_kue";

    private static final String USER =
            "root";

    private static final String PASSWORD =
            "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
```

Baris URL, USER, dan PASS mendefinisikan kredensial akses ke server MySQL lokal, di mana db_manajemen_produksi bertindak sebagai nama database yang digunakan. Method getConnection() menggunakan DriverManager.getConnection(URL, USER, PASS) untuk meminta objek sambungan fisik (Connection) dari driver MySQL. Blok try-catch disertakan untuk menangkap potensi galat (exception) apabila server database mati, alamat URL salah, atau autentikasi ditolak, sehingga program tidak langsung berhenti secara mendadak.

Class Code TesKoneksi:
```java
package sistemmanajemenproduksi.database;
import java.sql.Connection;
import java.sql.SQLException;
import sistemmanajemenproduksi.database.Koneksi;

public class TesKoneksi {

    public static void main(String[] args) {
        try (Connection conn = Koneksi.getConnection()) {
            System.out.println("Koneksi database berhasil!");
        } catch (SQLException e) {
            System.out.println("Koneksi database gagal.");
            System.out.println(e.getMessage());
        }
    }
}
```
Kode di atas adalah kode uji coba pada class TesKoneksi.java untuk memverifikasi sambungan database MySQL secara mandiri sebelum aplikasi utama dijalankan, dengan memanfaatkan struktur try-with-resources untuk memanggil Koneksi.getConnection(). Jika konfigurasi seperti URL, username, dan password sudah benar serta server MySQL aktif, program akan mencetak pesan sukses ke konsol; sebaliknya, jika terjadi kesalahan, blok catch akan menangkap SQLException dan mencetak detail pesan galat tanpa membuat program berhenti mendadak.

<img width="647" height="243" alt="image" src="https://github.com/user-attachments/assets/382c5f91-7434-4340-8e94-75b73de77500" />

Output di atas adalah bukti bahwa java kita sudah terkoneksi dengan database kita sendiri yang dimana nanti perubahan pada java saat menjalankan fitur aplikasi pada database juga akan berubah.

## Penjelasan Prepared Statement
PreparedStatement adalah kelas turunan dari JDBC yang digunakan untuk mengeksekusi pernyataan SQL yang telah dikompilasi sebelumnya. Di dalam proyek ini, seluruh operasi manipulasi data—seperti tambah (INSERT), ubah (UPDATE), cari (SELECT), dan hapus (DELETE)—wajib menggunakan PreparedStatement alih-alih menggunakan Statement biasa.

Penerapan ini memiliki dua keunggulan utama yang krusial, yaitu perlindungan terhadap celah keamanan SQL Injection dan peningkatan performa eksekusi. 

Contoh Code:
```java
public boolean updateStok(int idProduk, int stokBaru) {
    String sql = "UPDATE produk SET stok = ? WHERE id_produk = ?";

    try (
        Connection conn = Koneksi.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)
    ) {
        // Mengisi parameter tanda tanya (?) berdasarkan urutan indeks
        ps.setInt(1, stokBaru);
        ps.setInt(2, idProduk);

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        System.out.println("Gagal memperbarui stok: " + e.getMessage());
        return false;
    }
}
```
Pada Code di atas Penerapan PreparedStatement menggunakan tanda tanya (?) sebagai parameter penampung dinamis untuk mencegah manipulasi input, di mana database akan mengompilasi struktur kueri tersebut satu kali di awal agar eksekusi berulang menjadi lebih cepat dan efisien. Melalui method seperti ps.setInt(), nilai variabel dimasukkan secara aman berdasarkan indeksnya sehingga input diperlakukan murni sebagai data, sebelum akhirnya perintah ps.executeUpdate() dijalankan untuk memproses perubahan data dan mengembalikan nilai boolean penanda keberhasilan.

## Penjelasan DAO
Penerapan pola desain DAO (Data Access Object) memisahkan logika akses basis data—seperti operasi CRUD dan pencarian—secara terpusat di dalam kelas ProdukDAO.java, sehingga mewujudkan prinsip Separation of Concerns di mana lapisan Controller dan View terbebas dari kerumitan kueri SQL maupun struktur tabel MySQL dan membuat pemeliharaan kode menjadi jauh lebih mudah.

Contoh Code:
```java
public class ProdukDAO {

    // 1. Menampilkan semua produk dari database
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
            System.out.println("Gagal mengambil data produk: " + e.getMessage());
        }

        return daftarProduk;
    }

    // 2. Menghapus produk berdasarkan ID
    public boolean hapus(int idProduk) {
        String sql = "DELETE FROM produk WHERE id_produk = ?";

        try (
            Connection conn = Koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, idProduk);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal menghapus produk: " + e.getMessage());
            return false;
        }
    }
}
```

Melalui pembungkusan operasi basis data ke dalam method mandiri yang memanfaatkan try-with-resources untuk mengelola koneksi secara aman, pola DAO memungkinkan Controller untuk memanggil fungsi seperti tampilkanSemua() atau hapus(idProduk) secara bersih dan terstruktur, sehingga kode program keseluruhan menjadi jauh lebih rapi dan mudah dipelihara.


