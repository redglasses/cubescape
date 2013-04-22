package edu.ouhk.student.cubescape;

import java.util.LinkedHashMap;

import com.badlogic.gdx.backends.android.AndroidApplication;

import edu.ouhk.student.cubescape.engine.Factory;
import edu.ouhk.student.cubescape.engine.renderer.GLES20;
import edu.ouhk.student.cubescape.engine.scene.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

public class MainActivity extends AndroidApplication {
	private class MenuActionManager implements OnItemClickListener{
		private LinkedHashMap<String, Runnable> items;
		public MenuActionManager(int itemCount) {
			items = new LinkedHashMap<String, Runnable>(itemCount);
		}
		
		public void add(int textId, Runnable r) {
			items.put(getResources().getString(textId), r);
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View v, int pos,
				long id) {
			((Runnable)items.values().toArray()[pos]).run();
		}
		
		public String[] getActionString() {
			return items.keySet().toArray(new String[items.size()]);
		}
	}

	private AndroidApplication self;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		self = this;
		setContentView(R.layout.activity_main);
		
		MenuActionManager menu = new MenuActionManager(4);
		menu.add(R.string.new_game, new Runnable(){
			@Override
			public void run() {
				startActivity(new Intent(self, GameActivity.class));
				overridePendingTransition(R.anim.fadein,R.anim.fadeout);
				exit();
			}
		});
		menu.add(R.string.score_board, new Runnable(){
			@Override
			public void run() {
				startActivity(new Intent(self, ScoreBoardActivity.class));
			}
		});
		menu.add(R.string.options, new Runnable(){
			@Override
			public void run() {
				startActivity(new Intent(self, SettingsActivity.class));
			}
		});
		menu.add(R.string.about, new Runnable(){
			@Override
			public void run() {
				startActivity(new Intent(self, AboutActivity.class));
			}
		});
		
		ListView lv = (ListView) findViewById(R.id.main_list_view);
		lv.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                menu.getActionString()));
		lv.setOnItemClickListener(menu);

		FrameLayout l = (FrameLayout)findViewById(R.id.main_top_view);
		l.addView(initializeForView(new GLES20(new MainMenuScene()), Application.GLConfig));
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Factory.cleanTextureCache();
	}
}
