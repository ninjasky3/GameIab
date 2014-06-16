package gamelab.leap;

import gamelab.player.LeapMotionManager.LeapListener;

import com.snakybo.sengine.core.Component;

public class LeapMove extends Component {
	@Override
	protected void update(float delta) {
		getTransform().getPosition().set(LeapListener.getPosition());
	}
}
