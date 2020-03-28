package com.krzem.scratch;



import java.awt.image.BufferedImage;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;



public class Block extends Constants{
	public Main cls;
	public EditorView ev;
	public Vector pos;
	public Vector d_off;
	public Vector size;
	public boolean drag=false;
	public BufferedImage L;
	public BufferedImage M;
	public BufferedImage R;
	public BufferedImage LA;
	public BufferedImage MA;
	public BufferedImage RA;
	public InputConnectorList Tconn;
	public OutputConnectorList Bconn;
	public String nm;
	public Block parent;
	public ArrayList<Block> code=null;
	public BufferedImage topM;
	public BufferedImage bottomM;
	public BufferedImage topMA;
	public BufferedImage bottomMA;
	public boolean _panel=false;



	public Block(){
		
	}



	public void init(){

	}



	public String group(){
		return BLOCK_GROUP_PANEL_DEFAULT_GROUP_NAME;
	}



	public String tex_name(){
		return this.nm;
	}



	public double index(){
		return 0.0;
	}



	public void update(){

	}



	public void draw(Graphics g){

	}



	public boolean run(){
		return false;
	}



	public Vector get_size(){
		return new Vector(BLOCK_DEFAULT_WIDTH,BLOCK_DEFAULT_HEIGHT);
	}



	public void _init(Main cls,EditorView ev,String nm,boolean p){
		this.cls=cls;
		this.ev=ev;
		this.pos=new Vector(0,0);
		this.nm=nm;
		this._panel=p;
		this.d_off=new Vector(0,0);
		this.size=new Vector(BLOCK_DEFAULT_WIDTH,BLOCK_DEFAULT_HEIGHT);
		this.parent=null;
		this.Tconn=new InputConnectorList(this.cls,this,"code");
		this.Bconn=new OutputConnectorList(this.cls,this,"code");
		this._load();
		this.init();
	}



