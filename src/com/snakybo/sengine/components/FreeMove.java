package com.snakybo.sengine.components;

import com.snakybo.sengine.core.Component;
import com.snakybo.sengine.core.Input;
import com.snakybo.sengine.core.Input.KeyCode;

/** Free move component extends {@link Component}
 * 
 * <p>
 * Allows the parent game object to move freely
 * </p>
 * 
 * @author Kevin Krol
 * @since Apr 4, 2014 */
public class FreeMove extends Component {
	private float speed;
	
	private int keyForward;
	private int keyLeft;
	private int keyBack;
	private int keyRight;
	private int keyUp;
	private int keyDown;
	
	/** Constructor for the free move component
	 * @param speed The speed of the component */
	public FreeMove(float speed) {
		this(speed, KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D, KeyCode.NONE, KeyCode.NONE);
	}
	
	/** Constructor for the free move component
	 * @param speed The speed of the component
	 * @param keyForward The key to move forward
	 * @param keyLeft The key to move to the left
	 * @param keyBack The key to move backwards
	 * @param keyRight The key to move to the right */
	public FreeMove(float speed, int keyForward, int keyBack, int keyLeft, int keyRight, int keyUp, int keyDown) {
		this.speed = speed;
		
		this.keyForward = keyForward;
		this.keyBack = keyBack;
		
		this.keyLeft = keyLeft;
		this.keyRight = keyRight;
		
		this.keyUp = keyUp;
		this.keyDown = keyDown;
	}
	
	@Override
	protected void input(float delta) {
		float moveAmt = speed * delta;
		
		if(Input.getKey(keyForward))
			getTransform().translate(getTransform().getLocalRotation().getForward().mul(moveAmt));
		
		if(Input.getKey(keyBack))
			getTransform().translate(getTransform().getLocalRotation().getBack().mul(moveAmt));
		
		if(Input.getKey(keyLeft))
			getTransform().translate(getTransform().getLocalRotation().getLeft().mul(moveAmt));
		
		if(Input.getKey(keyRight))
			getTransform().translate(getTransform().getLocalRotation().getRight().mul(moveAmt));
		
		if(Input.getKey(keyUp))
			getTransform().translate(getTransform().getLocalRotation().getUp().mul(moveAmt));
		
		if(Input.getKey(keyDown))
			getTransform().translate(getTransform().getLocalRotation().getDown().mul(moveAmt));
	}
}
