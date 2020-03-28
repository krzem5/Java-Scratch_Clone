import com.krzem.scratch_clone.*;



public class IfBlock extends CodeBlock{
	public String group(){
		return "1events";
	}



	public String tex_name(){
		return "events";
	}



	public double index(){
		return 0.2;
	}


	
	public void init(){
		this.Bconn=new OutputConnectorList(this.cls,this,"code","if");
	}



	public void draw(Graphics g){
		g.drawText("if",this.pos.x+5,this.pos.y+2,16,BLOCK_TEXT_COLOR,"heveltica",java.awt.Font.BOLD);
	}
}