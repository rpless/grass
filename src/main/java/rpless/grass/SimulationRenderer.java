package rpless.grass;

import com.jogamp.common.nio.Buffers;
import rpless.grass.gl.buffers.Mesh;
import rpless.grass.gl.buffers.MeshFactory;
import rpless.grass.gl.buffers.MeshFormat;
import rpless.grass.gl.shader.FragmentShader;
import rpless.grass.gl.shader.ShaderProgram;
import rpless.grass.gl.shader.ShaderProgramFactory;
import rpless.grass.gl.shader.VertexShader;
import rpless.grass.math.Matrix4f;
import rpless.grass.math.Matrix4fUtil;
import rpless.grass.window.SimulationWindow;

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

            -1.0f, 1.0f,  0.0f, 1.0f,
            1.0f,  -1.0f, 0.0f, 1.0f,
            -1.0f, -1.0f, 0.0f, 1.0f
    });
    private final Path vertexShaderPath = Paths.get("src", "main", "resources", "vertex.glsl");
    private final Path fragmentShaderPath = Paths.get("src", "main", "resources", "fragment.glsl");

    private ShaderProgram shaderProgram;
    private Mesh mesh;

    @Override
    public void init(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        ShaderProgramFactory shaderProgramFactory = new ShaderProgramFactory();
        shaderProgram = shaderProgramFactory.makeShader(gl, new VertexShader(vertexShaderPath), new FragmentShader(fragmentShaderPath));
        shaderProgram.useProgram(gl);

        MeshFactory meshFactory = new MeshFactory();
        mesh = meshFactory.createMesh(gl, vertices, new MeshFormat(shaderProgram.getAttributeLocation("position"), 4, GL4.GL_FLOAT, 0, false));
        mesh.enable(gl);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        gl.glClear(GL4.GL_COLOR_BUFFER_BIT | GL4.GL_DEPTH_BUFFER_BIT);
        shaderProgram.uniform(gl, "perspectiveMatrix", Matrix4fUtil.perspective(45, SimulationWindow.WIDTH / SimulationWindow.HEIGHT, 0.1f, 100.0f));
        shaderProgram.uniform(gl, "modelMatrix", Matrix4fUtil.translate(0, 0, -6));
        mesh.display(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL4 gl = drawable.getGL().getGL4();
        shaderProgram.uniform(gl, "perspectiveMatrix", Matrix4fUtil.perspective(45, ((float) width) / ((float) height), 0.1f, 100.0f));
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        mesh.delete(gl);
        shaderProgram.disuseProgram(gl);
        shaderProgram.delete(gl);
    }
}