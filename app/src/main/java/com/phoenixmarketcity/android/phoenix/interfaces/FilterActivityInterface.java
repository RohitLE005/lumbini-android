package com.phoenixmarketcity.android.phoenix.interfaces;

import com.phoenixmarketcity.android.phoenix.activities.FilterPageActivity;

import java.util.List;

public interface FilterActivityInterface {
    void onCategorySelected(int position);
    List<FilterPageActivity.Categories> getCategories();
    List<FilterPageActivity.Categories.SubCategories> getCurrentSubCategories();
    void onSubCategorySelected(int subCategoryIndex, boolean selected);
    void clearAllSelection();
    int getDefaultSelectionIndex();
    void applySelection();
}
