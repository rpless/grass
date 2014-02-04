#version 330

in fragmentData {
  vec4 color;
} frag;

void main() {
  gl_FragColor = frag.color;
}