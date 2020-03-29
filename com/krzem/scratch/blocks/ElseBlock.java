import com.krzem.scratch_clone.*;



public class ElseBlock extends CodeBlock{
	public String group(){
		return "1events";
	}
	


	public String tex_name(){
		return "events";
	}



	public double index(){
		return 1.0;
	}


	
	public void init(){
		this.Tconn=new InputConnectorList(this.cls,this,"code","if");
	}



	public void draw(Graphics g){
		g.drawText("else",this.pos.x+5,this.pos.y+2,16,BLOCK_TEXT_COLOR,"heveltica",java.awt.Font.BOLD);
	}
}