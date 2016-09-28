package org.kukish.android.simpleshoppinglist.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import org.kukish.android.simpleshoppinglist.R;

/**
 * Created by Alejandro Awesome on 9/26/2016.
 */

public class ItemView extends TextView {

    private Paint marginPaint;
    private Paint linePaint;
    private int paperColor;
    private float margin;

    public ItemView(Context context) {
        super(context);
        init();
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Resources resources = getResources();

        //brush
        marginPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        marginPaint.setColor(ContextCompat.getColor(getContext(), R.color.notepad_margin));
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(ContextCompat.getColor(getContext(), R.color.notepad_lines));

        //background
        paperColor = resources.getColor(R.color.notepad_paper);
        margin = resources.getDimension(R.dimen.notepad_margin);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(paperColor);
        canvas.drawLine(0, getMeasuredHeight(), getMeasuredWidth(),
                getMeasuredHeight(), linePaint);

        canvas.drawLine(margin, 0, margin, getMeasuredHeight(), marginPaint);

        canvas.save();
        canvas.translate(margin, 0);
        super.onDraw(canvas);
        canvas.restore();
    }
}
