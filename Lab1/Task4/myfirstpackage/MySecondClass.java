package myfirstpackage;

public class MySecondClass{
	private int a;
	private int b;
	public MySecondClass(final int a, final int b)
	{
		this.a = a;
		this.b = b;
	}
	public int getA()
	{
		return a;
	}
	public int getB()
	{
		return b;
	}
	public void setA(final int a){
	this.a = a;
	}
	public void setB(final int b){
	this.b = b;
	}
	public int divide()
	{
		return a / b;
	}
}