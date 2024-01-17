package com.example.cookitright.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cookitright.R;
import com.example.cookitright.callback.CooksCallback;
import com.example.cookitright.databinding.CookLayBinding;
import com.example.cookitright.datamodels.AllCookData;
import com.example.cookitright.utils.TitleFilter;

import java.util.ArrayList;
import java.util.List;

public class AllCookAdapter extends RecyclerView.Adapter<AllCookAdapter.ViewHolder> implements Filterable {

    private List<AllCookData> cookList;
    private Context context;
    //getting access to database
    private CooksCallback cooksCallback;
    private List<AllCookData> filteredList;
    private TitleFilter titleFilter;

    public AllCookAdapter(List<AllCookData> cookList, Context context, CooksCallback cooksCallback) {
        this.cookList = cookList;
        this.context = context;
        this.cooksCallback = cooksCallback;
        this.filteredList = new ArrayList<>(cookList);
    }

    public void setData(List<AllCookData> dataList) {
        this.cookList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CookLayBinding binding = CookLayBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllCookData cookData = cookList.get(position);

        Glide.with(context)
                .load(cookData.getCookCoverImage())
                .placeholder(R.drawable.placeholder_)
                .into(holder.binding.ivCover);

        holder.binding.tvTitle.setText(cookData.getCookName());
        holder.binding.tvAuthor.setText(cookData.getChef());
        holder.binding.tvGenre.setText(cookData.getCookCategory());
        holder.binding.tvDescription.setText(cookData.getCookRecipe());

        if (cookData.isRead()){
            holder.binding.cbRead.setChecked(true);
        }
        if (cookData.isCurrent()){
            holder.binding.cbCurrent.setChecked(true);
        }
        if (cookData.isFinished()){
            holder.binding.cbFinish.setChecked(true);
        }


        holder.binding.ivCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cooksCallback.onCookClick(cookData);
            }
        });

        holder.binding.cbRead.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cooksCallback.onReadClick(cookData, true);
                    holder.binding.cbCurrent.setChecked(false);
                    holder.binding.cbFinish.setChecked(false);
                } else {
                    cooksCallback.onReadClick(cookData, false);
                }
            }
        });


        holder.binding.cbCurrent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cooksCallback.onCurrentlyClick(cookData, true);
                    holder.binding.cbRead.setChecked(false);
                    holder.binding.cbFinish.setChecked(false);
                } else {
                    cooksCallback.onCurrentlyClick(cookData, false);
                }
            }
        });

        holder.binding.cbFinish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cooksCallback.onFinishedClick(cookData, true);
                    holder.binding.cbRead.setChecked(false);
                    holder.binding.cbCurrent.setChecked(false);
                } else {
                    cooksCallback.onFinishedClick(cookData, false);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return cookList.size();
    }

    @Override
    public Filter getFilter() {
      if (titleFilter == null) {
            titleFilter = new TitleFilter(cookList, this, context);
        }
        return titleFilter;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CookLayBinding binding;

        public ViewHolder(CookLayBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
