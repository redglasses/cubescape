package edu.ouhk.student.cubescape.engine.scene;

import edu.ouhk.student.cubescape.engine.Character;
import edu.ouhk.student.cubescape.engine.Scene;
import edu.ouhk.student.cubescape.engine.object.*;

public class SampleScene extends Scene {
	public SampleScene(Character character) {
		super(character);
		
		Crate o = new Crate();
		Circle c = new Circle();
		c.scale.x = c.scale.y = c.scale.z = 0.001f;
		c.position.x = character.position.x;
		c.position.y = character.position.y;
		c.position.z = character.position.z - 10f;
		addObjects(c);
		o.scale.x = o.scale.y = o.scale.z = .005f;
		o.position.x = 20f;
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
