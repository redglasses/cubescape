package edu.ouhk.student.cubescape.engine.character;

import edu.ouhk.student.cubescape.R;
import edu.ouhk.student.cubescape.engine.ActiveObject;
import edu.ouhk.student.cubescape.engine.Bullet;

import edu.ouhk.student.cubescape.engine.Model;
import edu.ouhk.student.cubescape.engine.Scene;

public class Bullet01 extends Bullet {
	
	public Bullet01() {
		super(R.raw.model_box,R.drawable.texture_crate2);
		
		this.jumpMotion = new Model.Animation(new String[]{
			
		}, 12f);

		this.runMotion = new Model.Animation(new String[]{
			
		}, 12f);
		
		this.standMotion = new Model.Animation(new String[]{
			
				"frame01",
		}, 24f);
		this.radius = 3;
		this.movingStep = 40f;
		
		this.scale.x = this.scale.y = .001f;
		this.scale.z = .02f;
	}
	public ActiveObject setPosition(float x, float y, float z){
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
		return this;
	}

	@Override
	public void onMoved() {
		super.onMoved();
		
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
		/*this.scale.x = 0.1f;
		this.scale.y = 0.1f;
		this.scale.z = 0.5f;*/

		//this.rotation.y = 180f;
		//this.move(Direction.LEFT);
		//this.stand();
		//this.rotation.z = 0;
		
	}
	public void stand(){
		//super.stand();
		this.rotation.z = 0;
	}
}
