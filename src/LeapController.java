
import java.awt.AWTException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.math.*;
import java.awt.Robot;
import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;
//import com.sun.glass.ui.Robot;


class LeapListener extends Listener{
	public void onInIt(Controller controller)
	{
		System.out.println("Initialized");
	}
	public void onConnect(Controller controller)
	{
		System.out.println("connected to motion sensor");
		controller.enableGesture(Gesture.Type.TYPE_SWIPE);
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
	}
	public void onDisconnect(Controller controller)
	{
		System.out.println("motion sensor disconnected");
	}
	public void onExit(Controller controller){
		System.out.println("Exited");
	}
	public void onFrame(Controller controller)
	{
		Frame frame= controller.frame();
		controller.setPolicyFlags(Controller.PolicyFlag.POLICY_BACKGROUND_FRAMES);
		/*System.out.println("Frame id: "+ frame.id()+",Time Stamp: "+ frame.timestamp()+ ",Number of hands:"
				+ frame.hands().count()+", Fingers"+ frame.fingers().count() + ", Tools"+ frame.tools().count()
				+", Gestures:"+ frame.gestures().count());*/
		/*for (Hand hand : frame.hands()) {
			String handType = hand.isLeft() ? "Left Hand" : "Right Hand";
			System.out.println(handType +"  "+ ", id:"+ hand.id()
				+ ", Palm Position: "+ hand.palmPosition());
			Vector  normal = hand.palmNormal();
			Vector direction = hand.direction();
			System.out.println("Pitch: "+ Math.toDegrees(direction.pitch())
								+"Roll: "+ Math.toDegrees(normal.roll())
								+"Yaw: "+ Math.toDegrees(direction.yaw()));
			}*/
		  /*for(Finger finger: frame.fingers())
		  {
			  System.out.println("Finger Type: "+finger.type()
					  + "ID: " + finger.id()
					  + "Finger Length: "+ finger.length()
					  + "Finger Width: "+ finger.width());
		  }*/
		GestureList gestures = frame.gestures();
		String sDirection=" ";
		for(int i=0; i<gestures.count();i++)
		{
			Gesture gesture = gestures.get(i);
			//System.out.println("Check");
			switch(gesture.type()){
			case TYPE_SWIPE:
				SwipeGesture swipe = new SwipeGesture(gesture);
				float fAbsX = Math.abs(swipe.direction().getX());
				float fAbsY = Math.abs(swipe.direction().getY());
				float fAbsZ = Math.abs(swipe.direction().getZ());
				// Was X the greatest?
				if (fAbsX > fAbsY && fAbsX > fAbsZ)
				{
					if (swipe.direction().getX() > 0)
						sDirection = "Right";
					else sDirection = "Left";
				}
				// Was Y the greatest?
				else if (fAbsY > fAbsX && fAbsY > fAbsZ)
				{
					if (swipe.direction().getY() > 0)
						sDirection = "Up";
					else sDirection = "Down";
				}
				else // Z was the greatest.
				{
					if (swipe.direction().getZ() > 0)
						sDirection = "Backward";
					else sDirection = "Forward";
				}
				//System.out.println(sDirection);
			}
			
			if(sDirection == "Right")
			{
				
				try{
				Robot robot = new Robot();
				
				robot.keyPress(KeyEvent.VK_0);
				robot.keyRelease(KeyEvent.VK_0);
				
				} catch (AWTException e){
						e.printStackTrace();
					}
			}
			if(sDirection == "Left")
			{
				try{
					Robot robot = new Robot();
					
					robot.keyPress(KeyEvent.VK_1);
					robot.keyRelease(KeyEvent.VK_1);
					
					} catch (AWTException e){
							e.printStackTrace();
						}
			}
			if(sDirection == "Up")
			{
				try{
					Robot robot = new Robot();
					
					robot.keyPress(KeyEvent.VK_2);
					robot.keyRelease(KeyEvent.VK_2);
					
					} catch (AWTException e){
							e.printStackTrace();
						}
			}
			if(sDirection == "Down")
			{
				try{
					Robot robot = new Robot();
					
					robot.keyPress(KeyEvent.VK_3);
					robot.keyRelease(KeyEvent.VK_3);
					
					} catch (AWTException e){
							e.printStackTrace();
						}
			}
		}
		
	}
}

public class LeapController {
		public static void main(String[] args)
		{
			LeapListener listener = new LeapListener();
			Controller controller = new Controller();
			
			controller.addListener(listener);
			
			System.out.println("Press enter to quit");
			
			try {
				System.in.read();
			} catch (IOException e){
				e.printStackTrace();
			}
			controller.removeListener(listener);
		}
}
