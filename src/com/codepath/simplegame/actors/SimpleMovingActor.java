package com.codepath.simplegame.actors;

import android.graphics.Point;
import android.graphics.Rect;

import com.codepath.simplegame.Velocity;

public abstract class SimpleMovingActor extends Actor {
	private Point pos;
	private int height;
	private int width;
	private Velocity velocity;

	public SimpleMovingActor(int x, int y) {
		this.pos = new Point(x, y);
		this.velocity = new Velocity();
	}
	
	public SimpleMovingActor(int x, int y, int width, int height) { 
		this(x, y);
		this.setDimensions(width, height);
	}
	
	// Called to move position based on the velocity
	public void move() {
		if (this.isEnabled() && this.isVisible()) {
			pos.x += (velocity.getXSpeed() * velocity.getXDirection());
			pos.y += (velocity.getYSpeed() * velocity.getYDirection());
		}
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public Velocity getVelocity() {
		return velocity;
	}
	
	public Rect getRect() {
		return new Rect(getX(), getY(), getX() + width, getY() + height);
	}
	
	public void setPos(int x, int y) {
		this.pos = new Point(x, y);
	}

	public int getX() {
		return this.pos.x;
	}

	public void setX(int x) {
		this.pos.x = x;
	}

	public int getY() {
		return this.pos.y;
	}

	public void setY(int y) {
		this.pos.y = y;
	}
	
	public boolean intersect(SimpleMovingActor actor) {
		return getRect().intersect(actor.getRect());
	}
}
