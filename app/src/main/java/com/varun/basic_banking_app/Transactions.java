package com.varun.basic_banking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.varun.basic_banking_app.database.DbHandler;
import com.varun.basic_banking_app.database.TransfersHandler;
import com.varun.basic_banking_app.model.Customer;
import com.varun.basic_banking_app.model.Transfer_Model;

import java.util.ArrayList;
import java.util.List;

public class Transactions extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> contactArrayList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        listView=findViewById(R.id.transactionsList);
        TransfersHandler db = new TransfersHandler(Transactions.this);
        int c=db.getCount();
        final List<Transfer_Model> customers = db.getAllTransfers();
        if (customers != null) {
            contactArrayList = new ArrayList<String>();
            for (Transfer_Model contact : customers) {
                contactArrayList.add(contact.getN1()+" --> "+contact.getN2()+" "+contact.getAmount());
                Log.d("contacts", String.valueOf(contact.getN1()));
            }
            arrayAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    contactArrayList
            );
            listView.setAdapter(arrayAdapter);
        }

    }
}