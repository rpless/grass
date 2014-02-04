#version 330

layout(location = 0) in vec4 position;
layout(location = 1) in vec4 color;

uniform mat4 modelMatrix;
uniform mat4 cameraMatrix;
uniform mat4 perspectiveMatrix;

out vertexData {
  vec4 color;
} vertex;

void main() {
  gl_Position = perspectiveMatrix * cameraMatrix * modelMatrix * position;
  vertex.color = color;
}