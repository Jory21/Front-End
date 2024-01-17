package com.example.cookitright.utils;

import android.content.Context;
import android.widget.Filter;

import com.example.cookitright.adapters.AllCookAdapter;
import com.example.cookitright.datamodels.AllCookData;

import java.util.ArrayList;
import java.util.List;

public class TitleFilter extends Filter {
    private List<AllCookData> originalList;
    private List<AllCookData> filteredList;
    private AllCookAdapter adapter;
    private Context context;

    public TitleFilter(List<AllCookData> originalList, AllCookAdapter adapter, Context context) {
        this.originalList = originalList;
        this.filteredList = new ArrayList<>();
        this.adapter = adapter;
        this.context = context;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        filteredList.clear();
        final FilterResults results = new FilterResults();

        if (constraint.length() == 0) {
            filteredList.addAll(originalList);
        } else {
            final String filterPattern = constraint.toString().toLowerCase().trim();

            for (AllCookData item : originalList) {
                if (Constant.getData(context) == 1){
                    if (item.getCookName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }

                if (Constant.getData(context) == 2){
                    if (item.getChef().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }

                if (Constant.getData(context) == 3){
                    if (item.getCookCategory().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
        }

        results.values = filteredList;
        results.count = filteredList.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.setData(filteredList);
        adapter.notifyDataSetChanged();
    }
}
