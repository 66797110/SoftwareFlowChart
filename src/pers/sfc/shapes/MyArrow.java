package pers.sfc.shapes;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class MyArrow extends Shape{
	private double H = 10; // 箭头高度
	private double L = 5; // 底边的一半
	private MyPoint p1;
	private MyPoint p2;
	private MyPoint p3;
	private MyPoint p4;
	private MyPoint p5;
	private MyPoint p6;
	private Shape start;
	private Shape end;
	private Position startP;
	private Position endP;
	public MyArrow(Shape start,Shape end,Position startP,Position endP)
	{
		this.start = start;
		this.end = end;
		this.startP = startP;
		this.endP = endP;
		super.func = Func.ARROW;
		start.setConnect(startP);
		start.setArrow(this,startP,true);
		end.setConnect(endP);
		end.setArrow(this, endP, false);
		start.startPlus();
		end.endPlus();
	}
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
	//返回自己
	
	public void draw(Graphics2D g) {
		/*
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
		 */
		state.draw(this, g);
	}
	@Override
	public boolean containsN(Point2D p) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void drawEntity(Graphics2D g) {
		p1 = start.getPoint(startP);
		p6 = end.getPoint(endP);
		g.setStroke(new BasicStroke(2));
		GeneralPath triangle = new GeneralPath();
		if(endP.equals(Position.NORTH))
		{
			g.draw(new Line2D.Double(p1.getX(),p1.getY(),p6.getX(),p6.getY()-10));
			triangle.moveTo(p6.getX(),p6.getY());
			triangle.lineTo(p6.getX()-L,p6.getY()-H);
			triangle.lineTo(p6.getX()+L,p6.getY()-H);
		}
		else if(endP.equals(Position.WEST))
		{
			g.draw(new Line2D.Double(p1.getX(),p1.getY(),p6.getX()-10,p6.getY()));
			triangle.moveTo(p6.getX(),p6.getY());
			triangle.lineTo(p6.getX()-H,p6.getY()+L);
			triangle.lineTo(p6.getX()-H,p6.getY()-L);
		}
		else if(endP.equals(Position.SOUTH))
		{
			g.draw(new Line2D.Double(p1.getX(),p1.getY(),p6.getX(),p6.getY()+10));
			triangle.moveTo(p6.getX(),p6.getY());
			triangle.lineTo(p6.getX()-L,p6.getY()+H);
			triangle.lineTo(p6.getX()+L,p6.getY()+H);
		}
		else if(endP.equals(Position.EAST))
		{
			g.draw(new Line2D.Double(p1.getX(),p1.getY(),p6.getX()+10,p6.getY()));
			triangle.moveTo(p6.getX(),p6.getY());
			triangle.lineTo(p6.getX()+H,p6.getY()+L);
			triangle.lineTo(p6.getX()+H,p6.getY()-L);
		}
		triangle.closePath();
		//实心箭头
		g.fill(triangle);
	}
	@Override
	public String writeObject() {
		// TODO Auto-generated method stub
		return null;
	}
	public void putStart(Shape s,Position p)
	{
		this.start = s;
		this.startP = p;
	}
	public void putEnd(Shape s,Position p)
	{
		this.end = s;
		this.endP = p;
	}
	public void drawCirRect(Graphics2D g) {}
	public void onSize(double newX,double newY,double oldX,double oldY,int state) {}
	@Override
	public boolean contains(Point2D pIn) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean showDialog(Component parent) {
		// TODO Auto-generated method stub
		return false;
	}
	//获得终点
	public Shape getEnd()
	{
		return this.end;
	}
	//删除该箭头
	public void delete()
	{
		start.deConnectArrow(startP);
		end.deConnectArrow(endP);
	}
}