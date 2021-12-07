package android.example.saloonpartner.adapter;

import android.content.Context;
import android.example.saloonpartner.common.Common;
import android.example.saloonpartner.model.BookingModel;
import android.example.saloonpartner.model.HomeVendorModel;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class HmVendorAdapter extends RecyclerView.Adapter<HmVendorAdapter.HmVendorViewHolder> {

    List<HomeVendorModel> list;
    Context context;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;



    public HmVendorAdapter(List<HomeVendorModel> list, Context context) {
        this.list = list;
        this.context = context;
        firebaseDatabase = FirebaseDatabase.getInstance();

        reference = firebaseDatabase.getReference("hmvendor");

    }



    @NonNull
    @Override
    public HmVendorAdapter.HmVendorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull HmVendorAdapter.HmVendorViewHolder holder, int position) {
        HomeVendorModel homeVendorModel = new HomeVendorModel();
        homeVendorModel.setPhoneNo(list.get(position).getPhoneNo());


        homeVendorModel.setRegno(list.get(position).getRegno());
        homeVendorModel.setOwnerName(list.get(position).getOwnerName());
        homeVendorModel.setEmail(list.get(position).getEmail());

        homeVendorModel.setDocType(list.get(position).getDocType());
        homeVendorModel.setGovtidno(list.get(position).getGovtidno());
        homeVendorModel.setGidFrontPhoto(list.get(position).getGidFrontPhoto());
        homeVendorModel.setGidBackPhoto(list.get(position).getGidBackPhoto());

        homeVendorModel.setPhoneNo(list.get(position).getPhoneNo());
        homeVendorModel.setAddress(list.get(position).getAddress());
        homeVendorModel.setServiceList(list.get(position).getServiceList());

        homeVendorModel.setCity(list.get(position).getCity());
        homeVendorModel.setTime(list.get(position).getTime());
        homeVendorModel.setStatus(list.get(position).getStatus());


        homeVendorModel.setBankname(list.get(position).getBankname());
        homeVendorModel.setBankadress(list.get(position).getBankadress());
        homeVendorModel.setBenificaryname(list.get(position).getBenificaryname());
        homeVendorModel.setAccountNo(list.get(position).getAccountNo());
        homeVendorModel.setAccounttype(list.get(position).getAccounttype());
        homeVendorModel.setIfsccode(list.get(position).getIfsccode());
        homeVendorModel.setBankPhoto(list.get(position).getBankPhoto());


        homeVendorModel.setWorkImg(list.get(position).getWorkImg());


        Common.currentRecord = homeVendorModel;

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class HmVendorViewHolder extends RecyclerView.ViewHolder {
        public HmVendorViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
