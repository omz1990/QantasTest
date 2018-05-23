package com.omar.qantastest.Common.utils;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by omz on 24/5/18
 */
public class AppClickableSpan extends ClickableSpan {

    // This util class is used to set span inside a TextView
    private final int color;

    public AppClickableSpan(int color) {
        this.color = color;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(color);
        ds.setFakeBoldText(false);
        ds.setUnderlineText(true);
    }

}
