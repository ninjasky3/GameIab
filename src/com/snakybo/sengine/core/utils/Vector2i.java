package com.snakybo.sengine.core.utils;

/** Vector 2 math class
 * 
 * @author Kevin Krol
 * @since Apr 5, 2014 */
public class Vector2i {
	private int x;
	private int y;
	
	/** Constructor for the vector 2
	 * @param x The X component
	 * @param y The Y component */
	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "Vector2i (x=" + x + ", y=" + y + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		
		if(obj == null)
			return false;
		
		if(getClass() != obj.getClass())
			
			return false;
		
		Vector2i other = (Vector2i)obj;
		
		if(x != other.x)
			return false;
		
		if(y != other.y)
			return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		
		int result = 1;
		
		result = prime * result + x;
		result = prime * result + y;
		
		return result;
	}

	/** @return The length of the vector */
	public int length() {
		return (int)Math.sqrt(x * x + y * y);
	}
	
	/** @return The highest component of the vector */
	public int max() {
		return Math.max(x, y);
	}
	
	/** @return The dot product of this vector and another
	 * @param r The other vector */
	public int dot(Vector2i r) {
		return x * r.getX() + y * r.getY();
	}
	
	/** @return The cross product of this vector and another
	 * @param r The other vector */
	public int cross(Vector2i r) {
		return x * r.getY() - y * r.getX();
	}
	
	/** @return A new vector that has been normalized */
	public Vector2i normalize() {
		int length = length();
		
		return new Vector2i(x / length, y / length);
	}
	
	/** Rotate the vector
	 * @param angle The angle to rotate by
	 * @return A new vector that has been rotated */
	public Vector2i rotate(int angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		
		return new Vector2i((int)(x * cos - y * sin), (int)(x * sin + y * cos));
	}
	
	/** Linearly interpolate the vector
	 * @param dest The destination
	 * @param lerpFactor The lerp factor
	 * @return A new vector that has been lerped */
	public Vector2i lerp(Vector2i dest, int lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}
	
	/** Add the specified vector to the vector
	 * @param r The vector to add
	 * @return A new vector with the addition */
	public Vector2i add(Vector2i r) {
		return new Vector2i(x + r.getX(), y + r.getY());
	}
	
	/** Add the specified value to the vector
	 * @param r The value to add
	 * @return A new vector with the addition */
	public Vector2i add(int r) {
		return new Vector2i(x + r, y + r);
	}
	
	/** Subtract the specified vector from the vector
	 * @param r The vector to subtract
	 * @return A new vector with the subtraction */
	public Vector2i sub(Vector2i r) {
		return new Vector2i(x - r.getX(), y - r.getY());
	}
	
	/** Subtract the specified value from the vector
	 * @param r The value to subtract
	 * @return A new vector with the subtraction */
	public Vector2i sub(int r) {
		return new Vector2i(x - r, y - r);
	}
	
	/** Multiply the vector by another
	 * @param r The vector to multiply by
	 * @return A new vector that has been multiplied */
	public Vector2i mul(Vector2i r) {
		return new Vector2i(x * r.getX(), y * r.getY());
	}
	
	/** Multiply the vector by the specified value
	 * @param r The value to multiply by
	 * @return A new vector that has been multiplied */
	public Vector2i mul(int r) {
		return new Vector2i(x * r, y * r);
	}
	
	/** Divide the vector by another
	 * @param r The vector to divide by
	 * @return A new vector that has been divided */
	public Vector2i div(Vector2i r) {
		return new Vector2i(x / r.getX(), y / r.getY());
	}
	
	/** Divide the vector by the specified value
	 * @param r The value to divide by
	 * @return A new vector that has been divided */
	public Vector2i div(int r) {
		return new Vector2i(x / r, y / r);
	}
	
	/** @return The absolute value of the vector */
	public Vector2i abs() {
		return new Vector2i(Math.abs(x), Math.abs(y));
	}
	
	/** Set the components of the vector
	 * @param x The new X component
	 * @param y The new Y component
	 * @return This vector, used for chaining */
	public Vector2i set(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}
	
	/** Set the values of this vector to those of another
	 * @param r The vector to clone
	 * @return This vector, used for chaining */
	public Vector2i set(Vector2i r) {
		set(r.getX(), r.getY());
		
		return this;
	}
	
	/** Set the X component of the vector
	 * @param x The new X component */
	public void setX(int x) {
		this.x = x;
	}
	
	/** Set the Y component of the vector
	 * @param y The new Y component */
	public void setY(int y) {
		this.y = y;
	}
	
	/** @return The X component of the vector */
	public int getX() {
		return x;
	}
	
	/** @return The Y component of the vector */
	public int getY() {
		return y;
	}
	
	public Vector3f toVector3f() {
		return new Vector3f(x, y, 0);
	}
}
