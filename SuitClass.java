import java.awt.*;

public abstract class SuitClass extends ShapeClass
{
    public SuitClass ()
    {
	super ();
	setHeight ((int) (getWidth () * 0.4));
	setWidth ((int) (getHeight () * 0.8));
    }


    public SuitClass (Color c)
    {
	super (c);
	setHeight ((int) (getWidth () * 0.4));
	setWidth ((int) (getHeight () * 0.8));
    }
}
