package com.krzem.scratch_clone;



import java.io.File;
import java.io.IOException;
import java.lang.Class;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class BlockManager extends Constants{
	public Main cls;
	public ArrayList<String> groups=new ArrayList<String>();
	public Map<String,Class> lst;



	public BlockManager(Main cls){
		this.cls=cls;
		this.lst=new HashMap<String,Class>();
	}



	public Block get(String nm){
		try{
			Class c=this.lst.get(nm);
			if (c==null){
				return null;
			}
			Block b=(Block)(c.getConstructors()[0].newInstance());
			b._init(this.cls,this.cls.ev,nm,false);
			return b;
		}
		catch (Exception e){
			IO.dump_error(e);
		}
		return null;
	}



	public void load(){
		this._register_block(com.krzem.scratch_clone.blocks.ElseBlock.class);
		this._register_block(com.krzem.scratch_clone.blocks.IfBlock.class);
		this._register_block(com.krzem.scratch_clone.blocks.MoveBlock.class);
	}



	private void _register_block(Class c){
		IO.dump_log(String.format("Regitering Block '%s'...",c.getName().replace("com.krzem.scratch_clone.blocks.","")));
		this.lst.put(c.getName().replace("com.krzem.scratch_clone.blocks.",""),c);
		try{
			Block b=(Block)(Block)(c.getConstructors()[0].newInstance());
			boolean add=true;
			String g=b.group();
			for (String s:this.groups){
				if (s.equals(g)){
					add=false;
					break;
				}
			}
			if (add==true){
				this.groups.add(g);
			}
		}
		catch (Exception e){
			IO.dump_error(e);
			System.exit(1);
		}
	}
}
