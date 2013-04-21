package edu.ouhk.student.cubescape.engine.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import edu.ouhk.student.cubescape.engine.Character;
import edu.ouhk.student.cubescape.engine.Model;
import edu.ouhk.student.cubescape.engine.Scene;
import edu.ouhk.student.cubescape.engine.character.*;
import edu.ouhk.student.cubescape.engine.object.*;

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
					"stand36","stand37","stand38","stand39","stand40"
			}, 12f);
			position.z = -50f;
			position.y = -5f;
			stand();
		}
		
		@Override
		public void onFrameChange() {
			super.onFrameChange();
			rotation.y += 1f;
		}
	};
	
	public MainMenuScene() {
		super(mainChar);
		this.backgroundColor = Color.BLACK;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		UniverseBox o = new UniverseBox(){
			private float timeInterval = 1 / 12;
			private float timer = 0;
			@Override
			public void render(ShaderProgram program) {
				super.render(program);
				timer += Gdx.graphics.getDeltaTime();
				if(timer>=timeInterval) {
					rotation.z += .1f;
					timer = 0;
				}
			}
		};
		addObjects(o);
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
