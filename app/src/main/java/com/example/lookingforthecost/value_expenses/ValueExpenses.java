package com.example.lookingforthecost.value_expenses;

import com.example.lookingforthecost.App;
import com.example.lookingforthecost.DateToday;
import com.example.lookingforthecost.database.AppDataBase;
import com.example.lookingforthecost.database.entity.Category;
import com.example.lookingforthecost.database.entity.Expenses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class ValueExpenses extends Thread {
    private int necessarySum = 0;
    private int optionalSum = 0;
    private ArrayList<Expenses> arrCategories = new ArrayList<>();
    private HashMap<String, Integer> sumExpensesByCategory = new HashMap<>();
    public static final String SUM_NECESSARY_EXPENSES = "NECESSARY_EXPENSES";
    public static final String SUM_OPTIONAL_EXPENSES = "OPTIONAL_EXPENSES";
    @Inject
    AppDataBase dataBase;

    @Override
    public void run() {
        App.getComponent().inject(this);
        arrCategories = (ArrayList<Expenses>) dataBase.expensesDao().findExpensesByDate(DateToday.getDateToday());//получает категории созданные сегодня
        super.run();
    }

    public HashMap<String, Integer> getSumExpensesByCategory() {//получить сумму расходов по категориям
        start();
        do {
            try {
                join();
                for (int i = 0; i < arrCategories.size(); i++) {
                    if (arrCategories.get(i).importance == 0) {
                        optionalSum += arrCategories.get(i).spendMoney;
                    } else if (arrCategories.get(i).importance == 1) {
                        necessarySum += arrCategories.get(i).spendMoney;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (isAlive());
        sumExpensesByCategory.put(SUM_OPTIONAL_EXPENSES,optionalSum);
        sumExpensesByCategory.put(SUM_NECESSARY_EXPENSES,necessarySum);
        return sumExpensesByCategory;
    }

    public static int getRemainsBudget(List<Category> categories, int budget) {//получить остаток от бюджета
        int sumExpenses = 0;
        for (int i = 0; i < categories.size(); i++) {
            sumExpenses += categories.get(i).amountSpending;
        }
        return budget - sumExpenses;
    }

}

