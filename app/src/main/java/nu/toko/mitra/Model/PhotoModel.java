package nu.toko.mitra.Model;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;

public class PhotoModel {

    private Uri uri;
    private String editurl;

    public PhotoModel() {

    }

    public PhotoModel(Uri uri) {
        this.uri = uri;
    }

    public PhotoModel(String editurl) {
        this.editurl = editurl;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getEditurl() {
        return editurl;
    }

    public void setEditurl(String editurl) {
        this.editurl = editurl;
    }
}
