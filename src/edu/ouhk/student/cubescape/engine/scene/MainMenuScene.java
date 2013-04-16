package edu.ouhk.student.cubescape.engine.scene;

import com.badlogic.gdx.graphics.Color;

import edu.ouhk.student.cubescape.engine.Character;
import edu.ouhk.student.cubescape.engine.Model;
import edu.ouhk.student.cubescape.engine.Scene;
import edu.ouhk.student.cubescape.engine.character.*;

public class MainMenuScene extends Scene {
	public static Character mainChar = new ValkyrieVF1A() {
		@Override
		public void create() {
			super.create();
			standMotion = new Model.Animation(new String[]{
					"stand01","stand02","stand03","stand04","stand05",
					"stand06","stand07","stand08","stand09","stand10",
					"stand11","stand12","stand13","stand14","stand15",
					"stand16","stand17","stand18","stand19","stand20",
					"stand21","stand22","stand23","stand24","stand25",
					"stand26","stand27","stand28","stand29","stand30",
					"stand31","stand32","stand33","stand34","stand35",
					"stand36","stand37","stand38","stand39","stand40"/*,
					"taunt01","taunt02","taunt03","taunt04","taunt05",
					"taunt06","taunt07","taunt08","taunt09","taunt10",
					"taunt11","taunt12","taunt13","taunt14","taunt15",
					"taunt16","taunt17"*/
			}, 12f);
			position.z = -1f;
			position.y = -5f;
			stand();
		}
	};
	
	public MainMenuScene() {
		super(mainChar);
		this.backgroundColor = Color.BLACK;
	}
	
	public Character getCharacter() {
		return this.character;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	@Override
	public void onPreRender() {
		super.onPreRender();
		camera.update();
	}
	
	@Override
	public void onPostRender() {
		super.onPostRender();
		character.update();
	}
}
