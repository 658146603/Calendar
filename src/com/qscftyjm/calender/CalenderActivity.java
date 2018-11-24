package com.qscftyjm.calender;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class CalenderActivity extends Activity {

	int heigth ;
	int width ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DisplayMetrics dm = getResources().getDisplayMetrics();
		heigth = dm.heightPixels;
		width = dm.widthPixels;
		setContentView(new ClanderView(this,heigth,width));

		
		
		
		
		Toast.makeText(this, "height "+heigth+"width "+width, Toast.LENGTH_SHORT).show();
	}
	
	class ClanderView extends View{

		float screenY;
		float screenX;
		Paint paint;
		public ClanderView(Context context, int heigth, int width) {
			super(context);
			paint = new Paint(Paint.ANTI_ALIAS_FLAG);//在画图的时候，图片如果旋转或缩放之后，总是会出现那些华丽的锯齿。给Paint加上抗锯齿标志
            paint.setAntiAlias(true);
            paint.setColor(Color.BLUE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(1);
            screenX=width;
            screenY=heigth;
		}

		@Override
        protected void onDraw(Canvas canvas) {
            //canvas.translate(200,200);
            //canvas.rotate(30);
            //drawCircle(50,50,90,canvas,paint);
            //drawLine(canvas);
            paint.setColor(0xFFCCCCCC);
            Log.d("Clander",screenX+"*"+screenY);
//           for(int i=130;i<=screenY;i+=120){
//            	drawPartLine(i, canvas, paint);
//            	for(int j=100;j<screenX;j+=(screenX-200)/6){
//            		drawCircle(j, i-50, 40, canvas, paint);
//            	}
//           }
            float radius=screenX/7;
            
            for(int i=0;i<5;i++){
            	for(int j=0;j<7;j++){
            		drawCircle(20, 20, 50, canvas, paint);
            	}
            	
            	drawPartLine(300+i*150, canvas, paint);
            }
            
            
            //drawCircle(90,90,50,canvas,paint);
        }
	
		private void drawCircle(int cx,int cy,float radius,Canvas canvas,Paint paint){
            canvas.drawCircle(cx,cy,radius,paint);
        }

        private void drawLine(Canvas canvas){
            //画一条线
            canvas.drawLine(250, 250, 400, 400, paint);
        }

        private void drawALine(float startX,float startY,float stopX,float stopY,Canvas canvas,Paint paint){
            //画一条线
            canvas.drawLine( startX, startY, stopX, stopY,paint);
        }
        
        private void drawPartLine(float height,Canvas canvas,Paint paint){
        	canvas.drawLine( 20, height, screenX-20, height,paint);
        }
        
        
		

	}

}