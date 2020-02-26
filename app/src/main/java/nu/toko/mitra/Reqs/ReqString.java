package nu.toko.mitra.Reqs;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.NetworkError;
import com.android.volley.error.NoConnectionError;
import com.android.volley.error.ParseError;
import com.android.volley.error.ServerError;
import com.android.volley.error.TimeoutError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.request.StringRequest;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nu.toko.mitra.Model.NewProductModel;
import nu.toko.mitra.Utils.UserPrefs;

import static nu.toko.mitra.Utils.Staticvar.PENGADUAN;

public class ReqString {

    String TAG = getClass().getSimpleName();
    Activity activity;
    RequestQueue requestQueue;

    public ReqString(Activity activity, RequestQueue requestQueue) {
        this.activity = activity;
        this.requestQueue = requestQueue;
        this.requestQueue.getCache().clear();
    }

    public void go(Response.Listener<String> newroom, String url){
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                newroom, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.i("RESPON EROR", "TimeoutError NoConnectionError");
                } else if (error instanceof ServerError) {
                    Log.i("RESPON EROR", "ServerError");
                } else if (error instanceof NetworkError) {
                    Log.i("RESPON EROR", "NetworkError");
                } else if (error instanceof ParseError) {
                    Log.i("RESPON EROR", "ParseError");
                }
            }
        }
        );

        getRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(getRequest);
    }

    public void go(Response.Listener<String> newroom, Response.ErrorListener errorListener, String url){
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                newroom, errorListener
        );

        getRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(getRequest);
    }

    public void pos(Response.Listener<String> responstatus, final String barangObjek, String url){
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, responstatus, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.i("RESPON EROR", "TimeoutError NoConnectionError");
                } else if (error instanceof ServerError) {
                    Log.i("RESPON EROR", "ServerError");
                } else if (error instanceof NetworkError) {
                    Log.i("RESPON EROR", "NetworkError");
                } else if (error instanceof ParseError) {
                    Log.i("RESPON EROR", "ParseError");
                }
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("json" , barangObjek);
                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }

    public void multipart(Response.Listener<String> responstatus, String url, String idtrans, String tanggal, File uri){
        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, url,
                responstatus, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.i("RESPON EROR", "TimeoutError NoConnectionError");
                } else if (error instanceof ServerError) {
                    Log.i("RESPON EROR", "ServerError");
                } else if (error instanceof NetworkError) {
                    Log.i("RESPON EROR", "NetworkError");
                } else if (error instanceof ParseError) {
                    Log.i("RESPON EROR", "ParseError");
                }
            }
        });
        smr.addStringParam("id_transaksi", idtrans);
        smr.addStringParam("tgl_transaksi", tanggal);
        smr.addFile("image", uri.toString());
        requestQueue.add(smr);
    }

    public void addproduk(Response.Listener<String> responstatus, NewProductModel newProductModel, String url){
        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, url,
                responstatus, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.i("RESPON EROR", "TimeoutError NoConnectionError");
                } else if (error instanceof ServerError) {
                    Log.i("RESPON EROR", "ServerError");
                } else if (error instanceof NetworkError) {
                    Log.i("RESPON EROR", "NetworkError");
                } else if (error instanceof ParseError) {
                    Log.i("RESPON EROR", "ParseError");
                }
            }
        });
        smr.addStringParam("id_mitra", UserPrefs.getId(activity));
        smr.addStringParam("nama_produk", newProductModel.getNama());
        smr.addStringParam("deskripsi_produk", newProductModel.getDeskripsi());
        smr.addStringParam("id_sub_kategori", newProductModel.getSubkategori());
        smr.addStringParam("berat_produk", newProductModel.getBerat());
        smr.addStringParam("kondisi_produk", newProductModel.getKondisi());
        smr.addStringParam("diskon", newProductModel.getDiskon());
        smr.addStringParam("harga_mitra", newProductModel.getHarga());
        smr.addStringParam("stok", newProductModel.getStok());

        for (int i = 0; i < newProductModel.getUris().size(); i++){
            try {
                smr.addFile("image"+i, new File(new URI(newProductModel.getUris().get(i).getUri().toString())).toString());
            } catch (URISyntaxException e){
                Log.i(TAG, "addproduk: "+e.getMessage());
            }
        }
        requestQueue.add(smr);
    }

    public void bantuan(Response.Listener<String> responstatus, String pengaduan){
        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, PENGADUAN,
                responstatus, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.i("RESPON EROR", "TimeoutError NoConnectionError");
                } else if (error instanceof ServerError) {
                    Log.i("RESPON EROR", "ServerError");
                } else if (error instanceof NetworkError) {
                    Log.i("RESPON EROR", "NetworkError");
                } else if (error instanceof ParseError) {
                    Log.i("RESPON EROR", "ParseError");
                }
            }
        });
        smr.addStringParam("id_pembeli", UserPrefs.getId(activity));
        smr.addStringParam("pengaduan", pengaduan);
        requestQueue.add(smr);
    }

    public void editproduk(Response.Listener<String> responstatus, NewProductModel newProductModel, String url){
        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, url,
                responstatus, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.i("RESPON EROR", "TimeoutError NoConnectionError");
                } else if (error instanceof ServerError) {
                    Log.i("RESPON EROR", "ServerError");
                } else if (error instanceof NetworkError) {
                    Log.i("RESPON EROR", "NetworkError");
                } else if (error instanceof ParseError) {
                    Log.i("RESPON EROR", "ParseError");
                }
            }
        });
        smr.addStringParam("nama_produk", newProductModel.getNama());
        smr.addStringParam("deskripsi_produk", newProductModel.getDeskripsi());
        smr.addStringParam("id_sub_kategori", newProductModel.getSubkategori());
        smr.addStringParam("berat_produk", newProductModel.getBerat());
        smr.addStringParam("kondisi_produk", newProductModel.getKondisi());
        smr.addStringParam("harga_mitra", newProductModel.getHarga());
        smr.addStringParam("diskon", newProductModel.getDiskon());
        smr.addStringParam("stok", newProductModel.getStok());
        if (newProductModel.getDeletedphoto()!=null){
            smr.addStringParam("gambarhapus", newProductModel.getDeletedphoto());
        }
        ArrayList<Uri> uri = new ArrayList<>();
        for (int i = 0; i < newProductModel.getUris().size(); i++){
            if (newProductModel.getUris().get(i).getUri()!=null){
                uri.add(newProductModel.getUris().get(i).getUri());
            }
        }

        for (int i = 0; i < uri.size(); i++){
                try {
                    smr.addFile("image"+i, new File(new URI(uri.get(i).toString())).toString());
                } catch (URISyntaxException e){
                    Log.i(TAG, "addproduk: "+e.getMessage());
                }
        }
        requestQueue.add(smr);
    }
}
