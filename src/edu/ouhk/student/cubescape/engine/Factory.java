package edu.ouhk.student.cubescape.engine;

import java.io.IOException;

import android.content.res.Resources.NotFoundException;
import android.util.Log;
import android.util.SparseArray;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g3d.loaders.md2.MD2Loader;
import com.badlogic.gdx.graphics.g3d.model.keyframe.KeyframedModel;

import edu.ouhk.student.cubescape.Application;

public class Factory {
	public static final String TAG = "cubescape.engine.Factory";
	
	private static MD2Loader modelLoader;
	private static SparseArray<KeyframedModel> models;
	private static SparseArray<Texture> textures;

	public static KeyframedModel loadModel(int resourceId) {
		if(modelLoader==null)
			modelLoader = new MD2Loader();
		if(models==null)
			models = new SparseArray<KeyframedModel>();
		
		if(models.get(resourceId)==null) {
			KeyframedModel m = modelLoader.load(
					Application.getContext()
								.getResources()
								.openRawResource(resourceId), .2f);
			models.put(resourceId, m);
			return m;
		}
		
		return models.get(resourceId);
	}

	public static Texture loadTexture(int resourceId) {
		if(textures==null)
			textures = new SparseArray<Texture>();
		
		if(textures.get(resourceId)==null) {
			try {
				Texture t = new Texture(new Pixmap(new Gdx2DPixmap(
						Application.getContext()
									.getResources()
									.openRawResource(resourceId),
						Gdx2DPixmap.GDX2D_FORMAT_RGB888)), true);
				textures.put(resourceId, t);
				return t;
			} catch (NotFoundException e) {
				Log.e(TAG, "Texture resource not found.");
				e.printStackTrace();
			} catch (IOException e) {
				Log.e(TAG, "Fail to load texture resource.");
				e.printStackTrace();
			}
		}
		
		return textures.get(resourceId);
	}
	
	public static void cleanTextureCache() {
		if(textures!=null) {
			for(int i=0;i<textures.size();i++)
				textures.valueAt(i).dispose();
			
			textures.clear();
		}
	}
	
	public static void cleanCache() {
		if(models!=null) {
			for(int i=0;i<models.size();i++)
				models.valueAt(i).dispose();
			
			models.clear();
		}
		
		if(textures!=null) {
			for(int i=0;i<textures.size();i++){
				textures.valueAt(i).dispose();
			}
			textures.clear();
		}
	}
}
