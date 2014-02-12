#version 330

layout(triangles, invocations = 1) in;
in vec4 vColor[];

uniform mat4 modelMatrix;
uniform mat4 cameraMatrix;
uniform mat4 perspectiveMatrix;

layout(triangle_strip, max_vertices=6) out;

out vec4 gColor;

void main() {
  for (int i = 0; i < gl_in.length(); i++) {
    gl_Position = perspectiveMatrix * cameraMatrix * modelMatrix * gl_in[i].gl_Position;
    gColor = vColor[i];
    EmitVertex();
  }
  EndPrimitive();

  float R = 0.4;
  float S = 0.4;
  vec4 A = gl_in[0].gl_Position;
  vec4 B = gl_in[1].gl_Position;
  vec4 C = gl_in[2].gl_Position;
  vec4 bary = A + (R * (B - A)) + (S * (C - A));

  // Emit coordinates
  gl_Position = perspectiveMatrix * cameraMatrix * modelMatrix * bary;
  gColor = vColor[0] + vec4(0.4, 0, 0, 0);
  EmitVertex();
  gl_Position = perspectiveMatrix * cameraMatrix * modelMatrix * (bary + vec4(0.1, 0, 0, 0));
  gColor = vColor[0] + vec4(0.4, 0, 0, 0);
  EmitVertex();
  gl_Position = perspectiveMatrix * cameraMatrix * modelMatrix * (bary + vec4(0.1, 0.1, 0, 0));
  gColor = vColor[0] + vec4(0.4, 0, 0, 0);
  EmitVertex();
  EndPrimitive();
}