//created by LE005//
package com.phoenixmarketcity.android.phoenix.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;
import com.phoenixmarketcity.android.phoenix.R;

import org.parceler.Parcel;

import java.util.List;


public class MyaddressActivity extends BaseActivity {
    TextView T2,T3;
    private String jsondata="useraddress";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //phoniex theme action bar//
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setContentView(R.layout.activity_myaddress);


        final TextView username = (TextView) findViewById(R.id.UserName);
        final TextView addressline1 = (TextView) findViewById(R.id.addressline1);
        final TextView addressline2 = (TextView) findViewById(R.id.addressline2);
        final TextView addressline3 = (TextView) findViewById(R.id.cityCode);
        final TextView phonenumber = (TextView) findViewById(R.id.contactnumber);
        final TextView deletecard = (TextView) findViewById(R.id.removecard2);
        final TextView editaddress = (TextView) findViewById(R.id.editdefaultaddress);
        final TextView editaddress1 = (TextView) findViewById(R.id.editalternateaddress);
        final ImageView img =  (ImageView) findViewById(R.id.ticktodisplay);
        final ImageView img1 = (ImageView) findViewById(R.id.current);
        final TextView deletecard1 = (TextView) findViewById(R.id.removecard);
        final TextView tosetdefault = (TextView) findViewById(R.id.clicktosetdefault);
        final TextView changedefault = (TextView) findViewById(R.id.cadd);
        final TextView addnewaddrs = (TextView) findViewById(R.id.addnewaddrs);
        final TextView alternateaddressusername = (TextView) findViewById(R.id.Uname);
        final TextView alternateaddressaddressline1 = (TextView) findViewById(R.id.alternateaddress1);
        final TextView alternateaddressaddressline2 = (TextView) findViewById(R.id.alternateaddresslandmark);
        final TextView alternateaddressaddressline3 = (TextView) findViewById(R.id.alternateaddressPinCode);
        final TextView alternateaddressphonenumber = (TextView) findViewById(R.id.alternateaddressPhonenumber);


         //to set an address as a default//
        //toggles the other as set as default//


        tosetdefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                T2 = (TextView) findViewById(R.id.clicktosetdefault);
                T2.setText("Default");

                img.setVisibility(View.VISIBLE);
                T3 = (TextView) findViewById(R.id.cadd);
                T3.setText("Set as Default");
                ImageView img1 = (ImageView) findViewById(R.id.current);
                img1.setVisibility(View.INVISIBLE);
            }
        });

        changedefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView T4 = (TextView) findViewById(R.id.cadd);
                T4.setText("Default");
                ImageView img1 = (ImageView) findViewById(R.id.current);
                img1.setVisibility(View.VISIBLE);
                TextView T2 = (TextView) findViewById(R.id.clicktosetdefault);
                T2.setText("Set as Default");
                ImageView img = (ImageView) findViewById(R.id.ticktodisplay);
                img.setVisibility(View.INVISIBLE);

            }
        });
        //navigation to the add new_addressactivity//

        addnewaddrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyaddressActivity.this, AddnewaddressActivity.class);
                startActivity(intent);
            }
        });


        //navigates to add_new_Address_Activity and sets the address in the fields and changes fields


{
    editaddress.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(editaddress.isPressed()||editaddress.isActivated()||editaddress.isClickable())
            {
                Intent i = new Intent(MyaddressActivity.this, AddnewaddressActivity.class);
                i.putExtra("name", username.getText().toString());
                i.putExtra("address1", addressline1.getText().toString());
                i.putExtra("address2", addressline2.getText().toString());
                i.putExtra("address3", addressline3.getText().toString());
                i.putExtra("contactnumber", phonenumber.getText().toString());
                startActivity(i);
            }
            else
            {
                Intent alternateaddressedit = new Intent(MyaddressActivity.this, AddnewaddressActivity.class);
                alternateaddressedit.putExtra("name", alternateaddressusername.getText().toString());
                alternateaddressedit.putExtra("address1", alternateaddressaddressline1.getText().toString());
                alternateaddressedit.putExtra("address2", alternateaddressaddressline2.getText().toString());
                alternateaddressedit.putExtra("address3", alternateaddressaddressline3.getText().toString());
                alternateaddressedit.putExtra("contactnumber", alternateaddressphonenumber.getText().toString());
                startActivity(alternateaddressedit);

            }

        }
    });
}

        editaddress1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editaddress1.isPressed())
                {
                    Intent alternateaddressedit = new Intent(MyaddressActivity.this, AddnewaddressActivity.class);
                    alternateaddressedit.putExtra("name", alternateaddressusername.getText().toString());
                    alternateaddressedit.putExtra("address1", alternateaddressaddressline1.getText().toString());
                    alternateaddressedit.putExtra("address2", alternateaddressaddressline2.getText().toString());
                    alternateaddressedit.putExtra("address3", alternateaddressaddressline3.getText().toString());
                    alternateaddressedit.putExtra("contactnumber", alternateaddressphonenumber.getText().toString());
                    startActivity(alternateaddressedit);
                }
                else
                {

                    Intent i = new Intent(MyaddressActivity.this, AddnewaddressActivity.class);
                    i.putExtra("name", username.getText().toString());
                    i.putExtra("address1", addressline1.getText().toString());
                    i.putExtra("address2", addressline2.getText().toString());
                    i.putExtra("address3", addressline3.getText().toString());
                    i.putExtra("contactnumber", phonenumber.getText().toString());
                    startActivity(i);
                }

            }
        });
        //removes the address card if clicked on remove button only if the address is not default//

        deletecard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (img1.isShown()){
                    Toast.makeText(MyaddressActivity.this, "Default address cannot be deleted", Toast.LENGTH_SHORT).show();

                }
                else {
                    LinearLayout L1 = (LinearLayout) findViewById(R.id.parentL1);
                    L1.setVisibility(View.GONE);

                }
            }
        });

        deletecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (img.isShown()){
                    Toast.makeText(MyaddressActivity.this, "Default address cannot be deleted", Toast.LENGTH_SHORT).show();

                }
                else {
                    LinearLayout L2 = (LinearLayout) findViewById(R.id.ParentL2);
                    L2.setVisibility(View.GONE);

                }
            }
        });
        Intent i=getIntent();
        String name=i.getStringExtra("username");
        username.setText(name);
        String address=i.getStringExtra("address");
        addressline1.setText(address);
        String landmark=i.getStringExtra("landmark");
        addressline2.setText(landmark);
        String pincode=i.getStringExtra("pincode");
        addressline3.setText(pincode);
        String phone=i.getStringExtra("phonenumber");
        phonenumber.setText(phone);

    }







}
