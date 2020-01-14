// The "DiamondApplet" class.
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class DiamondPanelsApplet extends Applet implements ActionListener, MouseListener, MouseMotionListener
{

    Graphics g;   // declares a graphics canvas for drawing
    Button buttonDraw = new Button ("Draw");
    Button buttonErase = new Button ("Erase");
    Label labelAction = new Label ("Action");
    TextField textFieldAction = new TextField (5);
    Checkbox checkboxFilled = new Checkbox ("Filled", true);
    CheckboxGroup colorGroup = new CheckboxGroup ();
    Checkbox colorGroupBlue = new Checkbox ("Blue", colorGroup, false);
    Checkbox colorGroupRed = new Checkbox ("Red", colorGroup, true);
    Checkbox colorGroupGreen = new Checkbox ("Green", colorGroup, false);
    CheckboxGroup objectGroup = new CheckboxGroup ();
    Checkbox objectGroupKite = new Checkbox ("Kite", objectGroup, false);
    Checkbox objectGroupBalloon = new Checkbox ("Balloon", objectGroup, true);
    Checkbox objectGroupWhirlyGig = new Checkbox ("Whirly Gig", objectGroup, false);
    Choice choiceSize = new Choice ();

    BorderLayout lm = new BorderLayout ();


    boolean drawFlag = false;
    DiamondClass d1;

    boolean OKtoMove = false;

    public void init ()
    {

	setSize (500, 500);
	Panel pDraw = new Panel ();
	add ("Center", pDraw);
	setLayout (lm);
	g = pDraw.getGraphics (); // gets canvas created by browser-replaces new stmt

	Panel pdeWest = new Panel ();
	pdeWest.setLayout (new BorderLayout ());
	pdeWest.add ("North", buttonDraw);
	pdeWest.add ("South", buttonErase);
	add ("West", pdeWest);


	Panel pdeNorth = new Panel ();
	GridBagLayout gbl = new GridBagLayout ();
	pdeNorth.setLayout (gbl);
	GridBagConstraints gbc = new GridBagConstraints ();
	gbc.weightx = 1;
	gbc.weighty = 1;
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbl.setConstraints (colorGroupBlue, gbc);
	pdeNorth.add (colorGroupBlue);
	gbc.gridx = 1;
	gbc.gridy = 0;
	gbl.setConstraints (colorGroupRed, gbc);
	pdeNorth.add (colorGroupRed);
	gbc.gridx = 2;
	gbc.gridy = 0;
	gbl.setConstraints (colorGroupGreen, gbc);
	pdeNorth.add (colorGroupGreen);
	gbc.gridx = 0;
	gbc.gridy = 1;
	gbl.setConstraints (labelAction, gbc);
	pdeNorth.add (labelAction);
	gbc.gridx = 1;
	gbc.gridy = 1;
	gbl.setConstraints (textFieldAction, gbc);
	pdeNorth.add (textFieldAction);
	gbc.gridx = 2;
	gbc.gridy = 1;
	gbl.setConstraints (checkboxFilled, gbc);
	pdeNorth.add (checkboxFilled);
	add ("North", pdeNorth);

	Panel pdeEast = new Panel ();
	pdeEast.setLayout (new FlowLayout ());
	choiceSize.add ("Large");       // H = 100; W = 75
	choiceSize.add ("Medium");      // H = 80;  W = 60
	choiceSize.add ("Small");       // H = 60;  W = 45
	pdeEast.add (choiceSize);
	add ("East", pdeEast);

	Panel pdeSouth = new Panel ();
	pdeSouth.setLayout (new GridLayout ());
	pdeSouth.add (objectGroupKite);
	pdeSouth.add (objectGroupBalloon);
	pdeSouth.add (objectGroupWhirlyGig);
	add ("South", pdeSouth);

	buttonDraw.addActionListener (this);
	buttonErase.addActionListener (this);
	addMouseListener (this);
	addMouseMotionListener (this);

	d1 = new DiamondClass ();


    } // init method


    public void paint (Graphics g)
    {
	if (drawFlag == true)
	{
	    d1.draw (g);
	}
	else
	{
	    //d1.erase (g);
	}

    } // paint method



    public boolean action (Event e, Object o)
    {
	handleAction (e.target);
	return true;
    }


    public void actionPerformed (ActionEvent e)
    {
	handleAction (e.getSource ());
    }


    public void handleAction (Object objSource)
    {
	if (objSource instanceof Button)
	{
	    if (objSource == buttonDraw)
	    {
		textFieldAction.setText ("Draw");
		drawFlag = true;
	    }
	    if (objSource == buttonErase)
	    {
		textFieldAction.setText ("Erase");
		drawFlag = false;
	    }
	}
	if (objSource instanceof Checkbox)
	{
	    if (checkboxFilled.getState () == true)
	    {
		d1.setIsFilled (true);
	    }
	    if (checkboxFilled.getState () == false)
	    {
		d1.setIsFilled (false);
	    }
	}
	if (colorGroupRed.getState () == true)
	{
	    d1.setColor (Color.red);
	}
	if (colorGroupBlue.getState () == true)
	{
	    d1.setColor (Color.blue);
	}
	if (colorGroupGreen.getState () == true)
	{
	    d1.setColor (Color.green);
	}
	if (objSource instanceof Choice)
	{
	    if (choiceSize.getSelectedIndex () == 0)
	    {
		d1.setHeight (100);
		d1.setWidth (75);
	    }
	    else if (choiceSize.getSelectedIndex () == 1)
	    {
		d1.setHeight (70);
		d1.setWidth (40);
	    }
	    else if (choiceSize.getSelectedIndex () == 2)
	    {
		d1.setHeight (50);
		d1.setWidth (20);
	    }
	}


	repaint ();
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


    public void mousePressed (MouseEvent e)
    {
	if (d1.isPointInside (e.getX (), e.getY ()) == true)
	{
	    textFieldAction.setText ("Pressed");
	    OKtoMove = true;
	    d1.setCenter (e.getX (), e.getY ());
	    d1.draw (g);
	    repaint ();
	}
    }


    public void mouseReleased (MouseEvent e)
    {
	textFieldAction.setText ("Released");
	OKtoMove = false;
    }


    public void mouseDragged (MouseEvent e)
    {
	if (OKtoMove == true)
	{
	    textFieldAction.setText ("mouseDragged");
	    d1.setCenter (e.getX (), e.getY ());
	    d1.draw (g);
	    repaint ();
	}

    }


    public void mouseMoved (MouseEvent e)
    {
    }
} // DiamondApplet class
