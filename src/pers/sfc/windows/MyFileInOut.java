package pers.sfc.windows;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import pers.sfc.shapes.Shape;

public class MyFileInOut {
	//����
	//private ArrayList list;
	//�򿪵��ļ���
	private String FileName;
	//���л�����
	private MyIOStream oios;
	
	public MyFileInOut()
	{
		FileName = null;
		oios = new MyIOStream();
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
		FileName = fc.getSelectedFile().getAbsolutePath();
		if(FileName.endsWith(".xml")) {
			list = oios.objectInput(FileName);
			return list;
		}
		JOptionPane.showMessageDialog(null, "���ļ����ʹ���", "����", JOptionPane.ERROR_MESSAGE);
		return null;
	}

	//�洢�ļ�
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
			fc.setCurrentDirectory(new File("../"));//Ĭ�ϴ�λ��
			fc.setDialogTitle("�����ļ�");//����
			fc.setSelectedFile(new File("test.xml"));//Ĭ���ļ���
			fc.showSaveDialog(null);
			FileName = fc.getSelectedFile().getAbsolutePath();//����Ƚ�����·��
			if(FileName.endsWith(".xml"))
			{
				oios.objectOutput(list,FileName);
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
		FileName = fc.getSelectedFile().getAbsolutePath();//����Ƚ�����·��
		if(FileName.endsWith(".xml"))
		{
			oios.objectOutput(list,FileName);
			return;
		}
		JOptionPane.showMessageDialog(null, "��ʹ��xml���ͽ��б���", "����", JOptionPane.ERROR_MESSAGE);
	}
	
	public void clean()
	{
		FileName = null;
	}
}
