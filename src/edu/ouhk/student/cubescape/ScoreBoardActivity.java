package edu.ouhk.student.cubescape;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class ScoreBoardActivity extends ListActivity {
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scoreboard);
		
		Cursor c = Application.ScoreBoard.getCursor();
		startManagingCursor(c);
		setListAdapter(new SimpleCursorAdapter(
				this,
				android.R.layout.two_line_list_item,
				c,
                new String[] {Application.ScoreBoard.DB_COL_SCORE, Application.ScoreBoard.DB_COL_DATE},
                new int[] {android.R.id.text1, android.R.id.text2}));
	}
}
