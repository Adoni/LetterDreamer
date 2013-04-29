package com.example.letterdreamer;


import com.example.letterdreamer.PaintView.OnPaintListener;
import com.example.letterdreamer.PathStore.node;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	private ColorPickerDialog dialog;  
	private Context context;
	private PaintView paint;
	private Button colorPicker; 
	private Button backgroud;
	private Button penWidthPicker;
	private Button penStylePicker;
	private Button previewButton;
	private PathStore myPathStore;//全局变量
	private long current_time;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myPathStore=(PathStore) getApplication();
		current_time=System.currentTimeMillis();
		// 设置画笔属性
		//根据参数创建新位图
		DisplayMetrics dm = new DisplayMetrics();  
        //获取窗口属性  
        getWindowManager().getDefaultDisplay().getMetrics(dm);  
        //窗口宽度  
        int width = dm.widthPixels;  
        //窗口高度  
        int height = dm.heightPixels;  
        context=this;
		//Toast.makeText(c, ""+height+" "+width, Toast.LENGTH_LONG).show();
        //PaintView paintView=new PaintView(this);
		setContentView(R.layout.activity_main);
		((Button)findViewById(R.id.clear)).setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				paint.clean();
			}
		});
		colorPicker=(Button)findViewById(R.id.color);
		((Button)findViewById(R.id.snap)).setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				paint.printScreen();
			}
		});
		penWidthPicker=(Button)findViewById(R.id.penwidth);
		penWidthPicker.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WidthPickerDialog dialog=new WidthPickerDialog(context, new WidthPickerDialog.OnWidthChangedListener() {
			
					public void widthChanged(int penWidth) {
						// TODO Auto-generated method stub
						paint.setPenWidth(penWidth);
					}
				});
				dialog.show();

				dialog.setPenWidth(paint.getPenWidth());
			}
		});
		penStylePicker=(Button)findViewById(R.id.penstyle);
		penStylePicker.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StylePickerDialog dialog=new StylePickerDialog(context, new StylePickerDialog.OnStyleChangedListener() {
					public void styleChanged(int penStyle) {
						// TODO Auto-generated method stub
					}
				});
				dialog.show();
			}
		});
		paint=(PaintView)findViewById(R.id.paint);
		paint.setOnPaintListener(new OnPaintListener() {
			
			public void paint(float x, float y, int action) {
				// TODO Auto-generated method stub
				PathStore.node tempnode=myPathStore.new node();
				tempnode.x=x;
				tempnode.y=y;
				tempnode.action=action;
				tempnode.time=System.currentTimeMillis();
				myPathStore.addNode(tempnode);
			}
		});
		colorPicker.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog = new ColorPickerDialog(context, Color.BLACK,  "aaa",   
                        	new ColorPickerDialog.OnColorChangedListener() {
		                    public void colorChanged(int color) {  
		                        paint.setColor(color);
		                        colorPicker.setBackgroundColor(color);
		                    }  
                		});  
                dialog.show();
			}
		});
		previewButton=(Button)findViewById(R.id.preview);
		previewButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				paint.preview(myPathStore.getTempPath());
			}
		});
	}
}