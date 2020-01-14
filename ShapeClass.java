import java.awt.*;

public abstract class ShapeClass
{
    private int iHeight;
    private int iWidth;
    private int iCentreX;
    private int iCentreY;
    private Color iColor;

    public abstract void draw (Graphics g);

    public ShapeClass ()
    {
	iHeight = 100;
	iWidth = 100;
	iCentreX = 100;
	iCentreY = 100;
	iColor = Color.black;
    }


    public ShapeClass (Color c)
    {
	this ();
	iColor = c;
    }


    public void setCentre (int Cx, int Cy)
    {
	iCentreX = Cx;
	iCentreY = Cy;

    }


    public void setWidth (int nW)
    {
	iWidth = nW;
    }


    public void setHeight (int nH)
    {
	iHeight = nH;
    }


    public void setColor (Color nC)
    {
	iColor = nC;
    }


    public int getHeight ()
    {
	return iHeight;
    }


    public int getWidth ()
    {
	return iWidth;
    }


    public int getCentreX ()
    {
	return iCentreX;
    }


    public int getCentreY ()
    {
	return iCentreY;
    }


    public Color getColor ()
    {
	return iColor;
    }


    public void delay (int iDelayTime)
    {
	long lFinalTime = System.currentTimeMillis () + iDelayTime;
	do
	{
	}
	while (lFinalTime >= System.currentTimeMillis ());
    }
}
