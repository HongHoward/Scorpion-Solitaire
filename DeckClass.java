import java.util.*;  // Vector class is in the 'util'  package
import java.awt.*;

public class DeckClass extends ShapeClass
{
    private Vector vec = new Vector (0, 1);
    private char deckType;
    public DeckClass ()
    {
	super (); // instantiate as a shape
	deckType = ' ';
	setHeight (80);
	setWidth ((int) (getHeight () / 1.25));
    }


    public DeckClass (char s)
    {
	super ();
	deckType = 's';
	setHeight (80);
	setWidth ((int) (getHeight () / 1.25));
	if (deckType == 's') // standard vec, THERE SHOULD ONLY BE ONE DECK THAT IS STANDARD
	{
	    for (int i = 1 ; i <= 4 ; i++)
	    {
		for (int f = 1 ; f <= 13 ; f++)
		{
		    CardClass tCard = new CardClass ();
		    tCard.setCentre (getCentreX (), getCentreY ());
		    tCard.setSuit (i);
		    tCard.setVal (f);
		    tCard.setColor (getColor ());
		    vec.addElement (tCard);
		}
	    }
	}
    }


    public int getNumCard ()
    {
	return vec.size ();
    }


    public char getDeckType ()
    {
	return deckType;
    }


    public boolean checkStraight ()
    {
	if (getNumCard () != 13)
	{
	    return false;
	}
	CardClass tCard1;
	CardClass tCard2;
	for (int i = 0 ; i < getNumCard () - 1 ; i++)
	{
	    tCard1 = getCard (i);
	    tCard2 = getCard (i + 1);
	    if (tCard1.getVal () - 1 != tCard2.getVal () || tCard1.getSuit () != tCard2.getSuit ())
	    {
		return false;
	    }
	}
	return true;
    }


    public void addCard (CardClass cardToAdd, int Pos)
    {
	if (Pos == 0 || vec.size () == 0)
	{
	    vec.addElement (cardToAdd);
	}
	else if (Pos > vec.size ())
	{
	    vec.insertElementAt (cardToAdd, vec.size ());
	}
	else
	{
	    vec.insertElementAt (cardToAdd, Pos);
	}
    }


    // Debugging Method
    // public Vector retVec ()
    // {
    //     return vec;
    // }


    public CardClass dealCard (int Pos)
    {
	if (Pos < 0)
	{
	    return (CardClass) vec.remove (0);
	}
	else if (Pos > vec.size ())
	{
	    return (CardClass) vec.remove (vec.size ());
	}
	else
	{
	    return (CardClass) vec.remove (Pos);   // must type cast element
	}
    }


    public void addDeck (DeckClass deckToAdd)
    {
	int temp = deckToAdd.getNumCard ();

	for (int i = 0 ; i < temp ; i++)
	{
	    addCard (deckToAdd.dealCard (0), vec.size ());
	}
    }


    public void addDeck (DeckClass deckToAdd, int pos)
    {
	int temp = deckToAdd.getNumCard ();

	for (int i = 0 ; i < temp - pos ; i++)
	{
	    addCard (deckToAdd.dealCard (pos), vec.size ());
	}
    }


    public CardClass getCard (int Pos)  // returns card, doesnt remove
    {
	return (CardClass) vec.elementAt (Pos);
    }


    public boolean isPointInDeck (int x, int y)
    {
	boolean isInDeck = false;
	if (deckType == ' ')
	{
	    if (vec.size () > 0)
	    {
		if (
			(x >= getCentreX () - getWidth () / 2) &&
			(x <= getCentreX () + getWidth () / 2) &&
			(y >= getCentreY () - getHeight () / 2) &&
			(y <= getCentreY () + getHeight () / 2 + (vec.size () - 1) * 50)
			)
		{
		    isInDeck = true;
		}
	    }
	    else if (vec.size () == 0)
	    {
		if (
			(x >= getCentreX () - getWidth () / 2) &&
			(x <= getCentreX () + getWidth () / 2) &&
			(y >= getCentreY () - getHeight () / 2) &&
			(y <= getCentreY () + getHeight () / 2)
			)
		{
		    isInDeck = true;
		}
	    }
	}
	else if (deckType == 's')
	{
	    if (
		    (x >= getCentreX () - getWidth () / 2) &&
		    (x <= getCentreX () + getWidth () / 2 + (vec.size () - 1) * 10) &&
		    (y >= getCentreY () - getHeight () / 2) &&
		    (y <= getCentreY () + getHeight () / 2)
		    )
	    {
		isInDeck = true;
	    }
	}
	return isInDeck;

    }


