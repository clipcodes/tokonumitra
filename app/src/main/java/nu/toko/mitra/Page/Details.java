package nu.toko.mitra.Page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.AppBarLayout;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nu.toko.mitra.Adapter.AdapImages;
import nu.toko.mitra.Adapter.BuyerFeedbackAdapter;
import nu.toko.mitra.Adapter.DescriptionAdapter;
import nu.toko.mitra.Adapter.ProductAdapter;
import nu.toko.mitra.Model.BuyerFeedbackModel;
import nu.toko.mitra.Model.ImagesModel;
import nu.toko.mitra.Model.ProductModel;
import nu.toko.mitra.Model.ProductModelNU;
import nu.toko.mitra.R;
import nu.toko.mitra.Reqs.ReqString;
import nu.toko.mitra.Sqlite.CartDB;
import nu.toko.mitra.Utils.Others;

import static nu.toko.mitra.Utils.Staticvar.BERAT_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.CREATED_AT;
import static nu.toko.mitra.Utils.Staticvar.DESKRIPSI_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.FEEDBACKALL;
import static nu.toko.mitra.Utils.Staticvar.HARGA_ADMIN;
import static nu.toko.mitra.Utils.Staticvar.HARGA_MITRA;
import static nu.toko.mitra.Utils.Staticvar.ID;
import static nu.toko.mitra.Utils.Staticvar.ID_FEEDBACK;
import static nu.toko.mitra.Utils.Staticvar.ID_GAMBAR;
import static nu.toko.mitra.Utils.Staticvar.ID_KATEGORI;
import static nu.toko.mitra.Utils.Staticvar.ID_MITRA;
import static nu.toko.mitra.Utils.Staticvar.ID_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.ID_SUB_KATEGORI;
import static nu.toko.mitra.Utils.Staticvar.ID_TRANSAKSI;
import static nu.toko.mitra.Utils.Staticvar.KOMEN;
import static nu.toko.mitra.Utils.Staticvar.KONDISI_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.NAMA_KATEGORI;
import static nu.toko.mitra.Utils.Staticvar.NAMA_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.NAME;
import static nu.toko.mitra.Utils.Staticvar.PRODUKDETAIL;
import static nu.toko.mitra.Utils.Staticvar.RATING;
import static nu.toko.mitra.Utils.Staticvar.STOK;
import static nu.toko.mitra.Utils.Staticvar.TANGGAL;
import static nu.toko.mitra.Utils.Staticvar.TERJUAL;
import static nu.toko.mitra.Utils.Staticvar.URL_GAMBAR;

public class Details extends AppCompatActivity {

    String TAG = getClass().getSimpleName();

    AppBarLayout appBarLayout;
    ViewPager vpphotos;
    AdapImages adapImages;
    LinearLayout container_toolbar;
    WormDotsIndicator wormDotsIndicator;
    List<BuyerFeedbackModel> buyerFeedbackModelList;

    RecyclerView rvdescription;
    DescriptionAdapter descriptionAdapter;

    RecyclerView rvbuyerfeedback;
    BuyerFeedbackAdapter buyerFeedbackAdapter;

    ProductAdapter product3Adapter;

    CardView edit;

    TextView productname, price, title, totalfeedback, totalterjual, kondisi
            , stok, kategori, pengirimanmitra, deskripsi;
    String kategoriname, kategoriid, subkategoriname, subkategoriid;

    List<ImagesModel> imagesModels;

    String idproduk, produknama;
    int harga;
    String[] deskripsiarr = {"a-v"};

    RequestQueue requestQueue;
    ReqString reqString;

    ProductModelNU pnu;
    RatingBar star;

    String gambararr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Others.MahathirOptionGambar(getApplicationContext());
        idproduk = getIntent().getStringExtra(ID_PRODUK);
        harga = getIntent().getIntExtra(HARGA_MITRA, 0);
        produknama = getIntent().getStringExtra(NAMA_PRODUK);

        init();

