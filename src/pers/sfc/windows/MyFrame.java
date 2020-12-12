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
import pers.sfc.execute.ShapeExecute;
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
	private CodeExecute codeExecute;
	private ShapeExecute shapeExecute;
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
		//��ʼ����������
		codeExecute = new CodeExecute();
		shapeExecute = new ShapeExecute();
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
		//add(myComponent);  
		//setVisible(true);
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
					myFileInOut.save(myDocument.getList());
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
				var mrr = new MyRoundRectangle();
				//mrr.setExecute(codeExecute);
				myDocument.update(mrr);
				myComponent.update(myDocument);
				repaint();  
			}  
		}); 
		para.addActionListener(new ActionListener() 
		{  
			public void actionPerformed(ActionEvent event) 
			{
				var mp = new MyParallelogram();
				//mp.setExecute(codeExecute);
				myDocument.update(mp);
				myComponent.update(myDocument);
				repaint();  
			}  
		}); 
		rect.addActionListener(new ActionListener() 
		{  
			public void actionPerformed(ActionEvent event) 
			{
				var mr = new MyRectangle();
				//mr.setExecute(codeExecute);
				myDocument.update(mr);
				myComponent.update(myDocument);
				repaint();  
			}  
		}); 
		diam.addActionListener(new ActionListener() 
		{  
			public void actionPerformed(ActionEvent event) 
			{
				var md = new MyDiamond();
				//md.setExecute(codeExecute);
				myDocument.update(md);
				myComponent.update(myDocument);
				repaint();  
			}  
		}); 
		cir.addActionListener(new ActionListener() 
		{  
			public void actionPerformed(ActionEvent event) 
			{
				var mc = new MyCircle();
				//mc.setExecute(codeExecute);
				myDocument.update(mc);
				myComponent.update(myDocument);
				repaint();  
			}  
		});

		//����˵�������
		run.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event) 
			{
				codeExecute = new CodeExecute();
				myDocument.setExecute(codeExecute);
				shapeExecute.setStartEnd(myComponent.getStart(), myComponent.getEnd());
				shapeExecute.execute();
			} 
		});
		//�����˵�������
		delete.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event) 
			{
				if(myComponent!=null)
					myComponent.delete();
			} 
		});
	}
}
