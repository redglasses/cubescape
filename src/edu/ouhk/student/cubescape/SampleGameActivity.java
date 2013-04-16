package edu.ouhk.student.cubescape;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import edu.ouhk.student.cubescape.engine.Character;
import edu.ouhk.student.cubescape.engine.Scene;
import edu.ouhk.student.cubescape.engine.character.*;
import edu.ouhk.student.cubescape.engine.renderer.GLES20;
import edu.ouhk.student.cubescape.engine.scene.*;

public class SampleGameActivity extends AndroidApplication {
	private float firstTouchX, firstTouchY;

	private Character character;
	private Scene game;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		character = new ValkyrieVF1A(){
			@Override
			public void create() {
				super.create();
				movingStep = 2f;
				this.rotation.y = 180;
				//this.move(Direction.LEFT);
				this.stand();
				
			}
		};
		
		game = new SampleScene(character);
		initialize(new GLES20(game), Application.GLConfig);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch(keyCode) {
		case KeyEvent.KEYCODE_W:
		case KeyEvent.KEYCODE_DPAD_UP:
			character.move(Character.Direction.UP);
			break;
		case KeyEvent.KEYCODE_S:
		case KeyEvent.KEYCODE_DPAD_DOWN:
			character.move(Character.Direction.DOWN);
			break;
		case KeyEvent.KEYCODE_A:
		case KeyEvent.KEYCODE_DPAD_LEFT:
			character.move(Character.Direction.LEFT);
			break;
		case KeyEvent.KEYCODE_D:
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			character.move(Character.Direction.RIGHT);
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		switch(keyCode) {
		case KeyEvent.KEYCODE_A:
		case KeyEvent.KEYCODE_DPAD_LEFT:
		case KeyEvent.KEYCODE_D:
		case KeyEvent.KEYCODE_DPAD_RIGHT:
		case KeyEvent.KEYCODE_W:
		case KeyEvent.KEYCODE_DPAD_UP:
		case KeyEvent.KEYCODE_S:
		case KeyEvent.KEYCODE_DPAD_DOWN:
			
			character.stand();
			break;
		}
		return super.onKeyUp(keyCode, event);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//firstTouchX = event.getAxisValue(MotionEvent.AXIS_X);
		//firstTouchY = event.getAxisValue(MotionEvent.AXIS_Y);
		float angle = event.getOrientation(event.getPointerId(event.getPointerCount()));
		int orientation = (int)Math.round(angle/Math.PI*4);
		switch (orientation){
			case 0:
				character.move(Character.Direction.UP);
				break;
			case 1:
				character.move(Character.Direction.UP_RIGHT);
				break;
			case 2:
				character.move(Character.Direction.RIGHT);
				break;
			case 3:
				character.move(Character.Direction.DOWN_RIGHT);
				break;
			case 4:
			case -4:
				character.move(Character.Direction.DOWN);
				break;
			case -3:
				character.move(Character.Direction.DOWN_LEFT);
				break;
			case -2:
				character.move(Character.Direction.LEFT);
				break;
			case -1:
				character.move(Character.Direction.UP_LEFT);
				break;
		}
		return super.onTouchEvent(event);
	}
}
