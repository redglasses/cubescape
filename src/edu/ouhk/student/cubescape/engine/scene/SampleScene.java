package edu.ouhk.student.cubescape.engine.scene;

import edu.ouhk.student.cubescape.engine.Character;
import edu.ouhk.student.cubescape.engine.Scene;
import edu.ouhk.student.cubescape.engine.object.*;

public class SampleScene extends Scene {
	public SampleScene(Character character) {
		super(character);
		
		
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		this.camera.translate(0, 500, 0);
		this.camera.rotate(-90, 1, 0, 0);
		Crate o = new Crate();
		o.scale.x = o.scale.y = o.scale.z = .002f;
		o.position.y = 1;
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
		//camera.position.x = character.position.x;
		//camera.lookAt(character.position.x, character.position.y, character.position.z);
	}
}
