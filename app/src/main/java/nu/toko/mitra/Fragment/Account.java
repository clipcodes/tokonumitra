package nu.toko.mitra.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.firebase.messaging.FirebaseMessaging;

import nu.toko.mitra.Dialog.DialogLogout;
import nu.toko.mitra.Page.Chats;
import nu.toko.mitra.Page.EditProfile;
import nu.toko.mitra.Page.Login;
import nu.toko.mitra.Page.UserSetting;
import nu.toko.mitra.R;
import nu.toko.mitra.Utils.UserPrefs;

public class Account extends Fragment {

    TextView fullname, email;
    FrameLayout logout;
    FrameLayout chat, edit_profile, pesanan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.page_accountnew, container, false);

        init(root);

        return root;
    }

    void init(View v){
        logout = v.findViewById(R.id.logout);
        fullname = v.findViewById(R.id.fullname);
        email = v.findViewById(R.id.email);
        chat = v.findViewById(R.id.chat);
        pesanan = v.findViewById(R.id.pesanan);
        edit_profile = v.findViewById(R.id.edit_profile);
        logout.setOnClickListener(new onClick());
        chat.setOnClickListener(new onClick());
        edit_profile.setOnClickListener(new onClick());
        pesanan.setOnClickListener(new onClick());

        fullname.setText(UserPrefs.getNama(getActivity()));
        email.setText(UserPrefs.getEmail(getActivity()));
    }

    class onClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent xx = null;
            switch (v.getId()){
                case R.id.pesanan:
                    xx = new Intent(getActivity(), Chats.class);
                    getActivity().startActivity(xx);
                    break;
                case R.id.chat:
                    Intent aa = new Intent(getActivity(), Chats.class);
                    getActivity().startActivity(aa);
                    break;
                case R.id.edit_profile:
                    Intent go = new Intent(getActivity(), UserSetting.class);
                    getActivity().startActivity(go);
                    break;
                case R.id.logout:
                    new DialogLogout(getActivity()).mentriger(new DialogLogout.Go() {
                        @Override
                        public void trigerbos() {
                            UserPrefs.setLogin(getActivity(), false);
                            FirebaseMessaging.getInstance().unsubscribeFromTopic("mitra"+UserPrefs.getId(getActivity()));
                            Intent i = new Intent(getActivity(), Login.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            getActivity().finish();
                        }
                    });
                    break;
            }
        }
    }

}