package pers.sfc.execute;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import pers.sfc.shapes.MyDiamond;
import pers.sfc.shapes.Shape;
import pers.sfc.windows.MyDocument;

public class ShapeGenerate {
	private Shape start;
	@SuppressWarnings("unused")
	private Shape end;
	private String code;
	private MyDocument myDocument;
	public void setStart(Shape start)
	{
		this.start = start;
	}
	public void setEnd(Shape end)
	{
		this.end = end;
	}
	public void setStartEnd(Shape start,Shape end)
	{
		this.start = start;
		this.end = end;
	}
	public void setDoc(MyDocument myDocument)
	{
		this.myDocument = myDocument;
	}
	public void write()
	{
		var fc = new JFileChooser();
		fc.setCurrentDirectory(new File("D:\\eclipse-workspace\\Code\\src"));//默认打开位置
		fc.setDialogTitle("保存文件");//标题
		fc.setSelectedFile(new File("Main.java"));//默认文件名
		fc.showSaveDialog(null);
		String FileName = fc.getSelectedFile().getAbsolutePath();//获得稳健绝对路径
		String line = System.getProperty("line.separator");
		generate();
		String [] ch = code.split("\n");
		if(FileName.endsWith(".java"))
		{
			File file = new File(FileName);
			try {
				FileWriter out = new FileWriter(file);
				for(int i = 0;i<ch.length;i++)
				{
					out.write(ch[i]);
					out.write(line);
				}
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		JOptionPane.showMessageDialog(null, "请使用java类型进行保存", "错误", JOptionPane.ERROR_MESSAGE);
	}
	private void generate()
	{
		int startWidth = 1;
		code = "";
		code = code+("import java.util.Scanner;\n \npublic class Main { \n"+String.join("", Collections.nCopies(startWidth, "	"))+"public static void main(String[] args) { \n");
		startWidth++;
		code = baseCodeGen(start.getNext(myDocument),startWidth,code);
		startWidth--;
		code = code+String.join("", Collections.nCopies(startWidth, "	"))+"}\n}";
	}
	//分类生成函数
	private String baseCodeGen(Shape current,int startWidth,String code)
	{
		for(;current!=null;current = current.getNext(myDocument))
		{
			if(current.codeGen(startWidth).equals("end"))
				return code;
			if(current!=null&&current.getClass().getName().equals("pers.sfc.shapes.MyCircle"))
			{
				for(Shape s;(s = myDocument.getNext())!=null;)
					if(s.getClass().getName().equals("pers.sfc.shapes.MyCircle")&&current.getCode().equals(s.getCode()))
					{
						current = s;
						myDocument.reset();
						break;
					}
				continue;
			}
			if(current.getClass().getName().equals("pers.sfc.shapes.MyDiamond"))
			{
				if(((MyDiamond)current).equals(((MyDiamond)current).getTNext(myDocument).getNext(myDocument)))
				{
					code = code+String.join("", Collections.nCopies(startWidth, "	"))+
							"while("+current.codeGen(startWidth)+") {\n";
					startWidth++;
					code = code+((MyDiamond)current).getTNext(myDocument).codeGen(startWidth);
					startWidth--;
					code = code+String.join("", Collections.nCopies(startWidth, "	"))+"} \n";
					((MyDiamond)current).setJudge(0);
					//current = ((MyDiamond)current).getFNext();
					continue;
				}
				else if(((MyDiamond)current).equals(((MyDiamond)current).getFNext(myDocument).getNext(myDocument)))
				{
					code = code+String.join("", Collections.nCopies(startWidth, "	"))+
							"while(!("+current.codeGen(startWidth)+")) {\n";
					startWidth++;
					code = code+((MyDiamond)current).getFNext(myDocument).codeGen(startWidth);
					startWidth--;
					code = code+String.join("", Collections.nCopies(startWidth, "	"))+"} \n";
					((MyDiamond)current).setJudge(1);
					//current = ((MyDiamond)current).getTNext();
					continue;
				}
				else if(((MyDiamond)current).getTNext(myDocument).getNext(myDocument)!=null&&
						((MyDiamond)current).getFNext(myDocument).getNext(myDocument)!=null&&
						((MyDiamond)current).getTNext(myDocument).getNext(myDocument).equals(((MyDiamond)current).getFNext(myDocument).getNext(myDocument)))
				{
					code = code+String.join("", Collections.nCopies(startWidth, "	"))+
							"if("+current.codeGen(startWidth)+") {\n";
					startWidth++;
					code = code+((MyDiamond)current).getTNext(myDocument).codeGen(startWidth);
					startWidth--;
					code = code+String.join("", Collections.nCopies(startWidth, "	"))+"} \n";
					code = code+String.join("", Collections.nCopies(startWidth, "	"))+"else {\n";
					startWidth++;
					code = code+((MyDiamond)current).getFNext(myDocument).codeGen(startWidth);
					startWidth--;
					code = code+String.join("", Collections.nCopies(startWidth, "	"))+"} \n";
					current = ((MyDiamond)current).getTNext(myDocument);
					continue;
				}
				else
				{
					Shape tCurrent = ((MyDiamond)current).getTNext(myDocument);
					Shape fCurrent = ((MyDiamond)current).getFNext(myDocument);
					code = code+String.join("", Collections.nCopies(startWidth, "	"))+
							"if("+current.codeGen(startWidth)+") {\n";
					startWidth++;
					code = baseCodeGen(tCurrent,startWidth,code);
					startWidth--;
					code = code+String.join("", Collections.nCopies(startWidth, "	"))+"} \n";
					code = code+String.join("", Collections.nCopies(startWidth, "	"))+"else {\n";
					startWidth++;
					code = baseCodeGen(fCurrent,startWidth,code);
					startWidth--;
					code = code+String.join("", Collections.nCopies(startWidth, "	"))+"} \n";
					((MyDiamond)current).setJudge(-1);
				}
			}
			else
				code = code+current.codeGen(startWidth);
		}
		return code;
	}
}
