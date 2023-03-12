package com.example.myapplication.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Database.Dbhelper;
import com.example.myapplication.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDao {
    private SQLiteDatabase db;

    public ThuThuDao(Context context) {
        Dbhelper dbHelper = new Dbhelper(context);
        db = dbHelper.getWritableDatabase();
    }


    public long insert(ThuThu odj) {
        ContentValues values = new ContentValues();
        values.put("maTT", odj.getMaTT());
        values.put("hoTen", odj.getHoTen());
        values.put("matKhau", odj.getMatKhau());
        return db.insert("ThuThu", null, values);
    }

    public long update(ThuThu odj) {
        ContentValues values = new ContentValues();
        values.put("maTT", odj.getMaTT());
        values.put("hoTen", odj.getHoTen());
        values.put("matKhau", odj.getMatKhau());
        return db.update("ThuThu", values, "maTT=?", new String[]{String.valueOf(odj.getMaTT())});
    }

    public int delete(String id) {
        if (!id.equals("admin")) {
            return db.delete("ThuThu", "maTT=?", new String[]{id});
        }
        return 0;
    }

    public List<ThuThu> getAll() {
        String sql = "SELECT * FROM ThuThu";
        return getData(sql);
    }

    public ThuThu getID(String id) {
        String sql = "SELECT * FROM ThuThu WHERE maTT=?";
        List<ThuThu> list = getData(sql, id);
        return list.get(0);
    }

    @SuppressLint("Range")
    private List<ThuThu> getData(String sql, String... selectionArgs) {
        List<ThuThu> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            ThuThu obj = new ThuThu();
            obj.setMaTT(c.getString(c.getColumnIndex("maTT")));
            obj.setHoTen(c.getString(c.getColumnIndex("hoTen")));
            obj.setMatKhau(c.getString(c.getColumnIndex("matKhau")));
            list.add(obj);
        }
        return list;
    }

    public int checkLogin(String id, String password) {
        String sql = "SELECT * FROM ThuThu WHERE maTT=? AND matKhau=?";
        List<ThuThu> list = getData(sql, id, password);
        if (list.size() == 0) {
            return -1;
        }
        return 1;
    }
//    public boolean checkLogin(String usr, String password){
//        String sql = "SELECT COUNT(maTT) FROM ThuThu WHERE taiKhoan=? AND matKhau=?";
//        Cursor cursor = db.rawQuery(sql,new String[]{usr,password});
//        cursor.moveToFirst();
//        if (Integer.parseInt(cursor.getString(0))>0){
//            return true;
//        }
//        return false;
//    }
}
