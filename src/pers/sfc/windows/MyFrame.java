package pers.sfc.windows;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import pers.sfc.execute.CodeExecute;
import pers.sfc.execute.CodeGenerate;
import pers.sfc.execute.ShapeExecute;
import pers.sfc.execute.ShapeGenerate;
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
	//代码菜单
	private JMenu code;
	private JMenuItem run;
	private JMenuItem generate;
	//操作
	private JMenu operate;
	private JMenuItem delete;

	public MyFrame()
	{
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
		//初始化代码菜单
		code = new JMenu("代码");
		run = new JMenuItem("运行");
		generate = new JMenuItem("生成");
		//初始化操作菜单
		operate = new JMenu("操作");
		delete = new JMenuItem("删除");
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
		//将代码菜单添加进菜单栏
		code.add(run);
		code.add(generate);
		bar.add(code);
		//将操作菜单添加进菜单栏
		operate.add(delete);
		bar.add(operate);
		//将菜单栏和画板加入框架
		setLayout(new BorderLayout());  
		setJMenuBar(bar);
		//文件菜单栏监听
		create.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				if(myComponent!=null)
					remove(myComponent);
				myComponent = new MyComponent();
				add(myComponent);  
				setVisible(true);
				myDocument.clean();
				repaint();
			}
		});

		open.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event) 
			{
				try {
					if(myComponent!=null)
						remove(myComponent);
					myComponent = new MyComponent();
					add(myComponent);
					setVisible(true);
					myFileInOut.reFileName();
					myDocument.newList(myFileInOut.open());
					myComponent.update(myDocument);
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "alert", "alert", JOptionPane.ERROR_MESSAGE);
					((Throwable) e).printStackTrace();
				}
				repaint(); 
			}
		});

		save.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event) 
			{
				try {
					if(myComponent!=null) {
						myComponent.resetColor();
						myFileInOut.save(myDocument.getList());
					}
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
					myComponent.resetColor();
					myFileInOut.saveAs(myDocument.getList());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//JOptionPane.showMessageDialog(null, "错误", "打开文件类型错误", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});


		//图形菜单栏监听
		roundRect.addActionListener(new ActionListener() 
		{  
			public void actionPerformed(ActionEvent event) 
			{
				if(myComponent!=null)
				{
					var mrr = new MyRoundRectangle();
					myComponent.resetColor();
					myDocument.update(mrr);
					myComponent.update(myDocument);
					repaint();  
				}
			}  
		}); 
		para.addActionListener(new ActionListener() 
		{  
			public void actionPerformed(ActionEvent event) 
			{
				if(myComponent!=null)
				{
					var mp = new MyParallelogram();
					myComponent.resetColor();
					myDocument.update(mp);
					myComponent.update(myDocument);
					repaint();
				}
			}  
		}); 
		rect.addActionListener(new ActionListener() 
		{  
			public void actionPerformed(ActionEvent event) 
			{
				if(myComponent!=null)
				{
					var mr = new MyRectangle();
					myComponent.resetColor();
					myDocument.update(mr);
					myComponent.update(myDocument);
					repaint();  
				}
			}  
		}); 
		diam.addActionListener(new ActionListener() 
		{  
			public void actionPerformed(ActionEvent event) 
			{
				if(myComponent!=null)
				{
					var md = new MyDiamond();
					myComponent.resetColor();
					myDocument.update(md);
					myComponent.update(myDocument);
					repaint();
				}
			}  
		}); 
		cir.addActionListener(new ActionListener() 
		{  
			public void actionPerformed(ActionEvent event) 
			{
				if(myComponent!=null)
				{
					var mc = new MyCircle();
					myComponent.resetColor();
					myDocument.update(mc);
					myComponent.update(myDocument);
					repaint(); 
				}
			}  
		});
		//代码菜单栏监听
		run.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event) 
			{
				var shapeExecute = new ShapeExecute();
				myDocument.setExecute(new CodeExecute());
				shapeExecute.setStartEnd(myComponent.getStart(), myComponent.getEnd());
				shapeExecute.execute(myComponent,myDocument);
			} 
		});
		generate.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				var shapeGenerate = new ShapeGenerate();
				myDocument.setGenerate(new CodeGenerate());
				shapeGenerate.setStartEnd(myComponent.getStart(), myComponent.getEnd());
				shapeGenerate.setDoc(myDocument);
				shapeGenerate.write();
			}
		});

		//操作菜单栏监听
		delete.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event) 
			{
				myComponent.resetColor();
				if(myComponent!=null)
					myComponent.delete();
			} 
		});
	}
}
