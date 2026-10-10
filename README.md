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

