package com.yushilei.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author by  yushilei.
 * @time 2016/8/26 -15:06.
 * @Desc
 */
public class BaseLineView extends View {
    TextPaint mPaint = new TextPaint();

    TextPaint mDescPaint = new TextPaint();
    Paint linePaint = new Paint();

    String text = "Abgj 汉字，。12";

    private Paint.FontMetricsInt mFontMetricsInt;

    public BaseLineView(Context context) {
        this(context, null);
    }

    public BaseLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(150);
        mFontMetricsInt = mPaint.getFontMetricsInt();

        mDescPaint.setAntiAlias(true);
        mDescPaint.setTextAlign(Paint.Align.CENTER);
        mDescPaint.setTextSize(15);
        mDescPaint.setColor(Color.BLUE);

        linePaint.setAntiAlias(true);
    }

    int mWidth;
    int mHeight;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        int x = mWidth / 2;
        int baseLineY = mHeight / 2;

        canvas.drawText(text, x, baseLineY, mPaint);

        int ascent = -mFontMetricsInt.ascent;
        int descent = mFontMetricsInt.descent;
        int top = Math.abs(mFontMetricsInt.top);
        int bottom = Math.abs(mFontMetricsInt.bottom);

        linePaint.setColor(Color.RED);
        //BaseLine
        canvas.drawLine(0, baseLineY, mWidth, baseLineY, linePaint);
        //ascent
        linePaint.setColor(Color.BLUE);
        canvas.drawLine(0, baseLineY - ascent, mWidth, baseLineY - ascent, linePaint);
        //descent
        linePaint.setColor(Color.GREEN);
        canvas.drawLine(0, baseLineY + descent, mWidth, baseLineY + descent, linePaint);
        //top
        linePaint.setColor(Color.BLACK);
        canvas.drawLine(0, baseLineY - top, mWidth, baseLineY - top, linePaint);

        //bottom
        linePaint.setColor(Color.BLUE);
        canvas.drawLine(0, baseLineY + bottom, mWidth, baseLineY + bottom, linePaint);

    }
}
