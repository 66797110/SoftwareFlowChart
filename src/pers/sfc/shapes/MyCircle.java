package pers.sfc.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
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
		if(Math.sqrt(Math.pow(width*0.5+super.p.getX()-p.getX(), 2)+Math.pow(width*0.5+super.p.getY()-p.getY(), 2))<=width*0.5)
			return true;
		return false;
	}

	@Override
	public void drawEntity(Graphics2D g) {
		g.setStroke(new BasicStroke(B));
		g.draw(new Ellipse2D.Double(super.p.getX(), super.p.getY(), super.length, super.width));
	}

	@Override
	public String writeObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Graphics2D g) {
		state.draw(this, g);
	}
	
	@Override
	public void onSize(double newX,double newY,double oldX,double oldY,int state)
	{
		double disWL;
		if(newY-oldY<newX-oldX)
			disWL=newX-oldX;
		else
			disWL=newY-oldY;
		switch (state)
		{
		case Cursor.SE_RESIZE_CURSOR:
			this.length += disWL;
			this.width += disWL;
			break;
		case Cursor.NW_RESIZE_CURSOR:
			this.p.Offset(disWL, disWL);
			this.length -= disWL;
			this.width -= disWL;
			break;
		case Cursor.NE_RESIZE_CURSOR:
			this.p.Offset(0, disWL);
			this.length += disWL;
			this.width -= disWL;
			break;
		case Cursor.SW_RESIZE_CURSOR:
			this.p.Offset(disWL, 0);
			this.length -= disWL;
			this.width += disWL;
			break;
		}
	}
}
