package com.example.lookingforthecost.screens.expenses.adapters;


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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.lookingforthecost.App;
import com.example.lookingforthecost.controler.SortingValue;
import com.example.lookingforthecost.controler.ColorController;
import com.example.lookingforthecost.Output;
import com.example.lookingforthecost.R;
import com.example.lookingforthecost.database.model.Category;
import com.example.lookingforthecost.database.model.Expenses;
import com.example.lookingforthecost.screens.expenses.SpendingActivity;

import java.util.ArrayList;
import java.util.List;

public class AdapterListCategory extends RecyclerView.Adapter<AdapterListCategory.viewHolder> {
    private LinearLayout layoutCategory;
    private SortedList<Category> sortedList;
    private Context context;
    private ArrayList<Expenses> expenses;
    private SortingValue sortingValue = new SortingValue();
    private ColorController colorController;

    public AdapterListCategory(Context context) {
        this.context = context;
        expenses = new ArrayList<Expenses>();
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
        ArrayList<Expenses> arrayList;

        //удаление категории и всех трат в ней
        private class DelCategory extends AsyncTask<Category, Void, Void> {
            @Override
            protected Void doInBackground(Category... categories) {

                App.getInstance().getExpensesDao().delFromName(categories[0].nameCategory);
                App.getInstance().getCategoryDao().delete(categories[0]);

                return null;
            }
        }
        //

        public viewHolder(@NonNull final View itemView) {
            super(itemView);
            colorController = new ColorController();
            sumSpending = itemView.findViewById(R.id.i1);
            Button delCategory = itemView.findViewById(R.id.del);
            addSpending = itemView.findViewById(R.id.addSpending);
            recyclerView = itemView.findViewById(R.id.colectionSpending);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            adapterListSpending = new AdapterListSpending(itemView.getContext());
            recyclerView.setAdapter(adapterListSpending);
            layoutCategory = itemView.findViewById(R.id.layoutRv);
            arrayList = new ArrayList<>();




            layoutCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), Output.class);
                intent.putExtra(SpendingActivity.INTENT_TAG_NAME_CATEGORY,category);
                SpendingActivity.startOutputActivity((Activity) itemView.getContext(),category);
                }
            });


            addSpending.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SpendingActivity.startCreateCategory((Activity) itemView.getContext(), category);//запустим activity,передав категорию

                }
            });

            delCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DelCategory().execute(category);
                    SpendingActivity.reloadActivity((Activity) itemView.getContext());//перезапустим activity т.к можем получить некорректное отображение
                }
            });

            nameCategory = itemView.findViewById(R.id.nameCategory);
        }

        void bind(Category category, ArrayList<Expenses> arr) {
            adapterListSpending.setItems(sortingValue.getSortExpenses(category, arr));//вернется отсортированный массив трат который принадлежит категории
            adapterListSpending.notifyDataSetChanged();
            colorController.setCategoryBackground(category,nameCategory, layoutCategory);//покрасим в цвет,который зависит от веса(важности) категории
            nameCategory.setText(category.nameCategory);
            sumSpending.setText(String.valueOf(category.amountSpending));
            this.category = category;
        }
    }


    public void setItems(List<Category> category) {
        sortedList.replaceAll(category);
    }


    public void setItemsAdapterSpending(List<Expenses> arr) {
        expenses.clear();
        expenses.addAll(arr);
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.bind(sortedList.get(position), expenses);
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }
}

