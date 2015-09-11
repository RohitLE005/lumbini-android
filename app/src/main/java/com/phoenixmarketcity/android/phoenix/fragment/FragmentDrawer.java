package com.phoenixmarketcity.android.phoenix.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.activities.BaseActivity;
import com.phoenixmarketcity.android.phoenix.activities.Dummypages;
import com.phoenixmarketcity.android.phoenix.activities.MyaddressActivity;
import com.phoenixmarketcity.android.phoenix.activities.UserloginActivity;
import com.phoenixmarketcity.android.phoenix.adapter.NavigationDrawerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentDrawer extends Fragment {
    private static String[] mDrawerListItems = null;
    private static TypedArray mDrawerListImages = null;

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;

    private FragmentDrawerListener drawerListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDrawerListItems = getActivity().getResources().getStringArray(R.array.nav_drawer_items);
        mDrawerListImages = getActivity().getResources().obtainTypedArray(R.array.nav_drawer_images);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

       // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);

        adapter = new NavigationDrawerAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        
        //Navigation Drawer Images
        final ImageView imageFacebook=(ImageView)layout.findViewById(R.id.icon_social_facebook);
        final ImageView imageTwitter=(ImageView)layout.findViewById(R.id.icon_social_twitter);
        final ImageView imageGoogle=(ImageView)layout.findViewById(R.id.icon_social_google_plus);
        final ImageView imageInstagram=(ImageView)layout.findViewById(R.id.icon_social_instagram);
        final ImageView imageMail=(ImageView)layout.findViewById(R.id.icon_social_mail);
        final ImageView imagePhone=(ImageView)layout.findViewById(R.id.icon_social_phone);
        final Button tnc=(Button)layout.findViewById(R.id.footer_t_c);
        final Button safety=(Button)layout.findViewById(R.id.footer_safety_sec);
        final Button aboutus=(Button)layout.findViewById(R.id.footer_about_us);
        final Button help=(Button)layout.findViewById(R.id.footer_help);
        final Button contactus=(Button)layout.findViewById(R.id.footer_contact_us);
        imageFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserloginActivity.class);
                startActivity(intent);
                imageFacebook.setSelected(false);
             
            }
        });

        imageTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserloginActivity.class);
                startActivity(intent);
                imageTwitter.setSelected(false);
            }
        });

        imageGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                imageGoogle.setSelected(false);
                ((BaseActivity) getActivity()).signInToGooglePlus();
            }
        });

        imageInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserloginActivity.class);
                startActivity(intent);
                imageInstagram.setSelected(false);
            }
        });

        imageMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyaddressActivity.class);
                startActivity(intent);
                imageMail.setSelected(false);

            }
        });

        imagePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePhone.setSelected(false);
            }
        });
        tnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(),Dummypages.class);
                startActivity(i);
            }
        });
        safety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(),Dummypages.class);
                startActivity(i);
            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(),Dummypages.class);
                startActivity(i);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(),Dummypages.class);
                startActivity(i);
            }
        });

        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Dummypages.class);
                startActivity(i);
            }
        });


        /*recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override    aboutus
            public void onClick(View view, int position) {
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/
       /* //For Navigation Change items
        ImageView img=(ImageView) layout.findViewById(R.id.icon_social_phone);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDrawerListItems = getActivity().getResources().getStringArray(R.array.nav_drawer_items_login);
                mDrawerListImages = getActivity().getResources().getIntArray(R.array.nav_drawer_images_login);

                v = inflater.inflate(R.layout.fragment_navigation_drawer_login, container, true);
                recyclerView = (RecyclerView) v.findViewById(R.id.drawerList);

                adapter = new NavigationDrawerAdapter(getActivity(), getData());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            }
        });
        */
        //Profile Image fits in ImageView
        /*ImageView img=(ImageView)layout.findViewById(R.id.profile_image);
        Bitmap icon= BitmapFactory.decodeResource(getResources(), R.drawable.ic_profile);
        img.setImageBitmap(icon);*/
        return layout;
    }


    public FragmentDrawer() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    public static List<NavigationDrawerAdapter.NavDrawerItem> getData() {
        List<NavigationDrawerAdapter.NavDrawerItem> data = new ArrayList<>();


        // preparing navigation drawer items
        for (int i = 0; i < mDrawerListItems.length; i++) {
            NavigationDrawerAdapter.NavDrawerItem navItem = new
                    NavigationDrawerAdapter.NavDrawerItem(mDrawerListItems[i], mDrawerListImages.getDrawable(i));
            data.add(navItem);
        }
        return data;
    }



    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }
    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }
}