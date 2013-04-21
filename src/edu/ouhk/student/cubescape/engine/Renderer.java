package edu.ouhk.student.cubescape.engine;

import java.util.LinkedList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public abstract class Renderer implements ApplicationListener {
	public static interface RenderingListener {
		public void onCreate();
		public void onDispose();
		public void onPause();
		public void onResume();
		public void onResize(int width, int height);
		public void onPreRender();
		public void onRender(ShaderProgram program);
		public void onPostRender();
	}
	
	public static interface Renderable {
		public void create();
		public void dispose();
		public void render(ShaderProgram program);
	}

	public boolean isPaused = false;
	
	protected LinkedList<RenderingListener> renderingListeners;
	
	public Renderer() {
		this.renderingListeners = new LinkedList<RenderingListener>();
	}
	
	public Renderer(RenderingListener ...object) {
		this();
		for(RenderingListener o : object)
			renderingListeners.add(o);
	}

	public void addRenderingListener(RenderingListener object) {
		renderingListeners.add(object);
	}
	
	public void removeRenderingListener(RenderingListener object) {
		renderingListeners.remove(object);
	}

	@Override
	public void create() {
		resume();
		for(RenderingListener o : renderingListeners)
			o.onCreate();
	}

	@Override
	public void dispose() {
		for(RenderingListener o : renderingListeners)
			o.onDispose();
		
		Factory.cleanCache();
	}

	@Override
	public void pause() {
		isPaused = true;
		for(RenderingListener o : renderingListeners)
			o.onPause();
	}

	@Override
	public void resize(int width, int height) {
		for(RenderingListener o : renderingListeners)
			o.onResize(width, height);
	}

	@Override
	public void resume() {
		isPaused = false;
		for(RenderingListener o : renderingListeners)
			o.onResume();
	}
}
