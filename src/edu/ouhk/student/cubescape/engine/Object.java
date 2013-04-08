package edu.ouhk.student.cubescape.engine;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.materials.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.graphics.g3d.materials.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.model.keyframe.KeyframedModel;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Sphere;

import edu.ouhk.student.cubescape.engine.renderer.GLES20;

public abstract class Object implements Renderer.Renderable {
	public static final String TEXTURE_ATTRIBUTE = "u_texture";
	public static final String COLOR_ATTRIBUTE = "u_color";
	
	public static interface CollisionListener {
		public void onCollided(Object object);
	}

	public float radius = 1f;
	public Vector3 scale, position, rotation;
	public boolean isVisible = true;
	public boolean isBlockable = false;
	
	protected Model model;
	protected Vector<Renderer.Renderable> childs;
	
	private int modelId;
	private int textureId;
	private Color color;

	public Object(int modelId) {
		this.modelId = modelId;
		this.childs = new Vector<Renderer.Renderable>();
		
		this.scale = new Vector3(1, 1, 1);
		this.position = new Vector3(0, 0, 0);
		this.rotation = new Vector3(0, 0, 0);
	}
	
	public Object(int modelId, int textureId) {
		this(modelId);
		this.textureId = textureId;
	}
	
	public Object(int modelId, Color color) {
		this(modelId);
		this.color = color;
	}
	
	public void addChild(Object obj) {
		childs.add(obj);
	}
	
	public void removeChild(Object obj) {
		childs.remove(obj);
	}
	
	public boolean isOverlaid(Object object) {
		return getSphere().overlaps(object.getSphere());
	}
	
	public Sphere getSphere() {
		return new Sphere(this.model.getBoundingBox().getCenter().add(this.position), radius);
	}
	
	@Override
	public void create() {
		KeyframedModel model = Factory.loadModel(modelId);
		if(color==null){
			model.setMaterial(new Material("default",
					new TextureAttribute(Factory.loadTexture(textureId), 0, TEXTURE_ATTRIBUTE),
					new ColorAttribute(new Color(1, 1, 1, 1), COLOR_ATTRIBUTE)));
		} else
			model.setMaterial(new Material("default", new ColorAttribute(color, COLOR_ATTRIBUTE)));
		
		this.model = new Model(model);
		
		for(Renderer.Renderable o : childs)
			((Object)o).create();
	}

	@Override
	public void dispose() {
		for(Renderer.Renderable o : childs)
			((Object)o).dispose();
	}

	@Override
	public void render(ShaderProgram program) {
		if(isVisible){
			Gdx.gl20.glFrontFace(GL20.GL_CW);
			Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);

			Matrix4 translation = new Matrix4();
			translation.setToTranslationAndScaling(position.x, position.y, position.z, scale.x, scale.y, scale.z);
			program.setUniformMatrix(GLES20.MODEL_TRANSLATION_ATTRIBUTE, translation);
			
			Matrix4 rotationMatrix = new Matrix4();
			rotationMatrix.setToRotation(1, 0, 0, rotation.x);
			rotationMatrix.rotate(0, 1, 0, rotation.y);
			rotationMatrix.rotate(0, 0, 1, rotation.z);

			program.setUniformMatrix(GLES20.MODEL_ROTATION_ATTRIBUTE, rotationMatrix);

			model.render(program);
			
			Gdx.gl20.glDisable(GL20.GL_TEXTURE_2D);

			for(Renderer.Renderable o : childs) {
				o.render(program);
			}
		}		
	}
}
