package com.example.lookingforthecost.screens.expenses.adapters;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.lookingforthecost.screens.controler.ColorController;
import com.example.lookingforthecost.R;
import com.example.lookingforthecost.screens.controler.TextController;
import com.example.lookingforthecost.screens.TransferActivity;
import com.example.lookingforthecost.database.AppDataBase;
import com.example.lookingforthecost.database.entity.Category;
import com.example.lookingforthecost.database.entity.Expenses;
import com.example.lookingforthecost.screens.OutputExpensesActivity;
import com.example.lookingforthecost.screens.expenses.CreateExpensesActivity;
import com.example.lookingforthecost.screens.expenses.ExpensesActivity;
import com.example.lookingforthecost.view_model.MainViewModel;
import com.example.lookingforthecost.view_model.ModelFactory;

import java.util.ArrayList;
import java.util.List;

public class AdapterListCategory extends RecyclerView.Adapter<AdapterListCategory.viewHolder> {


    private SortedList<Category> sortedList;
    private Context context;
    private ArrayList<Expenses> expenses;

    private static AppDataBase db;
    private ColorController colorController;

    public AdapterListCategory(final Context context, AppDataBase appDataBase) {
        db = appDataBase;
        this.context = context;
        colorController = new ColorController();
        expenses = new ArrayList<>();
        sortedList = new SortedList<>(Category.class, new SortedList.Callback<Category>() {
            @Override
            public void onInserted(int position, int count) {
                notifyDataSetChanged();
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyDataSetChanged();
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyDataSetChanged();
            }

            @Override
            public int compare(Category o1, Category o2) {
                int res = 0;
                if (o1.id < o2.id) {
                    res = 1;
                }
                if (o1.id > o2.id) {
                    res = -1;
                }
                return res;
            }

            @Override
            public void onChanged(int position, int count) {
                notifyDataSetChanged();
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


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {
        holder.bind(sortedList.get(position), expenses);
        colorController.setCategoryBackground(sortedList.get(position).importance, holder.nameCategoryBack, holder.layoutCategoryBack);//метод установит цвет категории,в зависимости от ее важности.
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }


    public void setItems(List<Category> category) {
        sortedList.replaceAll(category);
    }

    public void setItemsAdapterSpending(List<Expenses> arr) {
        notifyDataSetChanged();
        expenses.clear();
        expenses.addAll(arr);
    }


    class viewHolder extends RecyclerView.ViewHolder {
        private ImageView nameCategoryBack, layoutCategoryBack;
        private TextView nameCategory, sumSpending;
        private AdapterListExpenses adapterListExpenses;
        private Category category;
        private TextController textController;
        final MainViewModel mainViewModel;

        public viewHolder(@NonNull final View itemView) {
            super(itemView);

            sumSpending = itemView.findViewById(R.id.sumCategory);
            Button addSpending = itemView.findViewById(R.id.addSpending);
            RecyclerView recyclerView = itemView.findViewById(R.id.spendingCollection);
            LinearLayout layoutCategory = itemView.findViewById(R.id.layoutRv);
            Button deleteCategory = itemView.findViewById(R.id.deleteCategory);
            nameCategory = itemView.findViewById(R.id.nameCategory);
            nameCategoryBack = itemView.findViewById(R.id.nameCategoryBack);
            layoutCategoryBack = itemView.findViewById(R.id.layoutRvBack);

            mainViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext(), new ModelFactory(db)).get(MainViewModel.class);
            textController = new TextController();
            adapterListExpenses = new AdapterListExpenses();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapterListExpenses);

            deleteCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    AlertDialog.Builder confirm = new AlertDialog.Builder(itemView.getContext());
                    confirm.setTitle("Удалить категорию?");
                    confirm.setMessage("Все расходы в этой категории будут удалены.");
                    confirm.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sortedList.remove(category);
                            mainViewModel.deleteCategory(category);
                            notifyDataSetChanged();
                        }
                    });
                    confirm.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    confirm.show();
                }

            });

            layoutCategory.setOnClickListener(new View.OnClickListener() { //перейти на экран вывода расходов
                @Override
                public void onClick(View view) {
                    if (category != null) {
                        TransferActivity.startActivityWithTransferCategory(context, new OutputExpensesActivity(), category, ExpensesActivity.INTENT_TAG_NAME_CATEGORY);//запустим activity,передав категорию
                    }
                }
            });

            addSpending.setOnClickListener(new View.OnClickListener() {//добавить расходы в категорию
                @Override
                public void onClick(View view) {
                    Activity activity = (Activity) itemView.getContext();
                    activity.finish();
                    TransferActivity.startActivityWithTransferCategory(context, new CreateExpensesActivity(), category, CreateExpensesActivity.TAG_CREATE_EXPENSES);
                }
            });

        }

        void bind(Category category, ArrayList<Expenses> arr) {
            nameCategory.setText(category.nameCategory);
            sumSpending.setText(TextController.getFormatStr(category.amountSpending));
            mainViewModel.getExpensesByName(category.nameCategory).observe((LifecycleOwner) itemView.getContext(), new Observer<List<Expenses>>() {
                @Override
                public void onChanged(List<Expenses> expenses) {
                    adapterListExpenses.setItems(expenses);
                }
            });
            this.category = category;
        }
    }


}
