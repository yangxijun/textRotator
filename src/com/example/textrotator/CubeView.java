package com.example.textrotator;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings.TextSize;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CubeView extends View {
	private static final double RADIUS = 0;

	// 摄像机
	private Camera mCamera;

	// 翻转用的变量
	private Matrix mMatrix = new Matrix();
	private Paint mPaint = new Paint();

	private int deltaX;

	public CubeView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		setWillNotDraw(false);
		mCamera = new Camera();
		mPaint.setAntiAlias(true);
		int TextSize = 24;
		mPaint.setTextSize(TextSize);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int speed = -1;
		rotate(speed, canvas);
		// createSphere(canvas);
		postInvalidate();

	}

	Rect rect;

	private float mStartTime = -1;

	private void createSphere(Canvas canvas) {

		for (int i = 0; i < 180; i += 10) {
			for (int j = 0; j < 360; j += 10) {

				float x = (float) (RADIUS * Math.sin(i * Math.PI / 180) * Math
						.cos(j * Math.PI / 180));
				float z = (float) (RADIUS * Math.sin(i * Math.PI / 180) * Math
						.sin(j * Math.PI / 180));
				float y = (float) (RADIUS * Math.cos(i * Math.PI / 180));
			}
		}
	}

	/**
	 * 转动
	 * 
	 * @param degreeX
	 */
	private void rotate(int degreeX, Canvas canvas) {
		deltaX += degreeX;

		mCamera.save();
		// 旋转--绕Y轴 变化
		mCamera.rotateY(deltaX);

		int radius = getWidth() / 3;
		mCamera.translate(0, 0, -radius);
		mCamera.getMatrix(mMatrix);
		mCamera.restore();
		// 以设置的点为旋转中心,如果不加这两句，就是以（0,0）点为旋转中心

		mMatrix.preTranslate(-getWidth() / 2, -getHeight() / 2);
		mMatrix.postTranslate(getWidth() / 2, getHeight() / 2);

		canvas.setMatrix(mMatrix);
		// canvas.drawText("GO短信", getWidth() / 1.5f, getHeight() / 1.5f,
		// mPaint);
		canvas.drawText("GO桌面", getWidth() / 2, getHeight() / 2, mPaint);
		canvas.drawText("GO天气", getWidth() / 3, getHeight() / 3, mPaint);

		mCamera.save();

		invalidate();
	}

}