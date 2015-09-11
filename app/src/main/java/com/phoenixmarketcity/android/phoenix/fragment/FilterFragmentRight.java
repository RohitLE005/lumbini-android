package com.phoenixmarketcity.android.phoenix.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.activities.FilterPageActivity;
import com.phoenixmarketcity.android.phoenix.interfaces.FilterActivityInterface;

import java.util.List;

public class FilterFragmentRight extends Fragment {
    public final static String TAG = FilterFragmentRight.class.getName();

    private FilterSubCategoryAdapter mListAdapter;
    private ListView mListView;
    private FilterActivityInterface mParentActivity;
    private Button mApplyButtonView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mParentActivity = (FilterActivityInterface) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_fragment_right, container, false);

        mListAdapter = new FilterSubCategoryAdapter(getActivity(), mParentActivity.getCurrentSubCategories());
        mListView = (ListView) view.findViewById(R.id.rightListViewId);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mParentActivity.onSubCategorySelected(position, !view.isSelected());
            }
        });

        mApplyButtonView = (Button) view.findViewById(R.id.buttonApply);
        mApplyButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mParentActivity.applySelection();
            }
        });
        return view;
    }

    public void refreshViews() {
        mListAdapter.setData(mParentActivity.getCurrentSubCategories());
    }

    public static class FilterSubCategoryAdapter extends BaseAdapter {
        private Context mContext;
        private List<FilterPageActivity.Categories.SubCategories> mSubCategories;

        FilterSubCategoryAdapter(Context context, List<FilterPageActivity.Categories.SubCategories> subCategories) {
            this.mContext = context;
            this.mSubCategories = subCategories;
        }

        @Override
        public int getCount() {
            return mSubCategories.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return mSubCategories.indexOf(getItem(position));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) mContext
                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.filter_list_items_right, null);
            }

            FilterPageActivity.Categories.SubCategories item = mSubCategories.get(position);

            ImageView imgIconView = (ImageView) convertView.findViewById(R.id.selectIconImageView);
            imgIconView.setSelected(item.mIsSelected);

            convertView.setSelected(item.mIsSelected);
            TextView textView = (TextView) convertView.findViewById(R.id.subCategoryTitleTextView);
            textView.setText(item.mSubCategoryName);

            textView = (TextView) convertView.findViewById(R.id.subCategoryItemCountView);
            textView.setText(Integer.toString(item.mResultCount));

            return convertView;
        }

        public void setData(List<FilterPageActivity.Categories.SubCategories> data) {
            mSubCategories = data;
            notifyDataSetChanged();
        }
    }
}