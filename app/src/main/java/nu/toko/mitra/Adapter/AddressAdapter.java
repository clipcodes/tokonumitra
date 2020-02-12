package nu.toko.mitra.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nu.toko.mitra.Model.AddressModel;
import nu.toko.mitra.R;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder>{

    Activity activity;
    List<AddressModel> items;
    OnClick onItemClickListener;

    public AddressAdapter(Activity activity, List<AddressModel> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modelitem_address, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.address.setText(items.get(position).getAddress());
        holder.additional.setText(items.get(position).getProvinsi()+", "+items.get(position).getKotakab()+", "+items.get(position).getKec());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(items.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        TextView additional, address;

        public ViewHolder(final View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address);
            additional = itemView.findViewById(R.id.additional);
        }
    }

    public void setOnItemClickListener(OnClick clickListener) {
        onItemClickListener = clickListener;
    }
    public interface OnClick {
        void onItemClick(AddressModel addressModel);
    }
}
