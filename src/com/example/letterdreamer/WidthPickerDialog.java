package com.example.letterdreamer;


import com.example.letterdreamer.ColorPickerDialog.OnColorChangedListener;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;


public class WidthPickerDialog extends Dialog {
	private Context context;
	private OnWidthChangedListener listener;
	public WidthPickerDialog(Context context, OnWidthChangedListener listener) {
        super(context);
        this.context = context;
        this.listener=listener;
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		WidthPickerView view=new WidthPickerView(context);
		setContentView(view);
	}
	
	public class WidthPickerView extends View{
		public WidthPickerView(Context context)
		{
			super(context);
		}
		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			super.onDraw(canvas);
		}
	}
	
	public interface OnWidthChangedListener{
		void widthChanged(int penWidth);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		listener.widthChanged(12);
		return super.onTouchEvent(event);
	}
	
	
	
}
