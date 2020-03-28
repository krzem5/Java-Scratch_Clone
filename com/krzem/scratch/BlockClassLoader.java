package com.krzem.scratch;



import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Class;
import java.lang.ClassLoader;
import java.lang.Exception;



public class BlockClassLoader extends ClassLoader{
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
		IO.dump_log("Compiling Class: "+nm);
		String dir=nm.substring(0,nm.lastIndexOf("\\"));
		String sp=System.getProperty("user.dir");
		String fn=nm.substring(nm.lastIndexOf("\\")+1);
		ProcessBuilder b=new ProcessBuilder("cmd.exe","/c","cd \""+dir+"\"&&javac -sourcepath "+sp+" "+fn);
		Process p=b.start();
		BufferedReader r=new BufferedReader(new InputStreamReader(p.getErrorStream()));
		String ln=null;
		boolean e=false;
		while ((ln=r.readLine())!=null){
			IO.dump_error_ln(ln);
			e=true;
		}
		if (e==true){
			IO._close();
			System.exit(1);
		}
		p.waitFor();
		InputStream inpS=new FileInputStream(new File(nm.replace(".java",".class")));
		ByteArrayOutputStream bS=new ByteArrayOutputStream();
		int cb=-1;
		while ((cb=inpS.read())!=-1){
			bS.write(cb);
		}
		byte[] bl=bS.toByteArray();
		String name=nm.substring(nm.lastIndexOf("\\")+1,nm.length()-".java".length());
		return this.defineClass(name,bl,0,bl.length);
	}
}