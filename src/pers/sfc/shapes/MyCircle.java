package pers.sfc.shapes;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MyCircle extends Shape{
	public MyCircle(MyPoint p,double length,double width)
	{
		super.p = p;
		super.length = length;
		super.width = width;
		super.func = Func.CONNECT;
	}

	public MyCircle(double x,double y,double length,double width)
	{
		this(new MyPoint(x,y),length,width);
	}

	public MyCircle()
	{
		this(new MyPoint(),40,40);
	}
	@Override
	public boolean containsN(Point2D p) {
		if(Math.sqrt(Math.pow(width*0.5+super.p.getX()-p.getX(), 2)+Math.pow(width*0.5+super.p.getY()-p.getY(), 2))<=width*0.5)
			return true;
		return false;
	}

	@Override
	public void drawEntity(Graphics2D g) {
		if(color != null&&color.equals(Color.RED))
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(B));
		g.draw(new Ellipse2D.Double(super.p.getX(), super.p.getY(), super.length, super.width));
		drawCode(g);
	}

	@Override
	public void draw(Graphics2D g) {
		state.draw(this, g);
	}

	@Override
	public void onSize(double newX,double newY,double oldX,double oldY,int state)
	{
		/*
		double disWL;
		if(newY-oldY<newX-oldX)
			disWL=newX-oldX;
		else
			disWL=newY-oldY;
		switch (state)
		{
		case Cursor.SE_RESIZE_CURSOR:
			this.length += disWL;
			this.width += disWL;
			break;
		case Cursor.NW_RESIZE_CURSOR:
			this.p.Offset(disWL, disWL);
			this.length -= disWL;
			this.width -= disWL;
			break;
		case Cursor.NE_RESIZE_CURSOR:
			this.p.Offset(0, disWL);
			this.length += disWL;
			this.width -= disWL;
			break;
		case Cursor.SW_RESIZE_CURSOR:
			this.p.Offset(disWL, 0);
			this.length -= disWL;
			this.width += disWL;
			break;
		}
		*/
	}

	@Override
	public boolean contains(Point2D pIn) {
		return this.state.contains(this, pIn);
	}
	//代码运行
	public boolean codeRun()
	{
		return execute.codeExecution(this);
	}
	//代码生成
	@Override
	public String codeGen(int num) {
		return generate.codeGenerate(this,num);
	}
	//显示窗口
	public boolean showDialog(Component parent)
	{
		var dialog = new MyDialog();
		boolean ok;
		if(ok = dialog.showDialog(parent, "代码输入"))
		{
			super.code = dialog.getFunction();
		}
		return ok;
	}

	//私有窗口类
	private class MyDialog extends JPanel
	{
		private static final long serialVersionUID = 2488127639285339811L;
		private JTextField codeInput;
		private JButton okButton;
		private JButton cancelButton;
		private JDialog dialog;
		private boolean ok;

		public MyDialog()
		{
			setLayout(new BorderLayout());
			//面板设置
			var panel = new JPanel();
			panel.setLayout(new GridLayout(1, 2));
			panel.add(new JLabel("名称"));
			panel.add(codeInput = new JTextField(code));
			add(panel, BorderLayout.CENTER);
			//设置确定取消按钮
			okButton = new JButton("Ok");
			okButton.addActionListener(event -> {
				ok = true;
				dialog.setVisible(false);
			});
			cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(event -> dialog.setVisible(false));
			//按钮面板
			var buttonPanel = new JPanel();
			buttonPanel.add(okButton);
			buttonPanel.add(cancelButton);
			add(buttonPanel, BorderLayout.SOUTH);
		}
		//获得选项
		public String getFunction()
		{
			return codeInput.getText();
		}
		//显示窗口
		public boolean showDialog(Component parent,String title)
		{
			ok = false;

			Frame owner = null;
			if (parent instanceof Frame)
				owner = (Frame) parent;
			else
				owner = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, parent);

			if (dialog == null || dialog.getOwner() != owner)
			{
				dialog = new JDialog(owner, true);
				dialog.add(this);
				dialog.getRootPane().setDefaultButton(okButton);
				dialog.pack();
			}

			dialog.setLocation((int)MyCircle.super.p.getX(),(int)MyCircle.super.p.getY());
			dialog.setTitle(title);
			dialog.setVisible(true);
			return ok;
		}
	}
}
