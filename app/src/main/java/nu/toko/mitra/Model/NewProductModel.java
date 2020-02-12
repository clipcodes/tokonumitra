package nu.toko.mitra.Model;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class NewProductModel {

    private String id;
    private String deletedphoto;
    private String nama;
    private String deskripsi;
    private String harga;
    private String kategori;
    private String subkategori;
    private String kondisi;
    private String stok;
    private String berat;
    private List<PhotoModel> uris;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getSubkategori() {
        return subkategori;
    }

    public void setSubkategori(String subkategori) {
        this.subkategori = subkategori;
    }

    public String getKondisi() {
        return kondisi;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getBerat() {
        return berat;
    }

    public void setBerat(String berat) {
        this.berat = berat;
    }

    public List<PhotoModel> getUris() {
        return uris;
    }

    public void setUris(List<PhotoModel> uris) {
        this.uris = uris;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeletedphoto() {
        return deletedphoto;
    }

    public void setDeletedphoto(String deletedphoto) {
        this.deletedphoto = deletedphoto;
    }
}
