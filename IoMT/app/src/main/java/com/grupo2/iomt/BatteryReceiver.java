package com.grupo2.iomt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.BatteryManager;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.room.Room;

import com.grupo2.iomt.Activities.MenuActivity;
import com.grupo2.iomt.db.DB;
import com.grupo2.iomt.helpers.GetTablesHelper;

public class BatteryReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        final TextView percentageLabel = ((MenuActivity)context).findViewById(R.id.percentageLabel);
        final ImageView batteryImage = ((MenuActivity)context).findViewById(R.id.batteryImage);

        String action = intent.getAction();
        DB db = Room.databaseBuilder(context, DB.class, "mainDB")
                .allowMainThreadQueries()
                .build();
        String token = db.getTokenDAO().getItems().get(0).getToken_string();

        if (action != null && action.equals(Intent.ACTION_BATTERY_CHANGED)) {
            final GetTablesHelper getTablesHelper = new GetTablesHelper(token, context);
            getTablesHelper.obtenerBateria("http://amstdb.herokuapp.com/db/dispositivo/4");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    // Percentage
                    int level = getTablesHelper.getBateria();
                    //int level = 3;
                    int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                    int percentage = level *100 / scale;
                    percentageLabel.setText(percentage + "%");


                    // Image
                    Resources res = context.getResources();

                    if (percentage >= 90) {
                        batteryImage.setImageDrawable(res.getDrawable(R.drawable.b100));


                    } else if (90 > percentage && percentage >= 65) {
                        batteryImage.setImageDrawable(res.getDrawable(R.drawable.b75));

                    } else if (65 > percentage && percentage >= 40) {
                        batteryImage.setImageDrawable(res.getDrawable(R.drawable.b50));

                    } else if (40 > percentage && percentage >= 15) {
                        batteryImage.setImageDrawable(res.getDrawable(R.drawable.b25));

                    } else {
                        batteryImage.setImageDrawable(res.getDrawable(R.drawable.b0));

                    }

                }

            }, 2000);
            // Status
        }
    }
}