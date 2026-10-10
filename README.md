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

## Alur Program

**Menu Utama**

<img width="362" height="195" alt="image" src="https://github.com/user-attachments/assets/2e995c5c-8e6a-4fbd-84a1-ad54e6a8277b" />

Ini adalah tampilan menu utama saat penggua pertama kali membuka program.

**Tambah Produksi**

<img width="428" height="494" alt="image" src="https://github.com/user-attachments/assets/424b9d90-c22d-46c5-924c-44ef483abf3d" />

Gambar di atas adalah pilihan menu pertama untuk menambah data produksi baru. Program meminta masukan informasi umum seperti ID produk, nama produk, stok, harga, jumlah produksi, dan tanggal, dilanjutkan dengan pemilihan status produksi (Selesai) dan jenis produk (Roti) beserta atribut khususnya yaitu rasa dan ukuran, sebelum akhirnya memproses dan menampilkan pesan konfirmasi bahwa data produk berhasil ditambahkan ke database.

<img width="1366" height="767" alt="image" src="https://github.com/user-attachments/assets/2f24db46-d757-4559-863c-cf53a6a0bb53" />

Dari gambar di atas dapat di lihat bahwa data yang sudah tambahkan pada program java sebelumnya sudah terkoneksi juga dengan database dengan bukti data tersebut ada di phpmyadmin yang menandakan bahwa program juga berjalan dan terkoneksi dengan baik.

**Tampilkan Data Produksi**

<img width="368" height="701" alt="image" src="https://github.com/user-attachments/assets/da34b3d9-caa2-4424-97dd-d2c7a179da70" />

Gambar di atas menunjukkan hasil saat pengguna memilih menu untuk menampilkan seluruh data produksi, di mana sistem berhasil mengambil dan mencetak daftar produk secara rinci—termasuk atribut umum seperti ID, nama, stok, harga, tanggal produksi, dan status, serta atribut spesifik masing-masing kategori seperti jenis kue atau rasa roti.

**Update Stok Produksi**

<img width="329" height="338" alt="image" src="https://github.com/user-attachments/assets/cf709b3c-8e69-471b-ba68-5746b8d4b8f3" />

Gambar di atas ialah saat pengguna memilih menu nomor 3 untuk memperbarui stok produk, di mana sistem meminta masukan ID produk (80), memvalidasi keberadaan data produk "Canai" dengan stok awal 50, menerima input nilai stok baru (30), dan berhasil memperbarui data tersebut di dalam database.

<img width="1364" height="765" alt="image" src="https://github.com/user-attachments/assets/71e507a6-398a-4dc0-bc28-63ae986277e8" />

Nah dari gambar di atas pun dapat di ketahui bahwa pada data di database phpmyadmin untuk data produk "Canai" itu yang awal stok nya "50" setelah di update pada program java input perubahan "30" , data produk tersebut di database juga ikut berubah yang menandakan koneksi ke database juga berhasil.

**Hapus Data Produksi**

<img width="324" height="262" alt="image" src="https://github.com/user-attachments/assets/05c1553d-2f40-4740-995f-2695900f80bc" />

Gambar di atas menunjukkan proses interaksi saat pengguna memilih menu nomor 4 untuk menghapus data produksi, di mana pengguna meminta masukan ID produk (80) dan langsung mengeksekusi perintah penghapusan, kemudian menampilkan pesan konfirmasi bahwa data produk tersebut berhasil dihapus dari database.

<img width="1364" height="765" alt="image" src="https://github.com/user-attachments/assets/a1de1748-d47f-41d4-a4f4-9cdd95e697c3" />

Setelah menghapus data produk dari program java , maka data produk di database phpmyadmin pun ikut terhapus.

**Cari Data Produksi**

**ID**

<img width="282" height="445" alt="image" src="https://github.com/user-attachments/assets/5365fa03-75be-4ea7-bfff-da78e93dcdbb" />

Gambar di atas menampilkan saat pengguna memilih menu nomor 5 untuk mencari data produksi berdasarkan ID, di mana setelah pengguna memasukkan nomor ID 17, sistem berhasil menemukan dan menampilkan informasi lengkap produk kue tersebut termasuk nama, stok, harga, tanggal produksi, status, hingga atribut spesifik seperti jenis kue dan ukurannya.

**NAMA**

<img width="302" height="601" alt="image" src="https://github.com/user-attachments/assets/61a4418a-95e3-407a-b051-8f240b7f9c08" />

Gambar di atas menampilkan saat pengguna memilih menu nomor 5 untuk mencari data produksi berdasarkan nama, di mana setelah memasukkan kata kunci "Roti Keju", sistem berhasil menemukan dan menampilkan informasi lengkap produk terkait—termasuk ID, stok, harga, tanggal produksi, status, hingga atribut spesifik seperti rasa dan ukuran—sebelum akhirnya kembali menampilkan menu utama aplikasi.

**Keluar Program**

<img width="353" height="283" alt="image" src="https://github.com/user-attachments/assets/6aa6eace-1269-4b43-98af-5eed27c7438d" />

Ini adalah tampilan saat pengguna sudah selesai menggunakan sistem maka pengguna akan memilih keluar program dan akhirnya program selesai.
