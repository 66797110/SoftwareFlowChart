package pers.sfc.shapes;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

public class MyArrow{
	private double H = 10; // 箭头高度
	private double L = 5; // 底边的一半
	private MyPoint p1;
	private MyPoint p2;
	private MyPoint p3;
	private MyPoint p4;
	private MyPoint p5;
	private MyPoint p6;
	public MyArrow(MyPoint p1,MyPoint p6)
	{
		this.p1=p1;
		this.p6=p6;
	}
	public MyArrow(double x1,double y1,double x6,double y6)
	{
		this(new MyPoint(x1,y1),new MyPoint(x6,y6));
	}
	public MyArrow()
	{
		this(100,100,100,150);
	}
	public void draw(Graphics2D g) {
		g.setStroke(new BasicStroke(2));
		g.draw(new Line2D.Double(p1.getX(),p1.getY(),p6.getX(),p6.getY()-10));
		g.setStroke(new BasicStroke(1));
		GeneralPath triangle = new GeneralPath();
		triangle.moveTo(p6.getX(),p6.getY());
		triangle.lineTo(p6.getX()-L,p6.getY()-H);
		triangle.lineTo(p6.getX()+L,p6.getY()-H);
		triangle.closePath();
		//实心箭头
		g.fill(triangle);
	}
}