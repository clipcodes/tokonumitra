package nu.toko.mitra.Page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nu.toko.mitra.Adapter.ChooserAdapter;
import nu.toko.mitra.Model.ChooserModel;
import nu.toko.mitra.R;
import nu.toko.mitra.Reqs.ReqString;

import static nu.toko.mitra.Utils.Staticvar.KATEGORI;

public class KategoriChooser extends AppCompatActivity {

    RecyclerView rvpilihan;
    ChooserAdapter adapter;
    List<ChooserModel> chooserModels;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_kategorichooser);

        init();

        new ReqString(this, requestQueue).go(respon, KATEGORI);
    }

    void init(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        rvpilihan = findViewById(R.id.rvpilihan);
        chooserModels = new ArrayList<>();
        adapter = new ChooserAdapter(this, chooserModels);
        rvpilihan.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvpilihan.setAdapter(adapter);
        adapter.setOnBuyClickListener(new ChooserAdapter.OnClickBuy() {
            @Override
            public void onClick(ChooserModel productModel) {
                Intent i = new Intent();
                i.putExtra("kategori", productModel.getPilihan());
                i.putExtra("id", productModel.getId());
                setResult(RESULT_OK, i);
                onBackPressed();
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    Response.Listener<String> respon = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++){
                    JSONObject object = array.getJSONObject(i);
                    ChooserModel chos = new ChooserModel();
                    chos.setId(object.getInt("id_kategori"));
                    chos.setPilihan(object.getString("nama_kategori"));
                    chooserModels.add(chos);
                }

                adapter.notifyDataSetChanged();
            } catch (JSONException e){
                Log.i("Chooser", "onResponse: "+e.getMessage());
            }
        }
    };
}
