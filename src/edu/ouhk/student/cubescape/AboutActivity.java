package edu.ouhk.student.cubescape;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

public class AboutActivity extends ListActivity {
	private class ListItemBuilder {
		private static final String LINE1 = "text1";
		private static final String LINE2 = "text2";
		public ArrayList <HashMap <String, String>> list;
		public ListItemBuilder() {
			list = new ArrayList <HashMap <String, String>>();
		}
		
		public void addItem(String line1, String line2) {
			HashMap <String, String> h = new HashMap <String, String>();
			h.put(LINE1, line1);
			h.put(LINE2, line2);
			list.add(h);
		}
		
		public String[] getCols() {
			return new String[] {LINE1, LINE2};
		}
		
		public int[] getColIds() {
			return new int[] {android.R.id.text1, android.R.id.text2};
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		ListItemBuilder listBuilder = new ListItemBuilder();
		listBuilder.addItem(getResources().getString(R.string.game_version), getResources().getString(R.string.app_version_name));
		listBuilder.addItem(getResources().getString(R.string.authors), getResources().getString(R.string.authors_name));
		listBuilder.addItem(getResources().getString(R.string.about_this_game), getResources().getString(R.string.game_readme));
		
		setListAdapter(new SimpleAdapter(this, listBuilder.list, android.R.layout.simple_list_item_2, listBuilder.getCols(), listBuilder.getColIds()));
	}
}
