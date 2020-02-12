package nu.toko.mitra.Page;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import nu.toko.mitra.Adapter.PhotoAddAdap;
import nu.toko.mitra.Adapter.Product2Adapter;
import nu.toko.mitra.Model.NewProductModel;
import nu.toko.mitra.Model.PhotoModel;
import nu.toko.mitra.Model.ProductModelNU;
import nu.toko.mitra.R;
import nu.toko.mitra.Reqs.ReqString;

import static nu.toko.mitra.Utils.Staticvar.BERAT_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.DESCRIPTION;
import static nu.toko.mitra.Utils.Staticvar.DESKRIPSI_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.HARGA_MITRA;
import static nu.toko.mitra.Utils.Staticvar.ID_KATEGORI;
import static nu.toko.mitra.Utils.Staticvar.ID_MITRA;
import static nu.toko.mitra.Utils.Staticvar.ID_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.ID_SUB_KATEGORI;
import static nu.toko.mitra.Utils.Staticvar.KONDISI_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.NAMA_KATEGORI;
import static nu.toko.mitra.Utils.Staticvar.NAMA_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.PRODUKADD;
import static nu.toko.mitra.Utils.Staticvar.PRODUKEDIT;
import static nu.toko.mitra.Utils.Staticvar.STOK;
import static nu.toko.mitra.Utils.Staticvar.URL_GAMBAR;

public class AddProduct extends AppCompatActivity {

