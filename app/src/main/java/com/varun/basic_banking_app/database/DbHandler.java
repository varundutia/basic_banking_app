package com.varun.basic_banking_app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.varun.basic_banking_app.model.Customer;
import com.varun.basic_banking_app.util.util;

import java.util.ArrayList;
import java.util.List;

import static com.varun.basic_banking_app.util.util.DATABASE_NAME;

public class DbHandler extends SQLiteOpenHelper {


    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, util.DATABASE_VERSION);
    }
    //we create our table here
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CUSTOMER_TABLE = "CREATE TABLE " + util.TABLE_NAME+"("
                + util.KEY_ID + " INTEGER PRIMARY KEY,"+util.KEY_NAME + " TEXT,"
                + util.KEY_EMAIL +" TEXT,"+ util.KEY_AMOUNT+ " TEXT"+")";
        db.execSQL(CREATE_CUSTOMER_TABLE);//creating our table
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String DROP_TABLE = String.valueOf(com.varun.basic_banking_app.R.string.db_drop);
        db.execSQL(DROP_TABLE, new String[]{DATABASE_NAME});
        onCreate(db);
    }
    /*
       CRUD= Create,Read,Update,Delete
     */
    //Add contact
    public void addCustomer(Customer customer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(util.KEY_NAME,customer.getName());
        values.put(util.KEY_EMAIL,customer.getEmail());
        values.put(util.KEY_AMOUNT,customer.getBalance());

        //Insert to row
        db.insert(util.TABLE_NAME,null,values);
        Log.d("add","customer added");
        db.close();
    }
    public Customer getCustomer(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.query(util.TABLE_NAME, new String[]{ util.KEY_ID,util.KEY_NAME,util.KEY_EMAIL,util.KEY_AMOUNT},util.KEY_ID +"=?",new String[]{String.valueOf(id)},null,null,null);
        Customer contact = new Customer();
        if(cursor !=null) {
            cursor.moveToFirst();
            contact.setId(Integer.parseInt(cursor.getString(0)));
            contact.setName(cursor.getString(1));
            contact.setEmail(cursor.getString(2));
            contact.setBalance(Double.parseDouble(cursor.getString(3)));
        }
        return contact;
    }
    public List<Customer> getAllCustomers(){
        List<Customer> contactList =new ArrayList<>();
        String selectAll="SELECT * FROM "+util.TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectAll,null);
        if(cursor.moveToFirst()){
            do {
                Customer contact=new Customer();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setEmail(cursor.getString(2));
                contact.setBalance(Double.parseDouble(cursor.getString(3)));
                contactList.add(contact);
            }while(cursor.moveToNext());
        }
        return contactList;
    }
    public int updateCustomer(Customer contact){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(util.KEY_AMOUNT,contact.getBalance());
        return db.update(util.TABLE_NAME,values,util.KEY_ID+"=?",new String[]{ String.valueOf(contact.getId())});
    }
    public void deleteContact(int i){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(util.TABLE_NAME,util.KEY_ID+"=?",new String[]{String.valueOf(i)});
    }

    public int getCount(){
        String countQuery = "SELECT * FROM "+util.TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(countQuery,null);
        return cursor.getCount();
    }
}
