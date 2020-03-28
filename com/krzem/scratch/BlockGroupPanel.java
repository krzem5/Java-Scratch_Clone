package com.krzem.scratch;



import java.awt.Cursor;
import java.awt.image.BufferedImage;
import java.lang.Exception;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;



public class BlockGroupPanel extends Constants{
	public Main cls;
	public EditorView ev;
	private String[] groups;
	private Block[] bl;
	private Block hb=null;
	private boolean md=false;



	public BlockGroupPanel(Main cls,EditorView ev){
		this.cls=cls;
		this.ev=ev;
		this.groups=this.cls.BLOCK_MANAGER.groups.toArray(new String[0]);
		Arrays.sort(this.groups);
		this._gen_block_list();
	}



	public void update(){
		if (this.md==false&&this.cls.MOUSE==1&&this.cls.MOUSE_COUNT==1&&this.cls.MOUSE_BUTTON==1){
			this.md=true;
			if (this.hb==null&&this.ev.BORDER.x+BLOCK_GROUP_PANEL_GROUP_PANEL_WIDTH<=this.cls.MOUSE_POS.x&&this.cls.MOUSE_POS.x<=this.ev.BORDER.x+BLOCK_GROUP_PANEL_WIDTH&&this.ev.BORDER.y<=this.cls.MOUSE_POS.y&&this.cls.MOUSE_POS.y<=this.ev.BORDER.y+this.ev.BORDER.h){
				Block b=this._get_drag_block();
				if (b!=null){
					this.hb=b._clone();
					this.hb._panel=true;
					this.hb._start_drag();
				}
			}
		}
		if (this.md==true&&this.cls.MOUSE==0){
			this.md=false;
			if (this.hb!=null){
				this.hb=null;
			}
		}
		if (this.hb!=null){
			this.hb._update();
			if (this.ev.INNER_BORDER.x<=this.hb.pos.x&&this.hb.pos.x<=this.ev.INNER_BORDER.x+this.ev.INNER_BORDER.w&&this.ev.INNER_BORDER.y<=this.hb.pos.y&&this.hb.pos.y<=this.ev.INNER_BORDER.y+this.ev.INNER_BORDER.h){
				List<Block> tl=new ArrayList<Block>();
				Block b=this.hb._clone();
				b._start_drag();
				tl.add(b);
				this.ev.BLOCKS.add(tl);
				this.ev._dragging=true;
				this.hb=null;
			}
		}
	}



