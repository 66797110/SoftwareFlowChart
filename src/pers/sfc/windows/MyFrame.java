package pers.sfc.windows;

import pers.sfc.windows.MyFileInOut;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import pers.sfc.shapes.MyRectangle;
import pers.sfc.shapes.Shape;

public class MyFrame extends JFrame{
	//图形文件
	private MyDocument myDocument;
	//输入输出
	private MyFileInOut myFileInOut;
	//画板
	private MyComponent myComponent;
	//按钮面板
	private JPanel buttons;
	//按钮
	private JButton rect;
	private JButton tri;
	private JButton cir;
	//菜单
	private JMenuBar bar;
	private JMenu file;
	private JMenuItem create;
	private JMenuItem open;
	private JMenuItem save;
	private JMenuItem saveAs;
	public MyFrame()
	{
		//初始化文件
		myDocument = new MyDocument();
		//初始化输入输出
		myFileInOut = new MyFileInOut();
		//初始化按钮
		buttons = new JPanel();
		buttons.setBackground(Color.WHITE);
		buttons.setPreferredSize(new Dimension(700, 300));
		rect = new JButton("新建矩形");
		tri = new JButton("新建三角形");
		cir = new JButton("新建圆形");
		//初始化菜单栏
		bar = new JMenuBar();
		file = new JMenu("文件");
		create = new JMenuItem("新建");
		open = new JMenuItem("打开");
		save = new JMenuItem("保存");
		saveAs = new JMenuItem("另存为");
		//将按钮添加进按钮组
		ButtonGroup bg = new ButtonGroup();
		bg.add(rect);
		bg.add(tri);
		bg.add(cir);
		//将选项添加进菜单栏
		file.add(create);
		file.add(open);
		file.add(save);
		file.add(saveAs);
		bar.add(file);
		//将按钮、下拉框加入按钮面板
		buttons.setLayout(new FlowLayout());
		buttons.add(rect);  
		buttons.add(tri);  
		buttons.add(cir);  
		//将按钮面板和画板加入框架
		setLayout(new BorderLayout());  
		setJMenuBar(bar);
		myComponent = new MyComponent();
		add(myComponent, BorderLayout.SOUTH);  
		add(buttons, BorderLayout.NORTH);
		setVisible(true);
		//为按钮设置监听
		rect.addActionListener(new ActionListener() 
		{  
			public void actionPerformed(ActionEvent event) 
			{
				//var myRect = new MyRectangle();
				repaint();  
			}  
		}); 
	}
}
