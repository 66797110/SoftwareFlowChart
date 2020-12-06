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
	public MyFrame()
	{
		//��ʼ������
		myComponent = new MyComponent();
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
		//���˵����ͻ��������
		setLayout(new BorderLayout());  
		setJMenuBar(bar);
		add(myComponent);  
		setVisible(true);
		//�ļ��˵�������
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
					//JOptionPane.showMessageDialog(null, "����", "���ļ����ʹ���", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});
		*/

		//ͼ�β˵�������
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
