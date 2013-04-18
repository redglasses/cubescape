package edu.ouhk.student.cubescape.engine.scene;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;


import edu.ouhk.student.cubescape.engine.*;

import edu.ouhk.student.cubescape.engine.Character;
import edu.ouhk.student.cubescape.engine.Object;

import edu.ouhk.student.cubescape.engine.Renderer;
import edu.ouhk.student.cubescape.engine.Renderer.Renderable;
import edu.ouhk.student.cubescape.engine.Scene;
import edu.ouhk.student.cubescape.engine.character.Enemy01;
import edu.ouhk.student.cubescape.engine.character.ValkyrieVF1A;
import edu.ouhk.student.cubescape.engine.object.*;


	
public class GameScene extends Scene {
	//protected LinkedList<Object> allyChar;
	protected int score = 0;
	protected float maxX = 300;
	protected float maxZ = 400;
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
	public GameScene(Character character) {
		super(character);
		//allyChar = new LinkedList<Object>();
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
		this.camera.translate(0, 500, 0);
		this.camera.rotate(-90, 1, 0, 0);
		Crate o = new Crate();
		o.scale.x = o.scale.y = o.scale.z = .002f;
		o.position.y = 1;
		addObjects(o);
		((ValkyrieVF1A)(this.character)).shoot(this);
		//addObjects(new Enemy01());
		
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
		
		for (Renderable o : objects){
			if (o instanceof ActiveObject){
				((ActiveObject)o).update();
			}
		}
		//camera.position.x = character.position.x;
		//camera.lookAt(character.position.x, character.position.y, character.position.z);
		
		//Collision Detection
		
		//for (Object ally : allyChar){
			/*for (Object enemy : enemyChar){
				if (character.isOverlaid(enemy)){
					((ActiveObject)character).onCollided((ActiveObject)enemy);
					if (((ActiveObject)character).isDied()){
						//respawn character
						//allyChar.remove(character);
						life--;
					}
					if (((ActiveObject)enemy).isDied()){
						enemy.isVisible = false;
						enemyChar.remove(enemy);
					}
				}
			}
			for (Object enemy : enemyBullet){
				if (character.isOverlaid(enemy)){
					((ActiveObject)character).onCollided((ActiveObject)enemy);
					if ((character).isDied()){
						//respawn character
						//allyChar.remove(ally);
						life--;
					}
					if (((ActiveObject)enemy).isDied()){
						enemy.isVisible = false;
					}
				}
			}*/
		//}
		/*for (Object ally : allyBullet){
			synchronized(ally){
				for (Object enemy : enemyChar){
					synchronized(enemy){
	
						if (ally.isOverlaid(enemy)){
							((ActiveObject)ally).onCollided((ActiveObject)enemy);
							if (((ActiveObject)ally).isDied()){
								ally.isVisible = false;
								allyBullet.remove(ally);
							}
							if (((ActiveObject)enemy).isDied()){
								enemy.isVisible = false;
								enemyChar.remove(enemy);
							}
						}
					}
				}
			}
		}
		*/
		//Remove out-screen object

		for (Renderable o : objects){
			if (Math.abs(((Object)o).position.x) > maxX || Math.abs(((Object)o).position.z) > maxZ){
				
				if (o instanceof Bullet){
					if (((ActiveObject)o).isEnemy()){
						enemyBullet.remove(o);
					}
					else{
						allyBullet.remove(o);
					}
				}
				if (o instanceof Character){
					enemyChar.remove(o);
				}
				
				((Object)o).isVisible = false;
				
			}
			
			System.out.println();
		}
		
		
		//enemy Generation

		if (EnemyInterval * enemyCurrentInterval < timeInterval){
			if (enemyChar.size() < maxEnemy){
				//generate enemies
				addObjects(new Enemy01().setHoming(this.character, Math.PI/180));
			}
			enemyCurrentInterval++;
			
		}
		
		
		//bullet Generation
		if (((ValkyrieVF1A)(this.character)).bulletInterval * ((ValkyrieVF1A)(this.character)).bulletShot < timeInterval){
			((ValkyrieVF1A)(this.character)).shoot(this);
		}
		timeInterval += Gdx.graphics.getDeltaTime();
	}
}
