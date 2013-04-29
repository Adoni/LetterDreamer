package com.example.letterdreamer;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

class PaintView extends View {
	private OnPaintListener listener;
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
	private Context context;
	
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
		context=c;
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
		listener.paint(x, y,MotionEvent.ACTION_DOWN);
	}// 移动画笔
	
	private void touch_move(float x, float y) {
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);
		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
			mX = x;
			mY = y;
			listener.paint(x, y,MotionEvent.ACTION_MOVE);
		}
	}
	
	// 画到屏幕
	private void touch_up() {
		mPath.lineTo(mX, mY);
		// 将内容画都屏幕上
		mCanvas.drawPath(mPath, mPaint);
		// 重设画笔防止重复描绘
		mPath.reset();
		listener.paint(mX, mY, MotionEvent.ACTION_UP);
		//Toast.makeText(context, myPathStore.tempath.lenth, Toast.LENGTH_LONG).show();
	}
	

	// 开始画图
	private void pre_touch_start(float x, float y) {
		mPath.reset();
		mPath.moveTo(x, y);
		mX = x;
		mY = y;
	}// 移动画笔
	
	private void pre_touch_move(float x, float y) {
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);
		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
			mX = x;
			mY = y;
		}
	}
	
	// 画到屏幕
	private void pre_touch_up() {
		mPath.lineTo(mX, mY);
		// 将内容画都屏幕上
		mCanvas.drawPath(mPath, mPaint);
		// 重设画笔防止重复描绘
		mPath.reset();
		//Toast.makeText(context, myPathStore.tempath.lenth, Toast.LENGTH_LONG).show();
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
	public int getPenWidth()
	{
		return (int)mPaint.getStrokeWidth();
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
	public interface OnPaintListener{
		void paint(float x,float y,int action);
	}
	public void setOnPaintListener(OnPaintListener listener){
		this.listener=listener;
	}
	public void preview(ArrayList<PathStore.node> temPath)
	{
		clean();
		long time=0;
		for(int i=0;i<temPath.size();i++)
		{
			PathStore.node tempnode=temPath.get(i);
			float x = tempnode.x;
			float y = tempnode.y;
			if(i<temPath.size()-1)
			{
				//time=tempnode.time-temPath.get(i+1).time;
			}
			switch (tempnode.action) {
				// 按下事件响应
				case MotionEvent.ACTION_DOWN:
				pre_touch_start(x+10, y);
				invalidate();
				break;
				// 移动事件响应
				case MotionEvent.ACTION_MOVE:
				pre_touch_move(x+10, y);
				invalidate();
				break;
				// 松开事件响应
				case MotionEvent.ACTION_UP:
				pre_touch_up();
				invalidate();
				break;
				
			}
			
		}
		
	}
}
