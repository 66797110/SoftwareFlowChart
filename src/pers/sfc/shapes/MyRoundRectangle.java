package pers.sfc.shapes;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

public class MyRoundRectangle extends Shape{
	MyArrow arrow;
	public MyRoundRectangle(MyPoint p,double length,double width)
	{
		super.p = p;
		super.length = length;
		super.width = width;
	}

	public MyRoundRectangle(double x,double y,double length,double width)
	{
		this(new MyPoint(x,y),length,width);
	}

	public MyRoundRectangle()
	{
		this(new MyPoint(),125,75);
	}

	@Override
	public boolean containsN(Point2D p) {
		double x = p.getX();
		double y = p.getY();
		if(super.p.getX()<x && x<super.p.getX()+super.length && 
				super.p.getY()<y && y<super.p.getY()+super.width)
			return true;
		return false;
	}

	@Override
	public boolean contains(Point2D pIn)
	{
		return this.state.contains(this, pIn);
	}
	//��Բ�Ǿ���
	@Override
	public void drawEntity(Graphics2D g) {
		g.setStroke(new BasicStroke(B));
		g.draw(new RoundRectangle2D.Double(super.p.getX(), super.p.getY(), super.length, super.width,40.0,40.0));
		drawCode(g);
	}

	@Override
	public String writeObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Graphics2D g) {
		state.draw(this, g);
	}
	//��ô���
	public String getCode()
	{
		return code;
	}
	//��������
	public boolean codeRun()
	{
		return execute.codeExecution(this);
	}
	//��ʾ����
	public boolean showDialog(Component parent)
	{
		var dialog = new MyDialog();
		boolean ok;
		if(ok = dialog.showDialog(parent, "����ѡ��"))
		{
			super.code = dialog.getFunction();
		}
		if(super.code.equals("start"))
			super.func = Func.START;
		else
			super.func = Func.END;
		return ok;
	}

	//˽�д�����
	private class MyDialog extends JPanel
	{
		private static final long serialVersionUID = 2488127639285339811L;
		private JRadioButton start;
		private JRadioButton end;
		private ButtonGroup bg;
		private JButton okButton;
		private JButton cancelButton;
		private JDialog dialog;
		private boolean ok;

		public MyDialog()
		{
			setLayout(new BorderLayout());
			//�������
			var panel = new JPanel();
			panel.setLayout(new GridLayout(1, 3));
			panel.add(new JLabel("���ܣ�"));
			bg = new ButtonGroup();
			bg.add(start = new JRadioButton("��ʼ"));
			bg.add(end = new JRadioButton("����"));
			panel.add(start);
			panel.add(end);
			add(panel, BorderLayout.CENTER);
			//����ȷ��ȡ����ť
			okButton = new JButton("Ok");
			okButton.addActionListener(event -> {
				ok = true;
				dialog.setVisible(false);
			});
			cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(event -> dialog.setVisible(false));
			//��ť���
			var buttonPanel = new JPanel();
			buttonPanel.add(okButton);
			buttonPanel.add(cancelButton);
			add(buttonPanel, BorderLayout.SOUTH);
		}
		//���ѡ��
		public String getFunction()
		{
			if(start.isSelected())
				return "start";
			else if(end.isSelected())
				return "end";
			else
				return null;
		}
		//��ʾ����
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

			dialog.setLocation((int)MyRoundRectangle.super.p.getX(),(int)MyRoundRectangle.super.p.getY());
			dialog.setTitle(title);
			dialog.setVisible(true);
			return ok;
		}
	}
}
