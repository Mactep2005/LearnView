package com.aplana.ivan.p0041basicviews;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by 1 on 19.04.2017.
 */

public class UpdateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("chat",
                "+ ChatActivity - ресивер получил сообщение - обновим ListView");
        create_lv();
    }
}
