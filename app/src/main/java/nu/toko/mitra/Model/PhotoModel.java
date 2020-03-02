package nu.toko.mitra.Model;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;

public class PhotoModel {

    private Bitmap bitmap;
    private String editurl;

    public PhotoModel() {

    }

    public PhotoModel(Bitmap uri) {
        this.bitmap = uri;
    }

    public PhotoModel(String editurl) {
        this.editurl = editurl;
    }

    public String getEditurl() {
        return editurl;
    }

    public void setEditurl(String editurl) {
        this.editurl = editurl;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
