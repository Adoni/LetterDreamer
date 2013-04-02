package com.example.letterdreamer;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Paint mPaint;
	private int penWidth;
	private int penColor;
	private int penStyle;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new PaintView(this));
		// 设置画笔属性
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
	}
	
	class PaintView extends View {
	
		private Bitmap mBitmap;
		private Canvas mCanvas;
		private Path mPath;
		private Paint mBitmapPaint;
		
		public PaintView(Context c) {
			super(c);
			//根据参数创建新位图
			DisplayMetrics dm = new DisplayMetrics();  
	        //获取窗口属性  
	        getWindowManager().getDefaultDisplay().getMetrics(dm);  
	        //窗口宽度  
	        int width = dm.widthPixels;  
	        //窗口高度  
	        int height = dm.heightPixels;  
			Toast.makeText(c, ""+height+" "+width, Toast.LENGTH_LONG).show();
			mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			mCanvas = new Canvas(mBitmap);
			mPath = new Path();
			mBitmapPaint = new Paint(Paint.DITHER_FLAG);
		}
		
		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
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
	
	}

}