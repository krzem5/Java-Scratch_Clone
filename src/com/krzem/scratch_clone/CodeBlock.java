package com.krzem.scratch_clone;



import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;



public class CodeBlock extends Block{
	public BufferedImage topL;
	public BufferedImage bottomL;
	public BufferedImage topR;
	public BufferedImage bottomR;
	public BufferedImage side;
	public BufferedImage topLA;
	public BufferedImage bottomLA;
	public BufferedImage topRA;
	public BufferedImage bottomRA;
	public BufferedImage sideA;
	public OutputConnectorList cTop;
	public List<Block> temp_code=null;
	public int temp_code_pos=-1;
	private int _l_temp_code_pos=-1;



	public CodeBlock(){

	}



	@Override
	public Vector get_size(){
		return new Vector(BLOCK_CONTROL_DEFAULT_WIDTH,BLOCK_CONTROL_DEFAULT_HEIGHT);
	}



	@Override
	public void _init(Main cls,EditorView ev,String nm,boolean p){
		super._init(cls,ev,nm,p);
		this.size=new Vector(BLOCK_CONTROL_DEFAULT_WIDTH,BLOCK_CONTROL_DEFAULT_HEIGHT);
		this.code=new ArrayList<Block>();
		this.cTop=new OutputConnectorList(this.cls,this,"code");
		this._load();
	}



	@Override
	public void _update(){
		super._update();
		if (this._panel==false){
			for (int i=0;i<this.code.size();i++){
				if (i<this.code.size()&&this.code.get(i)!=null){
					this.code.get(i)._update();
				}
			}
			this._recalc_size();
			this.update();
		}
	}



	@Override
	public void _draw(Graphics g){
		if (this.ev._is_dragging(this)||(this._panel==true&&this.drag==true)){
			g.drawImage(this.topLA,this.pos.x,this.pos.y,this.topLA.getWidth(),this.topLA.getHeight());
			this.Tconn.draw(g,this.pos.x+CONNECTOR_CODE_BLOCK_INNER_OFFSET,this.pos.y);
			this.cTop.draw(g,this.topMA,this.pos.x+this.topLA.getWidth(),this.pos.y,this.size.x-this.topLA.getWidth()-this.topRA.getWidth(),this.topMA.getHeight(),CONNECTOR_CODE_BLOCK_INNER_OFFSET);
			g.drawImage(this.topRA,this.pos.x+this.size.x-this.topRA.getWidth(),this.pos.y,this.topRA.getWidth(),this.topRA.getHeight());
			BufferedImage blm=new BufferedImage(this.size.x-this.bottomRA.getWidth(),this.bottomMA.getHeight(),BufferedImage.TYPE_INT_ARGB);
			Graphics2D ig=blm.createGraphics();
			ig.drawImage(this.bottomLA,0,this.bottomMA.getHeight()-this.bottomLA.getHeight(),this.bottomLA.getWidth(),this.bottomLA.getHeight(),null);
			ig.drawImage(this.bottomMA,this.bottomLA.getWidth(),0,this.size.x-this.bottomLA.getWidth()-this.bottomRA.getWidth(),this.bottomMA.getHeight(),null);
			ig.dispose();
			this.Bconn.draw(g,blm,this.pos.x,this.pos.y+this.size.y-blm.getHeight(),blm.getWidth(),blm.getHeight(),CONNECTOR_CODE_BLOCK_INNER_OFFSET);
			g.drawImage(this.bottomRA,this.pos.x+this.size.x-this.bottomRA.getWidth(),this.pos.y+this.size.y-this.bottomRA.getHeight(),this.bottomRA.getWidth(),this.bottomRA.getHeight());
			g.drawImage(this.sideA,this.pos.x,this.pos.y+this.topLA.getHeight(),this.sideA.getWidth(),this.size.y-this.topLA.getHeight()-this.bottomLA.getHeight());
		}
		else{
			g.drawImage(this.topL,this.pos.x,this.pos.y,this.topL.getWidth(),this.topL.getHeight());
			this.Tconn.draw(g,this.pos.x+CONNECTOR_CODE_BLOCK_INNER_OFFSET,this.pos.y);
			this.cTop.draw(g,this.topM,this.pos.x+this.topL.getWidth(),this.pos.y,this.size.x-this.topL.getWidth()-this.topR.getWidth(),this.topM.getHeight(),CONNECTOR_CODE_BLOCK_INNER_OFFSET);
			g.drawImage(this.topR,this.pos.x+this.size.x-this.topR.getWidth(),this.pos.y,this.topR.getWidth(),this.topR.getHeight());
			BufferedImage blm=new BufferedImage(this.size.x-this.bottomR.getWidth(),this.bottomM.getHeight(),BufferedImage.TYPE_INT_ARGB);
			Graphics2D ig=blm.createGraphics();
			ig.drawImage(this.bottomL,0,this.bottomM.getHeight()-this.bottomL.getHeight(),this.bottomL.getWidth(),this.bottomL.getHeight(),null);
			ig.drawImage(this.bottomM,this.bottomL.getWidth(),0,this.size.x-this.bottomL.getWidth()-this.bottomR.getWidth(),this.bottomM.getHeight(),null);
			ig.dispose();
			this.Bconn.draw(g,blm,this.pos.x,this.pos.y+this.size.y-blm.getHeight(),blm.getWidth(),blm.getHeight(),CONNECTOR_CODE_BLOCK_INNER_OFFSET);
			g.drawImage(this.bottomR,this.pos.x+this.size.x-this.bottomR.getWidth(),this.pos.y+this.size.y-this.bottomR.getHeight(),this.bottomR.getWidth(),this.bottomR.getHeight());
			g.drawImage(this.side,this.pos.x,this.pos.y+this.topL.getHeight(),this.side.getWidth(),this.size.y-this.topL.getHeight()-this.bottomL.getHeight());
		}
		this.draw(g);
		for (Block b:this.code){
			b._draw(g);
		}
	}



