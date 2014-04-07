#version 330

layout(triangles, invocations = 1) in;

uniform mat4 modelMatrix;
uniform mat4 cameraMatrix;
uniform mat4 perspectiveMatrix;

layout(triangle_strip, max_vertices=180) out; // (9 * 20)

out vec4 Color;

// Helper Function Signatures
void initMT(uint seed, uint m1, uint m2, uint tmat);
float random();
vec4 randomBarycentricCoordinate();
void grassBlade(vec4 center, mat4 PCMMatrix);
void createTriangle(mat4 PCMMatrix, vec4 A, vec4 B, vec4 C);


void main() {
  mat4 PCMMatrix = (perspectiveMatrix * cameraMatrix) * modelMatrix; // Precompute the perspective/camera/model matrix
  uint instanceFactor = uint(gl_PrimitiveID);
  initMT(234340U * instanceFactor, 0xf50a1d49U, 0xffa8ffebU, 0x0bf2bfffU);

  for (int i = 0; i < 20; i++) {
    grassBlade(randomBarycentricCoordinate(), PCMMatrix);
  }
}

// Generate a single blade of grass
// center - The vector representing the point that center of the base of blade of grass should be placed
// PCMMatrix - The matrix that is a precompution of the multiplication of the Projection, Camera, and Model Matrices
void grassBlade(vec4 center, mat4 PCMMatrix) {
  vec4 A = center + vec4(0.0015f, 0, 0, 0);
  vec4 B = center + vec4(-0.0015f, 0, 0, 0);
  vec4 C = center + vec4(0.0015f, 0.05f, 0, 0);
  vec4 D = center + vec4(-0.0015f, 0.05f, 0, 0);
  vec4 E = center + vec4(0, 0.15f, 0, 0);

  // Emit coordinates
  Color = vec4(0, 0.55, 0.05, 0);
  createTriangle(PCMMatrix, A, B, C);
  createTriangle(PCMMatrix, C, D, B);
  createTriangle(PCMMatrix, C, D, E);
}

// Create a vertex with the given matrix and three vertexes
void createTriangle(mat4 PCMMatrix, vec4 A, vec4 B, vec4 C) {
    gl_Position = PCMMatrix * A;
    EmitVertex();
    gl_Position = PCMMatrix * B;
    EmitVertex();
    gl_Position = PCMMatrix * C;
    EmitVertex();
    EndPrimitive();
}

// Produce a psuedo random point that exists on the current primitive.
vec4 randomBarycentricCoordinate() {
  float R = random();
  float S = random();
  if (R + S >= 1) {
    R = 1 - R;
    S = 1 - S;
  }
  return gl_in[0].gl_Position + (R * (gl_in[1].gl_Position - gl_in[0].gl_Position)) + (S * (gl_in[2].gl_Position - gl_in[0].gl_Position));
}

// TinyMT Implementation
// Represents the Mersenne Twistter's Internal State
struct mersenneTwister {
  uint status[4];
  uint m1;
  uint m2;
  uint tmat;
} MT;

// Initialize the Mersenne Twistter.
void initMT(uint seed, uint m1, uint m2, uint tmat) {
  MT.status[0] = seed;
  MT.status[1] = m1;
  MT.status[2] = m2;
  MT.status[3] = tmat;

  for (int i = 1; i < 8; i++) {
    MT.status[i & 3] ^= uint(i) + 1812433253U * MT.status[(i - 1) & 3] ^ (MT.status[(i - 1) & 3] >> 30);
  }
  for (int i = 0; i < 8; i++) {
    random();
  }
}

// Produce a psuedo-random float value on the range [0, 1]
float random() {
  uint x = MT.status[3];
  uint y = (MT.status[0] & 0x7fffffffU) ^ MT.status[1] ^ MT.status[2];
  x ^= (x << 1);
  y ^= (y << 1) ^ x;
  MT.status[0] = MT.status[1];
  MT.status[1] = MT.status[2];
  MT.status[2] = x ^ (y << 10);
  MT.status[3] = y;
  MT.status[1] ^= - (y & 1U) & MT.m1;
  MT.status[2] ^= - (y & 1U) & MT.m2;

  uint t0, t1;
  t0 = MT.status[3];
  t1 = MT.status[0] + (MT.status[2] >> 8);
  t0 ^= t1;
  t0 ^= -(t1 & 1U) & MT.tmat;

  return t0 * (1.0f / 4294967296.0f);
}