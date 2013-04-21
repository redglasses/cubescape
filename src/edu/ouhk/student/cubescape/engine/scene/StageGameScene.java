package edu.ouhk.student.cubescape.engine.scene;

import java.util.LinkedList;

import android.util.Log;

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
import edu.ouhk.student.cubescape.engine.util.Timer;

public class StageGameScene extends Scene {
	private static final float maxX = 300;
	private static final float maxZ = 400;
	private static final float CHAR_RESPAWN_TIME = 3f;
	private static final float BOSS_SPAWN_TIME = 30f;
	private static final int MAX_ENEMY_COUNT = 10;
	
	private Runnable bossGeneration = new Runnable(){
		@Override
		public void run() {
			boss = new Boss01();
			bossSpawnTimer = null;
			boss.bulletInterval *= enemyAttackIntervalMuplex;
			boss.hitPoint *= enemyHpMuplex;
			boss.maxHitPoint *= enemyHpMuplex;
			boss.setScore(boss.getScore() + stage);
			((Boss01)boss).noOfBullets += stage;
			addObjects(boss);
		}
	};
	
	private LinkedList<Character> enemyChar;
	private LinkedList<Bullet> allyBullet;
	private LinkedList<Bullet> enemyBullet;
	private float timeInterval = 0;
	
	private Timer charRespawnTimer;
	private Timer bossSpawnTimer;
	private Character boss;
	
	private int stage = 0;
	
	private float enemyHpMuplex = 1f;
	private float enemyAttackIntervalMuplex = .9f;
	
	private boolean bulletHoming = false;
	
	public StageGameScene() {
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
		character.position.z = 200f;
		enemyChar = new LinkedList<Character>();
		allyBullet = new LinkedList<Bullet>();
		enemyBullet = new LinkedList<Bullet>();
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		this.camera.far = Float.MAX_VALUE / 2;
		this.camera.translate(0, 500, 0);
		this.camera.rotate(-90, 1, 0, 0);
		
		Object bg = new UniverseBox() {
			private float timer = 0;
			private float timeInterval = 1 / 48;
			private float step = .03f;
			@Override
			public void render(ShaderProgram program) {
				super.render(program);
				
				this.timer += Gdx.graphics.getDeltaTime();
				if (this.timer>=this.timeInterval) {
					this.timer = 0;
					rotation.y += step;
				}
			}
		};
		bg.scale.y = .1f;
		bg.scale.x = bg.scale.z = 2f;
		bg.position.x = 800f;
		bg.position.y = -250f;
		addObjects(bg);
		
		bossSpawnTimer = new Timer(BOSS_SPAWN_TIME, bossGeneration);

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
		
		if(charRespawnTimer==null)
			((ValkyrieVF1A)this.character).shoot(this);
		
		for(Character c : enemyChar)
			c.shoot(this);
	}
	
	@Override
	public void onPostRender() {
		super.onPostRender();
		character.update();
		
		if(character.isDead()){
			if(charRespawnTimer==null) {
				charRespawnTimer = new Timer(CHAR_RESPAWN_TIME, new Runnable(){
					@Override
					public void run() {
						character.hitPoint = character.maxHitPoint;
						character.isVisible = true;
						charRespawnTimer = null;
					}
				});
				onCharacterDead();
			} else {
				character.isVisible = character.isVisible ? false : true;
				charRespawnTimer.keepAlive();
			}
		}
		
		if(boss!=null && boss.isDead()) {
			onEnemyKilled(boss.getScore());
			
			stage++;
			
			enemyAttackIntervalMuplex *= .9f;//stage%10>=5?.9f:1f;
			enemyHpMuplex *= 1.1f;//stage%10>=5?1f:1.1f;
			
			if(stage>=10)
				bulletHoming = true;
			
			enemyChar.remove(boss);
			removeObjects(boss);
			boss = null;
			bossSpawnTimer = new Timer(BOSS_SPAWN_TIME, bossGeneration);
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
		
		if(bossSpawnTimer!=null) {
			bossSpawnTimer.keepAlive();
			// Enemy Generation
			timeInterval += Gdx.graphics.getDeltaTime();
			if(timeInterval>=1) {
				timeInterval = 0;
				if (enemyChar.size() < MAX_ENEMY_COUNT){
					Enemy01 e = new Enemy01();
					if(bulletHoming) {
						e.setHoming(this.character, Math.PI/180);
					}
					e.bulletInterval *= enemyAttackIntervalMuplex;
					e.hitPoint *= enemyHpMuplex;
					e.maxHitPoint *= enemyHpMuplex;
					e.setScore(e.getScore() + stage);
					addObjects(e);
					//addObjects(Enemy01?new Enemy01().setHoming(this.character, Math.PI/180):new Enemy01());
				}
			}
		}
	}
	
	public boolean isOffScreen(Object obj) {
		return Math.abs(obj.position.x) > maxX || Math.abs(obj.position.z) > maxZ;
	}

	public void onEnemyKilled(int score) { /* override me! */ }
	public void onCharacterDead() { /* override me! */ }
}
