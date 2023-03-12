package com.example.myapplication.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.myapplication.Adapter.SachAdapter;
import com.example.myapplication.DAO.LoaiSachDAO;
import com.example.myapplication.DAO.SachDAO;import myapplication.R;
import com.example.myapplication.model.LoaiSach;
import com.example.myapplication.model.Sach;

public class SachFragment extends Fragment {
    private GridView lvSach;
    private FloatingActionButton floatingButtonAddSach;
    private ArrayList<Sach> listSach;
    private SachDAO daoSach;
    private SachAdapter adapter;
    //    spinner
    private ArrayList<LoaiSach> listLoaiSach;
    private LoaiSachDAO daoLoaiSach;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sach, null, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listSach = new ArrayList<>();
        listLoaiSach = new ArrayList<>();
        daoSach = new SachDAO(getContext());
        daoLoaiSach = new LoaiSachDAO(getContext());
        findID(view);
        capNhat();
        floatingButtonAddSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSachDialog();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void addSachDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.dialog_them_sach, null);
        TextView tvError;
        EditText  edTenSach, edGiaThue;
        Spinner spinner;
        Button btnCreate, btnCancel;
//        anh xa
        tvError = viewDialog.findViewById(R.id.tvError);
        edTenSach = viewDialog.findViewById(R.id.edTenSach);
        edGiaThue = viewDialog.findViewById(R.id.edGiaThue);
        spinner = viewDialog.findViewById(R.id.spinner);
        btnCreate = viewDialog.findViewById(R.id.btnCreate);
        btnCancel = viewDialog.findViewById(R.id.btnCancel);
//        set view
        SimpleAdapter adapter = new SimpleAdapter(getContext(),getDataLoaiSach(),R.layout.item_spinner,new String[]{"tenLoai"},new int[]{R.id.tvLoaiSach});
        spinner.setAdapter(adapter);

        builder.setView(viewDialog);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        action
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sach sach = new Sach();
                HashMap<String,Object>hs = (HashMap<String, Object>) spinner.getSelectedItem();
                sach.setMaLoai((int) hs.get("maLoai"));
                sach.setTenSach(edTenSach.getText().toString());
                sach.setGiaThue(Integer.parseInt(edGiaThue.getText().toString()));
                if (validate(tvError, edTenSach, edGiaThue) > 0) {
                    if (daoSach.insertSach(sach)>0)
                        Toast.makeText(getActivity(), "Lưu thành công", Toast.LENGTH_LONG).show();

                    dialog.dismiss();
                    capNhat();
                } else {
                    Toast.makeText(getActivity(), "Lưu thất bại", Toast.LENGTH_LONG).show();
                    edTenSach.setText("");
                    edGiaThue.setText("");
                }
            }
        });
        dialog.show();
    }

    private int validate(TextView tvError, EditText edTenSach, EditText edGiaThue) {
        int check = 1;
        if (
                edTenSach.getText().toString().isEmpty() ||
                edGiaThue.getText().toString().isEmpty()
        ) {
            tvError.setText("Vui lòng không để trống");
            check = -1;
        }
        return check;
    }

    private ArrayList<HashMap<String,Object>> getDataLoaiSach() {
        listLoaiSach.clear();
        listLoaiSach = (ArrayList<LoaiSach>) daoLoaiSach.getAllLoaiSach();
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        for (LoaiSach loaiSach : listLoaiSach) {
            HashMap<String, Object> objLoaiSach = new HashMap<>();
            objLoaiSach.put("maLoai", loaiSach.getMaLoai());
            objLoaiSach.put("tenLoai", loaiSach.getTenLoai());
            list.add(objLoaiSach);
        }return list;
    }

    private void capNhat() {
        listSach = daoSach.getDSSach();
        adapter = new SachAdapter(listSach, getContext(), daoSach);
        lvSach.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void findID(View view) {
        lvSach = view.findViewById(R.id.lvSach);
        floatingButtonAddSach = view.findViewById(R.id.floatingButtonAddSach);
    }
}
