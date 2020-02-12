package nu.toko.mitra.Page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import nu.toko.mitra.Adapter.AddressAdapter;
import nu.toko.mitra.Model.AddressModel;
import nu.toko.mitra.R;
import nu.toko.mitra.Sqlite.AddressDB;

public class PageAddress extends AppCompatActivity {

    ImageView add;
    RecyclerView rvaddress;
    AddressAdapter addressAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_address);

        init();

    }

    void init(){
        rvaddress = findViewById(R.id.rvaddress);
        addressAdapter = new AddressAdapter(this, new AddressDB(getApplicationContext()).getAll());
        rvaddress.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvaddress.setAdapter(addressAdapter);

        add = findViewById(R.id.add);

        addressAdapter.setOnItemClickListener(new AddressAdapter.OnClick() {
            @Override
            public void onItemClick(AddressModel addressModel) {
                Intent i = new Intent();
                i.putExtra("prov", addressModel.getProvinsi());
                i.putExtra("kabkota", addressModel.getKotakab());
                i.putExtra("kec", addressModel.getKec());
                i.putExtra("pos", addressModel.getKodepos());
                i.putExtra("address", addressModel.getAddress());
                setResult(RESULT_OK, i);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddPageAddress.class);
                startActivity(i);
            }
        });
    }
}
