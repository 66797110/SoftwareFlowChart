package pers.sfc.windows;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import pers.sfc.execute.CodeExecute;
import pers.sfc.execute.CodeGenerate;
import pers.sfc.shapes.Shape;


public class MyDocument {
	//����
	private ArrayList<Shape> list;
	//�ڵ����
	private int i;

	public MyDocument()
	{
		list = new ArrayList<Shape>();
		i = 0;
	}
	public MyDocument(ArrayList<Shape> list)
	{
		this.list = list;
		i = 0;
	}
	//����
	public void update(Shape s)
	{
		list.add(0,s);
	}
	//����
	public void replace(Shape s)
	{
		remove(s);
		update(s);
	}
	//��һ��
	public Shape getNext()
	{
		if(i < list.size())
			return list.get(i++);
		i = 0;
		return null;
	}
	//��һ��
	public Shape getFirst()
	{
		if(!list.isEmpty())
			return list.get(0);
		return null;
	}
	//�ҵ����ͼ�Σ��ڲ���
	public Shape findIn(Point2D p)
	{
		for(Shape s : list)
		{
			if(s.contains(p))
				return s;
		}
		return null;
	}
	//�ҵ����ͼ�Σ����ϣ�
	public Shape findOn(Point2D p)
	{
		for(Shape s : list)
		{
			if(s.on(p) > 0)
				return s;
		}
		return null;
	}
	//���ñ�����
	public void setExecute(CodeExecute execute)
	{
		for(Shape s : list)
		{
			s.setExecute(execute);
		}
	}
	//����������
	public void setGenerate(CodeGenerate generate)
	{
		for(Shape s : list)
		{
			s.setGenerate(generate);
		}
	}
	//�������ͼ���Ƶ�����ͷ��ʵ�ֲ�Σ�
	public void move(Shape s)
	{
		if(s == null)
			return;
		Shape now = s;
		list.remove(s);
		list.add(0,now);
	}
	//��ͼ����ӵ�����β
	public void moveToEnd(Shape s)
	{
		if(s == null)
			return;
		list.remove(s);
		list.add(s);
	}
	//ɾ��ͼ��
	public void remove(Shape s)
	{
		if(s == null)
			return;
		list.remove(s);
	}
	//��������
	public void newList(ArrayList<?> list)
	{
		this.list = (ArrayList<Shape>)list;
	}
	//��������
	public ArrayList<Shape> getList()
	{
		return list;
	}
	//����������ļ���
	public void clean()
	{
		list.clear();
	}
	public void reset()
	{
		this.i = 0;
	}
}