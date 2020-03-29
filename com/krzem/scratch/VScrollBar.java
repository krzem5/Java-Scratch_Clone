package com.krzem.scratch;



import java.lang.Math;



public class VScrollBar extends Constants{
	public Main cls;
	public EditorView ev;
	public int min_h;
	public int h;
	public int dh;
	public Vector p;
	public int s_pos;
	public int s_h;
	private int _dy_off=-1;



	public VScrollBar(Main cls,EditorView ev,int min_h,int dh,Vector p){
		this.cls=cls;
		this.ev=ev;
		this.min_h=min_h;
		this.h=this.min_h+0;
		this.dh=dh;
		this.p=p;
		this.s_pos=0;
		this.s_h=this.min_h+0;
	}



	public void update(boolean md){
		this.h=Math.max(this.h,this.min_h);
		this.s_h=(int)(((double)(this.min_h)/this.h)*this.dh);
		this.s_pos=Math.max(Math.min(this.s_pos,this.p.y+this.dh-this.s_h),this.p.y);
		if (md==false&&this._dy_off==-1&&this.ev._dragging==false&&this.cls.MOUSE==1&&this.cls.MOUSE_COUNT==1&&this.cls.MOUSE_BUTTON==1&&this.p.x<=this.cls.MOUSE_POS.x&&this.cls.MOUSE_POS.x<=this.p.x+SCROLLBAR_SIZE&&this.s_pos<=this.cls.MOUSE_POS.y&&this.cls.MOUSE_POS.y<=this.s_pos+this.s_h){
			this._dy_off=this.cls.MOUSE_POS.y-this.s_pos;
		}
		if (this._dy_off!=-1&&this.cls.MOUSE==0){
			this._dy_off=-1;
		}
		if (this._dy_off!=-1){
			this.s_pos=this.cls.MOUSE_POS.y-this._dy_off;
		}
		this.s_pos=Math.max(Math.min(this.s_pos,this.p.y+this.dh-this.s_h),this.p.y);
	}



	public void step(int d){
		this.s_pos=Math.max(Math.min(this.s_pos+d*(int)this._map(SCROLLBAR_SCROLL_WHEEL_STEP_SIZE,0,this.h,0,this.dh),this.p.y+this.dh-this.s_h),this.p.y);
	}



	public void draw(Graphics g){
		g.setColor(SCROLLBAR_BACKGROUND_COLOR);
		g.fillRect(this.p.x,this.p.y,SCROLLBAR_SIZE,this.dh);
		if (this.ev._dragging==true){
			g.setColor(SCROLLBAR_DISABLED_COLOR);
		}
		else if (this._dy_off==-1){
			g.setColor(SCROLLBAR_COLOR);
		}
		else{
			g.setColor(SCROLLBAR_DRAGGING_COLOR);
		}
		g.fillRect(this.p.x,this.s_pos,SCROLLBAR_SIZE,this.s_h);
	}



	public int get(){
		if (this.dh==this.s_h){
			return 0;
		}
		return (int)this._map(this.s_pos,0,this.dh-this.s_h,0,this.h-this.min_h);
	}



	public boolean hover(){
		return (this.p.x<=this.cls.MOUSE_POS.x&&this.cls.MOUSE_POS.x<=this.p.x+SCROLLBAR_SIZE&&this.s_pos<=this.cls.MOUSE_POS.y&&this.cls.MOUSE_POS.y<=this.s_pos+this.s_h);
	}



	public boolean drag(){
		return (this._dy_off!=-1);
	}



	private double _map(double v,double aa,double ab,double ba,double bb){
		return (v-aa)/(ab-aa)*(bb-ba)+ba;
	}
}