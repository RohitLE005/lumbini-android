package com.phoenixmarketcity.android.phoenix.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.phoenixmarketcity.android.phoenix.R;


public class AddnewaddressActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //action bar code//
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setContentView(R.layout.activity_addnewaddress);

//        final TextView username=(TextView)findViewById(R.id.Uname);
        final TextView defaultusername=(TextView)findViewById(R.id.UserName);

        final EditText et1 = (EditText) findViewById(R.id.name);
        final EditText et2 = (EditText) findViewById(R.id.address);
        final EditText et3 = (EditText) findViewById(R.id.landmark);
        final EditText et4 = (EditText) findViewById(R.id.pincode);
        final EditText et5 = (EditText) findViewById(R.id.phonenumber);
        final Button sav = (Button) findViewById(R.id.save);

        final Intent i=getIntent();
        String name=i.getStringExtra("name");
        et1.setText(name);
        String address1=i.getStringExtra("address1");
        et2.setText(address1);
        String address2=i.getStringExtra("address2");
        et3.setText(address2);
        String address3=i.getStringExtra("address3");
        et4.setText(address3);
        String phonenumber=i.getStringExtra("contactnumber");
        et5.setText(phonenumber);


        sav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et1.getText().length() == 0) {


                    et1.setBackgroundResource(R.drawable.errorborder);
                    Toast.makeText(getApplicationContext(), "Please fill in all the mandatory fields ", Toast.LENGTH_LONG).show();
                }
                else if (et2.getText().length() == 0) {


                    et2.setBackgroundResource(R.drawable.errorborder);
                    Toast.makeText(getApplicationContext(), "Please fill in all the mandatory fields ", Toast.LENGTH_LONG).show();
                }
                else if (et3.getText().length() == 0) {


                    et3.setBackgroundResource(R.drawable.errorborder);
                    Toast.makeText(getApplicationContext(), "Please fill in all the mandatory fields ", Toast.LENGTH_LONG).show();
                }
               else if (et4.getText().length() == 0) {


                    et4.setBackgroundResource(R.drawable.errorborder);
                    Toast.makeText(getApplicationContext(), "Please fill in all the mandatory fields ", Toast.LENGTH_LONG).show();
                }
               else if (et5.getText().length() == 0) {


                    et5.setBackgroundResource(R.drawable.errorborder);
                    Toast.makeText(getApplicationContext(), "Please fill in all the mandatory fields ", Toast.LENGTH_LONG).show();
                }



                else {
                    Intent intent = new Intent(AddnewaddressActivity.this, MyaddressActivity.class);
                    intent.putExtra("username",et1.getText().toString());
                    intent.putExtra("address",et2.getText().toString());
                    intent.putExtra("landmark",et3.getText().toString());
                    intent.putExtra("pincode",et4.getText().toString());
                    intent.putExtra("phonenumber",et5.getText().toString());
                    startActivity(intent);

                }

            }

            }

            );
        }


    }
