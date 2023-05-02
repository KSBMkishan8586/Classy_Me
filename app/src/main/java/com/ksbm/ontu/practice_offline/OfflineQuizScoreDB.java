package com.ksbm.ontu.practice_offline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ksbm.ontu.utils.Constant;

import java.util.ArrayList;
import java.util.HashMap;

import static com.ksbm.ontu.utils.Constant.COLUMN_ID;
import static com.ksbm.ontu.utils.Constant.IS_PLAYED;
import static com.ksbm.ontu.utils.Constant.LEVEL_ID;
import static com.ksbm.ontu.utils.Constant.QUIZ_COIN;
import static com.ksbm.ontu.utils.Constant.QUIZ_ID;
import static com.ksbm.ontu.utils.Constant.STAGE_ID;

public class OfflineQuizScoreDB extends SQLiteOpenHelper {

    private static String DB_NAME = "Quiz.db";
    private static int DB_VERSION = 1;
    private SQLiteDatabase db;

    public static final String CART_TABLE = "OFFLINE_QUIZ_SCORE";

    public OfflineQuizScoreDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        String exe = "CREATE TABLE IF NOT EXISTS " + CART_TABLE
                + "(" + COLUMN_ID + " integer primary key autoincrement, "
                + STAGE_ID + " TEXT ,"
                + LEVEL_ID + " TEXT ,"
                + QUIZ_ID + " TEXT ,"
                + QUIZ_COIN + " TEXT ,"
                + IS_PLAYED + " TEXT "
                + ")";

        db.execSQL(exe);

    }

    public boolean save_score(HashMap<String, String> map) {
        db = getWritableDatabase();
//        if (isInCart(map.get(COLUMN_ID))) {
//            db.execSQL("update " + CART_TABLE + " set " + COLUMN_QTY + " = '" + Qty + "' where " + COLUMN_ID + "=" + map.get(COLUMN_ID));
//            return false;
//        }
        //else {
        ContentValues values = new ContentValues();

        values.put(COLUMN_ID, map.get(COLUMN_ID));
        values.put(STAGE_ID, map.get(STAGE_ID));
        values.put(LEVEL_ID, map.get(LEVEL_ID));
        values.put(QUIZ_COIN, map.get(QUIZ_COIN));
        values.put(IS_PLAYED, map.get(IS_PLAYED));
        values.put(QUIZ_ID, map.get(QUIZ_ID));

        db.insert(CART_TABLE, null, values);
        db.close();
        return true;
    }


    public ArrayList<HashMap<String, String>> getScore() {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        db = getReadableDatabase();
        String qry = "Select *  from " + CART_TABLE;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put(COLUMN_ID, cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
            map.put(STAGE_ID, cursor.getString(cursor.getColumnIndex(STAGE_ID)));
            map.put(LEVEL_ID, cursor.getString(cursor.getColumnIndex(LEVEL_ID)));
            map.put(QUIZ_COIN, cursor.getString(cursor.getColumnIndex(QUIZ_COIN)));
            map.put(QUIZ_ID, cursor.getString(cursor.getColumnIndex(QUIZ_ID)));
            map.put(IS_PLAYED, cursor.getString(cursor.getColumnIndex(IS_PLAYED)));

            list.add(map);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }


    public int getItemCount() {
        db = getReadableDatabase();
        String qry = "Select *  from " + CART_TABLE;
        Cursor cursor = db.rawQuery(qry, null);
        return cursor.getCount();
    }


    public boolean isInTable(String id) {
        db = getReadableDatabase();
        String qry = "Select *  from " + CART_TABLE + " where " + QUIZ_ID + " = " + id;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) return true;

        return false;
    }

    //    // code to update the single contact
    public boolean updateTable(String id, String fav) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUIZ_COIN, fav);
        // contentValues.put(TextNote_Content, content);
        db.update(CART_TABLE, contentValues, COLUMN_ID + " = ? ",
                new String[]{String.valueOf(id)});

        return true;
    }


//    public String getAnyOneItem(String id) {
//        db = getReadableDatabase();
//        String qry = "Select *  from " + CART_TABLE + " where " + COLUMN_ID + " = " + id;
//        Cursor cursor = db.rawQuery(qry, null);
//        cursor.moveToFirst();
//        return cursor.getString(cursor.getColumnIndex(DamageFiles));
//
//    }

//    public String getInCartItemQty(String id) {
//        if (isInCart(id)) {
//            db = getReadableDatabase();
//            String qry = "Select *  from " + CART_TABLE + " where " + COLUMN_ID + " = " + id;
//            Cursor cursor = db.rawQuery(qry, null);
//            cursor.moveToFirst();
//            return cursor.getString(cursor.getColumnIndex(COLUMN_QTY));
//        } else {
//            return "0.0";
//        }
//    }


    //    public String getTotalAmount() {
//        db = getReadableDatabase();
//        String qry = "Select SUM(" + COLUMN_QTY + " * " + COLUMN_PRICE + ") as total_amount  from " + CART_TABLE;
//        Cursor cursor = db.rawQuery(qry, null);
//        cursor.moveToFirst();
//        String total = cursor.getString(cursor.getColumnIndex("total_amount"));
//        if (total != null) {
//
//            return total;
//        } else {
//            return "0";
//        }
//    }

    public String getFavConcatString() {
        db = getReadableDatabase();
        String qry = "Select *  from " + CART_TABLE;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();
        String concate = "";
        for (int i = 0; i < cursor.getCount(); i++) {
            if (concate.equalsIgnoreCase("")) {
                concate = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
            } else {
                concate = concate + "_" + cursor.getString(cursor.getColumnIndex(COLUMN_ID));
            }
            cursor.moveToNext();
        }
        return concate;
    }

    public void clearCart() {
        db = getReadableDatabase();
        db.execSQL("delete from " + CART_TABLE);
    }

    public void removeItemFromCart(String id) {
        db = getReadableDatabase();
        db.execSQL("delete from " + CART_TABLE + " where " + COLUMN_ID + " = " + id);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
