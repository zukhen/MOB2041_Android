package com.example.myapplication.Screen;

import static com.example.myapplication.MainActivity.hideSoftKeyboard;

import android.annotation.SuppressLint;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import com.example.myapplication.DAO.ThuThuDao;
import com.example.myapplication.MainActivity;import myapplication.R;

public class LoginFragment extends Fragment {
    private ImageView imageView;
    private EditText edtUserName, edtPassword;
    private TextView tvFogotPass;
    private Button btnLogin;
    private CheckBox checkBox;
    private ThuThuDao dao;
    static boolean passwordVisible;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, null, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUi(view);
        Animation();
//        logout
      checkLogout();

        tvFogotPass.setOnClickListener(view1 -> {
            Snackbar snackbar = Snackbar.make(view1, R.string.update, Snackbar.LENGTH_SHORT);
            snackbar.show();
        });
        btnLogin.setOnClickListener(view13 -> checkLogin());
        edtPassword.setOnTouchListener((view12, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                int selection = edtPassword.getSelectionEnd();
                if (motionEvent.getRawX() >= edtPassword.getRight() - edtPassword.getCompoundDrawables()[2].getBounds().width()) {
                    if (passwordVisible) {
                        edtPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
//                            hide password
                        edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        passwordVisible = false;
                    } else {
                        edtPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
//                            show password
                        edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        passwordVisible = true;
                    }
                }
                edtPassword.setSelection(selection);
            }
            return false;
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void checkLogout() {
        if (!MainScreen.logOut) {
            SharedPreferences preferences = getActivity().getSharedPreferences("dataAccount.txt", getActivity().MODE_PRIVATE);
            edtUserName.setText(preferences.getString("Username", ""));
            edtPassword.setText(preferences.getString("Password", ""));
            checkBox.setChecked(preferences.getBoolean("Remember", false));
        } else {
            edtUserName.setText("");
            edtPassword.setText("");
            checkBox.setChecked(false);
        }
    }

    private void checkLogin() {
        String strUser = edtUserName.getText().toString();
        String strPass = edtPassword.getText().toString();
        if (strUser.isEmpty() || strPass.isEmpty()) {
            Toast.makeText(getActivity(), "Tên đăng nhập và mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
        } else {
            if (dao.checkLogin(strUser, strPass) > 0) {
                Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                hideSoftKeyboard(getActivity());
                rememberUser(strUser, strPass, checkBox.isChecked());
                MainActivity.admin.setMaTT(strUser);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainScreen()).commit();
            } else {
                Toast.makeText(getActivity(), "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();

            }
        }
    }


    private void rememberUser(String u, String p, boolean status) {
        SharedPreferences preferences = getActivity().getSharedPreferences("dataAccount.txt", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        if (!status) {
            edit.clear();
        } else {
            edit.putString("Username", u);
            edit.putString("Password", p);
            edit.putBoolean("Remember", status);
        }
//      luu lai toan bo
        edit.commit();
    }

    private void initUi(View view) {
        imageView = view.findViewById(R.id.imageView);
        edtUserName = view.findViewById(R.id.edtUserName);
        edtPassword = view.findViewById(R.id.edtPassword);
        tvFogotPass = view.findViewById(R.id.tvFogotPass);
        btnLogin = view.findViewById(R.id.btnLogin);
        checkBox = view.findViewById(R.id.chkRemember);
        dao = new ThuThuDao(getActivity());

    }

    private void Animation() {
        Animation fadein = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);
        Animation floatin = AnimationUtils.loadAnimation(getActivity(), R.anim.floatin);
        checkBox.setAnimation(fadein);
        btnLogin.setAnimation(fadein);
        tvFogotPass.setAnimation(fadein);
        edtPassword.setAnimation(fadein);
        edtUserName.startAnimation(fadein);
        imageView.startAnimation(floatin);
    }


}
