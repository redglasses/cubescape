package edu.ouhk.student.cubescape.engine.character;

import com.badlogic.gdx.Gdx;

import edu.ouhk.student.cubescape.R;
import edu.ouhk.student.cubescape.engine.ActiveObject;
import edu.ouhk.student.cubescape.engine.Character;
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
			
		}, 12f);
		
		this.standMotion = new Model.Animation(new String[]{
			
				"taunt08"
		}, 12f);
		movingAngle = 180;
	}
	
	public void shoot(Scene scene){
		timer += Gdx.graphics.getDeltaTime();
		if(timer>=bulletInterval) {
			timer = 0;
			for (ActiveObject bullet : generateBullets()){
				scene.addObjects(bullet);
				bulletShot++;
			}
		}
	}
	
	public ActiveObject[] generateBullets(){
		return new ActiveObject[]{
			new Bullet01().setPosition(this.position.x - (float)(Math.random()*3+4), this.position.y, this.position.z + 10), new Bullet01().setPosition(this.position.x + (float)(Math.random()*3+4), this.position.y, this.position.z + 10)	
		};
		
	}
	
	@Override
	public void onMoved() {
		
	}

	@Override
	public boolean onDirectionChange(Direction to) {
		
			switch(to){
				case LEFT:
					this.rotation.z = 60;
					break;
				case UP_LEFT:
				case DOWN_LEFT:
					this.rotation.z = 30;
					break;
				case RIGHT:
					this.rotation.z = -60;
					break;
				case UP_RIGHT:
				case DOWN_RIGHT:
					this.rotation.z = -30;
					break;
				default:
					this.rotation.z = 0;
				
			}
		
		
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
