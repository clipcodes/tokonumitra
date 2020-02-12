package nu.toko.mitra.Page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import nu.toko.mitra.Fragment.SignIn;
import nu.toko.mitra.Fragment.SignUp;
import nu.toko.mitra.MainActivity;
import nu.toko.mitra.R;
import nu.toko.mitra.Utils.UserPrefs;

public class Login extends AppCompatActivity {

    FrameLayout container;
    FrameLayout login, signup;

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
