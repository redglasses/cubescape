package edu.ouhk.student.cubescape.engine;

import android.util.Log;

public abstract class Character extends ActiveObject implements Model.FrameChangeListener, Object.CollisionListener {	
	public enum Direction {
		UP,
		DOWN,
		LEFT,
		RIGHT,
		UP_LEFT,
		UP_RIGHT,
		DOWN_LEFT,
		DOWN_RIGHT,
	}

	protected Model.Animation jumpMotion;
	protected Model.Animation runMotion;
	protected Model.Animation standMotion;
	
	protected boolean isMoving = false;
	protected float movingStep = 4f;
	protected int score_bonus = 3;
	protected Direction direction;

	protected float setMovingStep(float step){
		movingStep = step;
		return movingStep;
	}
	protected Character(int modelId, int textureId) {
		super(modelId, textureId);
		this.attackPower = 10;
	}
	
	public int getScore() {
		return this.score_bonus;
	}
	
	@Override
	public void create() {
		super.create();
		model.addFrameChangeListener(this);
		
	}

	public boolean isMoving() {
		return isMoving;
	}
	
	public void move(double angle) {	
		isMoving = true;
		movingAngle = angle;
		switch ((int)Math.round(angle/Math.PI*4)){
			case 0:
				onDirectionChange(Character.Direction.RIGHT);
				break;
			case 1:
				onDirectionChange(Character.Direction.DOWN_RIGHT);
				break;
			case 2:
				onDirectionChange(Character.Direction.DOWN);
				break;
			case 3:
				onDirectionChange(Character.Direction.DOWN_LEFT);
				break;
			case 4:
			case -4:
				onDirectionChange(Character.Direction.LEFT);
				break;
			case -3:
				onDirectionChange(Character.Direction.UP_LEFT);
				break;
			case -2:
				onDirectionChange(Character.Direction.UP);
				break;
			case -1:
				onDirectionChange(Character.Direction.UP_RIGHT);
				break;
		}
	}
	
	public void stand() {
		model.playAnimtion(standMotion);
		this.direction = Direction.UP;
		isMoving = false;
	}
	
	public void update() {
		model.update();
	}
	
	@Override
	public void onFrameChange() {
		if(isMoving){
			position.x += movingStep * Math.cos(movingAngle);
			position.z += movingStep * Math.sin(movingAngle);
			onMoved();

		}
	}
	

	/**
	 * @param from 
	 * @param to
	 * @return true means handled, false means unhandled.
	 */
	public abstract boolean onDirectionChange(Direction to);
	
	@Override
	public void onCollided(Object object) {
		if (this.isEnemy() != ((ActiveObject)object).isEnemy()){
			hitPoint -= ((ActiveObject)object).attackPower;
			((ActiveObject) object).hitPoint -= this.attackPower;
		}
			
			
	}
	public void setScore(int i) {
		// TODO Auto-generated method stub
		this.score_bonus = i;
	}
}