    String TAG = getClass().getSimpleName();
    RecyclerView rvphoto;
    PhotoAddAdap photoAddAdap;
    List<PhotoModel> files;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    private static final int KATEGORI = 345;
    private static final int SUBKATEGORI = 3235;
    EditText nama, deskripsi, harga, stok, berat;
    String kondisiselect, kategoriselect, subkategoriselect;
    TextView kategoritex, subkategoritex;
    FrameLayout kategori, subkategori;
    TextView err;
    CardView add;
    Spinner kondisi;
    RequestQueue requestQueue;
    TextView addedittex;
    ArrayList<String> kondisipilihan;
    ProductModelNU productModelNU;
    String gambararr;
    String deletedimage = "";
    boolean edited = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_addproduct);

        init();

        if (getIntent().getStringExtra(ID_PRODUK)!=null){
            edited = true;
            edit();
        }
    }

    void edit(){
        productModelNU = new ProductModelNU();
        productModelNU.setId_produk(getIntent().getStringExtra(ID_PRODUK));
        productModelNU.setId_mitra(getIntent().getStringExtra(ID_MITRA));
        productModelNU.setNama_produk(getIntent().getStringExtra(NAMA_PRODUK));
        productModelNU.setDeskripsi_produk(getIntent().getStringExtra(DESKRIPSI_PRODUK));
        productModelNU.setId_sub_kategori(getIntent().getStringExtra(ID_SUB_KATEGORI));
        productModelNU.setBerat_produk(getIntent().getStringExtra(BERAT_PRODUK));
        productModelNU.setKondisi_produk(getIntent().getStringExtra(KONDISI_PRODUK));
        productModelNU.setStok(getIntent().getStringExtra(STOK));
        productModelNU.setHarga_mitra(getIntent().getIntExtra(HARGA_MITRA, 0));

        nama.setText(productModelNU.getNama_produk());
        deskripsi.setText(productModelNU.getDeskripsi_produk());
        harga.setText(String.valueOf(productModelNU.getHarga_mitra()));
        stok.setText(productModelNU.getStok());
        berat.setText(productModelNU.getBerat_produk());
        subkategori.setVisibility(View.VISIBLE);
        kategoritex.setText("Kategori "+getIntent().getStringExtra(NAMA_KATEGORI));
        subkategoritex.setText("Sub Kategori "+getIntent().getStringExtra("namasubkategori"));
        kategoriselect = getIntent().getStringExtra(ID_KATEGORI);
        subkategoriselect = getIntent().getStringExtra(ID_SUB_KATEGORI);
        if (productModelNU.getKondisi_produk().equals("Bekas")){
            kondisipilihan.add("Bekas");
            kondisipilihan.add("Baru");
        }

        gambararr = getIntent().getStringExtra("gambararr");

        try {
            JSONArray gambar = new JSONArray(gambararr);
            for (int i = 0; i < gambar.length(); i++){
                JSONObject j = gambar.getJSONObject(i);
                PhotoModel p = new PhotoModel();
                p.setEditurl(j.getString(URL_GAMBAR));
                files.add(p);
                photoAddAdap.notifyDataSetChanged();
            }
        } catch (JSONException e){

        }

        addedittex.setText("Simpan");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, kondisipilihan);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kondisi.setAdapter(spinnerArrayAdapter);
    }

    void init(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        files = new ArrayList<>();
        addedittex = findViewById(R.id.addedittex);
        rvphoto = findViewById(R.id.rvphoto);
        photoAddAdap = new PhotoAddAdap(this, files);
        rvphoto.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.HORIZONTAL, false));
        rvphoto.setAdapter(photoAddAdap);

        photoAddAdap.Klik(new PhotoAddAdap.saatKlik() {
            @Override
            public void terKlik() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
                } else {
                    CropImage.startPickImageActivity(AddProduct.this);
                }
            }

            @Override
            public void delete(int pos) {
                files.remove(pos);
                photoAddAdap.notifyDataSetChanged();
            }

            @Override
            public void deleteefit(int pos, String url) {
                deletedimage = deletedimage + url + "-";
                Log.i(TAG, "deleteefit: "+deletedimage.substring(0, deletedimage.length()-1));
                files.remove(pos);
                photoAddAdap.notifyDataSetChanged();
            }
        });

        kategori = findViewById(R.id.kategori);
        kategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), KategoriChooser.class);
                startActivityForResult(i, KATEGORI);
            }
        });

        subkategori = findViewById(R.id.subkategori);
        subkategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SubKategoriChooser.class);
                i.putExtra("id", kategoriselect);
                startActivityForResult(i, SUBKATEGORI);
            }
        });

        subkategoritex = findViewById(R.id.subkategoritex);
        kategoritex = findViewById(R.id.kategoritex);
        err = findViewById(R.id.err);
        nama = findViewById(R.id.nama);
        deskripsi = findViewById(R.id.deskripsi);
        harga = findViewById(R.id.harga);
        stok = findViewById(R.id.stock);
        berat = findViewById(R.id.berat);

        kategoriselect = "Baru";
        kondisipilihan = new ArrayList<>();
        kondisipilihan.add("Baru");
        kondisipilihan.add("Bekas");
        kondisi = findViewById(R.id.kondisi);

        kondisi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kondisiselect = kondisipilihan.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, kondisipilihan);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kondisi.setAdapter(spinnerArrayAdapter);

        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                err.setText("");
                String namap = nama.getText().toString();
                String deskripsip = deskripsi.getText().toString();
                String hargap = harga.getText().toString();
                String stokp = stok.getText().toString();
                String beratp = berat.getText().toString();

                if (files.size() <= 0 && !edited){
                    err.setText("Tambahkan Foto Produk");
                    return;
                }

                if (namap.isEmpty()){
                    err.setText("Isikan Nama Produk");
                    return;
                }

                if (deskripsip.isEmpty()){
                    err.setText("Isikan Deskripsi");
                    return;
                }

                if (kategoriselect.isEmpty()){
                    err.setText("Pilih Kategori");
                    return;
                }

                if (subkategoriselect.isEmpty()){
                    err.setText("Pilih Sub Kategori");
                    return;
                }

                if (hargap.isEmpty()){
                    err.setText("Isikan Harga");
                    return;
                }

                if (kondisiselect.isEmpty()){
                    err.setText("Pilih Kondisi Barang");
                    return;
                }

                if (stokp.isEmpty()){
                    err.setText("Isikan Stok");
                    return;
                }

                if (beratp.isEmpty()){
                    err.setText("Isikan Berat");
                    return;
                }

                NewProductModel adds = new NewProductModel();
                adds.setNama(namap);
                adds.setDeskripsi(deskripsip);
                adds.setBerat(beratp);
                adds.setHarga(hargap);
                adds.setKondisi(kondisiselect);
                adds.setKategori(kategoriselect);
                adds.setSubkategori(subkategoriselect);
                adds.setStok(stokp);
                adds.setUris(files);

                add.setCardBackgroundColor(getResources().getColor(R.color.white));
                findViewById(R.id.progress).setVisibility(View.VISIBLE);
                findViewById(R.id.addedittex).setVisibility(View.INVISIBLE);

                if (edited){
                    adds.setId(productModelNU.getId_produk());
                    if (!deletedimage.isEmpty()){
                        adds.setDeletedphoto(deletedimage.substring(0, deletedimage.length() -1));
                    }
                    new ReqString(AddProduct.this, requestQueue).editproduk(editrespon, adds, PRODUKEDIT+adds.getId());
                } else {
                    new ReqString(AddProduct.this, requestQueue).addproduk(addrespon, adds, PRODUKADD);
                }

            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    Response.Listener<String> addrespon = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse: "+response);
            try {
                JSONObject object = new JSONObject(response);
                Intent i = new Intent(getApplicationContext(), Details.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
                i.putExtra(ID_PRODUK, object.getString(ID_PRODUK));
                i.putExtra(HARGA_MITRA, object.getString(HARGA_MITRA));
                i.putExtra(NAMA_PRODUK, object.getString(NAMA_PRODUK));
                startActivity(i);
                finish();
            } catch (JSONException e){
                Log.i(TAG, "onResponse: "+e.getMessage());
            }
        }
    };

    Response.Listener<String> editrespon = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse: "+response);
            try {
                JSONObject object = new JSONObject(response);
                Intent i = new Intent(getApplicationContext(), Details.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
                i.putExtra(ID_PRODUK, object.getString(ID_PRODUK));
                i.putExtra(HARGA_MITRA, object.getString(HARGA_MITRA));
                i.putExtra(NAMA_PRODUK, object.getString(NAMA_PRODUK));
                startActivity(i);
                finish();
            } catch (JSONException e){
                Log.i(TAG, "onResponse: "+e.getMessage());
            }
        }
    };

    private void Ngecorep(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setRequestedSize(1000, 1200)
                .setAspectRatio(6,8)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            CropImage.startPickImageActivity(this);
        }
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                Ngecorep(imageUri);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                files.add(new PhotoModel(result.getUri()));
                photoAddAdap.notifyDataSetChanged();
            }
        }

        if (requestCode == KATEGORI) {
            if(resultCode == Activity.RESULT_OK){
                kategoritex.setText("Kategori "+data.getStringExtra("kategori"));
                kategoriselect = String.valueOf(data.getIntExtra("id", 0));
                subkategoritex.setText("Pilih Sub Kategori");
                subkategoriselect = "";
                subkategori.setVisibility(View.VISIBLE);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

        if (requestCode == SUBKATEGORI) {
            if(resultCode == Activity.RESULT_OK){
                subkategoritex.setText("Sub Kategori "+data.getStringExtra("subkategori"));
                subkategoriselect = String.valueOf(data.getIntExtra("id", 0));
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

}