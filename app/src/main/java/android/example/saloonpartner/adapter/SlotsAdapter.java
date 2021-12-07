package android.example.saloonpartner.adapter;

import android.content.Context;


import android.example.saloonpartner.R;
import android.example.saloonpartner.common.Common;
import android.example.saloonpartner.model.SlotsModel;
import android.view.LayoutInflater;


import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import android.widget.PopupMenu;
import android.widget.TextView;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.List;

public class SlotsAdapter extends RecyclerView.Adapter<SlotsAdapter.HmSlotsViewHolder> {
    List<SlotsModel> list;
    Context context;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;


    public SlotsAdapter(List<SlotsModel> list, Context context) {
        this.list = list;
        this.context = context;
        firebaseDatabase = FirebaseDatabase.getInstance();

        String phone = Common.currentRecord.getPhoneNo();
        reference = firebaseDatabase.getReference("hmvendor").child(phone).child("days");


    }

    @NonNull
    @Override
    public HmSlotsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slots_design, parent, false);

        return new HmSlotsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HmSlotsViewHolder holder, int position) {


        holder.daySlot1.setText(list.get(position).getSlot1());
        holder.daySlot2.setText(list.get(position).getSlot2());
        holder.daySlot3.setText(list.get(position).getSlot3());
        holder.daySlot4.setText(list.get(position).getSlot4());
        holder.daySlot5.setText(list.get(position).getSlot5());
        holder.daySlot6.setText(list.get(position).getSlot6());

        holder.dateTxt.setText(list.get(position).getDateId());

        String date = holder.dateTxt.getText().toString();

        modifySchedule(holder.nine, date, "slot1");
        modifySchedule(holder.eleven, date, "slot2");
        modifySchedule(holder.one, date, "slot3");
        modifySchedule(holder.three, date, "slot4");
        modifySchedule(holder.five, date, "slot5");
        modifySchedule(holder.seven, date, "slot6");


    }


    private void modifySchedule(LinearLayout slot, String dateCh, String slotName) {

        slot.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, slot);
            popupMenu.getMenuInflater().inflate(R.menu.slotupdatemenu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {

                if (item.getItemId() == R.id.disable_slot) {
                    reference.child(dateCh).child(slotName).setValue("UnAvailable");
                } else if (item.getItemId() == R.id.enable_slot) {
                    reference.child(dateCh).child(slotName).setValue("Available");
                }

                return false;
            });
            popupMenu.show();

            return true;
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class HmSlotsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout item, nine, eleven, one, three, five, seven;


        TextView dateTxt;
        TextView daySlot1;
        TextView daySlot2;
        TextView daySlot3;
        TextView daySlot4;
        TextView daySlot5;
        TextView daySlot6;


        public HmSlotsViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTxt = itemView.findViewById(R.id.dayTxtView);
            daySlot1 = itemView.findViewById(R.id.daySlot1);
            daySlot2 = itemView.findViewById(R.id.daySlot2);
            daySlot3 = itemView.findViewById(R.id.daySlot3);
            daySlot4 = itemView.findViewById(R.id.daySlot4);
            daySlot5 = itemView.findViewById(R.id.daySlot5);
            daySlot6 = itemView.findViewById(R.id.daySlot6);


            nine = itemView.findViewById(R.id.nine);
            eleven = itemView.findViewById(R.id.eleven);
            one = itemView.findViewById(R.id.one);
            three = itemView.findViewById(R.id.three);
            five = itemView.findViewById(R.id.five);
            seven = itemView.findViewById(R.id.seven);


            item = itemView.findViewById(R.id.slots_layout_linear);


        }
    }
}
