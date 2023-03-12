package com.example.myapplication.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;


import com.example.myapplication.DAO.LoaiSachDAO;
import com.example.myapplication.DAO.PhieuMuonDAO;
import com.example.myapplication.DAO.SachDAO;
import com.example.myapplication.DAO.ThanhVienDAO;
import com.example.myapplication.DAO.ThuThuDao;
import com.example.myapplication.MainActivity;
import myapplication.R;

import com.example.myapplication.model.ThuThu;

public class HomeFragment extends Fragment  {
    private ThuThuDao daoThuThu;
    private ThanhVienDAO daoThanhVien;
    private LoaiSachDAO daoLoaiSach;
    private SachDAO daoSach;
    private PhieuMuonDAO daoPhieuMuon;
    private TextView tv_numberMembers,tv_numberKindOfBook,tv_numberBook,tv_numberLoans,tvUserName;
    private ImageView image_view;
    private LinearLayout layout_thanhVien,layout_loaiSach,layout_Sach,layout_numberLoans;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, null, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findID(view);
        Animation();
        daoThuThu = new ThuThuDao(getActivity());
        daoThanhVien = new ThanhVienDAO(getActivity());
        daoLoaiSach = new LoaiSachDAO(getActivity());
        daoSach = new SachDAO(getActivity());
        daoPhieuMuon = new PhieuMuonDAO(getActivity());

//        set Name
        ThuThu thuThu = daoThuThu.getID(MainActivity.admin.getMaTT());
        tvUserName.setText("Xin chào! "+thuThu.getHoTen());

//        set so luong ban ghi
        tv_numberMembers.setText(daoThanhVien.getNumberThanhVien());
        tv_numberKindOfBook.setText(daoLoaiSach.getNumberLoaiSach());
        tv_numberBook.setText(daoSach.getNumberSach());
        tv_numberLoans.setText(daoPhieuMuon.getNumberPhieuMuon());
//    set avatar
        String user = MainActivity.admin.getMaTT();
        if (!user.equalsIgnoreCase("admin"))
        {
            image_view.setTooltipText("chua tim thay anh");
            image_view.setBackgroundResource(R.drawable.avatar_not_admin);
        }
        else{
            image_view.setBackgroundResource(R.drawable.avatar);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    private void findID(View view) {
        image_view = view.findViewById(R.id.image_view);
        layout_thanhVien = view.findViewById(R.id.layout_thanhVien);
        layout_loaiSach=view.findViewById(R.id.layout_loaiSach);
        layout_Sach = view.findViewById(R.id.layout_Sach);
        layout_numberLoans=view.findViewById(R.id.layout_numberLoans);
        tv_numberMembers = view.findViewById(R.id.tv_numberMembers);
        tv_numberKindOfBook = view.findViewById(R.id.tv_numberKindOfBook);
        tv_numberBook = view.findViewById(R.id.tv_numberBook);
        tv_numberLoans = view.findViewById(R.id.tv_numberLoans);
        tvUserName = view.findViewById(R.id.tvUserName);

    }

    private void Animation() {
        Animation lefttoright = AnimationUtils.loadAnimation(getActivity(), R.anim.lefttoright);
        Animation righttoleft = AnimationUtils.loadAnimation(getActivity(), R.anim.righttoleft);
        layout_Sach.setAnimation(lefttoright);
        layout_thanhVien.setAnimation(lefttoright);
        layout_loaiSach.setAnimation(righttoleft);
        layout_numberLoans.setAnimation(righttoleft);
    }


}
