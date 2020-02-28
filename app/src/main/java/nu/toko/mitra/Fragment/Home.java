package nu.toko.mitra.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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

import nu.toko.mitra.Adapter.Product2Adapter;
import nu.toko.mitra.Model.ProductModelNU;
import nu.toko.mitra.Model.UserMitra;
import nu.toko.mitra.Model.UserMitraModel;
import nu.toko.mitra.Page.AddProduct;
import nu.toko.mitra.R;
import nu.toko.mitra.Reqs.ReqString;
import nu.toko.mitra.Utils.Others;
import nu.toko.mitra.Utils.UserPrefs;

import static nu.toko.mitra.Utils.Staticvar.BERAT_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.DESKRIPSI_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.DIKIRIMDARI;
import static nu.toko.mitra.Utils.Staticvar.DISKON;
import static nu.toko.mitra.Utils.Staticvar.HARGA_ADMIN;
import static nu.toko.mitra.Utils.Staticvar.HARGA_MITRA;
import static nu.toko.mitra.Utils.Staticvar.ID_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.ID_SUB_KATEGORI;
import static nu.toko.mitra.Utils.Staticvar.KONDISI_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.NAMA_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.PRODUCTMITRA;
import static nu.toko.mitra.Utils.Staticvar.RATING;
import static nu.toko.mitra.Utils.Staticvar.SLASH;
import static nu.toko.mitra.Utils.Staticvar.STATUSBYPAGE;
import static nu.toko.mitra.Utils.Staticvar.STOK;
import static nu.toko.mitra.Utils.Staticvar.TERJUAL;
import static nu.toko.mitra.Utils.Staticvar.TOTALFEEDBACK;
import static nu.toko.mitra.Utils.Staticvar.URL_GAMBAR;

public class Home extends Fragment {

