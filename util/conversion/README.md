To run, from the src directory:

Compile:
javac -classpath <matlabroot>/extern/engines/java/jar/engine.jar ConvertToFmu.java

Run:
java -Djava.library.path=<matlabroot>/bin/glnxa64 -classpath .:<matlabroot>/extern/engines/java/jar/engine.jar ConvertToFmu <filepath>

Notes: Only Linux is supported. File has to copied into the same directory as the source file. MATLAB must be installed for Simulink conversion, and `omc` must be installed for OpenModelica conversion. For `.mo` files, class name must be equal to the file name without the extension.

--------------------------
References:
https://mathworks.com/products/simulink.html 2016-2017 The MathWorks, Inc.
https://openmodelica.org
https://smolang.org
