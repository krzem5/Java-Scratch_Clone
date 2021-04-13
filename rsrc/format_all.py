from PIL import Image
import os



for p in os.listdir("connector/"):
	if (p.endswith("-alpha.png")):
		os.remove("connector/"+p)
for p in os.listdir("block/"):
	if (p.endswith("-alpha.png")):
		os.remove("block/"+p)
for p in os.listdir("connector/"):
	if (p.endswith(".png")):
		img=Image.open("connector/"+p)
		img=img.convert("RGBA")
		ndt=[]
		for i in img.getdata():
			ndt+=[(i[0],i[1],i[2],min(200,i[3]))]
		img.putdata(ndt)
		img.save("connector/"+p.replace(".png","-alpha.png"),"PNG")
for p in os.listdir("block/"):
	if (p.endswith(".png")):
		img=Image.open("block/"+p)
		img=img.convert("RGBA")
		ndt=[]
		for i in img.getdata():
			ndt+=[(i[0],i[1],i[2],min(200,i[3]))]
		img.putdata(ndt)
		img.save("block/"+p.replace(".png","-alpha.png"),"PNG")
