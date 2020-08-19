package com.example.lookingforthecost.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.lookingforthecost.DateToday;
import com.example.lookingforthecost.R;
import com.example.lookingforthecost.screens.MainActivity;
import com.example.lookingforthecost.screens.controler.TextController;
import com.example.lookingforthecost.value_expenses.ValueExpenses;

import java.util.HashMap;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String sumNecessaryExpenses;
        String sumOptionalExpenses;
        HashMap<String, Integer> sumExpensesByCategory;
        ValueExpenses valueExpenses = new ValueExpenses();
        sumExpensesByCategory = valueExpenses.getSumExpensesByCategory();//вернет массив из двух элементов,в каждом из которых сумма всех трат по категориям

        if (sumExpensesByCategory.get(ValueExpenses.SUM_NECESSARY_EXPENSES) != null) {
            sumNecessaryExpenses = TextController.getFormatStr("Необходимые расходы:", sumExpensesByCategory.get(ValueExpenses.SUM_NECESSARY_EXPENSES));//получение суммы обязательных расходов
        } else {
            sumNecessaryExpenses = "На сегодня расходов нет";
        }

        if (sumExpensesByCategory.get(ValueExpenses.SUM_OPTIONAL_EXPENSES) != null) {
            sumOptionalExpenses = TextController.getFormatStr("Опциональные траты:", sumExpensesByCategory.get(ValueExpenses.SUM_OPTIONAL_EXPENSES));//получение суммы опциональных расходов
        } else {
            sumOptionalExpenses = "На сегодня расходов нет";
        }

        Intent intentMainActivity = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 100, intentMainActivity, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setContentIntent(pendingIntent1)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Ваши расходы на " + DateToday.getDateToday() + " :")
                        .setStyle(new NotificationCompat.InboxStyle()
                                .addLine(sumNecessaryExpenses)
                                .addLine(sumOptionalExpenses)
                        )
                        .setAutoCancel(true);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence channelName = "Looking for the cost";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel notificationChannel = new NotificationChannel("1000", channelName, importance);
            notificationChannel.enableLights(true);
            nm.createNotificationChannel(notificationChannel);
            builder.setChannelId(notificationChannel.getId());
        }
        nm.notify(100, builder.build());
    }
}
