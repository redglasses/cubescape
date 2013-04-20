package edu.ouhk.student.cubescape;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.math.Vector2;

import edu.ouhk.student.cubescape.engine.Factory;
import edu.ouhk.student.cubescape.engine.Scene;
import edu.ouhk.student.cubescape.engine.renderer.GLES20;
import edu.ouhk.student.cubescape.engine.scene.GameScene;

public class GameActivity extends AndroidApplication implements InputProcessor {
	private static final int pointerOffset = 2;
	
	private Scene game;
	private Vector2 touchedAt;
	private Music bgMusic;
	
	private static int score = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		((RelativeLayout)findViewById(R.id.game_view)).addView(
				initializeForView(new GLES20(game = new GameScene(){
					@Override
					public void onGameOver() {
						Log.d("Game","Over");
					}
					
					@Override
					public void onKill() {
						Log.d("Game","onKill");
						runOnUiThread(new Runnable(){
							@Override
							public void run() {
								addScore(3);
							}
						});
					}
					
					@Override
					public void onCharacterDead() {
						
					}
				}), Application.GLConfig));

		findViewById(R.id.txt_score).bringToFront();
		findViewById(R.id.txt_life_bar).bringToFront();
		
		((TextView)findViewById(R.id.txt_score)).setText(""+score);
		
		bgMusic = Gdx.audio.newMusic(files.internal("audio/imagine.mp3"));
		
		getInput().setInputProcessor(this);
	}
	
	public void addScore(int score) {
		this.score += score;
		((TextView)findViewById(R.id.txt_score)).setText(""+this.score);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		bgMusic.pause();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		bgMusic.play();
		Factory.cleanTextureCache();
	}
	
	@Override
	public void onBackPressed() {
		startActivity(new Intent(this, MainActivity.class));
		exit();
	}
	
	@Override
	public boolean keyDown(int keyCode) {
		return false;
	}

	@Override
	public boolean keyTyped(char key) {
		return false;
	}

	@Override
	public boolean keyUp(int keyCode) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int d) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		touchedAt = new Vector2(screenX, screenY);
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(touchedAt.y != screenY && touchedAt.x != screenX
				&& (Math.abs(screenY - touchedAt.y) > pointer * pointerOffset
						|| Math.abs(screenX - touchedAt.x) > pointer * pointerOffset))
			synchronized(game.getCharacter()){
				game.getCharacter().move(Math.atan2(screenY - touchedAt.y, screenX - touchedAt.x));
			}
		
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		touchedAt = null;
		game.getCharacter().stand();
		return true;
	}
}