    String TAG = getClass().getSimpleName();
    Product2Adapter product2Adapter;
    List<ProductModelNU> productModelList;
    RecyclerView rvproduct;
    boolean isLoadData = false;
    ReqString reqString;
    int PAGE = 1;
    NestedScrollView nested;
    AVLoadingIndicatorView loadingbot;
    RequestQueue requestQueue;
    int STATUS = 1;
    FrameLayout dicline, waiting, published;
    View indicline, inwaiting, inpublished;
    int colorprimary, colorwhite;
    View roots;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.page_home, container, false);

        roots = root;
        Others.MahathirOptionGambar(getActivity());
        init(root);

        return root;
    }

    @Override
    public void onStart() {
        productModelList.clear();
        product2Adapter.notifyDataSetChanged();
        reqString.go(suksesproducthome, PRODUCTMITRA+UserPrefs.getId(getActivity())+SLASH+STATUS);
        super.onStart();
    }

    void init(View root){
        requestQueue = Volley.newRequestQueue(getActivity());
        productModelList = new ArrayList<>();
        product2Adapter = new Product2Adapter(getActivity(), productModelList);
        reqString = new ReqString(getActivity(), requestQueue);
        nested = root.findViewById(R.id.nested);

        dicline = root.findViewById(R.id.dicline);
        waiting = root.findViewById(R.id.waiting);
        published = root.findViewById(R.id.published);
        dicline.setOnClickListener(new kliktab());
        waiting.setOnClickListener(new kliktab());
        published.setOnClickListener(new kliktab());

        indicline = root.findViewById(R.id.indicline);
        inpublished = root.findViewById(R.id.inpublished);
        inwaiting = root.findViewById(R.id.inwaiting);

        rvproduct = root.findViewById(R.id.rvproduct);
        rvproduct.setLayoutManager(new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false));
        rvproduct.setAdapter(product2Adapter);

        loadingbot = root.findViewById(R.id.lodingbot);
        loadingbot.hide();

        colorprimary = getActivity().getResources().getColor(R.color.colorPrimary);
        colorwhite = getActivity().getResources().getColor(R.color.white);

        onscroll();

        root.findViewById(R.id.tambahproduk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddProduct.class);
                startActivity(i);
            }
        });
    }

    public class kliktab implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            PAGE = 1;
            switch (v.getId()){
                case R.id.dicline:
                    inpublished.setBackgroundColor(colorwhite);
                    inwaiting.setBackgroundColor(colorwhite);
                    indicline.setBackgroundColor(colorprimary);
                    STATUS = 2;
                    break;
                case R.id.waiting:
                    inpublished.setBackgroundColor(colorwhite);
                    inwaiting.setBackgroundColor(colorprimary);
                    indicline.setBackgroundColor(colorwhite);
                    STATUS = 0;
                    break;
                case R.id.published:
                    inpublished.setBackgroundColor(colorprimary);
                    inwaiting.setBackgroundColor(colorwhite);
                    indicline.setBackgroundColor(colorwhite);
                    STATUS = 1;
                    break;
            }
            productModelList.clear();
            product2Adapter.notifyDataSetChanged();
            reqString.go(suksesproducthome, PRODUCTMITRA+UserPrefs.getId(getActivity())+SLASH+STATUS);
        }
    }

    private void onscroll(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            nested.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if(v.getChildAt(v.getChildCount() - 1) != null) {
                        if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) && scrollY > oldScrollY && isLoadData) {
                            Log.i("CEK", "Scroll Down "+ PRODUCTMITRA+UserPrefs.getId(getActivity())+SLASH+STATUS+STATUSBYPAGE+PAGE);
                            isLoadData = false;
                            loadingbot.show();
                            reqString.go(suksesproducthome, PRODUCTMITRA+UserPrefs.getId(getActivity())+SLASH+STATUS+STATUSBYPAGE+PAGE);
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
                    pnu.setDeskripsi_produk(object.getString(DESKRIPSI_PRODUK));
                    pnu.setId_sub_kategori(object.getString(ID_SUB_KATEGORI));
                    pnu.setKondisi_produk(object.getString(KONDISI_PRODUK));
                    pnu.setStok(object.getString(STOK));
                    pnu.setTerjual(object.getString(TERJUAL));
                    pnu.setBerat_produk(object.getString(BERAT_PRODUK));
                    pnu.setRating((float)object.getDouble(RATING));
                    pnu.setTotalfeedback(object.getString(TOTALFEEDBACK));
                    pnu.setDikirimdari(object.getString(DIKIRIMDARI));
                    pnu.setDiskon(object.getInt(DISKON));

                    JSONObject mitrajson = new JSONObject(object.getString("mitra"));
                    UserMitra um = new UserMitra();
                    um.setKabupaten_mitra(mitrajson.getString("kabupaten_mitra"));
                    um.setNama_toko_mitra(mitrajson.getString("nama_toko_mitra"));
                    pnu.setKategorimitra(mitrajson.getString("kategori"));
                    pnu.setId_mitra(mitrajson.getString("id_mitra"));

                    pnu.setOwner(um);

                    JSONArray jsonArray1 = new JSONArray(object.getJSONArray("gambar").toString());
                    if (jsonArray1.length() >= 1){
                        JSONObject j = jsonArray1.getJSONObject(0);
                        pnu.setGambarfirst(j.getString(URL_GAMBAR));
                    }

                    productModelList.add(pnu);
                    product2Adapter.notifyItemInserted(productModelList.size());
                    rvproduct.scrollToPosition(productModelList.size());
                }

                if (productModelList.size()<=0){
                    roots.findViewById(R.id.nodata).setVisibility(View.VISIBLE);
                } else {
                    roots.findViewById(R.id.nodata).setVisibility(View.GONE);
                }
                isLoadData = true;
                loadingbot.hide();
                PAGE+= 1;
            } catch (JSONException e){
                Log.i("Error", "onResponse: Err:"+e.getMessage());
            }
        }
    };


}