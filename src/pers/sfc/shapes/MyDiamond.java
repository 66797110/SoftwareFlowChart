package pers.sfc.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class MyDiamond extends Shape{
	public MyDiamond(MyPoint p,double length,double width)
	{
		super.p = p;
		super.length = length;
		super.width = width;
	}
	
	public MyDiamond(double x,double y,double length,double width)
	{
		this(new MyPoint(x,y),length,width);
	}

	public MyDiamond()
	{
		this(new MyPoint(),125,75);
	}

	//鼠标是否在图形内
	@Override
	public boolean contains(Point2D p) {
		// TODO Auto-generated method stub
		return false;
	}
	//画菱形
	@Override
	public void draw(Graphics2D g) {
		double x = super.p.getX();
		double y = super.p.getY();
		g.setStroke(new BasicStroke(3));
		g.setColor(Color.BLACK);
		g.draw(new Line2D.Double(x,y+super.width/2,x+super.length/2,y));
		g.draw(new Line2D.Double(x,y+super.width/2,x+super.length/2,y+super.width));
		g.draw(new Line2D.Double(x+super.length,y+super.width/2,x+super.length/2,y));
		g.draw(new Line2D.Double(x+super.length,y+super.width/2,x+super.length/2,y+super.width));
	}

	@Override
	public String writeObject() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
