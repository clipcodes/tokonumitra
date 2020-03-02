package nu.toko.mitra.Adapter;

import android.app.Activity;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nu.toko.mitra.Model.PhotoModel;
import nu.toko.mitra.R;

import static nu.toko.mitra.Utils.Staticvar.FOTOPRODUK;

public class PhotoAddAdap extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity activity;
    saatKlik saatKlik;
    List<PhotoModel> items;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;
    private static final int TYPE_NOEDIT = 3;

    public PhotoAddAdap(Activity activity, List<PhotoModel> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelitem_photo, parent, false);
            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_FOOTER) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelitem_photonull, parent, false);
            return new FooterViewHolder(itemView);
        } if (viewType == TYPE_NOEDIT) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelitem_photo, parent, false);
            return new NoEditHolder(itemView);
        } else return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder headerHolder = (ItemViewHolder) holder;
            headerHolder.init(position, items.get(position));
        } else if (holder instanceof NoEditHolder) {
            NoEditHolder footerHolder = (NoEditHolder) holder;
            footerHolder.init(position, items.get(position));
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            footerHolder.init();
        }
    }

    @Override
    public int getItemCount() {
        return items.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == items.size()) {
            return TYPE_FOOTER;
        }
        if (items.get(position).getEditurl()!=null){
            return TYPE_NOEDIT;
        }
        return TYPE_ITEM;
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

        public void init(){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saatKlik.terKlik();
                }
            });
        }
    }

    private class NoEditHolder extends RecyclerView.ViewHolder {

        ImageView image, delete;

        public NoEditHolder(View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.delete);
            image = itemView.findViewById(R.id.image);
        }

        public void init(final int position, PhotoModel photoModel){
            ImageLoader.getInstance().displayImage( FOTOPRODUK + photoModel.getEditurl(), image);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saatKlik.deleteefit(position, items.get(position).getEditurl());
                }
            });
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView image, delete;

        public ItemViewHolder(View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.delete);
            image = itemView.findViewById(R.id.image);
        }

        public void init(final int position, PhotoModel photoModel){
            image.setImageBitmap(photoModel.getBitmap());
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saatKlik.delete(position);
                }
            });
        }
    }

    public void Klik(saatKlik clickListener) {
        saatKlik = clickListener;
    }

    public interface saatKlik {
        void terKlik();
        void delete(int position);
        void deleteefit(int pos, String url);
    }
}