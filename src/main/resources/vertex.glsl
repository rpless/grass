#version 330

layout(location = 0) in vec4 position;
layout(location = 1) in vec4 color;

uniform mat4 modelMatrix;
uniform mat4 cameraMatrix;
uniform mat4 perspectiveMatrix;

smooth out vec4 fragmentColor;

void main() {
  vec4 temp = modelMatrix * position;
  temp = cameraMatrix * temp;
  gl_Position = perspectiveMatrix * temp;
  fragmentColor = color;
}