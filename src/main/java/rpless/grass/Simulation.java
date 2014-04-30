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
import java.nio.file.Paths;

/**
 * The {@code Simulation} is a {@link javax.media.opengl.GLEventListener} that implements the main
 * rendering logic for the simulation.
 */
public class Simulation implements GLEventListener {

    // Ground Shader
    private final Shader groundVertexShader = new VertexShader(Paths.get("src", "main", "resources", "ground-vertex.glsl"));
    private final Shader groundFragmentShader = new FragmentShader(Paths.get("src", "main", "resources", "fragment.glsl"));
    private ShaderProgram groundShaderProgram;

    // Grass Shader

    private final Shader grassVertexShader = new VertexShader(Paths.get("src", "main", "resources", "pass-through-vertex.glsl"));
    private final Shader grassGeometryShader= new GeometryShader(Paths.get("src", "main", "resources", "grass.glsl"));
    private final Shader grassFragmentShader = groundFragmentShader;
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

        groundShaderProgram = ShaderProgramFactory.makeShader(gl, groundVertexShader, groundFragmentShader);
        groundShaderProgram.useProgram(gl);
        groundShaderProgram.uniform(gl, "perspectiveMatrix", Matrix4fUtil.perspective(45, SimulationWindow.WIDTH / SimulationWindow.HEIGHT, 0.1f, 100.0f));

        ground = MeshFactory.createMesh(gl, Data.vertexData, Data.indexData,
                new MeshFormat(groundShaderProgram.getAttributeLocation("position"), 4, GL3.GL_FLOAT, 0, false));
        groundShaderProgram.disuseProgram(gl);


        grassShaderProgram = ShaderProgramFactory.makeShader(gl, grassVertexShader, grassGeometryShader, grassFragmentShader);
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