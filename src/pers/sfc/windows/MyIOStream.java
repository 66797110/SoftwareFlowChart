package pers.sfc.windows;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;

import pers.sfc.shapes.Shape;


public class MyIOStream {
	private ArrayList<Shape> list;
	//序列化
	public void objectOutput(ArrayList<Shape> list,String File)
	{
		XStream xStream = new XStream();
		//XStream.setupDefaultSecurity(xStream);
		//xStream.allowTypes(new Class[]{Shape.class});
		FileOutputStream foStream;
		try {
			foStream = new FileOutputStream(File);
			xStream.toXML(list,foStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//反序列化
	public ArrayList<Shape> objectInput(String File)
	{
		XStream xStream = new XStream();
		//XStream.setupDefaultSecurity(xStream);
		//xStream.allowTypes(new Class[]{Shape.class});
		FileInputStream flStream;
		try {
			flStream = new FileInputStream(File);
			list = (ArrayList<Shape>)xStream.fromXML(flStream);
			return list;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
