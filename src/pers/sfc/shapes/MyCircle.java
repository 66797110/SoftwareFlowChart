package pers.sfc.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class MyCircle extends Shape{
	public MyCircle(MyPoint p,double length,double width)
	{
		super.p = p;
		super.length = length;
		super.width = width;
	}
	
	public MyCircle(double x,double y,double length,double width)
	{
		this(new MyPoint(x,y),length,width);
	}

	public MyCircle()
	{
		this(new MyPoint(),40,40);
	}
	
	@Override
	public boolean contains(Point2D p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setStroke(new BasicStroke(3));
		g.setColor(Color.BLACK);
		g.draw(new Ellipse2D.Double(super.p.getX(), super.p.getY(), super.length, super.width));
	}

	@Override
	public String writeObject() {
		// TODO Auto-generated method stub
		return null;
	}

}
