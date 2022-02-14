
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

public class Arc
{
    private final Point start;
    private final Point end;

    private final int nodInceput;
    private final int nodSfarsit;
    private final int cost;
    public Arc(Point start, Point end,int nodInceput,int nodSfarsit,int cost)
    {
        this.start = start;
        this.end = end;
        this.nodInceput = nodInceput;
        this.nodSfarsit = nodSfarsit;
        this.cost=cost;
    }

    public Point getStart(){
        return start;
    }
    public Point getEnd(){
        return end;
    }
    public int getCost(){return cost;}
    public int getNodInceput(){ return nodInceput;}
    public int getNodSfarsit(){ return nodSfarsit;}

    public void drawArc(Graphics g,Point inceput,Point sfarsit)
    {

        double dx=sfarsit.getX()-inceput.getX();
        double dy=sfarsit.getY()-inceput.getY();
        double x1=inceput.getX();
        double y1=inceput.getY();
         Graphics2D g1 = (Graphics2D) g.create();

          int lungime= (int)Math.sqrt(dx*dx+dy*dy);

          double unghi=Math.atan2( dy,dx );


          g1.setColor(Color.RED);

          AffineTransform at = AffineTransform.getTranslateInstance(  x1, y1  );
          at.concatenate(AffineTransform.getRotateInstance( unghi ));
          g1.transform(at);

          g1.drawLine(0, 0, lungime, 0);

          g1.fillPolygon(new int[] {lungime, lungime-10, lungime-10, lungime},
                new int[] {0, -10, 10, 0}, 4);


    }
    public void drawLine(Graphics g)
    {
        if (start != null)
        {
            g.setColor(Color.RED);
            g.drawLine(start.x, start.y, end.x, end.y);
        }
    }
    @Override()
    public String toString()
    {
        return this.nodInceput + " " + this.nodSfarsit;
    }
}

