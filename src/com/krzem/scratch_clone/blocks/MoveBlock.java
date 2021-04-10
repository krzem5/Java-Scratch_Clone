package com.krzem.scratch_clone.blocks;



import com.krzem.scratch_clone.Block;
import com.krzem.scratch_clone.Graphics;



public class MoveBlock extends Block{
	@Override
	public String group(){
		return "0movement";
	}



	@Override
	public String tex_name(){
		return "movement";
	}



	@Override
	public double index(){
		return 0.2;
	}



	@Override
	public void draw(Graphics g){
		g.drawText("move by",this.pos.x+5,this.pos.y+2,16,BLOCK_TEXT_COLOR,"heveltica",java.awt.Font.BOLD);
	}
}
