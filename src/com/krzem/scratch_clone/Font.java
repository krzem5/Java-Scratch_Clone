package com.krzem.scratch_clone;



import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;



public class Font extends Constants{
	public Main cls;



	public Font(Main cls){
		this.cls=cls;
	}



	public void draw(Graphics g,String txt,int x,int y,int s,Color c){
		txt=this._encode(txt);
		for (int i=0;i<txt.length();i++){
			String fp=String.format("%s%s.png",FONT_TEX_PATH,String.valueOf(txt.charAt(i)).replace(FONT_UNKNOWN_CHAR,FONT_UNKNOWN_CHAR_NAME).replace(FONT_SPACE_CHAR,FONT_SPACE_CHAR_NAME));
			BufferedImage b=this.cls.IMAGE_LOADER.get(fp);
			BufferedImage l=this._tint(b,s,c);
			g.drawImage(l,x,y,l.getWidth(),l.getHeight());
			x+=l.getWidth()+FONT_CHAR_SPACE*(s/b.getHeight());
		}
	}



	private String _encode(String txt){
		return new String(txt.getBytes(java.nio.charset.StandardCharsets.ISO_8859_1),java.nio.charset.StandardCharsets.UTF_8).replaceAll(String.format("[^%s]",FONT_CHARS),FONT_UNKNOWN_CHAR);
	}



	private BufferedImage _tint(BufferedImage i,int s,Color c){
		s/=i.getHeight();
		BufferedImage o=new BufferedImage(i.getWidth()*s,i.getHeight()*s,BufferedImage.TRANSLUCENT);
		Graphics2D g=o.createGraphics();
		g.setColor(c);
		for (int x=0;x<i.getWidth();x++){
			for (int y=0;y<i.getHeight();y++){
				int a=(i.getRGB(x,y)>>24)&255;
				if (a>0){
					g.fillRect(x*s,y*s,s,s);
				}
			}
		}
		g.dispose();
		return o;
	}
}
