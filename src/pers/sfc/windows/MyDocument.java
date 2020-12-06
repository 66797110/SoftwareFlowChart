package pers.sfc.windows;

import java.awt.geom.Point2D;
import java.util.ArrayList;

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
	//��һ��
	public Shape getNext()
	{
		if(i < list.size())
			return list.get(i++);
		i = 0;
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
	//�������ͼ���Ƶ�����ͷ��ʵ�ֲ�Σ�
	public void move(Shape s)
	{
		if(s == null)
			return;
		Shape now = s;
		list.remove(s);
		list.add(0,now);
	}
	//ɾ��ͼ��
	public void remove(Shape s)
	{
		if(s == null)
			return;
		list.remove(s);
	}
	//��������
	@SuppressWarnings("unchecked")
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
}