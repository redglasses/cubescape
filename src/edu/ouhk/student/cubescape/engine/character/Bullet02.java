package edu.ouhk.student.cubescape.engine.character;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import edu.ouhk.student.cubescape.R;
import edu.ouhk.student.cubescape.engine.ActiveObject;
import edu.ouhk.student.cubescape.engine.Bullet;

import edu.ouhk.student.cubescape.engine.Model;
import edu.ouhk.student.cubescape.engine.Scene;

public class Bullet02 extends Bullet {
	private Color color = new Color(0,0,0,0);
	public Bullet02() {
		super(R.raw.model_ufo,R.drawable.texture_ufo);
		this.jumpMotion = new Model.Animation(new String[]{
			
		}, 12f);

		this.runMotion = new Model.Animation(new String[]{
			
		}, 12f);
		
		this.standMotion = new Model.Animation(new String[]{
			
				"run1"//,"run2","run3","run4","run5","run6"
		}, 12f);
		this.radius = 1;
		this.scale.x = this.scale.y = this.scale.z = 0.2f;
	}
	

	@Override
	public void onMoved() {
		super.onMoved();
		
	}

	@Override
	public boolean onDirectionChange(Direction to) {
		
			/*switch(to){
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
				
			}*/
		
		
		return false;
	}
	@Override
	public void create() {
		super.create();
		/*this.scale.x = 0.1f;
		this.scale.y = 0.1f;
		this.scale.z = 0.5f;*/
		this.movingStep = 10f;
		this.move(this.movingAngle);
		//this.scale.x = this.scale.y = this.scale.z = .001f;
		//this.scale.z = .006f;
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
