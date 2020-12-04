package pers.sfc.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class MyRectangle extends Shape{
	public MyRectangle(MyPoint p,double length,double width)
	{
		super.p = p;
		super.length = length;
		super.width = width;
	}

	public MyRectangle(double x,double y,double length,double width)
	{
		this(new MyPoint(x,y),length,width);
	}

	public MyRectangle()
	{
		this(new MyPoint(),125,75);
	}
	
	//鼠标是否在图形内
	@Override
	public boolean contains(Point2D p)
	{
		double x = p.getX();
		double y = p.getY();
		if(super.p.getX()<x && x<super.p.getX()+super.length && 
				super.p.getY()<y && y<super.p.getY()+super.width)
			return true;
		return false;
	}
	//画矩形
	@Override
	public void draw(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(3));
		g.draw(new Rectangle2D.Double(super.p.getX(), super.p.getY(), super.length, super.width));
	}
	//序列化
	@Override
	public String writeObject()
	{
		String thisClass = " @MyRectangle";
		String thisData = " @p = "+super.p.writeObject()+" @longth = "+super.length+" @width = "+super.width+" ";
		return "</"+thisClass+thisData+"/>";
	}
}
