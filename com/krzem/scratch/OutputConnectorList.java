package com.krzem.scratch;



import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;



public class OutputConnectorList extends Constants{
	public Main cls;
	public Block b;
	public String[] conns;



	public OutputConnectorList(Main cls,Block b,String... conns){
		this.cls=cls;
		this.b=b;
		this.conns=conns;
	}



	public void draw(Graphics g,BufferedImage bgi,int x,int y,int w,int h,int ox){
		BufferedImage oi=bgi;
		if (bgi.getWidth()!=w||bgi.getHeight()!=h){
			oi=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
			Graphics2D ig=oi.createGraphics();
			ig.drawImage(bgi,0,0,w,h,null);
			ig.dispose();
		}
		for (String c:this.conns){
			BufferedImage img=this.cls.IMAGE_LOADER.get(String.format("%s%s-%s-o.png",CONNECTOR_TEX_PATH,this.b.tex_name(),c));
			if (img==null){
				continue;
			}
			for (int ix=0;ix<img.getWidth();ix++){
				for (int iy=0;iy<img.getHeight();iy++){
					oi.setRGB(ox+ix,h-img.getHeight()+iy,img.getRGB(ix,iy));
				}
			}
			ox+=img.getWidth()+CONNECTOR_GAP_SIZE;
		}
		g.drawImage(oi,x,y,w,h);
	}



	public void draw_a(Graphics g,BufferedImage bgi,int x,int y,int w,int h,int ox){
		BufferedImage oi=bgi;
		if (bgi.getWidth()!=w||bgi.getHeight()!=h){
			oi=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
			Graphics2D ig=oi.createGraphics();
			ig.drawImage(bgi,0,0,w,h,null);
			ig.dispose();
		}
		for (String c:this.conns){
			BufferedImage img=this.cls.IMAGE_LOADER.get(String.format("%s%s-%s-o-alpha.png",CONNECTOR_TEX_PATH,this.b.tex_name(),c));
			if (img==null){
				continue;
			}
			for (int ix=0;ix<img.getWidth();ix++){
				for (int iy=0;iy<img.getHeight();iy++){
					oi.setRGB(ox+ix,h-img.getHeight()+iy,img.getRGB(ix,iy));
				}
			}
			ox+=img.getWidth()+CONNECTOR_GAP_SIZE;
		}
		g.drawImage(oi,x,y,w,h);
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
						Rectangle px=new Rectangle(ox+x,oy-img.getHeight()+y,1,1);
						px.neg=true;
						sh.add(px);

					}
				}
			}
			ox+=img.getWidth()+CONNECTOR_GAP_SIZE;
		}
		return sh;
	}



	public boolean can_connect(InputConnectorList cl){
		if (cl.conns.length>this.conns.length){
			return false;
		}
		for (int i=0;i<cl.conns.length;i++){
			if (!this.conns[i].equals(cl.conns[i])){
				return false;
			}
		}
		return true;
	}



	@Override
	public String toString(){
		String cl="";
		for (String c:this.conns){
			cl+=", "+c;
		}
		return "Output(conns="+cl.substring(2)+")";
	}
}