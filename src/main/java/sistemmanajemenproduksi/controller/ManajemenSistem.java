package sistemmanajemenproduksi.controller;

import java.util.ArrayList;
import sistemmanajemenproduksi.model.Kue;
import sistemmanajemenproduksi.model.Produk;
import sistemmanajemenproduksi.model.Roti;
import sistemmanajemenproduksi.view.View;
import sistemmanajemenproduksi.controller.KelolaProduksi;
import sistemmanajemenproduksi.dao.ProdukDAO;

public class ManajemenSistem implements KelolaProduksi {

    private final View view;
    private final ProdukDAO produkDAO = new ProdukDAO();

    public ManajemenSistem(View view) {
        this.view = view;
    }
    
    @Override
    public void tambahProduk() {
        System.out.println();
        System.out.println("================================");
        System.out.println("===== TAMBAH DATA PRODUKSI =====");
        System.out.println("================================");

        int idProduk = view.inputId();
        
        if (produkDAO.cariById(idProduk) != null) {
            System.out.println("ID Produk sudah digunakan di database!");
            return;
        }

        String namaProduk = view.inputNamaProduk();
        int stok = view.inputStok();
        double hargaProduk = view.inputHarga();
        int jumlahProduksi = view.inputJumlahProduksi();
        String tanggalProduksi = view.inputTanggalProduksi();
        String statusProduksi = view.inputStatusProduksi();
        int jenisProduk = view.inputJenisProduk();

        Produk produkBaru = null;

        if (jenisProduk == 1) {
            System.out.println();
            System.out.println("=====================");
            System.out.println("===== DATA ROTI =====");
            System.out.println("=====================");

            String rasa = view.inputRasa();
            String ukuranRoti = view.inputUkuranRoti();

            produkBaru = new Roti(
                    idProduk, namaProduk, stok, hargaProduk, 
                    jumlahProduksi, tanggalProduksi, statusProduksi, 
                    rasa, ukuranRoti
            );
        } else {
            System.out.println();
            System.out.println("====================");
            System.out.println("===== DATA KUE =====");
            System.out.println("====================");

            String jenisKue = view.inputJenisKue();
            String ukuranKue = view.inputUkuranKue();

            produkBaru = new Kue(
                    idProduk, namaProduk, stok, hargaProduk, 
                    jumlahProduksi, tanggalProduksi, statusProduksi, 
                    jenisKue, ukuranKue
            );
        }

        boolean berhasil = produkDAO.tambah(produkBaru);
        if (berhasil) {
            System.out.println("Data produk berhasil ditambahkan ke database.");
        } else {
            System.out.println("Gagal menambahkan data produk.");
        }
    }

    @Override
    public void tampilkanProduk() {
        System.out.println();
        System.out.println("=========================");
        System.out.println("===== DATA PRODUKSI =====");
        System.out.println("=========================");

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

    @Override
    public void updateProduk() {
        System.out.println();
        System.out.println("==============================");
        System.out.println("===== UPDATE STOK PRODUK =====");
        System.out.println("==============================");

        int idProduk = view.inputId();
        Produk produk = produkDAO.cariById(idProduk);

        if (produk != null) {
            System.out.println();
            System.out.println("Produk ditemukan.");
            System.out.println("Nama Produk   : " + produk.getNamaProduk());
            System.out.println("Stok Saat Ini : " + produk.getStok());

            int stokBaru = view.inputStok();
            
            boolean berhasil = produkDAO.updateStok(idProduk, stokBaru);
            if (berhasil) {
                System.out.println("Stok berhasil diperbarui di database.");
            } else {
                System.out.println("Gagal memperbarui stok di database.");
            }
        } else {
            System.out.println("Produk dengan ID tersebut tidak ditemukan.");
        }
    }

    @Override
    public void hapusProduk() {
        System.out.println();
        System.out.println("===============================");
        System.out.println("===== HAPUS DATA PRODUKSI =====");
        System.out.println("===============================");
        
        int idProduk = view.inputId();
        
        boolean berhasil = produkDAO.hapus(idProduk);
        if (berhasil) {
            System.out.println("Data produksi berhasil dihapus dari database.");
        } else {
            System.out.println("Produk dengan ID tersebut tidak ditemukan atau gagal dihapus.");
        }
    }
    
    // Overloading mencari berdasarkan ID
    public void cariProduk(int idProduk) {
        System.out.println();
        System.out.println("===== HASIL PENCARIAN =====");

        Produk produk = produkDAO.cariById(idProduk);

        if (produk != null) {
            produk.tampilkanProduk();
        } else {
            System.out.println("Produk dengan ID tersebut tidak ditemukan.");
        }
    }

    // Overloading mencari berdasarkan nama
    public void cariProduk(String namaProduk) {
        System.out.println();
        System.out.println("===== HASIL PENCARIAN =====");

        ArrayList<Produk> hasil = produkDAO.cariByNama(namaProduk);

        if (hasil.isEmpty()) {
            System.out.println("Produk dengan nama tersebut tidak ditemukan.");
        } else {
            for (Produk produk : hasil) {
                System.out.println();
                produk.tampilkanProduk();
            }
        }
    }
}

