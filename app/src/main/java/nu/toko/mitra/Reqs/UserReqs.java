package nu.toko.mitra.Reqs;

import android.app.Activity;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import nu.toko.mitra.Model.UserMitraModel;

public class UserReqs {

    String TAG = getClass().getSimpleName();
    Activity activity;
    RequestQueue requestQueue;

    public UserReqs(Activity activity, RequestQueue requestQueue) {
        this.activity = activity;
        this.requestQueue = requestQueue;
    }

    public void daftar(Response.Listener<String> responstatus, final UserMitraModel usr, final String pass, String url){
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
                params.put("email_mitra" , usr.getEmail_mitra());
                if (pass!=null){
                    params.put("password_mitra" , pass);
                }
                params.put("nama_mitra" , usr.getNama_mitra());
                params.put("nama_toko_mitra" , usr.getNama_toko_mitra());
                params.put("nik_mitra" , usr.getNik_mitra());
                params.put("alamat_toko_mitra" , usr.getAlamat_toko_mitra());
                params.put("no_telp_mitra" , usr.getNo_telp_mitra());
                params.put("no_rekening_mitra" , usr.getNo_rekening_mitra());
                params.put("deskripsi_toko_mitra" , usr.getDeskripsi_toko_mitra());
                params.put("provinsi_mitra" , usr.getProvinsi_mitra());
                params.put("kabupaten_mitra" , usr.getKabupaten_mitra());
                params.put("kecamatan_mitra" , usr.getKecamatan_mitra());
                params.put("kode_pos_mitra" , usr.getKode_pos_mitra());
                params.put("nama_bank" , usr.getNama_bank());
                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }

    public void login(Response.Listener<String> responstatus, final String email, final String pass, String url){
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
                params.put("email" , email);
                params.put("password" , pass);
                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }

}
