package pers.sfc.execute;

import java.util.ArrayList;
import java.util.Collections;

import pers.sfc.shapes.MyCircle;
import pers.sfc.shapes.MyDiamond;
import pers.sfc.shapes.MyParallelogram;
import pers.sfc.shapes.MyRectangle;
import pers.sfc.shapes.MyRoundRectangle;

public class CodeGenerate{
	private String code;
	public String codeGenerate(MyRoundRectangle roundrectangle,int startWidth) {
		return code = roundrectangle.getCode();
		//if(code.equals("start"))
			//return true;
		//return false;
	}

	public String codeGenerate(MyRectangle rectangle,int startWidth) {
		code = rectangle.getCode(); 
		String [] arr = code.split("\n");
		code = "";
		String space = String.join("", Collections.nCopies(startWidth, "	"));
		for(int i=0;i<arr.length;i++)
			code = code + space + arr[i] + "\n";
		return code;
	}

	public String codeGenerate(MyParallelogram parallelogram,int startWidth) {
		code = parallelogram.getCode();
		String [] arr = code.split("\\s+");
		code = "";
		String space = String.join("", Collections.nCopies(startWidth, "	"));
		if(arr[0].equals("int")||arr[0].equals("double")||arr[0].equals("float"))
		{
			code = code+space+"Scanner sc = new Scanner(System.in);\n";
			if(arr[0].equals("int"))
				code = code+space+arr[0]+" "+arr[1]+"="+"sc.nextInt();\n";
			else if(arr[0].equals("float"))
				code = code+space+arr[0]+" "+arr[1]+"="+"sc.nextFloat();\n";
			else if(arr[0].equals("double"))
				code = code+space+arr[0]+" "+arr[1]+"="+"sc.nextDouble();\n";
		}
		else
			code = code+space+"System.out.println("+arr[0]+");\n";
		return code;
	}

	public String codeGenerate(MyDiamond diamond,int startWidth) {
		return code = diamond.getCode(); 
		//String initCode = diamond.getCode();
		//code = "";
		/*
		String space = String.join("", Collections.nCopies(startWidth, "	"));
		if(type)//whileÑ­»·
		{
			code = code+space+"while("+initCode+")";
		}
		else
		{
			code = code+space+"if()"
		}
		*/
	}

	public String codeGenerate(MyCircle circle,int startWidth) {
		return code = circle.getCode();
	}

}
