package com.example.lookingforthecost.screens.expenses.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.lookingforthecost.R;

import com.example.lookingforthecost.screens.controler.TextController;
import com.example.lookingforthecost.database.entity.Expenses;

import java.util.ArrayList;
import java.util.List;

public class AdapterListExpenses extends RecyclerView.Adapter<AdapterListExpenses.viewHolder> {

    private ArrayList<Expenses> arrayList;

    public AdapterListExpenses() {
        arrayList = new ArrayList<>();
    }

    void setItems(List<Expenses> expenses) {
        arrayList.clear();
        arrayList.addAll(expenses);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterListExpenses.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expenses, parent, false);
        return new viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.bind(arrayList.get(position));
        holder.nameSpending.setText(arrayList.get(position).nameSpending);
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class viewHolder extends RecyclerView.ViewHolder {
        private TextView nameSpending, spendMoney;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            nameSpending = itemView.findViewById(R.id.nameSpending);
            spendMoney = itemView.findViewById(R.id.spendMoney);
        }

        void bind(Expenses expenses) {
            nameSpending.setText(expenses.nameSpending);
            spendMoney.setText(TextController.getFormatStr(expenses.spendMoney));
        }
    }


}
