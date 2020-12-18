package pers.sfc.shapes;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MyDiamond extends Shape{
	private MyArrow arrow1;
	private MyArrow arrow2;
	private Position pos1;
	private Position pos2;
	private Position truePos;
	private Position falsePos;
	private MyArrow trueArrow;
	private MyArrow falseArrow;
	private int Judge;
	public MyDiamond(MyPoint p,double length,double width)
	{
		super.p = p;
		super.length = length;
		super.width = width;
		this.arrow1 = null;
		this.arrow2 = null;
		this.pos1 = null;
		this.pos2 = null;
		this.trueArrow = null;
		this.falseArrow = null;
		super.func = Func.JUDGE;
		this.Judge = -1;
	}

	public MyDiamond(double x,double y,double length,double width)
	{
		this(new MyPoint(x,y),length,width);
	}

	public MyDiamond()
	{
		this(new MyPoint(),125,75);
	}

	//����Ƿ���ͼ����
	@Override
	public boolean containsN(Point2D p) {//�����
		var testp = new MyPoint(p.getX(),p.getY());
		var p1 = new MyPoint(super.p.getX()+super.length*0.5,super.p.getY());
		var p2 = new MyPoint(super.p.getX(),super.p.getY()+super.width*0.5);
		var p3 = new MyPoint(super.p.getX()+super.length*0.5,super.p.getY()+super.width);
		var p4 = new MyPoint(super.p.getX()+super.length,super.p.getY()+super.width*0.5);
		double area = triArea(testp,p1,p2)+triArea(testp,p2,p3)+triArea(testp,p3,p4)+triArea(testp,p1,p4);
		if(area<super.length*0.5*super.width+C)
			return true;
		return false;
	}
	//������
	@Override
	public void drawEntity(Graphics2D g) {
		double x = super.p.getX();
		double y = super.p.getY();
		if(color != null&&color.equals(Color.RED))
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(B));
		g.draw(new Line2D.Double(x,y+super.width/2,x+super.length/2,y));
		g.draw(new Line2D.Double(x,y+super.width/2,x+super.length/2,y+super.width));
		g.draw(new Line2D.Double(x+super.length,y+super.width/2,x+super.length/2,y));
		g.draw(new Line2D.Double(x+super.length,y+super.width/2,x+super.length/2,y+super.width));
		drawCode(g);
	}

	//�����һ��ͼ��
	public Shape getNext()
	{
		if(Judge == 1)
			return getTNext();
		else if(Judge == 0)
			return getFNext();
		else
			return null;
	}
	//�����һ����ȷͼ��
	public Shape getTNext()
	{
		if(nJudge&&nFunc&&truePos.equals(Position.NORTH)) {
			nArrow.setColorOn(Color.RED);
			return this.nArrow.getEnd();
		}
		else if(wJudge&&wFunc&&truePos.equals(Position.WEST)) {
			wArrow.setColorOn(Color.RED);
			return this.wArrow.getEnd();
		}
		else if(sJudge&&sFunc&&truePos.equals(Position.SOUTH)) {
			sArrow.setColorOn(Color.RED);
			return this.sArrow.getEnd();
		}
		else if(eJudge&&eFunc&&truePos.equals(Position.EAST)) {
			eArrow.setColorOn(Color.RED);
			return this.eArrow.getEnd();
		}
		else
			return null;
	}
	//�����һ������ͼ��
	public Shape getFNext()
	{
		if(nJudge&&nFunc&&falsePos.equals(Position.NORTH)) {
			nArrow.setColorOn(Color.RED);
			return this.nArrow.getEnd();
		}
		else if(wJudge&&wFunc&&falsePos.equals(Position.WEST)) {
			wArrow.setColorOn(Color.RED);
			return this.wArrow.getEnd();
		}
		else if(sJudge&&sFunc&&falsePos.equals(Position.SOUTH)) {
			sArrow.setColorOn(Color.RED);
			return this.sArrow.getEnd();
		}
		else if(eJudge&&eFunc&&falsePos.equals(Position.EAST)) {
			eArrow.setColorOn(Color.RED);
			return this.eArrow.getEnd();
		}
		else
			return null;
	}
	@Override
	public void draw(Graphics2D g) {
		state.draw(this, g);
	}

	@Override
	public boolean contains(Point2D pIn) {
		return this.state.contains(this, pIn);
	}
	//��������
	public boolean codeRun()
	{
		Judge = execute.codeExecution(this);
		return true;
	}
	//��������
	@Override
	public String codeGen(int num) {
		return generate.codeGenerate(this,num);
	}
	//��Ϊ��ʼ��Ǳ���++
	public void startPlus()
	{
		if(asStart<2)
			asStart++;
	}
	//ȷ���Ƿ��������ӵ�
	/*
	public void setConnect(Position p,MyArrow arrow)
	{
		if(pos1 == null)
		{
			if(Position.NORTH.equals(p))
			{
				nJudge = true;
				pos1 = p;
			}
			else if(Position.WEST.equals(p))
			{
				wJudge = true;
				pos1 = p;
			}
			else if(Position.SOUTH.equals(p))
			{
				sJudge = true;
				pos1 = p;
			}
			else if(Position.EAST.equals(p))
			{
				eJudge = true;
				pos1 = p;
			}
		}
		else if(pos2 == null)
		{
			if(Position.NORTH.equals(p))
			{
				nJudge = true;
				pos2 = p;
			}
			else if(Position.WEST.equals(p))
			{
				wJudge = true;
				pos2 = p;
			}
			else if(Position.SOUTH.equals(p))
			{
				sJudge = true;
				pos2 = p;
			}
			else if(Position.EAST.equals(p))
			{
				eJudge = true;
				pos2 = p;
			}
		}
	}
	//���ü�ͷ
	public void setArrow(MyArrow arrow)
	{
		if(this.arrow1 == null)
			this.arrow1 = arrow;
		else if(this.arrow2 == null)
			this.arrow2 = arrow;
	}
	 */
	//��ʾ����
	public boolean showDialog(Component parent)
	{
		var dialog = new MyDialog();
		boolean ok;
		if(ok = dialog.showDialog(parent, "��������"))
		{
			truePos = dialog.getTOut();
			falsePos = dialog.getFOut();
			super.code = dialog.getFunction();
		}
		return ok;
	}
	//���ó�������
	public void setJudge(int judge)
	{
		this.Judge = -1;
	}
	//˽�д�����
	private class MyDialog extends JPanel
	{
		private static final long serialVersionUID = 2488127639285339811L;
		private JTextField codeInput;
		private JRadioButton trueNorth;
		private JRadioButton trueWest;
		private JRadioButton trueSouth;
		private JRadioButton trueEast;
		private ButtonGroup trueGroup;
		private JRadioButton falseNorth;
		private JRadioButton falseWest;
		private JRadioButton falseSouth;
		private JRadioButton falseEast;
		private ButtonGroup falseGroup;
		private JButton okButton;
		private JButton cancelButton;
		private JDialog dialog;
		private boolean ok;

		public MyDialog()
		{
			setLayout(new BorderLayout());
			//�������
			var textPanel = new JPanel();
			var rbPanel = new JPanel();
			trueGroup = new ButtonGroup();
			falseGroup = new ButtonGroup();
			textPanel.setLayout(new GridLayout(1, 2));
			textPanel.add(new JLabel("����"));
			textPanel.add(codeInput = new JTextField(""));
			add(textPanel, BorderLayout.NORTH);
			trueGroup.add(trueEast = new JRadioButton("��"));
			trueGroup.add(trueWest = new JRadioButton("��"));
			trueGroup.add(trueSouth = new JRadioButton("��"));
			trueGroup.add(trueNorth = new JRadioButton("��"));
			falseGroup.add(falseEast = new JRadioButton("��"));
			falseGroup.add(falseWest = new JRadioButton("��"));
			falseGroup.add(falseSouth = new JRadioButton("��"));
			falseGroup.add(falseNorth = new JRadioButton("��"));
			rbPanel.setLayout(new GridLayout(2, 5));
			rbPanel.add(new JLabel("��ȷ����"));
			rbPanel.add(trueEast);
			rbPanel.add(trueWest);
			rbPanel.add(trueSouth);
			rbPanel.add(trueNorth);
			rbPanel.add(new JLabel("�������"));
			rbPanel.add(falseEast);
			rbPanel.add(falseWest);
			rbPanel.add(falseSouth);
			rbPanel.add(falseNorth);
			add(rbPanel, BorderLayout.CENTER);
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
			return codeInput.getText();
		}
		//�����ȷ����
		public Position getTOut()
		{
			if(trueNorth.isSelected())
				return Position.NORTH;
			else if(trueWest.isSelected())
				return Position.WEST;
			else if(trueSouth.isSelected())
				return Position.SOUTH;
			else if(trueEast.isSelected())
				return Position.EAST;
			else
				return Position.NONE;
		}
		//��ô������
		public Position getFOut()
		{
			if(falseNorth.isSelected())
				return Position.NORTH;
			else if(falseWest.isSelected())
				return Position.WEST;
			else if(falseSouth.isSelected())
				return Position.SOUTH;
			else if(falseEast.isSelected())
				return Position.EAST;
			else
				return Position.NONE;
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

			dialog.setLocation((int)MyDiamond.super.p.getX(),(int)MyDiamond.super.p.getY());
			dialog.setTitle(title);
			dialog.setVisible(true);
			return ok;
		}
	}
}
