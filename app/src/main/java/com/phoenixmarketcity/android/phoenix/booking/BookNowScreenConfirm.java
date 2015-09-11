package com.phoenixmarketcity.android.phoenix.booking;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.activities.BaseActivity;

/**
 * Created by mujeeb on 07-Jul-15.
 */
public class BookNowScreenConfirm extends BaseActivity {
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    //private BookNowScreen.BookTicketData mTicketData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setContentView(R.layout.book_now_screen_confirm);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        TextView bsc_titletext = (TextView) findViewById(R.id.bsc_title);
        TextView bsc_datetext = (TextView) findViewById(R.id.bsc_date);
        TextView bsc_timetext = (TextView) findViewById(R.id.bsc_time);
        TextView bsc_tickettype1 = (TextView) findViewById(R.id.bsc_tickettype_1);
        TextView bsc_tickettype1QTY = (TextView) findViewById(R.id.bsc_tickettype_1_qty);
        TextView bsc_tickettype1Price = (TextView) findViewById(R.id.bsc_tickettype_1_price);
        TextView bsc_tickettype2 = (TextView) findViewById(R.id.bsc_tickettype_2);
        TextView bsc_tickettype2QTY = (TextView) findViewById(R.id.bsc_tickettype_2_qty);
        TextView bsc_tickettype2Price = (TextView) findViewById(R.id.bsc_tickettype_2_price);
        TextView bsc_tickettype3 = (TextView) findViewById(R.id.bsc_tickettype_3);
        TextView bsc_tickettype3QTY = (TextView) findViewById(R.id.bsc_tickettype_3_qty);
        TextView bsc_tickettype3Price = (TextView) findViewById(R.id.bsc_tickettype_3_price);
        TextView bsc_subtotalqty = (TextView) findViewById(R.id.bsc_subtotal_QTY);
        TextView bsc_subtotalprice = (TextView) findViewById(R.id.bsc_subtotal_PRICE);
        TextView bsc_totalprice = (TextView) findViewById(R.id.bsc_total_price);
        TextView bsc_internetcharges_price = (TextView) findViewById(R.id.internetcharges_price);
        TextView bsc_tax_price = (TextView) findViewById(R.id.tax_price);
        LinearLayout ll1=(LinearLayout)findViewById(R.id.TicketLayout_1);
        LinearLayout ll2=(LinearLayout)findViewById(R.id.TicketLayout_2);
        LinearLayout ll3=(LinearLayout)findViewById(R.id.TicketLayout_3);
        LinearLayout ll4=(LinearLayout)findViewById(R.id.layout_internethandlingfees);
        LinearLayout ll5=(LinearLayout)findViewById(R.id.layout_tax);
        //To get Data from previous activity
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b != null) {
            String passed_title = (String) b.get("Title");
            String passed_date = (String) b.get("Date");
            String passed_time = (String) b.get("Time");
            String passed_ticket_1 = (String) b.get("Tickettype_1");
            String passed_ticket_1_qty = (String) b.get("Tickettype_1_QTY");
            String passed_ticket_1_price = (String) b.get("Tickettype_1_PRICE");
            String passed_ticket_2 = (String) b.get("Tickettype_2");
            String passed_ticket_2_qty = (String) b.get("Tickettype_2_QTY");
            String passed_ticket_2_price = (String) b.get("Tickettype_2_PRICE");
            String passed_ticket_3 = (String) b.get("Tickettype_3");
            String passed_ticket_3_qty = (String) b.get("Tickettype_3_QTY");
            String passed_ticket_3_price = (String) b.get("Tickettype_3_PRICE");
            String passed_subtotal_qty = (String) b.get("Total_QTY");
            String passed_subtotal_price = (String) b.get("Total_PRICE");
            bsc_titletext.setText(passed_title);
            bsc_datetext.setText(passed_date);
            bsc_timetext.setText(passed_time);
           // bsc_subtotalqty.setText(passed_subtotal_qty + " Tickets");
            String m=String.valueOf(passed_ticket_1_qty);
            Integer im=Integer.valueOf(m);
            if (im==1) {
                bsc_subtotalqty.setText(passed_subtotal_qty + " Ticket");
            }
            else {
                bsc_subtotalqty.setText(passed_subtotal_qty + " Tickets");
            }
            bsc_subtotalprice.setText(passed_subtotal_price);

            //For total calculation
            String  i=bsc_subtotalprice.getText().toString();
            String j=bsc_internetcharges_price.getText().toString();
            String k=bsc_tax_price.getText().toString();
            int q = (Integer.valueOf(i)) + (Integer.valueOf(j))+(Integer.valueOf(k));
            String qq = String.valueOf(q);
            bsc_totalprice.setText(qq);

            //For Hiding Ticket 1 Layout
            String t1=String.valueOf(b.get("Tickettype_1_PRICE"));
            int tt1=Integer.valueOf(t1);
            String m1=String.valueOf(passed_ticket_1_qty);
            Integer im1=Integer.valueOf(m1);
            if (im1==1) {
                bsc_tickettype1QTY.setText(passed_ticket_1_qty + " Ticket");
            }
            else {
                bsc_tickettype1QTY.setText(passed_ticket_1_qty + " Tickets");
            }

            if(tt1==0){
                ll1.setVisibility(View.GONE);
                bsc_tickettype1.setVisibility(View.GONE);
                bsc_tickettype1QTY.setVisibility(View.GONE);
                bsc_tickettype1Price.setVisibility(View.GONE);}
            else {
                bsc_tickettype1.setText(passed_ticket_1);
              //  bsc_tickettype1QTY.setText(passed_ticket_1_qty + " Tickets");
                bsc_tickettype1Price.setText(passed_ticket_1_price); }

            //For Hiding Ticket 2 Layout
            String t2=String.valueOf(b.get("Tickettype_2_PRICE"));
            int tt2=Integer.valueOf(t2);
            String m2=String.valueOf(passed_ticket_2_qty);
            Integer im2=Integer.valueOf(m2);
            if (im2==1) {
                bsc_tickettype2QTY.setText(passed_ticket_2_qty + " Ticket");
            }
            else {
                bsc_tickettype2QTY.setText(passed_ticket_2_qty + " Tickets");
            }
            if(tt2==0) {
                ll2.setVisibility(View.GONE);
                bsc_tickettype2.setVisibility(View.GONE);
                bsc_tickettype2QTY.setVisibility(View.GONE);
                bsc_tickettype2Price.setVisibility(View.GONE); }
            else {
                bsc_tickettype2.setText(passed_ticket_2);
                bsc_tickettype2QTY.setText(passed_ticket_2_qty + " Tickets");
                bsc_tickettype2Price.setText(passed_ticket_2_price); }

            //For Hiding Ticket 3 Layout
            String t3=String.valueOf(b.get("Tickettype_3_PRICE"));
            int tt3=Integer.valueOf(t3);
            String m3=String.valueOf(passed_ticket_3_qty);
            Integer im3=Integer.valueOf(m3);
            if (im3==1) {
                bsc_tickettype3QTY.setText(passed_ticket_3_qty + " Ticket");
            }
            else {
                bsc_tickettype3QTY.setText(passed_ticket_3_qty + " Tickets");
            }

            if(tt3==0) {
                ll3.setVisibility(View.GONE);
                bsc_tickettype3.setVisibility(View.GONE);
                bsc_tickettype3QTY.setVisibility(View.GONE);
                bsc_tickettype3Price.setVisibility(View.GONE); }
            else {
                bsc_tickettype3.setText(passed_ticket_3);
              //  bsc_tickettype3QTY.setText(passed_ticket_3_qty + " Tickets");
                bsc_tickettype3Price.setText(passed_ticket_3_price); }

            //For Hiding Internetcharges Layout
            String j1=String.valueOf(bsc_internetcharges_price.getText().toString());
            int jj1=Integer.valueOf(j1);
            if(jj1==0){
                ll4.setVisibility(View.GONE); }

            //For Hiding Tax Layout
            String k1=String.valueOf(bsc_tax_price.getText().toString());
            int kk1=Integer.valueOf(k1);
            if(kk1==0){
                ll5.setVisibility(View.GONE); }
        }
    }
}
