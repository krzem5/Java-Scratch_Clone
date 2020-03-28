package com.krzem.scratch;



import java.lang.Math;



public class HScrollBar extends Constants{
	public Main cls;
	public EditorView ev;
	public int min_w;
	public int w;
	public int dw;
	public Vector p;
	public int s_pos;
	public int s_w;
	private int _dx_off=-1;



	public HScrollBar(Main cls,EditorView ev,int min_w,int dw,Vector p){
		this.cls=cls;
		this.ev=ev;
		this.min_w=min_w;
		this.w=this.min_w+0;
		this.dw=dw;
		this.p=p;
		this.s_pos=0;
		this.s_w=this.min_w+0;
	}



	public void update(boolean md){
		this.w=Math.max(this.w,this.min_w);
		this.s_w=(int)(((double)(this.min_w)/this.w)*this.dw);
		this.s_pos=Math.max(Math.min(this.s_pos,this.p.x+this.dw-this.s_w),this.p.x);
		if (md==false&&this._dx_off==-1&&this.ev._dragging==false&&this.cls.MOUSE==1&&this.cls.MOUSE_COUNT==1&&this.cls.MOUSE_BUTTON==1&&this.s_pos<=this.cls.MOUSE_POS.x&&this.cls.MOUSE_POS.x<=this.s_pos+this.s_w&&this.p.y<=this.cls.MOUSE_POS.y&&this.cls.MOUSE_POS.y<=this.p.y+SCROLLBAR_SIZE){
			this._dx_off=this.cls.MOUSE_POS.x-this.s_pos;
		}
		if (this._dx_off!=-1&&this.cls.MOUSE==0){
			this._dx_off=-1;
		}
		if (this._dx_off!=-1){
			this.s_pos=this.cls.MOUSE_POS.x-this._dx_off;
		}
		this.s_pos=Math.max(Math.min(this.s_pos,this.p.x+this.dw-this.s_w),this.p.x);
	}



	public void step(int d){
		this.s_pos=Math.max(Math.min(this.s_pos+d*(int)this._map(SCROLLBAR_SCROLL_WHEEL_STEP_SIZE,0,this.w,0,this.dw),this.p.x+this.dw-this.s_w),this.p.x);
	}



	public void draw(Graphics g){
		g.clear_offset();
		g.setColor(SCROLLBAR_BACKGROUND_COLOR);
		g.fillRect(this.p.x,this.p.y,this.dw,SCROLLBAR_SIZE);
		if (this.ev._dragging==true){
			g.setColor(SCROLLBAR_DISABLED_COLOR);
		}
		else if (this._dx_off==-1){
			g.setColor(SCROLLBAR_COLOR);
		}
		else{
			g.setColor(SCROLLBAR_DRAGGING_COLOR);
		}
		g.fillRect(this.s_pos,this.p.y,this.s_w,SCROLLBAR_SIZE);
	}



	public int get(){
		if (this.dw==this.s_w){
			return 0;
		}
		return (int)this._map(this.s_pos,0,this.dw-this.s_w,0,this.w-this.min_w);
	}



	public boolean hover(){
		return (this.s_pos<=this.cls.MOUSE_POS.x&&this.cls.MOUSE_POS.x<=this.s_pos+this.s_w&&this.p.y<=this.cls.MOUSE_POS.y&&this.cls.MOUSE_POS.y<=this.p.y+SCROLLBAR_SIZE);
	}



	public boolean drag(){
		return (this._dx_off!=-1);
	}



	private double _map(double v,double aa,double ab,double ba,double bb){
		return (v-aa)/(ab-aa)*(bb-ba)+ba;
	}
}