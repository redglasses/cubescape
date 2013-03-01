package edu.ouhk.student.cubescape;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLES11;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;

public class GameRenderer implements GLSurfaceView.Renderer {
	
	private static final float[] triangleCoords = new float[] {
	     0,  1, 0,
	    -1, -1, 0,
	     1, -1, 0
	};
	
	private static final float[] quadCoords = new float[] {
	   -1, 1, 0,
	   -1,-1, 0,
	    1, 1, 0,
	    1,-1, 0
	};
	private static final float[] quadTexCoords = new float[] {
		1, 1,
		1, 0,
		0, 1,
		0, 0
	};
	
	private float triangleAngle = 0;
	private float quadAngle = 0;
	
	private static FloatBuffer triangleBuffer;
    private static FloatBuffer quadBuffer;
    private static FloatBuffer quadTexBuffer;
    private static IntBuffer intBuff;
    static {
    	triangleBuffer = ByteBuffer.allocateDirect(triangleCoords.length * Float.SIZE).order(ByteOrder.nativeOrder()).asFloatBuffer();
		quadBuffer = ByteBuffer.allocateDirect(quadCoords.length * Float.SIZE).order(ByteOrder.nativeOrder()).asFloatBuffer();
		quadTexBuffer = ByteBuffer.allocateDirect(quadTexCoords.length * Float.SIZE).order(ByteOrder.nativeOrder()).asFloatBuffer();
		triangleBuffer.put(triangleCoords).rewind();
		quadTexBuffer.put(quadTexCoords).rewind();
		quadBuffer.put(quadCoords).rewind();
		
		intBuff = IntBuffer.allocate(1);
    }
    
    private Context context;
    
    public GameRenderer(Context context) {
    	this.context = context;
    }

	@Override
	public void onDrawFrame(GL10 unused) {
		GLES11.glClear(GLES11.GL_COLOR_BUFFER_BIT | GLES11.GL_DEPTH_BUFFER_BIT);

		GLES11.glMatrixMode(GLES11.GL_MODELVIEW);
		        
        // draw triangle
		GLES11.glLoadIdentity();
		GLES11.glTranslatef(-1.5f, 0, -6);
		GLES11.glRotatef(triangleAngle, 0, 1, 0);
		//GLES11.glColor4f(1f, 0f, 0f, 1.0f);
		GLES11.glEnableClientState(GLES11.GL_VERTEX_ARRAY);
		GLES11.glVertexPointer(3, GLES11.GL_FLOAT, 0, triangleBuffer);
		GLES11.glDrawArrays(GLES11.GL_TRIANGLE_STRIP, 0, 3);
		GLES11.glDisableClientState(GLES11.GL_VERTEX_ARRAY);
        
        // draw quad
		GLES11.glLoadIdentity();
		GLES11.glTranslatef(1.5f, 0, -6);
		GLES11.glRotatef(quadAngle, 1, 0, 0);
		//GLES11.glColor4f(0f, 0f, 0f, 1.0f);
		GLES11.glBindTexture(GL10.GL_TEXTURE_2D, intBuff.get(0));
		GLES11.glEnableClientState(GLES11.GL_VERTEX_ARRAY);
		GLES11.glEnableClientState(GLES11.GL_TEXTURE_COORD_ARRAY);
		GLES11.glVertexPointer(3, GLES11.GL_FLOAT, 0, quadBuffer);
		GLES11.glTexCoordPointer(2, GL10.GL_FLOAT, 0, quadTexBuffer);
		GLES11.glDrawArrays(GLES11.GL_TRIANGLE_STRIP, 0, 4);
		GLES11.glDisableClientState(GLES11.GL_TEXTURE_COORD_ARRAY);
		GLES11.glDisableClientState(GLES11.GL_VERTEX_ARRAY);
		
		triangleAngle += 0.5f;
		quadAngle -= 0.5f;
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// avoid division by zero
        if (height == 0) height = 1;
        // draw on the entire screen
        GLES11.glViewport(0, 0, width, height);
        // setup projection matrix
        GLES11.glMatrixMode(GLES11.GL_PROJECTION);
        GLES11.glLoadIdentity();
        GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 0.1f, 100.0f);
	}

	@Override
	public void onSurfaceCreated(GL10 unused, EGLConfig config) {
		GLES11.glShadeModel(GLES11.GL_SMOOTH);

		GLES11.glClearDepthf(1.0f);
		GLES11.glEnable(GLES11.GL_DEPTH_TEST);
		GLES11.glDepthFunc(GLES11.GL_LEQUAL);
		
		GLES11.glHint(GLES11.GL_PERSPECTIVE_CORRECTION_HINT, GLES11.GL_NICEST);

		GLES11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		GLES11.glEnable(GL10.GL_TEXTURE_2D);
		GLES11.glGenTextures(1, intBuff);
		GLES11.glBindTexture(GL10.GL_TEXTURE_2D, intBuff.get(0));
		
		GLES11.glTexParameterx(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_MAG_FILTER, GLES11.GL_LINEAR);
		GLES11.glTexParameterx(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_MIN_FILTER, GLES11.GL_LINEAR);
		GLES11.glTexParameterx(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_WRAP_S, GLES11.GL_CLAMP_TO_EDGE);
		GLES11.glTexParameterx(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_WRAP_T, GLES11.GL_CLAMP_TO_EDGE);
		
		Bitmap texture = getTextureFromBitmapResource(context, R.drawable.head);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, texture, 0);
        texture.recycle();
	}
	
	 private static Matrix yFlipMatrix;
     
     static
     {
             yFlipMatrix = new Matrix();
             yFlipMatrix.postScale(1, -1); // flip Y axis
     }

	public static Bitmap getTextureFromBitmapResource(Context context, int resourceId)
    {
            Bitmap bitmap = null;
            try {
                    bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
                    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), yFlipMatrix, false);
            }
            finally {
                    if (bitmap != null) {
                            bitmap.recycle();
                    }
            }
    }
}
