package nu.toko.mitra.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import nu.toko.mitra.Model.ChooserModel;
import nu.toko.mitra.Model.ProductModel;
import nu.toko.mitra.Model.ProductModelNU;
import nu.toko.mitra.Page.Details;
import nu.toko.mitra.R;
import nu.toko.mitra.Utils.Others;

import static nu.toko.mitra.Utils.Staticvar.FOTOPRODUK;

public class ChooserAdapter extends RecyclerView.Adapter<ChooserAdapter.ViewHolder>{

    Activity activity;
    List<ChooserModel> items;
    OnClickBuy onClickBuy;

    public ChooserAdapter(Activity activity, List<ChooserModel> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modelitem_chooser, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.pilihan.setText(items.get(position).getPilihan());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBuy.onClick(items.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        TextView pilihan;

        public ViewHolder(final View itemView) {
            super(itemView);
            pilihan = itemView.findViewById(R.id.pilihan);
        }
    }

    public void setOnBuyClickListener(OnClickBuy onClickBuy) {
        this.onClickBuy = onClickBuy;
    }
    public interface OnClickBuy {
        void onClick(ChooserModel productModel);
    }
}
