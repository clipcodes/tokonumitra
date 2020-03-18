package nu.toko.mitra.Reqs;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
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

    public void foto(final String nama, final Bitmap bitmap, Response.Listener<NetworkResponse> res, String url) {
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, res,
                new Response.ErrorListener() {
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
                })  {

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("images", new DataPart(nama + ".jpg", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        Volley.newRequestQueue(activity).add(volleyMultipartRequest);
    }

    public void addproduk(Response.Listener<String> responstatus, final NewProductModel newProductModel, String url){
        StringRequest smr = new StringRequest(Request.Method.POST, url,
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
        }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("id_mitra", UserPrefs.getId(activity));
                params.put("nama_produk", newProductModel.getNama());
                params.put("deskripsi_produk", newProductModel.getDeskripsi());
                params.put("id_sub_kategori",  newProductModel.getSubkategori());
                params.put("berat_produk", newProductModel.getBerat());
                params.put("kondisi_produk", newProductModel.getKondisi());
                params.put("diskon", newProductModel.getDiskon());
                params.put("harga_mitra", newProductModel.getHarga());
                params.put("stok", newProductModel.getStok());
                return params;
            }
        };
        requestQueue.add(smr);
    }

    public void bantuan(Response.Listener<String> responstatus, final String pengaduan){
        StringRequest smr = new StringRequest(Request.Method.POST, PENGADUAN,
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
        }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("id_pembeli", UserPrefs.getId(activity));
                params.put("pengaduan", pengaduan);
                return params;
            }
        };
        requestQueue.add(smr);
    }

    public void editproduk(Response.Listener<String> responstatus, final NewProductModel newProductModel, String url){
        StringRequest smr = new StringRequest(Request.Method.POST, url,
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
        }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("nama_produk", newProductModel.getNama());
                params.put("deskripsi_produk", newProductModel.getDeskripsi());
                params.put("id_sub_kategori", newProductModel.getSubkategori());
                params.put("berat_produk", newProductModel.getBerat());
                params.put("kondisi_produk", newProductModel.getKondisi());
                params.put("diskon", newProductModel.getDiskon());
                params.put("harga_mitra", newProductModel.getHarga());
                params.put("stok", newProductModel.getStok());
                if (newProductModel.getDeletedphoto()!=null){
                    params.put("gambarhapus", newProductModel.getDeletedphoto());
                }
                return params;
            }
        };
        requestQueue.add(smr);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
