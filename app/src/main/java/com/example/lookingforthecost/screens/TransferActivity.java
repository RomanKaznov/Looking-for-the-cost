package com.example.lookingforthecost.screens;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.lookingforthecost.database.entity.Category;
import com.example.lookingforthecost.database.entity.FinPlan;

public class TransferActivity {

    public static void startActivityWithTransferCategory(Context context, Activity activity2, Category category, String TAG_INTENT) {
        Intent intent = new Intent(context, activity2.getClass());
        if (category != null)
            intent.putExtra(TAG_INTENT, category);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startActivityWithTransferPlan(Context context, Activity activity2, FinPlan finPlan, String TAG_INTENT) {
        Intent intent = new Intent(context, activity2.getClass());
        if (finPlan != null)
            intent.putExtra(TAG_INTENT, finPlan);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


}
