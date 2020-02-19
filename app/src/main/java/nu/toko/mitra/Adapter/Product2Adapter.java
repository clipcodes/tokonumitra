package nu.toko.mitra.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import nu.toko.mitra.Model.ProductModel;
import nu.toko.mitra.Model.ProductModelNU;
import nu.toko.mitra.Page.Details;
import nu.toko.mitra.R;
import nu.toko.mitra.Utils.Others;

import static nu.toko.mitra.Utils.Staticvar.FOTOPRODUK;
import static nu.toko.mitra.Utils.Staticvar.HARGA_MITRA;
import static nu.toko.mitra.Utils.Staticvar.ID_PRODUK;
import static nu.toko.mitra.Utils.Staticvar.NAMA_PRODUK;

public class Product2Adapter extends RecyclerView.Adapter<Product2Adapter.ViewHolder>{

    Activity activity;
    List<ProductModelNU> items;
    OnClickBuy onClickBuy;

    public Product2Adapter(Activity activity, List<ProductModelNU> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modeitem_product2, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.price.setText("Rp."+ Others.PercantikHarga(Integer.valueOf(items.get(position).getHarga_mitra())));
        ImageLoader.getInstance().displayImage(FOTOPRODUK + items.get(position).getGambarfirst(), holder.thumb);
        holder.title.setText(items.get(position).getNama_produk());
        holder.namatoko.setText(items.get(position).getOwner().getNama_toko_mitra());
        holder.lokasi.setText(items.get(position).getDikirimdari());
        holder.stock.setText(items.get(position).getStok()+" Stock");
        holder.star.setRating(items.get(position).getRating());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, Details.class);
                i.putExtra(ID_PRODUK, items.get(position).getId_produk());
                i.putExtra(HARGA_MITRA, items.get(position).getHarga_mitra());
                i.putExtra(NAMA_PRODUK, items.get(position).getNama_produk());
                activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, price, namatoko, lokasi, stock;
        ImageView thumb;
        RatingBar star;

        public ViewHolder(final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            star = itemView.findViewById(R.id.star);
            thumb = itemView.findViewById(R.id.thumb);
            namatoko = itemView.findViewById(R.id.namatoko);
            lokasi = itemView.findViewById(R.id.lokasi);
            stock = itemView.findViewById(R.id.stock);
        }
    }

    public void setOnBuyClickListener(OnClickBuy onClickBuy) {
        this.onClickBuy = onClickBuy;
    }
    public interface OnClickBuy {
        void onClick(ProductModel productModel);
    }
}
