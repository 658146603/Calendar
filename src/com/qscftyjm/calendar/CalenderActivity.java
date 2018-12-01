package com.qscftyjm.calendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
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
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(new ClanderView(this,heigth,width));
		
		Toast.makeText(this, "height "+heigth+" width "+width, Toast.LENGTH_SHORT).show();
		
	}
	
	class ClanderView extends View{

		float screenY;
		float screenX;
		Paint paint;
		public ClanderView(Context context, int heigth, int width) {
			super(context);
			paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLUE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(3);
            screenX=width;
            screenY=heigth;
		}

		@Override
        protected void onDraw(Canvas canvas) {
            //canvas.translate(200,200);
            //canvas.rotate(30);
            //drawCircle(50,50,90,canvas,paint);
            //drawLine(canvas);
            paint.setColor(0xFFCCCCDD);
            Log.d("Calendar",screenX+"*"+screenY);
//           for(int i=130;i<=screenY;i+=120){
//            	drawPartLine(i, canvas, paint);
//            	for(int j=100;j<screenX;j+=(screenX-200)/6){
//            		drawCircle(j, i-50, 40, canvas, paint);
//            	}
//           }
//            float radius=(screenX/7)*0.3f;
//            
//            for(int i=0;i<7;i++){
//            	for(int j=0;j<5;j++){
//            		drawCircle((int)(150+i*radius*2.8f), (int)(140+j*radius*2.8f), radius, canvas, paint);
//            		
//            		drawPartLine(150+(j*radius*2.8f), canvas, paint);
//            	}
//            
//            }
            canvas.drawColor(Color.TRANSPARENT,Mode.CLEAR);
            canvas.drawColor(0xFFBBDEFB,Mode.ADD);
            canvas.drawRoundRect(800+2.5f, 800+2.5f, 1000-2.5f, 1500-2.5f, 20, 20, paint);
//            drawCircle(120,120,50,canvas,paint);
//            drawCircle(250,120,50,canvas,paint);
//            drawCircle(380,120,50,canvas,paint);
//            drawCircle(510,120,50,canvas,paint);
//            drawCircle(640,120,50,canvas,paint);
//            drawCircle(770,120,50,canvas,paint);
//            drawCircle(900,120,50,canvas,paint);
            
//            for(int i=0;i<5;i++){
//            	for(int j=0;j<7;j++){
//            		drawCircle(140+j*130, 120+i*120, 50, canvas, paint);
//            		
//            		drawText(140+j*130, 120+i*120,String.valueOf(i*7+j+1),canvas, paint);
//            		
//            	}
//            	
//            	drawPartLine(180+i*120, canvas, paint);
//            	
//            }
            
            if(width>1400){
              
            }else if(width>1000){
            	for(int i=0;i<5;i++){
                	for(int j=0;j<7;j++){
                		drawCircle(140+j*130, 120+i*120, 50, canvas, paint);
                		
                		drawText(140+j*130, 120+i*120,String.valueOf(i*7+j+1),canvas, paint);
                		
                	}
                	
                	drawPartLine(180+i*120, canvas, paint);
                  }
            
            }else if(width>700){
            	for(int i=0;i<5;i++){
                	for(int j=0;j<7;j++){
                		drawCircle(100+j*100, 120+i*90, 45, canvas, paint);
                		
                		drawText(100+j*90, 120+i*90,String.valueOf(i*7+j+1),canvas, paint);
                		
                	}
                	
                	drawPartLine(180+i*90, canvas, paint);
                  }
            }else{finish();}
            
            
            
        }
	
		private void drawText(int x, int y, String text, Canvas canvas, Paint paint) {
			// Text 
			canvas.drawText(text, x, y, paint);
			
		}

		private void drawCircle(int cx,int cy,float radius,Canvas canvas,Paint paint){
			//Circle
            canvas.drawCircle(cx,cy,radius,paint);
        }

        private void drawALine(float startX,float startY,float stopX,float stopY,Canvas canvas,Paint paint){
            // Line
            canvas.drawLine( startX, startY, stopX, stopY,paint);
        }
        
        private void drawPartLine(float height,Canvas canvas,Paint paint){
        	//PartLine
        	canvas.drawLine( 40, height, screenX-40, height,paint);
        }
        
        
		

	}

}