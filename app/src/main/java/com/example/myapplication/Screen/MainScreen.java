package com.example.myapplication.Screen;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import com.example.myapplication.Fragment.AddUserFragment;
import com.example.myapplication.Fragment.ChangePasswordFragment;
import com.example.myapplication.Fragment.DoanhThuFragment;
import com.example.myapplication.Fragment.HomeFragment;
import com.example.myapplication.Fragment.PhieuMuonFragment;
import com.example.myapplication.Fragment.LoaiSachFragment;
import com.example.myapplication.Fragment.SachFragment;
import com.example.myapplication.Fragment.ThanhVienFragment;
import com.example.myapplication.Fragment.ThongKeSachFragment;
import com.example.myapplication.MainActivity;import myapplication.R;

public class MainScreen extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private int item_selected = R.id.nav_Home;
    public static boolean logOut;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_mainscreen, null, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUi(view);
        setupToolbar();
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupToolbar() {

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
//        set default
        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_Home).setChecked(true);
        //        hide addUser and set image
        String user = MainActivity.admin.getMaTT();
        if (user.equalsIgnoreCase("admin")) {
            navigationView.getMenu().findItem(R.id.nav_addUser).setVisible(true);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == item_selected) return true;
        switch (item.getItemId()) {
            case R.id.nav_Home:
                replaceFragment(new HomeFragment());
                toolbar.setTitle("");
                break;
            case R.id.nav_phieuMuon:

                replaceFragment(new PhieuMuonFragment());
                toolbar.setTitle(R.string.nav_phieuMuon);
                break;
            case R.id.nav_loaiSach:

                replaceFragment(new LoaiSachFragment());
                toolbar.setTitle(R.string.nav_loaiSach);

                break;
            case R.id.nav_thanhVien:

                replaceFragment(new ThanhVienFragment());
                toolbar.setTitle(R.string.nav_thanhVien);

                break;
            case R.id.nav_thongKeSach:

                replaceFragment(new ThongKeSachFragment());
                toolbar.setTitle(R.string.nav_thongKeSach);

                break;
            case R.id.nav_doanhThu:
                replaceFragment(new DoanhThuFragment());
                toolbar.setTitle("");

                break;

            case R.id.nav_sach:
                replaceFragment(new SachFragment());
                toolbar.setTitle(R.string.nav_sach);

                break;
            case R.id.nav_addUser:
                replaceFragment(new AddUserFragment());
                toolbar.setTitle(R.string.nav_addUser);

                break;
            case R.id.nav_doiMatKhau:
                replaceFragment(new ChangePasswordFragment());
                toolbar.setTitle("");

                break;
            case R.id.nav_dangXuat:
                logOut();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }


    private void logOut() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.logOut);

        builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();

                logOut = true;

            }
        });
        builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                logOut = false;
            }
        });
        //custom dialog
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                //set color đăng xuất
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));
                //set color huỷ
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.Statusbar));
            }
        });
        dialog.show();
        ;
    }

    private void initUi(View view) {
        drawerLayout = view.findViewById(R.id.drawer_layout);
        toolbar = view.findViewById(R.id.toolbar);
        navigationView = view.findViewById(R.id.navigation_view);

    }

}
