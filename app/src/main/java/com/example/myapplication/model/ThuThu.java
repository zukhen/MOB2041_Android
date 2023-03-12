package com.example.myapplication.model;

public class ThuThu {
    private int ID;
    private String maTT;
    private String hoTen;
    private String matKhau;
    public ThuThu() {

    }


    public ThuThu(int ID, String maTT, String hoTen, String matKhau) {
        this.ID = ID;
        this.maTT = maTT;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
