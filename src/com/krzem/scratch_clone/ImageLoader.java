package com.krzem.scratch_clone;



import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
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



	public BufferedImage get(String n){
		BufferedImage o=this.imgl.get(n);
		if (o==null){
			try{
				InputStream is=ImageLoader.class.getResourceAsStream(ASSETS_PATH+n);
				BufferedImage bi=(BufferedImage)ImageIO.read(is);
				is.close();
				o=SCREEN.getConfigurations()[0].createCompatibleImage(bi.getWidth(),bi.getHeight(),bi.getTransparency());
				Graphics ig=o.createGraphics();
				ig.drawImage(bi,0,0,null);
				ig.dispose();
				this.imgl.put(n,o);
				IO.dump_log("Loaded Image: "+n);
			}
			catch (IOException e){
				IO.dump_error(e);
			}
		}
		return o;
	}
}