	public void draw(Graphics g){
		if (this._get_drag_block()!=null){
			g.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		else if (this.hb!=null){
			g.setCursor(new Cursor(Cursor.MOVE_CURSOR));
		}
		Graphics bg=g.clone();
		g.setColor(BLOCK_GROUP_PANEL_BORDER_COLOR);
		g.fillRect(this.ev.BORDER.x,this.ev.BORDER.y,BLOCK_GROUP_PANEL_WIDTH+BLOCK_GROUP_PANEL_BORDER,this.ev.BORDER.h);
		g=g.clip(this.ev.BORDER.x,this.ev.BORDER.y,BLOCK_GROUP_PANEL_WIDTH,this.ev.BORDER.h);
		g.setColor(BLOCK_GROUP_PANEL_BG_COLOR);
		g.fillRect(this.ev.BORDER.x,this.ev.BORDER.y,BLOCK_GROUP_PANEL_WIDTH,this.ev.BORDER.h);
		Graphics gp=g.clip(this.ev.BORDER.x,this.ev.BORDER.y,BLOCK_GROUP_PANEL_GROUP_PANEL_WIDTH,this.ev.BORDER.h);
		gp.setColor(BLOCK_GROUP_PANEL_GROUP_PANEL_BORDER_COLOR);
		gp.fillRect(this.ev.BORDER.x,this.ev.BORDER.y,BLOCK_GROUP_PANEL_GROUP_PANEL_WIDTH,this.ev.BORDER.h);
		gp.setColor(BLOCK_GROUP_PANEL_GROUP_PANEL_BG_COLOR);
		gp.fillRect(this.ev.BORDER.x,this.ev.BORDER.y,BLOCK_GROUP_PANEL_GROUP_PANEL_WIDTH-BLOCK_GROUP_PANEL_GROUP_PANEL_BORDER,this.ev.BORDER.h);
		int x=this.ev.BORDER.x+BLOCK_GROUP_PANEL_GROUP_PANEL_WIDTH/2-BLOCK_GROUP_PANEL_GROUP_PANEL_BORDER/2-BLOCK_GROUP_PANEL_GROUP_PANEL_GROUP_SIZE/2;
		int y=this.ev.BORDER.y+BLOCK_GROUP_PANEL_GROUP_PANEL_GROUP_DIST;
		for (String gnm:this.groups){
			gp.setColor(BLOCK_GROUP_PANEL_GROUP_PANEL_GROUP_BORDER_COLOR);
			gp.fillRect(x-BLOCK_GROUP_PANEL_GROUP_PANEL_GROUP_BORDER,y-BLOCK_GROUP_PANEL_GROUP_PANEL_GROUP_BORDER,BLOCK_GROUP_PANEL_GROUP_PANEL_GROUP_SIZE+BLOCK_GROUP_PANEL_GROUP_PANEL_GROUP_BORDER*2,BLOCK_GROUP_PANEL_GROUP_PANEL_GROUP_SIZE+BLOCK_GROUP_PANEL_GROUP_PANEL_GROUP_BORDER*2);
			Graphics eg=gp.clip(x,y,BLOCK_GROUP_PANEL_GROUP_PANEL_GROUP_SIZE,BLOCK_GROUP_PANEL_GROUP_PANEL_GROUP_SIZE);
			eg.setColor(BLOCK_GROUP_PANEL_GROUP_PANEL_GROUP_DEFAULT_BG_COLOR);
			eg.fillRect(x,y,BLOCK_GROUP_PANEL_GROUP_PANEL_GROUP_SIZE,BLOCK_GROUP_PANEL_GROUP_PANEL_GROUP_SIZE);
			BufferedImage i=this.cls.IMAGE_LOADER.get(String.format("%s%s.png",BLOCK_GROUP_TEX_PATH,gnm.substring(1)));
			if (i!=null){
				eg.drawImage(i,x+BLOCK_GROUP_PANEL_GROUP_PANEL_GROUP_SIZE/2-i.getWidth()/2,y+BLOCK_GROUP_PANEL_GROUP_PANEL_GROUP_SIZE/2-i.getHeight()/2,i.getWidth(),i.getHeight());
			}
			y+=BLOCK_GROUP_PANEL_GROUP_PANEL_GROUP_SIZE+BLOCK_GROUP_PANEL_GROUP_PANEL_GROUP_DIST*2;
		}
		g=g.clip(this.ev.BORDER.x+BLOCK_GROUP_PANEL_GROUP_PANEL_WIDTH,this.ev.BORDER.y,BLOCK_GROUP_PANEL_WIDTH-BLOCK_GROUP_PANEL_GROUP_PANEL_WIDTH,this.ev.BORDER.h);
		g.setColor(BLOCK_GROUP_PANEL_BG_COLOR);
		g.fillRect(this.ev.BORDER.x+BLOCK_GROUP_PANEL_GROUP_PANEL_WIDTH,this.ev.BORDER.y,BLOCK_GROUP_PANEL_WIDTH-BLOCK_GROUP_PANEL_GROUP_PANEL_WIDTH,this.ev.BORDER.h);
		x=this.ev.BORDER.x+BLOCK_GROUP_PANEL_GROUP_PANEL_WIDTH+BLOCK_GROUP_PANEL_BLOCK_PADDING_X;
		y=this.ev.BORDER.y+0;
		boolean f=true;
		for (Block b:this.bl){
			double i=b.index();
			if (i%1==0.2){
				y+=(f==true?BLOCK_GROUP_PANEL_BLOCK_PADDING_Y:BLOCK_GROUP_PANEL_BLOCK_GROUP_TITLE_BEFORE_DIST);
				g.drawText(b.group().substring(1,2).toUpperCase()+b.group().substring(2),x,y,BLOCK_GROUP_PANEL_BLOCK_GROUP_TITLE_FONT_SIZE,BLOCK_GROUP_PANEL_BLOCK_GROUP_TITLE_FONT_COLOR,BLOCK_GROUP_PANEL_BLOCK_GROUP_TITLE_FONT_NAME,BLOCK_GROUP_PANEL_BLOCK_GROUP_TITLE_FONT_MODIFIER);
				y+=BLOCK_GROUP_PANEL_BLOCK_GROUP_TITLE_AFTER_DIST;
			}
			else if (i%1==0.1){
				y+=BLOCK_GROUP_PANEL_BLOCK_MAX_DIST;
			}
			else{
				y+=BLOCK_GROUP_PANEL_BLOCK_MIN_DIST;
			}
			b.pos=new Vector(x,y);
			b._draw(g);
			y+=b.size.y;
			f=false;
		}
		if (this.hb!=null){
			this.hb._draw(bg);
		}
	}



	private void _gen_block_list(){
		try{
			ArrayList<Block> l=new ArrayList<Block>();
			for (String bnm:this.cls.BLOCK_MANAGER.lst.keySet()){
				Block b=(Block)(this.cls.BLOCK_MANAGER.lst.get(bnm).getConstructors()[0].newInstance());
				b._init(this.cls,this.ev,bnm,true);
				l.add(b);
			}
			this.bl=new Block[l.size()];
			int off=0;
			int c=0;
			for (String gnm:this.groups){
				for (Block b:l){
					if (b.group().equals(gnm)){
						this.bl[(int)(Math.floor(b.index()))+off]=b;
						c++;
					}
				}
				off+=c;
				c=0;
			}
		}
		catch (Exception e){
			IO.dump_error(e);
		}
	}



	private Block _get_drag_block(){
		for (Block b:this.bl){
			if (b._contains(this.cls.MOUSE_POS)){
				return b;
			}
		}
		return null;
	}
}