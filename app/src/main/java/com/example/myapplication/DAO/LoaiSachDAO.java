package com.example.myapplication.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.Database.Dbhelper;
import com.example.myapplication.model.LoaiSach;

public class LoaiSachDAO {
    private SQLiteDatabase db;

    public LoaiSachDAO(Context context) {
        Dbhelper dbhelper = new Dbhelper(context);
        db = dbhelper.getWritableDatabase();
    }

    public List<LoaiSach> getAllLoaiSach() {
        String sql = "SELECT * FROM LoaiSach";
        return getData(sql);
    }

    public long insertLoaiSach(LoaiSach ls) {
        ContentValues values = new ContentValues();
        values.put("tenLoai", ls.getTenLoai());
        return db.insert("LoaiSach", null, values);
    }
//    getdata theo id
    private LoaiSach getIDLoaiSach(String id)
    {
        String sql = "SELECT * FROM LoaiSach WHERE maSach=?";
        List<LoaiSach> list= getData(sql,id);
        return list.get(0);
    }
    public int deleteLoaiSach(String id) {
        return db.delete("LoaiSach", "maLoai=?", new String[]{id});
    }

    public int updateLoaiSach(LoaiSach ls,int id) {
        ContentValues values = new ContentValues();
        values.put("tenLoai", ls.getTenLoai());
        return db.update("LoaiSach", values, "maLoai=?", new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    private List<LoaiSach> getData(String sql, String... selectionArgs) {
        List<LoaiSach> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            LoaiSach obj = new LoaiSach();
            obj.setMaLoai(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maLoai"))));
            obj.setTenLoai(cursor.getString(cursor.getColumnIndex("tenLoai")));
            list.add(obj);
        }
        return list;
    }
    public String getNumberLoaiSach() {
        String sql="";
        Cursor cursor = db.rawQuery("SELECT COUNT(*)  FROM LoaiSach",null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            sql = cursor.getString(0);
        }
        return sql;
    }
}