    public int getPos (int x, int y)
    {
	// returns the card given a coordinate
	// 1: check if point is in the deck area
	// 2: determine card #
	int position = -1;
	if (isPointInDeck (x, y) == true && vec.size () != 0) // if its in the deck and the deck is not empty
	{
	    for (int i = 0 ; i < vec.size () ; i++) // for i = 0 to vector size
	    {
		if (deckType == ' ')
		{
		    if (i != vec.size () - 1) // if i isnt the limit and position hasnt been found
		    {
			// position algorithm
			if (
				(x >= getCard (i).getCentreX () - getCard (i).getWidth () / 2) &&
				(x <= getCard (i).getCentreX () + getCard (i).getWidth () / 2) &&
				(y >= getCard (i).getCentreY () - getCard (i).getHeight () / 2) &&
				(y <= getCard (i).getCentreY () + getCard (i).getHeight () / 2 - 30)
				)
			    // if the card is in the card area
			    {
				position = i;
			    }
		    }
		    else if (i == vec.size () - 1)
		    {
			if (
				(x >= getCard (i).getCentreX () - getCard (i).getWidth () / 2) &&
				(x <= getCard (i).getCentreX () + getCard (i).getWidth () / 2) &&
				(y >= getCard (i).getCentreY () - getCard (i).getHeight () / 2) &&
				(y <= getCard (i).getCentreY () + getCard (i).getHeight () / 2)
				)
			{
			    position = i;
			}
		    }
		}
		else if (deckType == 's')
		{
		    if (
			    (x >= getCard (i).getCentreX () - getCard (i).getWidth () / 2) &&
			    (x <= getCard (i).getCentreX () + getCard (i).getWidth () / 2) &&
			    (y >= getCard (i).getCentreY () - getCard (i).getHeight () / 2) &&
			    (y <= getCard (i).getCentreY () + getCard (i).getHeight () / 2)
			    )
		    {
			position = i;
		    }
		}
	    }
	}
	return position;
    }


    public void shuffle ()
    {
	CardClass tCard = new CardClass ();
	for (int i = 0 ; i < 52 ; i++)
	{
	    tCard = dealCard ((int) (Math.random () * vec.size ()));
	    addCard (tCard, (int) (Math.random () * vec.size ()));
	}
    }


    public void draw (Graphics g)
    {
	Color tClr = getColor ();
	if (vec.size () != 0 && deckType == ' ')
	{
	    CardClass nCard = new CardClass ();
	    for (int i = 0 ; i < vec.size () ; i++)
	    {
		nCard = getCard (i); // vec.size returns the # of cards, but getCard counts from 0 - vec.size().
		nCard.setColor (tClr);
		nCard.setCentre (getCentreX (), getCentreY () + (i * 40));
		nCard.setHeight (getHeight ());
		nCard.setWidth (getWidth ());
		nCard.draw (g);
	    }
	}
	else if (vec.size () != 0 && deckType == 's')
	{
	    CardClass nCard = new CardClass ();
	    for (int i = 0 ; i < vec.size () ; i++)
	    {
		nCard = getCard (i);
		nCard.setColor (tClr);
		nCard.setCentre (getCentreX () + (i * 10), getCentreY ());
		nCard.setHeight (getHeight ());
		nCard.setWidth (getWidth ());
		nCard.draw (g);
	    }
	}
    }
}


