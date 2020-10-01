package com.varun.basic_banking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.varun.basic_banking_app.database.DbHandler;
import com.varun.basic_banking_app.database.TransfersHandler;
import com.varun.basic_banking_app.model.Customer;
import com.varun.basic_banking_app.model.Transfer_Model;

import java.util.ArrayList;
import java.util.List;

public class TransferCust extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> contactArrayList;
    private ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_cust);
        listView=findViewById(R.id.listView1);
        final DbHandler db = new DbHandler(TransferCust.this);
        final TransfersHandler db1 = new TransfersHandler(TransferCust.this);
        final List<Customer> customers = db.getAllCustomers();
        int count = customers.size();
        if (customers != null) {
            contactArrayList = new ArrayList<String>();
            for (Customer contact : customers) {
                if(getIntent().getIntExtra("id",0)!=contact.getId())
                contactArrayList.add(contact.getName());
                Log.d("contacts", String.valueOf(contact));
            }
            arrayAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    contactArrayList
            );
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Transfer_Model t=new Transfer_Model();
                    Customer c=customers.get(i+1);
                    double tra=getIntent().getDoubleExtra("amount",0);
                    double amount=c.getBalance()+tra;
                    c.setBalance(amount);
                    db.updateCustomer(c);
                    t.setAmount(tra);
                    t.setN1(db.getCustomer(getIntent().getIntExtra("id",0)).getName());
                    t.setN2(c.getName());
                    db1.addTransfer(t);
                    Toast.makeText(TransferCust.this,"Transfer Success",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(TransferCust.this,MainActivity.class));
                    finish();
                }
            });
        }
    }
}