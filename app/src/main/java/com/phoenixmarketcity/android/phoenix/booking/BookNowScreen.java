package com.phoenixmarketcity.android.phoenix.booking;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.actions.BaseAction;
import com.phoenixmarketcity.android.phoenix.activities.BaseActivity;

import static java.lang.Integer.*;

/**
 * Created by mujeeb on 06-Jul-15.
 */
public class BookNowScreen extends BaseActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
    private Context mContext;
    private static int counter1 = 0;
    private static int counter2 = 0;
    private static int counter3 = 0;
    private String stringVal = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        setContentView(R.layout.book_now_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        createPreEventCards();
    }

    private void createPreEventCards() {
        final TextView bsTitle = (TextView) findViewById(R.id.bsl_title);
        bsTitle.setText(getIntent().getStringExtra("EventTitle"));
        final TextView bsDate = (TextView) findViewById(R.id.bs_date);
        bsDate.setText(getIntent().getStringExtra("EventDate"));
        final TextView bsTime = (TextView) findViewById(R.id.bs_time);
        bsTime.setText(getIntent().getStringExtra("EventTime"));

        //For BookScreen TicketType 1
        final TextView tickettype1 = (TextView) findViewById(R.id.bs_tickettype_1);
        tickettype1.setText(getIntent().getStringExtra("GTicket"));
        LinearLayout tickettype1DEC = (LinearLayout) findViewById(R.id.bs_tickettype_1_qty_dec);
        final TextView tickettype1QTY = (TextView) findViewById(R.id.bs_tickettype_1_qty);
        LinearLayout tickettype1INC = (LinearLayout) findViewById(R.id.bs_tickettype_1_qty_inc);
        final TextView tickettype1Price = (TextView) findViewById(R.id.bs_tickettype_1_price);
        tickettype1Price.setText(getIntent().getStringExtra("GPrice"));

        //For BookScreen TicketType 2
        final TextView tickettype2 = (TextView) findViewById(R.id.bs_tickettype_2);
        tickettype2.setText(getIntent().getStringExtra("STicket"));
        LinearLayout tickettype2DEC = (LinearLayout) findViewById(R.id.bs_tickettype_2_qty_dec);
        final TextView tickettype2QTY = (TextView) findViewById(R.id.bs_tickettype_2_qty);
        LinearLayout tickettype2INC = (LinearLayout) findViewById(R.id.bs_tickettype_2_qty_inc);
        final TextView tickettype2Price = (TextView) findViewById(R.id.bs_tickettype_2_price);
        tickettype2Price.setText(getIntent().getStringExtra("SPrice"));

        //For BookScreen TicketType 3
        final TextView tickettype3 = (TextView) findViewById(R.id.bs_tickettype_3);
        tickettype3.setText(getIntent().getStringExtra("VIPTicket"));
        LinearLayout tickettype3DEC = (LinearLayout) findViewById(R.id.bs_tickettype_3_qty_dec);
        final TextView tickettype3QTY = (TextView) findViewById(R.id.bs_tickettype_3_qty);
        LinearLayout tickettype3INC = (LinearLayout) findViewById(R.id.bs_tickettype_3_qty_inc);
        final TextView tickettype3Price = (TextView) findViewById(R.id.bs_tickettype_3_price);
        tickettype3Price.setText(getIntent().getStringExtra("VIPPrice"));

        //For Total Price, No.Of seats
        final TextView totalprice = (TextView) findViewById(R.id.bs_total_price);
        final TextView totalseats = (TextView) findViewById(R.id.bs_total_qty);


        //For Button View
      Button bs_button = (Button) findViewById(R.id.BS_Button);
        bs_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i = tickettype1Price.getText().toString();
                String j = tickettype2Price.getText().toString();
                String k = tickettype3Price.getText().toString();
                String n = tickettype1QTY.getText().toString();
                String o = tickettype2QTY.getText().toString();
                String p = tickettype3QTY.getText().toString();
                final int r = (valueOf(i) * valueOf(n));
                final int s = (valueOf(j) * valueOf(o));
                final int t = (valueOf(k) * valueOf(p));
                final String rr = String.valueOf(r);
                final String ss = String.valueOf(s);
                final String tt = String.valueOf(t);

                if(r==0&&s==0&&t==0){
                    Toast.makeText(getBaseContext(),"Select Seat Quantities",Toast.LENGTH_SHORT).show();}
                else {
                    Intent intent = new Intent(getBaseContext(), BookNowScreenConfirm.class);
                    intent.putExtra("Title", bsTitle.getText().toString());
                    intent.putExtra("Date", bsDate.getText().toString());
                    intent.putExtra("Time", bsTime.getText().toString());
                    intent.putExtra("Tickettype_1", tickettype1.getText().toString());
                    intent.putExtra("Tickettype_1_QTY", tickettype1QTY.getText().toString());
                    intent.putExtra("Tickettype_1_PRICE", rr);
                    intent.putExtra("Tickettype_2", tickettype2.getText().toString());
                    intent.putExtra("Tickettype_2_QTY", tickettype2QTY.getText().toString());
                    intent.putExtra("Tickettype_2_PRICE", ss);
                    intent.putExtra("Tickettype_3", tickettype3.getText().toString());
                    intent.putExtra("Tickettype_3_QTY", tickettype3QTY.getText().toString());
                    intent.putExtra("Tickettype_3_PRICE", tt);
                    intent.putExtra("Total_QTY", totalseats.getText().toString());
                    intent.putExtra("Total_PRICE", totalprice.getText().toString());
                    startActivity(intent);
                }
            }
        });

        tickettype1DEC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (counter1 == 0) {
                    counter1 = 0;
                } else {
                    counter1--;
                    stringVal = Integer.toString(counter1);
                    tickettype1QTY.setText(stringVal);
                }
                String i = tickettype1Price.getText().toString();
                String j = tickettype2Price.getText().toString();
                String k = tickettype3Price.getText().toString();
                String m = totalprice.getText().toString();
                String n = tickettype1QTY.getText().toString();
                String o = tickettype2QTY.getText().toString();
                String p = tickettype3QTY.getText().toString();
                int iii = (valueOf(i) * valueOf(n)) + (valueOf(j) * valueOf(o)) + (valueOf(k) * valueOf(p));
                String iiii = String.valueOf(iii);
                totalprice.setText(iiii);

                int q = (valueOf(n)) + (valueOf(o)) + (valueOf(p));
                String qq = String.valueOf(q);
                totalseats.setText(qq);
            }
        });

        tickettype1INC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counter1++;
                stringVal = Integer.toString(counter1);
                tickettype1QTY.setText(stringVal);

                String i = tickettype1Price.getText().toString();
                String j = tickettype2Price.getText().toString();
                String k = tickettype3Price.getText().toString();
                String m = totalprice.getText().toString();
                String n = tickettype1QTY.getText().toString();
                String o = tickettype2QTY.getText().toString();
                String p = tickettype3QTY.getText().toString();
                int iii = (valueOf(i) * valueOf(n)) + (valueOf(j) * valueOf(o)) + (valueOf(k) * valueOf(p));
                String iiii = String.valueOf(iii);
                totalprice.setText(iiii);
                int q = (valueOf(n)) + (valueOf(o)) + (valueOf(p));
                String qq = String.valueOf(q);
                totalseats.setText(qq);
            }
        });

        tickettype2DEC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (counter2 == 0) {
                    counter2 = 0;
                } else {
                    counter2--;
                    stringVal = Integer.toString(counter2);
                    tickettype2QTY.setText(stringVal);
                }
                String i = tickettype1Price.getText().toString();
                String j = tickettype2Price.getText().toString();
                String k = tickettype3Price.getText().toString();
                String m = totalprice.getText().toString();
                String n = tickettype1QTY.getText().toString();
                String o = tickettype2QTY.getText().toString();
                String p = tickettype3QTY.getText().toString();
                int iii = (valueOf(i) * valueOf(n)) + (valueOf(j) * valueOf(o)) + (valueOf(k) * valueOf(p));
                String iiii = String.valueOf(iii);
                totalprice.setText(iiii);
                int q = (valueOf(n)) + (valueOf(o)) + (valueOf(p));
                String qq = String.valueOf(q);
                totalseats.setText(qq);
            }
        });

        tickettype2INC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter2++;
                stringVal = Integer.toString(counter2);
                tickettype2QTY.setText(stringVal);

                String i = tickettype1Price.getText().toString();
                String j = tickettype2Price.getText().toString();
                String k = tickettype3Price.getText().toString();
                String m = totalprice.getText().toString();
                String n = tickettype1QTY.getText().toString();
                String o = tickettype2QTY.getText().toString();
                String p = tickettype3QTY.getText().toString();
                int iii = (valueOf(i) * valueOf(n)) + (valueOf(j) * valueOf(o)) + (valueOf(k) * valueOf(p));
                String iiii = String.valueOf(iii);
                totalprice.setText(iiii);
                int q = (valueOf(n)) + (valueOf(o)) + (valueOf(p));
                String qq = String.valueOf(q);
                totalseats.setText(qq);
            }
        });

       tickettype3DEC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (counter3 == 0) {
                    counter3 = 0;
                } else {
                    counter3--;
                    stringVal = Integer.toString(counter3);
                    tickettype3QTY.setText(stringVal);
                }
                String i = tickettype1Price.getText().toString();
                String j = tickettype2Price.getText().toString();
                String k = tickettype3Price.getText().toString();
                String m = totalprice.getText().toString();
                String n = tickettype1QTY.getText().toString();
                String o = tickettype2QTY.getText().toString();
                String p = tickettype3QTY.getText().toString();
                int iii = (valueOf(i) * valueOf(n)) + (valueOf(j) * valueOf(o)) + (valueOf(k) * valueOf(p));
                String iiii = String.valueOf(iii);
                totalprice.setText(iiii);
                int q = (valueOf(n)) + (valueOf(o)) + (valueOf(p));
                String qq = String.valueOf(q);
                totalseats.setText(qq);
            }
        });

       tickettype3INC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counter3++;
                stringVal = Integer.toString(counter3);
                tickettype3QTY.setText(stringVal);

                String i = tickettype1Price.getText().toString();
                String j = tickettype2Price.getText().toString();
                String k = tickettype3Price.getText().toString();
                String m = totalprice.getText().toString();
                String n = tickettype1QTY.getText().toString();
                String o = tickettype2QTY.getText().toString();
                String p = tickettype3QTY.getText().toString();
                int ii = (valueOf(i) * valueOf(n)) + (valueOf(j) * valueOf(o)) + (valueOf(k) * valueOf(p));
                String iii = String.valueOf(ii);
                totalprice.setText(iii);
                int q = (valueOf(n)) + (valueOf(o)) + (valueOf(p));
                String qq = String.valueOf(q);
                totalseats.setText(qq);
            }
        });
    }

    public static class BookNowScreenData {
        private Context mContext;
        public String mBsTitle;
        public String mBsDate;
        public String mBsTime;
        public String mBsTicketType1;
        public int mBsTicketType1Price;
        public String mBsTicketType2;
        public int mBsTicketType2Price;
        public String mBsTicketType3;
        public int mBsTicketType3Price;
        private BaseAction mActionObj;

        public BookNowScreenData(Context context) {
            mContext = context;
        }

        public BookNowScreenData(String titletext, BaseAction actobj) {
            mBsTitle = titletext;
            mActionObj = actobj;
        }

        public String getTitle() {
            return mBsTitle;
        }

        public String getDate() {
            return mBsDate;
        }

        public String getTime() {
            return mBsTime;
        }

        public String getTicket1() {
            return mBsTicketType1;
        }

        public int getTicket1price() {
            return mBsTicketType1Price;
        }

        public String getTicket2() {
            return mBsTicketType2;
        }

        public int getTicket2price() {
            return mBsTicketType2Price;
        }

        public String getTicket3() {
            return mBsTicketType3;
        }

        public int getTicket3price() {
            return mBsTicketType3Price;
        }

        public BaseAction getAction() {
            return mActionObj;
        }


        public Object getItem(int i) {
            return i;
        }

        public long getItemId(int i) {
            return i;
        }
    }
}
