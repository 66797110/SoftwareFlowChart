package pers.sfc.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

abstract public class Shape {
	private static final double A = 5;//���������
	protected MyPoint p; //���ϽǶ�λ
	protected double length; //��
	protected double width; //��
	//����Ƿ���ͼ����
	abstract public boolean contains(Point2D p);
	//��ͼ
	abstract public void draw(Graphics2D g);
	//�����ڲ���ɫ
	//abstract public void setColorIn();
	//���ñ߿���ɫ
	//abstract public void setColorOn();
	//���л�
	abstract public String writeObject();
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
        g.fill(new Rectangle2D.Double(this.p.getX()-4, this.p.getY()-4, 9, 9.0));
        g.fill(new Rectangle2D.Double(this.p.getX()+this.length-4, this.p.getY()-4, 9, 9));
        g.fill(new Rectangle2D.Double(this.p.getX()-4, this.p.getY()+this.width-4, 9, 9));
        g.fill(new Rectangle2D.Double(this.p.getX()+this.length-4, this.p.getY()+this.width-4, 9, 9));
	}
	//����Ƿ�����Ӿ�����
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
}
