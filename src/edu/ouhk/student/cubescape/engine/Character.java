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
	protected float movingStep45 = (float)Math.sqrt((movingStep*movingStep)*2);
	protected Direction direction;
	
	
	
	protected float setMovingStep(float step){
		movingStep = step;
		movingStep45 = (float)Math.sqrt((movingStep*movingStep)*2);
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
	
	public void move(Character.Direction direction) {	
		isMoving = true;
		if(this.direction != direction) {
			if(!this.onDirectionChange(this.direction, direction))
				this.direction = direction;
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
			switch(direction) {
			case UP:
				position.z += movingStep;
				break;
			case DOWN:
				position.z -= movingStep;
				break;
			case RIGHT:
				position.x += movingStep;
				break;
			case LEFT:
				position.x -= movingStep;
				break;
			case UP_LEFT:
				position.z += movingStep45;
				position.x -= movingStep45;
				break;
			case UP_RIGHT:
				position.z += movingStep45;
				position.x += movingStep45;
				break;
			case DOWN_LEFT:
				position.z -= movingStep45;
				position.x -= movingStep45;
				break;
			case DOWN_RIGHT:
				position.z -= movingStep45;
				position.x += movingStep45;
				break;
			}
			onMoved();
		}
	}
	
	public abstract void onMoved();
	/**
	 * @param from 
	 * @param to
	 * @return true means handled, false means unhandled.
	 */
	public abstract boolean onDirectionChange(Direction from, Direction to);
}
