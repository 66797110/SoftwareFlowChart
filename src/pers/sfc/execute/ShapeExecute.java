package pers.sfc.execute;

import java.awt.Color;

import pers.sfc.shapes.Shape;
import pers.sfc.windows.MyComponent;
import pers.sfc.windows.MyDocument;

public class ShapeExecute {
	private Shape start;
	@SuppressWarnings("unused")
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
	public void execute(MyComponent myComponent,MyDocument myDocument)
	{
		for(current = start;current!=null;current = current.getNext(myDocument))
		{
			current.setColorOn(Color.RED);
			myDocument.move(current);
			myComponent.repaint();
			if(current!=null&&current.getClass().getName().equals("pers.sfc.shapes.MyCircle"))
			{
				for(Shape s;(s = myDocument.getNext())!=null;)
					if(!s.equals(current)&&s.getClass().getName().equals("pers.sfc.shapes.MyCircle")&&current.getCode().equals(s.getCode()))
					{
						s.setColorOn(Color.RED);
						myComponent.repaint();
						current = s;
						myDocument.reset();
						break;
					}
			}
			else if(current!=null&&!current.codeRun())
			{
				//myComponent.resetColor();
				//myComponent.repaint();
				return;
			}
		}
	}
}
