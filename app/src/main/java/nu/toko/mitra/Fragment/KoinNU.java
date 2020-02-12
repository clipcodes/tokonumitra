package nu.toko.mitra.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import nu.toko.mitra.Adapter.Product2Adapter;
import nu.toko.mitra.R;

public class KoinNU extends Fragment {

    RecyclerView rvproduct;
    Product2Adapter product2Adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.page_koinnu, container, false);

        init(root);

        return root;
    }
    
    void init(View root){
    }
}