Note: Only Linux is supported and MATLAB must be installed for Simulink conversion.
--------------------------
From the source directory:

Compile:
javac -classpath <matlabroot>/extern/engines/java/jar/engine.jar ConvertToFmu.java

Run:
java -Djava.library.path=<matlabroot>/bin/glnxa64 -classpath .:<matlabroot>/extern/engines/java/jar/engine.jar ConvertToFmu <filepath>

Note: File has to copied into the same directory as the source file.
Note: The file is not exported from inside the engine to the local folder.
Note: OpenModelica conversion does not work
--------------------------
References:
2016-2017 The MathWorks, Inc.
https://smolang.org
