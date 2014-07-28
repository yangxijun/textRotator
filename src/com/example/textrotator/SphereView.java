package com.example.textrotator;

import android.R.integer;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

public class SphereView extends View {

	// 摄像机
	private Camera mCamera;
	// 绘制变换用
	private Matrix mMatrix = new Matrix();
	private Paint mPaint = new Paint();

	private String[] mText = { "水煮鱼", "红烧肉", "烧排骨", "瓜丝儿", "拌海蜇", "炝冬笋", "玉兰片", "烧鱼头",
			"炸面筋", "炸豆腐" };
	private int mSize = 10;
	private int mTextSize = 40;
	private float[] mRandom = new float[mSize];

	// [0]:旋转的角度，[1]:X坐标，[2]:Y坐标
	private float[][] mTextPosition = new float[mSize][3];

	public SphereView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		setWillNotDraw(false);
		mCamera = new Camera();
		mPaint.setAntiAlias(true);
		mPaint.setTextSize(mTextSize);
		createRandom();
	}

	// 创造一组-50到+50的随机数,作为旋转的角度
	private void createRandom() {
		for (int i = 0; i < mSize; i++) {
			mRandom[i] = (float) (Math.random() * 100 - 50);
		}
	}

	@Override
	public void draw(Canvas canvas) {

		float time = 0;
		for (int i = 0; i < mSize; i++, time++) {

			float speed = (i + 1) / 5f;
			rotate2(mRandom[i], time, 1, i);
			canvas.save();
			canvas.setMatrix(mMatrix);
			canvas.drawText(mText[i], 0, 0, mPaint);
			canvas.restore();
			invalidate();
		}
	}

	float mStartTime = -1;

	private void rotate2(float degreeX, float offset, float speed, int textNum) {

		mCamera = new Camera();

		if (mStartTime == -1) {
			mStartTime = AnimationUtils.currentAnimationTimeMillis();
		}
		float timePassed = AnimationUtils.currentAnimationTimeMillis()
				- mStartTime - offset * 3000;

		// 每毫秒转动的度数 * 速度的倍数
		float degreeY = (timePassed * 0.045f) * speed;

		mCamera.translate(0, 0, getWidth() / 2);

		// Y旋转
		mCamera.rotateY(degreeY);
		// X倾斜
		mCamera.rotateX(degreeX);
		// 旋转的中心
		mCamera.translate(0, 0, -getWidth() / 2);
		mCamera.getMatrix(mMatrix);
		// 移动至屏幕中间
		mMatrix.postTranslate(getWidth() / 2, getHeight() / 2);

		float[] fMatrix = new float[9];
		mMatrix.getValues(fMatrix);
		
		// 拿到角度和XY的值
		mTextPosition[textNum][0] = degreeY % 360;
		mTextPosition[textNum][1] = fMatrix[2];
		mTextPosition[textNum][2] = fMatrix[5];

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY() + mTextSize;

		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			for (int i = 0; i < mSize; i++)
				if (mTextPosition[i][0] < 90 || mTextPosition[i][0] > 270) {
					if (mTextPosition[i][1] > x - 50
							&& mTextPosition[i][1] < x + 50
							&& mTextPosition[i][2] > y - 30
							&& mTextPosition[i][2] < y + 30)
						Toast.makeText(getContext(), mText[i],
								Toast.LENGTH_SHORT).show();
				}

		}
		Log.i("My", "x:" + x + "y:" + y);
		Log.i("My", "度数" + mTextPosition[0][0] + "!X:" + mTextPosition[0][1]
				+ "!Y:" + mTextPosition[0][2]);

		return true;
	}
}