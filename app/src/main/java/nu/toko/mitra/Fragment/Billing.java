package nu.toko.mitra.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import nu.toko.mitra.Adapter.BillingAdapter;
import nu.toko.mitra.Model.BillingModel;
import nu.toko.mitra.R;

public class Billing extends Fragment {

    RecyclerView rvbill;
    BillingAdapter billingAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.page_billing, container, false);

        init(root);

        return root;
    }

    void init(View root){
        rvbill = root.findViewById(R.id.rvbill);
        billingAdapter = new BillingAdapter(getActivity(), BillingModel.DataBill());
        rvbill.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvbill.setAdapter(billingAdapter);
    }

}