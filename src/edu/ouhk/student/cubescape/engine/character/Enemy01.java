package edu.ouhk.student.cubescape.engine.character;

import com.badlogic.gdx.Gdx;

import edu.ouhk.student.cubescape.R;
import edu.ouhk.student.cubescape.engine.ActiveObject;
import edu.ouhk.student.cubescape.engine.Bullet;
import edu.ouhk.student.cubescape.engine.Character;
import edu.ouhk.student.cubescape.engine.Model;
import edu.ouhk.student.cubescape.engine.Scene;

public class Enemy01 extends Character {
	
	public float bulletInterval = 3f;
	public long bulletShot = 0;
	
	public float timer = 0;
	public Enemy01() {
		
		super(R.raw.model_mario,R.drawable.texture_mario);
		
		this.jumpMotion = new Model.Animation(new String[]{
			
		}, 12f);

		this.runMotion = new Model.Animation(new String[]{
			
		}, 12f);
		
		this.standMotion = new Model.Animation(new String[]{
			"run1", "run2", "run3", "run4", "run5", "run6"
		}, 12f);
		this.position.x = (float)(Math.random() * 500 - 250);
		this.position.z = -350f;
		
		this.hitPoint = 100;
		this.setEnemy(true);
	}
	
	public void shoot(Scene scene){
		timer += Gdx.graphics.getDeltaTime();
		System.out.println(timer);
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
	
	}
	public ActiveObject[] generateBullets(){
		float x = (float)(10 * Math.sin(this.movingAngle));
		float z = (float)(10 * Math.cos(this.movingAngle));
		return new ActiveObject[]{
				new Bullet01().setPosition(this.position.x + x, this.position.y - 5, this.position.z + z)
		};
	}

	

	@Override
	public boolean onDirectionChange(Direction to) {
		
		return false;
	}
	@Override
	public void create(){
		super.create();
		this.stand();
		move(Math.random() * Math.PI);
	}
	public void onMoved(){
		super.onMoved();
		this.rotation.y = (float)((this.movingAngle + Math.PI + Math.PI/2) * 180 / Math.PI * -1);
		//this.move(this.movingAngle);
	}

}
