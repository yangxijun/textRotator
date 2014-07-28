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

	String[] text = { "水煮鱼", "红烧肉", "烧排骨", "瓜丝儿", "拌海蜇", "炝冬笋", "玉兰片", "烧鱼头",
			"炸面筋", "炸豆腐" };
	int size = 10;
	int TextSize = 40;
	float[] random = new float[size];

	// [0]:旋转的角度，[1]:X坐标，[2]:Y坐标
	float[][] textPosition = new float[size][3];

	public SphereView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		setWillNotDraw(false);
		mCamera = new Camera();
		mPaint.setAntiAlias(true);
		mPaint.setTextSize(TextSize);
		createRandom();
	}

	// 创造一组-50到+50的随机数,作为旋转的角度
	private void createRandom() {
		for (int i = 0; i < size; i++) {
			random[i] = (float) (Math.random() * 100 - 50);
		}
	}

	@Override
	public void draw(Canvas canvas) {

		float time = 0;
		for (int i = 0; i < size; i++, time++) {

			float speed = (i + 1) / 5f;
			rotate2(random[i], time, 1, i);
			canvas.save();
			canvas.setMatrix(mMatrix);
			canvas.drawText(text[i], 0, 0, mPaint);
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
		textPosition[textNum][0] = degreeY % 360;
		textPosition[textNum][1] = fMatrix[2];
		textPosition[textNum][2] = fMatrix[5];

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY() + TextSize;

		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			for (int i = 0; i < size; i++)
				if (textPosition[i][0] < 90 || textPosition[i][0] > 270) {
					if (textPosition[i][1] > x - 50
							&& textPosition[i][1] < x + 50
							&& textPosition[i][2] > y - 30
							&& textPosition[i][2] < y + 30)
						Toast.makeText(getContext(), text[i],
								Toast.LENGTH_SHORT).show();
				}

		}
		Log.i("My", "x:" + x + "y:" + y);
		Log.i("My", "度数" + textPosition[0][0] + "!X:" + textPosition[0][1]
				+ "!Y:" + textPosition[0][2]);

		return true;
	}
}