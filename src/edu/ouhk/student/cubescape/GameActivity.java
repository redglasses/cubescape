package edu.ouhk.student.cubescape;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.math.Vector2;

import edu.ouhk.student.cubescape.engine.Factory;
import edu.ouhk.student.cubescape.engine.Renderer;
import edu.ouhk.student.cubescape.engine.Scene;
import edu.ouhk.student.cubescape.engine.renderer.GLES20;
import edu.ouhk.student.cubescape.engine.scene.*;

public class GameActivity extends AndroidApplication implements InputProcessor {
	private static final int pointerOffset = 2;
	
	private Renderer renderer;
	private Scene game;
	private Vector2 touchedAt;
	private Music bgMusic;
	
	private int score = 0;
	private int life = 3;
	
	public boolean isEnableMusic() {
		return Application.Preferences.get().getBoolean(getResources().getString(R.string.pref_music_id), true);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		((RelativeLayout)findViewById(R.id.game_view)).addView(
				initializeForView(renderer = new GLES20(game = new StageGameScene(){
					@Override
					public void onEnemyKilled(final int score) {
						runOnUiThread(new Runnable(){
							@Override
							public void run() {
								addScore(score);
							}
						});
					}
					
					@Override
					public void onCharacterDead() {
						runOnUiThread(new Runnable(){
							@Override
							public void run() {
								dead();
							}
						});
					}
				}), Application.GLConfig));

		findViewById(R.id.txt_score).bringToFront();
		findViewById(R.id.txt_life_bar).bringToFront();
		
		((TextView)findViewById(R.id.txt_score)).setText(""+score);
		((RatingBar)findViewById(R.id.txt_life_bar)).setRating(life);
		
		bgMusic = Gdx.audio.newMusic(files.internal("audio/imagine.mp3"));
		bgMusic.setLooping(true);
		bgMusic.setVolume(0);
		bgMusic.play();
		
		getInput().setInputProcessor(this);
	}
	
	public void addScore(int score) {
		this.score += score;
		((TextView)findViewById(R.id.txt_score)).setText(""+this.score);
	}
	
	public void dead() {
		this.life--;
		((RatingBar)findViewById(R.id.txt_life_bar)).setRating(life);
		
		if (life == 0)
			gameOver();
	}
	
	public void gameOver() {
		Application.ScoreBoard.addRecord(score);
		Intent i = new Intent(this, GameOverActivity.class);
		i.putExtra(Application.ScoreBoard.DB_COL_SCORE, score);
		startActivity(i);
		exit();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		bgMusic.setVolume(0);
		renderer.pause();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Factory.cleanTextureCache();
		if (isEnableMusic()) bgMusic.setVolume(1);
		renderer.resume();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		bgMusic.setVolume(0);
		renderer.pause();
	}
	
	@Override
	public void onRestart() {
		super.onRestart();
		if (isEnableMusic()) bgMusic.setVolume(1);
		renderer.resume();
	}
	
	
	@Override
	public void onBackPressed() {
		/* Do nothing here */
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.game_option_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.btn_menu_quit:
			startActivity(new Intent(this, MainActivity.class));
			exit();
			break;
		case R.id.btn_game_options:
			startActivity(new Intent(this, SettingsActivity.class));
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		bgMusic.setVolume(0);
		renderer.pause();
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public void onOptionsMenuClosed(Menu menu) {
		super.onOptionsMenuClosed(menu);
		if (isEnableMusic()) bgMusic.setVolume(1);
		renderer.resume();
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
		return true;
	}

	@Override
	public boolean scrolled(int d) {
		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(touchedAt==null)
			touchedAt = new Vector2(screenX, screenY);
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(touchedAt!=null){
			if(touchedAt.y != screenY && touchedAt.x != screenX
					&& Math.abs(screenY - touchedAt.y) > pointer * pointerOffset
					&& Math.abs(screenX - touchedAt.x) > pointer * pointerOffset)
				synchronized(game.getCharacter()){
					game.getCharacter().move(Math.atan2(screenY - touchedAt.y, screenX - touchedAt.x));
				}
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(touchedAt!=null){
			touchedAt = null;
			game.getCharacter().stand();
		}
		return true;
	}
}
