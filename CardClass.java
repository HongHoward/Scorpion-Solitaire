//CardClass
import java.awt.*;
import hsa.Console;

public class CardClass extends ShapeClass
{
    private int suit;     // suit
    private int fVal;     // face val
    private boolean fDown;

    public CardClass ()
    {
	super ();
	setWidth ((int) (getHeight () / 1.25));
	suit = 1;
	fVal = 1;
	fDown = false;
    }


    public void setSuit (int nS)
    {
	if (nS < 1)
	{
	    nS = 1;
	}
	else if (nS > 4)
	{
	    suit = 4;
	}
	else
	{
	    suit = nS;
	}
    }


    public void setState (boolean f)
    {
	fDown = f;
    }


    public void setVal (int nV)
    {
	if (nV < 1)
	{
	    fVal = 1;
	}
	else if (nV > 13)
	{
	    fVal = 13;
	}
	else
	{
	    fVal = nV;
	}
    }


    public boolean isPointInside (int x, int y)
    {
	if ((x >= getCentreX () - getWidth () / 2) && (x <= getCentreX () + getWidth () / 2)
		&& (y >= getCentreY () - getHeight () / 2) && (y <= getCentreY () + getHeight () / 2))
	{
	    return true;
	}
	return false;

    }



    public boolean getState ()
    {
	return fDown;
    }


    public int getSuit ()
    {
	return suit;
    }


    public int getVal ()
    {
	return fVal;
    }


    public void draw (Graphics g)
    {
	Font f1 = new Font ("SanSerif", Font.BOLD, 20);
	g.setFont (f1);
	int TLx = getCentreX () - getWidth () / 2;
	int TLy = getCentreY () - getHeight () / 2;
	Color tClr = getColor ();

	if (fDown == false)
	{
	    g.setColor (Color.white);
	    g.fillRect (TLx, TLy, getWidth (), getHeight ());
	    g.setColor (Color.black);
	    g.drawRect (TLx, TLy, getWidth (), getHeight ());

	    if (getSuit () == 1) // diamond
	    {
		DiamondClass dia = new DiamondClass ();
		setColor (tClr);
		dia.setCentre (TLx + getWidth () / 2, TLy + getHeight () / 2);
		dia.draw (g);
	    }
	    else if (getSuit () == 2) // club
	    {
		ClubClass club = new ClubClass ();
		club.setCentre (TLx + getWidth () / 2, TLy + getHeight () / 2);
		club.draw (g);

	    }
	    else if (getSuit () == 3) // heart
	    {
		HeartClass heart = new HeartClass ();
		heart.setCentre (TLx + getWidth () / 2, TLy + getHeight () / 2);
		heart.draw (g);

	    }
	    else if (getSuit () == 4) // spade
	    {
		SpadeClass spade = new SpadeClass ();
		spade.setCentre (TLx + getWidth () / 2, TLy + getHeight () / 2);
		spade.draw (g);
	    }
	    g.setColor (Color.black);
	    if (fVal == 1)
	    {
		g.drawString ("A", TLx + getWidth () / 8, TLy + getHeight () / 4);
	    }

	    else if (fVal == 11)
	    {
		g.drawString ("J", TLx + getWidth () / 8, TLy + getHeight () / 4);
	    }

	    else if (fVal == 12)
	    {
		g.drawString ("Q", TLx + getWidth () / 8, TLy + getHeight () / 4);
	    }

	    else if (fVal == 13)
	    {
		g.drawString ("K", TLx + getWidth () / 8, TLy + getHeight () / 4);
	    }
	    else
	    {
		g.drawString (Integer.toString (fVal), TLx + getWidth () / 8, TLy + getHeight () / 4);
	    }


	}


	else
	{
	    Color tColor = getColor ();
	    g.setColor (Color.white);
	    g.fillRect (TLx, TLy, getWidth (), getHeight ());
	    g.setColor (Color.black);
	    g.fillRect (TLx + getWidth () / 7, TLy + getHeight () / 7, getWidth () - getWidth () / 4, getHeight () - getHeight () / 4);
	    g.drawRect (TLx, TLy, getWidth (), getHeight ());
	    g.setColor (tColor);
	}
    }



}


