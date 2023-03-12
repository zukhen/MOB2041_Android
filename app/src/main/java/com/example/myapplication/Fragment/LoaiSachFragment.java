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

import com.example.myapplication.Adapter.LoaiSachAdapter;
import com.example.myapplication.DAO.LoaiSachDAO;
import myapplication.R;
import com.example.myapplication.model.LoaiSach;

public class LoaiSachFragment extends Fragment {
    private ArrayList<LoaiSach> listLoaiSach;
    private LoaiSachDAO daoLoaiSach;
    private LoaiSachAdapter adapter;
    private FloatingActionButton floatingButtonAddLoaiSach;
    private ListView lvLoaiSach;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loai_sach, null, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        daoLoaiSach = new LoaiSachDAO(getContext());
        listLoaiSach = new ArrayList<>();
        findID(view);
        floatingButtonAddLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLoaiSachDialog(daoLoaiSach);
            }
        });
        capNhat();

        super.onViewCreated(view, savedInstanceState);
    }

    private void addLoaiSachDialog(LoaiSachDAO daoLoaiSach) {
//    khai bao
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.dialog_them_loai_sach, null);
        EditText edTenLS;
        TextView tvError;
        Button btnAddLS, btnCancel;
//      findID
        tvError = viewDialog.findViewById(R.id.tvError);
        edTenLS = viewDialog.findViewById(R.id.edTenLS);
        btnAddLS = viewDialog.findViewById(R.id.btnAddLS);
        btnCancel = viewDialog.findViewById(R.id.btnCancel);
//        set view
        builder.setView(viewDialog);
//        builer view
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        action
        btnAddLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoaiSach item = new LoaiSach();
                item.setTenLoai(edTenLS.getText().toString());
                if (!edTenLS.getText().toString().isEmpty()) {
                    if (daoLoaiSach.insertLoaiSach(item) > 0) {
                        Toast.makeText(getActivity(), "Lưu thành công", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Lưu thất bại", Toast.LENGTH_LONG).show();
                    }
                    capNhat();
                } else {
                    tvError.setText("Vui lòng không để trống");
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

    private void capNhat() {
        listLoaiSach.clear();
        listLoaiSach = (ArrayList<LoaiSach>) daoLoaiSach.getAllLoaiSach();
        adapter = new LoaiSachAdapter(listLoaiSach, getContext(), daoLoaiSach);
        lvLoaiSach.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void findID(View view) {
        floatingButtonAddLoaiSach = view.findViewById(R.id.floatingButtonAddLoaiSach);
        lvLoaiSach = view.findViewById(R.id.lvLoaiSach);
    }
}