	@Override
	public ArrayList<Rectangle> _get_shadow(Vector v){
		ArrayList<Rectangle> sl=new ArrayList<Rectangle>();
		sl.addAll(this.Tconn._get_shadow(v.x+CONNECTOR_CODE_BLOCK_INNER_OFFSET,v.y));
		sl.add(new Rectangle(v.x,v.y,this.size.x,this.topM.getHeight()));
		sl.addAll(this.cTop._get_shadow(v.x+this.topL.getWidth(),v.y+this.topM.getHeight()));
		sl.add(new Rectangle(v.x,v.y+this.topL.getHeight(),this.side.getWidth(),this.size.y-this.topL.getHeight()-this.bottomM.getHeight()));
		sl.add(new Rectangle(v.x,v.y+this.size.y-this.bottomM.getHeight(),this.size.x,this.bottomM.getHeight()));
		sl.addAll(this.Bconn._get_shadow(v.x,v.y+this.size.y));
		int x=v.x+this.side.getWidth();
		int y=v.y+this.topM.getHeight();
		for (Block b:this.code){
			sl.addAll(b._get_shadow(new Vector(x,y)));
			y+=b.size.y;
		}
		return sl;
	}



	@Override
	public boolean _contains(Vector v){
		return ((this.pos.x<=v.x&&v.x<=this.pos.x+this.size.x&&this.pos.y<=v.y&&v.y<=this.pos.y+this.topL.getHeight())||(this.pos.x<=v.x&&v.x<=this.pos.x+this.size.x&&this.pos.y+this.size.y-this.bottomM.getHeight()<=v.y&&v.y<=this.pos.y+this.size.y)||(this.pos.x<=v.x&&v.x<=this.side.getWidth()&&this.pos.y<=v.y&&v.y<=this.pos.y+this.size.y));
	}



	public boolean _empty_box_contains(Vector v){
		for (Block b:this.code){
			if (b._box_contains(v)){
				return false;
			}
		}
		return true;
	}



	@Override
	public boolean _inner_box_contains(Vector v,Block bl){
		if (this.pos.x<=v.x&&v.x<=this.pos.x+this.size.x&&this.pos.y+this.topM.getHeight()/2<=v.y&&v.y<=this.pos.y+this.size.y-this.bottomM.getHeight()/2){
			if (this.pos.y+this.topM.getHeight()/2<=v.y&&v.y<=this.pos.y+this.topM.getHeight()&&!this.cTop.can_connect(bl.Tconn)){
				return false;
			}
			if (this.pos.y+this.size.y-this.bottomM.getHeight()<=v.y&&v.y<=this.pos.y+this.size.y-this.bottomM.getHeight()/2&&((this.code.size()==0&&!this.cTop.can_connect(bl.Tconn))||(this.code.size()>0&&!this.code.get(this.code.size()-1).Bconn.can_connect(bl.Tconn)))){
				return false;
			}
			int i=0;
			for (Block b:this.code){
				if (b.code!=null&&b._inner_box_contains(v,bl)){
					return false;
				}
				if (b.pos.y<=v.y&&v.y<=b.pos.y+b.size.y/2&&((i==0&&!this.cTop.can_connect(bl.Tconn))||(i>0&&!this.code.get(i-1).Bconn.can_connect(bl.Tconn)))){
					return false;
				}
				if (b.pos.y+b.size.y/2<=v.y&&v.y<=b.pos.y+b.size.y&&!b.Bconn.can_connect(bl.Tconn)){
					return false;
				}
				i++;
			}
			return true;
		}
		return false;
	}



	@Override
	public void _temp_expand_size(List<Block> bl,int i){
		this.temp_code=bl;
		this.temp_code_pos=i;
		this._l_temp_code_pos=i;
	}



