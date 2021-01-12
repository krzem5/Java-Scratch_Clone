package com.krzem.scratch_clone;



import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.Exception;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class IO extends Constants{
	private static String _ID=new File(LOG_PATH+"/"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+".log").getAbsolutePath();
	private static PrintWriter _wr=IO._writer();
	private static String fp="";
	public static boolean _err=false;


	public static void _close(){
		if (IO._wr==null){
			return;
		}
		IO.dump_log("Saving Log to file: "+IO._ID);
		IO._wr.close();
	}



	public static String get_file_path(){
		return IO.fp;
	}



	public static void load_from_file(EditorView ev,String fp){
		IO.fp=fp;
		try{
			ev.BLOCKS=new ArrayList<List<Block>>();
			Document doc=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fp);
			doc.getDocumentElement().normalize();
			Element root=doc.getDocumentElement();
			for (Element blc:IO._get_children_by_tag_name(root,"block-chain")){
				Element pos_e=IO._get_children_by_tag_name(blc,"pos").get(0);
				if (pos_e==null){
					continue;
				}
				Vector pos=new Vector(Integer.parseInt(IO._get_children_by_tag_name(pos_e,"x").get(0).getTextContent())+ev.INNER_BORDER.x,Integer.parseInt(IO._get_children_by_tag_name(pos_e,"y").get(0).getTextContent())+ev.INNER_BORDER.y);
				if (IO._get_children_by_tag_name(blc,"block-list").size()>0){
					Element lst=IO._get_children_by_tag_name(blc,"block-list").get(0);
					ArrayList<Block> chain=IO._parse_code(lst,ev);
					if (chain.size()==0){
						continue;
					}
					chain.get(0).pos=pos;
					ev.BLOCKS.add(chain);
				}
			}
		}
		catch (Exception e){
			IO.dump_error(e);
		}
	}



	public static void save_to_file(EditorView ev,String fp){
		if (fp.length()==0){
			fp=IO.fp+"";
		}
		try{
			Document doc=DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element root=doc.createElement("scratch");
			for (List<Block> bl:ev.BLOCKS){
				root.appendChild(IO._encode_block_list(ev,bl,"block-chain",doc,true));
			}
			doc.appendChild(root);
			StreamResult o=new StreamResult(new FileWriter(new File(fp)));
			Transformer t=TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.INDENT,"yes");
			t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","4");
			t.setOutputProperty(OutputKeys.VERSION,"1.0");
			t.setOutputProperty(OutputKeys.ENCODING,"utf-8");
			t.transform(new DOMSource(doc),o);
		}
		catch (Exception e){
			IO.dump_error(e);
		}
	}



	public static void dump_log(String s){
		try{
			if (IO._wr!=null){
				IO._wr.println(s);
			}
			System.out.println(s);
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}



	public static void dump_error(Exception e){
		IO._err=true;
		try{
			if (IO._wr!=null){
				e.printStackTrace(IO._wr);
			}
			e.printStackTrace(System.err);
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}



	public static void dump_error_ln(String ln){
		IO._err=true;
		try{
			if (IO._wr!=null){
				IO._wr.println(ln);
			}
			System.err.println(ln);
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}



	private static Element _encode_block_list(EditorView ev,List<Block> lst,String otnm,Document doc,boolean pos){
		Element o=doc.createElement(otnm);
		Element bl_e=o;
		if (pos==true){
			Element pos_e=doc.createElement("pos");
			Element pos_x_e=doc.createElement("x");
			pos_x_e.appendChild(doc.createTextNode(Integer.toString(lst.get(0).pos.x-ev.INNER_BORDER.x)));
			pos_e.appendChild(pos_x_e);
			Element pos_y_e=doc.createElement("y");
			pos_y_e.appendChild(doc.createTextNode(Integer.toString(lst.get(0).pos.y-ev.INNER_BORDER.y)));
			pos_e.appendChild(pos_y_e);
			o.appendChild(pos_e);
			bl_e=doc.createElement("block-list");
			o.appendChild(bl_e);
		}
		for (Block b:lst){
			Element b_e=doc.createElement("block");
			Element b_nm_e=doc.createElement("name");
			b_nm_e.appendChild(doc.createTextNode(b.nm));
			b_e.appendChild(b_nm_e);
			if (b.code!=null){
				b_e.appendChild(IO._encode_block_list(ev,b.code,"code",doc,false));
			}
			bl_e.appendChild(b_e);
		}
		return o;
	}



	private static ArrayList<Block> _parse_code(Element pe,EditorView ev){
		ArrayList<Block> o=new ArrayList<Block>();
		for (Element b_e:IO._get_children_by_tag_name(pe,"block")){
			Block b=ev.cls.BLOCK_MANAGER.get(IO._get_children_by_tag_name(b_e,"name").get(0).getTextContent());
			if (b==null){
				continue;
			}
			if (b.code!=null){
				b.code=IO._parse_code(IO._get_children_by_tag_name(b_e,"code").get(0),ev);
			}
			o.add(b);
		}
		return o;
	}



	private static ArrayList<Element> _get_children_by_tag_name(Element p,String tn){
		ArrayList<Element> o=new ArrayList<Element>();
		NodeList cl=p.getChildNodes();
		for (int j=0;j<cl.getLength();j++){
			if (cl.item(j).getNodeType()!=Node.ELEMENT_NODE){
				continue;
			}
			Element e=(Element)cl.item(j);
			if (e.getTagName().equals(tn)){
				o.add(e);
			}
		}
		return o;
	}



	private static PrintWriter _writer(){
		if (GENERATE_LOG_FILES==false){
			System.out.println("[LOG FILES DISABLED]");
			return null;
		}
		try{
			System.out.println("Log File Path: "+IO._ID);
			return new PrintWriter(IO._ID,"utf-8");
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
