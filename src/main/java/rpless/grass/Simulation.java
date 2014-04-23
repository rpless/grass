package rpless.grass;

import rpless.grass.gl.shader.*;
import rpless.grass.math.Matrix4f;
import rpless.grass.math.Matrix4fUtil;
import rpless.grass.mesh.Mesh;
import rpless.grass.mesh.MeshFactory;
import rpless.grass.mesh.MeshFormat;
import rpless.grass.window.SimulationWindow;

import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * The {@code Simulation} is a {@link javax.media.opengl.GLEventListener} that implements the main
 * rendering logic for the simulation.
 */
public class Simulation implements GLEventListener {

    private static String readFile(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            StringBuilder builder = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                builder.append(line).append("\n");
                line = reader.readLine();
            }
            return builder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Failed to find file: " + filename);
    }

    // Ground Shader
    private final String groundVertexPath = readFile("src//main//resources//ground-vertex.glsl");
    private final String fragmentVertexPath = readFile("src//main//resources//fragment.glsl");
    private ShaderProgram groundShaderProgram;

    // Grass Shader
    private final String grassVertexPath = readFile("src//main//resources//pass-through-vertex.glsl");
    private final String grassGeometryPath = readFile("src//main//resources//grass.glsl");
    private final String grassFragmentPath = fragmentVertexPath;
    private ShaderProgram grassShaderProgram;

    private Camera camera = new Camera();
    private Mesh ground;

    public Simulation(SimulationWindow window) {
        MouseHandler mouseHandler = new MouseHandler(window, camera);
        KeyHandler keyHandler = new KeyHandler(window, camera);
        window.addGLEventListener(this);
        window.addKeyListener(keyHandler);
        window.addMouseListener(mouseHandler);
        window.setPointerVisible(false);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL3.GL_DEPTH_TEST);
        gl.glDepthFunc(GL3.GL_LEQUAL);
        gl.glEnable(GL3.GL_DEPTH_CLAMP);
        gl.glEnable(GL3.GL_CULL_FACE);
        gl.glCullFace(GL3.GL_BACK);
        gl.glFrontFace(GL3.GL_CW);

        groundShaderProgram = ShaderProgramFactory.makeShader(gl, new VertexShader(groundVertexPath), new FragmentShader(fragmentVertexPath));
        groundShaderProgram.useProgram(gl);
        groundShaderProgram.uniform(gl, "perspectiveMatrix", Matrix4fUtil.perspective(45, SimulationWindow.WIDTH / SimulationWindow.HEIGHT, 0.1f, 100.0f));

        ground = MeshFactory.createMesh(gl, Data.vertexData, Data.indexData,
                new MeshFormat(groundShaderProgram.getAttributeLocation("position"), 4, GL3.GL_FLOAT, 0, false));
        groundShaderProgram.disuseProgram(gl);


        grassShaderProgram = ShaderProgramFactory.makeShader(gl, new VertexShader(grassVertexPath),
                                                                 new GeometryShader(grassGeometryPath),
                                                                 new FragmentShader(grassFragmentPath));
        grassShaderProgram.useProgram(gl);
        groundShaderProgram.uniform(gl, "perspectiveMatrix", Matrix4fUtil.perspective(45, SimulationWindow.WIDTH / SimulationWindow.HEIGHT, 0.1f, 100.0f));
        grassShaderProgram.disuseProgram(gl);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);
        Matrix4f cameraTransform = camera.lookAt();
        Matrix4f modelTransform = Matrix4fUtil.translate(0, -0.15f, -0.5f);

        groundShaderProgram.useProgram(gl);
        groundShaderProgram.uniform(gl, "cameraMatrix", cameraTransform);
        groundShaderProgram.uniform(gl, "modelMatrix", modelTransform);
        ground.enable(gl);
        ground.display(gl);

        grassShaderProgram.useProgram(gl);
        grassShaderProgram.uniform(gl, "cameraMatrix", cameraTransform);
        grassShaderProgram.uniform(gl, "modelMatrix", modelTransform);
        ground.display(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL3 gl = drawable.getGL().getGL3();
        groundShaderProgram.uniform(gl, "perspectiveMatrix", Matrix4fUtil.perspective(45, ((float) width) / ((float) height), 0.1f, 100.0f));
        grassShaderProgram.uniform(gl, "perspectiveMatrix", Matrix4fUtil.perspective(45, ((float) width) / ((float) height), 0.1f, 100.0f));
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        ground.delete(gl);
        groundShaderProgram.disuseProgram(gl);
        groundShaderProgram.delete(gl);
        grassShaderProgram.disuseProgram(gl);
        grassShaderProgram.delete(gl);
    }
}