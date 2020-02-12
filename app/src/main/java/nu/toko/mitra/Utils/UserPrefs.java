package nu.toko.mitra.Utils;

import android.content.Context;

public class UserPrefs {

    private String id;
    private String email;
    private String nama;
    private String no_telp;
    private String provinsi;
    private String kabupaten;
    private String kecamatan;
    private String kode_pos;
    private String alamat;
    private String toko;
    private String nik;
    private String alamat_toko;
    private String rekening;
    private String deskripsi_toko;

    public static boolean isLogin(Context c) {
        return Boolean.valueOf(Pref.read(c, "login", "false"));
    }

    public static void setLogin(Context c, boolean login) {
        Pref.write(c, "login", String.valueOf(login));
    }

    public static String getEmail(Context c) {
        return Pref.read(c, "email", "false");
    }

    public static void setEmail(String email, Context c) {
        Pref.write(c, "email", email);
    }

    public static String getNama(Context c) {
        return Pref.read(c, "nama", "false");
    }

    public static void setNama(String nama, Context c) {
        Pref.write(c, "nama", nama);
    }

    public static String getNo_telp(Context c) {
        return Pref.read(c, "no_telp", "false");
    }

    public static void setNo_telp(String no_telp, Context c) {
        Pref.write(c, "no_telp", no_telp);
    }

    public static String getProvinsi(Context c) {
        return Pref.read(c, "provinsi", "false");
    }

    public static void setProvinsi(String provinsi, Context c) {
        Pref.write(c, "provinsi", provinsi);
    }

    public static String getKabupaten(Context c) {
        return Pref.read(c, "kabupaten", "false");
    }

    public static void setKabupaten(String kabupaten, Context c) {
        Pref.write(c, "kabupaten", kabupaten);
    }

    public static String getKecamatan(Context c) {
        return Pref.read(c, "kecamatan", "false");
    }

    public static void setKecamatan(String kecamatan, Context c) {
        Pref.write(c, "kecamatan", kecamatan);
    }

    public static String getKode_pos(Context c) {
        return Pref.read(c, "kode_pos", "false");
    }

    public static void setKode_pos(String kode_pos, Context c) {
        Pref.write(c, "kode_pos", kode_pos);
    }

    public static String getAlamat(Context c) {
        return Pref.read(c, "alamat", "false");
    }

    public static void setAlamat(String alamat, Context c) {
        Pref.write(c, "alamat", alamat);
    }

    public static String getToko(Context c) {
        return Pref.read(c, "toko", "false");
    }

    public static void setToko(String toko, Context c) {
        Pref.write(c, "toko", toko);
    }

    public static String getNik(Context c) {
        return Pref.read(c, "nik", "false");
    }

    public static void setNik(String nik, Context c) {
        Pref.write(c, "nik", nik);
    }

    public static String getAlamat_toko(Context c) {
        return Pref.read(c, "alamat_toko", "false");
    }

    public static void setAlamat_toko(String alamat_toko, Context c) {
        Pref.write(c, "alamat_toko", alamat_toko);
    }

    public static String getRekening(Context c) {
        return Pref.read(c, "rekening", "false");
    }

    public static void setRekening(String rekening, Context c) {
        Pref.write(c, "rekening", rekening);
    }

    public static String getDeskripsi_toko(Context c) {
        return Pref.read(c, "deskripsi_toko", "false");
    }

    public static void setDeskripsi_toko(String deskripsi_toko, Context c) {
        Pref.write(c, "deskripsi_toko", deskripsi_toko);
    }

    public static String getId(Context c) {
        return Pref.read(c, "id", "false");
    }

    public static void setId(String id, Context c) {
        Pref.write(c, "id", id);
    }

    public static String getUrl_profil(Context c) {
        return Pref.read(c, "url_profil", "false");
    }

    public static void setUrl_profil(String url_profil, Context c) {
        Pref.write(c, "url_profil", url_profil);
    }
}
