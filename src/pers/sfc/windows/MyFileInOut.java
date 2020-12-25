package pers.sfc.windows;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import pers.sfc.shapes.Shape;

public class MyFileInOut {
	//打开的文件名
	private String fileName;
	//序列化对象
	private MyIOStream mios;
	
	public MyFileInOut()
	{
		fileName = null;
		mios = new MyIOStream();
	}
	//重置fileName
	public void reFileName()
	{
		fileName = null;
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
		fileName = fc.getSelectedFile().getAbsolutePath();
		if(fileName.endsWith(".xml")) {
			list = mios.objectInput(fileName);
			return list;
		}
		JOptionPane.showMessageDialog(null, "打开文件类型错误", "错误", JOptionPane.ERROR_MESSAGE);
		return null;
	}

	//存储文件
	public void save(ArrayList<Shape> list) throws IOException
	{
		if(fileName != null)
		{
			mios.objectOutput(list,fileName);
			return;
		}
		else
		{
			var fc = new JFileChooser();
			fc.setCurrentDirectory(new File("../"));//默认打开位置
			fc.setDialogTitle("保存文件");//标题
			fc.setSelectedFile(new File("test.xml"));//默认文件名
			fc.showSaveDialog(null);
			fileName = fc.getSelectedFile().getAbsolutePath();//获得稳健绝对路径
			if(fileName.endsWith(".xml"))
			{
				mios.objectOutput(list,fileName);
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
		fileName = fc.getSelectedFile().getAbsolutePath();//获得稳健绝对路径
		if(fileName.endsWith(".xml"))
		{
			mios.objectOutput(list,fileName);
			return;
		}
		JOptionPane.showMessageDialog(null, "请使用xml类型进行保存", "错误", JOptionPane.ERROR_MESSAGE);
	}
	
	public void clean()
	{
		fileName = null;
	}
}
