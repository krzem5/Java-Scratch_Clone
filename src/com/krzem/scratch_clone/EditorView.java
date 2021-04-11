package com.krzem.scratch_clone;



import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;



public class EditorView extends Constants{
	public Main cls;
	public Rectangle BORDER=new Rectangle(100,100,WINDOW_SIZE.width-200,WINDOW_SIZE.height-200);
	public Rectangle INNER_BORDER;
	public List<List<Block>> BLOCKS;
	public VScrollBar VSB;
	public HScrollBar HSB;
	public BlockGroupPanel BGP;
	private boolean md=false;
	private List<Block> _temp_expand_l=null;
	private List<Block> _temp_expand_dl=null;
	private int _temp_expand_dl_pos=-1;
	public ArrayList<Rectangle> _temp_shadow=null;
	public boolean _dragging=false;
	private boolean _deld=false;



	public EditorView(Main cls){
		this.cls=cls;
		this.INNER_BORDER=new Rectangle(this.BORDER.x+BLOCK_GROUP_PANEL_WIDTH+BLOCK_GROUP_PANEL_BORDER,this.BORDER.y,this.BORDER.w-BLOCK_GROUP_PANEL_WIDTH-BLOCK_GROUP_PANEL_BORDER,this.BORDER.h);
		this.BLOCKS=new ArrayList<List<Block>>();
		this.VSB=new VScrollBar(this.cls,this,this.INNER_BORDER.h,this.INNER_BORDER.h-SCROLLBAR_SIZE,new Vector(this.INNER_BORDER.x+this.INNER_BORDER.w-SCROLLBAR_SIZE,this.INNER_BORDER.y));
		this.HSB=new HScrollBar(this.cls,this,this.INNER_BORDER.w,this.INNER_BORDER.w-SCROLLBAR_SIZE,new Vector(this.INNER_BORDER.x,this.INNER_BORDER.y+this.INNER_BORDER.h-SCROLLBAR_SIZE));
		this.BGP=new BlockGroupPanel(this.cls,this);
	}



	public void update(){
		boolean omd=(this.md?true:false);
		if (this.md==false&&this.cls.MOUSE==1&&this.cls.MOUSE_COUNT==1&&this.cls.MOUSE_BUTTON==1){
			this.md=true;
			if (this.INNER_BORDER.x<=this.cls.MOUSE_POS.x&&this.cls.MOUSE_POS.x<=this.INNER_BORDER.w-SCROLLBAR_SIZE+this.INNER_BORDER.x&&this.INNER_BORDER.y<=this.cls.MOUSE_POS.y&&this.cls.MOUSE_POS.y<=this.INNER_BORDER.h-SCROLLBAR_SIZE+this.INNER_BORDER.y){
				Block t=this._get_drag_block();
				if (t!=null){
					t._start_drag();
					this._dragging=true;
				}
			}
		}
		if (this._dragging==true&&(this.cls.MOUSE==0||!(this.INNER_BORDER.x<=this.cls.MOUSE_POS.x&&this.cls.MOUSE_POS.x<=this.INNER_BORDER.w-SCROLLBAR_SIZE+this.INNER_BORDER.x&&this.INNER_BORDER.y<=this.cls.MOUSE_POS.y&&this.cls.MOUSE_POS.y<=this.INNER_BORDER.h-SCROLLBAR_SIZE+this.INNER_BORDER.y))){
			this._dragging=false;
		}
		if (this.cls.MOUSE==0){
			this.md=false;
		}
		this._update_block_list_pos();
		for (int bli=0;bli<this.BLOCKS.size();bli++){
			if (bli<this.BLOCKS.size()&&this.BLOCKS.get(bli)!=null){
				List<Block> bl=this.BLOCKS.get(bli);
				for (int bi=0;bi<bl.size();bi++){
					if (bi<bl.size()&&bl.get(bi)!=null){
						bl.get(bi)._update();
					}
				}
			}
		}
		this._update_block_list_pos();
		if (this._dragging==false){
			int mx=0;
			int my=0;
			for (int bli=0;bli<this.BLOCKS.size();bli++){
				if (bli<this.BLOCKS.size()&&this.BLOCKS.get(bli)!=null){
					List<Block> bl=this.BLOCKS.get(bli);
					for (int bi=0;bi<bl.size();bi++){
						if (bi<bl.size()&&bl.get(bi)!=null){
							Block b=bl.get(bi);
							mx=Math.max(mx,b.pos.x+b.size.x-this.INNER_BORDER.x+SCROLLBAR_SIZE+SCROLLBAR_MARGIN);
							my=Math.max(my,b.pos.y+b.size.y-this.INNER_BORDER.y+SCROLLBAR_SIZE+SCROLLBAR_MARGIN);
						}
					}
				}
			}
			this.HSB.w=mx;
			this.VSB.h=my;
		}
		if (this._deld==false&&this.cls.KEYBOARD.pressed(127)){
			this._deld=true;
			Block db=this._get_del_block();
			if (db!=null){
				List<Block> dbl=this._get_block_list(db);
				boolean st=false;
				int i=0;
				while (dbl.size()>i){
					Block b=dbl.get(i);
					if (b==db){
						st=true;
					}
					if (st==true){
						this._remove_block(b);
					}
					else{
						i++;
					}
				}
			}
		}
		if (this._deld==true&&!this.cls.KEYBOARD.pressed(127)){
			this._deld=false;
		}
		this.VSB.update(omd);
		if (this.cls.SCROLL_D!=0&&this.INNER_BORDER.x<=this.cls.MOUSE_POS.x&&this.cls.MOUSE_POS.x<=this.INNER_BORDER.x+this.INNER_BORDER.w&&this.INNER_BORDER.y<=this.cls.MOUSE_POS.y&&this.cls.MOUSE_POS.y<=this.INNER_BORDER.y+this.INNER_BORDER.h){
			this.VSB.step(-this.cls.SCROLL_D);
		}
		this.HSB.update(omd);
		this.BGP.update();
	}



