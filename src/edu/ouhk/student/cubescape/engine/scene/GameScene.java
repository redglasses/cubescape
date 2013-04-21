package edu.ouhk.student.cubescape.engine.scene;

import java.util.Date;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import edu.ouhk.student.cubescape.engine.ActiveObject;
import edu.ouhk.student.cubescape.engine.Bullet;
import edu.ouhk.student.cubescape.engine.Character;
import edu.ouhk.student.cubescape.engine.Object;
import edu.ouhk.student.cubescape.engine.Renderer.Renderable;
import edu.ouhk.student.cubescape.engine.Scene;
import edu.ouhk.student.cubescape.engine.character.*;
import edu.ouhk.student.cubescape.engine.object.UniverseBox;

public class GameScene extends Scene {
	private static final float maxX = 300;
	private static final float maxZ = 400;
	protected LinkedList<Character> enemyChar;
	protected LinkedList<Bullet> allyBullet;
	protected LinkedList<Bullet> enemyBullet;
	protected float startTime = 0;
	protected float timeInterval = 0;
	protected final float EnemyInterval = 1f;
	protected final float BossInterval = 180f;
	protected long enemyCurrentInterval = 0;
	protected long bossCurrentInterval = 0;
	public int maxEnemy = 10;
	public int currentEnemy = 0;
	
	public GameScene() {
		super(new ValkyrieVF1A(){
			@Override
			public void onCollided(Object object){
				if(object instanceof ActiveObject && ((ActiveObject)object).isEnemy())
					super.onCollided(object);
			}
			
			@Override
			public void onFrameChange() {
				if(isMoving){
					position.x += movingStep * Math.cos(movingAngle);
					position.z += movingStep * Math.sin(movingAngle);
					
					if(Math.abs(position.x) >= maxX - 100) {
						position.x -= movingStep * Math.cos(movingAngle);
					}
					
					if(Math.abs(position.z) >= maxZ - 100) {
						position.z -= movingStep * Math.sin(movingAngle);
					}
					
					onMoved();
				}
			}
		});

		enemyChar = new LinkedList<Character>();
		allyBullet = new LinkedList<Bullet>();
		enemyBullet = new LinkedList<Bullet>();
		startTime = 0;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		startTime = new Date().getTime();
		this.camera.far = Float.MAX_VALUE / 2;
		this.camera.translate(0, 500, 0);
		this.camera.rotate(-90, 1, 0, 0);
		
		Object bg = new UniverseBox() {
			private float timer = 0;
			private float timeInterval = 1 / 48;
			private float step = .2f;
			@Override
			public void render(ShaderProgram program) {
				super.render(program);
				
				timer += Gdx.graphics.getDeltaTime();
				if (timer>=timeInterval) {
					timer = 0;
					position.z += step;
				}
			}
		};
		bg.scale.y = .2f;
		bg.scale.x = bg.scale.z = 1f;
		bg.position.y = -250f;
		bg.position.z = -400f;
		addObjects(bg);

		((ValkyrieVF1A)(this.character)).shoot(this);
	}
	
	@Override
	public void addObjects(Object ...object) {
		super.addObjects(object);
		for (Renderable o : object){
			if (o instanceof Character && ((ActiveObject)o).isEnemy())
					enemyChar.add((Character)o);

			if (o instanceof Bullet){
				if (((ActiveObject)o).isEnemy())
					enemyBullet.add((Bullet)o);
				else
					allyBullet.add((Bullet)o);
			}
		}
	}
	@Override
	public void onPreRender() {
		super.onPreRender();
		camera.update();
		
		((ValkyrieVF1A)this.character).shoot(this);
		for(Character c : enemyChar)
			((Enemy01)c).shoot(this);
	}
	
	@Override
	public void onPostRender() {
		super.onPostRender();
		character.update();
		
		if(character.isDead()){
			onCharacterDead();
			character.hitPoint = character.maxHitPoint;
		}
		
		for (Object o : objects){
			if (o instanceof ActiveObject)
				((ActiveObject)o).update();
			
			if(o instanceof Character && enemyChar.contains(o)) {
				if (isOffScreen(o)) {
					enemyChar.remove(o);
					removeObjects(o);
				}
			} else if (o instanceof Bullet && isOffScreen(o)) {
				(((ActiveObject)o).isEnemy() ? enemyBullet : allyBullet).remove(o);
				removeObjects(o);
			}
		}
		
		// Enemy character handling
		LinkedList<Character> removingEnemyChar = new LinkedList<Character>();
		for (Character c : enemyChar)
			if (c.isDead()) {
				onEnemyKilled(c.getScore());
				removingEnemyChar.add(c);
				removeObjects(c);
			}
		for(Character c : removingEnemyChar)
			enemyChar.remove(c);
		
		// Enemy bullet handling
		LinkedList<Bullet> removingEnemyBullet = new LinkedList<Bullet>();
		for (Bullet b : enemyBullet)
			if (b.isDead()) {
				removingEnemyBullet.add(b);
				removeObjects(b);
			}
		for(Bullet b : removingEnemyBullet)
			enemyBullet.remove(b);
		
		// Ally bullet handling
		LinkedList<Bullet> removingAllyBullet = new LinkedList<Bullet>();
		for (Bullet b : allyBullet)
			if (b.isDead()) {
				removingAllyBullet.add(b);
				removeObjects(b);
			}
		for(Bullet b : removingAllyBullet)
			allyBullet.remove(b);
		
		// Enemy Generation
		timeInterval += Gdx.graphics.getDeltaTime();
		if(timeInterval>=1) {
			timeInterval = 0;
			if (enemyChar.size() < maxEnemy){
				addObjects(new Enemy01().setHoming(this.character, Math.PI/180));
			}
		}
	}
	
	public boolean isOffScreen(Object obj) {
		return Math.abs(obj.position.x) > maxX || Math.abs(obj.position.z) > maxZ;
	}

	public void onEnemyKilled(int score) { /* override me! */ }
	public void onCharacterDead() { /* override me! */ }
}
