//DiamondClass.java

import hsa.Console;
import java.awt.*;

public class DiamondClass extends SuitClass
{
    public DiamondClass ()
    {
	super(Color.red);
    }


    public void draw (Graphics g)
    {
	// declare two arrays for X & Y coordinates of Diamond
	int iPointsX[] = new int [4];
	int iPointsY[] = new int [4];

	// calculate points on diamond & store in the arrays
	iPointsX [0] = getCentreX () - getWidth () / 2;
	iPointsY [0] = getCentreY ();
	iPointsX [1] = getCentreX ();
	iPointsY [1] = getCentreY () - getHeight () / 2;
	iPointsX [2] = getCentreX () + getWidth () / 2;
	iPointsY [2] = getCentreY ();
	iPointsX [3] = getCentreX ();
	iPointsY [3] = getCentreY () + getHeight () / 2;

	// draw the diamond using methods available from the Console object (c)
	if (getColor() != Color.white)
	{
	    g.setColor (Color.red);
	}
	else
	{
	    g.setColor (Color.white);
	}
	g.fillPolygon (iPointsX, iPointsY, 4);

    }
}


