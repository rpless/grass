package rpless.grass.gl.shader;

public abstract class Shader {
    private String source;
    private int handle;

    public Shader(String source) {
        this.source = source;
        this.handle = -1;
    }

    public String getSource() {
        return source;
    }

    public abstract int getType();

    int getHandle() {
        return handle;
    }

    void setHandle(int handle) {
        this.handle = handle;
    }
}