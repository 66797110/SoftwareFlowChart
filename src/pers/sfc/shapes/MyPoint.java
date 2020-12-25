package pers.sfc.shapes;

public class MyPoint {
	private double x;   //横坐标
	private double y;   //纵坐标
    
	//构造函数
	public MyPoint(double x,double y)
	{
		this.x = x;
		this.y = y;
	}

	public MyPoint()
	{
		this(200,200);
	}
    //获得横坐标
	public double getX()
	{
		return this.x;
	}
    //获得纵坐标
	public double getY()
	{
		return this.y;
	}
	//移动
	public void Offset(double cx,double cy)
	{
		this.x += cx;
		this.y += cy;
	}
}
