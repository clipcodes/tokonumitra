package nu.toko.mitra.Page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

import nu.toko.mitra.Adapter.CheckoutListAdapter;
import nu.toko.mitra.Model.ProductModelNU;
import nu.toko.mitra.R;
import nu.toko.mitra.Reqs.ReqString;
import nu.toko.mitra.Sqlite.CheckoutDB;
import nu.toko.mitra.Utils.Others;

import static nu.toko.mitra.Utils.Staticvar.CHECKOUT;
import static nu.toko.mitra.Utils.Staticvar.HARGA_ONGKIR;
import static nu.toko.mitra.Utils.Staticvar.HARGA_TOTAL;
import static nu.toko.mitra.Utils.Staticvar.ID_MITRA;
import static nu.toko.mitra.Utils.Staticvar.ID_PEMBELI;
import static nu.toko.mitra.Utils.Staticvar.ID_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.ITEM;
import static nu.toko.mitra.Utils.Staticvar.QTY;
import static nu.toko.mitra.Utils.Staticvar.SUB_TOTAL;

public class Checkout extends AppCompatActivity {

    String TAG = getClass().getSimpleName();
    CheckoutListAdapter checkoutListAdapter;
    RecyclerView rvcartlist;
    ImageView back;
    CheckoutDB checkoutDB;
    int ALAMAT = 992;
    int PAYMETHOD = 5443;
    TextView alamatpengiriman, method, subtotal, biayapengiriman, paytotal, paynowtex;
    FrameLayout address, paymentmethod;
    int subtotalintent, biayakirim;
    ProgressBar progress;
    CardView paynow;
    List<ProductModelNU> productModelNU;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_checkout);

        subtotalintent = getIntent().getIntExtra("subtotal", 0);

        init();

    }

    void init(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        productModelNU = new ArrayList<>();
        checkoutDB = new CheckoutDB(getApplicationContext());
        productModelNU = checkoutDB.getAll();
        rvcartlist = findViewById(R.id.rvcartlist);
        checkoutListAdapter = new CheckoutListAdapter(this, productModelNU);
        address = findViewById(R.id.address);

        progress = findViewById(R.id.progress);
        alamatpengiriman = findViewById(R.id.alamatpengiriman);
        back = findViewById(R.id.back);
        paymentmethod = findViewById(R.id.paymentmethod);
        method = findViewById(R.id.method);
        subtotal = findViewById(R.id.subtotal);
        biayapengiriman = findViewById(R.id.biayapengiriman);
        paytotal = findViewById(R.id.paytotal);
        paynow = findViewById(R.id.paynow);
        paynowtex = findViewById(R.id.paynowtex);

        subtotal.setText("Rp."+Others.PercantikHarga(subtotalintent));
        biayapengiriman.setText("Rp.2.000");
        paytotal.setText("Rp."+Others.PercantikHarga(subtotalintent+biayakirim));

        rvcartlist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvcartlist.setAdapter(checkoutListAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PageAddress.class);
                startActivityForResult(i, ALAMAT);
            }
        });
        paymentmethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PagePaymentMethod.class);
                startActivityForResult(i, PAYMETHOD);
            }
        });
        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paynowtex.setVisibility(View.INVISIBLE);
                progress.setVisibility(View.VISIBLE);
                paynow.setCardBackgroundColor(getResources().getColor(R.color.white));

                ArrayList<String> mitra = new ArrayList<>();
                String id_mitra = "mitra";
                for (int i = 0; i < productModelNU.size(); i++){
                    if (!id_mitra.equals(productModelNU.get(i).getId_mitra())){
                        id_mitra = productModelNU.get(i).getId_mitra();
                        mitra.add(productModelNU.get(i).getId_mitra());
                    }
                }

                try {
                    JSONArray array = new JSONArray();
                    for (int i = 0; i < mitra.size(); i++){
                        JSONObject transaks = new JSONObject();
                        transaks.put(ID_MITRA, mitra.get(i));
                        transaks.put(ID_PEMBELI, "1");
                        JSONArray jsonArray = new JSONArray();
                        int sub_total = 0;
                        int harga_ongkir = 0;
                        int harga_total;
                        for (int l = 0; l < productModelNU.size(); l++){
                            if (productModelNU.get(l).getId_mitra().equals(mitra.get(i))){
                                sub_total += (productModelNU.get(l).getHarga_admin()+productModelNU.get(l).getHarga_mitra())*productModelNU.get(l).getQty();
                                harga_ongkir += 1000;

                                JSONObject object = new JSONObject();
                                object.put(QTY, productModelNU.get(i).getQty());
                                object.put(ID_PRODUK, productModelNU.get(i).getId_produk());
                                jsonArray.put(object);
                            }
                        }
                        harga_total = sub_total+harga_ongkir;
                        transaks.put(SUB_TOTAL, sub_total);
                        transaks.put(HARGA_ONGKIR, harga_ongkir);
                        transaks.put(HARGA_TOTAL, harga_total);
                        transaks.put(ITEM, jsonArray);

                        array.put(transaks);
                    }

                    new ReqString(Checkout.this, requestQueue).pos(responcheckout, array.toString(), CHECKOUT);
                } catch (JSONException e){
                    Log.i(TAG, "createTrans: er "+e.getMessage());
                }
            }
        });
    }

    Response.Listener<String> responcheckout = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse: Berhasil "+ response);
            Intent i = new Intent(getApplicationContext(), Billing.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == ALAMAT){
                alamatpengiriman.setText(data.getStringExtra("prov")+" "+data.getStringExtra("kabkota")+" "+data.getStringExtra("kec")+", "+data.getStringExtra("address"));
            }
            if (requestCode == PAYMETHOD){
                method.setText("Dikirim Menggunakan "+data.getStringExtra("method"));
            }
        }
    }

    @Override
    protected void onDestroy() {
        new CheckoutDB(getApplicationContext()).deleteAll();
        super.onDestroy();
    }
}
