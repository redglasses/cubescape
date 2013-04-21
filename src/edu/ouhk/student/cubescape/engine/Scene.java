package edu.ouhk.student.cubescape.engine;

import java.util.LinkedList;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import edu.ouhk.student.cubescape.engine.renderer.GLES20;

public abstract class Scene implements Renderer.RenderingListener {
	public static final float ANGLE_OF_VIEW = 67f;
	
	protected LinkedList<Object> objects;
	protected LinkedList<Object.CollisionListener> colliables;
	protected Vector<Object> removingObjects;
	protected Camera camera;
	protected Character character;
	
	private Object[] bufferRenderable;
	
	public Color backgroundColor;

	public Scene(Character character) {
		this.character = character;
		this.objects = new LinkedList<Object>();
		this.colliables = new LinkedList<Object.CollisionListener>();
		this.removingObjects = new Vector<Object>();
		this.backgroundColor = new Color(1f, 1f, 1f, 1f);
	}
	
	public Scene(Character character, Object ...object) {
		this(character);
		bufferRenderable = object;
	}
	
	public void addObjects(Object ...object) {
		for(Object o : object){
			objects.add(o);
			o.create();
			if (o instanceof Object.CollisionListener)
				colliables.add((Object.CollisionListener)o);
		}
	}
	
	public void removeObjects(Object ...object) {
		for(Object o : object) {
			removingObjects.add(o);
			if(o instanceof Object.CollisionListener && colliables.contains(o))
				colliables.remove(o);
		}
	}

	public Character getCharacter() {
		return this.character;
	}

	public LinkedList<Object> getObjects() {
		return this.objects;
	}

	@Override
	public void onCreate() {
		this.camera = new PerspectiveCamera(ANGLE_OF_VIEW, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		this.camera.far = Float.MAX_VALUE / 2;
		this.camera.near = 1f;
		addObjects(this.character);
		if (bufferRenderable != null){
			addObjects(bufferRenderable);
		}
	}
	
	@Override
	public void onDispose() {
		for(Renderer.Renderable o : objects)
			o.dispose();
	}
	
	@Override
	public void onPause() {
		
	}
	
	@Override
	public void onResume() {
		
	}
	
	@Override
	public void onResize(int width, int height) {
		
	}

	@Override
	public void onPreRender() {
		Gdx.gl20.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
		
		for(Object o : removingObjects)
			objects.remove(o);
		removingObjects.clear();
	}
	
	@Override
	public void onRender(ShaderProgram program) {
		program.setUniformMatrix(GLES20.PROJECTION_ATTRIBUTE, camera.combined);

		for(Object o : objects)
			o.render(program);
	}
	
	@Override
	public void onPostRender() {
		// Collision detection
		for(Object o : objects)
			for(Object.CollisionListener c : colliables)
				if (o != c && o.isOverlaid((Object)c))
					c.onCollided(o);
	}
}
