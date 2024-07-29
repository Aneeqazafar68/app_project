package com.example.apps.presentationLayer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apps.R;
import com.example.apps.logicLayer.Hotel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Vector;

public class Hotel_Selection_adapter extends RecyclerView.Adapter<Hotel_Selection_adapter.Hotel_Selection_Holder> {
    List<Hotel_Selection_row> ls;
    Context c;
    Vector<Hotel> hotels;

    public Hotel_Selection_adapter(List<Hotel_Selection_row> ls, Context c, Vector<Hotel> hotels) {
        this.c = c;
        this.ls = ls;
        this.hotels = hotels;
    }

    @NonNull
    @Override
    public Hotel_Selection_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(c).inflate(R.layout.hotel_selection_row, parent, false);
        return new Hotel_Selection_Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Hotel_Selection_Holder holder, int position) {
        holder.name.setText(ls.get(position).getName());
        holder.singlePrice.setText(ls.get(position).getSinglePrice());
        holder.doublePrice.setText(ls.get(position).getDoublePrice());
        holder.location.setText(ls.get(position).getLocation());
        holder.l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(c)
                        .setTitle("Confirm Booking")
                        .setMessage("Are you sure you want to Reserve this Hotel?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(c, Hotel_Reservation_Screen.class);
                                intent.putExtra("Email", ((Hotel_Selection) c).Email);
                                intent.putExtra("Hotel_name", ls.get(holder.getAdapterPosition()).getName());
                                intent.putExtra("Hotel_Loc", ls.get(holder.getAdapterPosition()).getLocation());
                                intent.putExtra("checkinDate", ((Hotel_Selection) c).checkInDate);
                                intent.putExtra("checkOutDate", ((Hotel_Selection) c).checkOutDate);
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                LocalDate localDate = LocalDate.parse(((Hotel_Selection) c).checkInDate, formatter);
                                LocalDate localDate1 = LocalDate.parse(((Hotel_Selection) c).checkOutDate, formatter);

                                ((Hotel_Selection) c).hrs.makeReservation(((Hotel_Selection) c).Email, hotels.get(holder.getAdapterPosition()), localDate, localDate1);
                                c.startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class Hotel_Selection_Holder extends RecyclerView.ViewHolder {
        TextView name, singlePrice, doublePrice, location;
        LinearLayout l1;

        public Hotel_Selection_Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            singlePrice = itemView.findViewById(R.id.single_price);
            doublePrice = itemView.findViewById(R.id.double_price);
            location = itemView.findViewById(R.id.Location_hotel);
            l1 = itemView.findViewById(R.id.hotel_sel_row_id);
        }
    }
}
