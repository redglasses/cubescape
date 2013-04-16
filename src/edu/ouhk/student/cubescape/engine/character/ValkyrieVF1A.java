package edu.ouhk.student.cubescape.engine.character;

import edu.ouhk.student.cubescape.R;
import edu.ouhk.student.cubescape.engine.Character;
import edu.ouhk.student.cubescape.engine.Model;

public class ValkyrieVF1A extends Character {
	public ValkyrieVF1A() {
		super(R.raw.model_valkyrievf1a,R.drawable.texture_vf1a_hikaru);
		
		this.jumpMotion = new Model.Animation(new String[]{
			
		}, 12f);

		this.runMotion = new Model.Animation(new String[]{
			
		}, 12f);
		
		this.standMotion = new Model.Animation(new String[]{
			
				"taunt08"
		}, 12f);
	}
	@Override
	public void onMoved() {
		
	}

	@Override
	public boolean onDirectionChange(Direction from, Direction to) {
		
			switch(to){
				case LEFT:
				case UP_LEFT:
				case DOWN_LEFT:
					this.rotation.z = -60;
					break;
				case RIGHT:
				case UP_RIGHT:
				case DOWN_RIGHT:
					this.rotation.z = 60;
					break;
				default:
					this.rotation.z = 0;
				
			}
		
		
		return false;
	}
}
