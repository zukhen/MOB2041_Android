package com.example.myapplication.Adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.example.myapplication.DAO.PhieuMuonDAO;
import com.example.myapplication.DAO.SachDAO;
import com.example.myapplication.DAO.ThanhVienDAO;
import com.example.myapplication.model.PhieuMuon;
import com.example.myapplication.model.Sach;
import com.example.myapplication.model.ThanhVien;

import myapplication.R;

public class PhieuMuonAdapter extends BaseAdapter {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private ArrayList<Sach> listSach;
    private ArrayList<PhieuMuon> phieuMuonArrayList;
    private Context context;
    private PhieuMuonDAO daoPhieuMuon;
    //    spinner
    private ThanhVienDAO daoThanhVien;
    private SachDAO daoSach;
    private ArrayList<ThanhVien> listThanhVien;

    public PhieuMuonAdapter(ArrayList<PhieuMuon> phieuMuonArrayList, Context context, PhieuMuonDAO daoPhieuMuon) {
        this.phieuMuonArrayList = phieuMuonArrayList;
        this.context = context;
        this.daoPhieuMuon = daoPhieuMuon;
    }

    @Override
    public int getCount() {
        return phieuMuonArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return phieuMuonArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return phieuMuonArrayList.get(i).getMaPM();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewItemPhieuMuon viewItemPhieuMuon ;
        //        tao constructor
        if (view == null) {
            view = ((Activity) context).getLayoutInflater().inflate(R.layout.item_phieu_muon, viewGroup, false);
            viewItemPhieuMuon = new ViewItemPhieuMuon();
            viewItemPhieuMuon.tvTenNguoiMuon = view.findViewById(R.id.tvTenNguoiMuon);
            viewItemPhieuMuon.tvTenSach = view.findViewById(R.id.tvTenSach);
            viewItemPhieuMuon.tvTrangThai = view.findViewById(R.id.tvTrangThai);
            viewItemPhieuMuon.imgEdit = view.findViewById(R.id.imgEdit);
            viewItemPhieuMuon.imgDel = view.findViewById(R.id.imgDel);
            viewItemPhieuMuon.tvNgayMuon = view.findViewById(R.id.tvNgayMuon);
//          luu tru view
            view.setTag(viewItemPhieuMuon);
        } else {
            viewItemPhieuMuon = (ViewItemPhieuMuon) view.getTag();
        }
        showdata(viewItemPhieuMuon, position);
        daoSach = new SachDAO(context);
        daoThanhVien = new ThanhVienDAO(context);
        viewItemPhieuMuon.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (daoPhieuMuon.deletePhieuMuon(String.valueOf(phieuMuonArrayList.get(position).getMaPM())) < 0) {
                    Toast.makeText(context, "Xoá thất bại", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                }
                loadDanhSach();
            }
        });
        viewItemPhieuMuon.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPhieuMuonDiaLog(position);
            }
        });

        return view;
    }

    private void editPhieuMuonDiaLog(int vitri) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.dialog_sua_phieu_muon, null);

        Button btnCreate, btnCancel;
        TextView tvMaPhieuMuon, tvError;
        Spinner spinnerTV, spinnerSach;
        EditText edNgayMuon, edGiaMuon;
        ImageView date_picker_actions;
        CheckBox chkTraSach;
//        anh xa
        edGiaMuon = viewDialog.findViewById(R.id.edGiaMuon);
        btnCreate = viewDialog.findViewById(R.id.btnCreate);
        btnCancel = viewDialog.findViewById(R.id.btnCancel);
        tvError = viewDialog.findViewById(R.id.tvError);
        tvMaPhieuMuon = viewDialog.findViewById(R.id.tvMaPhieuMuon);
        spinnerSach = viewDialog.findViewById(R.id.spinnerSach);
        spinnerTV = viewDialog.findViewById(R.id.spinnerTV);
        edNgayMuon = viewDialog.findViewById(R.id.edNgayMuon);
        date_picker_actions = viewDialog.findViewById(R.id.date_picker_actions);
        chkTraSach = viewDialog.findViewById(R.id.chkTraSach);
