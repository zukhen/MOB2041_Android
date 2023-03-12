package com.example.myapplication.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import com.example.myapplication.Adapter.ThanhVienAdapter;
import com.example.myapplication.DAO.ThanhVienDAO;import myapplication.R;
import com.example.myapplication.model.ThanhVien;

public class ThanhVienFragment extends Fragment {
    private ListView lvThanhVien;
    private FloatingActionButton floatingButtonAddTV;
    private ArrayList<ThanhVien> listThanhVien;
    private ThanhVienDAO daoThanhVien;
    private ThanhVienAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thanhvien, null, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listThanhVien = new ArrayList<>();
        daoThanhVien = new ThanhVienDAO(getContext());
        findID(view);
        capNhatLv();
        floatingButtonAddTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDiaLog(daoThanhVien);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void openDiaLog(ThanhVienDAO daoThanhVien) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.dialog_them_thanh_vien, null);
        EditText edTenTV, edNamSinh;
        Button btnCreate, btnCancel;
//        findID
        edTenTV = viewDialog.findViewById(R.id.edTenTV);
        edNamSinh = viewDialog.findViewById(R.id.edNamSinh);
        btnCreate = viewDialog.findViewById(R.id.btnCreate);
        btnCancel = viewDialog.findViewById(R.id.btnCancel);
//        set view
        builder.setView(viewDialog);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        event
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThanhVien item = new ThanhVien();
                item.setHoTen(edTenTV.getText().toString());
                item.setNamSinh(edNamSinh.getText().toString());
                if (validate(viewDialog) > 0) {
                    if (daoThanhVien.insertThanhVien(item) > 0) {
                        Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        capNhatLv();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        dialog.show();
    }


    private int validate(View viewDialog) {
        int check = 1;
        String regexNamSinh = "^\\d{4}$";
        TextView tvError;
        EditText edTenTV, edNamSinh;
        edTenTV = viewDialog.findViewById(R.id.edTenTV);
        edNamSinh = viewDialog.findViewById(R.id.edNamSinh);
        tvError = viewDialog.findViewById(R.id.tvError);
        if (edTenTV.getText().toString().isEmpty() || edNamSinh.getText().toString().isEmpty()) {
            tvError.setText("Bạn phải nhập đầy đủ thông tin");
            check = -1;
        } else {
            if (!edNamSinh.getText().toString().matches(regexNamSinh)) {
                tvError.setText("Bạn phải nhập đúng định dạng năm sinh 4 ký tự");
                check = -1;
            }
        }
        return check;
    }

    private void findID(View view) {
        floatingButtonAddTV = view.findViewById(R.id.floatingButtonAddTV);
        lvThanhVien = view.findViewById(R.id.lvThanhVien);
    }

    private void capNhatLv() {
        listThanhVien.clear();
        listThanhVien = (ArrayList<ThanhVien>) daoThanhVien.getAllThanhVien();
        adapter = new ThanhVienAdapter(listThanhVien, getActivity(), daoThanhVien);
        lvThanhVien.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
