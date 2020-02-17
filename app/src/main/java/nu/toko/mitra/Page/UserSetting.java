package nu.toko.mitra.Page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nu.toko.mitra.Model.KotaModel;
import nu.toko.mitra.Model.ProvModel;
import nu.toko.mitra.Model.UserMitraModel;
import nu.toko.mitra.R;
import nu.toko.mitra.Reqs.ReqString;
import nu.toko.mitra.Reqs.UserReqs;
import nu.toko.mitra.Utils.UserPrefs;

import static nu.toko.mitra.Utils.Staticvar.USER_EDIT;

public class UserSetting extends AppCompatActivity {

    EditText email_mitra, nama_mitra, nik_mitra, nama_toko_mitra, alamat_toko_mitra, no_rekening_mitra, deskripsi_toko_mitra, no_telp_mitra, kecamatan_mitra, kode_pos_mitra;
    TextView err;
    TextView gotex;
    ProgressBar progress;
    TextView provinsi_mitra, kabupaten_mitra;
    RequestQueue requestQueue;
    CardView save;
    String TAG = getClass().getSimpleName();
    ArrayList<String> PROVNAME;
    ArrayList<String> PROVID;
    ReqString reqString;
    String idprovterpilih = null;
    String kabterpilih = "";
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_setting);

        init();

    }
    
    void init(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        reqString = new ReqString(this, requestQueue);
        PROVNAME = new ArrayList<>();
        PROVID = new ArrayList<>();
        save = findViewById(R.id.save);
        email_mitra = findViewById(R.id.email_mitra);
        progress = findViewById(R.id.progress);
        gotex = findViewById(R.id.gotex);
        err = findViewById(R.id.err);
        kode_pos_mitra = findViewById(R.id.kode_pos_mitra);
        nama_mitra = findViewById(R.id.nama_mitra);
        no_telp_mitra = findViewById(R.id.no_telp_mitra);
        provinsi_mitra = findViewById(R.id.provinsi_mitra);
        kecamatan_mitra = findViewById(R.id.kecamatan_mitra);
        kabupaten_mitra = findViewById(R.id.kabupaten_mitra);
        nama_toko_mitra = findViewById(R.id.nama_toko_mitra);
        alamat_toko_mitra = findViewById(R.id.alamat_toko_mitra);
        no_rekening_mitra = findViewById(R.id.no_rekening_mitra);
        deskripsi_toko_mitra = findViewById(R.id.deskripsi_toko_mitra);
        nik_mitra = findViewById(R.id.nik_mitra);

        findViewById(R.id.pilihprov).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PageProv.class);
                startActivityForResult(i, 121);
            }
        });

        findViewById(R.id.pilihkabkot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idprovterpilih==null){
                    Toast.makeText(getApplicationContext(), "Pilih Provinsi Dahulu", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(getApplicationContext(), PageKota.class);
                i.putExtra("idprov", idprovterpilih);
                startActivityForResult(i, 131);
            }
        });

        email_mitra.setText(UserPrefs.getEmail(getApplicationContext()));
        nama_mitra.setText(UserPrefs.getNama(getApplicationContext()));

        Log.i(TAG, "init: "+UserPrefs.getProvinsi(getApplicationContext()).equals("0"));

        if (!UserPrefs.getNo_telp(getApplicationContext()).equals("0")){
            no_telp_mitra.setText(UserPrefs.getNo_telp(getApplicationContext()));
        }
        if (!UserPrefs.getProvinsi(getApplicationContext()).equals("0")){
            provinsi_mitra.setText(UserPrefs.getProvinsi(getApplicationContext()));
        }
        if (!UserPrefs.getKecamatan(getApplicationContext()).equals("0")){
            kecamatan_mitra.setText(UserPrefs.getKecamatan(getApplicationContext()));
        }
        if (!UserPrefs.getNamakab(getApplicationContext()).equals("0")){
            kabupaten_mitra.setText(UserPrefs.getNamakab(getApplicationContext()));
        }
        if (!UserPrefs.getKode_pos(getApplicationContext()).equals("0")){
            kode_pos_mitra.setText(UserPrefs.getKode_pos(getApplicationContext()));
        }
        if (!UserPrefs.getAlamat_toko(getApplicationContext()).equals("0")){
            alamat_toko_mitra.setText(UserPrefs.getAlamat_toko(getApplicationContext()));
        }
        if (!UserPrefs.getRekening(getApplicationContext()).equals("0")){
            no_rekening_mitra.setText(UserPrefs.getRekening(getApplicationContext()));
        }
        if (!UserPrefs.getDeskripsi_toko(getApplicationContext()).equals("0")){
            deskripsi_toko_mitra.setText(UserPrefs.getDeskripsi_toko(getApplicationContext()));
        }
        if (!UserPrefs.getNamatoko(getApplicationContext()).equals("0")){
            nama_toko_mitra.setText(UserPrefs.getNamatoko(getApplicationContext()));
        }
        if (!UserPrefs.getNik(getApplicationContext()).equals("0")){
            nik_mitra.setText(UserPrefs.getNik(getApplicationContext()));
        }

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserMitraModel usr = new UserMitraModel();
                usr.setEmail_mitra(email_mitra.getText().toString());
                usr.setProvinsi_mitra(provinsi_mitra.getText().toString());
                usr.setKabupaten_mitra(kabterpilih);
                usr.setKecamatan_mitra(kecamatan_mitra.getText().toString());
                usr.setNama_toko_mitra(nama_toko_mitra.getText().toString());
                usr.setNama_mitra(nama_mitra.getText().toString());
                usr.setAlamat_toko_mitra(alamat_toko_mitra.getText().toString());
                usr.setNo_telp_mitra(no_telp_mitra.getText().toString());
                usr.setDeskripsi_toko_mitra(deskripsi_toko_mitra.getText().toString());
                usr.setKode_pos_mitra(kode_pos_mitra.getText().toString());
                usr.setNo_rekening_mitra(no_rekening_mitra.getText().toString());
                usr.setDeskripsi_toko_mitra(deskripsi_toko_mitra.getText().toString());
                usr.setNik_mitra(nik_mitra.getText().toString());

                if (usr.getEmail_mitra().isEmpty()){
                    err.setText("Isikan Email");
                    return;
                }

                if (usr.getNama_mitra().isEmpty()){
                    err.setText("Isikan Nama Lengkap");
                    return;
                }

                if (usr.getNama_toko_mitra().isEmpty()){
                    err.setText("Isikan Nama Toko");
                    return;
                }

                if (usr.getNik_mitra().isEmpty()){
                    err.setText("Isikan NIK Mitra");
                    return;
                }

                if (usr.getDeskripsi_toko_mitra().isEmpty()){
                    err.setText("Isikan Deskripsi");
                    return;
                }

                if (usr.getNo_telp_mitra().isEmpty()){
                    err.setText("Isikan Nomor Anda");
                    return;
                }

                if (usr.getNo_rekening_mitra().isEmpty()){
                    err.setText("Isikan Nomor Rekening");
                    return;
                }

                if (usr.getProvinsi_mitra().contains("Pilih")){
                    err.setText("Pilih Provinsi Tinggal");
                    return;
                }

                if (kabterpilih.contains("Pilih")){
                    err.setText("Pilih Kota/Kab Tinggal");
                    return;
                }

                if (usr.getAlamat_toko_mitra().isEmpty()){
                    err.setText("Isikan Alamat Toko");
                    return;
                }

                if (usr.getKecamatan_mitra().isEmpty()){
                    err.setText("Isikan Kecamatan Tinggal");
                    return;
                }

                go(true);

                new UserReqs(UserSetting.this, requestQueue).daftar(res, usr, null, USER_EDIT+UserPrefs.getId(getApplicationContext()));
            }
        });
    }

    void go(boolean go){
        if (go){
            save.setCardBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));
            progress.setVisibility(View.VISIBLE);
            gotex.setVisibility(View.INVISIBLE);
            err.setText("");
        } else {
            save.setCardBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimary));
            progress.setVisibility(View.INVISIBLE);
            gotex.setVisibility(View.VISIBLE);
        }
    }

    private Response.Listener<String> res = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("RESPON", response);
            try {
                JSONObject object = new JSONObject(response);
                if (response.contains("message")){
                    err.setText(object.getString("message"));
                    go(false);
                    return;
                }
                UserPrefs.setId(object.getString("id"), getApplicationContext());
                UserPrefs.setAlamat(object.getString("alamat"), getApplicationContext());
                UserPrefs.setEmail(object.getString("email"), getApplicationContext());
                UserPrefs.setNama(object.getString("name"), getApplicationContext());
                UserPrefs.setNo_telp(object.getString("hp"), getApplicationContext());
                UserPrefs.setProvinsi(object.getString("provinsi"), getApplicationContext());
                UserPrefs.setKabupaten(object.getString("kabupaten"), getApplicationContext());
                UserPrefs.setKecamatan(object.getString("kecamatan"), getApplicationContext());
                UserPrefs.setKode_pos(object.getString("kode_pos"), getApplicationContext());
                UserPrefs.setUrl_profil(object.getString("url_profil"), getApplicationContext());
                UserPrefs.setNamakab(object.getString("namakota"), getApplicationContext());
                UserPrefs.setNamatoko(object.getString("nama_toko_mitra"), getApplicationContext());

                go(false);

                Toast.makeText(UserSetting.this, "Tersimpan", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                Log.i("RESPON EROR", e.getMessage());
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 121){
                ProvModel model = new ProvModel();
                model.setNama_provinsi(data.getStringExtra("nama"));
                model.setProvinsi_id(data.getStringExtra("id"));
                provinsi_mitra.setText(data.getStringExtra("nama"));
                idprovterpilih = data.getStringExtra("id");
            }
            if (requestCode == 131){
                KotaModel model = new KotaModel();
                model.setNama_kota(data.getStringExtra("nama"));
                model.setKota_id(data.getStringExtra("id"));
                kabupaten_mitra.setText(data.getStringExtra("nama"));
                kabterpilih = data.getStringExtra("id");
            }
        }
    }
}
