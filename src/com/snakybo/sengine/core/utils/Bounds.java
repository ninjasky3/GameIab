package com.snakybo.sengine.core.utils;

/** @author Kevin Krol
 * @since May 27, 2014 */
public class Bounds {
	private float left;
	private float bottom;
	private float right;
	private float top;
	
	public Bounds(float left, float bottom, float right, float top) {
		if(right < left) {
			float temp = left;
			
			left = right;
			right = temp;
		}
		
		if(top < bottom) {
			float temp = bottom;
			
			bottom = top;
			top = temp;
		}
		
		this.left = left;
		this.bottom = bottom;
		this.right = right;
		this.top = top;
	}
	
	@Override
	public String toString() {
		return "Bounds (left=" + left + ", bottom=" + bottom + ", right=" + right + ", top=" + top + ")";
	}

	public void setLeft(float left) {
		this.left = left;
	}
	
	public void setBottom(float bottom) {
		this.bottom = bottom;
	}
	
	public void setRight(float right) {
		this.right = right;
	}
	
	public void setTop(float top) {
		this.top = top;
	}

	public float getLeft() {
		return left;
	}

	public float getBottom() {
		return bottom;
	}

	public float getRight() {
		return right;
	}
	
	public float getTop() {
		return top;
	}
}
