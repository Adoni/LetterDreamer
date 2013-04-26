package com.example.letterdreamer;

import com.example.letterdreamer.ColorPickerDialog.OnColorChangedListener;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;


public class StylePickerDialog extends Dialog {
	private Context context;
	private OnStyleChangedListener listener;
	public StylePickerDialog(Context context, OnStyleChangedListener listener) {
        super(context);
        this.context = context;
        this.listener=listener;
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		StylePickerView view=new StylePickerView(context);
		setContentView(view);
	}
	
	public class StylePickerView extends View{
		public StylePickerView(Context context)
		{
			super(context);
		}
		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			super.onDraw(canvas);
		}
	}
	
	public interface OnStyleChangedListener{
		void styleChanged(int penStyle);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}
	
	
	
}
