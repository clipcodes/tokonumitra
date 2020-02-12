package nu.toko.mitra.Model;

import static nu.toko.mitra.Utils.Staticvar.ADDRESS;
import static nu.toko.mitra.Utils.Staticvar.ID_ADDRESS;
import static nu.toko.mitra.Utils.Staticvar.KEC;
import static nu.toko.mitra.Utils.Staticvar.KODEPOS;
import static nu.toko.mitra.Utils.Staticvar.KOTAKAB;
import static nu.toko.mitra.Utils.Staticvar.PROVINSI;

public class ChooserModel {

    private int id;
    private String pilihan;

    public ChooserModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPilihan() {
        return pilihan;
    }

    public void setPilihan(String pilihan) {
        this.pilihan = pilihan;
    }
}
