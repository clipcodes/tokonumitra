package nu.toko.mitra.Page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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

import nu.toko.mitra.Adapter.PayListAdapter;
import nu.toko.mitra.Dialog.DialogConfirm;
import nu.toko.mitra.Dialog.DialogInfo;
import nu.toko.mitra.Model.BillingItemModel;
import nu.toko.mitra.Model.BillingModelNU;
import nu.toko.mitra.Model.ProductModelNU;
import nu.toko.mitra.R;
import nu.toko.mitra.Reqs.ReqString;
import nu.toko.mitra.Utils.Others;

import static nu.toko.mitra.Utils.Staticvar.ALAMAT_KIRIM;
import static nu.toko.mitra.Utils.Staticvar.BANK;
import static nu.toko.mitra.Utils.Staticvar.BUKTI;
import static nu.toko.mitra.Utils.Staticvar.FEEDADDED;
import static nu.toko.mitra.Utils.Staticvar.FEEDBACK;
import static nu.toko.mitra.Utils.Staticvar.GAMBARFIRST;
import static nu.toko.mitra.Utils.Staticvar.HARGA_ADMIN;
import static nu.toko.mitra.Utils.Staticvar.HARGA_MITRA;
import static nu.toko.mitra.Utils.Staticvar.HARGA_ONGKIR;
import static nu.toko.mitra.Utils.Staticvar.HARGA_TOTAL;
import static nu.toko.mitra.Utils.Staticvar.ID_MITRA;
import static nu.toko.mitra.Utils.Staticvar.ID_PEMBELI;
import static nu.toko.mitra.Utils.Staticvar.ID_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.ID_TRANSAKSI;
import static nu.toko.mitra.Utils.Staticvar.ID_TRANSAKSI_ITEM;
import static nu.toko.mitra.Utils.Staticvar.ITEM;
import static nu.toko.mitra.Utils.Staticvar.KOMEN;
import static nu.toko.mitra.Utils.Staticvar.KURIR;
import static nu.toko.mitra.Utils.Staticvar.NAMALENGKAP;
import static nu.toko.mitra.Utils.Staticvar.NAMA_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.NOMINAL;
import static nu.toko.mitra.Utils.Staticvar.NOREK;
import static nu.toko.mitra.Utils.Staticvar.PRODUK;
import static nu.toko.mitra.Utils.Staticvar.QTY;
import static nu.toko.mitra.Utils.Staticvar.RATING;
import static nu.toko.mitra.Utils.Staticvar.RESI;
import static nu.toko.mitra.Utils.Staticvar.SLASH;
import static nu.toko.mitra.Utils.Staticvar.STATUS_TRANSAKSI;
import static nu.toko.mitra.Utils.Staticvar.SUB_TOTAL;
import static nu.toko.mitra.Utils.Staticvar.TGL_PEMESANAN;
import static nu.toko.mitra.Utils.Staticvar.TRANSAKSIDETAIL;
import static nu.toko.mitra.Utils.Staticvar.TRANSAKSIDIKIRIM;
import static nu.toko.mitra.Utils.Staticvar.TRANSAKSIDIKONFIR;
import static nu.toko.mitra.Utils.Staticvar.TRANSAKSIDITOLAK;
import static nu.toko.mitra.Utils.Staticvar.URL_BUKTI_TRANSAKSI;

public class PageOrders  extends AppCompatActivity {

