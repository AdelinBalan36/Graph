

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class GrafNeorientat extends JPanel {

    private int nodeNr = 1;
    private int node_diam = 30;

    private Vector<Node> listaNoduri;
    private Vector<Arc> listaArce;

    Point pointStart = null;
    Point pointEnd = null;

    boolean isDragging = false;
    public GrafNeorientat()
    {
        listaNoduri = new Vector<Node>();
        listaArce = new Vector<Arc>();
        // borderul panel-ului

        setBorder(BorderFactory.createLineBorder(Color.black));
        addMouseListener(new MouseAdapter() {

            //evenimentul care se produce la apasarea mousse-ului
            public void mousePressed(MouseEvent e) {
                pointStart = e.getPoint();
            }

            //evenimentul care se produce la eliberarea mousse-ului
            public void mouseReleased(MouseEvent e) {

                if (!isDragging) {
                    addNode(e.getX(), e.getY());
                }
                else {

                    boolean inceput=false,sfarsit=false;
                    int nodInceput=0, nodSfarsit=0;
                    for(int i=0;i<listaNoduri.size();i++) {
                        if(Math.abs(listaNoduri.get(i).getCoordX()-pointStart.x)< node_diam
                                && Math.abs(listaNoduri.get(i).getCoordY()-pointStart.y)<node_diam)
                        {
                            inceput=true;
                            nodInceput=listaNoduri.get(i).getNumber();
                        }
                        if(Math.abs(listaNoduri.get(i).getCoordX()-pointEnd.x)< node_diam
                                && Math.abs(listaNoduri.get(i).getCoordY()-pointEnd.y)<node_diam)
                        {
                            sfarsit=true;
                            nodSfarsit=listaNoduri.get(i).getNumber();
                        }

                    }
                    if(inceput && sfarsit) {
                        Arc arc = new Arc(pointStart, pointEnd,nodInceput,nodSfarsit,0);
                        boolean ok=false;
                        for (Arc arcIterator : listaArce) {
                            if (  (arcIterator.getNodInceput() == nodInceput && arcIterator.getNodSfarsit() == nodSfarsit)
                                    || ( arcIterator.getNodInceput() == nodSfarsit && arcIterator.getNodSfarsit() == nodInceput )  )
                                ok = true;
                        }
                        if(ok==false) {
                            listaArce.add(arc);
                        }
                        File.orientat(listaNoduri.size(), listaArce);

                    }
                    repaint();
                }
                pointStart = null;
                isDragging = false;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            //evenimentul care se produce la drag&drop pe mousse
            public void mouseDragged(MouseEvent e) {
                pointEnd = e.getPoint();
                isDragging = true;
                repaint();
            }
        });
    }

    //metoda care se apeleaza la eliberarea mouse-ului
    private void addNode(int x, int y) {
        boolean ok=true;
        for(int i=0;i<listaNoduri.size();i++) {

            if(Math.abs(listaNoduri.get(i).getCoordX()-x)< node_diam && Math.abs(listaNoduri.get(i).getCoordY()-y)<node_diam)
                ok=false;
        }
        if(ok) {
            Node node = new Node(x, y, nodeNr);
            listaNoduri.add(node);
            nodeNr++;
            File.neorientat( listaNoduri.size(), listaArce);
            repaint();
        }
    }

    //se executa atunci cand apelam repaint()
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);//apelez metoda paintComponent din clasa de baza
        g.drawString("This is my Graph!", 10, 20);

        //deseneaza arcele existente in lista

        for (Arc a : listaArce)
        {
            a.drawLine(g);
        }

        //deseneaza arcul curent; cel care e in curs de desenare
        if (pointStart != null)
        {
            g.setColor(Color.RED);
            g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
        }
        //deseneaza lista de noduri
        for(int i=0; i<listaNoduri.size(); i++)
        {
            listaNoduri.elementAt(i).drawNode(g, node_diam);
        }

    }
}
