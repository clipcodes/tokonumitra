package nu.toko.mitra.Page;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.chrisbanes.photoview.PhotoView;
import com.nostra13.universalimageloader.core.ImageLoader;

import nu.toko.mitra.R;
import nu.toko.mitra.Utils.Others;

import static nu.toko.mitra.Utils.Staticvar.FOTOBUKTI;
import static nu.toko.mitra.Utils.Staticvar.URL_BUKTI_TRANSAKSI;

public class PagePhotoView extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_photoview);

        Others.MahathirOptionGambar(getApplicationContext());
        PhotoView photoView = findViewById(R.id.photo_view);
        ImageLoader.getInstance().displayImage(FOTOBUKTI + getIntent().getStringExtra(URL_BUKTI_TRANSAKSI), photoView);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
