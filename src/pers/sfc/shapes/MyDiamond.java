package pers.sfc.shapes;

import java.awt.BasicStroke;
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
	public boolean contains(Point2D p) {//面积法
		var testp = new MyPoint(p.getX(),p.getY());
		var p1 = new MyPoint(super.p.getX()+super.length*0.5,super.p.getY());
		var p2 = new MyPoint(super.p.getX(),super.p.getY()+super.width*0.5);
		var p3 = new MyPoint(super.p.getX()+super.length*0.5,super.p.getY()+super.width);
		var p4 = new MyPoint(super.p.getX()+super.length,super.p.getY()+super.width*0.5);
		double area = triArea(testp,p1,p2)+triArea(testp,p2,p3)+triArea(testp,p3,p4)+triArea(testp,p1,p4);
		if(area<super.length*0.5*super.width+C)
			return true;
		return false;
	}
	//画菱形
	@Override
	public void drawEntity(Graphics2D g) {
		double x = super.p.getX();
		double y = super.p.getY();
		g.setStroke(new BasicStroke(B));
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

	@Override
	public void draw(Graphics2D g) {
		state.draw(this, g);
	}
}
