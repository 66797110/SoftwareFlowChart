package pers.sfc.shapes;

public class MyPoint {
	private double x;   //ºá×ø±ê
	private double y;   //×Ý×ø±ê

	public MyPoint(double x,double y)
	{
		this.x = x;
		this.y = y;
	}

	public MyPoint()
	{
		this(200,200);
	}

	public double getX()
	{
		return this.x;
	}

	public double getY()
	{
		return this.y;
	}
	public void Offset(double cx,double cy)
	{
		this.x += cx;
		this.y += cy;
	}
	public String writeObject()
	{
		//String thisClass = "@Class=\""+this.getClass().toString()+"\"\n";
		String thisClass = " @MyPoint";
		String thisData = " @x = "+this.x+" @y = "+this.y+" ";
		return "</"+thisClass+thisData+"/>";
	}
}
