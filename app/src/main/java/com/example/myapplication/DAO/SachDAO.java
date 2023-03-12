package com.example.myapplication.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.Database.Dbhelper;
import com.example.myapplication.model.Sach;

public class SachDAO {
    private SQLiteDatabase db;
    private Dbhelper dbhelper;

    public SachDAO(Context context) {
        dbhelper = new Dbhelper(context);
        db = dbhelper.getWritableDatabase();
    }

    public List<Sach> getAllSach() {
        String sql = "SELECT * FROM Sach";
        return getData(sql);
    }

    public ArrayList<Sach> getDSSach() {
        ArrayList<Sach> list = new ArrayList<>();
        String sql = "SELECT sc.maSach,sc.tenSach, sc.giaThue, sc.maLoai,ls.tenLoai FROM Sach sc,LoaiSach ls WHERE sc.maLoai=ls.maLoai";
        db = dbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4)));
            cursor.moveToNext();
        }
        return list;
    }

    public long insertSach(Sach s) {
        ContentValues values = new ContentValues();
        values.put("tenSach", s.getTenSach());
        values.put("giaThue", s.getGiaThue());
        values.put("maLoai", s.getMaLoai());
        return db.insert("Sach", null, values);
    }

    //    getdata theo id
    public Sach getIDSach(String id) {
        String sql = "SELECT * FROM Sach WHERE maSach=?";
        List<Sach> list = getData(sql, id);
        return list.get(0);
    }

    public int deleteSach(String id) {
        return db.delete("Sach", "maSach=?", new String[]{id});
    }

    public int updateSach(int ma,Sach s) {
        ContentValues values = new ContentValues();
        values.put("tenSach", s.getTenSach());
        values.put("giaThue", s.getGiaThue());
        values.put("maLoai", s.getMaLoai());
        return db.update("Sach", values, "maSach=?", new String[]{String.valueOf(ma)});
    }

    @SuppressLint("Range")
    private List<Sach> getData(String sql, String... selectionArgs) {
        List<Sach> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            Sach obj = new Sach();
            obj.setMaSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maSach"))));
            obj.setTenSach(cursor.getString(cursor.getColumnIndex("tenSach")));
            obj.setGiaThue(Integer.parseInt(cursor.getString(cursor.getColumnIndex("giaThue"))));
            obj.setMaLoai(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maLoai"))));
            list.add(obj);
        }
        return list;
    }

    public String getNumberSach() {
        String sql = "";
        Cursor cursor = db.rawQuery("SELECT COUNT(*)  FROM Sach", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            sql = cursor.getString(0);
        }
        return sql;
    }
}
