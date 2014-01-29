package rpless.grass;

import com.jogamp.common.nio.Buffers;
import rpless.grass.gl.buffers.Mesh;
import rpless.grass.gl.buffers.MeshFactory;
import rpless.grass.gl.buffers.MeshFormat;
import rpless.grass.gl.shader.FragmentShader;
import rpless.grass.gl.shader.ShaderProgram;
import rpless.grass.gl.shader.ShaderProgramFactory;
import rpless.grass.gl.shader.VertexShader;
import rpless.grass.math.Matrix4fUtil;
import rpless.grass.math.Vector3f;
import rpless.grass.window.SimulationWindow;

import javax.media.opengl.GL4;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import java.nio.FloatBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The {@code SimulationRenderer} is a {@link javax.media.opengl.GLEventListener} that implements the main
 * rendering logic for the simulation.
 */
public class SimulationRenderer implements GLEventListener {

    private final Path vertexShaderPath = Paths.get("src", "main", "resources", "vertex.glsl");
    private final Path fragmentShaderPath = Paths.get("src", "main", "resources", "fragment.glsl");

    private ShaderProgram shaderProgram;
    private Mesh mesh;

    @Override
    public void init(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL4.GL_DEPTH_TEST);
        gl.glDepthFunc(GL4.GL_LEQUAL);

        shaderProgram = ShaderProgramFactory.makeShader(gl, new VertexShader(vertexShaderPath), new FragmentShader(fragmentShaderPath));
        shaderProgram.useProgram(gl);

        mesh = MeshFactory.createMesh(gl, Data.vertexData, Data.indexData,
                new MeshFormat(shaderProgram.getAttributeLocation("position"), 4, GL4.GL_FLOAT, 0, false),
                new MeshFormat(shaderProgram.getAttributeLocation("color"), 4, GL4.GL_FLOAT, 4 * Buffers.SIZEOF_FLOAT, false));
        mesh.enable(gl);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        gl.glClear(GL4.GL_COLOR_BUFFER_BIT | GL4.GL_DEPTH_BUFFER_BIT);
        shaderProgram.uniform(gl, "perspectiveMatrix", Matrix4fUtil.perspective(45, SimulationWindow.WIDTH / SimulationWindow.HEIGHT, 0.1f, 100.0f));
        shaderProgram.uniform(gl, "modelMatrix", Matrix4fUtil.translate(0, 0, -5).multiply(Matrix4fUtil.rotateY(45)));
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