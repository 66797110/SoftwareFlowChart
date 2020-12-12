package pers.sfc.execute;

import java.awt.Component;

import pers.sfc.shapes.MyCircle;
import pers.sfc.shapes.MyDiamond;
import pers.sfc.shapes.MyParallelogram;
import pers.sfc.shapes.MyRectangle;
import pers.sfc.shapes.MyRoundRectangle;

public interface Execute {
	public boolean codeExecution(MyRoundRectangle roundrectangle);
	public boolean codeExecution(MyRectangle rectangle);
	public boolean codeExecution(MyParallelogram parallelogram,Component parent,boolean InOut);
	public int codeExecution(MyDiamond diamond);
	public boolean codeExecution(MyCircle circle);
}
