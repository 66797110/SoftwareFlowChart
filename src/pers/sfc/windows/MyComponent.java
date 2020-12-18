package pers.sfc.windows;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

import pers.sfc.shapes.Func;
import pers.sfc.shapes.MyArrow;
import pers.sfc.shapes.NormalState;
import pers.sfc.shapes.Position;
import pers.sfc.shapes.SelectState;
import pers.sfc.shapes.SelectedState;
import pers.sfc.shapes.Shape;

public class MyComponent extends JComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 317505908118897438L;
	//��������
	private static final int DEFAULT_WIDTH = 700;
	private static final int DEFAULT_HEIGHT = 700;

	//ͼ��
	private Shape current;
	private Shape last;
	private Shape startShape;
	private Shape endShape;
	private double x,y;
	//private MyPoint start;
	//private MyPoint end;
	private Position startP;//�������λ��
	private Position endP;//�յ�����λ��

	//ͼ���ļ�
	private MyDocument myDocument = new MyDocument();

	//��ʼ������
	public MyComponent()  
	{
		this.current = null;
		this.last = null;
		startShape = null;
		//this.start = null;
		//this.end = null;
		this.startP = null;
		this.endP = null;
		addMouseListener(new MouseHandler());
		addMouseMotionListener(new MouseMotionHandler());
	}
	//���û�������
	public Dimension getPreferredSize()
	{
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	//�Ի������л���
	public void paintComponent(Graphics g)  
	{
		g.setColor(Color.WHITE);
		g.fillRect(0,0,this.getWidth(),this.getHeight());  //��䱳��
		g.setColor(Color.BLACK);
		for(Shape s ;(s = myDocument.getNext()) != null;)
			s.draw((Graphics2D)g);
	}
	//��������
	public void update(MyDocument d)
	{
		this.myDocument = d;
		repaint();
	}
	//��ÿ�ʼͼ��
	public Shape getStart()
	{
		for(Shape s ;(s = myDocument.getNext()) != null;)
			if(s.getFunc().equals(Func.START))
			{
				myDocument.reset();
				return s;
			}
		return null;
	}
	//��ý���ͼ��
	public Shape getEnd()
	{
		for(Shape s ;(s = myDocument.getNext()) != null;)
			if(s.getFunc().equals(Func.END))
			{
				myDocument.reset();
				return s;
			}
		return null;
	}
	//ɾ��ͼ��
	public void delete()
	{
		Shape s = myDocument.getFirst();
		if(s != null && s.getState().getClass().getName().equals("pers.sfc.shapes.SelectState"))
		{
			s.deleteArrow(myDocument);
			myDocument.remove(s);
		}
		repaint();
	}
	//������
	private class MouseHandler extends MouseAdapter
	{
		public void mousePressed(MouseEvent event)
		{
			resetColor();
			last = current;
			if((current = myDocument.findIn(event.getPoint())) != null || (current = myDocument.findOn(event.getPoint())) != null) 
			{
				myDocument.move(current);
				if(last != null && !last.equals(current))
					last.backState();
				current.setState(new SelectState());
				//if(current)
			}
			else if(startShape != null)
			{
				startShape.strongBackState();
				//start = end = null;
				startShape = endShape = null;
				startP = endP = null;
			}
			else if(last != null)
				last.backState();
			repaint();
		}
		public void mouseClicked(MouseEvent event)
		{
			resetColor();
			if(current != null&&!current.clickedPoint(event.getPoint()).equals(Position.NONE))
			{
				if(startP == null)
				{
					startP = current.clickedPoint(event.getPoint());
					startShape = current;//�������ͼ�ζ���
					//start = current.getPoint(startP);
					current.setState(new SelectedState(startP));
				}
				else 
				{
					if(!startShape.equals(current))
					{
						endP = current.clickedPoint(event.getPoint());
						if(endP!=null) 
						{
							endShape = current;//�յ�ͼ��
							//end = current.getPoint(endP);
							var Arrow = new MyArrow(startShape,endShape,startP,endP);
							myDocument.update(Arrow);
							startShape.strongBackState();
							endShape.strongBackState();
							//start = end = null;
							startShape = endShape = null;
							startP = endP = null;
						}
					}
				}
			}

			if(((current = myDocument.findIn(event.getPoint())) != null || (current = myDocument.findOn(event.getPoint())) != null)&&
					(event.getClickCount() >= 2))
				current.showDialog(MyComponent.this);
			//current.setState(new CodeState())
			/*
			else if((myDocument.findIn(event.getPoint())) == null && (myDocument.findOn(event.getPoint())) == null)
			{
				if(startShape!=null)
					startShape.strongBackState();
				start = end = null;
				startShape = null;
			}
			 */
			repaint();
		}
	}
	//������
	private class MouseMotionHandler implements MouseMotionListener
	{
		private int state = -1,p;
		public void mouseMoved(MouseEvent event)
		{
			resetColor();
			Shape s;
			if(current!=null&&!current.clickedPoint(event.getPoint()).equals(Position.NONE))
			{
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				state = -1;
			}
			else if(myDocument.findIn(event.getPoint()) != null)
			{
				setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
				state = 0;
				x = event.getX();
				y = event.getY();
			}
			else if((s = myDocument.findOn(event.getPoint())) != null)
			{
				p = s.on(event.getPoint());
				setCursor(Cursor.getPredefinedCursor(p));
				state = 1;
				x = event.getX();
				y = event.getY();
			}
			else 
			{
				setCursor(Cursor.getDefaultCursor());
				state = -1;
			}
		}

		public void mouseDragged(MouseEvent event) 
		{
			resetColor();
			double newX = event.getX();
			double newY = event.getY();
			if(state == 0)
				current.Offset(newX-x,newY-y);
			else if(state == 1)
				current.onSize(newX,newY,x,y,p);
			x = newX;
			y = newY;
			repaint();
		}
	}
	//��ͣ
	public void sleep()
	{
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//��������ͼ�ε���ɫ
	public void resetColor()
	{
		for(Shape s ;(s = myDocument.getNext()) != null;)
			s.setColorOn(Color.BLACK);
	}
}
