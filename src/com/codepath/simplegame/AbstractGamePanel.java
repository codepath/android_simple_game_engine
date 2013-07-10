package com.codepath.simplegame;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class AbstractGamePanel extends SurfaceView implements SurfaceHolder.Callback {
	private GameLoopThread thread;
	private Paint paint;
	private int panelColor = Color.GRAY;

	public AbstractGamePanel(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		// create the game loop thread
		thread = new GameLoopThread(getHolder(), this);
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
		// setup brush
		panelColor = Color.GRAY;
		paint = new Paint();
	}

	// Draw the game board
	protected void render(Canvas canvas) {
		// clear canvas
		canvas.drawColor(panelColor);
		// draw canvas
		this.redrawCanvas(canvas);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// ...
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// start game thread
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
	}
	
	public void setPanelColor(int color) {
		panelColor = color;
	}

	// This method is called when the game first launches. Use this to
	// initialize variables and set starting values.
	public abstract void onStart();
	// Called every "tick" to apply game logic
	public abstract void onTimer();
	// Called every second to redrawCanvas
	public abstract void redrawCanvas(Canvas canvas);
	// Handles key presses within our game
	public abstract boolean onKeyDown(int keyCode, KeyEvent event);
	// Handle touch inputs in the game
	public abstract boolean onTouchEvent(MotionEvent event);
	
	protected Paint getPaint() {
		return paint;
	}
	
	protected GameLoopThread getThread() {
		return thread;
	}
}