package pers.sfc.windows;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import pers.sfc.shapes.Shape;

public class MyFileInOut {
	//链表
	//private ArrayList list;
	//打开的文件名
	private String FileName;
	//序列化对象
	private MyIOStream oios;
	
	public MyFileInOut()
	{
		FileName = null;
		oios = new MyIOStream();
	}
	
	//打开文件
	public ArrayList<?> open() throws ClassNotFoundException, IOException
	{
		ArrayList<?> list;
		var fc = new JFileChooser();
		fc.setCurrentDirectory(new File("../"));//默认打开位置
		fc.setDialogTitle("打开文件");//标题
		//fc.setSelectedFile(new File("test.xml"));//默认文件名
		fc.showOpenDialog(null);
		FileName = fc.getSelectedFile().getAbsolutePath();
		if(FileName.endsWith(".xml")) {
			list = oios.objectInput(FileName);
			return list;
		}
		JOptionPane.showMessageDialog(null, "打开文件类型错误", "错误", JOptionPane.ERROR_MESSAGE);
		return null;
	}

	//存储文件
	public void save(ArrayList<Shape> list) throws IOException
	{
		if(FileName != null)
		{
			oios.objectOutput(list,FileName);
			return;
		}
		else
		{
			var fc = new JFileChooser();
			fc.setCurrentDirectory(new File("../"));//默认打开位置
			fc.setDialogTitle("保存文件");//标题
			fc.setSelectedFile(new File("test.xml"));//默认文件名
			fc.showSaveDialog(null);
			FileName = fc.getSelectedFile().getAbsolutePath();//获得稳健绝对路径
			if(FileName.endsWith(".xml"))
			{
				oios.objectOutput(list,FileName);
				return;
			}
			JOptionPane.showMessageDialog(null, "请使用xml类型进行保存", "错误", JOptionPane.ERROR_MESSAGE);
		}
	}


	//另存文件
	public void saveAs(ArrayList<Shape> list) throws IOException
	{
		var fc = new JFileChooser();
		fc.setCurrentDirectory(new File("../"));//默认打开位置
		fc.setDialogTitle("保存文件");//标题
		fc.setSelectedFile(new File("test.xml"));//默认文件名
		fc.showSaveDialog(null);
		FileName = fc.getSelectedFile().getAbsolutePath();//获得稳健绝对路径
		if(FileName.endsWith(".xml"))
		{
			oios.objectOutput(list,FileName);
			return;
		}
		JOptionPane.showMessageDialog(null, "请使用xml类型进行保存", "错误", JOptionPane.ERROR_MESSAGE);
	}
	
	public void clean()
	{
		FileName = null;
	}
}
