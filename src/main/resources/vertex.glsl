#version 330

layout(location = 0) in vec4 position;

uniform mat4 modelMatrix;
uniform mat4 perspectiveMatrix;

void main() {
  vec4 temp = modelMatrix * position;
  gl_Position = perspectiveMatrix * temp;
}