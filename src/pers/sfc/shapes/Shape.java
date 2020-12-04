package pers.sfc.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

abstract public class Shape {
	private static final double A = 5;//鼠标监听宽度
	protected MyPoint p; //左上角定位
	protected double length; //长
	protected double width; //宽
	//鼠标是否在图形内
	abstract public boolean contains(Point2D p);
	//画图
	abstract public void draw(Graphics2D g);
	//设置内部颜色
	//abstract public void setColorIn();
	//设置边框颜色
	//abstract public void setColorOn();
	//序列化
	abstract public String writeObject();
	//外接矩形
	public void drawCirRect(Graphics2D g)
	{
		//画虚线边框
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
        g.draw(new Rectangle2D.Double(this.p.getX(), this.p.getY(), this.length, this.width));
        //画四角小正方形
        g.setStroke(new BasicStroke(1));
        g.draw(new Rectangle2D.Double(this.p.getX()-5, this.p.getY()-5, 10, 10));
        g.draw(new Rectangle2D.Double(this.p.getX()+this.length-5, this.p.getY()-5, 10, 10));
        g.draw(new Rectangle2D.Double(this.p.getX()-5, this.p.getY()+this.width-5, 10, 10));
        g.draw(new Rectangle2D.Double(this.p.getX()+this.length-5, this.p.getY()+this.width-5, 10, 10));
        //小正方形填充白色
        g.setColor(Color.WHITE);
        g.fill(new Rectangle2D.Double(this.p.getX()-4, this.p.getY()-4, 9, 9.0));
        g.fill(new Rectangle2D.Double(this.p.getX()+this.length-4, this.p.getY()-4, 9, 9));
        g.fill(new Rectangle2D.Double(this.p.getX()-4, this.p.getY()+this.width-4, 9, 9));
        g.fill(new Rectangle2D.Double(this.p.getX()+this.length-4, this.p.getY()+this.width-4, 9, 9));
	}
	//鼠标是否在外接矩形上
	public int on(Point2D pIn)
	{
		double x = pIn.getX();
		double y = pIn.getY();
		if(this.p.getX()-A<x && x<this.p.getX()+A && this.p.getY()-A<y && y<this.p.getY()+A)
			return Cursor.NW_RESIZE_CURSOR;
		else if(this.p.getX()+this.length-A<x && x<this.p.getX()+this.length+A && this.p.getY()-A<y && y<this.p.getY()+A)
			return Cursor.NE_RESIZE_CURSOR;
		else if(this.p.getX()+this.length-A<x && x<this.p.getX()+this.length+A && this.p.getY()+this.width-A<y && y<this.p.getY()+this.width+A)
			return Cursor.SE_RESIZE_CURSOR;
		else if(this.p.getX()-A<x && x<this.p.getX()+A && this.p.getY()+this.width-A<y && y<this.p.getY()+this.width+A)
			return Cursor.SW_RESIZE_CURSOR;
		else
			return 0;
	}
	//移动
	public void Offset(double cx,double cy)
	{
		this.p.Offset(cx, cy);
	}
	//改变大小
	public void onSize(double newX,double newY,double oldX,double oldY,int state)
	{
		switch (state)
		{
		case Cursor.SE_RESIZE_CURSOR:
			this.length += newX-oldX;
			this.width += newY-oldY;
			break;
		case Cursor.NW_RESIZE_CURSOR:
			this.p.Offset(newX-oldX, newY-oldY);
			this.length -= newX-oldX;
			this.width -= newY-oldY;
			break;
		case Cursor.NE_RESIZE_CURSOR:
			this.p.Offset(0, newY-oldY);
			this.length += newX-oldX;
			this.width -= newY-oldY;
			break;
		case Cursor.SW_RESIZE_CURSOR:
			this.p.Offset(newX-oldX, 0);
			this.length -= newX-oldX;
			this.width += newY-oldY;
			break;
		}
	}
}
