package edu.ouhk.student.cubescape.engine.scene;

import java.util.Date;
import java.util.LinkedList;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import edu.ouhk.student.cubescape.engine.ActiveObject;
import edu.ouhk.student.cubescape.engine.Bullet;
import edu.ouhk.student.cubescape.engine.Character;
import edu.ouhk.student.cubescape.engine.Object;
import edu.ouhk.student.cubescape.engine.Renderer;
import edu.ouhk.student.cubescape.engine.Renderer.Renderable;
import edu.ouhk.student.cubescape.engine.Scene;
import edu.ouhk.student.cubescape.engine.character.*;
import edu.ouhk.student.cubescape.engine.object.UniverseBox;

public class GameScene extends Scene {
	protected int score = 0;
	protected static float maxX = 300;
	protected static float maxZ = 400;
	protected LinkedList<Object> enemyChar;
	protected LinkedList<Object> allyBullet;
	protected LinkedList<Object> enemyBullet;
	protected LinkedList<Object> environmentObjects;
	protected int life = 3;
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

		enemyChar = new LinkedList<Object>();
		allyBullet = new LinkedList<Object>();
		enemyBullet = new LinkedList<Object>();
		environmentObjects = new LinkedList<Object>();
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
	public void addObjects(Renderer.Renderable ...object) {
		super.addObjects(object);
		for (Renderable o : object){
			if (o instanceof Character){
				if (((ActiveObject)o).isEnemy()){
					enemyChar.add((Object)o);
				}
				
			}
			if (o instanceof Bullet){
				if (((ActiveObject)o).isEnemy()){
					enemyBullet.add((Object)o);
				}
				else{
					allyBullet.add((Object)o);
				}
			}
		}
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
		
		LinkedList<Object> removalObjectList = new LinkedList<Object>();
		LinkedList<Object> removalEnemyList = new LinkedList<Object>();
		LinkedList<Object> removalEnemyBulletList = new LinkedList<Object>();
		LinkedList<Object> removalAllyBulletList = new LinkedList<Object>();
		
		for (Renderable o : objects){
			if (o instanceof ActiveObject){
				((ActiveObject)o).update();
			}
			
			// off screen objects set to invisible
			if (Math.abs(((Object)o).position.x) > maxX || Math.abs(((Object)o).position.z) > maxZ){
				if (o instanceof Bullet){
					if (((ActiveObject)o).isEnemy()){
						enemyBullet.remove(o);
					} else{
						allyBullet.remove(o);
					}
				}
				if (o instanceof Character)
					enemyChar.remove(o);
				
				removalObjectList.add((Object)o);
			}
		}

		//Collision Detection
		/*for (Object enemy : enemyChar) {
			if(character.isOverlaid(enemy)){
				//Log.d("GameScene","isOverlaid");
				//enemyChar.remove(enemy);
				removalEnemyList.add(enemy);
				removalObjectList.add(enemy);
				life--;
				onCharacterDead();
			}
			
			for (Object ally : allyBullet) {
				if (ally.isOverlaid(enemy)) {
					Log.d("GameScene","ally bullet hit enemy");
					if (enemy instanceof Object.CollisionListener) {
						((Object.CollisionListener)enemy).onCollided(ally);
					}
				}
			}
			
			if(((ActiveObject)enemy).isDead()) {
				Log.d("GameScene","enemy isDead");
				onKill();
				removalEnemyList.add(enemy);
				removalObjectList.add(enemy);
			}
		}*/
		
		for (Object enemy : enemyChar){
			if (character.isOverlaid(enemy)){
				character.onCollided(enemy);
				if (character.isDead()){
					life--;
					onCharacterDead();
					if(life<=0)
						onGameOver();
				}
				if (((ActiveObject)enemy).isDead()){
					onKill();
					removalEnemyList.add(enemy);
					removalObjectList.add(enemy);
				}
			}
		}
		for (Object enemy : enemyBullet){
			if (character.isOverlaid(enemy)){
				character.onCollided(enemy);
				if ((character).isDead()){
					life--;
					onCharacterDead();
					if(life<=0)
						onGameOver();
				}
				if (((ActiveObject)enemy).isDead()){
					onKill();
					removalEnemyBulletList.add(enemy);
					removalObjectList.add(enemy);
				}
			}
		}

		for (Object ally : allyBullet){
			for (Object enemy : enemyChar){
				if (ally.isOverlaid(enemy)){
					((Object.CollisionListener)ally).onCollided(enemy);
					if (((ActiveObject)ally).isDead()){
						removalAllyBulletList.add(ally);
						removalObjectList.add(ally);
					}
					if (((ActiveObject)enemy).isDead()){
						onKill();
						removalEnemyList.add(enemy);
						removalObjectList.add(enemy);
					}
				}
			}
		}
		
		for(Object o : removalObjectList)
			objects.remove(o);
		for(Object o : removalEnemyList)
			enemyChar.remove(o);
		for(Object o : removalAllyBulletList)
			allyBullet.remove(o);
		for(Object o : removalEnemyBulletList)
			enemyBullet.remove(o);
		
		//enemy Generation
		timeInterval += Gdx.graphics.getDeltaTime();
		if(timeInterval>=1) {
			timeInterval = 0;
			if (enemyChar.size() < maxEnemy){
				addObjects(new Enemy01().setHoming(this.character, Math.PI/180));
			}
		}
		((ValkyrieVF1A)(this.character)).shoot(this);
		/*if (EnemyInterval * enemyCurrentInterval < timeInterval){
			if (enemyChar.size() < maxEnemy){
				//generate enemies
				addObjects(new Enemy01().setHoming(this.character, Math.PI/180));
			}
			enemyCurrentInterval++;	
		}*/
		//bullet Generation
		/*if (((ValkyrieVF1A)(this.character)).bulletInterval * ((ValkyrieVF1A)(this.character)).bulletShot < timeInterval){
			((ValkyrieVF1A)(this.character)).shoot(this);
		}*/
		//timeInterval += Gdx.graphics.getDeltaTime();
	}

	@Override
	public void onGameOver() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKill() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCharacterDead() {
		// TODO Auto-generated method stub
		
	}
}
