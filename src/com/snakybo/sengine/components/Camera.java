package com.snakybo.sengine.components;

import com.snakybo.sengine.core.Component;
import com.snakybo.sengine.core.CoreEngine;
import com.snakybo.sengine.core.utils.Bounds;
import com.snakybo.sengine.core.utils.Matrix4f;
import com.snakybo.sengine.core.utils.Vector2f;
import com.snakybo.sengine.core.utils.Vector2i;
import com.snakybo.sengine.core.utils.Vector3f;

/** Camera component extends {@link Component}
 * 
 * @author Kevin Krol
 * @since Apr 4, 2014 */
public class Camera extends Component {
	private Matrix4f projection;
	
	private Vector2f orthoSize;
	
	/** Initialize a perspective camera
	 * @param fov The field of view
	 * @param aspect The aspect ratio
	 * @param zNear The near clipping plane
	 * @param zFar The far clipping plane
	 * @return A new perspective camera */
	public static Camera initPerspectiveCamera(float fov, float aspectRatio, float zNear, float zFar) {
		return new Camera(new Matrix4f().initPerspectiveCamera(fov, aspectRatio, zNear, zFar));
	}
	
	/** Initialize the matrix to the default values for an orthographic projection
	 * @param left The amount of pixels to the left
	 * @param right The amount of pixels to the right
	 * @param bottom The amount of pixels to the bottom
	 * @param top The amount of pixels to the top
	 * @param near The near clipping plane
	 * @param far The far clipping plane */
	public static Camera initOrthographicCamera(float left, float right, float bottom, float top, float near, float far) {
		Camera camera = new Camera(new Matrix4f().initOrthographicCamera(left, right, bottom, top, near, far));
		
		camera.orthoSize = new Vector2f(right - left, top - bottom);
		
		return camera;
	}
	
	/** Constructor for the camera
	 * @param projection The projection of the camera */
	public Camera(Matrix4f projection) {
		this.projection = projection;
	}
	
	@Override
	protected void addToEngine(CoreEngine engine) {
		engine.getRenderingEngine().addCamera(this);
	}
	
	public Bounds toBounds() {
		final Vector2f position = getTransform().getPosition().getXY();
		
		Bounds result = new Bounds(0, 0, 0, 0);
		
		result.setLeft(position.getX());
		result.setBottom(position.getY());
		result.setRight(position.getX() + orthoSize.getX());
		result.setTop(position.getY() + orthoSize.getY());
		
		return result;
	}
	
	
	
	
	public Vector2i cursorToWorld(Vector2i cursorPosition) {
		final Vector2i cameraPosition = getTransform().getLocalPosition().getXY().toVector2i();
		
		cursorPosition.set(cursorPosition.add(cameraPosition));
		cursorPosition.set(cursorPosition.getX() >> 4, cursorPosition.getY() >> 4);
		
		return cursorPosition;
	}
	
	/** Get the view projcetion of the camera represented as a matrix 4 */
	public Matrix4f getViewProjection() {
		Matrix4f cameraRotation = getTransform().getRotation().conjugate().toRotationMatrix();
		Vector3f cameraPos = getTransform().getPosition().mul(-1);
		
		Matrix4f cameraTranslation = new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
		
		return projection.mul(cameraRotation.mul(cameraTranslation));
	}
	
	public Vector2f getOrthoSize() {
		return orthoSize;
	}
}
