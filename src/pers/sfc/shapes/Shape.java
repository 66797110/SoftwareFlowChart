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
	private static final double A = 5;//鼠标监听宽度
	protected static final float B = 2;//画笔宽度/箭头鼠标监听宽度
	protected static final float C = 2;//面积容错
	protected MyPoint p; //左上角定位
	protected double length; //长
	protected double width; //宽
	protected State state = new NormalState();
	protected State lstate;
	protected String code = "";
	protected JTextField textField;
	protected CodeExecute execute;//代码运行
	protected CodeGenerate generate;//代码生成
	protected MyArrow nArrow,wArrow,sArrow,eArrow;//东西南北点持有箭头
	protected boolean nFunc,wFunc,sFunc,eFunc;//各方向箭头的作用，false：进；true：出
	protected boolean nJudge = false,
			wJudge = false,
			sJudge = false,
			eJudge = false;//东西南北是否连接箭头，false：没连接，true：连接
	protected int asStart = 0,
			asEnd = 0;//作为开始和结束图形的数量
	//protected MyArrow outArrow;
	protected Func func;
	//颜色设置
	protected Color color = Color.BLACK;
	//鼠标是否在图形内普通情况
	abstract public boolean containsN(Point2D p);
	//鼠标是否在图形内
	abstract public boolean contains(Point2D pIn);
	//画图
	abstract public void draw(Graphics2D g);
	//画图内部
	abstract public void drawEntity(Graphics2D g);
	//对话框
	abstract public boolean showDialog(Component parent);
	//代码运行
	public boolean codeRun() {return true;}
	//代码生成
	abstract public String codeGen(int num);
	//设置内部颜色
	//abstract public void setColorIn();
	//设置边框颜色
	public void setColorOn(Color color)
	{
		this.color = color;
	}
	//画连接点
	public void connectPoint(Graphics2D g)
	{
		//画四边小圆形
		g.setStroke(new BasicStroke(1));
		if(!nJudge||(!nFunc&&nJudge))g.draw(new Ellipse2D.Double(this.p.getX()+this.length*0.5-5, this.p.getY()-5, 10, 10));
		if(!wJudge||(!wFunc&&wJudge))g.draw(new Ellipse2D.Double(this.p.getX()-5, this.p.getY()+this.width*0.5-5, 10, 10));
		if(!sJudge||(!sFunc&&sJudge))g.draw(new Ellipse2D.Double(this.p.getX()+this.length*0.5-5, this.p.getY()+this.width-5, 10, 10));
		if(!eJudge||(!eFunc&&eJudge))g.draw(new Ellipse2D.Double(this.p.getX()+this.length-5, this.p.getY()+this.width*0.5-5, 10, 10));
		//小圆形填充白色
		g.setColor(Color.WHITE);
		if(!nJudge||(!nFunc&&nJudge))g.fill(new Ellipse2D.Double(this.p.getX()+this.length*0.5-4.5, this.p.getY()-4.5, 9, 9));
		if(!wJudge||(!wFunc&&wJudge))g.fill(new Ellipse2D.Double(this.p.getX()-4.5, this.p.getY()+this.width*0.5-4.5, 9, 9));
		if(!sJudge||(!sFunc&&sJudge))g.fill(new Ellipse2D.Double(this.p.getX()+this.length*0.5-4.5, this.p.getY()+this.width-4.5, 9, 9));
		if(!eJudge||(!eFunc&&eJudge))g.fill(new Ellipse2D.Double(this.p.getX()+this.length-4.5, this.p.getY()+this.width*0.5-4.5, 9, 9));
	}
	//获得坐标点
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
	//鼠标点击连接点
	public Position clickedPoint(Point2D pIn)
	{
		if(!this.state.getClass().getName().equals("pers.sfc.shapes.SelectState"))
			return Position.NONE;
		double x=pIn.getX();
		double y=pIn.getY();
		if(Math.sqrt(Math.pow(this.p.getX()+this.length*0.5-x, 2)+Math.pow(this.p.getY()-y, 2))<=6)//上方圆圈
			if(!nJudge||(!nFunc&&nJudge))return Position.NORTH;else return Position.NONE;
		else if(Math.sqrt(Math.pow(this.p.getX()-x, 2)+Math.pow(this.p.getY()+this.width*0.5-y, 2))<=6)//左方圆圈
			if(!wJudge||(!wFunc&&wJudge))return Position.WEST;else return Position.NONE;
		else if(Math.sqrt(Math.pow(this.p.getX()+this.length*0.5-x, 2)+Math.pow(this.p.getY()+this.width-y, 2))<=6)//下方圆圈
			if(!sJudge||(!sFunc&&sJudge))return Position.SOUTH;else return Position.NONE;
		else if(Math.sqrt(Math.pow(this.p.getX()+this.length-x, 2)+Math.pow(this.p.getY()+this.width*0.5-y, 2))<=6)//右方圆圈
			if(!eJudge||(!eFunc&&eJudge))return Position.EAST;else return Position.NONE;
		else
			return Position.NONE;
	}
	//显示鼠标点击连接点涂黑
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
	//外接矩形
	public void drawCirRect(Graphics2D g)
	{
		//画虚线边框
		g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
		g.draw(new Rectangle2D.Double(this.p.getX(), this.p.getY(), this.length, this.width));
		//画四角小正方形
		g.setStroke(new BasicStroke(1));
		g.draw(new Rectangle2D.Double(this.p.getX()-5, this.p.getY()-5, 10, 10));
		g.draw(new Rectangle2D.Double(this.p.getX()+this.length-5, this.p.getY()-5, 10, 10));
		g.draw(new Rectangle2D.Double(this.p.getX()-5, this.p.getY()+this.width-5, 10, 10));
		g.draw(new Rectangle2D.Double(this.p.getX()+this.length-5, this.p.getY()+this.width-5, 10, 10));
		//小正方形填充白色
		g.setColor(Color.WHITE);
		g.fill(new Rectangle2D.Double(this.p.getX()-4, this.p.getY()-4, 9, 9));
		g.fill(new Rectangle2D.Double(this.p.getX()+this.length-4, this.p.getY()-4, 9, 9));
		g.fill(new Rectangle2D.Double(this.p.getX()-4, this.p.getY()+this.width-4, 9, 9));
		g.fill(new Rectangle2D.Double(this.p.getX()+this.length-4, this.p.getY()+this.width-4, 9, 9));
	}
	//鼠标是否在外接矩形的四角上
	public int on(Point2D pIn)
	{
		return this.state.on(this,pIn);
	}
	//鼠标是否在外接矩形的四角上
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
	//移动
	public void Offset(double cx,double cy)
	{
		this.p.Offset(cx, cy);
	}
	//改变大小
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
	//计算三角形面积
	protected static final double triArea(MyPoint a,MyPoint b,MyPoint c)
	{
		return Math.abs((a.getX()*b.getY()+b.getX()*c.getY()+c.getX()*a.getY()-b.getX()*a.getY()-c.getX()*b.getY()-a.getX()*c.getY())/2.0); 
	}
	//设置状态
	public void setState(State state)
	{
		if(!this.state.getClass().getName().equals("pers.sfc.shapes.SelectedState"))
		{
			this.lstate = this.state;
			this.state = state;
		}
	}
	//回调状态
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
	//强制回调状态
	public void strongBackState()
	{
		//this.state = this.lstate;
		this.state = new NormalState();
	}
	//获得状态
	public State getState()
	{
		return this.state;
	}
	//返回坐标
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
		return this.p;//填补返回值
	}
	//作为开始标记变量++
	public void startPlus()
	{
		if(asStart<1)
			asStart++;
	}
	//作为结束变量++
	public void endPlus()
	{
		if(asStart+asEnd<4)
			asEnd++;
	}
	//确定是否连接连接点
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
	//设置箭头
	public void setArrow(MyArrow arrow,Position pos,boolean func)//func：false：进；true：出
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
	//获得下一个图形
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
	//删除北箭头
	public void deleteNArrow()
	{
		nJudge = false;

		nArrow = null;
	}
	//删除西箭头
	public void deleteWArrow()
	{
		wJudge = false;
		wArrow = null;
	}
	//删除南箭头
	public void deleteSArrow()
	{
		sJudge = false;
		sArrow = null;
	}
	//删除东箭头
	public void deleteEArrow()
	{
		eJudge = false;
		eArrow = null;
	}
	//断开箭头
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
	//删除箭头
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
	//画代码
	protected void drawCode(Graphics2D g)
	{
		if(this.code != null) {
			Font font=new Font("宋体",Font.PLAIN,20);  
			g.setFont(font);  
			// 抗锯齿
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			// 计算文字长度，计算居中的x点坐标
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
			// 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
			if(SWidth>0&&SHeight>0)
				g.drawString(this.code,(int)this.p.getX()+SWidth,(int)this.p.getY()+SHeight+textHeight-3);
			else if(DWidth>0&&DHeight>0)
				g.drawString(Default,(int)this.p.getX()+DWidth,(int)this.p.getY()+DHeight+DeHeight-3);
		}
	}
	//大写转小写
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
	//获得作用
	public Func getFunc()
	{
		return this.func;
	}
	//设置代码运行
	public void setExecute(CodeExecute codeExecute)
	{
		this.execute = codeExecute;
	}
	//设置代码生成
	public void setGenerate(CodeGenerate codeGenerate)
	{
		this.generate = codeGenerate;
	}
	//获得代码
	public String getCode()
	{
		return code;
	}
	/*
	//显示窗口
	public boolean showDialog(Component parent)
	{
		var dialog = new MyDialog();
		boolean ok;
		if(ok = dialog.showDialog(parent, "代码输入"))
		{
			this.code = dialog.getFunction();
		}
		return ok;
	}

	//私有窗口类
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
			//面板设置
			var panel = new JPanel();
			var label = new JLabel("代码",JLabel.CENTER);
			label.setPreferredSize(new Dimension(400, 30));
			//panel.setLayout(new GridLayout(2,1));
			panel.add(label);
			panel.add(codeInput = new JTextField(""));
			codeInput.setPreferredSize(new Dimension(400,300));
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

	//移动状态
	/*
	public class MoveState implements State{
		@Override
		public void draw(Shape s,Graphics2D g) {
			g.setColor(Color.BLACK);
			//s.drawEntity(g);
			Shape.this.drawEntity(g);
			g.setColor(Color.BLACK);
			//s.drawCirRect(g);
			//画虚线边框
			g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
			g.draw(new Rectangle2D.Double(Shape.this.p.getX(), Shape.this.p.getY(), Shape.this.length, Shape.this.width));
			//画四角小正方形
			g.setStroke(new BasicStroke(1));
			g.draw(new Rectangle2D.Double(Shape.this.p.getX()-5, Shape.this.p.getY()-5, 10, 10));
			g.draw(new Rectangle2D.Double(Shape.this.p.getX()+Shape.this.length-5, Shape.this.p.getY()-5, 10, 10));
			g.draw(new Rectangle2D.Double(Shape.this.p.getX()-5, Shape.this.p.getY()+Shape.this.width-5, 10, 10));
			g.draw(new Rectangle2D.Double(Shape.this.p.getX()+Shape.this.length-5, Shape.this.p.getY()+Shape.this.width-5, 10, 10));
			//小正方形填充白色
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
