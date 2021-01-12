package com.krzem.scratch_clone;



import java.awt.Cursor;
import java.awt.Graphics2D;
import java.lang.Exception;
import javax.swing.JComponent;



public class Canvas extends JComponent{
	public Main cls;
	public CanvasRepaintManager CRM;



	public Canvas(Main cls){
		this.cls=cls;
		this.CRM=new CanvasRepaintManager(this.cls);
		System.setProperty("sun.java2d.opengl","true");
	}



	public void paintComponent(java.awt.Graphics _g){
		try{
			Graphics g=new Graphics(this.cls,(Graphics2D)_g.create());
			this.cls.draw(g);
			g._end();
		}
		catch (Exception e){
			IO.dump_error(e);
		}
	}



	public void addNotify(){
		super.addNotify();
		this.requestFocus();
	}
}
