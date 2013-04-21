package edu.ouhk.student.cubescape.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public abstract class ActiveObject extends Object{

	public float bulletInterval = 0f;
	protected long bulletShot = 0;
	protected float timer = 0;
	protected Character character;
	protected boolean isHoming = false;
	protected double movingAngle;
	protected double maxAngle = 0;
	protected boolean isEnemy = false;
	
	public int maxHitPoint = 1, hitPoint = 1, attackPower = 0;
	
	public boolean isDead(){
		return hitPoint <= 0;
	}
	public ActiveObject setPosition(float x, float y, float z){
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
		return this;
	}
	public ActiveObject setEnemy(boolean isEnemy){
		this.isEnemy = isEnemy;
		return this;
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
	
	public void update() {
		model.update();
	}
	public void destroy(){
		//call when the object is destroyed due to enemy's attack
	}
	public void onMoved() {
		double angle;
		if (isHoming){
			synchronized (this.character){
				angle = Math.atan2(((Character)character).position.z - this.position.z, ((Character)character).position.x - this.position.x) + Math.PI;

			}
			double mAngle = movingAngle + Math.PI;
			
			if (angle - mAngle > maxAngle){
				if (angle - mAngle <= Math.PI){
					movingAngle = (mAngle + maxAngle) % (Math.PI + Math.PI) - Math.PI;
				}else{
					movingAngle = (mAngle - maxAngle) % (Math.PI + Math.PI) - Math.PI;
				}
			}else if (mAngle - angle > maxAngle){
				if (mAngle - angle <= Math.PI){
					movingAngle = (mAngle - maxAngle) % (Math.PI + Math.PI) - Math.PI;
				}else{
					movingAngle = (mAngle + maxAngle) % (Math.PI + Math.PI) - Math.PI;
				}
			}else{
				movingAngle = angle - Math.PI;
			}
		}
		
	}
	public Object setHoming(Character character, double maxAngle){
		this.isHoming = true;
		this.character = character;
		this.maxAngle = maxAngle;
		return this;
	}

	
	public void shoot(Scene scene){
		timer += Gdx.graphics.getDeltaTime();
		if(timer>=bulletInterval) {
			timer = 0;
			for (ActiveObject bullet : generateBullets()){
				//bullet.setEnemy(true);
				//((Bullet)bullet).move(bullet.movingAngle);
				
				scene.addObjects(bullet);
				
				bulletShot++;
			}
		}
	}
	public ActiveObject[] generateBullets(){
		return new ActiveObject[0];
	}
}
