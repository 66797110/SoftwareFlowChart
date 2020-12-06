package pers.sfc.windows;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import pers.sfc.shapes.MyCircle;
import pers.sfc.shapes.MyDiamond;
import pers.sfc.shapes.MyParallelogram;
import pers.sfc.shapes.MyRectangle;
import pers.sfc.shapes.MyRoundRectangle;

public class MyFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9152295842323604216L;
	//图形文件
	private MyDocument myDocument;
	//输入输出
	private MyFileInOut myFileInOut;
	//画板
	private MyComponent myComponent;
	//菜单
	private JMenuBar bar;
	//文件菜单
	private JMenu file;
	private JMenuItem create;
	private JMenuItem open;
	private JMenuItem save;
	private JMenuItem saveAs;
	//图形菜单
	private JMenu shape;
	private JMenuItem roundRect;
	private JMenuItem para;
	private JMenuItem rect;
	private JMenuItem diam;
	private JMenuItem cir;
	public MyFrame()
	{
		//初始化画板
		myComponent = new MyComponent();
		//初始化文件
		myDocument = new MyDocument();
		//初始化输入输出
		myFileInOut = new MyFileInOut();
		//初始化菜单栏
		bar = new JMenuBar();
		//初始化文件菜单
		file = new JMenu("文件");
		create = new JMenuItem("新建");
		open = new JMenuItem("打开");
		save = new JMenuItem("保存");
		saveAs = new JMenuItem("另存为");
		//初始化图形菜单
		shape = new JMenu("图形");
		roundRect = new JMenuItem("起止点");
		para = new JMenuItem("输入/出框");
		rect = new JMenuItem("处理框");
		diam = new JMenuItem("判断框");
		cir = new JMenuItem("连接点");
		//将文件菜单添加进菜单栏
		file.add(create);
		file.add(open);
		file.add(save);
		file.add(saveAs);
		bar.add(file);
		//将图形菜单添加进菜单栏
		shape.add(roundRect);
		shape.add(para);
		shape.add(rect);
		shape.add(diam);
		shape.add(cir);
		bar.add(shape);
		//将菜单栏和画板加入框架
		setLayout(new BorderLayout());  
		setJMenuBar(bar);
		add(myComponent);  
		setVisible(true);
		//文件菜单栏监听
		create.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				//myComponent = new MyComponent();
				//add(myComponent);  
				//setVisible(true);
				myDocument.clean();
				//repaint();
			}
		});
/*
		open.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event) 
			{
				try {
					myComponent = new MyComponent();
					add(myComponent, BorderLayout.SOUTH);  
					add(buttons, BorderLayout.NORTH);
					myDocument.newList(fileInOut.open());
					myComponent.update(myDocument);
					setVisible(true);
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "alert", "alert", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
				//repaint(); 
			}
		});

		save.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event) 
			{
				try {
					fileInOut.save(myDocument.getList());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "alert", "alert", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});

		saveAs.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				try {
					fileInOut.saveAs(myDocument.getList());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//JOptionPane.showMessageDialog(null, "错误", "打开文件类型错误", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});
		*/

		//图形菜单栏监听
		roundRect.addActionListener(new ActionListener() 
		{  
			public void actionPerformed(ActionEvent event) 
			{
				myDocument.update(new MyRoundRectangle());
				myComponent.update(myDocument);
				repaint();  
			}  
		}); 
		para.addActionListener(new ActionListener() 
		{  
			public void actionPerformed(ActionEvent event) 
			{
				myDocument.update(new MyParallelogram());
				myComponent.update(myDocument);
				repaint();  
			}  
		}); 
		rect.addActionListener(new ActionListener() 
		{  
			public void actionPerformed(ActionEvent event) 
			{
				myDocument.update(new MyRectangle());
				myComponent.update(myDocument);
				repaint();  
			}  
		}); 
		diam.addActionListener(new ActionListener() 
		{  
			public void actionPerformed(ActionEvent event) 
			{
				myDocument.update(new MyDiamond());
				myComponent.update(myDocument);
				repaint();  
			}  
		}); 
		cir.addActionListener(new ActionListener() 
		{  
			public void actionPerformed(ActionEvent event) 
			{
				myDocument.update(new MyCircle());
				myComponent.update(myDocument);
				repaint();  
			}  
		});
	}
}
