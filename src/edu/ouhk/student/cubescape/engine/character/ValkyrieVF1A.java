package edu.ouhk.student.cubescape.engine.character;

import com.badlogic.gdx.Gdx;

import edu.ouhk.student.cubescape.R;
import edu.ouhk.student.cubescape.engine.ActiveObject;
import edu.ouhk.student.cubescape.engine.Character;
import edu.ouhk.student.cubescape.engine.Bullet;
import edu.ouhk.student.cubescape.engine.Model;
import edu.ouhk.student.cubescape.engine.Scene;

public class ValkyrieVF1A extends Character {
	public float bulletInterval = 0.1f;
	public long bulletShot = 0;
	
	public float timer = 0;
	
	public ValkyrieVF1A() {
		
		super(R.raw.model_valkyrievf1a,R.drawable.texture_vf1a_hikaru);
		
		this.jumpMotion = new Model.Animation(new String[]{
			
		}, 12f);

		this.runMotion = new Model.Animation(new String[]{
			"crstand01"
		}, 12f);
		
		this.standMotion = new Model.Animation(new String[]{
			
				"taunt08"
		}, 12f);
		movingAngle = 180;
		this.radius = 2.5f;
	}
	
	public void shoot(Scene scene){
		timer += Gdx.graphics.getDeltaTime();
		if(timer>=bulletInterval) {
			timer = 0;
			for (ActiveObject bullet : generateBullets()){
				((Bullet)bullet).move(0-Math.PI/2);
				scene.addObjects(bullet);
				bulletShot++;
			}
		}
	}
	
	public ActiveObject[] generateBullets(){
		return new ActiveObject[]{
			new Bullet01().setPosition(this.position.x - (float)(Math.random()*3+2), 
					this.position.y - 5, 
					(float)(this.position.z - Math.random() * 10 - 20)), 
			new Bullet01().setPosition(this.position.x + (float)(Math.random()*3+2), 
					this.position.y - 5, 
					(float)(this.position.z - Math.random() * 10 - 20))	
		};
		
	}
	
	@Override
	public void onMoved() {
		
	}

	@Override
	public boolean onDirectionChange(Direction to) {
			switch(this.direction){
			case LEFT:
			case RIGHT:
			case UP_LEFT:
			case UP_RIGHT:
			case UP:
				switch(to){
				case DOWN:
				case DOWN_LEFT:
				case DOWN_RIGHT:
					this.model.playAnimtion(runMotion);
					break;
				}
				break;
			case DOWN:
			case DOWN_LEFT:
			case DOWN_RIGHT:
				switch(to){
				case LEFT:
				case RIGHT:
				case UP_LEFT:
				case UP_RIGHT:
				case UP:
					this.model.playAnimtion(standMotion);
					break;
				}
				break;
			}
			switch(to){
				case LEFT:
					this.rotation.z = -60;
					break;
				case UP_LEFT:
					this.rotation.z = -30;
					break;
				case RIGHT:
					this.rotation.z = 60;
					break;
				case UP_RIGHT:
					this.rotation.z = 30;
					break;
				case DOWN_LEFT:
				case DOWN_RIGHT:
				case DOWN:
					this.rotation.z = 0;
					break;

			}
		
		this.direction = to;
		return false;
	}
	@Override
	public void create() {
		super.create();
		this.radius = 15f;
		this.movingStep = 10f;
		this.rotation.y = 180f;
		//this.move(Direction.LEFT);
		this.stand();
	}
	public void stand(){
		super.stand();
		this.rotation.z = 0;
	}

}
