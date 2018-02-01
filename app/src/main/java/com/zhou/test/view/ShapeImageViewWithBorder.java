package com.zhou.test.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.zhou.test.R;

/**
 * @author zqm
 * @since 2017/12/1
 */

public class ShapeImageViewWithBorder extends android.support.v7.widget.AppCompatImageView {
    private int mShape;

    public static final int CIRCLE = 1;
    public static final int RECTANGLE = 2;
    public static final int ARC = 3;
    private int borderColor;
    private int borderWidth;
    private int roundRadius;
    private int leftTopRadius;
    private int rightTopRadius;
    private int rightBottomRadius;
    private int leftBottomRadius;
    private int borderImage;

    private Paint paint;

    public ShapeImageViewWithBorder(Context context) {
        super(context);
    }

    public ShapeImageViewWithBorder(Context context, AttributeSet attrs) {
        super(context, attrs);
        sharedConstructor(context, attrs);
    }

    public ShapeImageViewWithBorder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        sharedConstructor(context, attrs);
    }

    private void sharedConstructor(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ShapeImageViewWithBorder);
        mShape = a.getInt(R.styleable.ShapeImageViewWithBorder_shape, RECTANGLE);
        borderColor = a.getColor(R.styleable.ShapeImageViewWithBorder_borderColor, Color.RED);
        borderWidth = a.getDimensionPixelSize(R.styleable.ShapeImageViewWithBorder_imageBorderWidth, 0);
        roundRadius = a.getDimensionPixelSize(R.styleable.ShapeImageViewWithBorder_roundRadius, 0);
        leftTopRadius = a.getDimensionPixelSize(R.styleable.ShapeImageViewWithBorder_leftTopRadius, roundRadius);
        rightTopRadius = a.getDimensionPixelSize(R.styleable.ShapeImageViewWithBorder_rightTopRadius, roundRadius);
        rightBottomRadius = a.getDimensionPixelSize(R.styleable.ShapeImageViewWithBorder_rightBottomRadius, roundRadius);
        leftBottomRadius = a.getDimensionPixelSize(R.styleable.ShapeImageViewWithBorder_leftBottomRadius, roundRadius);
        borderImage = a.getResourceId(R.styleable.ShapeImageViewWithBorder_borderImage, 0);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int saveCount = canvas.getSaveCount();
        canvas.save();
        super.onDraw(canvas);

        if (paint == null) {
            paint = new Paint();
            paint.setFilterBitmap(false);
        }
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        Bitmap circleBitmap = drawShape(width, height);
        canvas.drawBitmap(circleBitmap, 0, 0, paint); //形状
        paint.setXfermode(null);

        drawBorder(canvas); //边框
        canvas.restoreToCount(saveCount);
    }

    private Bitmap drawShape(int w, int h) {
        RectF rectF = new RectF();
        Paint localPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);

        switch (mShape) {
            case CIRCLE:
                float min = Math.min(w, h);
                canvas.drawCircle(w / 2, h / 2, (min - borderWidth) / 2, localPaint);
                break;
            case RECTANGLE:
                Path path = new Path();
                rectF.left = borderWidth / 2;
                rectF.top = borderWidth / 2;
                rectF.right = w - borderWidth / 2;
                rectF.bottom = h - borderWidth / 2;
                float[] rad = {leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius};
                path.addRoundRect(rectF, rad, Path.Direction.CW);
                canvas.drawPath(path, localPaint);
                break;
            case ARC:
                rectF.left = borderWidth / 2;
                rectF.top = borderWidth / 2;
                rectF.right = w - borderWidth / 2;
                rectF.bottom = h - borderWidth / 2;
                canvas.drawArc(rectF, 0, 360, true, localPaint);
                break;
        }
        return bm;
    }

    private void drawBorder(Canvas canvas) {
        if (borderWidth == 0 || borderColor == Color.TRANSPARENT) {
            return;
        }
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(borderWidth);
        mPaint.setColor(borderColor);
        switch (mShape) {
            case CIRCLE:
                float radius = (Math.min(getWidth(), getHeight()) - borderWidth) / 2;
                float cx = getWidth() / 2f;
                float cy = getHeight() / 2f;
                canvas.drawCircle(cx, cy, radius, mPaint);
                break;
            case RECTANGLE:
                Path path = new Path();
                RectF rectF = new RectF();
                rectF.left = borderWidth / 2;
                rectF.top = borderWidth / 2;
                rectF.right = getWidth() - borderWidth / 2;
                rectF.bottom = getHeight() - borderWidth / 2;

                float[] rad = {leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius};
                path.addRoundRect(rectF, rad, Path.Direction.CW);
                canvas.drawPath(path, mPaint);
                return;
            case ARC:
                RectF rectF2 = new RectF();
                rectF2.left = borderWidth / 2;
                rectF2.top = borderWidth / 2;
                rectF2.right = getWidth() - borderWidth / 2;
                rectF2.bottom = getHeight() - borderWidth / 2;
                canvas.drawArc(rectF2, 0, 360, false, mPaint);
                break;
        }
    }
}
