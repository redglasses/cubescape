package edu.ouhk.student.cubescape.engine;

public abstract class Character extends Object implements Model.FrameChangeListener {	
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
	protected double movingAngle;
	protected Direction direction;
	
	protected float setMovingStep(float step){
		movingStep = step;
		return movingStep;
	}
	protected Character(int modelId, int textureId) {
		super(modelId, textureId);
		
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
				onDirectionChange(Character.Direction.LEFT);
				break;
			case 1:
				onDirectionChange(Character.Direction.UP_LEFT);
				break;
			case 2:
				onDirectionChange(Character.Direction.UP);
				break;
			case 3:
				onDirectionChange(Character.Direction.UP_RIGHT);
				break;
			case 4:
			case -4:
				onDirectionChange(Character.Direction.RIGHT);
				break;
			case -3:
				onDirectionChange(Character.Direction.DOWN_RIGHT);
				break;
			case -2:
				onDirectionChange(Character.Direction.DOWN);
				break;
			case -1:
				onDirectionChange(Character.Direction.DOWN_LEFT);
				break;
		}
	}
	
	public void stand() {
		model.playAnimtion(standMotion);
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
	
	public abstract void onMoved();
	/**
	 * @param from 
	 * @param to
	 * @return true means handled, false means unhandled.
	 */
	public abstract boolean onDirectionChange(Direction to);
}
