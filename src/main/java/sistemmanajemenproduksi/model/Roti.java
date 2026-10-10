package sistemmanajemenproduksi.model;

public class Roti extends Produk {

    private final String rasa;
    private final String ukuranRoti;

    public Roti(int idProduk, String namaProduk, int stok,
                double hargaProduk, int jumlahProduksi,
                String tanggalProduksi, String statusProduksi,
                String rasa, String ukuranRoti) {

        super(idProduk, namaProduk, stok, hargaProduk,
              jumlahProduksi, tanggalProduksi, statusProduksi);

        this.rasa = rasa;
        this.ukuranRoti = ukuranRoti;
    }

    public String getRasa() {
        return rasa;
    }

    public String getUkuranRoti() {
        return ukuranRoti;
    }

    @Override
    public void tampilkanJenisProduk() {

        System.out.println("Jenis Produk     : Roti");
        System.out.println("Rasa             : " + rasa);
        System.out.println("Ukuran           : " + ukuranRoti);
    }

    @Override
    public void tampilkanProduk() {

        super.tampilkanProduk();

        System.out.println("Kategori         : Produk Roti");
    }
}

