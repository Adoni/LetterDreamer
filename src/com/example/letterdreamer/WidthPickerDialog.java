package com.example.letterdreamer;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


public class WidthPickerDialog extends Dialog {
	private Context context;
	private OnWidthChangedListener listener;
	private int penwidth;
	private SeekBar widthseek;
	private Button okButton;
	private TextView widthtext;
	
	public WidthPickerDialog(Context context, OnWidthChangedListener listener) {
        super(context);
        this.context = context;
        this.listener=listener;
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("笔触宽度");
		setContentView(R.layout.widthpicker);
		widthseek=(SeekBar)findViewById(R.id.widthseek);
		widthtext=(TextView)findViewById(R.id.widthtext);
		okButton=(Button)findViewById(R.id.okbutton);
		widthseek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				//Toast.makeText(context, ""+penwidth, Toast.LENGTH_LONG).show();
			}
			
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				penwidth=progress;
				widthtext.setText(""+penwidth);
			}
		});
		okButton.setOnClickListener(new android.view.View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				listener.widthChanged(penwidth);
				WidthPickerDialog.this.dismiss();
			}
		});
	}
	
	public interface OnWidthChangedListener{
		void widthChanged(int penWidth);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}
}
