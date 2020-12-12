package pers.sfc.execute;

public class Variable {
	private String name;
	private Type type;
	private Double var;
	private Boolean bvar;
	Variable(String name,String type,double var)
	{
		this.name = name;
		if(type.equals("int"))
			this.type = Type.INT;
		else if(type.equals("float"))
			this.type = Type.FLOAT;
		else if(type.equals("double"))
			this.type = Type.DOUBLE;
		else if(type.equals("boolean"))
			this.type = Type.BOOLEAN;
		this.var = var;
	}
	Variable(String name,String type)
	{
		this.name = name;
		if(type.equals("int"))
			this.type = Type.INT;
		else if(type.equals("float"))
			this.type = Type.FLOAT;
		else if(type.equals("double"))
			this.type = Type.DOUBLE;
		else if(type.equals("boolean"))
			this.type = Type.BOOLEAN;
		this.var = Double.NaN;
		this.bvar = null;
	}
	@SuppressWarnings("deprecation")
	public void setVar(double var)
	{
		if(this.type.equals(Type.INT))
			this.var = new Double((int)var);
		else if(this.type.equals(Type.FLOAT))
			this.var = new Double((float)var);
		else if(this.type.equals(Type.DOUBLE))
			this.var = new Double(var);
	}
	public void setVar(boolean var)
	{
		this.bvar = var;
	}
	public String getName()
	{
		return this.name;
	}
	public Type getType()
	{
		return this.type;
	}
	public double getVar()
	{
		return this.var.doubleValue();
	}
	public boolean getBVar() 
	{
		return this.bvar.booleanValue();
	}
}
