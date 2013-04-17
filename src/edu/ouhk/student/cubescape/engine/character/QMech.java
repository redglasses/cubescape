package edu.ouhk.student.cubescape.engine.character;

import edu.ouhk.student.cubescape.R;
import edu.ouhk.student.cubescape.engine.Character;
import edu.ouhk.student.cubescape.engine.Model;

public class QMech extends Character {
	public QMech() {
		super(R.raw.model_qmech, R.drawable.texture_qmech);

		this.jumpMotion = new Model.Animation(new String[]{
			"jump01", "jump02", "jump03", "jump04", "jump05", "jump06"
		}, 12f);

		this.runMotion = new Model.Animation(new String[]{
			"run01", "run02", "run03", "run04", "run05", "run06"
		}, 12f);
		
		this.standMotion = new Model.Animation(new String[]{
			"stand01","stand02","stand03","stand04","stand05",
			"stand06","stand07","stand08","stand09","stand10",
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
