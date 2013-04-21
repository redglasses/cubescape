package edu.ouhk.student.cubescape;

import com.badlogic.gdx.backends.android.AndroidApplication;

import edu.ouhk.student.cubescape.engine.Factory;
import edu.ouhk.student.cubescape.engine.renderer.GLES20;
import edu.ouhk.student.cubescape.engine.scene.GameOverScene;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class GameOverActivity extends AndroidApplication implements android.view.View.OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gameover);
		
		FrameLayout l = (FrameLayout)findViewById(R.id.gameover_top);
		l.addView(initializeForView(new GLES20(new GameOverScene()), Application.GLConfig));
		
		((TextView)findViewById(R.id.txt_gameisover)).setText(
				String.format(getResources().getString(R.string.game_is_over),
						getIntent().getIntExtra(Application.ScoreBoard.DB_COL_SCORE, 0)));
		
		findViewById(R.id.btn_gameover_retry).setOnClickListener(this);
		findViewById(R.id.btn_gameover_quit).setOnClickListener(this);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Factory.cleanTextureCache();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_gameover_retry:
			startActivity(new Intent(this, GameActivity.class));
			overridePendingTransition(R.anim.fadein,R.anim.fadeout);
			exit();
			break;
		case R.id.btn_gameover_quit:
			startActivity(new Intent(this, MainActivity.class));
			exit();
			break;
		}
	}
}