    TextView alamat, subtotal, ongkir, total, kurir, atasnama, namabank, nominal, norek, infohead, info, ulasan;
    String TAG = getClass().getSimpleName();
    List<BillingItemModel> billingItemModels;
    PayListAdapter billadap;
    RecyclerView rvpay;
    CardView confirms, dicline, chat, lihatfoto, informasi, delivery;
    LinearLayout deliverycontainer;
    RequestQueue requestQueue;
    EditText resi;
    int status, idtrans = 0;
    LinearLayout containeraction, containerbukti;
    String fotobukti = null;
    RatingBar star;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_orders);

        idtrans = getIntent().getIntExtra(ID_TRANSAKSI, 0);
        status = getIntent().getIntExtra(STATUS_TRANSAKSI, 0);
        init();
        new ReqString(this, requestQueue).go(respon, TRANSAKSIDETAIL+idtrans);

    }

    void init(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.getCache().clear();
        rvpay = findViewById(R.id.rvorders);
        billingItemModels = new ArrayList<>();
        billadap = new PayListAdapter(this, billingItemModels);
        rvpay.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvpay.setAdapter(billadap);

        resi = findViewById(R.id.resi);
        lihatfoto = findViewById(R.id.lihatfoto);
        alamat = findViewById(R.id.alamat);
        subtotal = findViewById(R.id.subtotal);
        ongkir = findViewById(R.id.ongkir);
        total = findViewById(R.id.total);
        kurir = findViewById(R.id.kurir);
        atasnama = findViewById(R.id.atasnama);
        namabank = findViewById(R.id.namabank);
        nominal = findViewById(R.id.nominal);
        deliverycontainer = findViewById(R.id.deliverycontainer);
        norek = findViewById(R.id.norek);
        informasi = findViewById(R.id.informasi);
        infohead = findViewById(R.id.infohead);
        info = findViewById(R.id.info);
        star = findViewById(R.id.star);
        ulasan = findViewById(R.id.ulasan);

        containeraction = findViewById(R.id.containeraction);
        containerbukti = findViewById(R.id.containerbukti);

        dicline = findViewById(R.id.dicline);
        confirms = findViewById(R.id.confirms);
        delivery = findViewById(R.id.delivery);
        chat = findViewById(R.id.chat);

        confirms.setOnClickListener(new klik());
        dicline.setOnClickListener(new klik());
        delivery.setOnClickListener(new klik());
        chat.setOnClickListener(new klik());
        lihatfoto.setOnClickListener(new klik());

        if (status==2){
            deliverycontainer.setVisibility(View.VISIBLE);
            dicline.setVisibility(View.VISIBLE);
        } else if (status==3){
            informasi.setVisibility(View.VISIBLE);
            infohead.setText("Barang Dikirim");
            info.setText("Barang anda sedang dalam proses pengiriman, sampai pembeli mengkonfirmasi bahwa barang sudah diterima.");
        } else if (status==4){
            informasi.setVisibility(View.VISIBLE);
            info.setVisibility(View.GONE);
            star.setVisibility(View.VISIBLE);
            ulasan.setVisibility(View.VISIBLE);
            infohead.setText("Pesanan Telah Sampai");
        }

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public class klik implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.confirms:
                    new DialogConfirm(PageOrders.this).mentriger(new DialogConfirm.Go() {
                        @Override
                        public void trigerbos() {
                            new ReqString(PageOrders.this, requestQueue).go(respontrans, TRANSAKSIDIKONFIR+idtrans);
                        }
                    });
                    break;
                case R.id.dicline:
                    new DialogInfo(PageOrders.this, "Apakah anda yakin tidak bisa memenuhi pesanan transaksi.").mentriger(new DialogInfo.Go() {
                        @Override
                        public void trigerbos() {
                            new ReqString(PageOrders.this, requestQueue).go(respontrans, TRANSAKSIDITOLAK+idtrans);
                        }
                    });
                    break;
                case R.id.delivery:
                    if (resi.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Resi Pengiriman Kosong", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    new DialogInfo(PageOrders.this, "Jika anda mengkonfirmasi pengiriman ini, maka barang yang dipesan telah dalam proses pengiriman.").mentriger(new DialogInfo.Go() {
                        @Override
                        public void trigerbos() {
                            findViewById(R.id.deliverytex).setVisibility(View.INVISIBLE);
                            findViewById(R.id.deliveryprog).setVisibility(View.VISIBLE);
                            new ReqString(PageOrders.this, requestQueue).go(respontrans, TRANSAKSIDIKIRIM+idtrans+SLASH+resi.getText().toString());
                        }
                    });
                    break;
                case R.id.lihatfoto:
                    Intent i = new Intent(getApplicationContext(), PagePhotoView.class);
                    i.putExtra(URL_BUKTI_TRANSAKSI, fotobukti);
                    startActivity(i);
                    break;
            }
        }
    }

    Response.Listener<String> respon = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse: "+response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                BillingModelNU bill = new BillingModelNU();
                bill.setId_pembeli(jsonObject.getInt(ID_PEMBELI));
                bill.setSub_total(jsonObject.getInt(SUB_TOTAL));
                bill.setHarga_ongkir(jsonObject.getInt(HARGA_ONGKIR));
                bill.setHarga_total(jsonObject.getInt(HARGA_TOTAL));
                bill.setId_transaksi(jsonObject.getInt(ID_TRANSAKSI));
                bill.setTgl_pemesanan(jsonObject.getString(TGL_PEMESANAN));
                bill.setStatus_transaksi(jsonObject.getInt(STATUS_TRANSAKSI));
                bill.setResi(jsonObject.getString(RESI));
                bill.setFeedadded(jsonObject.getBoolean(FEEDADDED));
                bill.setKurir(jsonObject.getString(KURIR));

                JSONObject mitra = jsonObject.getJSONObject("mitra");
                bill.setId_mitra(mitra.getInt(ID_MITRA));

                alamat.setText(jsonObject.getString(ALAMAT_KIRIM));
                kurir.setText(jsonObject.getString(KURIR).toUpperCase());
                subtotal.setText("Rp."+ Others.PercantikHarga(jsonObject.getInt(SUB_TOTAL)));
                ongkir.setText("Rp."+Others.PercantikHarga(jsonObject.getInt(HARGA_ONGKIR)));
                total.setText("Rp."+Others.PercantikHarga(jsonObject.getInt(HARGA_TOTAL)));

                JSONObject bukti = jsonObject.getJSONObject(BUKTI);
                bill.setUrl_bukti_transaksi(bukti.getString(URL_BUKTI_TRANSAKSI));
                bill.setNamalengkapbayar(bukti.getString(NAMALENGKAP));
                bill.setNorek(bukti.getString(NOREK));
                bill.setBank(bukti.getString(BANK));
                bill.setNominaltransfer(bukti.getInt(NOMINAL));

                if (jsonObject.getBoolean(FEEDADDED)){
                    JSONObject feedback = jsonObject.getJSONObject(FEEDBACK);
                    ulasan.setText(feedback.getString(KOMEN));
                    star.setRating(Float.valueOf(feedback.getString(RATING)));
                    star.setEnabled(false);
                } else {
                    star.setVisibility(View.GONE);
                    ulasan.setText("Tidak ada ulasan.");
                }

                fotobukti = bukti.getString(URL_BUKTI_TRANSAKSI);

                atasnama.setText(bukti.getString(NAMALENGKAP));
                namabank.setText(bukti.getString(BANK));
                nominal.setText(Others.PercantikHarga(bukti.getInt(NOMINAL)));
                norek.setText(bukti.getString(NOREK));

                //Transaksi Item
                JSONArray item = new JSONArray(jsonObject.getString(ITEM));
                for (int p = 0; p < item.length(); p++){
                    Log.i(TAG, "onResponse: XXX");

                    JSONObject objekitem = item.getJSONObject(p);
                    BillingItemModel billitem = new BillingItemModel();
                    billitem.setId_transaksi(objekitem.getString(ID_TRANSAKSI));
                    billitem.setId_transaksi_item(objekitem.getString(ID_TRANSAKSI_ITEM));
                    billitem.setQty(objekitem.getInt(QTY));

                    Log.i(TAG, "onResponse: COK");

                    //Produk
                    JSONObject jsonproduk = new JSONObject(objekitem.getString(PRODUK));
                    ProductModelNU produk = new ProductModelNU();
                    produk.setId_produk(jsonproduk.getString(ID_PRODUK));
                    produk.setNama_produk(jsonproduk.getString(NAMA_PRODUK));
                    produk.setGambarfirst(jsonproduk.getString(GAMBARFIRST));
                    produk.setHarga_admin(jsonproduk.getInt(HARGA_ADMIN));
                    produk.setHarga_mitra(jsonproduk.getInt(HARGA_MITRA));

                    Log.i(TAG, "onResponse: RES");

                    billitem.setProduk(produk);

                    billingItemModels.add(billitem);
                }

                billadap.notifyDataSetChanged();
            } catch (JSONException e){
                Log.i(TAG, "extracdata: Err "+e.getMessage());
            }
        }
    };

    Response.Listener<String> respontrans = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject j = new JSONObject(response);
                if (j.getString("msg").equals("dikirim")){
                    status = 3;
                    deliverycontainer.setVisibility(View.GONE);
                    informasi.setVisibility(View.VISIBLE);
                    delivery.setVisibility(View.GONE);
                    dicline.setVisibility(View.GONE);
                    infohead.setText("Barang Dikirim");
                    info.setText("Barang anda sedang dalam proses pengiriman, sampai pembeli mengkonfirmasi bahwa barang sudah diterima.");
                }

                if (j.getString("msg").equals("ditolak")){
                    Log.i(TAG, "onResponse: ditolak");
                }
            } catch (JSONException e){
                Log.i(TAG, "onResponse: "+e.getMessage());
            }
        }
    };
}
