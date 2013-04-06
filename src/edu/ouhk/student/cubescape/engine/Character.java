package edu.ouhk.student.cubescape.engine;

public abstract class Character extends Object implements Model.FrameChangeListener {	
	public enum Direction {
		UP,
		DOWN,
		LEFT,
		RIGHT
	}

	protected Model.Animation jumpMotion;
	protected Model.Animation runMotion;
	protected Model.Animation standMotion;
	
	protected boolean isMoving = false;
	protected float movingStep = 4f;
	protected Direction direction;

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
		if(this.direction != direction) {
			if(!this.onDirectionChange(this.direction, direction))
				this.direction = direction;
		}
		
		if(!isMoving)
			model.playAnimtion(runMotion);
		
		isMoving = true;
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
				position.y += movingStep;
				break;
			case DOWN:
				position.y -= movingStep;
				break;
			case RIGHT:
				position.x += movingStep;
				break;
			case LEFT:
				position.x -= movingStep;
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
