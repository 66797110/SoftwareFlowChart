package pers.sfc.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JTextField;

import pers.sfc.execute.CodeExecute;
import pers.sfc.execute.CodeGenerate;
import pers.sfc.windows.MyDocument;

abstract public class Shape {
	private static final double A = 5;//���������
	protected static final float B = 2;//���ʿ��/��ͷ���������
	protected static final float C = 2;//����ݴ�
	protected MyPoint p; //���ϽǶ�λ
	protected double length; //��
	protected double width; //��
	protected State state = new NormalState();
	protected State lstate;
	protected String code = "";
	protected JTextField textField;
	protected CodeExecute execute;//��������
	protected CodeGenerate generate;//��������
	protected MyArrow nArrow,wArrow,sArrow,eArrow;//�����ϱ�����м�ͷ
	protected boolean nFunc,wFunc,sFunc,eFunc;//�������ͷ�����ã�false������true����
	protected boolean nJudge = false,
			wJudge = false,
			sJudge = false,
			eJudge = false;//�����ϱ��Ƿ����Ӽ�ͷ��false��û���ӣ�true������
	protected int asStart = 0,
			asEnd = 0;//��Ϊ��ʼ�ͽ���ͼ�ε�����
	//protected MyArrow outArrow;
	protected Func func;
	//��ɫ����
	protected Color color = Color.BLACK;
	//����Ƿ���ͼ������ͨ���
	abstract public boolean containsN(Point2D p);
	//����Ƿ���ͼ����
	abstract public boolean contains(Point2D pIn);
	//��ͼ
	abstract public void draw(Graphics2D g);
	//��ͼ�ڲ�
	abstract public void drawEntity(Graphics2D g);
	//�Ի���
	abstract public boolean showDialog(Component parent);
	//��������
	public boolean codeRun() {return true;}
	//��������
	abstract public String codeGen(int num);
	//�����ڲ���ɫ
	//abstract public void setColorIn();
	//���ñ߿���ɫ
	public void setColorOn(Color color)
	{
		this.color = color;
	}
	//�����ӵ�
	public void connectPoint(Graphics2D g)
	{
		//���ı�СԲ��
		g.setStroke(new BasicStroke(1));
		if(!nJudge||(!nFunc&&nJudge))g.draw(new Ellipse2D.Double(this.p.getX()+this.length*0.5-5, this.p.getY()-5, 10, 10));
		if(!wJudge||(!wFunc&&wJudge))g.draw(new Ellipse2D.Double(this.p.getX()-5, this.p.getY()+this.width*0.5-5, 10, 10));
		if(!sJudge||(!sFunc&&sJudge))g.draw(new Ellipse2D.Double(this.p.getX()+this.length*0.5-5, this.p.getY()+this.width-5, 10, 10));
		if(!eJudge||(!eFunc&&eJudge))g.draw(new Ellipse2D.Double(this.p.getX()+this.length-5, this.p.getY()+this.width*0.5-5, 10, 10));
		//СԲ������ɫ
		g.setColor(Color.WHITE);
		if(!nJudge||(!nFunc&&nJudge))g.fill(new Ellipse2D.Double(this.p.getX()+this.length*0.5-4.5, this.p.getY()-4.5, 9, 9));
		if(!wJudge||(!wFunc&&wJudge))g.fill(new Ellipse2D.Double(this.p.getX()-4.5, this.p.getY()+this.width*0.5-4.5, 9, 9));
		if(!sJudge||(!sFunc&&sJudge))g.fill(new Ellipse2D.Double(this.p.getX()+this.length*0.5-4.5, this.p.getY()+this.width-4.5, 9, 9));
		if(!eJudge||(!eFunc&&eJudge))g.fill(new Ellipse2D.Double(this.p.getX()+this.length-4.5, this.p.getY()+this.width*0.5-4.5, 9, 9));
	}
	//��������
	public MyPoint getPoint(Position p)
	{
		if(Position.NORTH.equals(p))
			return new MyPoint(this.p.getX()+this.length*0.5,this.p.getY());
		else if(Position.WEST.equals(p))
			return new MyPoint(this.p.getX(),this.p.getY()+this.width*0.5);
		else if(Position.SOUTH.equals(p))
			return new MyPoint(this.p.getX()+this.length*0.5,this.p.getY()+this.width);
		else if(Position.EAST.equals(p))
			return new MyPoint(this.p.getX()+this.length,this.p.getY()+this.width*0.5);
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
			if(!nJudge||(!nFunc&&nJudge))return Position.NORTH;else return Position.NONE;
		else if(Math.sqrt(Math.pow(this.p.getX()-x, 2)+Math.pow(this.p.getY()+this.width*0.5-y, 2))<=6)//��ԲȦ
			if(!wJudge||(!wFunc&&wJudge))return Position.WEST;else return Position.NONE;
		else if(Math.sqrt(Math.pow(this.p.getX()+this.length*0.5-x, 2)+Math.pow(this.p.getY()+this.width-y, 2))<=6)//�·�ԲȦ
			if(!sJudge||(!sFunc&&sJudge))return Position.SOUTH;else return Position.NONE;
		else if(Math.sqrt(Math.pow(this.p.getX()+this.length-x, 2)+Math.pow(this.p.getY()+this.width*0.5-y, 2))<=6)//�ҷ�ԲȦ
			if(!eJudge||(!eFunc&&eJudge))return Position.EAST;else return Position.NONE;
		else
			return Position.NONE;
	}
	//��ʾ��������ӵ�Ϳ��
	public void fillPoint(Position p,Graphics2D g)
	{
		if(Position.NORTH.equals(p))
			g.fill(new Ellipse2D.Double(this.p.getX()+this.length*0.5-4.5, this.p.getY()-4.5, 9, 9));
		else if(Position.WEST.equals(p))
			g.fill(new Ellipse2D.Double(this.p.getX()-4.5, this.p.getY()+this.width*0.5-4.5, 9, 9));
		else if(Position.SOUTH.equals(p))
			g.fill(new Ellipse2D.Double(this.p.getX()+this.length*0.5-4.5, this.p.getY()+this.width-4.5, 9, 9));
		else if(Position.EAST.equals(p))
			g.fill(new Ellipse2D.Double(this.p.getX()+this.length-4.5, this.p.getY()+this.width*0.5-4.5, 9, 9));
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
		if(!this.state.getClass().getName().equals("pers.sfc.shapes.SelectedState"))
		{
			this.lstate = this.state;
			this.state = state;
		}
	}
	//�ص�״̬
	public void backState()
	{
		if(this.state.getClass().getName().equals("pers.sfc.shapes.SelectedState"))
		{
			return;
		}
		else if(this.lstate.getClass().getName().equals("pers.sfc.shapes.SelectState"))
		{
			this.state = new NormalState();
		}
		else
		{

			this.state = this.lstate;
		}
	}
	//ǿ�ƻص�״̬
	public void strongBackState()
	{
		//this.state = this.lstate;
		this.state = new NormalState();
	}
	//���״̬
	public State getState()
	{
		return this.state;
	}
	//��������
	public MyPoint returnPoint(Position p)
	{
		if(Position.NORTH.equals(p))
			return new MyPoint(this.p.getX()+this.length*0.5,this.p.getY());
		else if(Position.WEST.equals(p))
			return new MyPoint(this.p.getX(),this.p.getY()+this.width*0.5);
		else if(Position.SOUTH.equals(p))
			return new MyPoint(this.p.getX()+this.length*0.5,this.p.getY()+this.width);
		else if(Position.EAST.equals(p))
			return new MyPoint(this.p.getX()+this.length,this.p.getY()+this.width*0.5);
		return this.p;//�����ֵ
	}
	//��Ϊ��ʼ��Ǳ���++
	public void startPlus()
	{
		if(asStart<1)
			asStart++;
	}
	//��Ϊ��������++
	public void endPlus()
	{
		if(asStart+asEnd<4)
			asEnd++;
	}
	//ȷ���Ƿ��������ӵ�
	public void setConnect(Position p)
	{
		if(Position.NORTH.equals(p))
			nJudge = true;
		else if(Position.WEST.equals(p))
			wJudge = true;
		else if(Position.SOUTH.equals(p))
			sJudge = true;
		else if(Position.EAST.equals(p))
			eJudge = true;
	}
	//���ü�ͷ
	public void setArrow(MyArrow arrow,Position pos,boolean func)//func��false������true����
	{
		if(pos.equals(Position.NORTH)) {
			this.nArrow = arrow;
			this.nFunc = func;
		}
		else if(pos.equals(Position.WEST)) {
			this.wArrow = arrow;
			this.wFunc = func;
		}
		else if(pos.equals(Position.SOUTH)) {
			this.sArrow = arrow;
			this.sFunc = func;
		}
		else if(pos.equals(Position.EAST)) {
			this.eArrow = arrow;
			this.eFunc = func;
		}
	}
	//�����һ��ͼ��
	public Shape getNext()
	{
		if(nJudge&&nFunc) {
			nArrow.setColorOn(Color.RED);
			return this.nArrow.getEnd();
		}
		else if(wJudge&&wFunc) {
			wArrow.setColorOn(Color.RED);
			return this.wArrow.getEnd();
		}
		else if(sJudge&&sFunc) {
			sArrow.setColorOn(Color.RED);
			return this.sArrow.getEnd();
		}
		else if(eJudge&&eFunc) {
			eArrow.setColorOn(Color.RED);
			return this.eArrow.getEnd();
		}
		else
			return null;
	}
	//ɾ������ͷ
	public void deleteNArrow()
	{
		nJudge = false;

		nArrow = null;
	}
	//ɾ������ͷ
	public void deleteWArrow()
	{
		wJudge = false;
		wArrow = null;
	}
	//ɾ���ϼ�ͷ
	public void deleteSArrow()
	{
		sJudge = false;
		sArrow = null;
	}
	//ɾ������ͷ
	public void deleteEArrow()
	{
		eJudge = false;
		eArrow = null;
	}
	//�Ͽ���ͷ
	public void deConnectArrow(Position pos)
	{
		if(pos.equals(Position.NORTH))
			deleteNArrow();
		else if(pos.equals(Position.WEST))
			deleteWArrow();
		else if(pos.equals(Position.SOUTH))
			deleteSArrow();
		else if(pos.equals(Position.EAST))
			deleteEArrow();
	}
	//ɾ����ͷ
	public void deleteArrow(MyDocument myDocument)
	{
		MyArrow saveArrow;
		if(nJudge)
		{
			saveArrow = nArrow;
			nArrow.delete();
			myDocument.remove(saveArrow);
		}
		if(wJudge)
		{
			saveArrow = wArrow;
			wArrow.delete();
			myDocument.remove(saveArrow);
		}
		if(sJudge)
		{
			saveArrow = sArrow;
			sArrow.delete();
			myDocument.remove(saveArrow);
		}
		if(eJudge)
		{
			saveArrow = eArrow;
			eArrow.delete();
			myDocument.remove(saveArrow);
		}
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
			String Default = "...";
			int DeHeight = fm.getHeight();
			int DeWidth = fm.stringWidth(Default);
			int DWidth = (int)(this.length - DeWidth)/2;
			int DHeight = (int)(this.width - DeHeight)/2;
			// ��ʾ���������ͼƬ�ϵ�λ��(x,y) .��һ���������õ����ݡ�
			if(SWidth>0&&SHeight>0)
				g.drawString(this.code,(int)this.p.getX()+SWidth,(int)this.p.getY()+SHeight+textHeight-3);
			else if(DWidth>0&&DHeight>0)
				g.drawString(Default,(int)this.p.getX()+DWidth,(int)this.p.getY()+DHeight+DeHeight-3);
		}
	}
	//��дתСд
	protected String stringToLowerCase(String str)
	{
		char[] ch = str.toCharArray();  
		StringBuffer sbf = new StringBuffer();
		for(int i=0; i< ch.length; i++)
		{  
			if(ch[i] <= 90 && ch[i] >= 65) 
				ch[i] += 32;   
			sbf.append(ch[i]);  
		}  
		return sbf.toString();    
	}  
	//�������
	public Func getFunc()
	{
		return this.func;
	}
	//���ô�������
	public void setExecute(CodeExecute codeExecute)
	{
		this.execute = codeExecute;
	}
	//���ô�������
	public void setGenerate(CodeGenerate codeGenerate)
	{
		this.generate = codeGenerate;
	}
	//��ô���
	public String getCode()
	{
		return code;
	}
	/*
	//��ʾ����
	public boolean showDialog(Component parent)
	{
		var dialog = new MyDialog();
		boolean ok;
		if(ok = dialog.showDialog(parent, "��������"))
		{
			this.code = dialog.getFunction();
		}
		return ok;
	}

	//˽�д�����
	protected class MyDialog extends JPanel
	{
		private static final long serialVersionUID = 2488127639285339811L;
		private JTextField codeInput;
		private JButton okButton;
		private JButton cancelButton;
		private JDialog dialog;
		private boolean ok;

		public MyDialog()
		{
			setPreferredSize(new Dimension(400, 350));
			setLayout(new BorderLayout());
			//�������
			var panel = new JPanel();
			var label = new JLabel("����",JLabel.CENTER);
			label.setPreferredSize(new Dimension(400, 30));
			//panel.setLayout(new GridLayout(2,1));
			panel.add(label);
			panel.add(codeInput = new JTextField(""));
			codeInput.setPreferredSize(new Dimension(400,300));
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
			return codeInput.getText();
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
				//dialog.setSize(1000,2000);
				dialog.add(this);
				dialog.getRootPane().setDefaultButton(okButton);
				dialog.pack();
			}

			dialog.setLocation((int)Shape.this.p.getX(),(int)Shape.this.p.getY());
			dialog.setTitle(title);
			dialog.setVisible(true);
			return ok;
		}
	}
	 */

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
