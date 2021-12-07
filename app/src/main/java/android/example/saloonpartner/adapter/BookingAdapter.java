package android.example.saloonpartner.adapter;

import android.content.Context;
import android.content.Intent;
import android.example.saloonpartner.BookingDetailsActivity;
import android.example.saloonpartner.R;
import android.example.saloonpartner.common.Common;
import android.example.saloonpartner.model.BookingModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder>{
    List<BookingModel> list;
    Context context;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;



    public BookingAdapter(List<BookingModel> list, Context context) {
        this.list = list;
        this.context = context;
        firebaseDatabase = FirebaseDatabase.getInstance();

        reference = firebaseDatabase.getReference("bookings");

    }





    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookings_preview_design, parent, false);

        return new BookingViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {

        holder.userName.setText(list.get(position).getUserName());
        holder.userCity.setText(list.get(position).getUserCity());
        holder.userAddress.setText(list.get(position).getUserAddress());
        holder.status.setText(list.get(position).getStatus());

        String bookId = "B.ID "+list.get(position).getBookingId();
        holder.bookingId.setText(bookId);

        String dateS = "Date "+list.get(position).getDateSlot();
        holder.dateSlot.setText(dateS);

        String timeS = "Time "+list.get(position).getTimeSlot();
        holder.timeSlot.setText(timeS);
        String price = "Price Paid ₹"+list.get(position).getPricePaid();
        holder.pricePaid.setText(price);


        holder.item.setOnClickListener(v -> {
            BookingModel bookingModel = new BookingModel();
            bookingModel.setBookingId(list.get(position).getBookingId());
            bookingModel.setUserName(list.get(position).getUserName());
            bookingModel.setUserCity(list.get(position).getUserCity());
            bookingModel.setUserAddress(list.get(position).getUserAddress());
            bookingModel.setStatus(list.get(position).getStatus());
            bookingModel.setDateSlot(list.get(position).getDateSlot());
            bookingModel.setTimeSlot(list.get(position).getTimeSlot());
            bookingModel.setPricePaid(list.get(position).getPricePaid());
            bookingModel.setUserPhone(list.get(position).getUserPhone());
            bookingModel.setItemList(list.get(position).getItemList());

            Common.currentBooking=bookingModel;


            Intent intent = new Intent(context, BookingDetailsActivity.class);
            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        LinearLayout item;
      TextView userName,userCity,userAddress,status,bookingId,dateSlot,timeSlot,pricePaid;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);

            item   = itemView.findViewById(R.id.bookingsPreviewTemplate);

            userName   = itemView.findViewById(R.id.userNamePr);
            userCity   = itemView.findViewById(R.id.cityPr);
            userAddress   = itemView.findViewById(R.id.addressPr);
            status   = itemView.findViewById(R.id.statusPr);
            dateSlot   = itemView.findViewById(R.id.dateSlotPrv);
            timeSlot   = itemView.findViewById(R.id.timeSlotPrv);
            pricePaid   = itemView.findViewById(R.id.pricePaidPrv);
            bookingId   = itemView.findViewById(R.id.bookingIdPrv);



        }
    }
}
