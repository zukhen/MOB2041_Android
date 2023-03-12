package com.example.myapplication.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.model.ThanhVien;
import com.example.myapplication.Database.Dbhelper;

public class ThanhVienDAO {
    private SQLiteDatabase db;
    public ThanhVienDAO(Context context) {
        Dbhelper dbhelper = new Dbhelper(context);
        db = dbhelper.getWritableDatabase();
    }

    public List<ThanhVien> getAllThanhVien() {
        String sql = "SELECT * FROM ThanhVien";
        return getData(sql);
    }


    public long insertThanhVien(ThanhVien tv) {
        ContentValues values = new ContentValues();
        values.put("hoTen", tv.getHoTen());
        values.put("namSinh", tv.getNamSinh());
        return db.insert("ThanhVien", null, values);
    }
//    getdata theo id
    private ThanhVien getIDThanhVien(String id)
    {
        String sql = "SELECT * FROM ThanhVien WHERE maTV=?";
        List<ThanhVien> list= getData(sql,id);
        return list.get(0);
    }
    public int delete(String id) {
        return db.delete("ThanhVien", "maTV=?", new String[]{id});
    }

    public int updateThanhVien(ThanhVien tv,int ID) {
        ContentValues values = new ContentValues();
        values.put("hoTen", tv.getHoTen());
        values.put("namSinh", tv.getNamSinh());

        return         db.update("ThanhVien", values, "maTV=?", new String[]{String.valueOf(ID)});

    }

    @SuppressLint("Range")
    private List<ThanhVien> getData(String sql, String... selectionArgs) {
        List<ThanhVien> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            ThanhVien obj = new ThanhVien();
            obj.setMaTV(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maTV"))));
            obj.setHoTen(cursor.getString(cursor.getColumnIndex("hoTen")));
            obj.setNamSinh(cursor.getString(cursor.getColumnIndex("namSinh")));
            list.add(obj);
        }
        return list;
    }
    public String getNumberThanhVien() {
        String sql="";
        Cursor cursor = db.rawQuery("SELECT COUNT(*)  FROM ThanhVien",null);
        if (cursor.getCount()>0) {
         cursor.moveToFirst();
         sql = cursor.getString(0);
        }
        return sql;
    }
}
