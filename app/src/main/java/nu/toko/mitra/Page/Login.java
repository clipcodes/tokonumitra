package nu.toko.mitra.Page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import nu.toko.mitra.Fragment.Loader;
import nu.toko.mitra.Fragment.SignIn;
import nu.toko.mitra.Fragment.SignUp;
import nu.toko.mitra.MainActivity;
import nu.toko.mitra.Model.UserMitraModel;
import nu.toko.mitra.R;
import nu.toko.mitra.Reqs.UserReqs;
import nu.toko.mitra.Utils.UserPrefs;

import static nu.toko.mitra.Utils.Staticvar.USER_DAFTAR;

public class Login extends AppCompatActivity implements SignIn.KirimData{

    String TAG = getClass().getSimpleName();
    FrameLayout container;
    FrameLayout login, signup;
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    RequestQueue requestQueue;

    @Override
    public void trigerhome() {
        signIn();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_auth);

        if (UserPrefs.isLogin(getApplicationContext())){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();

        init();
        declare();
    }

    void init(){
        container = findViewById(R.id.container);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        login.setOnClickListener(new click());
        signup.setOnClickListener(new click());
    }

    void declare(){
        ngloadview(new SignIn());
    }

    class click implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.login:
                    findViewById(R.id.indpurchase).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimary));
                    findViewById(R.id.indbilling).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.greytranspa2));
                    ngloadview(new SignIn());
                    break;
                case R.id.signup:
                    findViewById(R.id.indpurchase).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.greytranspa2));
                    findViewById(R.id.indbilling).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimary));
                    ngloadview(new SignUp());
                    break;
            }
        }
    }

    void showProgressBar(){
        Log.i(TAG, "showProgressBar: ");
    }

    void hideProgressBar(){
        Log.i(TAG, "hideProgressBar: ");
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    void updateUI(FirebaseUser user){
        Log.i(TAG, "updateUI: ");
        if (user!=null){
//            Log.i(TAG, "updateUI sukses : " + user.getEmail());
//            Intent i = new Intent(getApplicationContext(), MainActivity.class);
//            UserPrefs.setLogin(getApplicationContext(), true);
//            startActivity(i);
//            finish();
            ngloadview(new Loader());
            UserMitraModel usr = new UserMitraModel();
            usr.setEmail_mitra(user.getEmail());
            usr.setNama_mitra(user.getDisplayName());
            usr.setProvinsi_mitra("0");
            usr.setKabupaten_mitra("0");
            usr.setKecamatan_mitra("0");
            usr.setNama_toko_mitra("0");
            usr.setNik_mitra("0");
            usr.setAlamat_toko_mitra("0");
            usr.setNo_telp_mitra("0");
            usr.setNo_rekening_mitra("0");
            usr.setDeskripsi_toko_mitra("0");
            usr.setKode_pos_mitra("0");
            usr.setNo_rekening_mitra("0");
            Log.i(TAG, "updateUI: "+user.getPhoneNumber());
            if (user.getPhoneNumber()==null){
                usr.setNo_telp_mitra("0");
            }

            new UserReqs(this, requestQueue).daftar(newuser, usr, "x", USER_DAFTAR);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        showProgressBar();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }

                        hideProgressBar();
                    }
                });
    }

    private Response.Listener<String> newuser = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("RESPON", response);
            try {
                JSONObject object = new JSONObject(response);
                UserPrefs.setLogin(getApplicationContext(), true);
                UserPrefs.setId(object.getString("id_mitra"), getApplicationContext());
                UserPrefs.setAlamat_toko(object.getString("alamat_toko_mitra"), getApplicationContext());
                UserPrefs.setEmail(object.getString("email_mitra"), getApplicationContext());
                UserPrefs.setNama(object.getString("nama_mitra"), getApplicationContext());
                UserPrefs.setNo_telp(object.getString("no_telp_mitra"), getApplicationContext());
                UserPrefs.setProvinsi(object.getString("provinsi_mitra"), getApplicationContext());
                UserPrefs.setKabupaten(object.getString("kabupaten_mitra"), getApplicationContext());
                UserPrefs.setKecamatan(object.getString("kecamatan_mitra"), getApplicationContext());
                UserPrefs.setKode_pos(object.getString("kode_pos_mitra"), getApplicationContext());
                UserPrefs.setDeskripsi_toko(object.getString("deskripsi_toko_mitra"), getApplicationContext());
                UserPrefs.setRekening(object.getString("no_rekening_mitra"), getApplicationContext());
                UserPrefs.setNik(object.getString("nik_mitra"), getApplicationContext());
                UserPrefs.setNamakab(object.getString("namakota"), getApplicationContext());
                FirebaseMessaging.getInstance().subscribeToTopic("mitra"+UserPrefs.getId(getApplicationContext()));
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            } catch (JSONException e) {
                Log.i("RESPON EROR", e.getMessage());
            }
        }
    };


    private boolean ngloadview(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_user, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
