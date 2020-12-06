package pers.sfc.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class MyParallelogram extends Shape{
	public MyParallelogram(MyPoint p,double length,double width)
	{
		super.p = p;
		super.length = length;
		super.width = width;
	}

	public MyParallelogram(double x,double y,double length,double width)
	{
		this(new MyPoint(x,y),length,width);
	}

	public MyParallelogram()
	{
		this(new MyPoint(),125,75);
	}

	@Override
	public boolean contains(Point2D p) {//面积法
		var testp = new MyPoint(p.getX(),p.getY());
		var p1 = new MyPoint(super.p.getX()+super.length*0.2,super.p.getY());
		var p2 = new MyPoint(super.p.getX()+super.length,super.p.getY());
		var p3 = new MyPoint(super.p.getX(),super.p.getY()+super.width);
		var p4 = new MyPoint(super.p.getX()+super.length*0.8,super.p.getY()+super.width);
		double area = triArea(testp,p1,p2)+triArea(testp,p2,p3)+triArea(testp,p3,p4)+triArea(testp,p1,p4);
		if(area<super.length*0.8*super.width+C)
			return true;
		return false;
	}
	//画平行四边形
	@Override
	public void drawEntity(Graphics2D g) {
		double x = super.p.getX();
		double y = super.p.getY();
		g.setStroke(new BasicStroke(B));
		g.draw(new Line2D.Double(x,y+super.width,x+super.length*0.2,y));
		g.draw(new Line2D.Double(x,y+super.width,x+super.length*0.8,y+super.width));
		g.draw(new Line2D.Double(x+super.length,y,x+super.length*0.2,y));
		g.draw(new Line2D.Double(x+super.length,y,x+super.length*0.8,y+super.width));
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
	public void connectPoint(Graphics2D g) {
		//画四边小圆形
		g.setStroke(new BasicStroke(1));
		g.draw(new Ellipse2D.Double(super.p.getX()+super.length*0.5-5, super.p.getY()-5, 10, 10));
		g.draw(new Ellipse2D.Double(super.p.getX()+super.length*0.1-5, super.p.getY()+super.width*0.5-5, 10, 10));
		g.draw(new Ellipse2D.Double(super.p.getX()+super.length*0.5-5, super.p.getY()+super.width-5, 10, 10));
		g.draw(new Ellipse2D.Double(super.p.getX()+super.length*0.9-5, super.p.getY()+super.width*0.5-5, 10, 10));
		//小圆形填充白色
		g.setColor(Color.WHITE);
		g.fill(new Ellipse2D.Double(super.p.getX()+super.length*0.5-4.5, super.p.getY()-4.5, 9, 9));
		g.fill(new Ellipse2D.Double(super.p.getX()+super.length*0.1-4.5, super.p.getY()+super.width*0.5-4.5, 9, 9));
		g.fill(new Ellipse2D.Double(super.p.getX()+super.length*0.5-4.5, super.p.getY()+super.width-4.5, 9, 9));
		g.fill(new Ellipse2D.Double(super.p.getX()+super.length*0.9-4.5, super.p.getY()+super.width*0.5-4.5, 9, 9));
	}
}
