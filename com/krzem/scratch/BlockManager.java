package com.krzem.scratch;



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
		this._recruive_load(BLOCK_CLASS_PATH,new BlockClassLoader(),new DefaultClassLoader());
	}



	private void _recruive_load(String d,BlockClassLoader bcl,DefaultClassLoader dcl){
		for (File f:new File(d).listFiles()){
			try{
				if (f.getName().endsWith("Block.java")){
					if (new File(f.getAbsolutePath().replace(".java",".class")).exists()&&new File(f.getAbsolutePath().replace(".java",".class")).isFile()){
						IO.dump_log("Compiled Class found: "+f.getAbsolutePath().replace(".java",".class"));
						this.lst.put(f.getName().replace("Block.java","").toLowerCase(),dcl.loadClass(f.getAbsolutePath().replace(".java",".class")));
					}
					else{
						this.lst.put(f.getName().replace("Block.java","").toLowerCase(),bcl.loadClass(f.getAbsolutePath()));
					}
					Block b=(Block)(this.lst.get(f.getName().replace("Block.java","").toLowerCase()).getConstructors()[0].newInstance());
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
					IO.dump_log("Loaded Block Class: "+f.getName());
				}
				else if (f.getName().indexOf(".")==-1){
					this._recruive_load(d+"\\"+f.getName(),bcl,dcl);
				}
			}
			catch (Exception e){
				IO.dump_error(e);
				System.exit(1);
			}
		}
	}
}