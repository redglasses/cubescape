package edu.ouhk.student.cubescape.engine.util;

import com.badlogic.gdx.Gdx;

public class Timer {
	public float interval;
	public Runnable execution;
	private float timer;
	
	public Timer(float interval, Runnable execution) {
		this.interval= interval;
		this.execution = execution;
		timer = 0;
	}
	
	public void keepAlive() {
		timer += Gdx.graphics.getDeltaTime();
		if(timer>=interval) {
			reset();
			execution.run();
		}
	}
	
	public void reset() {
		timer = 0;
	}
}
