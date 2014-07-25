package com.example.textrotator;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CubeView2 extends View {
	// 摄像机
	private Camera mCamera;

	// 翻转用的变量
	private Matrix mMatrix = new Matrix();
	private Paint mPaint = new Paint();

	int TextSize = 24;
	// 图片的中心点坐标
	private int mCenterX;
	// 转动的总距离
	private int deltaX;

	public CubeView2(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		setWillNotDraw(false);
		mCamera = new Camera();
		mPaint.setAntiAlias(true);
		mPaint.setTextSize(TextSize);
		// bWidth = face.getWidth();
		// bHeight = face.getHeight();
		// centerX = bWidth >> 1;
		// centerY = bHeight >> 1;

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		rotate(-2, canvas);
		postInvalidate();

	}

	/*
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		float x = event.getX();
		float y = event.getY();
		decideText(x, y);
		return super.onTouchEvent(event);
	}

	private void decideText(float x, float y) {
		if (y < getWidth() / 3 + 24) {
			Toast.makeText(getContext(), "GO天气", Toast.LENGTH_SHORT).show();
		} else if ((y > getWidth() / 3 + TextSize)
				&& y < getWidth() / 2 + TextSize)
			Toast.makeText(getContext(), "GO桌面", Toast.LENGTH_SHORT).show();
		else if ((y > getWidth() / 1.5f) && y < getWidth() / 1.3f)
			Toast.makeText(getContext(), "GO短信", Toast.LENGTH_SHORT).show();
	}
	*/

	/**
	 * 转动
	 * 
	 * @param degreeX
	 */
	void rotate(int degreeX, Canvas canvas) {
		deltaX += degreeX;

		mCamera.save();
		// 旋转--绕Y轴 变化
		mCamera.rotateY(deltaX);
		// 缩放
		mCamera.translate(0, 0, -getWidth() / 3);
		mCamera.getMatrix(mMatrix);
		mCamera.restore();
		// 以设置的点为旋转中心,如果不加这两句，就是以（0,0）点为旋转中心

		mMatrix.preTranslate(-getWidth() / 2, -getHeight() / 2);
		mMatrix.postTranslate(getWidth() / 2, getHeight() / 2);

		canvas.setMatrix(mMatrix);
//		canvas.drawText("GO短信", getWidth() / 1.5f, getHeight() / 1.5f, mPaint);
//		canvas.drawText("GO桌面", getWidth() / 2, getHeight() / 2, mPaint);
		canvas.drawText("GO天气", getWidth() / 3, getHeight() / 3, mPaint);

		mCamera.save();

		invalidate();
	}

}
