package com.krzem.scratch;



import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;



public class ImageLoader extends Constants{
	public Main cls;
	private Map<String,BufferedImage> imgl;



	public ImageLoader(Main cls){
		this.cls=cls;
		this.imgl=new HashMap<String,BufferedImage>();
	}



	public void load(String d){
		this._load(d,"");
	}



	public BufferedImage get(String n){
		return this.imgl.get(n);
	}



	private void _load(String d,String p){
		for (File f:new File(d).listFiles()){
			try{
				if (f.getName().endsWith(".png")){
					BufferedImage bi=(BufferedImage)ImageIO.read(f);
					BufferedImage i=SCREEN.getConfigurations()[0].createCompatibleImage(bi.getWidth(),bi.getHeight(),bi.getTransparency());
					Graphics ig=i.createGraphics();
					ig.drawImage(bi,0,0,null);
					ig.dispose();
					this.imgl.put(p+f.getName(),i);
					IO.dump_log("Loaded Image: "+p+f.getName());
				}
				else if (f.getName().indexOf(".")==-1){
					this._load(d+"\\"+f.getName(),p+f.getName()+"/");
				}
			}
			catch (IOException e){
				IO.dump_error(e);
			}
		}
	}
}