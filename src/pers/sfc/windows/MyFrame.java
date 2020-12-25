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
	//ͼ���ļ�
	private MyDocument myDocument;
	//�������
	private MyFileInOut myFileInOut;
	//����
	private MyComponent myComponent;
	//�˵�
	private JMenuBar bar;
	//�ļ��˵�
	private JMenu file;
	private JMenuItem create;
	private JMenuItem open;
	private JMenuItem save;
	private JMenuItem saveAs;
	//ͼ�β˵�
	private JMenu shape;
	private JMenuItem roundRect;
	private JMenuItem para;
	private JMenuItem rect;
	private JMenuItem diam;
	private JMenuItem cir;
	//����˵�
	private JMenu code;
	private JMenuItem run;
	private JMenuItem generate;
	//����
	private JMenu operate;
	private JMenuItem delete;

	public MyFrame()
	{
		//��ʼ���ļ�
		myDocument = new MyDocument();
		//��ʼ���������
		myFileInOut = new MyFileInOut();
		//��ʼ���˵���
		bar = new JMenuBar();
		//��ʼ���ļ��˵�
		file = new JMenu("�ļ�");
		create = new JMenuItem("�½�");
		open = new JMenuItem("��");
		save = new JMenuItem("����");
		saveAs = new JMenuItem("���Ϊ");
		//��ʼ��ͼ�β˵�
		shape = new JMenu("ͼ��");
		roundRect = new JMenuItem("��ֹ��");
		para = new JMenuItem("����/����");
		rect = new JMenuItem("�����");
		diam = new JMenuItem("�жϿ�");
		cir = new JMenuItem("���ӵ�");
		//��ʼ������˵�
		code = new JMenu("����");
		run = new JMenuItem("����");
		generate = new JMenuItem("����");
		//��ʼ�������˵�
		operate = new JMenu("����");
		delete = new JMenuItem("ɾ��");
		//���ļ��˵���ӽ��˵���
		file.add(create);
		file.add(open);
		file.add(save);
		file.add(saveAs);
		bar.add(file);
		//��ͼ�β˵���ӽ��˵���
		shape.add(roundRect);
		shape.add(para);
		shape.add(rect);
		shape.add(diam);
		shape.add(cir);
		bar.add(shape);
		//������˵���ӽ��˵���
		code.add(run);
		code.add(generate);
		bar.add(code);
		//�������˵���ӽ��˵���
		operate.add(delete);
		bar.add(operate);
		//���˵����ͻ��������
		setLayout(new BorderLayout());  
		setJMenuBar(bar);
		//�ļ��˵�������
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
					//JOptionPane.showMessageDialog(null, "����", "���ļ����ʹ���", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});


		//ͼ�β˵�������
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
		//����˵�������
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

		//�����˵�������
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
