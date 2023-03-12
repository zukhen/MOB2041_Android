package com.example.myapplication.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.myapplication.DAO.ThongkeDAO;
import myapplication.R;

public class DoanhThuFragment extends Fragment {
    EditText tvTuNgay, tvDenNgay;
    TextView tvDoanhThu;
    private Button btnStart;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private ThongkeDAO daoThongKe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_doanh_thu, null, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findID(view);
        daoThongKe = new ThongkeDAO(getActivity());
        tvTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i, i1, i2);
                        tvTuNgay.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        tvDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i, i1, i2);
                        tvDenNgay.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date objNgayBatDau, objNgayKetThuc;
                String chuoi_ngaybatdauMoi,chuoi_ngaykethucMoi,tuNgay,denNgay;
                android.text.format.DateFormat dateFormat = new DateFormat();
                 tuNgay = tvTuNgay.getText().toString();
                 denNgay = tvDenNgay.getText().toString();
                if (tuNgay.isEmpty() || denNgay.isEmpty()) {
                    Toast.makeText(getActivity(),"Vui lòng chọn ngày",Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        objNgayBatDau = simpleDateFormat.parse(tuNgay);
                        objNgayKetThuc = simpleDateFormat.parse(denNgay);
                        chuoi_ngaybatdauMoi = (String) dateFormat.format("yyyy-MM-dd", objNgayBatDau);
                        chuoi_ngaykethucMoi = (String) dateFormat.format("yyyy-MM-dd", objNgayKetThuc);
                        tvDoanhThu.setText("Tổng doanh thu: "+daoThongKe.getDoanhThu(chuoi_ngaybatdauMoi, chuoi_ngaykethucMoi) + "$");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }
            }
        });

        super.onViewCreated(view, savedInstanceState);

    }

    private void findID(View view) {
        tvTuNgay = view.findViewById(R.id.tvTuNgay);
        tvDenNgay = view.findViewById(R.id.tvDenNgay);
        tvDoanhThu = view.findViewById(R.id.tvDoanhThu);
        btnStart = view.findViewById(R.id.btnStart);
    }
}
