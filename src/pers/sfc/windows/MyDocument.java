package pers.sfc.windows;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import pers.sfc.execute.CodeExecute;
import pers.sfc.execute.CodeGenerate;
import pers.sfc.shapes.Shape;


public class MyDocument {
	//链表
	private ArrayList<Shape> list;
	//节点遍历
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
	//插入
	public void update(Shape s)
	{
		list.add(0,s);
	}
	//重置
	public void replace(Shape s)
	{
		remove(s);
		update(s);
	}
	//下一个
	public Shape getNext()
	{
		if(i < list.size())
			return list.get(i++);
		i = 0;
		return null;
	}
	//第一个
	public Shape getFirst()
	{
		if(!list.isEmpty())
			return list.get(0);
		return null;
	}
	//找点击的图形（内部）
	public Shape findIn(Point2D p)
	{
		for(Shape s : list)
		{
			if(s.contains(p))
				return s;
		}
		return null;
	}
	//找点击的图形（线上）
	public Shape findOn(Point2D p)
	{
		for(Shape s : list)
		{
			if(s.on(p) > 0)
				return s;
		}
		return null;
	}
	//设置编译器
	public void setExecute(CodeExecute execute)
	{
		for(Shape s : list)
		{
			s.setExecute(execute);
		}
	}
	//设置生成器
	public void setGenerate(CodeGenerate generate)
	{
		for(Shape s : list)
		{
			s.setGenerate(generate);
		}
	}
	//将点击的图形移到链表头（实现层次）
	public void move(Shape s)
	{
		if(s == null)
			return;
		Shape now = s;
		list.remove(s);
		list.add(0,now);
	}
	//将图形添加到链表尾
	public void moveToEnd(Shape s)
	{
		if(s == null)
			return;
		list.remove(s);
		list.add(s);
	}
	//删除图形
	public void remove(Shape s)
	{
		if(s == null)
			return;
		list.remove(s);
	}
	//更新链表
	public void newList(ArrayList<?> list)
	{
		this.list = (ArrayList<Shape>)list;
	}
	//返回链表
	public ArrayList<Shape> getList()
	{
		return list;
	}
	//清空链表与文件名
	public void clean()
	{
		list.clear();
	}
	public void reset()
	{
		this.i = 0;
	}
}