package nu.toko.mitra.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import nu.toko.mitra.Model.ProductModel;
import nu.toko.mitra.Page.Details;
import nu.toko.mitra.R;

import static nu.toko.mitra.Utils.Staticvar.DESCRIPTION;
import static nu.toko.mitra.Utils.Staticvar.IMAGES;
import static nu.toko.mitra.Utils.Staticvar.PRICE;
import static nu.toko.mitra.Utils.Staticvar.TITLE;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    Activity activity;
    List<ProductModel> items;
    OnClickBuy onClickBuy;

    public ProductAdapter(Activity activity, List<ProductModel> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modeitem_product, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (items.get(position).getImages()!=null){
            try {
                InputStream ims = activity.getAssets().open("images/"+items.get(position).getImages().split(",")[0]);
                Drawable d = Drawable.createFromStream(ims, null);
                holder.thumb.setImageDrawable(d);
            }
            catch(IOException ex) {
                Log.i("PHOTO ERR", ex.getMessage());
            }
        }
        holder.price.setText("$"+String.valueOf(items.get(position).getPrice()));
        holder.title.setText(items.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, Details.class);
                i.putExtra(TITLE, items.get(position).getTitle());
                i.putExtra(IMAGES, items.get(position).getImages());
                i.putExtra(DESCRIPTION, items.get(position).getDescription());
                i.putExtra(PRICE, items.get(position).getPrice());
                activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        ImageView thumb;
        TextView title, price;

        public ViewHolder(View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.price);
            title = itemView.findViewById(R.id.title);
            thumb = itemView.findViewById(R.id.thumb);
        }
    }

    public void setOnBuyClickListener(OnClickBuy onClickBuy) {
        this.onClickBuy = onClickBuy;
    }
    public interface OnClickBuy {
        void onClick(ProductModel productModel);
    }
}
