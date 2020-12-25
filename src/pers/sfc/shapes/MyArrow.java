package pers.sfc.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class MyArrow extends Shape{
	private double H = 10; // 箭头高度
	private double L = 5; // 底边的一半
	private MyPoint p1;//起点
	private MyPoint p2;//拐点
	private MyPoint p3;//拐点
	private MyPoint p4;//拐点
	private MyPoint p5;//拐点
	private MyPoint p6;//终点
	private Shape start;//起点图元
	private Shape end;//终点图元
	private Position startP;//起点方位
	private Position endP;//终点方位
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
		/*
		double x = p.getX();
		double y = p.getY();
		if(p1.getX() == p2.getX()&&p1.getX()-B<=x&&p1.getX()+B>=x&&
				Math.min(p1.getY(), p2.getY())<y&&y<Math.max(p1.getY(), p2.getY()))
			return true;
		else if(p1.getY() == p2.getY()&&p1.getY()-B<=y&&p1.getY()+B>=y&&
				Math.min(p1.getX(), p2.getX())<x&&x<Math.max(p1.getX(), p2.getX()))
			return true;
		else if(p5.getX() == p6.getX()&&p6.getX()-B<=x&&p6.getX()+B>=x&&
				Math.min(p5.getY(), p6.getY())<y&&y<Math.max(p5.getY(), p6.getY()))
			return true;
		else if(p5.getY() == p6.getY()&&p6.getY()-B<=y&&p6.getY()+B>=y&&
				Math.min(p5.getX(), p6.getX())<x&&x<Math.max(p5.getX(), p6.getX()))
			return true;
		else if(p3 == null&&p4 == null&&p2.getX() == p5.getX()&&p2.getX()-B<=x&&p2.getX()+B>=x&&
				Math.min(p2.getY(), p5.getY())<y&&y<Math.max(p2.getY(), p5.getY()))
			return true;
		else if(p3 == null&&p4 == null&&p2.getY() == p5.getY()&&p2.getY()-B<=y&&p2.getY()+B>=y&&
				Math.min(p2.getX(), p5.getX())<x&&x<Math.max(p2.getX(), p5.getX()))
			return true;
		else if(p3 != null&&p2.getX() == p3.getX()&&p2.getX()-B<=x&&p2.getX()+B>=x&&
				Math.min(p2.getY(), p3.getY())<y&&y<Math.max(p2.getY(), p3.getY()))
			return true;
		else if(p3 != null&&p2.getY() == p3.getY()&&p2.getY()-B<=y&&p2.getY()+B>=y&&
				Math.min(p2.getX(), p3.getX())<x&&x<Math.max(p2.getX(), p3.getX()))
			return true;
		else if(p3 != null&&p4 != null&&p3.getX() == p4.getX()&&p3.getX()-B<=x&&p3.getX()+B>=x&&
				Math.min(p3.getY(), p4.getY())<y&&y<Math.max(p3.getY(), p4.getY()))
			return true;
		else if(p3 != null&&p4 != null&&p3.getY() == p4.getY()&&p3.getY()-B<=y&&p3.getY()+B>=y&&
				Math.min(p3.getX(), p4.getX())<x&&x<Math.max(p3.getX(), p4.getX()))
			return true;
		else if(p4 != null&&p4.getX() == p5.getX()&&p4.getX()-B<=x&&p4.getX()+B>=x&&
				Math.min(p4.getY(), p5.getY())<y&&y<Math.max(p4.getY(), p5.getY()))
			return true;
		else if(p4 != null&&p4.getY() == p5.getY()&&p4.getY()-B<=y&&p4.getY()+B>=y&&
				Math.min(p4.getX(), p5.getX())<x&&x<Math.max(p4.getX(), p5.getX()))
			return true;
			*/
		return false;
	}
	public Position clickedPoint(Point2D pIn)
	{
		return Position.NONE;
	}
	public int onCorner(Point2D pIn) 
	{
		return 0;
	}
	public void connectPoint(Graphics2D g) {}
	public void Offset(double cx,double cy) {}
	@Override
	public void drawEntity(Graphics2D g) {
		if(color != null&&color.equals(Color.RED))
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		p1 = start.getPoint(startP);
		p6 = end.getPoint(endP);
		p2 = p3 = p4 = p5 = null;
		if(startP.equals(Position.NORTH)&&endP.equals(Position.NORTH))
		{
			p2 = new MyPoint(p1.getX(),Math.min(p1.getY(),p6.getY())-20);
			p5 = new MyPoint(p6.getX(),Math.min(p1.getY(),p6.getY())-20);
		}
		else if(startP.equals(Position.SOUTH)&&endP.equals(Position.SOUTH))
		{
			p2 = new MyPoint(p1.getX(),Math.max(p1.getY(),p6.getY())+20);
			p5 = new MyPoint(p6.getX(),Math.max(p1.getY(),p6.getY())+20);
		}
		else if(startP.equals(Position.WEST)&&endP.equals(Position.WEST))
		{
			p2 = new MyPoint(Math.min(p1.getX(),p6.getX())-20,p1.getY());
			p5 = new MyPoint(Math.min(p1.getX(),p6.getX())-20,p6.getY());
		}
		else if(startP.equals(Position.WEST)&&endP.equals(Position.WEST))
		{
			p2 = new MyPoint(Math.max(p1.getX(),p6.getX())+20,p1.getY());
			p5 = new MyPoint(Math.max(p1.getX(),p6.getX())+20,p6.getY());
		}
		else if(startP.equals(Position.SOUTH)&&endP.equals(Position.NORTH))
		{
			if(p1.getY()<=p6.getY())
			{
				p2 = new MyPoint(p1.getX(),(p1.getY()+p6.getY())*0.5);
				p5 = new MyPoint(p6.getX(),(p1.getY()+p6.getY())*0.5);
			}
			else if(p1.getY()>p6.getY())
			{
				p2 = new MyPoint(p1.getX(),p1.getY()+10);
				p5 = new MyPoint(p6.getX(),p6.getY()-20);
				p3 = new MyPoint((p1.getX()+p6.getX())*0.5,p2.getY());
				p4 = new MyPoint((p1.getX()+p6.getX())*0.5,p5.getY());
			}
		}
		else if(startP.equals(Position.NORTH)&&endP.equals(Position.SOUTH))
		{
			if(p1.getY()>=p6.getY())
			{
				p2 = new MyPoint(p1.getX(),(p1.getY()+p6.getY())*0.5);
				p5 = new MyPoint(p6.getX(),(p1.getY()+p6.getY())*0.5);
			}
			else if(p1.getY()<p6.getY())
			{
				p2 = new MyPoint(p1.getX(),p1.getY()-10);
				p5 = new MyPoint(p6.getX(),p6.getY()+20);
				p3 = new MyPoint((p1.getX()+p6.getX())*0.5,p2.getY());
				p4 = new MyPoint((p1.getX()+p6.getX())*0.5,p5.getY());
			}
		}
		else if(startP.equals(Position.WEST)&&endP.equals(Position.EAST))
		{
			if(p1.getX()>=p6.getX())
			{
				p2 = new MyPoint((p1.getX()+p6.getX())*0.5,p1.getY());
				p5 = new MyPoint((p1.getX()+p6.getX())*0.5,p6.getY());
			}
			else if(p1.getX()<p6.getX())
			{
				p2 = new MyPoint(p1.getX()-10,p1.getY());
				p5 = new MyPoint(p6.getX()+20,p6.getY());
				p3 = new MyPoint(p2.getX(),(p1.getY()+p6.getY())*0.5);
				p4 = new MyPoint(p5.getX(),(p1.getY()+p6.getY())*0.5);
			}
		}
		else if(startP.equals(Position.EAST)&&endP.equals(Position.WEST))
		{
			if(p1.getX()<=p6.getX())
			{
				p2 = new MyPoint((p1.getX()+p6.getX())*0.5,p1.getY());
				p5 = new MyPoint((p1.getX()+p6.getX())*0.5,p6.getY());
			}
			else if(p1.getX()>p6.getX())
			{
				p2 = new MyPoint(p1.getX()+10,p1.getY());
				p5 = new MyPoint(p6.getX()-20,p6.getY());
				p3 = new MyPoint(p2.getX(),(p1.getY()+p6.getY())*0.5);
				p4 = new MyPoint(p5.getX(),(p1.getY()+p6.getY())*0.5);
			}
		}
		else if(startP.equals(Position.NORTH)&&endP.equals(Position.WEST))
		{
			if(p1.getX()<p6.getX()&&p1.getY()<p6.getY()+10) 
			{
				p2 = new MyPoint(p1.getX(),p1.getY()-10);
				p5 = new MyPoint(p6.getX()-20,p6.getY());
				p3 = new MyPoint(p5.getX(),p2.getY());
				p4 = new MyPoint(p5.getX(),p2.getY());
			}
			else if(p1.getX()<p6.getX()&&p1.getY()>=p6.getY()+10)
			{
				p2 = new MyPoint(p1.getX(),p6.getY());
				p5 = new MyPoint(p1.getX(),p6.getY());
			}
			else if(p1.getX()>=p6.getX()/*&&p1.getY()-10<=p6.getY()*/)
			{
				p2 = new MyPoint(p1.getX(),p1.getY()-20);
				p5 = new MyPoint(p6.getX()-10,p6.getY());
				p3 = new MyPoint(p5.getX(),p2.getY());
				p4 = new MyPoint(p5.getX(),p2.getY());
			}
		}
		else if(startP.equals(Position.WEST)&&endP.equals(Position.NORTH))
		{
			if(p1.getX()>p6.getX()&&p1.getY()>p6.getY()+10) 
			{
				p2 = new MyPoint(p1.getX()-10,p1.getY());
				p5 = new MyPoint(p6.getX(),p6.getY()-20);
				p3 = new MyPoint(p2.getX(),p5.getY());
				p4 = new MyPoint(p5.getX(),p5.getY());
			}
			else if(p1.getX()>p6.getX()&&p1.getY()<=p6.getY()+10)
			{
				p2 = new MyPoint(p6.getX(),p1.getY());
				p5 = new MyPoint(p6.getX(),p1.getY());
			}
			else if(p1.getX()<=p6.getX()/*&&p1.getY()-10<=p6.getY()*/)
			{
				p2 = new MyPoint(p1.getX()-10,p1.getY());
				p5 = new MyPoint(p6.getX(),p6.getY()-20);
				p3 = new MyPoint(p2.getX(),p5.getY());
				p4 = new MyPoint(p2.getX(),p5.getY());
			}
		}
		else if(startP.equals(Position.WEST)&&endP.equals(Position.SOUTH))
		{
			if(p1.getX()>p6.getX()&&p1.getY()<p6.getY()+10) 
			{
				p2 = new MyPoint(p1.getX()-10,p1.getY());
				p5 = new MyPoint(p6.getX(),p6.getY()+20);
				p3 = new MyPoint(p2.getX(),p5.getY());
				p4 = new MyPoint(p2.getX(),p5.getY());
			}
			else if(p1.getX()>p6.getX()&&p1.getY()>=p6.getY()-10)
			{
				p2 = new MyPoint(p6.getX(),p1.getY());
				p5 = new MyPoint(p6.getX(),p1.getY());
			}
			else if(p1.getX()<=p6.getX()/*&&p1.getY()-10<=p6.getY()*/)
			{
				p2 = new MyPoint(p1.getX()-10,p1.getY());
				p5 = new MyPoint(p6.getX(),p6.getY()+20);
				p3 = new MyPoint(p2.getX(),p5.getY());
				p4 = new MyPoint(p2.getX(),p5.getY());
			}
		}
		else if(startP.equals(Position.SOUTH)&&endP.equals(Position.WEST))
		{
			if(p1.getX()<p6.getX()&&p1.getY()>p6.getY()-10) 
			{
				p2 = new MyPoint(p1.getX(),p1.getY()+10);
				p5 = new MyPoint(p6.getX()-20,p6.getY());
				p3 = new MyPoint(p5.getX(),p2.getY());
				p4 = new MyPoint(p5.getX(),p2.getY());
			}
			else if(p1.getX()<p6.getX()&&p1.getY()<=p6.getY()-10)
			{
				p2 = new MyPoint(p1.getX(),p6.getY());
				p5 = new MyPoint(p1.getX(),p6.getY());
			}
			else if(p1.getX()>=p6.getX()/*&&p1.getY()-10<=p6.getY()*/)
			{
				p2 = new MyPoint(p1.getX(),p1.getY()+10);
				p5 = new MyPoint(p6.getX()-20,p6.getY());
				p3 = new MyPoint(p5.getX(),p2.getY());
				p4 = new MyPoint(p5.getX(),p2.getY());
			}
		}
		else if(startP.equals(Position.SOUTH)&&endP.equals(Position.EAST))
		{
			if(p1.getX()>p6.getX()&&p1.getY()>p6.getY()-10) 
			{
				p2 = new MyPoint(p1.getX(),p1.getY()+10);
				p5 = new MyPoint(p6.getX()+20,p6.getY());
				p3 = new MyPoint(p5.getX(),p2.getY());
				p4 = new MyPoint(p5.getX(),p2.getY());
			}
			else if(p1.getX()>p6.getX()&&p1.getY()<=p6.getY()-10)
			{
				p2 = new MyPoint(p1.getX(),p6.getY());
				p5 = new MyPoint(p1.getX(),p6.getY());
			}
			else if(p1.getX()<=p6.getX()/*&&p1.getY()-10<=p6.getY()*/)
			{
				p2 = new MyPoint(p1.getX(),p1.getY()+10);
				p5 = new MyPoint(p6.getX()+20,p6.getY());
				p3 = new MyPoint(p5.getX(),p2.getY());
				p4 = new MyPoint(p5.getX(),p2.getY());
			}
		}
		else if(startP.equals(Position.EAST)&&endP.equals(Position.SOUTH))
		{
			if(p1.getX()<p6.getX()&&p1.getY()<p6.getY()+10) 
			{
				p2 = new MyPoint(p1.getX()+10,p1.getY());
				p5 = new MyPoint(p6.getX(),p6.getY()+20);
				p3 = new MyPoint(p2.getX(),p5.getY());
				p4 = new MyPoint(p2.getX(),p5.getY());
			}
			else if(p1.getX()<p6.getX()&&p1.getY()>=p6.getY()-10)
			{
				p2 = new MyPoint(p6.getX(),p1.getY());
				p5 = new MyPoint(p6.getX(),p1.getY());
			}
			else if(p1.getX()>=p6.getX()/*&&p1.getY()-10<=p6.getY()*/)
			{
				p2 = new MyPoint(p1.getX()+10,p1.getY());
				p5 = new MyPoint(p6.getX(),p6.getY()+20);
				p3 = new MyPoint(p2.getX(),p5.getY());
				p4 = new MyPoint(p2.getX(),p5.getY());
			}
		}
		else if(startP.equals(Position.EAST)&&endP.equals(Position.NORTH))
		{
			if(p1.getX()<p6.getX()&&p1.getY()>p6.getY()+10) 
			{
				p2 = new MyPoint(p1.getX()+10,p1.getY());
				p5 = new MyPoint(p6.getX(),p6.getY()-20);
				p3 = new MyPoint(p2.getX(),p5.getY());
				p4 = new MyPoint(p5.getX(),p5.getY());
			}
			else if(p1.getX()<p6.getX()&&p1.getY()<=p6.getY()+10)
			{
				p2 = new MyPoint(p6.getX(),p1.getY());
				p5 = new MyPoint(p6.getX(),p1.getY());
			}
			else if(p1.getX()>=p6.getX()/*&&p1.getY()-10<=p6.getY()*/)
			{
				p2 = new MyPoint(p1.getX()+10,p1.getY());
				p5 = new MyPoint(p6.getX(),p6.getY()-20);
				p3 = new MyPoint(p2.getX(),p5.getY());
				p4 = new MyPoint(p2.getX(),p5.getY());
			}
		}
		else if(startP.equals(Position.NORTH)&&endP.equals(Position.EAST))
		{
			if(p1.getX()>p6.getX()&&p1.getY()<p6.getY()+10) 
			{
				p2 = new MyPoint(p1.getX(),p1.getY()-10);
				p5 = new MyPoint(p6.getX()+20,p6.getY());
				p3 = new MyPoint(p5.getX(),p2.getY());
				p4 = new MyPoint(p5.getX(),p2.getY());
			}
			else if(p1.getX()>p6.getX()&&p1.getY()>=p6.getY()+10)
			{
				p2 = new MyPoint(p1.getX(),p6.getY());
				p5 = new MyPoint(p1.getX(),p6.getY());
			}
			else if(p1.getX()<=p6.getX()/*&&p1.getY()-10<=p6.getY()*/)
			{
				p2 = new MyPoint(p1.getX(),p1.getY()-10);
				p5 = new MyPoint(p6.getX()+20,p6.getY());
				p3 = new MyPoint(p5.getX(),p2.getY());
				p4 = new MyPoint(p5.getX(),p2.getY());
			}
		}
		g.setStroke(new BasicStroke(2));
		GeneralPath triangle = new GeneralPath();
		if(endP.equals(Position.NORTH))
		{
			if(p4 == null&&p3 == null)	
				g.draw(new Line2D.Double(p2.getX(),p2.getY(),p5.getX(),p5.getY()));
			else
			{
				g.draw(new Line2D.Double(p2.getX(),p2.getY(),p3.getX(),p3.getY()));
				g.draw(new Line2D.Double(p3.getX(),p3.getY(),p4.getX(),p4.getY()));
				g.draw(new Line2D.Double(p4.getX(),p4.getY(),p5.getX(),p5.getY()));
			}
			g.draw(new Line2D.Double(p1.getX(),p1.getY(),p2.getX(),p2.getY()));
			g.draw(new Line2D.Double(p5.getX(),p5.getY(),p6.getX(),p6.getY()-10));
			triangle.moveTo(p6.getX(),p6.getY());
			triangle.lineTo(p6.getX()-L,p6.getY()-H);
			triangle.lineTo(p6.getX()+L,p6.getY()-H);
		}
		else if(endP.equals(Position.WEST))
		{
			if(p4 == null&&p3 == null)	
				g.draw(new Line2D.Double(p2.getX(),p2.getY(),p5.getX(),p5.getY()));
			else
			{
				g.draw(new Line2D.Double(p2.getX(),p2.getY(),p3.getX(),p3.getY()));
				g.draw(new Line2D.Double(p3.getX(),p3.getY(),p4.getX(),p4.getY()));
				g.draw(new Line2D.Double(p4.getX(),p4.getY(),p5.getX(),p5.getY()));
			}
			g.draw(new Line2D.Double(p1.getX(),p1.getY(),p2.getX(),p2.getY()));
			g.draw(new Line2D.Double(p5.getX(),p5.getY(),p6.getX()-10,p6.getY()));
			triangle.moveTo(p6.getX(),p6.getY());
			triangle.lineTo(p6.getX()-H,p6.getY()+L);
			triangle.lineTo(p6.getX()-H,p6.getY()-L);
		}
		else if(endP.equals(Position.SOUTH))
		{
			if(p4 == null&&p3 == null)	
				g.draw(new Line2D.Double(p2.getX(),p2.getY(),p5.getX(),p5.getY()));
			else
			{
				g.draw(new Line2D.Double(p2.getX(),p2.getY(),p3.getX(),p3.getY()));
				g.draw(new Line2D.Double(p3.getX(),p3.getY(),p4.getX(),p4.getY()));
				g.draw(new Line2D.Double(p4.getX(),p4.getY(),p5.getX(),p5.getY()));
			}
			g.draw(new Line2D.Double(p1.getX(),p1.getY(),p2.getX(),p2.getY()));
			g.draw(new Line2D.Double(p5.getX(),p5.getY(),p6.getX(),p6.getY()+10));
			triangle.moveTo(p6.getX(),p6.getY());
			triangle.lineTo(p6.getX()-L,p6.getY()+H);
			triangle.lineTo(p6.getX()+L,p6.getY()+H);
		}
		else if(endP.equals(Position.EAST))
		{
			if(p4 == null&&p3 == null)	
				g.draw(new Line2D.Double(p2.getX(),p2.getY(),p5.getX(),p5.getY()));
			else
			{
				g.draw(new Line2D.Double(p2.getX(),p2.getY(),p3.getX(),p3.getY()));
				g.draw(new Line2D.Double(p3.getX(),p3.getY(),p4.getX(),p4.getY()));
				g.draw(new Line2D.Double(p4.getX(),p4.getY(),p5.getX(),p5.getY()));
			}
			g.draw(new Line2D.Double(p1.getX(),p1.getY(),p2.getX(),p2.getY()));
			g.draw(new Line2D.Double(p5.getX(),p5.getY(),p6.getX()+10,p6.getY()));
			triangle.moveTo(p6.getX(),p6.getY());
			triangle.lineTo(p6.getX()+H,p6.getY()+L);
			triangle.lineTo(p6.getX()+H,p6.getY()-L);
		}
		triangle.closePath();
		//实心箭头
		g.fill(triangle);
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
		return this.state.contains(this, pIn);
	}
	@Override
	public boolean showDialog(Component parent) {
		// TODO Auto-generated method stub
		return false;
	}
	//代码生成
	@Override
	public String codeGen(int num) {
		return null;
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