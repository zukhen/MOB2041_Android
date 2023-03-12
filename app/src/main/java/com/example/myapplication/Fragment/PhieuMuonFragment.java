package com.example.myapplication.Fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.example.myapplication.Adapter.PhieuMuonAdapter;
import com.example.myapplication.DAO.PhieuMuonDAO;
import com.example.myapplication.DAO.SachDAO;
import com.example.myapplication.DAO.ThanhVienDAO;import myapplication.R;
import com.example.myapplication.model.PhieuMuon;
import com.example.myapplication.model.Sach;
import com.example.myapplication.model.ThanhVien;

public class PhieuMuonFragment extends Fragment {
    private ListView listViewPhieuMuon;
    private PhieuMuonDAO daoPhieuMuon;
    private PhieuMuonAdapter adapter;
    private ArrayList<PhieuMuon> phieuMuonArrayList;
    private FloatingActionButton floatingButtonPhieuMuon;
    private ArrayList<ThanhVien> listThanhVien;
    private ArrayList<Sach> listSach;
    private SachDAO daoSach;
    private ThanhVienDAO daoThanhVien;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phieumuon, null, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findID(view);
        phieuMuonArrayList = new ArrayList<>();
        daoPhieuMuon = new PhieuMuonDAO(getContext());
        listThanhVien = new ArrayList<>();
        listSach = new ArrayList<>();
        daoSach = new SachDAO(getContext());
        daoThanhVien = new ThanhVienDAO(getActivity());
        capNhat();
        floatingButtonPhieuMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPhieuMuonDiaLOg();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void addPhieuMuonDiaLOg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.dialog_them_phieu_muon, null);
        EditText edNgayMuon,edGiaMuon;
        Spinner spinnerTV, spinnerSach;
        CheckBox chkTraSach;
        TextView tvError;
        ImageView date_picker_actions;
        Button btnCreate, btnCancel;
//        anh xa
        edGiaMuon = viewDialog.findViewById(R.id.edGiaMuon);
        edNgayMuon = viewDialog.findViewById(R.id.edNgayMuon);
        spinnerTV = viewDialog.findViewById(R.id.spinnerTV);
        spinnerSach = viewDialog.findViewById(R.id.spinnerSach);
        chkTraSach = viewDialog.findViewById(R.id.chkTraSach);
        tvError = viewDialog.findViewById(R.id.tvError);
        btnCreate = viewDialog.findViewById(R.id.btnCreate);
        btnCancel = viewDialog.findViewById(R.id.btnCancel);

        date_picker_actions = viewDialog.findViewById(R.id.date_picker_actions);
//        set spinner
        SimpleAdapter adapterTV = new SimpleAdapter(getContext(), getDataThanhVien(), R.layout.item_spinner, new String[]{"hoTen"}, new int[]{R.id.tvLoaiSach});
        SimpleAdapter adapterSach = new SimpleAdapter(getContext(), getDataSach(), R.layout.item_spinner, new String[]{"tenSach"}, new int[]{R.id.tvLoaiSach});
        spinnerTV.setAdapter(adapterTV);
        spinnerSach.setAdapter(adapterSach);
//        set view
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
        chkTraSach.setChecked(true);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhieuMuon phieuMuon = new PhieuMuon();
                HashMap<String, Object> hs = (HashMap<String, Object>) spinnerTV.getSelectedItem();
                HashMap<String, Object> hv = (HashMap<String, Object>) spinnerSach.getSelectedItem();
                phieuMuon.setTienThue(Integer.parseInt(edGiaMuon.getText().toString()));
                phieuMuon.setMaTV((int) hs.get("maTV"));
                phieuMuon.setMaSach((int) hv.get("maSach"));
                if (edNgayMuon.getText().toString().isEmpty()) {
                    tvError.setText("Vui lòng không để trống");
                } else {
                    try {
                        phieuMuon.setNgay(simpleDateFormat.parse(edNgayMuon.getText().toString()));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    phieuMuon.setTraSach(chkTraSach.isChecked() ? 1 : 0);
                    if (daoPhieuMuon.insertPhieuMuon(phieuMuon) > 0) {
                        Toast.makeText(getActivity(), "Lưu thành công", Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                    } else {
                        Toast.makeText(getActivity(), "Lưu thất bại", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                    capNhat();
                }
            }
        });

        date_picker_actions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i, i1, i2);
                        edNgayMuon.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


        dialog.show();
    }

    private ArrayList<HashMap<String, Object>> getDataSach() {
        listSach.clear();
        listSach = (ArrayList<Sach>) daoSach.getAllSach();
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        for (Sach sach : listSach) {
            HashMap<String, Object> objLoaiSach = new HashMap<>();
            objLoaiSach.put("maSach", sach.getMaSach());
            objLoaiSach.put("tenSach", sach.getTenSach());
            objLoaiSach.put("giaThue", sach.getGiaThue());
            objLoaiSach.put("maLoai", sach.getMaLoai());
            list.add(objLoaiSach);
        }
        return list;
    }

    private ArrayList<HashMap<String, Object>> getDataThanhVien() {
        listThanhVien.clear();
        listThanhVien = (ArrayList<ThanhVien>) daoThanhVien.getAllThanhVien();
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        for (ThanhVien thanhVien : listThanhVien) {
            HashMap<String, Object> objLoaiSach = new HashMap<>();
            objLoaiSach.put("maTV", thanhVien.getMaTV());
            objLoaiSach.put("hoTen", thanhVien.getHoTen());
            objLoaiSach.put("namSinh", thanhVien.getNamSinh());
            list.add(objLoaiSach);
        }
        return list;
    }

    private void capNhat() {
        phieuMuonArrayList = daoPhieuMuon.getAllPhieuMuon();
        adapter = new PhieuMuonAdapter(phieuMuonArrayList, getContext(), daoPhieuMuon);
        listViewPhieuMuon.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void findID(View view) {
        listViewPhieuMuon = view.findViewById(R.id.listPhieuMuon);
        floatingButtonPhieuMuon = view.findViewById(R.id.floatingButtonPhieuMuon);
    }


}