        reqString.go(suksesfeedback, FEEDBACKALL+idproduk);
    }

    void init(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        reqString = new ReqString(this, requestQueue);
        appBarLayout = findViewById(R.id.appBarLayout);
        container_toolbar = findViewById(R.id.container_toolbar);
//        favorites = findViewById(R.id.favorites);
        edit = findViewById(R.id.edit);
        imagesModels = new ArrayList<>();
        productname = findViewById(R.id.productname);
        price = findViewById(R.id.price);
        title = findViewById(R.id.title);
        star = findViewById(R.id.star);
        kondisi = findViewById(R.id.kondisi);
        stok = findViewById(R.id.stok);
        kategori = findViewById(R.id.kategori);
        pengirimanmitra = findViewById(R.id.pengirimanmitra);
        deskripsi = findViewById(R.id.deskripsi);
        pnu = new ProductModelNU();

        totalfeedback = findViewById(R.id.totalfeedback);
        totalterjual = findViewById(R.id.totalterjual);
        productname.setText(produknama);
        price.setText("Rp."+ Others.PercantikHarga(harga));
        title.setText(produknama);


        //Viewpager Photo Product
        vpphotos = findViewById(R.id.vpphotos);
        adapImages = new AdapImages(this, imagesModels);
        vpphotos.setAdapter(adapImages);
        wormDotsIndicator = findViewById(R.id.wormDotsIndicator);
        wormDotsIndicator.setViewPager(vpphotos);

        //RecyclerView List of Description Product
        rvdescription = findViewById(R.id.rvdescription);
        rvdescription.setLayoutManager(new LinearLayoutManager(Details.this, LinearLayoutManager.VERTICAL, false));

//        rvmore = findViewById(R.id.rvmore);
//        product3Adapter = new ProductAdapter(this, ProductModel.DataWomen());
//        rvmore.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false));
//        rvmore.setAdapter(product3Adapter);

        buyerFeedbackModelList = new ArrayList<>();
        rvbuyerfeedback = findViewById(R.id.rvbuyerfeedback);
        buyerFeedbackAdapter = new BuyerFeedbackAdapter(this, buyerFeedbackModelList);
        rvbuyerfeedback.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvbuyerfeedback.setAdapter(buyerFeedbackAdapter);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > 400){
                    container_toolbar.setVisibility(View.VISIBLE);
                } else {
                    container_toolbar.setVisibility(View.INVISIBLE);
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddProduct.class);
                i.putExtra(ID_PRODUK, pnu.getId_produk());
                i.putExtra(ID_MITRA, pnu.getId_mitra());
                i.putExtra(HARGA_MITRA, pnu.getHarga_mitra());
                i.putExtra(NAMA_PRODUK, pnu.getNama_produk());
                i.putExtra(DESKRIPSI_PRODUK, pnu.getDeskripsi_produk());
                i.putExtra(ID_SUB_KATEGORI, pnu.getId_sub_kategori());
                i.putExtra(BERAT_PRODUK, pnu.getBerat_produk());
                i.putExtra(KONDISI_PRODUK, pnu.getKondisi_produk());
                i.putExtra(STOK, pnu.getStok());
                i.putExtra(ID_KATEGORI, kategoriid);
                i.putExtra(NAMA_KATEGORI, kategoriname);
                i.putExtra(ID_SUB_KATEGORI, subkategoriid);
                i.putExtra("namasubkategori", subkategoriname);
                i.putExtra("gambararr", gambararr);
                startActivity(i);
            }
        });
    }

    private Response.Listener<String> suksesproduct = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse: "+response);
            try {
                JSONObject jsonObject = new JSONObject(response);

                totalterjual.setText("  "+jsonObject.getString("totalfeedback")+" Reviews | "+jsonObject.getString("terjual")+" Terjual");
                star.setRating(Float.valueOf(jsonObject.getString("rating")));
                kondisi.setText(jsonObject.getString("kondisi_produk"));
                stok.setText(jsonObject.getString("stok"));

                JSONObject kategorijson = new JSONObject(jsonObject.getString("kategori"));
                kategoriname = kategorijson.getString("nama_kategori");
                kategoriid = kategorijson.getString("id_kategori");

                JSONObject subkategorijson = new JSONObject(jsonObject.getString("subkategori"));
                subkategoriname = subkategorijson.getString("nama_kategori");
                subkategoriid = subkategorijson.getString("id_sub_kategori");

                JSONObject mitrajson = new JSONObject(jsonObject.getString("mitra"));
                pengirimanmitra.setText(mitrajson.getString("kabupaten_mitra"));

                deskripsiarr = jsonObject.getString("deskripsi_produk").split("-");
                descriptionAdapter = new DescriptionAdapter(Details.this, deskripsiarr);
                rvdescription.setAdapter(descriptionAdapter);
                deskripsi.setVisibility(View.GONE);

                JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("gambar").toString());

                gambararr = jsonObject.getString("gambar");

                pnu.setGambarfirst(jsonArray.getJSONObject(0).getString(URL_GAMBAR));
                pnu.setId_mitra(mitrajson.getString(ID_MITRA));
                pnu.setId_produk(jsonObject.getString(ID_PRODUK));
                pnu.setNama_produk(jsonObject.getString(NAMA_PRODUK));
                pnu.setDeskripsi_produk(jsonObject.getString(DESKRIPSI_PRODUK));
                pnu.setId_sub_kategori(subkategoriid);
                pnu.setBerat_produk(jsonObject.getString(BERAT_PRODUK));
                pnu.setKondisi_produk(jsonObject.getString(KONDISI_PRODUK));
                pnu.setTerjual(jsonObject.getString(TERJUAL));
                pnu.setStok(jsonObject.getString(STOK));
                pnu.setHarga_mitra(jsonObject.getInt(HARGA_MITRA));
                pnu.setHarga_admin(jsonObject.getInt(HARGA_ADMIN));
                pnu.setCreated_at(jsonObject.getString(CREATED_AT));
                pnu.setQty(1);

                for (int g = 0; g < jsonArray.length(); g++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(g);
                    ImagesModel imagesModel = new ImagesModel();
                    imagesModel.setId_gambar(jsonObject1.getString(ID_GAMBAR));
                    imagesModel.setId_produk(jsonObject1.getString(ID_PRODUK));
                    imagesModel.setUrl_gambar(jsonObject1.getString(URL_GAMBAR));
                    imagesModels.add(imagesModel);
                }

                adapImages.notifyDataSetChanged();
            } catch (JSONException e){
                Log.i(TAG, "onResponse: ER"+e.getMessage());
            }
        }
    };


    Response.Listener<String> suksesfeedback = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse suksesfeedback: "+response);
            try {
                JSONArray jsonArray = new JSONArray(response);
                if (jsonArray.length()>0){
                    findViewById(R.id.feedbackcontainer).setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    BuyerFeedbackModel fm = new BuyerFeedbackModel();
                    fm.setId_feedback(object.getInt(ID_FEEDBACK));
                    fm.setId_produk(object.getInt(ID_PRODUK));
                    fm.setId_transaksi(object.getInt(ID_TRANSAKSI));
                    fm.setKomen(object.getString(KOMEN));
                    fm.setRating(object.getDouble(RATING));
                    fm.setTanggal(object.getString(TANGGAL));
                    JSONObject pembeli = object.getJSONObject("pembeli");
                    fm.setNama(pembeli.getString(NAME));
                    fm.setId_user_pembeli(pembeli.getInt(ID));

                    buyerFeedbackModelList.add(fm);
                }

                buyerFeedbackAdapter.notifyDataSetChanged();
            } catch (JSONException e){
                Log.i(TAG, "onResponse: "+e.getMessage());
            }
        }
    };

    @Override
    protected void onStart() {
        reqString.go(suksesproduct, PRODUKDETAIL+idproduk);
        super.onStart();
    }
}
