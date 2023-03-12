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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.myapplication.DAO.ThuThuDao;
import com.example.myapplication.MainActivity;
import com.example.myapplication.model.ThuThu;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import myapplication.R;

public class UserAdapter extends BaseAdapter {
    private ArrayList<ThuThu> listThuThu;
    private Context context;
    private ThuThuDao daoThuThu;

    public UserAdapter(ArrayList<ThuThu> listThuThu, Context context, ThuThuDao daoThuThu) {
        this.listThuThu = listThuThu;
        this.context = context;
        this.daoThuThu = daoThuThu;
    }

    @Override
    public int getCount() {
        return listThuThu.size();
    }

    @Override
    public Object getItem(int i) {
        return listThuThu.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
            String user = MainActivity.admin.getMaTT();
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//        tao constructor
        ViewItemUser viewItemUser = new ViewItemUser();
        if (view == null) {
            view = inflater.inflate(R.layout.item_user, null);
            viewItemUser.avatarUserItem = view.findViewById(R.id.avatarUserItem);
            viewItemUser.tvFullName = view.findViewById(R.id.tvFullName);
            viewItemUser.tvTaiKhoan = view.findViewById(R.id.tvTaiKhoan);
            viewItemUser.imgEdit = view.findViewById(R.id.imgEdit);
            viewItemUser.imgDel = view.findViewById(R.id.imgDel);
//          luu tru view
            view.setTag(viewItemUser);
        } else {
            viewItemUser = (ViewItemUser) view.getTag();
        }
        showData(viewItemUser, i);
//        button Update

        viewItemUser.imgEdit.setOnClickListener(view12 -> editUserDialog(i));
//        button xoa
        viewItemUser.imgDel.setOnClickListener(view1 -> {
                if (daoThuThu.delete(String.valueOf(listThuThu.get(i).getMaTT())) < 0) {
                    Toast.makeText(context, "Xoá thất bại", Toast.LENGTH_SHORT).show();
                } else {
                  if (user.equals("admin")) Toast.makeText(context, "Không thể xoá người dùng này ", Toast.LENGTH_SHORT).show();
                  else Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                }
                loadDanhsach();
            loadDanhsach();
        });

//        show data
        viewItemUser.avatarUserItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(i);
            }
        });
        viewItemUser.tvFullName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(i);

            }
        });
        viewItemUser.tvTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(i);

            }
        });
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void showDialog(int vitri) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.dialog_thong_tin_tai_khoan, null);
        TextView tvNameUser, tvTaiKhoanUser, tvMatKhauUser;
//        findID
        tvNameUser = viewDialog.findViewById(R.id.tvNameUser);
        tvTaiKhoanUser = viewDialog.findViewById(R.id.tvTaiKhoanUser);
        tvMatKhauUser = viewDialog.findViewById(R.id.tvMatKhauUser);
//        setThongTin
        tvNameUser.setText(String.valueOf(listThuThu.get(vitri).getHoTen()));
        tvTaiKhoanUser.setText("Tài khoản: " + listThuThu.get(vitri).getMaTT());
        tvMatKhauUser.setText("Mật khẩu: " + listThuThu.get(vitri).getMatKhau());
        //        set view
        builder.setView(viewDialog);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void editUserDialog(int vitri) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.dialog_sua_user, null);
        TextInputLayout edtTaiKhoanUpdate, edtFullNameUpdate, edtPasswordUpdate, edtRePasswordUpdate;
        Button btnUpdate, btnCancel;
        TextView tvError;
//        findID
        tvError = viewDialog.findViewById(R.id.tvError);
        edtTaiKhoanUpdate = viewDialog.findViewById(R.id.edtTaiKhoanUpdate);
        edtFullNameUpdate = viewDialog.findViewById(R.id.edtFullNameUpdate);
        edtPasswordUpdate = viewDialog.findViewById(R.id.edtPasswordUpdate);
        edtRePasswordUpdate = viewDialog.findViewById(R.id.edtRePasswordUpdate);
        btnUpdate = viewDialog.findViewById(R.id.btnUpdate);
        btnCancel = viewDialog.findViewById(R.id.btnCancel);
        //        set view
        builder.setView(viewDialog);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        set text
        edtTaiKhoanUpdate.getEditText().setText(String.valueOf(listThuThu.get(vitri).getMaTT()));
        edtFullNameUpdate.getEditText().setText(String.valueOf(listThuThu.get(vitri).getHoTen()));
        edtPasswordUpdate.getEditText().setText(String.valueOf(listThuThu.get(vitri).getMatKhau()));
        edtRePasswordUpdate.getEditText().setText(String.valueOf(listThuThu.get(vitri).getMatKhau()));
//        action
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThuThu item = new ThuThu();
                item.setMaTT(edtTaiKhoanUpdate.getEditText().getText().toString());
                item.setHoTen(edtFullNameUpdate.getEditText().getText().toString());
                item.setMatKhau(edtPasswordUpdate.getEditText().getText().toString());

                if (edtTaiKhoanUpdate.getEditText().getText().toString().isEmpty() ||
                        edtFullNameUpdate.getEditText().getText().toString().isEmpty() ||
                        edtPasswordUpdate.getEditText().getText().toString().isEmpty() ||
                        edtRePasswordUpdate.getEditText().getText().toString().isEmpty()
                ) {
                    tvError.setText("Vui lòng không để trống");
                } else {
                    String nPass = edtPasswordUpdate.getEditText().getText().toString();
                    String rePass = edtRePasswordUpdate.getEditText().getText().toString();
                    if (!nPass.equals(rePass)) {
                        tvError.setText("Mật khẩu nhập lại không đúng");
                    } else {
                        if (daoThuThu.update(item) < 0) {
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

    private void showData(ViewItemUser viewItemUser, int position) {
        viewItemUser.tvFullName.setText(String.valueOf(listThuThu.get(position).getHoTen()));
        viewItemUser.tvTaiKhoan.setText(String.valueOf(listThuThu.get(position).getMaTT()));
    }

    private void loadDanhsach() {
        listThuThu.clear();
        listThuThu = (ArrayList<ThuThu>) daoThuThu.getAll();
        notifyDataSetChanged();
    }

    static class ViewItemUser {
        TextView tvFullName, tvTaiKhoan;
        ImageView imgEdit, imgDel, avatarUserItem;
    }
}
