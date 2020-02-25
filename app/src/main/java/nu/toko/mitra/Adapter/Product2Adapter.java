package nu.toko.mitra.Adapter;

        import android.app.Activity;
        import android.content.Intent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.FrameLayout;
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

public class Product2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Activity activity;
    List<ProductModelNU> items;
    OnClickBuy onClickBuy;
    private static final int MODEL_ONE = 1;
    private static final int MODEL_TWO = 2;

    public Product2Adapter(Activity activity, List<ProductModelNU> items){
        this.activity = activity;
        this.items = items;
    }


    @Override
    public int getItemViewType(int position) {
        if (Integer.valueOf(items.get(position).getStok())<=0){
            return MODEL_TWO;
        } else {
            return MODEL_ONE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MODEL_ONE) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelitem_product2, parent, false);
            return new One(itemView);
        } else if (viewType == MODEL_TWO) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelitem_product2habis, parent, false);
            return new Two(itemView);
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
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class One extends RecyclerView.ViewHolder {

        TextView title, price, totalfeedback, namatoko, lokasi, stock;
        ImageView thumb;
        RatingBar star;

        public One(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            thumb = itemView.findViewById(R.id.thumb);
            stock = itemView.findViewById(R.id.stock);
            star = itemView.findViewById(R.id.star);
            namatoko = itemView.findViewById(R.id.namatoko);
            lokasi = itemView.findViewById(R.id.lokasi);
            totalfeedback = itemView.findViewById(R.id.totalfeedback);
        }

        public void init(final ProductModelNU productModelNU){
            price.setText("Rp."+ Others.PercantikHarga(Integer.valueOf(productModelNU.getHarga_mitra()+productModelNU.getHarga_admin())));
            ImageLoader.getInstance().displayImage(FOTOPRODUK + productModelNU.getGambarfirst(), thumb);
            title.setText(productModelNU.getNama_produk());
            star.setRating(productModelNU.getRating());
            stock.setText(productModelNU.getStok()+" Stok");
            lokasi.setText(productModelNU.getDikirimdari());
            namatoko.setText(productModelNU.getOwner().getNama_toko_mitra());
            totalfeedback.setText("("+productModelNU.getTotalfeedback()+")");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(activity, Details.class);
                    i.putExtra(ID_PRODUK, productModelNU.getId_produk());
                    i.putExtra(HARGA_MITRA, productModelNU.getHarga_mitra());
                    i.putExtra(NAMA_PRODUK, productModelNU.getNama_produk());
                    activity.startActivity(i);
                }
            });
        }
    }

    protected class Two extends RecyclerView.ViewHolder {

        TextView title, price, totalfeedback, namatoko, lokasi, stock, diskon;
        ImageView thumb;
        RatingBar star;

        public Two(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            thumb = itemView.findViewById(R.id.thumb);
            stock = itemView.findViewById(R.id.stock);
            star = itemView.findViewById(R.id.star);
            namatoko = itemView.findViewById(R.id.namatoko);
            lokasi = itemView.findViewById(R.id.lokasi);
            totalfeedback = itemView.findViewById(R.id.totalfeedback);
        }

        public void init(final ProductModelNU productModelNU){
            price.setText("Rp."+ Others.PercantikHarga(Integer.valueOf(productModelNU.getHarga_mitra()+productModelNU.getHarga_admin())));
            ImageLoader.getInstance().displayImage(FOTOPRODUK + productModelNU.getGambarfirst(), thumb);
            title.setText(productModelNU.getNama_produk());
            star.setRating(productModelNU.getRating());
            stock.setText(productModelNU.getStok()+" Stok");
            lokasi.setText(productModelNU.getDikirimdari());
            namatoko.setText(productModelNU.getOwner().getNama_toko_mitra());
            totalfeedback.setText("("+productModelNU.getTotalfeedback()+")");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(activity, Details.class);
                    i.putExtra(ID_PRODUK, productModelNU.getId_produk());
                    i.putExtra(HARGA_MITRA, productModelNU.getHarga_mitra());
                    i.putExtra(NAMA_PRODUK, productModelNU.getNama_produk());
                    activity.startActivity(i);
                }
            });
        }
    }

    public void setOnBuyClickListener(OnClickBuy onClickBuy) {
        this.onClickBuy = onClickBuy;
    }
    public interface OnClickBuy {
        void onClick(ProductModel productModel);
    }
}