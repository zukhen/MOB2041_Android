package com.example.myapplication.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import com.example.myapplication.DAO.ThuThuDao;
import com.example.myapplication.Screen.MainScreen;
import com.example.myapplication.model.ThuThu;

import myapplication.R;

public class ChangePasswordFragment extends Fragment {
    private TextView tvError, tvFogotPass;
    private EditText edtOldPassword, edtNewPassword, edtRePassword;
    private Button btnUpdate;
    private ThuThuDao dao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_changepassword, null, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findID(view);
        dao = new ThuThuDao(getContext());
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getActivity().getSharedPreferences("dataAccount.txt", Context.MODE_PRIVATE);
                String user = pref.getString("Username", "");
                if (validate() > 0) {
                    ThuThu thuThu = dao.getID(user);
                    thuThu.setMatKhau(edtNewPassword.getText().toString());
                    if (thuThu.getMatKhau().equals(edtOldPassword.getText().toString())) {
                        tvError.setText("Mật khẩu mới không được trùng với mật khẩu cũ");
                        Toast.makeText(getActivity(), "Thay đổi mật khẩu thất bại", Toast.LENGTH_LONG).show();
                    } else {
                        dao.update(thuThu);
                        Toast.makeText(getActivity(), "Thay đổi mật khẩu thành công", Toast.LENGTH_LONG).show();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new MainScreen()).commit();
                    }
                }
            }
        });
        tvFogotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(view, R.string.update, Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private int validate() {
        int check = 1;
        if (edtOldPassword.getText().toString().isEmpty() || edtNewPassword.getText().toString().isEmpty() || edtRePassword.getText().toString().isEmpty()) {
            tvError.setText("Bạn phải nhập đầy đủ thông tin");
            check = -1;
        } else {
            SharedPreferences pref = getActivity().getSharedPreferences("dataAccount.txt", Context.MODE_PRIVATE);
            String passOld = pref.getString("Password", "");
            String newPass = edtNewPassword.getText().toString();
            String rePass = edtRePassword.getText().toString();
            if (!passOld.equalsIgnoreCase(edtOldPassword.getText().toString())) {
                tvError.setText("Mật khẩu cũ không đúng");
                check = -1;
            }
            if (!newPass.equals(rePass)) {
                tvError.setText("Mật khẩu mới không trùng");
                check = -1;
            }
        }
        return check;
    }

    private void findID(View view) {
        tvError = view.findViewById(R.id.tvError);
        tvFogotPass = view.findViewById(R.id.tvFogotPass);
        edtOldPassword = view.findViewById(R.id.edtOldPassword);
        edtNewPassword = view.findViewById(R.id.edtNewPassword);
        edtRePassword = view.findViewById(R.id.edtRePassword);
        btnUpdate = view.findViewById(R.id.btnUpdate);
    }
}
