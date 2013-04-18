package edu.ouhk.student.cubescape.engine.character;

import edu.ouhk.student.cubescape.R;
import edu.ouhk.student.cubescape.engine.Character;
import edu.ouhk.student.cubescape.engine.Model;

public class Ogro extends Character {
	public static final float MOVING_STEP = 4f;
	
	public Ogro() {
		super(R.raw.model_ogro, R.drawable.texture_ogro);
		
		this.jumpMotion = new Model.Animation(new String[]{
			"jump_1", "jump_2", "jump_3", "jump_4", "jump_5", "jump_6"
		}, 12);

		this.runMotion = new Model.Animation(new String[]{
			"run_1", "run_2", "run_3", "run_4", "run_5", "run_6"
		}, 12);
		
		this.standMotion = new Model.Animation(new String[]{
			"stand_1","stand_2","stand_3","stand_4","stand_5",
			"stand_6","stand_7","stand_8","stand_9","stand_10",
			"stand_11","stand_12","stand_13","stand_14","stand_15",
			"stand_16","stand_17","stand_18","stand_19","stand_20",
			"stand_21","stand_22","stand_23","stand_24","stand_25",
			"stand_26","stand_27","stand_28","stand_29","stand_30",
			"stand_31","stand_32","stand_33","stand_34","stand_35",
			"stand_36","stand_37","stand_38","stand_39","stand_40"
		}, 12);
	}

	@Override
	public void onMoved() {
		
	}

	@Override
	public boolean onDirectionChange( Direction to) {
		return false;
	}
}
