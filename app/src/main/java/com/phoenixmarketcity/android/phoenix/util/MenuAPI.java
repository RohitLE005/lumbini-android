package com.phoenixmarketcity.android.phoenix.util;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.phoenixmarketcity.android.phoenix.webservices.WebService;

import org.parceler.Parcel;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by neeraj.nayan on 26/08/15.
 */
public class MenuAPI {

    public static void getMenu() {
        WebService.getInstance().getMenuTypes(33, new Callback<List<MenuTypes>>() {
            @Override
            public void success(List<MenuTypes> menuTypes, Response response) {
                if (menuTypes == null || menuTypes.size() == 0) return;
                getMenuGroups(Integer.parseInt(menuTypes.get(0).mMenuTypeId, 10));
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    private static void getMenuGroups(int menuTypeId) {
        Log.d("nayan", "Menu Type Id: " + menuTypeId);

        WebService.getInstance().getMenuGroups(menuTypeId, new Callback<List<MenuGroup>>() {
            @Override
            public void success(List<MenuGroup> menuGroups, Response response) {
                if (menuGroups == null || menuGroups.size() == 0) return;
                getMenuItems(Integer.parseInt(menuGroups.get(0).mMenuGroupId, 10));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("nayan", "Failed in menu groups");
            }
        });
    }

    private static void getMenuItems(int menuGroupdId) {
        Log.d("nayan", "Menu Group Id: " + menuGroupdId);

        WebService.getInstance().getMenuItems(menuGroupdId, new Callback<List<MenuItem>>() {
            @Override
            public void success(List<MenuItem> menuItems, Response response) {
                if (menuItems == null || menuItems.size() == 0) return;
                for (MenuItem item : menuItems) {
                    Log.d("nayan", item.mMenuItemName);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("nayan", "Failed in menu items");
            }
        });
    }

    @Parcel
    public static class MenuTypes {
        @SerializedName("mt_id")
        public String mMenuTypeId;
        @SerializedName("mt_name")
        public String mMenuTypeName;
        @SerializedName("mt_desc")
        public String mMenuTypeDescription;
    }

    @Parcel
    public static class MenuGroup {
        @SerializedName("mgm_id")
        public String mMenuGroupId;
        @SerializedName("menugroup_name")
        public String mMenuGroupName;
        @SerializedName("menugroup_description")
        public String mMenuGroupDescription;
    }

    public static class MenuItem {
        @SerializedName("mim_id")
        public String mMenuItemId;
        @SerializedName("menuitem_name")
        public String mMenuItemName;
        @SerializedName("menuitem_description")
        public String mMenuItemDesc;
        @SerializedName("menuitem_price")
        public String mMenuItemPrice;
        @SerializedName("menuitem_image")
        public String mMenuItemImg;
        @SerializedName("veg_nonveg")
        public String mItemVeg;
        @SerializedName("is_special")
        public String mIsSpecial;
    }
}
