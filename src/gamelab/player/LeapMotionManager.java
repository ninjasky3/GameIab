package gamelab.player;

import java.io.IOException;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.FingerList;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Vector;
import com.snakybo.sengine.core.Input;
import com.snakybo.sengine.core.utils.Vector2i;
import com.snakybo.sengine.core.utils.Vector3f;

public class LeapMotionManager implements Runnable {
	private static final String LEAP_THREAD_NAME = "LeapInputManager";
	
	private static Thread leapInputManager;
	
	private LeapListener leapListener;
	private Controller leapController;
	
	public LeapMotionManager() {
		if(leapInputManager == null) {
			leapInputManager = new Thread(this, LEAP_THREAD_NAME);
			leapInputManager.start();
			
			leapListener = new LeapListener();
			leapController = new Controller();
			
			leapController.addListener(leapListener);
		}
	}
	
	@Override
	public void run() {
		try {
			System.in.read();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		leapController.removeListener(leapListener);
	}
	
	public static class LeapListener extends Listener {
		private static Vector3f position = new Vector3f(0, 0, 0);
		
		@Override
		public void onInit(Controller controller) {
			System.out.println("[Leap Motion] Initialized");
		}
		
		@Override
		public void onConnect(Controller controller) {
			System.out.println("[Leap Motion] Connected");
			
		}
		
		@Override
		public void onDisconnect(Controller controller) {
			System.out.println("[Leap Motion] Disconnected");
		}
		
		@Override
		public void onExit(Controller controller) {
			System.out.println("[Leap Motion] Exited");
		}
		
		@Override
		public void onFrame(Controller controller) {
			Frame frame = controller.frame();
			
			if(!frame.hands().isEmpty()) {
				Hand hand = frame.hands().get(0);
				FingerList fingers = hand.fingers();
				
				if(!fingers.isEmpty()) {
					Vector avgPos = Vector.zero();
					
					for(Finger finger : fingers)
						avgPos = avgPos.plus(finger.tipPosition());
					
					avgPos = avgPos.divide(fingers.count());
					
					final int x = ((int)avgPos.getX() + 100) * 6;
					final int y = (int)(1000 - (3f * (int)avgPos.getY()));
					final int z = (int)avgPos.getZ();
					
					position.set(x, 500- y, z);
					
					
					Input.setMousePosition(new Vector2i(x, + 500 -y));
					if(Input.getMousePosition().getX()< 0){
						Input.setMousePosition(new Vector2i(0 , y));
					}
					if(Input.getMousePosition().getX()> 0 && Input.getMousePosition().getX()> 1280 ){
						Input.setMousePosition(new Vector2i(1280 , y));
					}
					if(Input.getMousePosition().getY()> 720){
						Input.setMousePosition(new Vector2i(x , 720));
					}
					if(Input.getMousePosition().getY()< 0 ){
						Input.setMousePosition(new Vector2i(x , 10));
					}
				}
			}
		}
		
		public static Vector3f getPosition() {
			return position;
		}
	}
}