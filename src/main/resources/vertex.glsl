#version 330

layout(location = 0) in vec4 position;
layout(location = 1) in vec4 color;

out vertexData {
  vec4 color;
} vertex;

void main() {
  gl_Position = position;
  vertex.color = color;
}