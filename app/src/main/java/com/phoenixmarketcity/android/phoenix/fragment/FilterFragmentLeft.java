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
import android.widget.ListView;
import android.widget.TextView;

import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.activities.FilterPageActivity;
import com.phoenixmarketcity.android.phoenix.interfaces.FilterActivityInterface;

import java.util.List;

public class FilterFragmentLeft extends Fragment {
    public final static String TAG = FilterFragmentLeft.class.getName();

    private FilterActivityInterface mParentActivity;
    private ListView mListView;
    private Button mClearAllButtonView;
    private FilterCategoryAdapter mListAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mParentActivity = (FilterActivityInterface) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_fragment_left, container, false);

        mListAdapter = new FilterCategoryAdapter(getActivity(), mParentActivity.getCategories());
        mListView = (ListView) view.findViewById(R.id.leftListViewId);
        mListView.setAdapter(mListAdapter);
        mListView.setSelection(mParentActivity.getDefaultSelectionIndex());
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mParentActivity.onCategorySelected(position);
            }
        });

        mClearAllButtonView = (Button) view.findViewById(R.id.buttonClearAll);
        mClearAllButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mParentActivity.clearAllSelection();
            }
        });
        return view;
    }

    public static class FilterCategoryAdapter extends BaseAdapter {
        private Context mContext;
        private List<FilterPageActivity.Categories> mCategories;

        FilterCategoryAdapter(Context context, List<FilterPageActivity.Categories> categories) {
            this.mContext = context;
            this.mCategories = categories;
        }

        @Override
        public int getCount() {
            return mCategories.size();
        }

        @Override
        public Object getItem(int position) {
            return mCategories.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mCategories.indexOf(getItem(position));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) mContext
                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.filter_list_items_left, null);
            }

            TextView txtTitle = (TextView) convertView.findViewById(R.id.categoryTitleTextViewId);
            txtTitle.setText(mCategories.get(position).mCategoryName);
            return convertView;
        }
    }
}
