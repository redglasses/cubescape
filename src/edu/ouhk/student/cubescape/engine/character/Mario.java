package edu.ouhk.student.cubescape.engine.character;

import edu.ouhk.student.cubescape.R;
import edu.ouhk.student.cubescape.engine.Character;
import edu.ouhk.student.cubescape.engine.Model;

public class Mario extends Character {
	public Mario() {
		super(R.raw.model_mario, R.drawable.texture_mario);
		
		//this.movingStep = 1f;
		
		this.jumpMotion = new Model.Animation(new String[]{
			"jump1", "jump2", "jump3", "jump4", "jump5", "jump6"
		}, 12f);

		this.runMotion = new Model.Animation(new String[]{
			"run1", "run2", "run3", "run4", "run5", "run6"
		}, 12f);
		
		this.standMotion = new Model.Animation(new String[]{
			"stand1","stand2","stand3","stand4","stand5",
			"stand6","stand7","stand8","stand9","stand10",
			"stand11","stand12","stand13","stand14","stand15",
			"stand16","stand17","stand18","stand19","stand20",
			"stand21","stand22","stand23","stand24","stand25",
			"stand26","stand27","stand28","stand29","stand30",
			"stand31","stand32","stand33","stand34","stand35",
			"stand36","stand37","stand38","stand39","stand40"
		}, 12f);
	}

	@Override
	public void onMoved() {
		
	}

	@Override
	public boolean onDirectionChange(Direction to) {
		return false;
	}
}
