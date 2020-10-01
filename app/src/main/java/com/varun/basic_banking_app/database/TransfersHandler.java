package com.varun.basic_banking_app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.varun.basic_banking_app.model.Customer;
import com.varun.basic_banking_app.model.Transfer_Model;
import com.varun.basic_banking_app.util.util;

import java.util.ArrayList;
import java.util.List;

import static com.varun.basic_banking_app.util.util.DATABASE_NAME1;

public class TransfersHandler extends SQLiteOpenHelper {

    public TransfersHandler(Context context) {
        super(context, DATABASE_NAME1, null, util.DATABASE_VERSION);
    }
    //we create our table here
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CUSTOMER_TABLE = "CREATE TABLE " + util.TABLE_NAME_T+"("
                + util.KEY_ID + " INTEGER PRIMARY KEY,"+util.KEY_NAME_1 + " TEXT,"
                + util.KEY_NAME_2 +" TEXT,"+ util.KEY_AMOUNT+ " TEXT"+")";
        db.execSQL(CREATE_CUSTOMER_TABLE);//creating our table
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String DROP_TABLE = String.valueOf(com.varun.basic_banking_app.R.string.db_drop);
        db.execSQL(DROP_TABLE, new String[]{DATABASE_NAME1});
        onCreate(db);
    }
    /*
       CRUD= Create,Read,Update,Delete
     */
    //Add contact
    public void addTransfer(Transfer_Model t){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(util.KEY_NAME_1,t.getN1());
        values.put(util.KEY_NAME_2,t.getN2());
        values.put(util.KEY_AMOUNT,String.valueOf(t.getAmount()));

        //Insert to row
        db.insert(util.TABLE_NAME_T,null,values);
        Log.d("add","customer added");
        db.close();
    }

    public List<Transfer_Model> getAllTransfers(){
        List<Transfer_Model> contactList =new ArrayList<>();
        String selectAll="SELECT * FROM "+util.TABLE_NAME_T;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectAll,null);
        if(cursor.moveToFirst()){
            do {
                Transfer_Model contact=new Transfer_Model();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setN1(cursor.getString(1));
                contact.setN2(cursor.getString(2));
                contact.setAmount(Double.parseDouble(cursor.getString(3)));
                contactList.add(contact);
            }while(cursor.moveToNext());
        }
        return contactList;
    }
    public void deleteContact(int i){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(util.TABLE_NAME_T,util.KEY_ID+"=?",new String[]{String.valueOf(i)});
    }

    public int getCount(){
        String countQuery = "SELECT * FROM "+util.TABLE_NAME_T;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(countQuery,null);
        return cursor.getCount();
    }
}
