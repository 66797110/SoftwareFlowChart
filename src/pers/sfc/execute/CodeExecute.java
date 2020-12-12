package pers.sfc.execute;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import pers.sfc.shapes.MyCircle;
import pers.sfc.shapes.MyDiamond;
import pers.sfc.shapes.MyParallelogram;
import pers.sfc.shapes.MyRectangle;
import pers.sfc.shapes.MyRoundRectangle;

public class CodeExecute implements Execute{
	private String code;
	private String num;
	private ArrayList<Variable> list = new ArrayList<Variable>();
	public boolean codeExecution(MyRoundRectangle roundrectangle)
	{
		code = roundrectangle.getCode();
		if(code.equals("start"))
			return true;
		else
			return false;
	}

	public boolean codeExecution(MyRectangle rectangle)
	{
		Variable num = null;
		Variable opNum = null;
		String op = null;
		String type = null;
		String name = null;
		code = rectangle.getCode();
		char[] ch = code.toCharArray();
		String[] arr = new String[4];
		StringBuffer sbf = new StringBuffer();
		StringBuffer vbf = new StringBuffer();
		int i;
		for(i = 0;i<ch.length&&ch[i]==' ';i++);
		for(;(('a'<=ch[i]&&ch[i]<='z')||('A'<=ch[i]&&ch[i]<='Z')||ch[i]=='_')&&i<ch.length;i++)
			vbf.append(ch[i]);
		if(vbf.toString().equals("int")||vbf.toString().equals("float")||vbf.toString().equals("double")||vbf.toString().equals("boolean")||vbf.toString().equals("char"))
		{
			type = vbf.toString();
			vbf.delete(0, vbf.length());
			for(;i<ch.length&&ch[i]==' ';i++);
			for(;(('a'<=ch[i]&&ch[i]<='z')||('A'<=ch[i]&&ch[i]<='Z')||ch[i]=='_')&&i<ch.length;i++)
				vbf.append(ch[i]);
			for(int j = 0;j<list.size();j++)
				if(list.get(j).getName().equals(vbf.toString()))
					return false;
			name = vbf.toString();
			var var = new Variable(name,type);
			for(;i<ch.length&&ch[i]==' ';i++);
			if(ch[i]==';')
				list.add(var);
			else if(ch[i]=='=')
			{
				vbf.delete(0, vbf.length());
				for(i++;i<ch.length&&ch[i]==' ';i++);
				for(;'0'<=ch[i]&&ch[i]<='9'&&i<ch.length;i++)
					vbf.append(ch[i]);
				var.setVar(Double.parseDouble(vbf.toString()));
				list.add(var);

			}
			else
				return false;
		}
		else
		{
			for(int j = 0;j<list.size();j++)
				if(list.get(j).getName().equals(vbf.toString()))
				{
					num = list.get(j);
					break;
				}
			vbf.delete(0, vbf.length());
			for(;i<ch.length&&ch[i]==' ';i++);
			if(ch[i]=='=') 
			{
				sbf.delete(0, sbf.length());
				for(i++;i<ch.length;i++)
				{
					if(i<ch.length&&ch[i]==' ')continue;
					for(;i<ch.length&&(('a'<=ch[i]&&ch[i]<='z')||('A'<=ch[i]&&ch[i]<='Z')||ch[i]=='_');i++)
						vbf.append(ch[i]);
					if(vbf.length() != 0)
					{
						opNum = null;
						for(int j = 0;j<list.size();j++)
							if(list.get(j).getName().equals(vbf.toString()))
							{
								opNum = list.get(j);
								break;
							}
						vbf.delete(0, vbf.length());
						sbf.append(""+opNum.getVar());
					}
					if(i<ch.length&&ch[i]==' ')continue;
					for(;i<ch.length&&(('0'<=ch[i]&&ch[i]<='9')||ch[i]=='+'||ch[i]=='-'||ch[i]=='*'||ch[i]=='/'||ch[i]=='('||ch[i]==')');i++)
						sbf.append(ch[i]);
					i--;
				}

			}
			else
				return false;
		}

		Calculator cal = new Calculator();
		double value = cal.calculator(sbf.toString());
		num.setVar(value);
		return true;
	}

	public boolean codeExecution(MyParallelogram parallelogram,Component parent,boolean InOut)//true:IN;   false:OUT;
	{
		code = parallelogram.getCode();
		String [] arr = code.split("\\s+");
		String type;
		String name;
		double value = 0;
		if(InOut)
		{
			type = arr[0];
			name = arr[1];
			var var = new Variable(name,type);
			if(showInDialog(parent,name) && !this.num.isEmpty())
				var.setVar(Double.parseDouble(num));
			list.add(var);
		}
		else
		{
			name = arr[0];
			Variable num = null;
			for(int i = 0;i<list.size();i++)
				if(list.get(i).getName().equals(name))
				{
					num = list.get(i);
					break;
				}
			if(num != null)
				value = num.getVar();
			showOutDialog(parent,name,value);
		}
		return true;
	}

