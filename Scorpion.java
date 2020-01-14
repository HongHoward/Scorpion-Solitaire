// The "DiamondApplet" class.

//=====================================
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Scorpion extends Applet implements ActionListener, MouseListener, MouseMotionListener
{
    private DeckClass[] piles; // all piles, including stock and foundation
    private DeckClass dragPile; // pile for dragging
    private int diffx, diffy, orig, score; // diffx/y for dragging, orig for original deck, score for score.
    private Graphics g;   // declares a graphics canvas for drawing
    private Button buttonNewGame = new Button ("New Game"); // buttons
    private Button buttonEndGame = new Button ("End Game");
    private Button buttonAutoWin = new Button ("Auto Win"); // remove when game is finished, used for debugging win condition.
    private Label labelAction = new Label ("Action"); // also remove when game is finished
    private TextField textFieldAction = new TextField (15); // along with ^
    private boolean OKtoMove = false; // check if the pile is OK to move
    private boolean win = false; // if win = true game is finished and won.
    private BorderLayout layout = new BorderLayout ();
    private Panel nPanel = new Panel ();


    //
    public void init ()
    {
	g = getGraphics (); // gets canvas created by browser-
	setSize (1000, 1000); //  set the size of the applet
	setBackground (new Color (0, 115, 0)); // changes color of BG to green
	setLayout (layout);
	nPanel.add (buttonNewGame);
	nPanel.add (buttonEndGame);
	nPanel.add (buttonAutoWin);
	buttonNewGame.addActionListener (this);
	buttonEndGame.addActionListener (this);
	buttonAutoWin.addActionListener (this);
	addMouseListener (this);
	addMouseMotionListener (this);
	add ("North", nPanel);
	reset (); // resets the game
    }


    public void reset ()
    {
	score = 0; // score is equivalent to number of moves made
	piles = new DeckClass [12]; // setting 12 piles up
	dragPile = new DeckClass ();
	if (win == false)
	{
	    addMouseListener (this);
	    addMouseMotionListener (this);
	}
	win = false;

	//
	for (int i = 0 ; i < 12 ; i++) // i = 1 to ignore the stock
	{
	    // deck 0 is stock, 1-3 is playing pile with top 3 hidden, 4-7 is playing pile, 8-11 is empty (finished pile)
	    piles [i] = new DeckClass ();


	    if (i == 0)
	    {
		piles [i] = new DeckClass ('s'); // creates standard deck
		piles [i].shuffle ();           // shuffle
		piles [i].setCentre (piles [i].getCentreX () + 80 * i, piles [i].getCentreY ());
	    }
	    if (i > 0 && i < 12)
	    {

		if (i > 0 && i < 8)
		{
		    for (int s = 0 ; s < 7 ; s++)
		    {
			CardClass tCard = new CardClass ();
			tCard = piles [0].dealCard (0);
			if (s < 3 && i < 4)
			{
			    tCard.setState (true);
			}
			piles [i].addCard (tCard, 0);
		    }
		}
		piles [i].setCentre (piles [i].getCentreX () + 80 * (i - 1), piles [i].getCentreY () + piles [i].getHeight () + 40);
	    }
	}
	DeckClass tempPile = new DeckClass ();
	CardClass tempCard = new CardClass ();
	for (int i = 0 ; i < 3 ; i++)
	{
	    tempCard = piles [0].dealCard (0);
	    tempCard.setState (true);
	    tempPile.addCard (tempCard, 0);
	}
	for (int i = 0 ; i < 3 ; i++)
	{
	    piles [0].addCard (tempPile.dealCard (0), 0);
	}
    }


    public void update (Graphics g)  // double buffering, overrides the update function
    {
	Graphics offscreengraphics;
	Image offscreen = null;
	Dimension d = size ();
	// creates a screen offscreen
	offscreen = createImage (d.width, d.height);
	offscreengraphics = offscreen.getGraphics ();

	// clear the screen
	offscreengraphics.setColor (getBackground ());
	offscreengraphics.fillRect (0, 0, d.width, d.height);
	offscreengraphics.setColor (getForeground ());

	// draw everything
	paint (offscreengraphics);

	// move the image from off the screen to current screen
	g.drawImage (offscreen, 0, 0, this);
    }


    public void paint (Graphics g)
    {
	for (int i = 0 ; i <= 11 ; i++)
	{
	    g.drawRect (piles [i].getCentreX () - piles [i].getWidth () / 2, piles [i].getCentreY () - piles [i].getHeight () / 2, piles [i].getWidth (), piles [i].getHeight ());
	    piles [i].draw (g);
	}
	dragPile.draw (g);
	Font tFont = g.getFont ();
	Color tColor = g.getColor ();
	Dimension d = size ();
	g.setFont (new Font ("SansSerif", Font.BOLD, 20));
	g.setColor (Color.white);
	g.drawString (Integer.toString (score), 15, 50);
	g.setFont (tFont);
	g.setColor (tColor);
	if (win == true)
	{
	    g.setColor (Color.white);
	    g.setFont (new Font ("SansSerif", Font.BOLD, 20));
	    g.drawString ("YOU WON!", (int) (d.getWidth () / 2 - 10), (int) (d.getHeight () / 8));
	}
    }




    public void actionPerformed (ActionEvent e)
    {
	Object objSource = e.getSource ();
	if (objSource == buttonNewGame)
	{
	    reset ();
	    addMouseListener (this);
	    addMouseMotionListener (this);
	    buttonAutoWin.setEnabled (true);
	}
	if (objSource == buttonEndGame)
	{
	    textFieldAction.setText ("Game ended");
	    System.exit (0);
	}
	repaint ();
	if (objSource == buttonAutoWin)
	{
	    buttonAutoWin.setEnabled (false);
	    DeckClass tDeck = new DeckClass ();
	    CardClass tCard = new CardClass ();
	    for (int i = 1 ; i < 5 ; i++)
	    {
		for (int s = 13 ; s > 0 ; s--)
		{
		    tCard = new CardClass ();
		    tCard.setVal (s);
		    tCard.setSuit (i);
		    piles [i].addCard (tCard, 0);
		}
	    }
	    checkWin ();
	}


    }


    public boolean action (Event e, Object o)
    {
	repaint ();
	return true;

    }




    public void checkHidden ()          // if there is a facedown card, flip it.
    {
	CardClass tCard, tPileCard;
	int lastPCard;
	for (int i = 1 ; i < 4 ; i++)
	{
	    lastPCard = piles [i].getNumCard () - 1;
	    tPileCard = piles [i].getCard (lastPCard);

	    if (tPileCard.getState () == true && piles[i].getNumCard() != 0)
	    {
		tCard = piles [i].dealCard (lastPCard);
		tCard.setState (false);
		piles [i].addCard (tCard, lastPCard);
	    }
	}
	repaint ();
    }


    public void checkWin ()
    {
	win = true;
	for (int i = 8 ; i < 12 ; i++)
	{
	    if (piles [i].checkStraight () == false)
	    {
		win = false;
	    }
	}
	if (win == true)
	{
	    buttonAutoWin.setEnabled (false);
	    removeMouseListener (this);
	    removeMouseMotionListener (this);
	}
    }


    public void checkAll ()
    {
	checkHidden ();
	checkWin ();
    }


    public void mouseClicked (MouseEvent e)
    {
    }


    public void mouseEntered (MouseEvent e)
    {
    }


    public void mouseExited (MouseEvent e)
    {
    }


    public void mouseMoved (MouseEvent e)
    {
    }


    public void center (MouseEvent e)
    {
	if (OKtoMove == true)
	{
	    dragPile.setCentre (e.getX () + diffx, e.getY () + diffy);
	}
    }


    public void mousePressed (MouseEvent e)
    {
	// left click only
	if (e.getButton () == 1)
	{
	    // instantiate variables and change textbox
	    textFieldAction.setText ("Pressed");
	    int pos = -1;
	    int ctr = 0;
	    int temp = 0;
	    // loop, while ctr is less than 8 and position hasn't been found (-1 when it isnt found) and something hasnt been found
	    while (ctr < 8 && pos == -1 && OKtoMove == false) // stops when it finds it (if it exists) or when it isnt in any deck for playing piles
	    {
		// position will return -1 if not found in piles[ctr].
		pos = piles [ctr].getPos (e.getX (), e.getY ());
		// if the mouse clicked in the pile, and its the stock (pile #0)
		if (piles [ctr].isPointInDeck (e.getX (), e.getY ()) == true && ctr == 0)
		{
		    // if stock isn't empty
		    if (piles [ctr].getNumCard () != 0)
		    {
			// instantiate temp card & position of the last card
			CardClass tCard;
			int lastPos;
			for (int b = 1 ; b < 4 ; b++) // 1 to 3
			{
			    lastPos = piles [b].getNumCard (); // gets last card #
			    tCard = piles [0].dealCard (0); // tCard removes first stock card
			    tCard.setState (false); // unflips it
			    piles [b].addCard (tCard, lastPos); // adds card to first 3 piles
			}
			score++;
		    }
		}
		else if (piles [ctr].isPointInDeck (e.getX (), e.getY ()) == true && ctr > 0 && pos != -1 && piles [ctr].getCard (pos).getState () == false)
		{
		    // diffx/y is for dragging the card from where they were clicked
		    diffx = piles [ctr].getCentreX () - e.getX ();
		    diffy = piles [ctr].getCentreY () + pos * 40 - e.getY (); // 40 for distance/hitbox
		    OKtoMove = true;
		    orig = ctr;
		    temp = piles [ctr].getNumCard () - pos;
		    for (int a = 0 ; a < temp ; a++)
		    {
			dragPile.addCard (piles [ctr].dealCard (pos), 0);
		    }
		    center (e);

		}

		ctr++;
	    }
	    repaint ();
	}
    }


    public void mouseDragged (MouseEvent e)  // needs fixing
    {
	if (OKtoMove == true)
	{
	    textFieldAction.setText ("Dragged");
	    center (e); // centers the object
	    repaint (); // redraws everything

	}
    }


    public void mouseReleased (MouseEvent e)  // add click zones here (if mouse is released here after dragging)
    {
	// left click ONLY
	if (e.getButton () == 1)
	{
	    textFieldAction.setText ("Released");
	    if (OKtoMove == true)
	    { // create new variables
		int i = 1; // ctr
		int OK = 0; // checks if the right pile has been found
		CardClass aCard; // temp cards
		CardClass bCard;
		while (i < 12 && OK == 0)    // i = 1 because piles[0] is the stock pile which can't be interacted with.
		{
		    // checks if dragPile is dropped on pile [1-8] and it isnt the original pile
		    if (piles [i].isPointInDeck (e.getX (), e.getY ()) == true && i != orig && i < 8)
		    {
			if (piles [i].getNumCard () != 0) // if
			{
			    aCard = piles [i].getCard (piles [i].getNumCard () - 1);
			    bCard = dragPile.getCard (0);
			    if (
				    (aCard.getSuit () == bCard.getSuit ()) &&
				    (aCard.getVal () - 1 == bCard.getVal ())
				    )
			    {
				OK = 1;
				piles [i].addDeck (dragPile);
				score++; // score goes up
			    }
			}
			// if the pile beneath dragPile is empty
			else if (piles [i].getNumCard () == 0)
			{
			    piles [i].addDeck (dragPile);
			    score++;                                                      // score goes up

			}

		    }
		    // if dragPile is on top of the foundations
		    else if (piles [i].isPointInDeck (e.getX (), e.getY ()) == true && i != orig && i >= 8)
		    {
			// if the dragPile is in order from K to A in the same suit
			if (dragPile.checkStraight () == true && piles [i].getNumCard () == 0)
			{
			    // allow it to drop on the foundation.
			    OK = 1;
			    piles [i].addDeck (dragPile);
			}
			score++; // score goes up
		    }
		    // inc counter
		    i++;
		}
		if (OK == 0)
		{
		    // if nothing happened above, return the dragPile to its original pile and lower the score.
		    piles [orig].addDeck (dragPile);
		}

	    }
	    // no longer able to drag, check if there are any hidden cards, check if the player has won, and redraw everthing.
	    OKtoMove = false;
	    checkAll ();
	    repaint ();
	}
    }
}


