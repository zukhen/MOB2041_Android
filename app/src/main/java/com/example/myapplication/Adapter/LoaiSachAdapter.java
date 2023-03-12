package com.example.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

import com.example.myapplication.DAO.LoaiSachDAO;
import com.example.myapplication.model.LoaiSach;
import myapplication.R;

public class LoaiSachAdapter extends BaseAdapter {
    private ArrayList<LoaiSach> listLoaiSach;
    private Context context;
    private LoaiSachDAO daoLoaiSach;

    public LoaiSachAdapter(ArrayList<LoaiSach> listLoaiSach, Context context, LoaiSachDAO daoLoaiSach) {
        this.listLoaiSach = listLoaiSach;
        this.context = context;
        this.daoLoaiSach = daoLoaiSach;
    }

    @Override
    public int getCount() {
        return listLoaiSach.size();
    }

    @Override
    public Object getItem(int i) {
        return listLoaiSach.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listLoaiSach.get(i).getMaLoai();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//        tao constructor
        ViewItemLoaiSach viewItemLoaiSach = new ViewItemLoaiSach();
        if (view == null) {
            view = inflater.inflate(R.layout.item_loai_sach, null);
            viewItemLoaiSach.tvTenLoaiSach = view.findViewById(R.id.tvTenLoaiSach);
            viewItemLoaiSach.imgEdit = view.findViewById(R.id.imgEdit);
            viewItemLoaiSach.imgDel = view.findViewById(R.id.imgDel);
//          luu tru view
            view.setTag(viewItemLoaiSach);
        } else {
            viewItemLoaiSach = (ViewItemLoaiSach) view.getTag();
        }
        showData(viewItemLoaiSach, i);
        daoLoaiSach = new LoaiSachDAO(context);
//        button edit
        viewItemLoaiSach.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editLoaiSachDIalog(i,listLoaiSach.get(i).getMaLoai());
            }
        });
//        button xoa
        viewItemLoaiSach.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (daoLoaiSach.deleteLoaiSach(String.valueOf(listLoaiSach.get(i).getMaLoai())) < 0) {
                    Toast.makeText(context, "Xoá thất bại", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                }
                loadDanhSach();
            }
        });
        return view;
    }

    private void editLoaiSachDIalog(int vitri,int ma) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.dialog_sua_loai_sach, null);
        Button btnCancel, btnSuaLS;
        TextView tvError;
        EditText edSuaTenLS;
//        anh xa
        btnSuaLS = viewDialog.findViewById(R.id.btnSuaLS);
        btnCancel = viewDialog.findViewById(R.id.btnCancel);
        tvError = viewDialog.findViewById(R.id.tvError);
        edSuaTenLS = viewDialog.findViewById(R.id.edSuaTenLS);
//        set view
        builder.setView(viewDialog);
        AlertDialog dialog = builder.create();
//        set Text
        edSuaTenLS.setText(String.valueOf(listLoaiSach.get(vitri).getTenLoai()));
//        action
        btnSuaLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoaiSach loaiSach = new LoaiSach();
                loaiSach.setTenLoai(edSuaTenLS.getText().toString());
                if (edSuaTenLS.getText().toString().isEmpty()) {
                    tvError.setText("Vui lòng không để trống");
                } else {
                    if (daoLoaiSach.updateLoaiSach(loaiSach,ma) < 0) {
                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    loadDanhSach();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
    private void loadDanhSach(){
        listLoaiSach.clear();
        listLoaiSach = (ArrayList<LoaiSach>) daoLoaiSach.getAllLoaiSach();
        notifyDataSetChanged();
    }
    private void showData(ViewItemLoaiSach viewItemLoaiSach, int i) {
        viewItemLoaiSach.tvTenLoaiSach.setText(listLoaiSach.get(i).getTenLoai());
    }

    static class ViewItemLoaiSach {
        TextView tvTenLoaiSach;
        ImageView imgDel, imgEdit;
    }
}
