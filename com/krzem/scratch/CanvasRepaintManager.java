package com.krzem.scratch;



import javax.swing.RepaintManager;



public class CanvasRepaintManager extends RepaintManager{
	public Main cls;



	public CanvasRepaintManager(Main cls){
		this.cls=cls;
		RepaintManager.setCurrentManager(this);
	}



	public void paint_canvas(){
		this.addDirtyRegion(this.cls.canvas,0,0,this.cls.WINDOW_SIZE.width,this.cls.WINDOW_SIZE.height);
		this.paintDirtyRegions();
	}
}