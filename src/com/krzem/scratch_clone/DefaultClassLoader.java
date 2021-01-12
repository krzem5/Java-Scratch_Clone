package com.krzem.scratch_clone;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Class;
import java.lang.ClassLoader;
import java.lang.Exception;



public class DefaultClassLoader extends ClassLoader{
	@Override
	public Class<?> findClass(String nm) throws ClassNotFoundException{
		try{
			return this._load(nm);
		}
		catch (Exception e){
			IO.dump_error(e);
		}
		return null;
	}



	private Class<?> _load(String nm) throws IOException,InterruptedException{
		InputStream inpS=new FileInputStream(new File(nm));
		ByteArrayOutputStream bS=new ByteArrayOutputStream();
		int cb=-1;
		while ((cb=inpS.read())!=-1){
			bS.write(cb);
		}
		byte[] bl=bS.toByteArray();
		String name=nm.substring(nm.lastIndexOf("\\")+1,nm.length()-".class".length());
		return this.defineClass(name,bl,0,bl.length);
	}
}
