package nu.toko.mitra.Page;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import nu.toko.mitra.Model.AddressModel;
import nu.toko.mitra.R;
import nu.toko.mitra.Sqlite.AddressDB;

public class AddPageAddress extends AppCompatActivity {

    CardView simpan;
    EditText prov, kabkota, kec, pos, address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_addaddress);

        init();

    }

    void init(){
        prov = findViewById(R.id.prov);
        kabkota = findViewById(R.id.kabkota);
        kec = findViewById(R.id.kec);
        pos = findViewById(R.id.pos);
        address = findViewById(R.id.address);
        simpan = findViewById(R.id.simpan);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressModel model = new AddressModel();
                model.setAddress(address.getText().toString());
                model.setProvinsi(prov.getText().toString());
                model.setKotakab(kabkota.getText().toString());
                model.setKec(kec.getText().toString());
                model.setKodepos(pos.getText().toString());
                new AddressDB(getApplicationContext()).insert(model);
                onBackPressed();
            }
        });
    }
}
