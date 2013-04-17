package edu.ouhk.student.cubescape.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public abstract class ActiveObject extends Object implements Object.CollisionListener{

	protected boolean isEnemy = false;
	protected int hitPoint = 1, attack = 0;
	
	public boolean isDied(){
		return hitPoint <= 0;
	}
	public boolean setEnemy(boolean isEnemy){
		this.isEnemy = isEnemy;
		return this.isEnemy;
	}
	public boolean isEnemy(){
		return this.isEnemy;
	}
	public ActiveObject(int modelId) {
		super(modelId);
		// TODO Auto-generated constructor stub
	}
	public ActiveObject(int modelId, int textureId) {
		super(modelId, textureId);
		// TODO Auto-generated constructor stub
	}
	public ActiveObject(int modelId, Color color) {
		super(modelId, color);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void create() {
		super.create();
	}
	@Override
	public void dispose() {
		super.dispose();
	}
	@Override
	public void render(ShaderProgram program) {
		super.render(program);
	}
	public void onCollided(Object object){
		if (object instanceof ActiveObject){
			this.hitPoint -= ((ActiveObject) object).attack;
			((ActiveObject) object).hitPoint -= this.attack;
		}
	
	}
	
	public void destroy(){
		//call when the object is destroyed due to enemy's attack
	}

}
