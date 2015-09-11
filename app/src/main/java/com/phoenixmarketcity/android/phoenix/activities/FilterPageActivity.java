package com.phoenixmarketcity.android.phoenix.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;
import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.fragment.FilterFragmentLeft;
import com.phoenixmarketcity.android.phoenix.fragment.FilterFragmentRight;
import com.phoenixmarketcity.android.phoenix.interfaces.FilterActivityInterface;
import com.phoenixmarketcity.android.phoenix.webservices.WebService;

import org.parceler.Parcel;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FilterPageActivity extends BaseActivity implements FilterActivityInterface {

    private List<Categories> mCategories;
    private static int mDefaultCategorySelectionIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_activity_main);

        WebService.getInstance().getCategories(new Callback<List<Categories>>() {
            @Override
            public void success(List<Categories> categories, Response response) {
                mCategories = categories;
                final FragmentManager fm = getFragmentManager();
                final FragmentTransaction transaction = fm.beginTransaction();

                transaction.add(R.id.filter_page_left_frame, new FilterFragmentLeft(), FilterFragmentLeft.TAG);
                transaction.add(R.id.filter_page_right_frame, new FilterFragmentRight(), FilterFragmentRight.TAG);
                transaction.commit();

                ProgressBar progressBar = (ProgressBar) findViewById(R.id.filterpage_progressbar);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void failure(RetrofitError error) {
                ProgressBar progressBar = (ProgressBar) findViewById(R.id.filterpage_progressbar);
                progressBar.setVisibility(View.GONE);

                TextView noContentTextView = (TextView) findViewById(R.id.no_content_text_view);
                noContentTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onCategorySelected(int position) {
        mDefaultCategorySelectionIndex = position;
        refreshViews();
    }

    @Override
    public List<FilterPageActivity.Categories> getCategories() {
        return mCategories;
    }

    @Override
    public List<FilterPageActivity.Categories.SubCategories> getCurrentSubCategories() {
        return mCategories.get(mDefaultCategorySelectionIndex).mSubCategories;
    }

    @Override
    public void onSubCategorySelected(int subCategoryIndex, boolean selected) {
        mCategories.get(mDefaultCategorySelectionIndex).mSubCategories.get(subCategoryIndex).mIsSelected = selected;
        refreshViews();
    }

    @Override
    public void clearAllSelection() {
        for (int i=0; i<mCategories.size(); i++) {
            List<Categories.SubCategories> subCategories = mCategories.get(i).mSubCategories;
            for (int j=0; j<subCategories.size(); j++) {
                subCategories.get(j).mIsSelected = false;
            }
        }
        refreshViews();
    }

    @Override
    public int getDefaultSelectionIndex() {
        return mDefaultCategorySelectionIndex;
    }

    @Override
    public void applySelection() {

    }

    // Redraw Views
    private void refreshViews() {
        FragmentManager fm = getFragmentManager();
        FilterFragmentRight subCategoryFragment = (FilterFragmentRight) fm.findFragmentByTag(FilterFragmentRight.TAG);
        subCategoryFragment.refreshViews();
    }

    // Data class for Filter Page
    @Parcel
    public static class Categories {
        @SerializedName("category_name")
        public String mCategoryName;
        @SerializedName("subcategory_name")
        public List<SubCategories> mSubCategories;

        @Parcel
        public static class SubCategories {
            @SerializedName("name")
            public String mSubCategoryName;
            @SerializedName("isselected")
            public boolean mIsSelected;
            @SerializedName("itemcount")
            public int mResultCount;
        }
    }
}
