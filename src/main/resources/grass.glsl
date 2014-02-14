#version 330

layout(triangles, invocations = 1) in;
in vec4 vColor[];

uniform mat4 modelMatrix;
uniform mat4 cameraMatrix;
uniform mat4 perspectiveMatrix;

layout(triangle_strip, max_vertices=6) out;

out vec4 gColor;

vec4 bary(float R, float S) {
  if (R + S >= 1) {
    R = 1 - R;
    S = 1 - S;
  }
  return gl_in[0].gl_Position + (R * (gl_in[1].gl_Position - gl_in[0].gl_Position)) + (S * (gl_in[2].gl_Position - gl_in[0].gl_Position));
}

void main() {
  mat4 PCMMatrix = (perspectiveMatrix * cameraMatrix) * modelMatrix;
  for (int i = 0; i < gl_in.length(); i++) {
    gl_Position = PCMMatrix * gl_in[i].gl_Position;
    gColor = vColor[i];
    EmitVertex();
  }
  EndPrimitive();

  vec4 coordinate = bary(0.5, 0.5);

  // Emit coordinates
  gColor = vColor[0] + vec4(0.4, 0, 0, 0);
  
  gl_Position = PCMMatrix * coordinate;
  EmitVertex();
  gl_Position = PCMMatrix * (coordinate - vec4(0.01, 0, 0, 0));
  EmitVertex();
  gl_Position = PCMMatrix * (coordinate + vec4(-0.005, 0.1, 0, 0));
  EmitVertex();
  EndPrimitive();
}