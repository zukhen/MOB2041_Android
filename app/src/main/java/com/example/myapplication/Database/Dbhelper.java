package com.example.myapplication.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Dbhelper extends SQLiteOpenHelper {
    static final String dbName = "PNLib";
    static final int dbVersion = 1;
    private final String createTableThuThu = "CREATE TABLE ThuThu(maTT TEXT PRIMARY KEY,hoTen TEXT NOT NULL,matKhau TEXT NOT NULL);";
    private final String createTableThanhVien = "CREATE TABLE ThanhVien(maTV INTEGER PRIMARY KEY AUTOINCREMENT,hoTen TEXT NOT NULL,namSinh TEXT NOT NULL);";
    private final String createTableloaiSach = "CREATE TABLE LoaiSach(maLoai INTEGER PRIMARY KEY AUTOINCREMENT,tenLoai TEXT NOT NULL);";
    private final String createTableSach = "CREATE TABLE Sach(maSach INTEGER PRIMARY KEY AUTOINCREMENT,tenSach TEXT NOT NULL,giaThue INTEGER NOT NULL, maLoai INTEGER REFERENCES LoaiSach(maLoai));";
    private final String createTablePhieuMuon = "CREATE TABLE PhieuMuon(maPM INTEGER PRIMARY KEY AUTOINCREMENT, maTT TEXT REFERENCES ThuThu(matt),maTV INTEGER REFERENCES ThanhVien(maTV),maSach INTEGER REFERENCES Sach(maSach),tienThue INTEGER Not NULL, ngay DATE Not NULL, traSach INTEGER Not NULL);";


    private final String PH28077_dbLoaiSanPham = "CREATE TABLE SanPham(maSP INTEGER PRIMARY KEY AUTOINCREMENT, tenSP TEXT,soLuong INTEGER, donGia INTEGER,hanSuDung DATE);";

    private final String createAdmin = "INSERT INTO ThuThu VALUES('admin','ha ha ha','admin')";

    public Dbhelper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableThuThu);
        db.execSQL(createTableThanhVien);
        db.execSQL(createTableSach);
        db.execSQL(createTableloaiSach);
        db.execSQL(createTablePhieuMuon);
        db.execSQL(createAdmin);
        db.execSQL(PH28077_dbLoaiSanPham);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS ThuThu");
        db.execSQL("DROP TABLE IF EXISTS ThanhVien");
        db.execSQL("DROP TABLE IF EXISTS LoaiSach");
        db.execSQL("DROP TABLE IF EXISTS Sach");
        db.execSQL("DROP TABLE IF EXISTS PhieuMuon");
        db.execSQL("DROP TABLE IF EXISTS SanPham");
    }
}
