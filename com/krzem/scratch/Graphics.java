package com.krzem.scratch;



import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;



public class Graphics{
	public Main cls;
	public Cursor c=new Cursor(Cursor.DEFAULT_CURSOR);
	private Graphics _p=null;
	private Graphics2D g;
	private Vector _off=new Vector(0,0);
	private Vector _bo=new Vector(0,0);



	public Graphics(Main cls,Graphics2D g){
		this.cls=cls;
		this.g=g;
		this.g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		this.g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		this.g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		this.g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		this.g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		this.g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	}



	public void set_offset(int x,int y){
		this._off=new Vector(x+this._bo.x,y+this._bo.y);
	}



	public void clear_offset(){
		this._off=new Vector(this._bo.x,this._bo.y);
	}



	public Graphics clip(int x,int y,int w,int h){
		Graphics ng=new Graphics(this.cls,(Graphics2D)this.g.create(x-this._bo.x,y-this._bo.y,w,h));
		ng._set_base_offset(x,y);
		ng._p=(this._p==null?this:this._p);
		return ng;
	}



	public Graphics clone(){
		Graphics ng=new Graphics(this.cls,(Graphics2D)this.g.create());
		ng._set_base_offset(this._off.x,this._off.y);
		ng._p=(this._p==null?this:this._p);
		return ng;
	}



	public void setColor(Color c){
		this.g.setColor(c);
	}



	public void setCursor(Cursor c){
		(this._p==null?this:this._p).c=c;
	}


	public void fillRect(int x,int y,int w,int h){
		this.g.fillRect(x-this._off.x,y-this._off.y,w,h);
	}



	public void drawImage(BufferedImage i,int x,int y,int w,int h){
		this.g.drawImage(i,x-this._off.x,y-this._off.y,w,h,this.cls.frame);
	}



	public void drawText(String txt,int x,int y,int s,Color c,String fn,int fm){
		if (fn.equals("blocky")){
			this.cls.FONT.draw(this,txt,x,y,s,c);
		}
		else{
			this.g.setFont(new Font(fn,fm,s));
			this.setColor(c);
			this.g.drawString(txt,x-this._off.x,y-this._off.y+this.g.getFontMetrics().getAscent());
		}
	}



	public void _end(){
		this.g.dispose();
		this.cls.canvas.setCursor(this.c);
	}



	private void _set_base_offset(int x,int y){
		this._bo=new Vector(x,y);
		this._off=new Vector(this._bo.x,this._bo.y);
	}
}