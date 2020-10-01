package com.varun.basic_banking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.varun.basic_banking_app.database.DbHandler;
import com.varun.basic_banking_app.model.Customer;

public class CustomerView extends AppCompatActivity {
    private TextView name,email,amount;
    private EditText editText;
    private Button transfer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view);
        name=findViewById(R.id.name_view);
        email=findViewById(R.id.email);
        amount=findViewById(R.id.amount);
        editText = findViewById(R.id.edittext);
        transfer=findViewById(R.id.button);
        int id = getIntent().getIntExtra("id",0);
        final DbHandler db = new DbHandler(CustomerView.this);
        final Customer c=db.getCustomer(id);
        name.setText(c.getName());
        email.setText(c.getEmail());
        amount.setText(String.valueOf(c.getBalance()));
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double amount1=Double.parseDouble(String.valueOf(editText.getText()));
                c.setBalance(c.getBalance()-amount1);
                db.updateCustomer(c);
                Intent intent=new Intent(CustomerView.this,TransferCust.class);
                intent.putExtra("amount",amount1);
                intent.putExtra("id",c.getId());
                startActivity(intent);
                finish();
            }
        });
    }
}