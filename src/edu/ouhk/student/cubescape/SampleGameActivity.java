package edu.ouhk.student.cubescape;

import android.os.Bundle;
import android.view.Menu;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.math.Vector2;

import edu.ouhk.student.cubescape.engine.Character;
import edu.ouhk.student.cubescape.engine.Scene;
import edu.ouhk.student.cubescape.engine.character.*;
import edu.ouhk.student.cubescape.engine.renderer.GLES20;
import edu.ouhk.student.cubescape.engine.scene.*;

public class SampleGameActivity extends AndroidApplication implements InputProcessor {
	private Character character;
	private Scene game;
	private Vector2 touchedAt;

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
		getInput().setInputProcessor(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean keyDown(int keyCode) {
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		touchedAt = new Vector2(screenX, screenY);
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(touchedAt.y != screenY && touchedAt.x != screenX)
			character.move(Math.atan2(screenY - touchedAt.y, screenX - touchedAt.x));
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		touchedAt = null;
		character.stand();
		return true;
	}
}
