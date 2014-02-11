#version 330

layout(triangles) in;
in vec4 vColor[];

uniform mat4 modelMatrix;
uniform mat4 cameraMatrix;
uniform mat4 perspectiveMatrix;

layout(triangle_strip, max_vertices=3) out;

out vec4 gColor;

void main() {
  for (int i = 0; i < gl_in.length(); i++) {
    gl_Position = perspectiveMatrix * cameraMatrix * modelMatrix * gl_in[i].gl_Position;
    gColor = vColor[i];
    EmitVertex();
  }
  EndPrimitive();
}