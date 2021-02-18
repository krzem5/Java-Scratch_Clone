@echo off
cls
if exist build rmdir /s /q build
mkdir build
cd src
javac -d ../build com/krzem/scratch_clone/blocks/* com/krzem/scratch_clone/Main.java&&jar cvmf ../manifest.mf ../build/scratch_clone.jar -C ../build *&&goto run
cd ..
goto end
:run
cd ..
pushd "build"
for /D %%D in ("*") do (
	rd /S /Q "%%~D"
)
for %%F in ("*") do (
	if /I not "%%~nxF"=="scratch_clone.jar" del "%%~F"
)
popd
cls
java -jar build/scratch_clone.jar test.xml
:end
