package com.example.myapplication.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.myapplication.Database.Dbhelper;
import com.example.myapplication.model.PhieuMuon;

public class PhieuMuonDAO {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SQLiteDatabase db;
    private Dbhelper dbhelper;

    public PhieuMuonDAO(Context context) {
        dbhelper = new Dbhelper(context);
        db = dbhelper.getWritableDatabase();
    }

    public ArrayList<PhieuMuon> getAllPhieuMuon() {
        ArrayList<PhieuMuon> list = new ArrayList<>();
        String sql = "SELECT pm.maPM,pm.maTT,pm.maTV,pm.maSach,pm.tienThue,pm.ngay,pm.traSach,sc.tenSach,tv.hoTen FROM PhieuMuon pm,Sach sc,ThanhVien tv WHERE pm.maSach = sc.maSach AND pm.maTV = tv.maTV;";
        db = dbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Date date = new Date();
            try {
                date = simpleDateFormat.parse(cursor.getString(5));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            list.add(new PhieuMuon(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), date, cursor.getInt(6),cursor.getString(7),cursor.getString(8)));
            cursor.moveToNext();
        }

        return list;
    }

//    public List<PhieuMuon> getAllPhieuMuon() {
//        String sql = "SELECT * FROM ThanhVien";
//        return getData(sql);
//    }

    public long insertPhieuMuon(PhieuMuon pm) {
        ContentValues values = new ContentValues();
        values.put("maTT", pm.getMaTT());
        values.put("maTV", pm.getMaTV());
        values.put("maSach", pm.getMaSach());
        values.put("tienThue", pm.getTienThue());
        values.put("ngay", simpleDateFormat.format(pm.getNgay()));
        values.put("traSach", pm.getTraSach());
        return db.insert("PhieuMuon", null, values);
    }

    //    getdata theo id
//    private PhieuMuon getIDPhieuMuon(String id) {
//        String sql = "SELECT * FROM PhieuMuon WHERE maPM=?";
//        List<PhieuMuon> list = getData(sql, id);
//        return list.get(0);
//    }

    public int deletePhieuMuon(String id) {
        return db.delete("PhieuMuon", "maPM=?", new String[]{id});
    }

    public int updatePhieuMuon(PhieuMuon pm) {
        ContentValues values = new ContentValues();
        values.put("maTT", pm.getMaTT());
        values.put("maTV", pm.getMaTV());
        values.put("maSach", pm.getMaSach());
        values.put("tienThue", pm.getTienThue());
        values.put("ngay", simpleDateFormat.format(pm.getNgay()));
        values.put("traSach", pm.getTraSach());
        return db.update("PhieuMuon", values, "maPM=?", new String[]{String.valueOf(pm.getMaPM())});
    }

    //    @SuppressLint("Range")
//    private List<PhieuMuon> getData(String sql, String... selectionArgs) {
//        List<PhieuMuon> list = new ArrayList<>();
//        Cursor cursor = db.rawQuery(sql, selectionArgs);
//        while (cursor.moveToNext()) {
//            Date date = new Date();
//            try {
//                date = simpleDateFormat.parse(cursor.getString(cursor.getColumnIndex("ngay")));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            PhieuMuon obj = new PhieuMuon();
//            obj.setMaPM(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maPM"))));
//            obj.setMaTT(cursor.getString(cursor.getColumnIndex("maTT")));
//            obj.setMaTV(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maTV"))));
//            obj.setMaSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maSach"))));
//            obj.setTienThue(Integer.parseInt(cursor.getString(cursor.getColumnIndex("tienThue"))));
//            obj.setNgay(date);
//            obj.setTienThue(Integer.parseInt(cursor.getString(cursor.getColumnIndex("traSach"))));
//            list.add(obj);
//        }
//        return list;
//    }
    public String getNumberPhieuMuon() {
        String sql = "";
        Cursor cursor = db.rawQuery("SELECT COUNT(*)  FROM PhieuMuon", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            sql = cursor.getString(0);
        }
        return sql;
    }
}
