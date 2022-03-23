package com.radanov.movieapp10.utils;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

import androidx.annotation.NonNull;

public class CustomTypefaceSpan extends TypefaceSpan {

    private final Typeface newType;

    public CustomTypefaceSpan(String family, Typeface newType) {
        super(family);
        this.newType = newType;
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        applyCustomTypeFace(ds, newType);
    }

    @Override
    public void updateMeasureState(@NonNull TextPaint paint) {
        applyCustomTypeFace(paint, newType);
    }

    @SuppressLint("WrongConstant")
    private void applyCustomTypeFace(TextPaint ds, Typeface newType) {
        int oldStyle;
        Typeface old = ds.getTypeface();
        if (old == null)
            oldStyle = 0;
        else oldStyle = old.getStyle();

        int fake = oldStyle & ~newType.getStyle();
        if ((fake & Typeface.BOLD) != 0)
            ds.setFakeBoldText(true);
        if ((fake & Typeface.ITALIC) != 0)
            ds.setTextSkewX(-0.25f);
        ds.setTypeface(newType);
    }
}
