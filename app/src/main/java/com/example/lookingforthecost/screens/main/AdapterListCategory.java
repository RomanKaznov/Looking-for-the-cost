package com.example.lookingforthecost.screens.main;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.lookingforthecost.App;
import com.example.lookingforthecost.R;
import com.example.lookingforthecost.database.model.Category;
import com.example.lookingforthecost.database.model.Spending;

import java.util.ArrayList;
import java.util.List;

public class AdapterListCategory extends RecyclerView.Adapter<AdapterListCategory.viewHolder> {

    private SortedList<Category> sortedList;
    private static MainViewModel mainViewModel;
    private Context context;
    static boolean checkClick;
    Intent intent;

    public AdapterListCategory(Context context, MainViewModel mainViewModel, Intent intent) {
        this.intent = intent;
        this.context = context;
        this.mainViewModel = mainViewModel;

        sortedList = new SortedList<Category>(Category.class, new SortedList.Callback<Category>() {
            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);

            }


            @Override
            public void onRemoved(int position, int count) {
                notifyDataSetChanged();
                notifyItemRangeInserted(position, count);

            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public int compare(Category o1, Category o2) {
                int res = 0;
                if (o1.id < o2.id) {
                    res = -1;
                }
                if (o1.id > o2.id) {
                    res = 1;
                }

                if (o1.nameCategory != o2.nameCategory) {
                    res = 1;
                }


                return res;
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public boolean areContentsTheSame(Category oldItem, Category newItem) {

                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Category item1, Category item2) {
                return item1.id == item2.id;
            }


        });
    }


    class viewHolder extends RecyclerView.ViewHolder {

        private TextView nameCategory;
        private Category category;
        private Button button;
        final AdapterListSpending adapterListSpending;
        LinearLayout addSpending;
        RecyclerView recyclerView;


        //удаление категории и всех трат в ней
        private class DelCategory extends AsyncTask<Category, Void, Void> {
            @Override
            protected Void doInBackground(Category... categories) {

                App.getInstance().getSpendingDao().delFromName(categories[0].nameCategory);
                App.getInstance().getCategoryDao().delete(categories[0]);
                return null;
            }
        }
        //

        public viewHolder(@NonNull final View itemView) {
            super(itemView);


            button = itemView.findViewById(R.id.del);
            addSpending = itemView.findViewById(R.id.addSpending);
            recyclerView = itemView.findViewById(R.id.colectionSpending);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            adapterListSpending = new AdapterListSpending();
            recyclerView.setAdapter(adapterListSpending);


            mainViewModel.getSpendingLiveData().observe((LifecycleOwner) itemView.getContext(), new Observer<List<Spending>>() {
                @Override
                public void onChanged(List<Spending> spendings) {

                    final ArrayList<Spending> arrayList = new ArrayList<>();
                    if (category != null) {
                        for (int i = 0; i < spendings.size(); i++) {
                            if (spendings.get(i).nameCategorySpending.equals(category.nameCategory)) {
                                arrayList.add(spendings.get(i));
                            }
                        }

                        adapterListSpending.setItems(arrayList);
                        arrayList.removeAll(spendings);
                        adapterListSpending.notifyDataSetChanged();
                    }
                }
            });


            addSpending.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.start((Activity) itemView.getContext(), category.nameCategory);
                }
            });


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapterListSpending.notifyDataSetChanged();
                    new DelCategory().execute(category);
                    adapterListSpending.notifyDataSetChanged();
                    Log.i("S", String.valueOf(checkClick));
                }
            });


            nameCategory = itemView.findViewById(R.id.nameCategory);
        }


        void bind(Category category) {
            nameCategory.setText(category.nameCategory);
            this.category = category;
        }
    }
    //


    void setItems(List<Category> category) {
        sortedList.replaceAll(category);
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.bind(sortedList.get(position));
        holder.nameCategory.setText(sortedList.get(position).nameCategory);
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }
}

