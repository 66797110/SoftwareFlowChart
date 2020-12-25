package pers.sfc.shapes;

public class MyPoint {
	private double x;   //������
	private double y;   //������
    
	//���캯��
	public MyPoint(double x,double y)
	{
		this.x = x;
		this.y = y;
	}

	public MyPoint()
	{
		this(200,200);
	}
    //��ú�����
	public double getX()
	{
		return this.x;
	}
    //���������
	public double getY()
	{
		return this.y;
	}
	//�ƶ�
	public void Offset(double cx,double cy)
	{
		this.x += cx;
		this.y += cy;
	}
}
