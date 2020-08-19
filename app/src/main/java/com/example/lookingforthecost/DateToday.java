package com.example.lookingforthecost;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateToday {

    public static String getDateToday() {
        java.util.Date currentDate = new java.util.Date();
        // Форматирование времени как "день.месяц.год"
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return dateFormat.format(currentDate);
    }

}
