package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import myapplication.R;

import com.example.myapplication.model.Top;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.UserViewHolder> {
    private Context context;
    private ArrayList<Top> arrayList;

    public TopAdapter(Context context)
    {
        this.context = context;
    }
    public void setData(ArrayList<Top> arrayList)
    {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thong_ke_sach, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Top top = arrayList.get(position);
        if (top == null) return;

        holder.tenSach.setText("Tên sách\n"+top.getTenSach());
        holder.soLanMuon.setText("Số lần mượn: "+top.getSoLuong());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView tenSach;
        private TextView soLanMuon;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tenSach = itemView.findViewById(R.id.tenSach);
            soLanMuon = itemView.findViewById(R.id.soLanMuon);

        }
    }
}