	public void draw(Graphics g){
		g.setColor(EDITOR_BORDER_COLOR);
		g.fillRect(this.BORDER.x-EDITOR_BORDER_SIZE,this.BORDER.y-EDITOR_BORDER_SIZE,this.BORDER.w+EDITOR_BORDER_SIZE*2,this.BORDER.h+EDITOR_BORDER_SIZE*2);
		g=g.clip(this.BORDER.x,this.BORDER.y,this.BORDER.w,this.BORDER.h);
		if (this.INNER_BORDER.x<=this.cls.MOUSE_POS.x&&this.cls.MOUSE_POS.x<=this.INNER_BORDER.x+this.INNER_BORDER.w&&this.INNER_BORDER.y<=this.cls.MOUSE_POS.y&&this.cls.MOUSE_POS.y<=this.INNER_BORDER.y+this.INNER_BORDER.h){
			if (this._dragging==false&&((this.md==false&&(this.HSB.hover()||this.VSB.hover()))||this._get_drag_block()!=null)){
				g.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			else if (this._dragging==false){
				g.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			}
			else if (this._dragging==true){
				g.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			}
		}
		if (this.HSB.drag()){
			g.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
		}
		else if (this.VSB.drag()){
			g.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
		}
		g.setColor(EDITOR_BG_COLOR);
		g.fillRect(this.INNER_BORDER.x,this.INNER_BORDER.y,this.INNER_BORDER.w,this.INNER_BORDER.h);
		g.setColor(EDITOR_BG_MARKER_COLOR);
		for (int x=this.INNER_BORDER.x-this.HSB.get()+EDITOR_BG_MARKER_DIST;x<this.INNER_BORDER.x+this.HSB.w;x+=EDITOR_BG_MARKER_DIST*2){
			for (int y=this.INNER_BORDER.y-this.VSB.get()+EDITOR_BG_MARKER_DIST;y<this.INNER_BORDER.y+this.VSB.h;y+=EDITOR_BG_MARKER_DIST*2){
				if (this.INNER_BORDER.x<=x&&x<=this.INNER_BORDER.x+this.INNER_BORDER.w&&this.INNER_BORDER.y<=y&&y<=this.INNER_BORDER.y+this.INNER_BORDER.h){
					g.fillRect(x-EDITOR_BG_MARKER_SIZE,y-EDITOR_BG_MARKER_SIZE,EDITOR_BG_MARKER_SIZE*2,EDITOR_BG_MARKER_SIZE*2);
				}
			}
		}
		g.set_offset(this.HSB.get(),this.VSB.get());
		if (this._temp_shadow!=null){
			BufferedImage sh=this._gen_shadow();
			g.drawImage(sh,this.INNER_BORDER.x,this.INNER_BORDER.y,sh.getWidth(),sh.getHeight());
			this._temp_shadow=null;
		}
		for (int bli=0;bli<this.BLOCKS.size();bli++){
			if (bli<this.BLOCKS.size()&&this.BLOCKS.get(bli)!=null){
				List<Block> bl=this.BLOCKS.get(bli);
				for (int bi=0;bi<bl.size();bi++){
					if (bi<bl.size()&&bl.get(bi)!=null){
						bl.get(bi)._draw(g);
					}
				}
			}
		}
		g.clear_offset();
		this.BGP.draw(g);
		this.VSB.draw(g);
		this.HSB.draw(g);
		g.setColor(SCROLLBAR_BACKGROUND_COLOR);
		g.fillRect(this.INNER_BORDER.w-SCROLLBAR_SIZE+this.INNER_BORDER.x,this.INNER_BORDER.h-SCROLLBAR_SIZE+this.INNER_BORDER.y,SCROLLBAR_SIZE,SCROLLBAR_SIZE);
	}



	public void _remove_block(Block b){
		for (List<Block> bl:this.BLOCKS){
			if (this._recrusive_remove_block(bl,b)==true){
				break;
			}
		}
		this._remove_empty_lists();
	}



	public void _move_to_top(Block b){
		for (List<Block> bl:this.BLOCKS){
			if (this._recrusive_move_to_top(bl,b)==true){
				break;
			}
		}
	}



	public List<Block> _get_drag_list(Block b){
		for (List<Block> bl:this.BLOCKS){
			if (bl.get(0)==b){
				List<Block> r=new ArrayList<Block>();
				for (Block tb:bl){
					r.add(tb);
				}
				return r;
			}
		}
		return null;
	}



	public void _temp_expand(List<Block> l,List<Block> dl,int i){
		this._temp_expand_l=l;
		this._temp_expand_dl=dl;
		this._temp_expand_dl_pos=i;
	}



	public boolean _is_dragging(Block b){
		for (List<Block> bl:this.BLOCKS){
			if (bl.get(0).drag==true){
				return this._recrusive_is_dragging(bl,b);
			}
		}
		return false;
	}



	public List<Block> _get_block_list(Block b){
		for (List<Block> bl:this.BLOCKS){
			List<Block> l=this._recrusive_get_block_list(bl,b);
			if (l!=null){
				return l;
			}
		}
		return null;
	}



	private List<Block> _recrusive_get_block_list(List<Block> bl,Block b){
		if (bl.contains(b)){
			return bl;
		}
		for (Block tb:bl){
			if (tb.code!=null){
				List<Block> l=this._recrusive_get_block_list(tb.code,b);
				if (l!=null){
					return l;
				}
			}
		}
		return null;
	}



	private boolean _recrusive_is_dragging(List<Block> bl,Block b){
		if (bl.contains(b)){
			return true;
		}
		for (Block tb:bl){
			if (tb.code!=null){
				if (this._recrusive_is_dragging(tb.code,b)==true){
					return true;
				}
			}
		}
		return false;
	}



	private boolean _recrusive_move_to_top(List<Block> l,Block mb){
		for (int i=0;i<l.size();i++){
			if (l.get(i)==mb){
				List<Block> sl=new ArrayList<Block>();
				for (;i<l.size();i++){
					sl.add(l.get(i));
				}
				for (Block b:sl){
					this._remove_block(b);
				}
				this.BLOCKS.add(sl);
				return true;
			}
			if (l.get(i).code!=null){
				if (this._recrusive_move_to_top(l.get(i).code,mb)==true){
					return true;
				}
			}
		}
		return false;
	}



	private boolean _recrusive_remove_block(List<Block> l,Block rb){
		if (l.contains(rb)){
			l.remove(rb);
			return true;
		}
		for (Block b:l){
			if (b.code!=null){
				if (this._recrusive_remove_block(b.code,rb)==true){
					return true;
				}
			}
		}
		return false;
	}



	private void _remove_empty_lists(){
		for (int i=this.BLOCKS.size()-1;i>=0;i--){
			if (this.BLOCKS.get(i).size()==0){
				this.BLOCKS.remove(i);
			}
		}
	}



	private Block _get_drag_block(){
		Block t=null;
		for (List<Block> bl:this.BLOCKS){
			Block tl=this._recrusive_get_drag_block(bl);
			if (tl!=null){
				t=tl;
			}
		}
		return t;
	}



	private Block _recrusive_get_drag_block(List<Block> l){
		Block t=null;
		for (Block b:l){
			if (b._contains(this.cls.MOUSE_POS.add(this.HSB.get(),this.VSB.get()))){
				t=b;
			}
			else if (b.code!=null){
				Block tl=this._recrusive_get_drag_block(b.code);
				if (tl!=null){
					t=tl;
				}
			}
		}
		return t;
	}



	private Block _get_del_block(){
		Block t=null;
		for (List<Block> bl:this.BLOCKS){
			Block tl=this._recrusive_get_del_block(bl);
			if (tl!=null){
				t=tl;
			}
		}
		return t;
	}



	private Block _recrusive_get_del_block(List<Block> l){
		Block t=null;
		for (Block b:l){
			if (b._contains(this.cls.MOUSE_POS.add(this.HSB.get(),this.VSB.get()))){
				t=b;
			}
			else if (b.code!=null){
				Block tl=this._recrusive_get_del_block(b.code);
				if (tl!=null){
					t=tl;
				}
			}
		}
		return t;
	}



	private void _update_block_list_pos(){
		for (List<Block> bl:this.BLOCKS){
			int x=bl.get(0).pos.x;
			int y=bl.get(0).pos.y;
			int i=0;
			for (Block b:bl){
				if (this._temp_expand_l!=null&&bl.get(0)==this._temp_expand_l.get(0)){
					if (this._temp_expand_dl_pos==i){
						this._temp_shadow=new ArrayList<Rectangle>();
						for (Block tb:this._temp_expand_dl){
							this._temp_shadow.addAll(tb._get_shadow(new Vector(x,y)));
							y+=tb.size.y;
						}
					}
				}
				b.pos.x=x+0;
				b.pos.y=y+0;
				b._update_pos();
				y+=b.size.y;
				i++;
			}
			if (this._temp_expand_l!=null&&bl.get(0)==this._temp_expand_l.get(0)&&this._temp_expand_dl_pos==i){
				this._temp_shadow=new ArrayList<Rectangle>();
				for (Block tb:this._temp_expand_dl){
					this._temp_shadow.addAll(tb._get_shadow(new Vector(x,y)));
					y+=tb.size.y;
				}
			}
		}
		this._temp_expand_l=null;
		this._temp_expand_dl=null;
		this._temp_expand_dl_pos=-1;
	}



	private BufferedImage _gen_shadow(){
		BufferedImage sh=new BufferedImage(this.INNER_BORDER.w,this.INNER_BORDER.h,BufferedImage.TYPE_INT_ARGB);
		Graphics g=new Graphics(this.cls,(Graphics2D)sh.createGraphics());
		g.setColor(BLOCK_SHADOW_COLOR);
		for (Rectangle r:this._temp_shadow){
			if (r.neg==false){
				g.fillRect(r.x-this.INNER_BORDER.x,r.y-this.INNER_BORDER.y,r.w,r.h);
			}
			else{
				sh.setRGB(r.x-this.INNER_BORDER.x,r.y-this.INNER_BORDER.y,(255<<24)+(255<<16));
			}
		}
		return sh;
	}
}
