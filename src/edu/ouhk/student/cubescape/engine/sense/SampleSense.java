package edu.ouhk.student.cubescape.engine.sense;

import edu.ouhk.student.cubescape.engine.Character;
import edu.ouhk.student.cubescape.engine.Scene;
import edu.ouhk.student.cubescape.engine.object.*;

public class SampleSense extends Scene {
	public SampleSense(Character character) {
		super(character);
		
		Crate o = new Crate();
		o.scale.x = o.scale.y = o.scale.z = .005f;
		o.position.x = 20f;
		addObjects(o);
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		camera.rotate(-35, 0, 1, 0);
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
		camera.position.x = character.position.x;
		camera.lookAt(character.position.x, character.position.y, character.position.z);
	}
}
