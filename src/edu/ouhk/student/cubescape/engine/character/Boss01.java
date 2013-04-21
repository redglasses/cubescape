package edu.ouhk.student.cubescape.engine.character;

import com.badlogic.gdx.Gdx;

import edu.ouhk.student.cubescape.R;
import edu.ouhk.student.cubescape.engine.ActiveObject;
import edu.ouhk.student.cubescape.engine.Bullet;
import edu.ouhk.student.cubescape.engine.Character;
import edu.ouhk.student.cubescape.engine.Model;
import edu.ouhk.student.cubescape.engine.Scene;

public class Boss01 extends Character {
	
	private boolean entered(){
		return this.position.z >= -300f;
	}
	public Boss01() {
		
		super(R.raw.model_spider,R.drawable.texture_spider);
		
		this.jumpMotion = new Model.Animation(new String[]{
			
		}, 12f);

		this.runMotion = new Model.Animation(new String[]{
			
		}, 12f);
		
		this.standMotion = new Model.Animation(new String[]{
			"run01", "run02", "run03", "run04", "run05", "run06"
		}, 24f);
		//this.position.x = (float)(Math.random() * 500 - 250);
		this.position.z = -350f;
		this.score_bonus = 30;
		this.radius = 50f;
		this.hitPoint = 1000;
		this.setEnemy(true);
		this.bulletInterval = 6f;
	}
	
	/*public void shoot(Scene scene){
		timer += Gdx.graphics.getDeltaTime();
		if(timer>=bulletInterval) {
			timer = 0;
			for (ActiveObject bullet : generateBullets()){
				bullet.setEnemy(true);
				((Bullet)bullet).move(this.movingAngle);
				bullet.rotation.x = 1;
				bullet.rotation.y = (float)((this.movingAngle + Math.PI + Math.PI/2) * 180 / Math.PI * -1);
				scene.addObjects(bullet);
				
				bulletShot++;
			}
		}
	
	}*/
	private float bulletX(double angle){
		return (float)(10 * Math.sin(angle));
	}
	private float bulletZ(double angle){
		return (float)(10 * Math.cos(angle));
	}
	public int noOfBullets = 5; 
	public ActiveObject[] generateBullets(){
		ActiveObject[] bullets = new ActiveObject[noOfBullets];
		double angleInterval = Math.PI/(noOfBullets+2);
		double randomAngle;
		for (int i = 0; i < noOfBullets; i++){
			randomAngle = angleInterval*i + angleInterval/2 + Math.random() * angleInterval;
			bullets[i] = new Bullet02().setEnemy(true).setPosition(
					this.position.x + bulletX(randomAngle), 
					this.position.y - 5, 
					this.position.z + bulletZ(randomAngle)
					);
			((Bullet)bullets[i]).move(randomAngle);
		}
		return bullets;

	}

	

	@Override
	public boolean onDirectionChange(Direction to) {
		
		return false;
	}
	@Override
	public void create(){
		super.create();
		this.stand();
		this.scale.x = this.scale.y = this.scale.z = 5;
		move(Math.PI/2);
	}
	public void onMoved(){
		super.onMoved();
		//this.rotation.y = (float)((this.movingAngle + Math.PI + Math.PI/2) * 180 / Math.PI * -1);
		//this.move(this.movingAngle);
	}
	private boolean outsided = false;
	private boolean firstEntered = false;
	@Override
	public void onFrameChange() {
		super.onFrameChange();
		if (!firstEntered){
			if (entered()){
				firstEntered = true;
				movingAngle = Math.random() * Math.PI ;
			}
		}else{
			if (this.position.z > 0 || this.position.z < -300){
				this.position.z -= movingStep * Math.sin(movingAngle);
				outsided = true;
			}
			if (Math.abs(this.position.x) > 200){
				position.x -= movingStep * Math.cos(movingAngle);
				outsided = true;
			}
			if (outsided){
				movingAngle = Math.random() * Math.PI * 2 - Math.PI;
				outsided = false;
			}
		}
		
	}

}