	public int codeExecution(MyDiamond diamond)
	{
		Variable num1 = null;
		Variable num2 = null;
		String op = null;
		code = diamond.getCode();
		char[] ch = code.toCharArray();
		String[] arr = new String[4];
		StringBuffer sbf = new StringBuffer();
		int j = 0;
		boolean full = true;//判断是否是带感叹号
		//获得比大小的变量名与比大小符号
		if((ch[0]>=65&&ch[0]<=90)||(ch[0]>=97&&ch[0]<=122))
		{
			full = true;
			boolean isVar = true;
			for(int i = 0;i < ch.length;i++)
			{
				if(ch[i]==' ')
					continue;
				if(((ch[i]>=65&&ch[i]<=90)||(ch[i]>=97&&ch[i]<=122)||ch[i]=='_')&&isVar) {
					sbf.append(ch[i]);
					continue;
				}
				if(isVar) {
					arr[j++] = sbf.toString();
					sbf.delete(0, sbf.length());
					isVar= false;
				}
				if((ch[i]=='<'||ch[i]=='='||ch[i]=='>'||ch[i]=='!')&&!isVar) {
					sbf.append(ch[i]);
					continue;
				}
				if(!isVar) {
					arr[j++] = sbf.toString();
					sbf.delete(0, sbf.length());
					isVar = true;
				}
			}
		}
		else// if(ch[0]=='!')
		{
			full = false;
			sbf.append(ch[0]);
			arr[j++] = sbf.toString();
			sbf.delete(0, sbf.length());
			for(int i = 1;i < ch.length;i++)
			{
				if(ch[i]==' ')
					continue;
				if((ch[i]>=65&&ch[i]<=90)||(ch[i]>=97&&ch[i]<=122)||ch[i]=='_') {
					sbf.append(ch[i]);
					continue;
				}
				arr[j++] = sbf.toString();
				sbf.delete(0, sbf.length());
			}
		}
		//if(full)
		//{
		for(int i = 0;i<list.size();i++)
			if(list.get(i).getName().equals(arr[0]))
			{
				num1 = list.get(i);
				break;
			}
		op = arr[1];
		for(int i = 0;i < list.size();i++)
			if(list.get(i).getName().equals(arr[2]))
			{
				num2 = list.get(i);
				break;
			}
		//}
		if(num1==null||num2==null)
			return 0;
		switch(op)
		{
		case("<"):
			if(num1.getVar()<num2.getVar())return 1;else return 0;
		case("<="):
			if(num1.getVar()<=num2.getVar())return 1;else return 0;
		case(">"):
			if(num1.getVar()>num2.getVar())return 1;else return 0;
		case(">="):
			if(num1.getVar()>=num2.getVar())return 1;else return 0;
		case("=="):
			if(num1.getVar()==num2.getVar())return 1;else return 0;
		case("!="):
			if(num1.getVar()!=num2.getVar())return 1;else return 0;
		}
		return -1;
	}

	public boolean codeExecution(MyCircle circle)
	{

		return true;
	}

	public void reset()
	{
		for(int i = 0;i<list.size();i++)
		{
			list.remove(i);
		}
		System.out.println(list);
	}


	//显示窗口
	private boolean showInDialog(Component parent,String varName)
	{
		var dialog = new MyInDialog(varName);
		boolean ok;
		if(ok = dialog.showDialog(parent, "输入数据"))
		{
			this.num = dialog.getFunction();
		}
		return ok;
	}

	//私有窗口类
	private class MyInDialog extends JPanel
	{
		private static final long serialVersionUID = 2488127639285339811L;
		private JTextField varInput;
		private JButton okButton;
		private JButton cancelButton;
		private JDialog dialog;
		private boolean ok;

		public MyInDialog(String varName)
		{
			setLayout(new BorderLayout());
			//面板设置
			var panel = new JPanel();
			panel.setLayout(new GridLayout(1, 2));
			panel.add(new JLabel("输入"+varName+"的值："));
			panel.add(varInput = new JTextField(""));
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
			return varInput.getText();
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

			dialog.setLocation(parent.getWidth()-this.getWidth()/2,parent.getHeight()-this.getHeight()/2);
			dialog.setTitle(title);
			dialog.setVisible(true);
			return ok;
		}
	}

	//显示窗口
	private boolean showOutDialog(Component parent,String varName,double value)
	{
		var dialog = new MyOutDialog(varName,value);
		boolean ok;
		ok = dialog.showDialog(parent, "输出数据");
		return ok;
	}

	//私有窗口类
	private class MyOutDialog extends JPanel
	{
		private static final long serialVersionUID = 2488127639285339811L;
		private JButton okButton;
		private JButton cancelButton;
		private JDialog dialog;
		private boolean ok;

		public MyOutDialog(String varName,double value)
		{
			setLayout(new BorderLayout());
			//面板设置
			var panel = new JPanel();
			panel.setLayout(new GridLayout(1, 1));
			panel.add(new JLabel(varName+"的值："+String.valueOf(value)));
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

			dialog.setLocation(parent.getWidth()-this.getWidth()/2,parent.getHeight()-this.getHeight()/2);
			dialog.setTitle(title);
			dialog.setVisible(true);
			return ok;
		}
	}
}
