package edu.ouhk.student.cubescape.engine;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import edu.ouhk.student.cubescape.engine.renderer.GLES20;

public abstract class Scene implements Renderer.RenderingListener {
	public static final float ANGLE_OF_VIEW = 67f;
	
	protected LinkedList<Renderer.Renderable> objects;
	protected Camera camera;
	protected Character character;
	private Renderer.Renderable[] bufferRenderable = null;
	public Color backgroundColor;

	public Scene(Character character) {
		this.character = character;
		this.objects = new LinkedList<Renderer.Renderable>();
		
		
		
		this.backgroundColor = new Color(1f, 1f, 1f, 1f);
	}
	
	public Scene(Character character, Renderer.Renderable ...object) {
		this(character);
		bufferRenderable = object;
		
	}
	
	public void addObjects(Renderer.Renderable ...object) {
		for(Renderer.Renderable o : object){
			this.objects.add(o);
			o.create();
		}
			
		
	}

			

	
	public LinkedList<Renderer.Renderable> getObjects() {
		return this.objects;
	}

	@Override
	public void onCreate() {
		this.camera = new PerspectiveCamera(ANGLE_OF_VIEW, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.camera.translate(0, 500, 0);
		this.camera.rotate(-90, 1, 0, 0);
		this.camera.far = 1000f;
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
	}
	
	@Override
	public void onRender(ShaderProgram program) {
		program.setUniformMatrix(GLES20.PROJECTION_ATTRIBUTE, camera.combined);
		
		for(Renderer.Renderable o : objects)
			o.render(program);
	}
	
	@Override
	public void onPostRender() {
		
	}
}
