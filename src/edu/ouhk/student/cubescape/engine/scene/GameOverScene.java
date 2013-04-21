package edu.ouhk.student.cubescape.engine.scene;

import com.badlogic.gdx.graphics.Color;

import edu.ouhk.student.cubescape.engine.Character;
import edu.ouhk.student.cubescape.engine.Model;
import edu.ouhk.student.cubescape.engine.Scene;
import edu.ouhk.student.cubescape.engine.character.ValkyrieVF1A;

public class GameOverScene extends Scene {
	public static Character mainChar;
	static {
		mainChar = new ValkyrieVF1A() {
			@Override
			public void create() {
				super.create();
				
				position.z = -50f;
				position.y = 5f;
				model.playAnimtion(new Model.Animation(new String[]{
					"crdeath5"
				},12f));
			}
			
			@Override
			public void onFrameChange() {
				super.onFrameChange();
				rotation.y += 1f;
			}
		};
	}
	public GameOverScene() {
		super(mainChar);
		backgroundColor = Color.BLACK;
	}
	
	@Override
	public void onPostRender() {
		super.onPostRender();
		character.update();
	}
}
