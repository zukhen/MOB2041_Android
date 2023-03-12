package com.example.myapplication.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import com.example.myapplication.Adapter.UserAdapter;
import com.example.myapplication.DAO.ThuThuDao;
import com.example.myapplication.model.ThuThu;

import myapplication.R;

public class AddUserFragment extends Fragment {
    private FloatingActionButton floatingButtonAddUser;
    private ListView listUser;
    private ArrayList<ThuThu> listThuThu;
    private ThuThuDao daoThuThu;
    private UserAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, null, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listThuThu = new ArrayList<>();
        daoThuThu = new ThuThuDao(getContext());
        findID(view);
        capNhatUser();
        floatingButtonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser(daoThuThu);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void addUser(ThuThuDao daoThuThu) {
//    khai bao
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.dialog_them_nguoi_dung, null);
        TextInputLayout edtTaiKhoan, edtFullName, edtPassword, edtRePassword;
        Button btnCreate, btnCancel;
//        findID
        edtTaiKhoan = viewDialog.findViewById(R.id.edtTaiKhoan);
        edtFullName = viewDialog.findViewById(R.id.edtFullName);
        edtPassword = viewDialog.findViewById(R.id.edtPassword);
        edtRePassword = viewDialog.findViewById(R.id.edtRePassword);
        btnCreate = viewDialog.findViewById(R.id.btnCreate);
        btnCancel = viewDialog.findViewById(R.id.btnCancel);
////        set view
        builder.setView(viewDialog);
//        builder view
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        action
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThuThu thuThu = new ThuThu();
                thuThu.setMaTT(edtTaiKhoan.getEditText().getText().toString());
                thuThu.setHoTen(edtFullName.getEditText().getText().toString());
                thuThu.setMatKhau(edtPassword.getEditText().getText().toString());
                if (validate(viewDialog) > 0) {
                    if (daoThuThu.insert(thuThu) > 0) {
                        Toast.makeText(getActivity(), "Lưu thành công", Toast.LENGTH_LONG).show();

                        dialog.dismiss();
                        capNhatUser();
                    } else {
                        Toast.makeText(getActivity(), "Lưu thất bại", Toast.LENGTH_LONG).show();
                        edtTaiKhoan.getEditText().setText("");
                        edtFullName.getEditText().setText("");
                        edtPassword.getEditText().setText("");
                        edtRePassword.getEditText().setText("");
                    }
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

    private int validate(View view) {
        int check = 1;
        TextInputLayout edtTaiKhoan, edtFullName, edtPassword, edtRePassword;
        TextView tvError;
        edtTaiKhoan = view.findViewById(R.id.edtTaiKhoan);
        edtFullName = view.findViewById(R.id.edtFullName);
        edtPassword = view.findViewById(R.id.edtPassword);
        edtRePassword = view.findViewById(R.id.edtRePassword);
        tvError = view.findViewById(R.id.tvError);
        if (edtTaiKhoan.getEditText().getText().toString().isEmpty() ||
                edtFullName.getEditText().getText().toString().isEmpty() ||
                edtRePassword.getEditText().getText().toString().isEmpty() ||
                edtPassword.getEditText().getText().toString().isEmpty()
        ) {
            tvError.setText("Bạn phải nhập đầy đủ thông tin");
            check = -1;
        } else {
            String nPass = edtPassword.getEditText().getText().toString();
            String rePass = edtRePassword.getEditText().getText().toString();
            if (!nPass.equals(rePass)) {
                tvError.setText("Mật khẩu nhập lại không đúng");
                check = -1;
            }
        }
        return check;
    }

    private void findID(View view) {
        listUser = view.findViewById(R.id.listUser);
        floatingButtonAddUser = view.findViewById(R.id.floatingButtonAddUser);
    }
    private void capNhatUser() {
        listThuThu.clear();
        listThuThu = (ArrayList<ThuThu>) daoThuThu.getAll();
        adapter = new UserAdapter(listThuThu, getActivity(), daoThuThu);
        listUser.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
