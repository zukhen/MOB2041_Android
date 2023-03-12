package com.example.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

import com.example.myapplication.DAO.ThanhVienDAO;
import com.example.myapplication.model.ThanhVien;

import myapplication.R;

public class ThanhVienAdapter extends BaseAdapter {
    private ArrayList<ThanhVien> listThanhVien;
    private Context context;
    private ThanhVienDAO daoThanhVien;

    public ThanhVienAdapter(ArrayList<ThanhVien> listThanhVien, Context context, ThanhVienDAO daoThanhVien) {
        this.listThanhVien = listThanhVien;
        this.context = context;
        this.daoThanhVien = daoThanhVien;
    }

    @Override
    public int getCount() {
        return listThanhVien.size();
    }

    @Override
    public Object getItem(int i) {
        return listThanhVien.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listThanhVien.get(i).getMaTV();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
//        tao constructor
        ViewItemThanhVien viewItemThanhVien = new ViewItemThanhVien();
//        layout
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        if (view == null) {
            view = inflater.inflate(R.layout.item_thanh_vien, null);

            viewItemThanhVien.tvTenThanhVien = view.findViewById(R.id.tvTenThanhVien);
            viewItemThanhVien.tvNamSinh = view.findViewById(R.id.tvNamSinh);
            viewItemThanhVien.imgEdit = view.findViewById(R.id.imgEdit);
            viewItemThanhVien.imgDel = view.findViewById(R.id.imgDel);
            viewItemThanhVien.avatarItem = view.findViewById(R.id.avatarItem);

//          luu tru view
            view.setTag(viewItemThanhVien);
        } else {
            viewItemThanhVien = (ViewItemThanhVien) view.getTag();
        }
        showData(viewItemThanhVien, position);
        daoThanhVien = new ThanhVienDAO(context);
//        button xoa
        viewItemThanhVien.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long kq = daoThanhVien.delete(String.valueOf(listThanhVien.get(position).getMaTV()));
                if (kq < 0) {
                    Toast.makeText(context, "Xoá thất bại", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                }
                loadDanhsach();

            }
        });
//        btn Update
        viewItemThanhVien.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editThanhVienDialog(listThanhVien.get(position).getMaTV(), position);
            }
        });
//        show information
        viewItemThanhVien.tvTenThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(position);
            }
        });
        viewItemThanhVien.avatarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(position);

            }
        });
        viewItemThanhVien.tvNamSinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(position);
            }
        });
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void showDialog(int vitri) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.dialog_thong_tin_thanh_vien, null);
        TextView tvIDThanhVien, tvTenTTThanhVien, tvTTNamSinh;
//        findID
        tvIDThanhVien = viewDialog.findViewById(R.id.tvIDThanhVien);
        tvTenTTThanhVien = viewDialog.findViewById(R.id.tvTenTTThanhVien);
        tvTTNamSinh = viewDialog.findViewById(R.id.tvTTNamSinh);
//        setThongTin
        tvIDThanhVien.setText("Mã TV: " + listThanhVien.get(vitri).getMaTV());
        tvTenTTThanhVien.setText("Họ và tên: " + listThanhVien.get(vitri).getHoTen());
        tvTTNamSinh.setText("Năm sinh: " + listThanhVien.get(vitri).getNamSinh());
        //        set view
        builder.setView(viewDialog);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void editThanhVienDialog(int ma, int vitri) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.dialog_sua_thanh_vien, null);
        TextView tvError;
        EditText edSuaTenTV, edSuaNamSinh;
        Button btnEdit, btnCancel;
        //        findID
        tvError = viewDialog.findViewById(R.id.tvError);
        edSuaTenTV = viewDialog.findViewById(R.id.edSuaTenTV);
        edSuaNamSinh = viewDialog.findViewById(R.id.edSuaNamSinh);
        btnEdit = viewDialog.findViewById(R.id.btnEdit);
        btnCancel = viewDialog.findViewById(R.id.btnCancel);
        //        set view
        builder.setView(viewDialog);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        set text
        edSuaTenTV.setText(String.valueOf(listThanhVien.get(vitri).getHoTen()));
        edSuaNamSinh.setText(String.valueOf(listThanhVien.get(vitri).getNamSinh()));
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regexNamSinh = "^\\d{4}$";
                ThanhVien item = new ThanhVien();
                item.setHoTen(edSuaTenTV.getText().toString());
                item.setNamSinh(edSuaNamSinh.getText().toString());
                if (edSuaTenTV.getText().toString().isEmpty() || edSuaNamSinh.getText().toString().isEmpty()) {
                    tvError.setText("Bạn phải nhập đầy đủ thông tin");
                } else {
                    if (!edSuaNamSinh.getText().toString().matches(regexNamSinh)) {
                        tvError.setText("Bạn phải nhập đúng định dạng năm sinh 4 ký tự");
                    } else {

                        if (daoThanhVien.updateThanhVien(item, ma) < 0) {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                    loadDanhsach();

                }
            }
        });
        dialog.show();
    }

    static class ViewItemThanhVien {
        TextView tvTenThanhVien, tvNamSinh;
        ImageView imgEdit, imgDel,avatarItem;
    }

    private void showData(ViewItemThanhVien viewItemThanhVien, int position) {
        viewItemThanhVien.tvTenThanhVien.setText(String.valueOf(listThanhVien.get(position).getHoTen()));
        viewItemThanhVien.tvNamSinh.setText(String.valueOf(listThanhVien.get(position).getNamSinh()));
    }

    private void loadDanhsach() {
        listThanhVien.clear();
        listThanhVien = (ArrayList<ThanhVien>) daoThanhVien.getAllThanhVien();
        notifyDataSetChanged();
    }

}
