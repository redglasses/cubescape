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
import edu.ouhk.student.cubescape.engine.sense.*;

public class SampleGameActivity extends AndroidApplication {
	private static AndroidApplicationConfiguration cfg;
	static {
		cfg = new AndroidApplicationConfiguration();
		cfg.useWakelock = true;
		cfg.useGL20 = true;
		cfg.useAccelerometer = false;
		cfg.useCompass = false;
	}

	private Character character;
	private Scene game;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		character = new Ogro(){
			@Override
			public void create() {
				super.create();
				movingStep = 2f;
				position.z = -50f;
			}
		};
		
		game = new SampleSense(character);
		initialize(new GLES20(game), cfg);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.game_option_menu, menu);
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
		return super.onTouchEvent(event);
	}
}