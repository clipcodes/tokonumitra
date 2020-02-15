package nu.toko.mitra.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import nu.toko.mitra.MainActivity;
import nu.toko.mitra.Model.KotaModel;
import nu.toko.mitra.Model.ProvModel;
import nu.toko.mitra.Model.UserMitraModel;
import nu.toko.mitra.Page.PageKota;
import nu.toko.mitra.Page.PageProv;
import nu.toko.mitra.R;
import nu.toko.mitra.Reqs.UserReqs;
import nu.toko.mitra.Utils.UserPrefs;

import static android.app.Activity.RESULT_OK;
import static nu.toko.mitra.Utils.Staticvar.USER_DAFTAR;

public class SignUp extends Fragment {

    CardView signup;
    ProgressBar loding;
    RequestQueue requestQueue;
    EditText email_mitra, nama_mitra, nama_toko_mitra, nik_mitra, alamat_toko_mitra, no_telp_mitra, no_rekening_mitra, deskripsi_toko_mitra, kecamatan_mitra, kode_pos_mitra, password;
    TextView err;
    TextView gotex, provinsi_mitra, kabupaten_mitra;
    ProgressBar progress;
    String idprovterpilih = null;
    String kabterpilih = "";

    public SignUp() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.users_signup, container, false);

        init(view);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserMitraModel usr = new UserMitraModel();
                usr.setEmail_mitra(email_mitra.getText().toString());
                usr.setProvinsi_mitra(provinsi_mitra.getText().toString());
                usr.setKabupaten_mitra(kabterpilih);
                usr.setKecamatan_mitra(kecamatan_mitra.getText().toString());
                usr.setNama_toko_mitra(nama_toko_mitra.getText().toString());
                usr.setNama_mitra(nama_mitra.getText().toString());
                usr.setNik_mitra(nik_mitra.getText().toString());
                usr.setAlamat_toko_mitra(alamat_toko_mitra.getText().toString());
                usr.setNo_telp_mitra(no_telp_mitra.getText().toString());
                usr.setNo_rekening_mitra(no_rekening_mitra.getText().toString());
                usr.setDeskripsi_toko_mitra(deskripsi_toko_mitra.getText().toString());
                usr.setKode_pos_mitra(kode_pos_mitra.getText().toString());
                usr.setNo_rekening_mitra(no_rekening_mitra.getText().toString());

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

                if (password.getText().toString().isEmpty()){
                    err.setText("Isikan Password");
                    return;
                }

                go(true);

                new UserReqs(getActivity(), requestQueue).daftar(res, usr, password.getText().toString(), USER_DAFTAR);
            }
        });

        return view;
    }

    void go(boolean go){
        if (go){
            signup.setCardBackgroundColor(getActivity().getResources().getColor(R.color.white));
            progress.setVisibility(View.VISIBLE);
            gotex.setVisibility(View.INVISIBLE);
            err.setText("");
        } else {
            signup.setCardBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
            progress.setVisibility(View.INVISIBLE);
            gotex.setVisibility(View.VISIBLE);
        }
    }

    void init(View view){
        requestQueue = Volley.newRequestQueue(getActivity());
        signup = view.findViewById(R.id.signup);
        progress = view.findViewById(R.id.progress);
        gotex = view.findViewById(R.id.gotex);
        err = view.findViewById(R.id.err);
        password = view.findViewById(R.id.password);
        email_mitra = view.findViewById(R.id.email_mitra);
        nama_mitra = view.findViewById(R.id.nama_mitra);
        nama_toko_mitra = view.findViewById(R.id.nama_toko_mitra);
        nik_mitra = view.findViewById(R.id.nik_mitra);
        alamat_toko_mitra = view.findViewById(R.id.alamat_toko_mitra);
        no_telp_mitra = view.findViewById(R.id.no_telp_mitra);
        no_rekening_mitra = view.findViewById(R.id.no_rekening_mitra);
        deskripsi_toko_mitra = view.findViewById(R.id.deskripsi_toko_mitra);
        provinsi_mitra = view.findViewById(R.id.provinsi_mitra);
        kabupaten_mitra = view.findViewById(R.id.kabupaten_mitra);
        kecamatan_mitra = view.findViewById(R.id.kecamatan_mitra);
        kode_pos_mitra = view.findViewById(R.id.kode_pos_mitra);

        view.findViewById(R.id.pilihprov).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PageProv.class);
                startActivityForResult(i, 121);
            }
        });

        view.findViewById(R.id.pilihkabkot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idprovterpilih==null){
                    Toast.makeText(getActivity(), "Pilih Provinsi Dahulu", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(getActivity(), PageKota.class);
                i.putExtra("idprov", idprovterpilih);
                startActivityForResult(i, 131);
            }
        });
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
                UserPrefs.setLogin(getActivity(), true);
                UserPrefs.setId(object.getString("id_mitra"), getActivity());
                UserPrefs.setAlamat_toko(object.getString("alamat_toko_mitra"), getActivity());
                UserPrefs.setEmail(object.getString("email_mitra"), getActivity());
                UserPrefs.setNama(object.getString("nama_mitra"), getActivity());
                UserPrefs.setNo_telp(object.getString("no_telp_mitra"), getActivity());
                UserPrefs.setProvinsi(object.getString("provinsi_mitra"), getActivity());
                UserPrefs.setKabupaten(object.getString("kabupaten_mitra"), getActivity());
                UserPrefs.setKecamatan(object.getString("kecamatan_mitra"), getActivity());
                UserPrefs.setKode_pos(object.getString("kode_pos_mitra"), getActivity());
                UserPrefs.setDeskripsi_toko(object.getString("deskripsi_toko_mitra"), getActivity());
                UserPrefs.setRekening(object.getString("no_rekening_mitra"), getActivity());
                UserPrefs.setNik(object.getString("nik_mitra"), getActivity());
                UserPrefs.setNamakab(object.getString("namakota"), getActivity());
                FirebaseMessaging.getInstance().subscribeToTopic("mitra"+UserPrefs.getId(getActivity()));
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                getActivity().finish();
            } catch (JSONException e) {
                Log.i("RESPON EROR", e.getMessage());
            }
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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