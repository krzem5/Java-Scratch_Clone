import os
import subprocess
import sys
import zipfile



if (os.path.exists("build")):
	dl=[]
	for r,ndl,fl in os.walk("build"):
		dl=[os.path.join(r,k) for k in ndl]+dl
		for f in fl:
			os.remove(os.path.join(r,f))
	for k in dl:
		os.rmdir(k)
else:
	os.mkdir("build")
cd=os.getcwd()
os.chdir("src")
jfl=[]
for r,_,fl in os.walk("."):
	for f in fl:
		if (f[-5:]==".java"):
			jfl.append(os.path.join(r,f))
if (subprocess.run(["javac","-d","../build"]+jfl).returncode!=0):
	sys.exit(1)
os.chdir(cd)
with zipfile.ZipFile("build/scratch_clone.jar","w") as zf:
	print("Writing: META-INF/MANIFEST.MF")
	zf.write("manifest.mf",arcname="META-INF/MANIFEST.MF")
	for r,_,fl in os.walk("build"):
		r=r.replace("\\","/").strip("/")+"/"
		for f in fl:
			if (f[-6:]==".class"):
				print(f"Writing: {(r+f)[6:]}")
				zf.write(r+f,(r+f)[6:])
	for r,_,fl in os.walk("rsrc"):
		r=r.replace("\\","/").strip("/")+"/"
		for f in fl:
			if (f[-4:]==".png"):
				print(f"Writing: {r+f}")
				zf.write(r+f,r+f)
if ("--run" in sys.argv):
	subprocess.run(["java","-jar","build/scratch_clone.jar","test.xml"])
