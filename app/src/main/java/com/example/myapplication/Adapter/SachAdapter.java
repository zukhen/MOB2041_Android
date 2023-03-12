package com.example.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.myapplication.DAO.LoaiSachDAO;
import com.example.myapplication.DAO.SachDAO;
import com.example.myapplication.model.LoaiSach;
import com.example.myapplication.model.Sach;

import myapplication.R;

public class SachAdapter extends BaseAdapter {
    private ArrayList<Sach> listSach;
    private Context context;
    private SachDAO daoSach;
    private LinearLayout layout;
    //    spinner
    private LoaiSachDAO daoLoaiSach;
    private ArrayList<LoaiSach> listLoaiSach;
    public SachAdapter(ArrayList<Sach> listSach, Context context, SachDAO daoSach) {
        this.listSach = listSach;
        this.context = context;
        this.daoSach = daoSach;
    }

    @Override
    public int getCount() {
        return listSach.size();
    }

    @Override
    public Object getItem(int i) {
        return listSach.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listSach.get(i).getMaSach();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//      constructor
        ViewItemSach viewItemSach = new ViewItemSach();
        if (view == null) {
            view = inflater.inflate(R.layout.item_sach, null);
            layout = view.findViewById(R.id.layout);
            viewItemSach.spinner = view.findViewById(R.id.spinner);
            viewItemSach.tvTenSach = view.findViewById(R.id.tvTenSach);
            viewItemSach.tvGiaSach = view.findViewById(R.id.tvGiaSach);
            viewItemSach.tvTenLoai = view.findViewById(R.id.tvTenLoai);
            viewItemSach.imgDel = view.findViewById(R.id.imgDel);
            viewItemSach.imgEdit = view.findViewById(R.id.imgEdit);
//          luu tru view
            view.setTag(viewItemSach);
        } else {
            viewItemSach = (ViewItemSach) view.getTag();
        }
//        setText
        showData(viewItemSach, i);
        daoSach = new SachDAO(context);
        daoLoaiSach = new LoaiSachDAO(context);
        listLoaiSach = new ArrayList<>();
//        edit
        viewItemSach.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (daoSach.deleteSach(String.valueOf(listSach.get(i).getMaSach())) < 0) {
                    Toast.makeText(context, "Xoá thất bại", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                }
                loadDanhSach();

            }
        });


        layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                dialogChoice(listSach.get(i).getMaSach(),i);
                return true;
            }
        });
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void showData(ViewItemSach viewItemSach, int vitri) {
        viewItemSach.tvTenSach.setText("Tên sách\n" + listSach.get(vitri).getTenSach());
        viewItemSach.tvGiaSach.setText(listSach.get(vitri).getGiaThue() + "$");
        viewItemSach.tvTenLoai.setText(String.valueOf(listSach.get(vitri).getTenLoai()));
    }

    private void dialogChoice(int ma,int vitri) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.dialog_sua_sach, null);
        EditText edTenSach, edGiaThue;
        TextView tvError;
        Button btnEditSach, btnCancel;
        Spinner spinner;
//      findID
        spinner = viewDialog.findViewById(R.id.spinner);
        tvError = viewDialog.findViewById(R.id.tvError);
        edTenSach = viewDialog.findViewById(R.id.edTenSach);
        edGiaThue = viewDialog.findViewById(R.id.edGiaThue);
        btnEditSach = viewDialog.findViewById(R.id.btnEditSach);
        btnCancel = viewDialog.findViewById(R.id.btnCancel);
//        set view
        builder.setView(viewDialog);
        AlertDialog dialog = builder.create();
//        setText
        getDataLoaiSach(spinner);
        edTenSach.setText(String.valueOf(listSach.get(vitri).getTenSach()));
        edGiaThue.setText(String.valueOf(listSach.get(vitri).getGiaThue()));
//        action
        btnEditSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sach sach = new Sach();
                HashMap<String, Object> hs = (HashMap<String, Object>) spinner.getSelectedItem();
                int maloai = (int) hs.get("maLoai");


                sach.setTenSach(edTenSach.getText().toString());
                sach.setGiaThue(Integer.parseInt(edGiaThue.getText().toString()));
                sach.setMaLoai(maloai);
                   if (edGiaThue.getText().toString().isEmpty()||edTenSach.getText().toString().isEmpty()){
                       tvError.setText("Vui lòng không để trống");
                   }
                   else{
                       if (daoSach.updateSach(ma,sach) < 0) {
                           Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                       } else {
                           Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                           dialog.dismiss();
                       }
                   }
                    loadDanhSach();
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

    private void loadDanhSach() {
        listSach.clear();
        listSach = daoSach.getDSSach();
        notifyDataSetChanged();
    }

    private void getDataLoaiSach(Spinner spinner) {
        listLoaiSach = (ArrayList<LoaiSach>) daoLoaiSach.getAllLoaiSach();
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        for (LoaiSach loaiSach : listLoaiSach) {
            HashMap<String, Object> objLoaiSach = new HashMap<>();
            objLoaiSach.put("maLoai", loaiSach.getMaLoai());
            objLoaiSach.put("tenLoai", loaiSach.getTenLoai());
            list.add(objLoaiSach);
        }
        SimpleAdapter adapter = new SimpleAdapter(context, list, R.layout.item_spinner, new String[]{"tenLoai"}, new int[]{R.id.tvLoaiSach});
        spinner.setAdapter(adapter);
    }

    static class ViewItemSach {
        ImageView imgDel, imgEdit;
        Spinner spinner;
        TextView tvTenSach, tvGiaSach, tvTenLoai;

    }
}
