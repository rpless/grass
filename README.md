# Grass Simulation
The goal of this project is to write a proof of concept geometry shader that generates grass.

## Controls
- W: Move Forward
- S: Move Backward
- A: Strafe Left
- D: Strafe Right
- Escape: Close the simulation
- Mouse: Moves the view around

## Strategy 
The idea is to use barycentric coordinates and a Mersenne Twister to generate random points on each triangle.
From these random points, we can generate geometry that is shaped like blades of grass.

## Generating Randomness on the GPU
To generate randomness I ported Mutsuo Saito's and Makoto Matsumoto's TinyMT mersenne twistter to GLSL.
I'm not quite sure if I nailed all of the casting that goes on in TinyMT, because GLSL isn't as forgiving with its Type System.
The original implementation can be found [here](http://www.math.sci.hiroshima-u.ac.jp/~%20m-mat/MT/TINYMT/index.html).