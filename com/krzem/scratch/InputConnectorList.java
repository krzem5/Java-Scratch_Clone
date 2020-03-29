package com.krzem.scratch;



import java.awt.image.BufferedImage;
import java.util.ArrayList;



public class InputConnectorList extends Constants{
	public Main cls;
	public Block b;
	public String[] conns;



	public InputConnectorList(Main cls,Block b,String... conns){
		this.cls=cls;
		this.b=b;
		this.conns=conns;
	}



	public void draw(Graphics g,int x,int y){
		for (String c:this.conns){
			BufferedImage img=this.cls.IMAGE_LOADER.get(String.format("%s%s-%s-i.png",CONNECTOR_TEX_PATH,this.b.tex_name(),c));
			if (img==null){
				continue;
			}
			g.drawImage(img,x,y-img.getHeight(),img.getWidth(),img.getHeight());
			x+=img.getWidth()+CONNECTOR_GAP_SIZE;
		}
	}



	public void draw_a(Graphics g,int x,int y){
		for (String c:this.conns){
			BufferedImage img=this.cls.IMAGE_LOADER.get(String.format("%s%s-%s-i-alpha.png",CONNECTOR_TEX_PATH,this.b.tex_name(),c));
			if (img==null){
				continue;
			}
			g.drawImage(img,x,y-img.getHeight(),img.getWidth(),img.getHeight());
			x+=img.getWidth()+CONNECTOR_GAP_SIZE;
		}
	}



	public ArrayList<Rectangle> _get_shadow(int ox,int oy){
		ArrayList<Rectangle> sh=new ArrayList<Rectangle>();
		for (String c:this.conns){
			BufferedImage img=this.cls.IMAGE_LOADER.get(String.format("%s%s-%s-i.png",CONNECTOR_TEX_PATH,this.b.tex_name(),c));
			if (img==null){
				continue;
			}
			for (int x=0;x<img.getWidth();x++){
				for (int y=0;y<img.getHeight();y++){
					int a=(img.getRGB(x,y)>>24)&255;
					if (a>0){
						sh.add(new Rectangle(ox+x,oy-img.getHeight()+y,1,1));
					}
				}
			}
			ox+=img.getWidth()+CONNECTOR_GAP_SIZE;
		}
		return sh;
	}



	@Override
	public String toString(){
		String cl="";
		for (String c:this.conns){
			cl+=", "+c;
		}
		return "Input(conns="+cl.substring(2)+")";
	}
}