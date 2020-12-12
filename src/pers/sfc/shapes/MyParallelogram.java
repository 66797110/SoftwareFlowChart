package pers.sfc.shapes;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class MyParallelogram extends Shape{
	private Component parent;
	private boolean InOut;//true:IN;  false:OUT;
	public MyParallelogram(MyPoint p,double length,double width)
	{
		super.p = p;
		super.length = length;
		super.width = width;
		InOut = true;
	}

	public MyParallelogram(double x,double y,double length,double width)
	{
		this(new MyPoint(x,y),length,width);
	}

	public MyParallelogram()
	{
		this(new MyPoint(),125,75);
	}

	@Override
	public boolean containsN(Point2D p) {//�����
		var testp = new MyPoint(p.getX(),p.getY());
		var p1 = new MyPoint(super.p.getX()+super.length*0.2,super.p.getY());
		var p2 = new MyPoint(super.p.getX()+super.length,super.p.getY());
		var p3 = new MyPoint(super.p.getX(),super.p.getY()+super.width);
		var p4 = new MyPoint(super.p.getX()+super.length*0.8,super.p.getY()+super.width);
		double area = triArea(testp,p1,p2)+triArea(testp,p2,p3)+triArea(testp,p3,p4)+triArea(testp,p1,p4);
		if(area<super.length*0.8*super.width+C)
			return true;
		return false;
	}
	//��ƽ���ı���
	@Override
	public void drawEntity(Graphics2D g) {
		double x = super.p.getX();
		double y = super.p.getY();
		g.setStroke(new BasicStroke(B));
		g.draw(new Line2D.Double(x,y+super.width,x+super.length*0.2,y));
		g.draw(new Line2D.Double(x,y+super.width,x+super.length*0.8,y+super.width));
		g.draw(new Line2D.Double(x+super.length,y,x+super.length*0.2,y));
		g.draw(new Line2D.Double(x+super.length,y,x+super.length*0.8,y+super.width));
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

	@Override
	public void connectPoint(Graphics2D g) {
		//���ı�СԲ��
		g.setStroke(new BasicStroke(1));
		if(!nJudge)g.draw(new Ellipse2D.Double(super.p.getX()+super.length*0.5-5, super.p.getY()-5, 10, 10));
		if(!wJudge)g.draw(new Ellipse2D.Double(super.p.getX()+super.length*0.1-5, super.p.getY()+super.width*0.5-5, 10, 10));
		if(!sJudge)g.draw(new Ellipse2D.Double(super.p.getX()+super.length*0.5-5, super.p.getY()+super.width-5, 10, 10));
		if(!eJudge)g.draw(new Ellipse2D.Double(super.p.getX()+super.length*0.9-5, super.p.getY()+super.width*0.5-5, 10, 10));
		//СԲ������ɫ
		g.setColor(Color.WHITE);
		if(!nJudge)g.fill(new Ellipse2D.Double(super.p.getX()+super.length*0.5-4.5, super.p.getY()-4.5, 9, 9));
		if(!wJudge)g.fill(new Ellipse2D.Double(super.p.getX()+super.length*0.1-4.5, super.p.getY()+super.width*0.5-4.5, 9, 9));
		if(!sJudge)g.fill(new Ellipse2D.Double(super.p.getX()+super.length*0.5-4.5, super.p.getY()+super.width-4.5, 9, 9));
		if(!eJudge)g.fill(new Ellipse2D.Double(super.p.getX()+super.length*0.9-4.5, super.p.getY()+super.width*0.5-4.5, 9, 9));
	}
	//��������
	public MyPoint getPoint(Position p)
	{
		if(Position.NORTH.equals(p))
			return new MyPoint(this.p.getX()+this.length*0.5,this.p.getY());
		else if(Position.WEST.equals(p))
			return new MyPoint(this.p.getX()+this.length*0.1,this.p.getY()+this.width*0.5);
		else if(Position.SOUTH.equals(p))
			return new MyPoint(this.p.getX()+this.length*0.5,this.p.getY()+this.width);
		else if(Position.EAST.equals(p))
			return new MyPoint(this.p.getX()+this.length*0.9,this.p.getY()+this.width*0.5);
		else
			return null;
	}
	//��������ӵ�
	public Position clickedPoint(Point2D pIn)
	{
		if(!this.state.getClass().getName().equals("pers.sfc.shapes.SelectState"))
			return Position.NONE;
		double x=pIn.getX();
		double y=pIn.getY();
		if(Math.sqrt(Math.pow(this.p.getX()+this.length*0.5-x, 2)+Math.pow(this.p.getY()-y, 2))<=6)//�Ϸ�ԲȦ
			if(!eJudge)return Position.NORTH;else return Position.NONE;
		else if(Math.sqrt(Math.pow(this.p.getX()+this.length*0.1-x, 2)+Math.pow(this.p.getY()+this.width*0.5-y, 2))<=6)//��ԲȦ
			if(!wJudge)return Position.WEST;else return Position.NONE;
		else if(Math.sqrt(Math.pow(this.p.getX()+this.length*0.5-x, 2)+Math.pow(this.p.getY()+this.width-y, 2))<=6)//�·�ԲȦ
			if(!sJudge)return Position.SOUTH;else return Position.NONE;
		else if(Math.sqrt(Math.pow(this.p.getX()+this.length*0.9-x, 2)+Math.pow(this.p.getY()+this.width*0.5-y, 2))<=6)//�ҷ�ԲȦ
			if(!eJudge)return Position.EAST;else return Position.NONE;
		else
			return Position.NONE;
	}
	//��ʾ��������ӵ�Ϳ��
	public void fillPoint(Position p,Graphics2D g)
	{
		if(Position.NORTH.equals(p))
			g.fill(new Ellipse2D.Double(this.p.getX()+this.length*0.5-4.5, this.p.getY()-4.5, 9, 9));
		else if(Position.WEST.equals(p))
			g.fill(new Ellipse2D.Double(this.p.getX()+this.length*0.1-4.5, this.p.getY()+this.width*0.5-4.5, 9, 9));
		else if(Position.SOUTH.equals(p))
			g.fill(new Ellipse2D.Double(this.p.getX()+this.length*0.5-4.5, this.p.getY()+this.width-4.5, 9, 9));
		else if(Position.EAST.equals(p))
			g.fill(new Ellipse2D.Double(this.p.getX()+this.length*0.9-4.5, this.p.getY()+this.width*0.5-4.5, 9, 9));
	}
	//��������
	public MyPoint returnPoint(Position p)
	{
		if(Position.NORTH.equals(p))
			return new MyPoint(this.p.getX()+this.length*0.5,this.p.getY());
		else if(Position.WEST.equals(p))
			return new MyPoint(this.p.getX()+this.length*0.1,this.p.getY()+this.width*0.5);
		else if(Position.SOUTH.equals(p))
			return new MyPoint(this.p.getX()+this.length*0.5,this.p.getY()+this.width);
		else if(Position.EAST.equals(p))
			return new MyPoint(this.p.getX()+this.length*0.9,this.p.getY()+this.width*0.5);
		return this.p;//�����ֵ
	}
	//������
	protected void drawCode(Graphics2D g)
	{
		if(this.code != null) {
			Font font=new Font("����",Font.PLAIN,20);  
			g.setFont(font);  
			// �����
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			// �������ֳ��ȣ�������е�x������
			FontMetrics fm = g.getFontMetrics(font);
			int textHeight = fm.getHeight();
			int textWidth = fm.stringWidth(this.code);
			int SWidth = (int)(this.length - textWidth)/2;
			int SHeight = (int)(this.width - textHeight)/2;
			// ��ʾ���������ͼƬ�ϵ�λ��(x,y) .��һ���������õ����ݡ�
			if(SWidth-super.width*0.1>0&&SHeight>0)
				g.drawString(this.code,(int)this.p.getX()+SWidth,(int)this.p.getY()+SHeight+textHeight-3);  
		}
	}
	@Override
	public boolean contains(Point2D pIn) {
		return this.state.contains(this, pIn);
	}
	//��ô���
	public String getCode()
	{
		return code;
	}
	//��������
	public boolean codeRun()
	{
		return execute.codeExecution(this,this.parent,this.InOut);
	}
	//��ʾ����
	public boolean showDialog(Component parent)
	{
		this.parent = parent;
		var dialog = new MyDialog();
		boolean ok;
		if(ok = dialog.showDialog(parent, "��������"))
		{
			super.code = dialog.getFunction();
		}
		return ok;
	}

	//˽�д�����
	private class MyDialog extends JPanel
	{
		private static final long serialVersionUID = 2488127639285339811L;
		private JTextField codeInput;
		private JTextField codeType;
		private JRadioButton in;
		private JRadioButton out;
		private ButtonGroup bg;
		private JButton okButton;
		private JButton cancelButton;
		private JDialog dialog;
		private boolean ok;

		public MyDialog()
		{
			//setPreferredSize(new Dimension(400, 350));
			setLayout(new BorderLayout());
			//�������
			var panel = new JPanel();
			var bPanel = new JPanel();
			bg = new ButtonGroup();
			bg.add(in = new JRadioButton("����"));
			bg.add(out = new JRadioButton("���"));
			bPanel.setLayout(new GridLayout(1,3));
			bPanel.add(new JLabel("����"));
			bPanel.add(in);
			bPanel.add(out);
			add(bPanel,BorderLayout.NORTH);
			panel.setLayout(new GridLayout(3, 3));
			panel.add(new JLabel("����"));
			panel.add(codeType = new JTextField(""));
			panel.add(new JLabel("������"));
			panel.add(codeInput = new JTextField(""));
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
			if(out.isSelected())
			{
				InOut = false;
				func = Func.OUT;
				return codeInput.getText();
			}
			else
			{
				InOut = true;
				func = Func.IN;
				return stringToLowerCase(codeType.getText())+" "+codeInput.getText();
			}
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

			dialog.setLocation((int)MyParallelogram.super.p.getX(),(int)MyParallelogram.super.p.getY());
			dialog.setTitle(title);
			dialog.setVisible(true);
			return ok;
		}
	}
}
