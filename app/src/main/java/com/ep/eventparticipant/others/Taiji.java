package com.ep.eventparticipant.others;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class Taiji extends View {
    private Paint whitePaint=new Paint();
    private Paint blackPaint=new Paint();
    public Taiji(Context context){
        super(context);
    }
    public Taiji(Context context, AttributeSet attr){
        this(context,attr,0);

    }
    public Taiji(Context context, AttributeSet attr, int defStyleAttr){
        super(context,attr,defStyleAttr);
        initPaint();
    }
    private void initPaint(){

        whitePaint.setColor(Color.WHITE);
        whitePaint.setAntiAlias(true);

        blackPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int mWidth=canvas.getWidth();
        int mHeight=canvas.getHeight();
        canvas.translate(mWidth/2,mHeight/2);
        canvas.drawColor(Color.GRAY);
        canvas.rotate(degrees);
        int ridius=Math.min(mWidth,mHeight)/2-100;
        RectF rectF=new RectF(-ridius,-ridius,ridius,ridius);
        canvas.drawArc(rectF,90,180,true,whitePaint);
        canvas.drawArc(rectF,-90,180,true,blackPaint);
        int smallerRadius=ridius/2;
        canvas.drawCircle(0,smallerRadius,smallerRadius,blackPaint);
        canvas.drawCircle(0,-smallerRadius,smallerRadius,whitePaint);
        canvas.drawCircle(0,smallerRadius,smallerRadius/4,whitePaint);
        canvas.drawCircle(0,-smallerRadius,smallerRadius/4,blackPaint);

    }
    private float degrees=0;
    public void setDegrees(float degrees){
        this.degrees=degrees;
        invalidate();
    }

}
