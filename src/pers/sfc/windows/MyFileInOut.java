package pers.sfc.windows;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import pers.sfc.shapes.Shape;

public class MyFileInOut {
	//�򿪵��ļ���
	private String fileName;
	//���л�����
	private MyIOStream mios;
	
	public MyFileInOut()
	{
		fileName = null;
		mios = new MyIOStream();
	}
	//����fileName
	public void reFileName()
	{
		fileName = null;
	}
	//���ļ�
	public ArrayList<?> open() throws ClassNotFoundException, IOException
	{
		ArrayList<?> list;
		var fc = new JFileChooser();
		fc.setCurrentDirectory(new File("../"));//Ĭ�ϴ�λ��
		fc.setDialogTitle("���ļ�");//����
		//fc.setSelectedFile(new File("test.xml"));//Ĭ���ļ���
		fc.showOpenDialog(null);
		fileName = fc.getSelectedFile().getAbsolutePath();
		if(fileName.endsWith(".xml")) {
			list = mios.objectInput(fileName);
			return list;
		}
		JOptionPane.showMessageDialog(null, "���ļ����ʹ���", "����", JOptionPane.ERROR_MESSAGE);
		return null;
	}

	//�洢�ļ�
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
			fc.setCurrentDirectory(new File("../"));//Ĭ�ϴ�λ��
			fc.setDialogTitle("�����ļ�");//����
			fc.setSelectedFile(new File("test.xml"));//Ĭ���ļ���
			fc.showSaveDialog(null);
			fileName = fc.getSelectedFile().getAbsolutePath();//����Ƚ�����·��
			if(fileName.endsWith(".xml"))
			{
				mios.objectOutput(list,fileName);
				return;
			}
			JOptionPane.showMessageDialog(null, "��ʹ��xml���ͽ��б���", "����", JOptionPane.ERROR_MESSAGE);
		}
	}


	//����ļ�
	public void saveAs(ArrayList<Shape> list) throws IOException
	{
		var fc = new JFileChooser();
		fc.setCurrentDirectory(new File("../"));//Ĭ�ϴ�λ��
		fc.setDialogTitle("�����ļ�");//����
		fc.setSelectedFile(new File("test.xml"));//Ĭ���ļ���
		fc.showSaveDialog(null);
		fileName = fc.getSelectedFile().getAbsolutePath();//����Ƚ�����·��
		if(fileName.endsWith(".xml"))
		{
			mios.objectOutput(list,fileName);
			return;
		}
		JOptionPane.showMessageDialog(null, "��ʹ��xml���ͽ��б���", "����", JOptionPane.ERROR_MESSAGE);
	}
	
	public void clean()
	{
		fileName = null;
	}
}
