package edu.ouhk.student.cubescape.engine.scene;

import edu.ouhk.student.cubescape.engine.Character;
import edu.ouhk.student.cubescape.engine.Scene;
import edu.ouhk.student.cubescape.engine.object.*;

public class SampleScene extends Scene {
	public SampleScene(Character character) {
		super(character);
		
		Crate o = new Crate();
		o.scale.x = o.scale.y = o.scale.z = .02f;
		addObjects(o);
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
		//camera.position.x = character.position.x;
		//camera.lookAt(character.position.x, character.position.y, character.position.z);
	}
}
