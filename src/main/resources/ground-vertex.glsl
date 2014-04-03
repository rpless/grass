#version 330

layout(location = 0) in vec4 position;

uniform mat4 modelMatrix;
uniform mat4 cameraMatrix;
uniform mat4 perspectiveMatrix;

out vec4 Color;

void main() {
  gl_Position = ((perspectiveMatrix * cameraMatrix) * modelMatrix) * position;
  Color = vec4(0.60f,  0.45f,  0.3f,  1.0f);
}