package edu.ouhk.student.cubescape.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class ActiveObject extends Object {

	protected boolean isEnemy = false;
	public boolean setEnemy(boolean isEnemy){
		this.isEnemy = isEnemy;
		return this.isEnemy;
	}
	public ActiveObject(int modelId) {
		super(modelId);
		// TODO Auto-generated constructor stub
	}
	public ActiveObject(int modelId, int textureId) {
		super(modelId, textureId);
		// TODO Auto-generated constructor stub
	}
	public ActiveObject(int modelId, Color color) {
		super(modelId, color);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void create() {
		super.create();
	}
	@Override
	public void dispose() {
		super.dispose();
	}
	@Override
	public void render(ShaderProgram program) {
		super.dispose();
	}


}
