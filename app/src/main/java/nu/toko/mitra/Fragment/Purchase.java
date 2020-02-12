package nu.toko.mitra.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import nu.toko.mitra.Adapter.PurchaseAdapter;
import nu.toko.mitra.Model.PurchaseModel;
import nu.toko.mitra.R;

public class Purchase extends Fragment {

    RecyclerView rvpurchase;
    PurchaseAdapter purchaseAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.page_purchase, container, false);

        init(root);

        return root;
    }

    void init(View root){
        rvpurchase = root.findViewById(R.id.rvpurchase);
        purchaseAdapter = new PurchaseAdapter(getActivity(), PurchaseModel.DataPurc());
        rvpurchase.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvpurchase.setAdapter(purchaseAdapter);
    }

}