	public void _update(){
		if (this._panel==false){
			this._recalc_size();
			List<Block> bl=this.ev._get_block_list(this);
			if (bl==null){
				return;
			}
			if (bl.indexOf(this)>0&&!bl.get(bl.indexOf(this)-1).Bconn.can_connect(this.Tconn)){
				this.ev._remove_block(this);
				List<Block> nl=new ArrayList<Block>();
				nl.add(this);
				this.pos.x+=BLOCK_POP_OUT_X_OFF;
				this.pos.y+=BLOCK_POP_OUT_Y_OFF;
				this.ev.BLOCKS.add(nl);
			}
			if (this.ev._dragging==false&&this.drag==true){
				this.drag=false;
				this.d_off=new Vector(0,0);
				Block c=this._check_all_blocks();
				if (c!=null){
					List<Block> cl=this.ev._get_drag_list(this);
					int i=Math.min(c.code.size(),c._get_drag_insert_pos(this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get())));
					if (i!=-1){
						for (Block b:cl){
							this.ev._remove_block(b);
							c.code.add(i,b);
							i++;
						}
					}
				}
				else{
					List<Block> tl=this._get_attach_list();
					if (tl!=null){
						List<Block> cl=this.ev._get_drag_list(this);
						int i=Math.min(tl.size(),this._get_attach_list_pos());
						for (Block b:cl){
							this.ev._remove_block(b);
							tl.add(i,b);
							i++;
						}
					}
				}
			}
			if (this.drag==true){
				this.pos=new Vector(Math.max(this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get()).x-this.d_off.x,this.ev.INNER_BORDER.x),Math.max(this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get()).y-this.d_off.y,this.ev.INNER_BORDER.y));
				Block c=this._check_all_blocks();
				if (c!=null){
					c._temp_expand_size(this.ev._get_drag_list(this),c._get_drag_insert_pos(this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get())));
				}
				else{
					if (this._get_attach_block()!=null){
						this.ev._temp_expand(this._get_attach_list(),this.ev._get_drag_list(this),this._get_attach_list_pos());
					}
				}
			}
			this.update();
		}
		else{
			if (this.drag==true){
				this.pos=new Vector(this.cls.MOUSE_POS.x-this.d_off.x,this.cls.MOUSE_POS.y-this.d_off.y);
			}
			this.update();
		}
	}



	public void _draw(Graphics g){
		if (this.ev._is_dragging(this)||(this._panel==true&&this.drag==true)){
			g.drawImage(this.LA,this.pos.x,this.pos.y,this.LA.getWidth(),this.size.y);
			this.Tconn.draw_a(g,this.pos.x+this.LA.getWidth(),this.pos.y);
			this.Bconn.draw_a(g,this.MA,this.pos.x+this.LA.getWidth(),this.pos.y,this.size.x-this.LA.getWidth()-this.RA.getWidth(),this.size.y,0);
			g.drawImage(this.RA,this.pos.x+this.size.x-this.RA.getWidth(),this.pos.y,this.RA.getWidth(),this.size.y);
		}
		else{
			g.drawImage(this.L,this.pos.x,this.pos.y,this.L.getWidth(),this.size.y);
			this.Tconn.draw(g,this.pos.x+this.L.getWidth(),this.pos.y);
			this.Bconn.draw(g,this.M,this.pos.x+this.L.getWidth(),this.pos.y,this.size.x-this.L.getWidth()-this.R.getWidth(),this.size.y,0);
			g.drawImage(this.R,this.pos.x+this.size.x-this.R.getWidth(),this.pos.y,this.R.getWidth(),this.size.y);
		}
		this.draw(g);
	}



	public Block _clone(){
		Block b=this.cls.BLOCK_MANAGER.get(this.nm);
		b._init(this.cls,this.ev,this.nm,false);
		b.pos=new Vector(this.pos.x,this.pos.y);
		return b;
	}



	public ArrayList<Rectangle> _get_shadow(Vector v){
		ArrayList<Rectangle> sl=new ArrayList<Rectangle>();
		sl.addAll(this.Tconn._get_shadow(v.x+this.L.getWidth(),v.y));
		sl.add(new Rectangle(v.x,v.y,this.size.x,this.size.y));
		sl.addAll(this.Bconn._get_shadow(v.x+this.L.getWidth(),v.y+this.size.y));
		return sl;
	}



	public void _start_drag(){
		this.drag=true;
		if (this._panel==false){
			this.d_off=new Vector(this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get()).x-this.pos.x,this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get()).y-this.pos.y);
			this.ev._move_to_top(this);
		}
		else{
			this.d_off=new Vector(this.cls.MOUSE_POS.x-this.pos.x,this.cls.MOUSE_POS.y-this.pos.y);
		}
	}



	public boolean _contains(Vector v){
		return (this.pos.x<=v.x&&v.x<=this.pos.x+this.size.x&&this.pos.y<=v.y&&v.y<=this.pos.y+this.size.y);
	}



	public boolean _box_contains(Vector v){
		return (this.pos.x<=v.x&&v.x<=this.pos.x+this.size.x&&this.pos.y<=v.y&&v.y<=this.pos.y+this.size.y);
	}



	public boolean _inner_box_contains(Vector v,Block bl){
		return false;
	}



	private void _load(){
		String b_dir=BLOCK_TEX_PATH+this.tex_name()+"-";
		this.L=this.cls.IMAGE_LOADER.get(b_dir+"left.png");
		this.M=this.cls.IMAGE_LOADER.get(b_dir+"middle.png");
		this.R=this.cls.IMAGE_LOADER.get(b_dir+"right.png");
		this.LA=this.cls.IMAGE_LOADER.get(b_dir+"left-alpha.png");
		this.MA=this.cls.IMAGE_LOADER.get(b_dir+"middle-alpha.png");
		this.RA=this.cls.IMAGE_LOADER.get(b_dir+"right-alpha.png");
	}



	private void _recalc_size(){
		Vector s=this.get_size();
		this.size.x=Math.max(this.size.x,s.x);
		this.size.y=Math.max(this.size.y,s.y);
	}



	private Block _check_all_blocks(){
		List<Block> igl=this.ev._get_drag_list(this);
		for (List<Block> bl:this.ev.BLOCKS){
			for (Block b:bl){
				if (igl.contains(b)||b.code==null){
					continue;
				}
				if (this._check_child_blocks(b)!=null){
					return this._check_child_blocks(b);
				}
				if (b._inner_box_contains(this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get()),this)){
					return b;
				}
			}
		}
		return null;
	}



	private Block _check_child_blocks(Block p){
		List<Block> igl=this.ev._get_drag_list(this);
		for (Block b:p.code){
			if (igl.contains(b)||b.code==null){
				continue;
			}
			if (this._check_child_blocks(b)!=null){
				return this._check_child_blocks(b);
			}
			if (b._inner_box_contains(this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get()),this)){
				return b;
			}
		}
		return null;
	}



	private List<Block> _get_attach_list(){
		Block ab=this._get_attach_block();
		if (ab==null){
			return null;
		}
		for (List<Block> bl:this.ev.BLOCKS){
			if (bl.contains(ab)){
				return bl;
			}
		}
		return null;
	}



	private Block _get_attach_block(){
		List<Block> igl=this.ev._get_drag_list(this);
		for (List<Block> bl:this.ev.BLOCKS){
			int i=0;
			for (Block b:bl){
				if (igl.contains(b)){
					continue;
				}
				if (i>0&&b.code!=null&&b.pos.x<=this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get()).x&&this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get()).x<b.pos.x+b.size.x&&b.pos.y<=this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get()).y&&this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get()).y<=b.pos.y+b.topM.getHeight()/2&&(i==0||(i>0&&bl.get(i-1).Bconn.can_connect(this.Tconn)))){
					return b;
				}
				if (b.code!=null&&b.pos.x<=this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get()).x&&this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get()).x<b.pos.x+b.size.x&&b.pos.y+b.size.y-b.bottomM.getHeight()/2<=this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get()).y&&this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get()).y<=b.pos.y+b.size.y&&b.Bconn.can_connect(this.Tconn)){
					return b;
				}
				if (i>0&&b.code==null&&b.pos.x<=this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get()).x&&this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get()).x<b.pos.x+b.size.x&&b.pos.y<=this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get()).y&&this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get()).y<=b.pos.y+b.M.getHeight()/2&&(i==0||(i>0&&bl.get(i-1).Bconn.can_connect(this.Tconn)))){
					return b;
				}
				if (b.code==null&&b.pos.x<=this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get()).x&&this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get()).x<b.pos.x+b.size.x&&b.pos.y+b.M.getHeight()/2<=this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get()).y&&this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get()).y<=b.pos.y+b.size.y&&b.Bconn.can_connect(this.Tconn)){
					return b;
				}
				i++;
			}
		}
		return null;
	}



	private int _get_attach_list_pos(){
		List<Block> al=this._get_attach_list();
		Block ab=this._get_attach_block();
		int i=al.indexOf(ab);
		if (this.cls.MOUSE_POS.add(this.ev.HSB.get(),this.ev.VSB.get()).y>=ab.pos.y+ab.size.y/2){
			i++;
		}
		return i;
	}



	public void _temp_expand_size(List<Block> bl,int i){

	}



	public void _update_pos(){

	}



	public int _get_drag_insert_pos(Vector v){
		return -1;
	}



	@Override
	public String toString(){
		return String.format("Block(name=%s, pos=(x=%d, y=%d))",this.nm,this.pos.x,this.pos.y);
	}
}