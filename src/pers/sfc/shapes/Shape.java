package pers.sfc.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

abstract public class Shape {
	private static final double A = 5;//���������
	protected static final float B = 2;//���ʿ��
	protected static final float C = 2;//����ݴ�
	protected MyPoint p; //���ϽǶ�λ
	protected double length; //��
	protected double width; //��
	protected State state = new NormalState();
	protected State lstate;
	//����Ƿ���ͼ����
	abstract public boolean contains(Point2D p);
	//��ͼ
	abstract public void draw(Graphics2D g);
	//��ͼ�ڲ�
	abstract public void drawEntity(Graphics2D g);
	//�����ڲ���ɫ
	//abstract public void setColorIn();
	//���ñ߿���ɫ
	//abstract public void setColorOn();
	//���л�
	abstract public String writeObject();
	//�����ӵ�
	public void connectPoint(Graphics2D g)
	{
		//���ı�СԲ��
				g.setStroke(new BasicStroke(1));
				g.draw(new Ellipse2D.Double(this.p.getX()+this.length*0.5-5, this.p.getY()-5, 10, 10));
				g.draw(new Ellipse2D.Double(this.p.getX()-5, this.p.getY()+this.width*0.5-5, 10, 10));
				g.draw(new Ellipse2D.Double(this.p.getX()+this.length*0.5-5, this.p.getY()+this.width-5, 10, 10));
				g.draw(new Ellipse2D.Double(this.p.getX()+this.length-5, this.p.getY()+this.width*0.5-5, 10, 10));
				//СԲ������ɫ
				g.setColor(Color.WHITE);
				g.fill(new Ellipse2D.Double(this.p.getX()+this.length*0.5-4.5, this.p.getY()-4.5, 9, 9));
				g.fill(new Ellipse2D.Double(this.p.getX()-4.5, this.p.getY()+this.width*0.5-4.5, 9, 9));
				g.fill(new Ellipse2D.Double(this.p.getX()+this.length*0.5-4.5, this.p.getY()+this.width-4.5, 9, 9));
				g.fill(new Ellipse2D.Double(this.p.getX()+this.length-4.5, this.p.getY()+this.width*0.5-4.5, 9, 9));
	}
	//��������ӵ�
	public int getPoint(Point2D pIn)
	{
		if(!this.state.getClass().equals("SelectState"))
			return Position.NONE.ordinal();
		double x=pIn.getX();
		double y=pIn.getY();
		if(Math.sqrt(Math.pow(this.p.getX()+this.length*0.5-x, 2)+Math.pow(this.p.getY()-y, 2))<=5)//�Ϸ�ԲȦ
			return Position.NORTH.ordinal();
		else if(Math.sqrt(Math.pow(this.p.getX()-x, 2)+Math.pow(this.p.getY()+this.width*0.5-y, 2))<=5)//��ԲȦ
			return Position.WEST.ordinal();
		else if(Math.sqrt(Math.pow(this.p.getX()+this.length*0.5-x, 2)+Math.pow(this.p.getY()+this.width-y, 2))<=5)//�·�ԲȦ
			return Position.SOUTH.ordinal();
		else if(Math.sqrt(Math.pow(this.p.getX()+this.length-x, 2)+Math.pow(this.p.getY()+this.width*0.5-y, 2))<=5)//�ҷ�ԲȦ
			return Position.EAST.ordinal();
		else
			return Position.NONE.ordinal();
	}
	//��Ӿ���
	public void drawCirRect(Graphics2D g)
	{
		//�����߱߿�
		g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
		g.draw(new Rectangle2D.Double(this.p.getX(), this.p.getY(), this.length, this.width));
		//���Ľ�С������
		g.setStroke(new BasicStroke(1));
		g.draw(new Rectangle2D.Double(this.p.getX()-5, this.p.getY()-5, 10, 10));
		g.draw(new Rectangle2D.Double(this.p.getX()+this.length-5, this.p.getY()-5, 10, 10));
		g.draw(new Rectangle2D.Double(this.p.getX()-5, this.p.getY()+this.width-5, 10, 10));
		g.draw(new Rectangle2D.Double(this.p.getX()+this.length-5, this.p.getY()+this.width-5, 10, 10));
		//С����������ɫ
		g.setColor(Color.WHITE);
		g.fill(new Rectangle2D.Double(this.p.getX()-4, this.p.getY()-4, 9, 9));
		g.fill(new Rectangle2D.Double(this.p.getX()+this.length-4, this.p.getY()-4, 9, 9));
		g.fill(new Rectangle2D.Double(this.p.getX()-4, this.p.getY()+this.width-4, 9, 9));
		g.fill(new Rectangle2D.Double(this.p.getX()+this.length-4, this.p.getY()+this.width-4, 9, 9));
	}
	//����Ƿ�����Ӿ��ε��Ľ���
	public int on(Point2D pIn)
	{
		return this.state.on(this,pIn);
	}
	//����Ƿ�����Ӿ��ε��Ľ���
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
	//�ƶ�
	public void Offset(double cx,double cy)
	{
		this.p.Offset(cx, cy);
	}
	//�ı��С
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
	//�������������
	protected static final double triArea(MyPoint a,MyPoint b,MyPoint c)
	{
		return Math.abs((a.getX()*b.getY()+b.getX()*c.getY()+c.getX()*a.getY()-b.getX()*a.getY()-c.getX()*b.getY()-a.getX()*c.getY())/2.0); 
	}
	//����״̬
	public void setState(State state)
	{
		this.lstate = this.state;
		this.state = state;
	}
	//�ص�״̬
	public void backState()
	{
		this.state = this.lstate;
	}
	//�ƶ�״̬
	/*
	public class MoveState implements State{
		@Override
		public void draw(Shape s,Graphics2D g) {
			g.setColor(Color.BLACK);
			//s.drawEntity(g);
			Shape.this.drawEntity(g);
			g.setColor(Color.BLACK);
			//s.drawCirRect(g);
			//�����߱߿�
			g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
			g.draw(new Rectangle2D.Double(Shape.this.p.getX(), Shape.this.p.getY(), Shape.this.length, Shape.this.width));
			//���Ľ�С������
			g.setStroke(new BasicStroke(1));
			g.draw(new Rectangle2D.Double(Shape.this.p.getX()-5, Shape.this.p.getY()-5, 10, 10));
			g.draw(new Rectangle2D.Double(Shape.this.p.getX()+Shape.this.length-5, Shape.this.p.getY()-5, 10, 10));
			g.draw(new Rectangle2D.Double(Shape.this.p.getX()-5, Shape.this.p.getY()+Shape.this.width-5, 10, 10));
			g.draw(new Rectangle2D.Double(Shape.this.p.getX()+Shape.this.length-5, Shape.this.p.getY()+Shape.this.width-5, 10, 10));
			//С����������ɫ
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
