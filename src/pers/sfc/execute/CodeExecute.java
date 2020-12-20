package pers.sfc.execute;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import pers.sfc.shapes.MyCircle;
import pers.sfc.shapes.MyDiamond;
import pers.sfc.shapes.MyParallelogram;
import pers.sfc.shapes.MyRectangle;
import pers.sfc.shapes.MyRoundRectangle;

public class CodeExecute{
	private String code;
	private String num;
	private ArrayList<Variable> list = new ArrayList<Variable>();
	public boolean codeExecution(MyRoundRectangle roundrectangle)
	{
		code = roundrectangle.getCode();
		if(code.equals("start"))
			return true;
		return false;
	}

	public boolean codeExecution(MyRectangle rectangle)
	{
		Variable num = null;
		Variable opNum = null;
		String type = null;
		String name = null;
		code = rectangle.getCode();
		String [] arr = code.split("\n");
		//char[] ch = code.toCharArray();
		char[] ch;
		StringBuffer sbf = new StringBuffer();
		StringBuffer vbf = new StringBuffer();
		int i;
		for(int k = 0;k<arr.length;k++)
		{
			vbf.delete(0, vbf.length());
			ch = arr[k].toCharArray();
			for(i = 0;i<ch.length&&ch[i]==' ';i++);
			for(;(('a'<=ch[i]&&ch[i]<='z')||('A'<=ch[i]&&ch[i]<='Z')||ch[i]=='_')&&i<ch.length;i++)
				vbf.append(ch[i]);
			if(vbf.toString().equals("int")||vbf.toString().equals("float")||vbf.toString().equals("double")||vbf.toString().equals("boolean")||vbf.toString().equals("char"))
			{
				type = vbf.toString();
				vbf.delete(0, vbf.length());
				for(;i<ch.length&&ch[i]==' ';i++);
				for(;(i<ch.length&&('a'<=ch[i]&&ch[i]<='z')||('A'<=ch[i]&&ch[i]<='Z')||ch[i]=='_');i++)
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
							if(opNum.getVar()<0&&sbf.charAt(sbf.length()-1)!='(')
								sbf.append("("+opNum.getVar()+")");
							else
								sbf.append(""+opNum.getVar());
						}
						if(i<ch.length&&ch[i]==' ')continue;
						for(;i<ch.length&&(('0'<=ch[i]&&ch[i]<='9')||ch[i]=='+'||ch[i]=='-'||ch[i]=='*'||ch[i]=='/'||ch[i]=='('||ch[i]==')');i++)
							sbf.append(ch[i]);
						if(i<ch.length&&ch[i]==';')break;
						i--;
					}
					sbf.append("+0=");
					Calculator cal = new Calculator();
					double value = cal.calculator(sbf.toString());
					var.setVar(value);
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
							if(opNum.getVar()<0&&sbf.charAt(sbf.length()-1)!='(')
								sbf.append("("+opNum.getVar()+")");
							else
								sbf.append(""+opNum.getVar());
						}
						if(i<ch.length&&ch[i]==' ')continue;
						for(;i<ch.length&&(('0'<=ch[i]&&ch[i]<='9')||ch[i]=='+'||ch[i]=='-'||ch[i]=='*'||ch[i]=='/'||ch[i]=='('||ch[i]==')');i++)
							sbf.append(ch[i]);
						if(i<ch.length&&ch[i]==';')break;
						i--;
					}
					sbf.append("+0=");
					Calculator cal = new Calculator();
					double value = cal.calculator(sbf.toString());
					num.setVar(value);
				}
				else
					return false;
			}
		}
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
		Double num1 = Double.NaN;
		Double num2 = Double.NaN;
		String op = null;
		code = diamond.getCode();
		char[] ch = code.toCharArray();
		StringBuffer sbf = new StringBuffer();
		if(ch[0] == '!')
		{
			op = "!";
			int i = 2;
			for(;i<ch.length&&ch[i]==' ';i++);
			for(;(i<ch.length&&('a'<=ch[i]&&ch[i]<='z')||('A'<=ch[i]&&ch[i]<='Z')||ch[i]=='_');i++)
				sbf.append(ch[i]);
			if(sbf.length()!=0)
				for(int j = 0;j<list.size();j++)
					if(list.get(j).getName().equals(sbf.toString()))
					{
						Variable var = list.get(j);
						num1 = var.getVar();
						break;
					}
		}
		else if(('a'<=ch[0]&&ch[0]<='z')||('A'<=ch[0]&&ch[0]<='Z')||ch[0]=='_'||('0'<=ch[0]&&ch[0]<='9')||ch[0]=='-')
		{
			for(int i=0;i<ch.length;i++)
			{
				if(i<ch.length&&ch[i]==' ')continue;
				for(;i<ch.length&&(('a'<=ch[i]&&ch[i]<='z')||('A'<=ch[i]&&ch[i]<='Z')||ch[i]=='_');i++)
					sbf.append(ch[i]);
				if(sbf.length() != 0)
				{
					for(int j = 0;j<list.size();j++)
						if(list.get(j).getName().equals(sbf.toString()))
						{
							Variable var = list.get(j);
							if(num1.equals(Double.NaN))
								num1 = var.getVar();
							else if(num2.equals(Double.NaN))
								num2 = var.getVar();
							else
								return -1;
							break;
						}
					sbf.delete(0, sbf.length());
				}
				if(i<ch.length&&ch[i]==' ')continue;
				for(;i<ch.length&&(ch[i]=='<'||ch[i]=='>'||ch[i]=='=');i++)
					sbf.append(ch[i]);
				if(sbf.length() != 0)
					op = sbf.toString();
				sbf.delete(0, sbf.length());
				if(i<ch.length&&ch[i]==' ')continue;
				for(;i<ch.length&&(('0'<=ch[i]&&ch[i]<='9')||ch[i]=='-');i++)
					sbf.append(ch[i]);
				if(sbf.length() != 0)
				{
					if(num1.equals(Double.NaN))
						num1 = Double.parseDouble(sbf.toString());
					else if(num2.equals(Double.NaN))
						num2 = Double.parseDouble(sbf.toString());
					else
						return -1;
					sbf.delete(0, sbf.length());
				}
				i--;
			}
		}
		else
			return -1;
		//运算
		if(op!=null)
			switch(op)
			{
			case("!"):
				if(num1 == 0)return 1;else return 0;
			case("<"):
				if(num1<num2)return 1;else return 0;
			case("<="):
				if(num1<=num2)return 1;else return 0;
			case(">"):
				if(num1>num2)return 1;else return 0;
			case(">="):
				if(num1>=num2)return 1;else return 0;
			case("=="):
				if(num1==num2)return 1;else return 0;
			case("!="):
				if(num1!=num2)return 1;else return 0;
			}
		else
			if(num1 == 1)return 1;else return 0;
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