//        set view
        builder.setView(viewDialog);
        AlertDialog dialog = builder.create();


        tvMaPhieuMuon.setText("Mã phiếu mượn" + phieuMuonArrayList.get(vitri).getMaPM());
        edGiaMuon.setText(String.valueOf(phieuMuonArrayList.get(vitri).getTienThue()));
        getDataSach(spinnerSach);
        getDataThanhVien(spinnerTV);
        date_picker_actions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i, i1, i2);
                        edNgayMuon.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhieuMuon phieuMuon = new PhieuMuon();
                HashMap<String, Object> hs = (HashMap<String, Object>) spinnerTV.getSelectedItem();
                HashMap<String, Object> hv = (HashMap<String, Object>) spinnerSach.getSelectedItem();

                phieuMuon.setMaTV((int) hs.get("maTV"));
                phieuMuon.setMaSach((int) hv.get("maSach"));
                try {
                    phieuMuon.setNgay(simpleDateFormat.parse(edNgayMuon.getText().toString()));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                phieuMuon.setTraSach(chkTraSach.isChecked() ? 1 : 0);
                if (edNgayMuon.getText().toString().isEmpty()) {
                    tvError.setText("Vui lòng không để trông ngày");
                } else {
                    if (daoPhieuMuon.updatePhieuMuon(phieuMuon) > 0) {
                        Toast.makeText(context, "Lưu thành công", Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                    } else {
                        Toast.makeText(context, "Lưu thất bại", Toast.LENGTH_LONG).show();
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

    private void getDataSach(Spinner spinner) {
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
        SimpleAdapter adapter = new SimpleAdapter(context, list, R.layout.item_spinner, new String[]{"tenSach"}, new int[]{R.id.tvLoaiSach});
        spinner.setAdapter(adapter);
    }

    private void getDataThanhVien(Spinner spinner) {
        listThanhVien = (ArrayList<ThanhVien>) daoThanhVien.getAllThanhVien();
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        for (ThanhVien thanhVien : listThanhVien) {
            HashMap<String, Object> objLoaiSach = new HashMap<>();
            objLoaiSach.put("maTV", thanhVien.getMaTV());
            objLoaiSach.put("hoTen", thanhVien.getHoTen());
            objLoaiSach.put("namSinh", thanhVien.getNamSinh());
            list.add(objLoaiSach);
        }
        SimpleAdapter adapter = new SimpleAdapter(context, list, R.layout.item_spinner, new String[]{"hoTen"}, new int[]{R.id.tvLoaiSach});
        spinner.setAdapter(adapter);
    }

    private void loadDanhSach() {
        phieuMuonArrayList.clear();
        phieuMuonArrayList = (ArrayList<PhieuMuon>) daoPhieuMuon.getAllPhieuMuon();
        notifyDataSetChanged();
    }

    private void showdata(ViewItemPhieuMuon viewItemPhieuMuon, int i) {
        viewItemPhieuMuon.tvTenNguoiMuon.setText(phieuMuonArrayList.get(i).getTenThanhVien());
        viewItemPhieuMuon.tvTenSach.setText(phieuMuonArrayList.get(i).getTenSach());
        viewItemPhieuMuon.tvNgayMuon.setText(simpleDateFormat.format(phieuMuonArrayList.get(i).getNgay()));
        int trasach = phieuMuonArrayList.get(i).getTraSach();
        if (trasach == 1) {
            viewItemPhieuMuon.tvTrangThai.setText("Đã trả");
            viewItemPhieuMuon.tvTrangThai.setTextColor(context.getResources().getColor(R.color.green));

        } else {
            viewItemPhieuMuon.tvTrangThai.setText("Chưa trả");
            viewItemPhieuMuon.tvTrangThai.setTextColor(context.getResources().getColor(R.color.red));

        }
    }

    static class ViewItemPhieuMuon {
        TextView tvTenNguoiMuon, tvTenSach, tvTrangThai, tvNgayMuon;
        ImageView imgEdit, imgDel;
    }
}
