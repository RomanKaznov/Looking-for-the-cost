package com.example.lookingforthecost.screens.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;


import com.example.lookingforthecost.R;

import com.example.lookingforthecost.database.model.Spending;

import java.util.List;

public class AdapterListSpending extends RecyclerView.Adapter<AdapterListSpending.viewHolder> {
    private SortedList<Spending> sortedList;


    public AdapterListSpending() {


        sortedList = new SortedList<Spending>(Spending.class, new SortedList.Callback<Spending>() {


            @Override
            public void onInserted(int position, int count) {


                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {


                notifyItemRangeInserted(position, count);
                notifyDataSetChanged();
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {


                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public int compare(Spending o1, Spending o2) {


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
            public boolean areContentsTheSame(Spending oldItem, Spending newItem) {


                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Spending item1, Spending item2) {


                return item1.id == item2.id;
            }
        });
    }





    class viewHolder extends RecyclerView.ViewHolder {

        private TextView nameSpending, spendMoney;
        private Spending spending;

        public viewHolder(@NonNull View itemView) {
            super(itemView);


            nameSpending = itemView.findViewById(R.id.nameSpending);
            spendMoney = itemView.findViewById(R.id.spendMoney);


        }


        void bind(Spending spending) {
            nameSpending.setText(spending.nameSpending);
            spendMoney.setText(String.valueOf(spending.nameCategorySpending));
            this.spending = spending;
        }

    }



    void setItems(List<Spending> spendings) {
        sortedList.replaceAll(spendings);
    }


    @NonNull
    @Override
    public AdapterListSpending.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spending, parent, false);
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
