package com.foodaclic.livraison.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

/**
 * Created by Cyrille GUIPIE on 2/16/2015.
 */
public class CustomDialog extends Dialog {

    public CustomDialog(Context context, View view) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view);
        getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
    }

}
