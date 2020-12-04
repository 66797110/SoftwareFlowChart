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

import pers.sfc.shapes.MyCircle;
import pers.sfc.shapes.MyParallelogram;
import pers.sfc.shapes.MyRectangle;
import pers.sfc.shapes.MyRoundRectangle;
import pers.sfc.shapes.Shape;
import pers.sfc.windows.MyDocument;

public class MyComponent extends JComponent{
	//��������
		private static final int DEFAULT_WIDTH = 700;
		private static final int DEFAULT_HEIGHT = 700;

		//ͼ��
		private Shape current;
		private double x,y;

		//ͼ���ļ�
		private MyDocument myDocument = new MyDocument();

		//��ʼ������
		public MyComponent()  
		{
			current = null;
			current = new MyParallelogram();
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
			current.draw((Graphics2D)g);
			current.drawCirRect((Graphics2D)g);
		}
		//������
		private class MouseHandler extends MouseAdapter
		{
			public void mouseClicked(MouseEvent event)
			{
				repaint();
			}
			/*
			public void mousePressed(MouseEvent event)
			{
				if((current = myDocument.findIn(event.getPoint())) != null || (current = myDocument.findOn(event.getPoint())) != null)
					myDocument.move(current);
			}

			public void mouseClicked(MouseEvent event)
			{
				if((current = myDocument.findIn(event.getPoint())) != null || myDocument.findOn(event.getPoint()) != null)
					myDocument.move(current);
				if(current !=null && event.getClickCount() >= 2)
				{
					myDocument.remove(current);
					repaint();
				}
			}
			*/
		}
		//������
		private class MouseMotionHandler implements MouseMotionListener
		{
			private int state = -1,p;
			/*
			public void mouseMoved(MouseEvent event)
			{
				Shape s;
				if(myDocument.findIn(event.getPoint()) != null)
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
			}*/
			public void mouseDragged(MouseEvent event) {
				double newX = event.getX();
				double newY = event.getY();
				//if(state == 0)
				
					current.Offset(newX-x,newY-y);
					
					
				//else if(state == 1)
					//current.onSize(newX,newY,x,y,p);
				x = newX;
				y = newY;
				repaint();
			}
			@Override
			public void mouseMoved(MouseEvent event) {
				x = event.getX();
				y = event.getY();
			}
		}
}
