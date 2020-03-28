echo off
echo NUL>_.class&&del /s /f /q *.class
cls
javac com/krzem/scratch_clone/Main.java&&java com/krzem/scratch_clone/Main test.xml
start /min cmd /c "echo NUL>_.class&&del /s /f /q *.class"