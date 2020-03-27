package com.example.lookingforthecost.screens.spending.adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.lookingforthecost.screens.spending.SpendingActivity;

import java.util.ArrayList;
import java.util.List;

public class AdapterListCategory extends RecyclerView.Adapter<AdapterListCategory.viewHolder> {

    private SortedList<Category> sortedList;
    private static MainViewModel mainViewModel;
    private Context context;
    private Intent intent;

    public AdapterListCategory(Context context, MainViewModel mainViewModel, Intent intent) {
        this.intent = intent;
        this.context = context;
        AdapterListCategory.mainViewModel = mainViewModel;

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

        private TextView nameCategory, i1;
        private Category category;
        private Button delCategory;
        private int sum = 0;
        final AdapterListSpending adapterListSpending;
        LinearLayout addSpending;
        RecyclerView recyclerView;


        //удаление категории и всех трат в ней
        private class DelCategory extends AsyncTask<Category, Void, List<Spending>> {
            @Override
            protected List<Spending> doInBackground(Category... categories) {

                App.getInstance().getSpendingDao().delFromName(categories[0].nameCategory);
                App.getInstance().getCategoryDao().delete(categories[0]);

                return App.getInstance().getSpendingDao().getAll();
            }

            @Override
            protected void onPostExecute(List<Spending> spendings) {
              /*  ArrayList<Spending> arrayList = new ArrayList<>();
                for (int i = 0; i < spendings.size(); i++) {
                    if (spendings.get(i).nameCategorySpending.equals(category.nameCategory)) {

                        arrayList.add(spendings.get(i));
                        sum += spendings.get(i).spendMoney;
                    }

                }
                ArrayList<Spending> sp = new ArrayList<>();

                adapterListSpending.setItems(sp);*
                adapterListSpending.setItems(arrayList);*/

              SpendingActivity spendingActivity = new SpendingActivity();




                super.onPostExecute(spendings);
            }
        }
        //

        public viewHolder(@NonNull final View itemView) {
            super(itemView);

            i1 = itemView.findViewById(R.id.i1);
            delCategory = itemView.findViewById(R.id.del);
            addSpending = itemView.findViewById(R.id.addSpending);
            recyclerView = itemView.findViewById(R.id.colectionSpending);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            adapterListSpending = new AdapterListSpending();
            recyclerView.setAdapter(adapterListSpending);


            mainViewModel.getSpendingLiveData().observe((LifecycleOwner) itemView.getContext(), new Observer<List<Spending>>() {
                @Override
                public void onChanged(List<Spending> spendings) {
                    ArrayList<Spending> arrayList = new ArrayList<>();

                    if (category != null) {
                        for (int i = 0; i < spendings.size(); i++) {
                            if (spendings.get(i).nameCategorySpending.equals(category.nameCategory)) {

                                arrayList.add(spendings.get(i));
                                sum += spendings.get(i).spendMoney;
                            }
                        }

                        adapterListSpending.setItems(arrayList);
                        adapterListSpending.notifyDataSetChanged();

                    }
                }
            });


            addSpending.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SpendingActivity.start((Activity) itemView.getContext(), category);
                    adapterListSpending.notifyDataSetChanged();
                }
            });


            delCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DelCategory().execute(category);
                    SpendingActivity spendingActivity = new SpendingActivity();
                    SpendingActivity.reloadActivity((Activity) itemView.getContext());
                    adapterListSpending.notifyDataSetChanged();

                }
            });


            nameCategory = itemView.findViewById(R.id.nameCategory);
        }


        void bind(Category category) {
            nameCategory.setText(category.nameCategory);
            i1.setText(String.valueOf(category.amountSpending));
            this.category = category;
        }
    }
    //




    public void setItems(List<Category> category) {
        sortedList.replaceAll(category);
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
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

