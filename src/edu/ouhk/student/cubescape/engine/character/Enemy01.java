package edu.ouhk.student.cubescape.engine.character;

import edu.ouhk.student.cubescape.R;
import edu.ouhk.student.cubescape.engine.ActiveObject;
import edu.ouhk.student.cubescape.engine.Character;
import edu.ouhk.student.cubescape.engine.Model;
import edu.ouhk.student.cubescape.engine.Scene;

public class Enemy01 extends Character {
	
	public Enemy01() {
		
		super(R.raw.model_mario,R.drawable.texture_mario);
		
		this.jumpMotion = new Model.Animation(new String[]{
			
		}, 12f);

		this.runMotion = new Model.Animation(new String[]{
			
		}, 12f);
		
		this.standMotion = new Model.Animation(new String[]{
			"run1", "run2", "run3", "run4", "run5", "run6"
		}, 12f);
		this.position.x = 0f;
		this.position.z = -300f;
		
		
		this.setEnemy(true);
	}
	
	public void shoot(Scene scene){
		for (ActiveObject bullet : generateBullets()){
			scene.addObjects(bullet);
		}
	
	}
	public ActiveObject[] generateBullets(){
		return new ActiveObject[0];
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
		//this.move(this.movingAngle);
	}

}
