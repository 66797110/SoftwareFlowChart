package pers.sfc.execute;

import pers.sfc.shapes.MyArrow;
import pers.sfc.shapes.Shape;

public class ShapeExecute {
	private Shape start;
	private Shape end;
	private Shape current;
	//private MyArrow arrow;
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
	public void execute()
	{
		for(current = start;!current.equals(end);current = current.getNext())
		{
			if(!current.codeRun())
				return;
		}
		if(!current.codeRun())
			return;
	}
}
