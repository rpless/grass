#version 330

layout(triangles) in;
in vertexData {
  vec4 color;
} vertices[];

layout(triangle_strip, max_vertices=3) out;
out fragmentData {
  vec4 color;
} fragment;

void main() {
  for (int i = 0; i <gl_in.length(); i++) {
    gl_Position = gl_in[i].gl_Position;
    fragment.color = vertices[i].color;
    EmitVertex();
  }
  EndPrimitive();
}