#version 330

layout(triangles, invocations = 1) in;
in vec4 vColor[];

uniform mat4 modelMatrix;
uniform mat4 cameraMatrix;
uniform mat4 perspectiveMatrix;

layout(triangle_strip, max_vertices=303) out;

out vec4 gColor;

// Helper Function Signatures
float rand(vec2 co);
vec4 bary(float R, float S);
void grassBlade(vec4 center, mat4 PCMMatrix);

void main() {
  mat4 PCMMatrix = (perspectiveMatrix * cameraMatrix) * modelMatrix; // Precompute the perspective/camera/model matrix

  // Emit the original triangle
  for (int i = 0; i < gl_in.length(); i++) {
    gl_Position = PCMMatrix * gl_in[i].gl_Position;
    gColor = vColor[i];
    EmitVertex();
  }
  EndPrimitive();

  for (int i = 0; i < 25; i++) {
    vec4 coordinate = bary(rand(vec2(3 * i, i / 3)), rand(vec2(50 * i + 1, sin(i) * 15)));
    grassBlade(coordinate, PCMMatrix);
  }
}

void grassBlade(vec4 center, mat4 PCMMatrix) {
  vec4 A = center + vec4(0, 0, 0.01, 0);
  vec4 B = center + vec4(0.0025, 0, 0, 0);
  vec4 C = center - vec4(0, 0, 0.01, 0);
  vec4 D = center - vec4(0.0025, 0, 0, 0);
  vec4 E = center + vec4(0, 0.1, 0, 0);

  // Emit coordinates
  gColor = vec4(0.4, 0, 0, 0);

  gl_Position = PCMMatrix * A;
  EmitVertex();
  gl_Position = PCMMatrix * D;
  EmitVertex();
  gl_Position = PCMMatrix * E;
  EmitVertex();
  EndPrimitive();

  gl_Position = PCMMatrix * A;
  EmitVertex();
  gl_Position = PCMMatrix * B;
  EmitVertex();
  gl_Position = PCMMatrix * E;
  EmitVertex();
  EndPrimitive();

  gl_Position = PCMMatrix * C;
  EmitVertex();
  gl_Position = PCMMatrix * D;
  EmitVertex();
  gl_Position = PCMMatrix * E;
  EmitVertex();
  EndPrimitive();

  gl_Position = PCMMatrix * C;
  EmitVertex();
  gl_Position = PCMMatrix * B;
  EmitVertex();
  gl_Position = PCMMatrix * E;
  EmitVertex();
  EndPrimitive();
}

float rand(vec2 co) {
  return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

vec4 bary(float R, float S) {
  if (R + S >= 1) {
    R = 1 - R;
    S = 1 - S;
  }
  return gl_in[0].gl_Position + (R * (gl_in[1].gl_Position - gl_in[0].gl_Position)) + (S * (gl_in[2].gl_Position - gl_in[0].gl_Position));
}