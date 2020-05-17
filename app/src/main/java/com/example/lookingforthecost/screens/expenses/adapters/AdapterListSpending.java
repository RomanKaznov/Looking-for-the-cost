package com.example.lookingforthecost.screens.expenses.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;


import com.example.lookingforthecost.R;

import com.example.lookingforthecost.database.model.Expenses;

import java.util.List;

public class AdapterListSpending extends RecyclerView.Adapter<AdapterListSpending.viewHolder> {
    private SortedList<Expenses> sortedList;
    private Context context;

    public AdapterListSpending(Context context) {
        this.context = context;
        sortedList = new SortedList<Expenses>(Expenses.class, new SortedList.Callback<Expenses>() {
            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
                notifyDataSetChanged();
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
            public int compare(Expenses o1, Expenses o2) {
                int res = 0;
                if (o1.id < o2.id) {
                    res = -1;
                }
                if (o1.id > o2.id) {
                    res = 1;
                }
                return res;
            }


            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public boolean areContentsTheSame(Expenses oldItem, Expenses newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Expenses item1, Expenses item2) {
                return item1.id == item2.id;
            }
        });
    }


    class viewHolder extends RecyclerView.ViewHolder {

        private TextView nameSpending, spendMoney;
        private Expenses expenses;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            nameSpending = itemView.findViewById(R.id.nameSpending);
            spendMoney = itemView.findViewById(R.id.spendMoney);
        }

        void bind(Expenses expenses) {
            nameSpending.setText(expenses.nameSpending);
            spendMoney.setText(String.valueOf(expenses.spendMoney) + " " + context.getString(R.string.currency));
            this.expenses = expenses;
        }

    }


    void setItems(List<Expenses> expenses) {
        sortedList.replaceAll(expenses);
    }


    @NonNull
    @Override
    public AdapterListSpending.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spending, parent, false);
        return new AdapterListSpending.viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.bind(sortedList.get(position));
        holder.nameSpending.setText(sortedList.get(position).nameSpending);
    }


    @Override
    public int getItemCount() {
        return sortedList.size();
    }

}
