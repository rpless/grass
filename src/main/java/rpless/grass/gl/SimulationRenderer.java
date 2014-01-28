package rpless.grass.gl;

import com.jogamp.common.nio.Buffers;
import rpless.grass.gl.shader.FragmentShader;
import rpless.grass.gl.shader.ShaderProgram;
import rpless.grass.gl.shader.ShaderProgramFactory;
import rpless.grass.gl.shader.VertexShader;
import rpless.grass.math.Matrix4f;
import rpless.grass.math.Matrix4fUtil;

import javax.media.opengl.GL4;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SimulationRenderer implements GLEventListener {

    static final FloatBuffer vertices = FloatBuffer.wrap(new float[] {
            1.0f,  1.0f,  0.0f, 1.0f,
            -1.0f, 1.0f,  0.0f, 1.0f,
            1.0f,  -1.0f, 0.0f, 1.0f,
            -1.0f, -1.0f, 0.0f, 1.0f
    });
    private final Path vertexShaderPath = Paths.get("src", "main", "resources", "vertex.glsl");
    private final Path fragmentShaderPath = Paths.get("src", "main", "resources", "fragment.glsl");

    private ShaderProgram shaderProgram;
    private int vertexBufferHandle = -1;

    @Override
    public void init(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        ShaderProgramFactory shaderProgramFactory = new ShaderProgramFactory();
        shaderProgram = shaderProgramFactory.makeShader(gl, new VertexShader(vertexShaderPath), new FragmentShader(fragmentShaderPath));
        shaderProgram.useProgram(gl);
        gl.glEnableVertexAttribArray(shaderProgram.getAttributeLocation("position"));

        vertexBufferHandle = createVertexBuffer(gl, vertices);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        gl.glClear(GL4.GL_COLOR_BUFFER_BIT | GL4.GL_DEPTH_BUFFER_BIT);
        gl.glUniformMatrix4fv(shaderProgram.getUniformLocation("perspectiveMatrix"), 1, false, perspective(45, 800.0f / 600.0f, 0.1f, 100.0f).toArray(), 0);
        gl.glUniformMatrix4fv(shaderProgram.getUniformLocation("modelMatrix"), 1, false, Matrix4fUtil.translate(0, 0, -6).toArray(), 0);

        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vertexBufferHandle);
        gl.glVertexAttribPointer(shaderProgram.getAttributeLocation("position"), 4, GL4.GL_FLOAT, false, 0, 0);
        gl.glDrawArrays(GL4.GL_TRIANGLE_STRIP, 0, 4);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}

    @Override
    public void dispose(GLAutoDrawable drawable) {}

    private int createVertexBuffer(GL4 gl, FloatBuffer buffer) {
        return createBuffer(gl, buffer, GL4.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT, GL4.GL_STATIC_DRAW);
    }

    private int createBuffer(GL4 gl, Buffer buffer, int target, int typeSize, int renderingHint) {
        int handle = this.generateBufferHandle(gl);
        gl.glBindBuffer(target, handle);
        gl.glBufferData(target, buffer.capacity() * typeSize, buffer, renderingHint);
        gl.glBindBuffer(target, handle);
        return handle;
    }

    private int generateBufferHandle(GL4 gl) {
        IntBuffer handleBuffer = Buffers.newDirectIntBuffer(1);
        gl.glGenBuffers(1, handleBuffer);
        return handleBuffer.get();
    }


    public Matrix4f perspective(float fov, float aspect, float zNear, float zFar) {
        float scale = (float) Math.tan(Math.toRadians(fov / 2)) * zNear;
        float r = aspect * scale;
        return frustum(-r, r, -scale, scale, zNear, zFar);
    }

    private Matrix4f frustum(float left, float right, float bottom, float top, float near, float far) {
        Matrix4f mat = new Matrix4f();
        mat.set(0, 0, (2 * near) / (right - left));
        mat.set(1, 1, (2 * near) / (top - bottom));
        mat.set(2, 0, (right + left) / (right - left));
        mat.set(2, 1, (top + bottom) / (top - bottom));
        mat.set(2, 2, -(far + near) / (far - near));
        mat.set(2, 3, -1);
        mat.set(3, 2, (-2 * far * near) / (far - near));
        return mat;
    }
}