package edu.ouhk.student.cubescape.engine;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.materials.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.graphics.g3d.materials.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.model.keyframe.KeyframedModel;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

import edu.ouhk.student.cubescape.util.StringArrayIterator;

public class Model {
	public static interface FrameChangeListener {
		public void onFrameChange();
	}
	
	public static class Animation {
		public String[] frames;
		public float fps;
		public Animation(String[] frames, float fps) {
			this.frames = frames;
			this.fps = fps;
		}
	}
	
	private StringArrayIterator frameIterator;
	private float timeInterval = 1 / 12f;
	private float timer = 0;
	private KeyframedModel model;
	private LinkedList<FrameChangeListener> frameChangeListeners;
	private BoundingBox box;
	
	protected Model(KeyframedModel model) {
		if(model.getAnimations().length>1) {
			String[] frames = new String[model.getAnimations().length];
			for(int i=0;i<frames.length;i++)
				frames[i] = model.getAnimations()[i].name;
			
			frameIterator = new StringArrayIterator(frames, true);
			frameChangeListeners = new LinkedList<FrameChangeListener>();
		}
		this.model = model;
	}
	
	public void bindMaterial(int textureId) {
		model.setMaterial(new Material("default",
				new TextureAttribute(Factory.loadTexture(textureId), 0, Object.TEXTURE_ATTRIBUTE),
				new ColorAttribute(new Color(1, 1, 1, 1), Object.COLOR_ATTRIBUTE)));
	}
	
	public void bindMaterial(Color color) {
		model.setMaterial(new Material("default", new ColorAttribute(color, Object.COLOR_ATTRIBUTE)));
	}
	
	public void bindMaterial(int textureId, Color color) {
		model.setMaterial(new Material("default",
				new TextureAttribute(Factory.loadTexture(textureId), 0, Object.TEXTURE_ATTRIBUTE),
				new ColorAttribute(color, Object.COLOR_ATTRIBUTE)));
	}
	
	public BoundingBox getBoundingBox() {
		if(this.box==null)
			this.model.getBoundingBox(this.box);
		return this.box;
	}
	
	public void addFrameChangeListener(FrameChangeListener object) {
		if(frameChangeListeners!=null)
			frameChangeListeners.add(object);
	}
	
	public void removeFrameChangeListener(FrameChangeListener object) {
		if(frameChangeListeners!=null)
			frameChangeListeners.remove(object);
	}
	
	public void playAnimtion(Animation animation) {
		if(model.getAnimations().length>1) {
			frameIterator = new StringArrayIterator(animation.frames);
			timeInterval = 1 / animation.fps;
			timer = 0;
		}
	}
	
	public void update() {
		if(frameIterator==null) {
			model.setAnimation(model.getAnimations()[0].name, 0, false);
		} else {
			timer += Gdx.graphics.getDeltaTime();
			if(timer>timeInterval) {
				timer = 0;
				model.setAnimation(frameIterator.next(), 0, false);
				for(FrameChangeListener o: frameChangeListeners)
					o.onFrameChange();
			}
		}
	}

	public void render(ShaderProgram program) {
		model.render(program);
	}
}
