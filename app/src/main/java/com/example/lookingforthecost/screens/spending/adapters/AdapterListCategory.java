package com.example.lookingforthecost.screens.spending.adapters;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ResourceCursorAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.lookingforthecost.App;
import com.example.lookingforthecost.Controler;
import com.example.lookingforthecost.Output;
import com.example.lookingforthecost.R;
import com.example.lookingforthecost.database.model.Category;
import com.example.lookingforthecost.database.model.Spending;
import com.example.lookingforthecost.screens.spending.SpendingActivity;

import java.util.ArrayList;
import java.util.List;

public class AdapterListCategory extends RecyclerView.Adapter<AdapterListCategory.viewHolder> {
    private LinearLayout layoutRv;
    private SortedList<Category> sortedList;
    private static MainViewModel mainViewModel;
    private Context context;
    private Intent intent;
    private ArrayList<Spending> spendings;
    SpendingActivity spendingActivity = new SpendingActivity();
    Controler controler;

    public AdapterListCategory(Context context, MainViewModel mainViewModel, Intent intent) {
        this.intent = intent;
        this.context = context;
        spendings = new ArrayList<Spending>();
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

        private TextView nameCategory, sumSpending;
        private Category category;
        private int sum = 0;
        final AdapterListSpending adapterListSpending;
        LinearLayout addSpending;
        RecyclerView recyclerView;
        ArrayList<Spending> arrayList;

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
            controler = new Controler();
            sumSpending = itemView.findViewById(R.id.i1);
            Button delCategory = itemView.findViewById(R.id.del);
            addSpending = itemView.findViewById(R.id.addSpending);
            recyclerView = itemView.findViewById(R.id.colectionSpending);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            adapterListSpending = new AdapterListSpending();
            recyclerView.setAdapter(adapterListSpending);
            layoutRv = itemView.findViewById(R.id.layoutRv);
            arrayList = new ArrayList<>();




            layoutRv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), Output.class);
                intent.putExtra("CATEGORY",category);
                SpendingActivity.start((Activity) itemView.getContext(),intent);
                }
            });


            addSpending.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SpendingActivity.start((Activity) itemView.getContext(), category);

                }
            });

            delCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DelCategory().execute(category);
                    SpendingActivity.reloadActivity((Activity) itemView.getContext());


                }
            });

            nameCategory = itemView.findViewById(R.id.nameCategory);
        }

        void bind(Category category, ArrayList<Spending> arr) {
            adapterListSpending.setItems(spendingActivity.ser(category, arr));
            adapterListSpending.notifyDataSetChanged();
            controler.setBackrondCategory(category,nameCategory,layoutRv);

            nameCategory.setText(category.nameCategory);
            sumSpending.setText(String.valueOf(category.amountSpending));
            this.category = category;
        }
    }


    public void setItems(List<Category> category) {
        sortedList.replaceAll(category);
    }


    public void setItemsAdapterSpending(List<Spending> arr) {
        spendings.clear();
        spendings.addAll(arr);
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.bind(sortedList.get(position), spendings);
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }
}

