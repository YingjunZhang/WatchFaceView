package com.holy.library.watchfaceview;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class WatchFaceView extends View{
	
	private int pointerColor = Color.BLACK;  //指针颜色
	private int pointerCircleColor = Color.YELLOW;//指针基座颜色
	private int bgDrawable = R.drawable.watch_face;     //表盘图片
	
	private double totalValue = 10;
	private double nowValue = 2.5;
	
	private int width;
	private int height;
	private int padding= 10;
	private Point needlePoint;  //指针固定点
	private double originX;
	private double originY;
	private int needleLength;   //指针长度
	private double pai = 3.1415926;
	
	private Context mContext;
	

	public WatchFaceView(Context context) {
		this(context, null);
	}

	

	public WatchFaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		
		TypedArray ta =context.obtainStyledAttributes(attrs, R.styleable.WatchFaceView);
        if(ta != null){
        	int n = ta.getIndexCount();
        	System.out.println("n="+n);
        	for (int i = 0; i < n; i++) {
                int attr = ta.getIndex(i);
                if(attr == R.styleable.WatchFaceView_pointer_color){
                	pointerColor= ta.getColor(attr, Color.BLACK);
                	continue;
                }
                if(attr == R.styleable.WatchFaceView_pointer_circle_color){
                	pointerCircleColor = ta.getColor(attr, Color.YELLOW);
                	continue;
                }
                if(attr == R.styleable.WatchFaceView_back_pic){
                	bgDrawable = ta.getResourceId(attr, R.drawable.watch_face);
                	continue;
                }
                if(attr == R.styleable.WatchFaceView_total_value){
                	totalValue =ta.getInt(attr, 10);
                	continue;
                }
                
                
            }
        }
        ta.recycle();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawColor(Color.WHITE);   //控件背景设置为白色
		width=getWidth();   //得到控件宽度
		height = getHeight();  //得到控件高度
		int displayWidth = 0;  //显示区域宽度
		int displayHeight = 0; //显示区域高度
		float xDrift = 0f;  //X轴偏移量
		float yDrift = 0f;  //Y轴偏移量
		needlePoint = new Point();
		Bitmap backPic = BitmapFactory.decodeResource(mContext.getResources(), bgDrawable);   //得到显示图片
		//创建显示区域时，表盘以宽比高为2:1显示；如果控件宽高比不为2:1，则需要进行修正
		if(height> width/2){  //如果控件高度大于宽度的二分之一
			System.out.println("height> width/2,height="+height+"width="+width);
			
			displayWidth= width-10;
			displayHeight = width/2-5;
			xDrift = 5f;
			yDrift=(height-width/2+5)/2.0f;
			needleLength=width/2-12;
			needlePoint.set(width/2, (height-5+width/2)/2);
		}
		if(height<width/2){//如果控件高度小于宽度的二分之一
			System.out.println("height < width/2,height="+height+"width="+width);
			
			displayWidth = height*2;
			displayHeight = height-6;
			xDrift =(width-height*2)/2.0f;
			yDrift=3.0f;
			needlePoint.set(width/2,height-3);
		}
		if(height == width/2){ //如果控件高度等于宽度的二分之一
			System.out.println("height = width/2,height="+height+"width="+width);
			
			displayWidth = width - 10;
			displayHeight = height -6;
			xDrift = 5.0f;
			yDrift = 3.0f;
			needlePoint.set(width/2,height-3);
		}
		float picHeight = backPic.getHeight();  //获取图片高度
		float picWidth = backPic.getWidth();    //获取图片宽度
		
		Matrix matrix = new Matrix();  //根据现实区域设置图片缩放
        matrix.setScale(displayWidth/picWidth, displayHeight/picHeight);
		
        //画出图片
		canvas.save();
		canvas.translate(xDrift, yDrift);
		canvas.drawBitmap(backPic, matrix, null);
		backPic.recycle();
		canvas.restore();
		//画指针
		Paint paint = new Paint();		   
        paint.setStrokeJoin(Paint.Join.ROUND);   
        paint.setStrokeCap(Paint.Cap.ROUND);   
        paint.setStrokeWidth(3); 
        paint.setColor(pointerColor);
		Path path = new Path();		
		path.moveTo(needlePoint.x, needlePoint.y);
		originX = needlePoint.x;
		originY = needlePoint.y;
		float nowX = (float) (originX - Math.cos(pai*(nowValue/totalValue))*needleLength);
		float nowY = (float) (originY - Math.sin(pai*(nowValue/totalValue))*needleLength);
		canvas.drawLine((float)originX, (float)originY, nowX, nowY, paint);//画指针线
		paint.setColor(pointerCircleColor);
		canvas.drawCircle(needlePoint.x, needlePoint.y, 5, paint);//画指针基座
		
		
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
	}
	
	/**
	 * 设置仪表当前显示值，并立刻改变控件显示
	 * @param value
	 */
	public void setValue(float value){
		nowValue = value;
		invalidate();
	}
	
	/**
	 * 设置仪表量程，并立刻改变控件显示
	 * @param value
	 */
	public void setTotalValue(float value){
		this.totalValue = value;
		invalidate();
	}
	
	
	
	

}
