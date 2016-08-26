package com.yushilei.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author by  yushilei.
 * @time 2016/8/26 -10:25.
 * @Desc
 */
public class MyCalendarView extends View {

    final int ROW = 6;//6行
    final int COLUMN = 7;//7列
    int mWidth;//View 宽
    int mHeight;//View 高

    TextPaint mTextPaint;
    Paint circlePaint;

    Paint linePaint;

    List<Day> mDays = new ArrayList<>();//该月拥有的天数

    Point[] arrPoint_42 = new Point[42];//记录42个点的坐标

    int mSubWidth;//每个Day所对应的半径
    int mSubHeight;
    private int mMonthDays;//该月对应的天数
    private int mDayOfWeek;

    float textSize = 50;
    private Paint.FontMetricsInt mMetricsInt;


    public MyCalendarView(Context context) {
        this(context, null);
    }

    public MyCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    String TAG = "MyCalendar";

    private void init(Context context, AttributeSet attrs) {

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTypeface(Typeface.SERIF);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mMetricsInt = mTextPaint.getFontMetricsInt();


        circlePaint = new Paint();
        circlePaint.setColor(Color.LTGRAY);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);


        linePaint = new Paint();
        linePaint.setColor(Color.GREEN);
        linePaint.setAntiAlias(true);

        for (int i = 0; i < arrPoint_42.length; i++) {
            arrPoint_42[i] = new Point();
        }

        Calendar calendar = Calendar.getInstance();
        int mCurrYear = calendar.get(Calendar.YEAR);
        int mCurrMonth = calendar.get(Calendar.MONTH);
        int mCurrDay = calendar.get(Calendar.DATE);

        mMonthDays = DateUtil.getMonthDays(mCurrYear, mCurrMonth);
        //23456 71
        mDayOfWeek = DateUtil.getDayOfWeek(mCurrYear, mCurrMonth);
        Log.d(TAG, "年：" + mCurrYear + ";月：" + mCurrMonth + ";日：" + mCurrDay +
                "本月天数：" + mMonthDays + ";第一天为星期：" + mDayOfWeek);

        for (int i = 0; i < mMonthDays; i++) {
            Day day = new Day(i + 1);
            if (i == mCurrDay - 1) {
                day.setIsTouch(true);
            }
            mDays.add(day);
        }
    }

    public void setDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        int mCurrYear = calendar.get(Calendar.YEAR);
        int mCurrMonth = calendar.get(Calendar.MONTH);
        int mCurrDay = calendar.get(Calendar.DATE);

        mMonthDays = DateUtil.getMonthDays(mCurrYear, mCurrMonth - 1);
        //23456 71
        mDayOfWeek = DateUtil.getDayOfWeek(mCurrYear, mCurrMonth - 1);
        Log.d(TAG, "年：" + mCurrYear + ";月：" + mCurrMonth + ";日：" + mCurrDay +
                "本月天数：" + mMonthDays + ";第一天为星期：" + mDayOfWeek);

        mDays = new ArrayList<>();

        for (int i = 0; i < mMonthDays; i++) {
            Day mDay = new Day(i + 1);
            if (i == mCurrDay - 1) {
                mDay.setIsTouch(true);
            }
            mDays.add(mDay);
        }
        int distance;
        if (mDayOfWeek == 1) {
            distance = 6;
        } else {
            distance = mDayOfWeek - 2;
        }
        for (int m = 0; m < mDays.size(); m++) {
            mDays.get(m).setPoint(arrPoint_42[m + distance]);
        }
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = (int) (size * ((float) ROW / (float) COLUMN));
        int newHeightMs = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, newHeightMs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        mSubWidth = w / COLUMN;
        mSubHeight = h / ROW;
        //23456 71  计算每个点的中心坐标
        int row = 0;

        for (int k = 0; k < 42; k++) {
            arrPoint_42[k].setX(mSubWidth / 2 + row * mSubWidth);
            arrPoint_42[k].setY(mSubHeight / 2 + (k / COLUMN) * mSubHeight);
            row++;
            if (row > 6) {
                row = 0;
            }
        }
        int distance = 0;
        if (mDayOfWeek == 1) {
            distance = 6;
        } else {
            distance = mDayOfWeek - 2;
        }
        for (int m = 0; m < mDays.size(); m++) {
            mDays.get(m).setPoint(arrPoint_42[m + distance]);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                clickPoint(touchX, touchY);
                break;
        }
        invalidate();
        return true;
    }


    private void clickPoint(float touchX, float touchY) {
        int index = getClickPoint((int) touchX, (int) touchY);
        if (index != -1) {
            Day day = mDays.get(index);
            day.setIsTouch(!day.isTouch);
            postInvalidate();
        }

    }

    private int getClickPoint(int touchX, int touchY) {
        for (int i = 0; i < mDays.size(); i++) {
            boolean contains = mDays.get(i).getRect().contains(touchX, touchY);
            if (contains) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        for (int i = 0; i < mDays.size(); i++) {
            Day day = mDays.get(i);
            if (day.isTouch) {
                int mR = Math.min(mSubHeight, mSubWidth) / 2;

                canvas.drawCircle((float) (day.getPoint().getX()), (float) (day.getPoint().getY()), mR - 5, circlePaint);
                mTextPaint.setColor(Color.RED);
            } else {
                mTextPaint.setColor(Color.BLUE);
            }
            int x = day.getPoint().getX();
            int y = day.getPoint().getY();
            int ascent = -mMetricsInt.ascent;
            int descent = mMetricsInt.descent;
            int distance = (ascent + descent) / 2 - descent;

            canvas.drawText(day.getDay() + "", x, y + distance, mTextPaint);
        }
//        for (int i = 0; i < ROW; i++) {
//            canvas.drawLine(0, (i + 1) * mSubHeight, mWidth, (i + 1) * mSubHeight, linePaint);
//        }
//        for (int i = 0; i < COLUMN; i++) {
//            canvas.drawLine((i + 1) * mSubWidth, 0, (i + 1) * mSubWidth, mHeight, linePaint);
//        }
    }

    public class Day {
        Rect mRect;
        Point mPoint;
        int day;
        boolean isTouch;

        public Day(int day) {
            this.day = day;
        }

        public Rect getRect() {
            return mRect;
        }

        public void setRect(Rect rect) {
            mRect = rect;
        }

        public Point getPoint() {
            return mPoint;
        }

        public void setPoint(Point point) {
            mRect = new Rect();
            mRect.set(point.getX() - mSubWidth / 2, point.getY() - mSubHeight / 2,
                    point.getX() + mSubWidth / 2, point.getY() + mSubHeight / 2);
            mPoint = point;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public boolean isTouch() {
            return isTouch;
        }

        public void setIsTouch(boolean isTouch) {
            this.isTouch = isTouch;
        }
    }

    public class Point {
        int x;
        int y;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

}