	@Override
	public void _update_pos(){
		int x=this.pos.x+this.side.getWidth();
		int y=this.pos.y+this.topM.getHeight();
		int i=0;
		for (Block b:this.code){
			if (this.temp_code!=null&&this.temp_code_pos==i){
				this.ev._temp_shadow=new ArrayList<Rectangle>();
				for (Block tb:this.temp_code){
					this.ev._temp_shadow.addAll(tb._get_shadow(new Vector(x,y)));
					y+=tb.size.y;
				}
			}
			b.pos.x=x;
			b.pos.y=y;
			b._update_pos();
			y+=b.size.y;
			i++;
		}
		if (this.temp_code!=null&&this.temp_code_pos==i){
			this.ev._temp_shadow=new ArrayList<Rectangle>();
			for (Block tb:this.temp_code){
				if (tb!=null){
					this.ev._temp_shadow.addAll(tb._get_shadow(new Vector(x,y)));
					y+=tb.size.y;
				}
			}
		}
	}



	@Override
	public int _get_drag_insert_pos(Vector v){
		if (this.pos.x<=v.x&&v.x<=this.pos.x+this.size.x&&this.pos.y+this.topM.getHeight()/2<=v.y&&v.y<=this.pos.y+this.topM.getHeight()){
			return 0;
		}
		if (this.pos.x<=v.x&&v.x<=this.pos.x+this.size.x&&this.pos.y+this.size.y-this.bottomM.getHeight()<=v.y&&v.y<=this.pos.y+this.size.y-this.bottomM.getHeight()/2){
			return this.code.size();
		}
		int i=0;
		for (Block b:this.code){
			if (b.code!=null){
				if (b.pos.x<=v.x&&v.x<=b.pos.x+b.size.x&&b.pos.y<=v.y&&v.y<=b.pos.y+b.topM.getHeight()/2){
					return i;
				}
				if (b.pos.x<=v.x&&v.x<=b.pos.x+b.size.x&&b.pos.y+b.size.y-b.bottomM.getHeight()/2<=v.y&&v.y<=b.pos.y+b.size.y){
					return i+1;
				}
			}
			else{
				if (b.pos.x<=v.x&&v.x<=b.pos.x+b.size.x&&b.pos.y<=v.y&&v.y<=b.pos.y+b.M.getHeight()/2){
					return i;
				}
				if (b.pos.x<=v.x&&v.x<=b.pos.x+b.size.x&&b.pos.y+b.M.getHeight()/2<=v.y&&v.y<=b.pos.y+b.M.getHeight()){
					return i+1;
				}
			}
			i++;
		}
		return this._l_temp_code_pos;
	}



	private void _load(){
		String b_dir=BLOCK_TEX_PATH+this.tex_name()+"-";
		this.topL=this.cls.IMAGE_LOADER.get(b_dir+"top-left.png");
		this.topM=this.cls.IMAGE_LOADER.get(b_dir+"top-middle.png");
		this.topR=this.cls.IMAGE_LOADER.get(b_dir+"top-right.png");
		this.bottomL=this.cls.IMAGE_LOADER.get(b_dir+"bottom-left.png");
		this.bottomM=this.cls.IMAGE_LOADER.get(b_dir+"bottom-middle.png");
		this.bottomR=this.cls.IMAGE_LOADER.get(b_dir+"bottom-right.png");
		this.side=this.cls.IMAGE_LOADER.get(b_dir+"side.png");
		this.topLA=this.cls.IMAGE_LOADER.get(b_dir+"top-left-alpha.png");
		this.topMA=this.cls.IMAGE_LOADER.get(b_dir+"top-middle-alpha.png");
		this.topRA=this.cls.IMAGE_LOADER.get(b_dir+"top-right-alpha.png");
		this.bottomLA=this.cls.IMAGE_LOADER.get(b_dir+"bottom-left-alpha.png");
		this.bottomMA=this.cls.IMAGE_LOADER.get(b_dir+"bottom-middle-alpha.png");
		this.bottomRA=this.cls.IMAGE_LOADER.get(b_dir+"bottom-right-alpha.png");
		this.sideA=this.cls.IMAGE_LOADER.get(b_dir+"side-alpha.png");
	}



	private void _recalc_size(){
		int x=this.side.getWidth();
		int y=this.topM.getHeight()+this.bottomM.getHeight();
		int mw=0;
		for (Block b:this.code){
			y+=b.size.y;
			mw=Math.max(mw,b.size.x);
		}
		if (this.temp_code!=null){
			for (Block b:this.temp_code){
				y+=b.size.y;
				mw=Math.max(mw,b.size.x);
			}
		}
		x+=mw;
		Vector s=this.get_size();
		this.size.x=Math.max(x,s.x);
		this.size.y=Math.max(y,s.y);
		this.temp_code=null;
		this.temp_code_pos=-1;
	}
}
