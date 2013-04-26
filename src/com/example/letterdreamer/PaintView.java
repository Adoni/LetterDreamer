package com.example.letterdreamer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

class PaintView extends View {
	
	private Bitmap mBitmap;
	private Canvas mCanvas;
	private Path mPath;
	private Paint mBitmapPaint;
	private int penWidth;
	private int penColor;
	private int penStyle;
	private Paint mPaint;
	private int width;
	private int height;
	
	public PaintView(Context c,AttributeSet attrs) {
		super(c,attrs);
		penWidth=10;
		penColor=0xFFFF0000;
		penStyle=0;
		
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(penColor);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(penWidth);
		width=1000;
		height=1000;
		mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
		mPath = new Path();
		mBitmapPaint = new Paint(Paint.DITHER_FLAG);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(penColor);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(penWidth);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(0xFFAAAAAA);
	
		canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
	
		canvas.drawPath(mPath, mPaint);
	}
	
	private float mX, mY;
	private static final float TOUCH_TOLERANCE = 4;
	
	// 开始画图
	private void touch_start(float x, float y) {
		mPath.reset();
		mPath.moveTo(x, y);
		mX = x;
		mY = y;
	}// 移动画笔
	
	private void touch_move(float x, float y) {
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);
		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
			mX = x;
			mY = y;
		}
	}
	
	// 画到屏幕
	private void touch_up() {
		mPath.lineTo(mX, mY);
		// 将内容画都屏幕上
		mCanvas.drawPath(mPath, mPaint);
		// 重设画笔防止重复描绘
		mPath.reset();
	}
	
	// 对屏幕的触摸时间的响应
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		
		switch (event.getAction()) {
			// 按下事件响应
			case MotionEvent.ACTION_DOWN:
			touch_start(x, y);
			invalidate();
			break;
			// 移动事件响应
			case MotionEvent.ACTION_MOVE:
			touch_move(x, y);
			invalidate();
			break;
			// 松开事件响应
			case MotionEvent.ACTION_UP:
			touch_up();
			invalidate();
			break;
		}
		return true;
	}

	public void setColor(int color)
	{
		mPaint.setColor(color);
	}
	public void setPenStyle(int style)
	{
		
	}
	public void setPenWidth(int width)
	{
		mPaint.setStrokeWidth(width);
	}
	public void clean()
	{
		mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		mCanvas.setBitmap(mBitmap);
		invalidate();
	}
	public void printScreen()
	{
		mCanvas.save();
	}
}
