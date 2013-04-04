package edu.ouhk.student.cubescape;

import android.content.Context;

public class Application extends android.app.Application {
	private static Context context;
	
	@Override
	public void onCreate() {
		super.onCreate();
		Application.context = getApplicationContext();
	}
	
	public static Context getContext() {
		return Application.context;
	}
}
