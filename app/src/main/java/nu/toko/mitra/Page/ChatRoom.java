package nu.toko.mitra.Page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import nu.toko.mitra.Adapter.ChatRoomAdapter;
import nu.toko.mitra.Model.ChatRoomModel;
import nu.toko.mitra.Model.ChatsModel;
import nu.toko.mitra.R;
import nu.toko.mitra.Utils.UserPrefs;

public class ChatRoom extends AppCompatActivity {

    String TAG = getClass().getSimpleName();
    RecyclerView rvchats;
    List<ChatRoomModel> chatsModelList;
    ChatRoomAdapter chatsAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Activity activity;
    EditText pesanedt;
    FrameLayout kirimpesan;
    DatabaseReference pesan, chatuser, chatmitra;
    HashMap<String, String> pesandata;
    String id_user = null;
    String PESAN, ROOM;
    String namauser;
    Date date;
    DateFormat dateFormat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_chatroom);

        id_user = String.valueOf(getIntent().getIntExtra("id_user", 0));
        namauser = getIntent().getStringExtra("namauser");
        activity = this;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("room");
        firebaseDatabase.getReference().child("chat").child("mitra"+UserPrefs.getId(getApplicationContext())).child("user"+id_user).child("dibaca").setValue(0);
        Log.i(TAG, "onCreate: "+id_user);
        init();
        ngisi();

    }

    void init(){
        chatsModelList = new ArrayList<>();
        rvchats = findViewById(R.id.rvchatroom);
        pesanedt = findViewById(R.id.pesanedt);
        kirimpesan = findViewById(R.id.kirimpesan);
        chatsAdapter = new ChatRoomAdapter(activity, chatsModelList);
        rvchats.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true));
        rvchats.setAdapter(chatsAdapter);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        kirimpesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pesanedt.getText().toString().isEmpty()){
                    Toast.makeText(activity, "Pesan Kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                PESAN = pesanedt.getText().toString();
                ROOM = id_user+""+UserPrefs.getId(getApplicationContext());

                chatuser = firebaseDatabase.getReference().child("chat").child("user"+id_user).child("mitra"+UserPrefs.getId(getApplicationContext()));
                chatmitra = firebaseDatabase.getReference().child("chat").child("mitra"+UserPrefs.getId(getApplicationContext())).child("user"+id_user);
                pesan = firebaseDatabase.getReference().child("room").child(ROOM).push();


                date = new Date();
                dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

                ChatsModel cm = new ChatsModel();
                cm.setTo(Integer.valueOf(UserPrefs.getId(getApplicationContext())));
                cm.setFrom(Integer.valueOf(id_user));
                cm.setLast(PESAN);
                cm.setDibaca(0);
                cm.setNamauser(namauser);
                cm.setCreated(dateFormat.format(date));
                cm.setRoom(cm.getFrom()+""+cm.getTo());
                chatmitra.setValue(cm);
                cm.setNamauser(UserPrefs.getNama(getApplicationContext()));
                cm.setDibaca(1);
                chatuser.setValue(cm);

                ChatRoomModel crm = new ChatRoomModel();
                crm.setUser("mitra");
                crm.setMsg(PESAN);
                crm.setCreated(dateFormat.format(date));
                pesan.setValue(crm);

                pesanedt.setText("");
            }
        });
    }

    public void ngisi(){
        Query myTopPostsQuery = databaseReference.child(id_user+UserPrefs.getId(getApplicationContext()));
        myTopPostsQuery.addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.i("KEY", dataSnapshot.getKey());
                        ChatRoomModel c = dataSnapshot.getValue(ChatRoomModel.class);
                        c.setKey(dataSnapshot.getKey());
                        chatsModelList.add(0, c);
                        chatsAdapter.notifyItemInserted(0);
                        rvchats.scrollToPosition(0);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.i("KEY", dataSnapshot.getKey());
//                        ChatsModel c = dataSnapshot.getValue(ChatsModel.class);
//                        chatsModelList.add(0, c);
//                        chatsAdapter.notifyItemInserted(0);
//                        chatsAdapter.notifyItemRangeChanged(1, chatsModelList.size());
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
