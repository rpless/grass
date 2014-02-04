#version 330

layout(triangles) in;
in vertexData {
  vec4 color;
} vertices[];

uniform mat4 modelMatrix;
uniform mat4 cameraMatrix;
uniform mat4 perspectiveMatrix;

layout(triangle_strip, max_vertices=6) out;

out fragmentData {
  vec4 color;
} fragment;

void main() {
  for (int i = 0; i < gl_in.length(); i++) {
    gl_Position = perspectiveMatrix * cameraMatrix * modelMatrix * gl_in[i].gl_Position;
    fragment.color = vertices[i].color;
    EmitVertex();
  }
  EndPrimitive();

  for (int i = 0; i < gl_in.length(); i++) {
    mat4 transform = mat4(modelMatrix);
    transform[3][0] += 4;
    gl_Position = perspectiveMatrix * cameraMatrix * transform * gl_in[i].gl_Position;
    fragment.color = vertices[i].color + vec4(0.5, 0.0, 0.0, 0.0);
    EmitVertex();
  }
  EndPrimitive();
}