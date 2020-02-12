package nu.toko.mitra.Page;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nu.toko.mitra.Adapter.CategoriesAdapter;
import nu.toko.mitra.Adapter.Product2Adapter;
import nu.toko.mitra.Model.CategoriesModelNU;
import nu.toko.mitra.Model.ProductModelNU;
import nu.toko.mitra.R;
import nu.toko.mitra.Reqs.ReqString;

import static nu.toko.mitra.Utils.Staticvar.BERAT_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.DESKRIPSI_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.HARGA_ADMIN;
import static nu.toko.mitra.Utils.Staticvar.HARGA_MITRA;
import static nu.toko.mitra.Utils.Staticvar.ID_KATEGORI;
import static nu.toko.mitra.Utils.Staticvar.ID_MITRA;
import static nu.toko.mitra.Utils.Staticvar.ID_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.ID_SUBKATEGORI;
import static nu.toko.mitra.Utils.Staticvar.ID_SUB_KATEGORI;
import static nu.toko.mitra.Utils.Staticvar.KONDISI_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.NAMA_KATEGORI;
import static nu.toko.mitra.Utils.Staticvar.NAMA_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.PRODUCTKATEGORI;
import static nu.toko.mitra.Utils.Staticvar.PRODUCTSUBKATEGORI;
import static nu.toko.mitra.Utils.Staticvar.STATUSBYPAGE;
import static nu.toko.mitra.Utils.Staticvar.STOK;
import static nu.toko.mitra.Utils.Staticvar.SUBKATEGORI;
import static nu.toko.mitra.Utils.Staticvar.TERJUAL;
import static nu.toko.mitra.Utils.Staticvar.URL_GAMBAR;

public class Categories extends AppCompatActivity {

    String TAG = getClass().getSimpleName();
    RecyclerView rvcategories, rvsubcategories;
    Product2Adapter product2Adapter;
    List<ProductModelNU> productModelList;
    List<CategoriesModelNU> categoriesModelList;
    CategoriesAdapter categoriesAdapter;
    TextView categoriesname;
    String CATEGORY;
    int IDCATEGORY;
    boolean isLoadData = false;
    int PAGE = 1;
    ReqString reqString;
    NestedScrollView nested;
    RequestQueue requestQueue;
    EditText pencarian;
    TextView listdatatex;
    FrameLayout kembali;
    AVLoadingIndicatorView loadingbot;
    String URLFLEKSIBEL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_categories);

        IDCATEGORY = getIntent().getIntExtra("id", 0);
        CATEGORY = getIntent().getStringExtra("kat");
        init();
        URLFLEKSIBEL = PRODUCTKATEGORI+IDCATEGORY;
        reqString.go(suksesproducthome, URLFLEKSIBEL);
        reqString.go(suksessubkategori, SUBKATEGORI+IDCATEGORY);

    }

    void init(){
        productModelList = new ArrayList<>();
        rvcategories = findViewById(R.id.rvcategories);
        product2Adapter = new Product2Adapter(this, productModelList);
        rvcategories.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false));
        rvcategories.setAdapter(product2Adapter);
        categoriesname = findViewById(R.id.categoriesname);
        loadingbot = findViewById(R.id.lodingbot);
        listdatatex = findViewById(R.id.listdata);
        kembali = findViewById(R.id.kembali);
        pencarian = findViewById(R.id.pencarian);
        pencarian.setHint("Cari "+CATEGORY);
        listdatatex.setText("Semua "+CATEGORY);
        categoriesname.setText("Sub "+CATEGORY);
        nested = findViewById(R.id.nested);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        reqString = new ReqString(this, requestQueue);

        rvsubcategories = findViewById(R.id.rvsubcategories);
        categoriesModelList = new ArrayList<>();
        categoriesAdapter = new CategoriesAdapter(this, categoriesModelList, 2);
        rvsubcategories.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false));
        rvsubcategories.setAdapter(categoriesAdapter);
        categoriesAdapter.setOnItemClickListener(new CategoriesAdapter.OnClick() {
            @Override
            public void onItemClick(CategoriesModelNU categoriesModelNU) {
                Log.i(TAG, "onItemClick: "+categoriesModelNU.getNama_kategori());
                productModelList.clear();
                PAGE = 1;
                listdatatex.setText(CATEGORY+" "+categoriesModelNU.getNama_kategori());
                product2Adapter.notifyDataSetChanged();
                URLFLEKSIBEL = PRODUCTSUBKATEGORI+categoriesModelNU.getId_sub_kategori();
                reqString.go(suksesproducthome, URLFLEKSIBEL);
            }
        });
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        onscroll();
    }

    private void onscroll(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            nested.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if(v.getChildAt(v.getChildCount() - 1) != null) {
                        if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) && scrollY > oldScrollY && isLoadData) {
                            Log.i("CEK", "Scroll Down");
                            isLoadData = false;
                            loadingbot.show();
                            reqString.go(suksesproducthome, URLFLEKSIBEL+STATUSBYPAGE+PAGE);
                        }
                    }
                }
            });
        }
    }

    private Response.Listener<String> suksesproducthome = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse: "+response);
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    ProductModelNU pnu = new ProductModelNU();
                    pnu.setId_produk(object.getString(ID_PRODUK));
                    pnu.setNama_produk(object.getString(NAMA_PRODUK));
                    pnu.setHarga_admin(object.getInt(HARGA_ADMIN));
                    pnu.setHarga_mitra(object.getInt(HARGA_MITRA));
                    pnu.setId_mitra(object.getString(ID_MITRA));
                    pnu.setDeskripsi_produk(object.getString(DESKRIPSI_PRODUK));
                    pnu.setId_sub_kategori(object.getString(ID_SUB_KATEGORI));
                    pnu.setKondisi_produk(object.getString(KONDISI_PRODUK));
                    pnu.setStok(object.getString(STOK));
                    pnu.setTerjual(object.getString(TERJUAL));
                    pnu.setBerat_produk(object.getString(BERAT_PRODUK));

                    JSONArray jsonArray1 = new JSONArray(object.getJSONArray("gambar").toString());
                    if (jsonArray1.length() >= 1){
                        JSONObject j = jsonArray1.getJSONObject(0);
                        pnu.setGambarfirst(j.getString(URL_GAMBAR));
                    }

                    productModelList.add(pnu);
                    product2Adapter.notifyItemInserted(-1);
                }

                isLoadData = true;
                loadingbot.hide();
                PAGE+= 1;
            } catch (JSONException e){
                Log.i("Error", "onResponse: Err:"+e.getMessage());
            }
        }
    };

    private Response.Listener<String> suksessubkategori = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse: "+response);
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    CategoriesModelNU categoriesModelNU = new CategoriesModelNU();
                    categoriesModelNU.setId_kategori(object.getInt(ID_KATEGORI));
                    categoriesModelNU.setNama_kategori(object.getString(NAMA_KATEGORI));
                    categoriesModelNU.setId_sub_kategori(object.getInt(ID_SUBKATEGORI));

                    categoriesModelList.add(categoriesModelNU);
                }

                Log.i(TAG, "onResponse: productModelList size="+productModelList.size());
                categoriesAdapter.notifyDataSetChanged();
            } catch (JSONException e){
                Log.i(TAG, "onResponse: Err:"+e.getMessage());
            }
        }
    };
}
