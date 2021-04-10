package com.krzem.scratch_clone.blocks;



import com.krzem.scratch_clone.CodeBlock;
import com.krzem.scratch_clone.Graphics;
import com.krzem.scratch_clone.InputConnectorList;



public class ElseBlock extends CodeBlock{
	@Override
	public String group(){
		return "1events";
	}



	@Override
	public String tex_name(){
		return "events";
	}



	@Override
	public double index(){
		return 1.0;
	}



	@Override
	public void init(){
		this.Tconn=new InputConnectorList(this.cls,this,"code","if");
	}



	@Override
	public void draw(Graphics g){
		g.drawText("else",this.pos.x+5,this.pos.y+2,16,BLOCK_TEXT_COLOR,"heveltica",java.awt.Font.BOLD);
	}
}
