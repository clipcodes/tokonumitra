package nu.toko.mitra.Model;

import java.util.Comparator;

import static nu.toko.mitra.Utils.Staticvar.BERAT_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.CREATED_AT;
import static nu.toko.mitra.Utils.Staticvar.DESKRIPSI_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.GAMBARFIRST;
import static nu.toko.mitra.Utils.Staticvar.HARGA_ADMIN;
import static nu.toko.mitra.Utils.Staticvar.HARGA_MITRA;
import static nu.toko.mitra.Utils.Staticvar.ID_MITRA;
import static nu.toko.mitra.Utils.Staticvar.ID_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.ID_SUB_KATEGORI;
import static nu.toko.mitra.Utils.Staticvar.KONDISI_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.NAMA_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.QTY;
import static nu.toko.mitra.Utils.Staticvar.STOK;
import static nu.toko.mitra.Utils.Staticvar.TERJUAL;

public class ProductModelNU {

    public static final String TABLE_NAME = "datatable";

    private String id_produk;
    private String id_mitra;
    private String nama_produk;
    private String deskripsi_produk;
    private String id_sub_kategori;
    private String berat_produk;
    private String kondisi_produk;
    private String terjual;
    private String stok;
    private int harga_mitra;
    private int harga_admin;
    private int qty;
    private String created_at;
    private String gambarfirst;
    private boolean checked;
    private UserMitraModel owner;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + ID_PRODUK + " TEXT,"
                    + ID_MITRA + " TEXT,"
                    + NAMA_PRODUK + " TEXT,"
                    + DESKRIPSI_PRODUK + " TEXT,"
                    + ID_SUB_KATEGORI + " TEXT,"
                    + BERAT_PRODUK + " TEXT,"
                    + KONDISI_PRODUK + " TEXT,"
                    + TERJUAL + " TEXT,"
                    + STOK + " TEXT,"
                    + HARGA_MITRA + " TEXT,"
                    + HARGA_ADMIN + " TEXT,"
                    + CREATED_AT + " TEXT,"
                    + QTY + " TEXT,"
                    + GAMBARFIRST + " TEXT"
                    + ")";

    public ProductModelNU() {
    }

    public ProductModelNU(String id_produk, String id_mitra, String nama_produk, String deskripsi_produk, String id_sub_kategori, String berat_produk, String kondisi_produk, String terjual, String stok, int harga_mitra, int harga_admin, String created_at, int qty, String gambarfirst) {
        this.id_produk = id_produk;
        this.id_mitra = id_mitra;
        this.nama_produk = nama_produk;
        this.deskripsi_produk = deskripsi_produk;
        this.id_sub_kategori = id_sub_kategori;
        this.berat_produk = berat_produk;
        this.kondisi_produk = kondisi_produk;
        this.terjual = terjual;
        this.stok = stok;
        this.harga_mitra = harga_mitra;
        this.harga_admin = harga_admin;
        this.created_at = created_at;
        this.qty = qty;
        this.gambarfirst = gambarfirst;
    }

    public String getId_mitra() {
        return id_mitra;
    }

    public void setId_mitra(String id_mitra) {
        this.id_mitra = id_mitra;
    }

    public String getId_produk() {
        return id_produk;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public String getDeskripsi_produk() {
        return deskripsi_produk;
    }

    public void setDeskripsi_produk(String deskripsi_produk) {
        this.deskripsi_produk = deskripsi_produk;
    }

    public String getId_sub_kategori() {
        return id_sub_kategori;
    }

    public void setId_sub_kategori(String id_sub_kategori) {
        this.id_sub_kategori = id_sub_kategori;
    }

    public String getBerat_produk() {
        return berat_produk;
    }

    public void setBerat_produk(String berat_produk) {
        this.berat_produk = berat_produk;
    }

    public String getKondisi_produk() {
        return kondisi_produk;
    }

    public void setKondisi_produk(String kondisi_produk) {
        this.kondisi_produk = kondisi_produk;
    }

    public String getTerjual() {
        return terjual;
    }

    public void setTerjual(String terjual) {
        this.terjual = terjual;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getGambarfirst() {
        return gambarfirst;
    }

    public void setGambarfirst(String gambarfirst) {
        this.gambarfirst = gambarfirst;
    }

    public int getHarga_mitra() {
        return harga_mitra;
    }

    public void setHarga_mitra(int harga_mitra) {
        this.harga_mitra = harga_mitra;
    }

    public int getHarga_admin() {
        return harga_admin;
    }

    public void setHarga_admin(int harga_admin) {
        this.harga_admin = harga_admin;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public static Comparator<ProductModelNU> MahathirASC = new Comparator<ProductModelNU>() {
        public int compare(ProductModelNU tai, ProductModelNU kucing) {
            return - Integer.valueOf(tai.id_mitra).compareTo(Integer.valueOf(kucing.id_mitra));
        }
    };

    public UserMitraModel getOwner() {
        return owner;
    }

    public void setOwner(UserMitraModel owner) {
        this.owner = owner;
    }
}
