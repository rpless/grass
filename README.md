#Grass Simulation

The goal of this project is to write a proof of concept geometry shader that generates grass.

## Generating Randomness on the GPU
To generate randomness I ported Mutsuo Saito's and Makoto Matsumoto's TinyMT mersenne twistter to GLSL.
I'm not quite sure if I nailed all of the casting that goes on in TinyMT, because GLSL isn't as forgiving with its Type System.
The original implementation can be found [here](http://www.math.sci.hiroshima-u.ac.jp/~%20m-mat/MT/TINYMT/index.html).