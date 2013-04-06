package edu.ouhk.student.cubescape.engine.renderer;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import edu.ouhk.student.cubescape.engine.Object;
import edu.ouhk.student.cubescape.engine.Renderer;

public class GLES20 extends Renderer {
	public static final String PROJECTION_ATTRIBUTE = "u_projTrans";
	public static final String MODEL_TRANSLATION_ATTRIBUTE = "u_modelTrans";
	public static final String MODEL_ROTATION_ATTRIBUTE = "u_modelRot";

	public static final String VERTEX_SHADER =
		"attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" +
		"attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" +
		"attribute vec4 " + ShaderProgram.NORMAL_ATTRIBUTE + ";\n" +
		"attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" +
		"uniform mat4 " + PROJECTION_ATTRIBUTE + ";\n" +
		"uniform mat4 " + MODEL_TRANSLATION_ATTRIBUTE + ";\n" +
		"uniform mat4 " + MODEL_ROTATION_ATTRIBUTE + ";\n" +
		"varying vec4 v_color;\n" +
		"varying vec2 v_texCoords;\n" +
		"\n" +
		"void main()\n" +
		"{\n" +
		"v_color = " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" +
		"v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" +
		"gl_Position =  "+ PROJECTION_ATTRIBUTE +" * " + MODEL_TRANSLATION_ATTRIBUTE + " * " + MODEL_ROTATION_ATTRIBUTE +" * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" +
		"}\n";
	public static final String FRAGMENT_SHADER =
		"precision mediump float;\n" +
		"varying vec4 v_color;\n" +
		"varying vec2 v_texCoords;\n" +
		"uniform vec4 " + Object.COLOR_ATTRIBUTE + ";\n" +
		"uniform sampler2D " + Object.TEXTURE_ATTRIBUTE + ";\n" +
		"\n" +
		"void main()\n" +
		"{\n" +
		"gl_FragColor = " + Object.COLOR_ATTRIBUTE + " * texture2D(" + Object.TEXTURE_ATTRIBUTE + ", v_texCoords);\n" +
		"}\n";
	
	private ShaderProgram program;
	
	public GLES20(RenderingListener object) {
		super(object);
	}

	@Override
	public void create() {
		super.create();

		program = new ShaderProgram(VERTEX_SHADER, FRAGMENT_SHADER);
		if(!program.isCompiled()){
			Log.e("ShaderProgram", program.getLog());
		}
	}

	@Override
	public void render() {
		Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);
	    Gdx.gl20.glClear(GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_COLOR_BUFFER_BIT);
	    
		for(RenderingListener o : renderingListeners)
			o.onPreRender();
		
		program.begin();
	    for(RenderingListener o : renderingListeners)
			o.onRender(program);
	    program.end();
	    
	    for(RenderingListener o : renderingListeners)
			o.onPostRender();
	}
}
