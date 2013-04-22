package edu.ouhk.student.cubescape;

import java.util.Date;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

public class Application extends android.app.Application {
	public static final String DB_NAME = "CubescapeDb";
	public static final int DB_VERSION = 1;
	
	public static AndroidApplicationConfiguration GLConfig;
	static {
		GLConfig = new AndroidApplicationConfiguration();
		GLConfig.useWakelock = true;
		GLConfig.useGL20 = true;
		GLConfig.useAccelerometer = false;
		GLConfig.useCompass = false;
	}
	
	private static Context context;
	
	@Override
	public void onCreate() {
		super.onCreate();
		Application.context = getApplicationContext();
		
		ScoreBoard.initialize();
	}
	
	public static Context getContext() {
		return Application.context;
	}
	
	public static class ScoreBoard {
		public static final String TABLE_NAME = "scroe";
		
		public static final String DB_COL_ID = "_id";
		public static final String DB_COL_SCORE = "score";
		public static final String DB_COL_DATE = "date";
		
		public static class DbHelper extends SQLiteOpenHelper {
			public DbHelper() {
				super(Application.getContext(), Application.DB_NAME, null, Application.DB_VERSION);
			}

			@Override
			public void onCreate(SQLiteDatabase db) {
				db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + 
						" (" +
						DB_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
						DB_COL_SCORE + " NUMERIC," + 
						DB_COL_DATE + " TEXT);");
			}

			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
				onCreate(db);
			}
		}

		private static SQLiteDatabase db;

		public static void initialize() {
			db = new DbHelper().getWritableDatabase();
		}

		public static void addRecord(long score) {
			db.execSQL("INSERT INTO " + TABLE_NAME + " ("+DB_COL_SCORE+", "+DB_COL_DATE+") VALUES (?, ?);",
					new Object[]{score, new Date().toString()});
		}
		
		public static Cursor getCursor() {
			Cursor c = db.query(TABLE_NAME, new String[]{DB_COL_ID, DB_COL_SCORE, DB_COL_DATE}, null, null, null, null, DB_COL_SCORE + " DESC");
			if(c!=null)
				c.moveToFirst();
			return c;
		}
	}
	
	public static class Preferences {
		public static SharedPreferences get() {
			return PreferenceManager.getDefaultSharedPreferences(Application.getContext());
		}
	}
}
