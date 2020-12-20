package pers.sfc.shapes;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class MyRectangle extends Shape{
	public MyRectangle(MyPoint p,double length,double width)
	{
		super.p = p;
		super.length = length;
		super.width = width;
		super.func = Func.OPERATE;
	}

	public MyRectangle(double x,double y,double length,double width)
	{
		this(new MyPoint(x,y),length,width);
	}

	public MyRectangle()
	{
		this(new MyPoint(),125,75);
	}

	//鼠标是否在图形内
	@Override
	public boolean containsN(Point2D p)
	{
		double x = p.getX();
		double y = p.getY();
		if(super.p.getX()<x && x<super.p.getX()+super.length && 
				super.p.getY()<y && y<super.p.getY()+super.width)
			return true;
		return false;
	}
	//画矩形
	@Override
	public void drawEntity(Graphics2D g)
	{
		if(color != null&&color.equals(Color.RED))
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(B));
		g.draw(new Rectangle2D.Double(super.p.getX(), super.p.getY(), super.length, super.width));
		drawCode(g);
	}
	
	@Override
	public void draw(Graphics2D g) {
		state.draw(this, g);
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
		private JTextArea codeInput;
		private JButton okButton;
		private JButton cancelButton;
		private JDialog dialog;
		private boolean ok;

		public MyDialog()
		{
			setLayout(new BorderLayout());
			//面板设置
			var panel = new JPanel();
			panel.setLayout(new GridLayout(2, 1));
			panel.add(new JLabel("代码"));
			codeInput = new JTextArea(3,20);
			codeInput.setLineWrap(true);
			codeInput.setEditable(true);
			codeInput.setText(code);
			JScrollPane pane=new JScrollPane(codeInput, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			//panel.add(codeInput);
			panel.add(pane);
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

			dialog.setLocation((int)MyRectangle.super.p.getX(),(int)MyRectangle.super.p.getY());
			dialog.setTitle(title);
			dialog.setVisible(true);
			return ok;
		}
	}
}
