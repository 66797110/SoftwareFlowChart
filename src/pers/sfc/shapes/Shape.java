package pers.sfc.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

abstract public class Shape {
	private static final double A = 5;//鼠标监听宽度
	protected static final float B = 2;//画笔宽度
	protected static final float C = 2;//面积容错
	protected MyPoint p; //左上角定位
	protected double length; //长
	protected double width; //宽
	protected State state = new NormalState();
	protected State lstate;
	//鼠标是否在图形内
	abstract public boolean contains(Point2D p);
	//画图
	abstract public void draw(Graphics2D g);
	//画图内部
	abstract public void drawEntity(Graphics2D g);
	//设置内部颜色
	//abstract public void setColorIn();
	//设置边框颜色
	//abstract public void setColorOn();
	//序列化
	abstract public String writeObject();
	//画连接点
	public void connectPoint(Graphics2D g)
	{
		//画四边小圆形
				g.setStroke(new BasicStroke(1));
				g.draw(new Ellipse2D.Double(this.p.getX()+this.length*0.5-5, this.p.getY()-5, 10, 10));
				g.draw(new Ellipse2D.Double(this.p.getX()-5, this.p.getY()+this.width*0.5-5, 10, 10));
				g.draw(new Ellipse2D.Double(this.p.getX()+this.length*0.5-5, this.p.getY()+this.width-5, 10, 10));
				g.draw(new Ellipse2D.Double(this.p.getX()+this.length-5, this.p.getY()+this.width*0.5-5, 10, 10));
				//小圆形填充白色
				g.setColor(Color.WHITE);
				g.fill(new Ellipse2D.Double(this.p.getX()+this.length*0.5-4.5, this.p.getY()-4.5, 9, 9));
				g.fill(new Ellipse2D.Double(this.p.getX()-4.5, this.p.getY()+this.width*0.5-4.5, 9, 9));
				g.fill(new Ellipse2D.Double(this.p.getX()+this.length*0.5-4.5, this.p.getY()+this.width-4.5, 9, 9));
				g.fill(new Ellipse2D.Double(this.p.getX()+this.length-4.5, this.p.getY()+this.width*0.5-4.5, 9, 9));
	}
	//鼠标点击连接点
	public int getPoint(Point2D pIn)
	{
		if(!this.state.getClass().equals("SelectState"))
			return Position.NONE.ordinal();
		double x=pIn.getX();
		double y=pIn.getY();
		if(Math.sqrt(Math.pow(this.p.getX()+this.length*0.5-x, 2)+Math.pow(this.p.getY()-y, 2))<=5)//上方圆圈
			return Position.NORTH.ordinal();
		else if(Math.sqrt(Math.pow(this.p.getX()-x, 2)+Math.pow(this.p.getY()+this.width*0.5-y, 2))<=5)//左方圆圈
			return Position.WEST.ordinal();
		else if(Math.sqrt(Math.pow(this.p.getX()+this.length*0.5-x, 2)+Math.pow(this.p.getY()+this.width-y, 2))<=5)//下方圆圈
			return Position.SOUTH.ordinal();
		else if(Math.sqrt(Math.pow(this.p.getX()+this.length-x, 2)+Math.pow(this.p.getY()+this.width*0.5-y, 2))<=5)//右方圆圈
			return Position.EAST.ordinal();
		else
			return Position.NONE.ordinal();
	}
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
		g.fill(new Rectangle2D.Double(this.p.getX()-4, this.p.getY()-4, 9, 9));
		g.fill(new Rectangle2D.Double(this.p.getX()+this.length-4, this.p.getY()-4, 9, 9));
		g.fill(new Rectangle2D.Double(this.p.getX()-4, this.p.getY()+this.width-4, 9, 9));
		g.fill(new Rectangle2D.Double(this.p.getX()+this.length-4, this.p.getY()+this.width-4, 9, 9));
	}
	//鼠标是否在外接矩形的四角上
	public int on(Point2D pIn)
	{
		return this.state.on(this,pIn);
	}
	//鼠标是否在外接矩形的四角上
	public int onCorner(Point2D pIn)
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
	//计算三角形面积
	protected static final double triArea(MyPoint a,MyPoint b,MyPoint c)
	{
		return Math.abs((a.getX()*b.getY()+b.getX()*c.getY()+c.getX()*a.getY()-b.getX()*a.getY()-c.getX()*b.getY()-a.getX()*c.getY())/2.0); 
	}
	//设置状态
	public void setState(State state)
	{
		this.lstate = this.state;
		this.state = state;
	}
	//回调状态
	public void backState()
	{
		this.state = this.lstate;
	}
	//移动状态
	/*
	public class MoveState implements State{
		@Override
		public void draw(Shape s,Graphics2D g) {
			g.setColor(Color.BLACK);
			//s.drawEntity(g);
			Shape.this.drawEntity(g);
			g.setColor(Color.BLACK);
			//s.drawCirRect(g);
			//画虚线边框
			g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
			g.draw(new Rectangle2D.Double(Shape.this.p.getX(), Shape.this.p.getY(), Shape.this.length, Shape.this.width));
			//画四角小正方形
			g.setStroke(new BasicStroke(1));
			g.draw(new Rectangle2D.Double(Shape.this.p.getX()-5, Shape.this.p.getY()-5, 10, 10));
			g.draw(new Rectangle2D.Double(Shape.this.p.getX()+Shape.this.length-5, Shape.this.p.getY()-5, 10, 10));
			g.draw(new Rectangle2D.Double(Shape.this.p.getX()-5, Shape.this.p.getY()+Shape.this.width-5, 10, 10));
			g.draw(new Rectangle2D.Double(Shape.this.p.getX()+Shape.this.length-5, Shape.this.p.getY()+Shape.this.width-5, 10, 10));
			//小正方形填充白色
			g.setColor(Color.WHITE);
			g.fill(new Rectangle2D.Double(Shape.this.p.getX()-4, Shape.this.p.getY()-4, 9, 9.0));
			g.fill(new Rectangle2D.Double(Shape.this.p.getX()+Shape.this.length-4, Shape.this.p.getY()-4, 9, 9));
			g.fill(new Rectangle2D.Double(Shape.this.p.getX()-4, Shape.this.p.getY()+Shape.this.width-4, 9, 9));
			g.fill(new Rectangle2D.Double(Shape.this.p.getX()+Shape.this.length-4, Shape.this.p.getY()+Shape.this.width-4, 9, 9));
		}

		@Override
		public int on() {
			// TODO Auto-generated method stub
			return 0;
		}
	}

	public class NormalState implements State{

		@Override
		public void draw(Shape s, Graphics2D g) {
			g.setColor(Color.BLACK);
			Shape.this.drawEntity(g);
		}

		@Override
		public int on() {
			// TODO Auto-generated method stub
			return 0;
		}

	}
	 */
}
