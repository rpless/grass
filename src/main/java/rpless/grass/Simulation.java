package rpless.grass;

import com.jogamp.common.nio.Buffers;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;
import rpless.grass.gl.shader.FragmentShader;
import rpless.grass.gl.shader.ShaderProgram;
import rpless.grass.gl.shader.ShaderProgramFactory;
import rpless.grass.gl.shader.VertexShader;
import rpless.grass.input.KeyInputAction;
import rpless.grass.input.MouseMotionInputAction;
import rpless.grass.input.recognizers.KeyCodeRecognizer;
import rpless.grass.input.recognizers.KeyPressRecognizer;
import rpless.grass.input.recognizers.MouseMotionRecognizer;
import rpless.grass.math.Matrix4fUtil;
import rpless.grass.mesh.Mesh;
import rpless.grass.mesh.MeshFactory;
import rpless.grass.mesh.MeshFormat;
import rpless.grass.window.SimulationWindow;

import javax.media.opengl.GL4;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The {@code Simulation} is a {@link javax.media.opengl.GLEventListener} that implements the main
 * rendering logic for the simulation.
 */
public class Simulation extends MouseAdapter implements GLEventListener, KeyListener {

    private final Path vertexShaderPath = Paths.get("src", "main", "resources", "vertex.glsl");
    private final Path fragmentShaderPath = Paths.get("src", "main", "resources", "fragment.glsl");

    private SimulationWindow window;
    private Camera camera = new Camera();
    private ShaderProgram shaderProgram;
    private Mesh mesh;

    // Input
    KeyInputAction moveLeft = new KeyInputAction(new KeyCodeRecognizer(KeyEvent.VK_A), new KeyPressRecognizer());
    KeyInputAction moveRight = new KeyInputAction(new KeyCodeRecognizer(KeyEvent.VK_D), new KeyPressRecognizer());
    KeyInputAction moveForward = new KeyInputAction(new KeyCodeRecognizer(KeyEvent.VK_W), new KeyPressRecognizer());
    KeyInputAction moveBack = new KeyInputAction(new KeyCodeRecognizer(KeyEvent.VK_S), new KeyPressRecognizer());
    KeyInputAction closeSimulation = new KeyInputAction(new KeyCodeRecognizer(KeyEvent.VK_ESCAPE), new KeyPressRecognizer());

    private boolean recentering = false;
    MouseMotionInputAction motion = new MouseMotionInputAction(new MouseMotionRecognizer());

    public Simulation(SimulationWindow window) {
        this.window = window;
        window.addGLEventListener(this);
        window.addKeyListener(this);
        window.addMouseListener(this);
        window.setPointerVisible(false);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL4.GL_DEPTH_TEST);
        gl.glDepthFunc(GL4.GL_LEQUAL);

        shaderProgram = ShaderProgramFactory.makeShader(gl, new VertexShader(vertexShaderPath), new FragmentShader(fragmentShaderPath));
        shaderProgram.useProgram(gl);
        shaderProgram.uniform(gl, "perspectiveMatrix", Matrix4fUtil.perspective(45, SimulationWindow.WIDTH / SimulationWindow.HEIGHT, 0.1f, 100.0f));

        mesh = MeshFactory.createMesh(gl, Data.vertexData, Data.indexData,
                new MeshFormat(shaderProgram.getAttributeLocation("position"), 4, GL4.GL_FLOAT, 0, false),
                new MeshFormat(shaderProgram.getAttributeLocation("color"), 4, GL4.GL_FLOAT, 4 * Buffers.SIZEOF_FLOAT, false));
        mesh.enable(gl);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        gl.glClear(GL4.GL_COLOR_BUFFER_BIT | GL4.GL_DEPTH_BUFFER_BIT);
        shaderProgram.uniform(gl, "cameraMatrix", camera.lookAt());
        shaderProgram.uniform(gl, "modelMatrix", Matrix4fUtil.translate(0, 0, -5).multiply(Matrix4fUtil.rotateY(45)));
        mesh.display(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL4 gl = drawable.getGL().getGL4();
        shaderProgram.uniform(gl, "perspectiveMatrix", Matrix4fUtil.perspective(60, ((float) width) / ((float) height), 0.1f, 100.0f));
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        mesh.delete(gl);
        shaderProgram.disuseProgram(gl);
        shaderProgram.delete(gl);
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (moveLeft.isDetected(event)) camera.strafeRight(-0.15f);
        if (moveRight.isDetected(event)) camera.strafeRight(0.15f);
        if (moveForward.isDetected(event)) camera.moveForward(0.175f);
        if (moveBack.isDetected(event)) camera.moveForward(-0.15f);
        if (closeSimulation.isDetected(event)) window.stop();
    }

    @Override
    public void keyReleased(KeyEvent event) {}

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        int dx = mouseEvent.getX() - 400;
        int dy = mouseEvent.getY() - 300;
        if (recentering && dx == 0 && dy == 0) {
            recentering = false;
        } else if (motion.isDetected(mouseEvent)) {
            camera.rotateX(dy > 0 ? -1 : 1);
            camera.rotateY(dx > 0 ? -1 : 1);
            recenter();
        }
    }

    private void recenter() {
        recentering = true;
        window.warpPointer(400, 300);
    }
}