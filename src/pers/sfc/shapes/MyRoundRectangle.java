package pers.sfc.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class MyRoundRectangle extends Shape{
	public MyRoundRectangle(MyPoint p,double length,double width)
	{
		super.p = p;
		super.length = length;
		super.width = width;
	}

	public MyRoundRectangle(double x,double y,double length,double width)
	{
		this(new MyPoint(x,y),length,width);
	}

	public MyRoundRectangle()
	{
		this(new MyPoint(),125,75);
	}

	@Override
	public boolean contains(Point2D p) {
		double x = p.getX();
		double y = p.getY();
		if(super.p.getX()<x && x<super.p.getX()+super.length && 
				super.p.getY()<y && y<super.p.getY()+super.width)
			return true;
		return false;
	}
	//»­Ô²½Ç¾ØÐÎ
	@Override
	public void drawEntity(Graphics2D g) {
		g.setStroke(new BasicStroke(B));
		g.draw(new RoundRectangle2D.Double(super.p.getX(), super.p.getY(), super.length, super.width,40.0,40.0));
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
}
