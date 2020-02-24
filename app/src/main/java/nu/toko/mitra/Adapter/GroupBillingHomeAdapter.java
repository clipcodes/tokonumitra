package nu.toko.mitra.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import nu.toko.mitra.Model.BillingModelNU;
import nu.toko.mitra.R;
import nu.toko.mitra.Utils.Others;

public class GroupBillingHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Activity activity;
    List<BillingModelNU> items;
    OnClick onItemClickListener;
    private static final int MODEL_ONE = 1;
    private static final int MODEL_TWO = 2;
    private static final int MODEL_THREE = 3;

    public GroupBillingHomeAdapter(Activity activity, List<BillingModelNU> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).getStatus_transaksi()<=3){
            return MODEL_ONE;
        } else if (items.get(position).getStatus_transaksi()==4) {
            return MODEL_TWO;
        }  else {
            return MODEL_THREE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MODEL_ONE) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelitem_groupbillhome, parent, false);
            return new One(itemView);
        } else if (viewType == MODEL_TWO) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelitem_groupbillhome4, parent, false);
            return new Two(itemView);
        } else if (viewType == MODEL_THREE) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelitem_groupbillhome5, parent, false);
            return new Three(itemView);
        } else return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof One) {
            One one = (One) holder;
            one.init(items.get(position));
        } else if (holder instanceof Two) {
            Two one = (Two) holder;
            one.init(items.get(position));
        } else if (holder instanceof Three) {
            Three one = (Three) holder;
            one.init(items.get(position));
        }
    }


    protected class One extends RecyclerView.ViewHolder {
 
        CircleImageView paystatus, waitingstatus, packingstatus, deliverystatus, donestatus;
        RecyclerView itemtrans;
        BillingItemAdapter bilitemadap;
        TextView tanggalpemesanan, statustex;
        CardView lihat;

        public One(View itemView) {
            super(itemView);
            itemtrans = itemView.findViewById(R.id.itemtrans);
            tanggalpemesanan = itemView.findViewById(R.id.tanggalpemesanan);
            statustex = itemView.findViewById(R.id.statustex);
            paystatus = itemView.findViewById(R.id.paystatus);
            waitingstatus = itemView.findViewById(R.id.waitingstatus);
            packingstatus = itemView.findViewById(R.id.packingstatus);
            deliverystatus = itemView.findViewById(R.id.deliverystatus);
            donestatus = itemView.findViewById(R.id.donestatus);
            lihat = itemView.findViewById(R.id.lihat);
        }

        public void init(final BillingModelNU productModelNU){
            tanggalpemesanan.setText(productModelNU.getTgl_pemesanan());
            statustex.setText(Others.TransactionStatus(productModelNU.getStatus_transaksi()));

            bilitemadap = new BillingItemAdapter(activity, productModelNU.getBillingItemModels());
            itemtrans.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
            itemtrans.setAdapter(bilitemadap);

            lihat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(productModelNU);
                }
            });

            if (productModelNU.getStatus_transaksi()==1){
                paystatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            }
            if (productModelNU.getStatus_transaksi()==2){
                paystatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
                waitingstatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            }
            if (productModelNU.getStatus_transaksi()==3){
                paystatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
                waitingstatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
                packingstatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
                deliverystatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            }
            if (productModelNU.getStatus_transaksi()==4){
                paystatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
                waitingstatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
                packingstatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
                deliverystatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
                donestatus.setColorFilter(activity.getResources().getColor(R.color.googlecolor));
            }
            if (productModelNU.getStatus_transaksi()==5){
                paystatus.setColorFilter(activity.getResources().getColor(R.color.merah));
                waitingstatus.setColorFilter(activity.getResources().getColor(R.color.merah));
                packingstatus.setColorFilter(activity.getResources().getColor(R.color.merah));
                deliverystatus.setColorFilter(activity.getResources().getColor(R.color.merah));
                donestatus.setColorFilter(activity.getResources().getColor(R.color.merah));
            }
        }
    }

    protected class Two extends RecyclerView.ViewHolder {

        RecyclerView itemtrans;
        BillingItemAdapter bilitemadap;
        TextView tanggalpemesanan, statustex;
        CardView lihat;

        public Two(View itemView) {
            super(itemView);
            itemtrans = itemView.findViewById(R.id.itemtrans);
            tanggalpemesanan = itemView.findViewById(R.id.tanggalpemesanan);
            statustex = itemView.findViewById(R.id.statustex);
            lihat = itemView.findViewById(R.id.lihat);
        }

        public void init(final BillingModelNU productModelNU){
            tanggalpemesanan.setText(productModelNU.getTgl_pemesanan());
            statustex.setText(Others.TransactionStatus(productModelNU.getStatus_transaksi()));

            bilitemadap = new BillingItemAdapter(activity, productModelNU.getBillingItemModels());
            itemtrans.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
            itemtrans.setAdapter(bilitemadap);

            lihat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(productModelNU);
                }
            });
        }
    }

    protected class Three extends RecyclerView.ViewHolder {

        RecyclerView itemtrans;
        BillingItemAdapter bilitemadap;
        TextView tanggalpemesanan, statustex;
        CardView lihat;

        public Three(View itemView) {
            super(itemView);
            itemtrans = itemView.findViewById(R.id.itemtrans);
            tanggalpemesanan = itemView.findViewById(R.id.tanggalpemesanan);
            statustex = itemView.findViewById(R.id.statustex);
            lihat = itemView.findViewById(R.id.lihat);
        }

        public void init(final BillingModelNU productModelNU){
            tanggalpemesanan.setText(productModelNU.getTgl_pemesanan());
            statustex.setText(Others.TransactionStatus(productModelNU.getStatus_transaksi()));

            bilitemadap = new BillingItemAdapter(activity, productModelNU.getBillingItemModels());
            itemtrans.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
            itemtrans.setAdapter(bilitemadap);


        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(OnClick clickListener) {
        onItemClickListener = clickListener;
    }
    public interface OnClick {
        void onItemClick(BillingModelNU billingModelNU);
    }
}
