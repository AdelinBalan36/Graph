import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JPanel;



public class GenerareGrafAleatoriu extends JPanel {
    private int nodeNr = 1;
    private final int node_diam = 10;
    private final Vector<Node> listaNoduri;
    private final Vector<Arc> listaArce;
    private ArrayList<ArrayList<Integer>> listaAdiacenta;
    double XC,YC;



    public GenerareGrafAleatoriu() {

        XC=1000/2;
        YC=1000/2;
        listaNoduri = new Vector<Node>();
        listaArce = new Vector<Arc>();

        // generare graf aleatori;
        int n,nPaths,nCycles;
        Random rand= new Random();
        n=rand.nextInt(25)  +1 ;
        nPaths=1;
        nCycles=0;
        listaAdiacenta=new ArrayList<>(n);

        for(int i=0;i<n+1;i++)
            listaAdiacenta.add(new ArrayList<>());
        // System.out.println(listaAdiacenta.size());

        // borderul panel-ului
        setBorder(BorderFactory.createLineBorder(Color.black));

        while(nodeNr<n)
        {
            double unghi = Math.random() * 2 * Math.PI,
                    ip = 400,
                    cx = Math.cos(unghi) * ip,
                    cy = Math.sin(unghi) * ip;

            addNode((int) (XC + cx), (int) (YC + cy));
            /*System.out.println(listaNoduri.elementAt(i).getCoordX());
            System.out.println(listaNoduri.elementAt(i).getCoordY());
            System.out.println("===========");*/

        }

        int s,k,u,v;

        // trebuie la fiecare path nou initializat iar pathnode
       while(nPaths!=0) {

           s=1;
           Vector<Boolean> pathnode=new Vector<>();
           pathnode.setSize(n);
           for (int i = 1; i < pathnode.size(); i++)
               pathnode.set(i, false);
           pathnode.set(1, true);

           u = s;
           System.out.println(getPointOfNode(u));
           boolean ok = true;


           while (ok) {

               k = rand.nextInt(nodeNr-1) + 1;
               // System.out.println(k);
               v = k;

               if (pathnode.elementAt(v) == false) {

                   if (getPointOfNode(u) != null && getPointOfNode(v) != null && u != v) {
                       addArc(getPointOfNode(u), getPointOfNode(v), u, v);
                       listaAdiacenta.get(u).add(v);

                       pathnode.set(v, true);
                   }
                   u = v;
               }


               // contor++;
               if (v == nodeNr - 1) {
                   if (getPointOfNode(u) != null && getPointOfNode(v) != null && u != v)
                       addArc(getPointOfNode(u), getPointOfNode(v), u, v);

                   ok = false;
                   nPaths--;
               }

           }
       }

        int u0;
        // trebuie la fiecare path nou initializat iar pathnode
        while(nCycles!=0) {

            u0= rand.nextInt(nodeNr-1) + 1;;
            Vector<Boolean> cycle=new Vector<>();
            cycle.setSize(n);
            for (int i = 1; i < cycle.size(); i++)
                cycle.set(i, false);
            cycle.set(1, true);

            u = u0;
            System.out.println(getPointOfNode(u));
            boolean ok = true;

            // cat timp nu ajungem la nodul final pentru un drum
            // System.out.println(getPointOfNode(0));
            while (ok) {
                //generam un posibil nod care sa faca parte din ciclu
                k = rand.nextInt(nodeNr-1) + 1;
                // System.out.println(k);
                v = k;

                if (cycle.elementAt(v) == false) {

                    if (getPointOfNode(u) != null && getPointOfNode(v) != null && u != v) {
                        addArc(getPointOfNode(u), getPointOfNode(v), u, v);
                        listaAdiacenta.get(u).add(v);

                        cycle.set(v, true);

                    }
                    u = v;
                }

                if (v==u0 ) {
                    if (getPointOfNode(u) != null && getPointOfNode(v) != null && u != v)
                        addArc(getPointOfNode(u), getPointOfNode(v), u, v);

                    ok = false;
                    nCycles--;
                }

            }
        }
        File.grafAleator(listaNoduri.size(), listaAdiacenta);
    }

    private Point getPointOfNode(int number){
         Point point=new Point();
         for(int i=0;i<listaNoduri.size();i++) {
              if (listaNoduri.elementAt(i).getNumber() == number) {
                     point.setLocation(listaNoduri.elementAt(i).getCoordX(),listaNoduri.elementAt(i).getCoordY());
                  //System.out.println(i);
                 // System.out.println(point);
                       break;}
            // System.out.println(i);
             //System.out.println(point);
         }
        //System.out.println(point);
         if (point.x !=0 && point.y !=0)
             return point;

         return null;
    }

    private void addArc(Point a, Point b,int inceput, int sfarsit) {

        Arc arc=new Arc(a,b,inceput,sfarsit,0);
        listaArce.addElement(arc);

    }
    // metoda care insereaza noduri daca nu se suprapun
    private void addNode(int x, int y) {
        boolean ok = true;
        for (int i = 0; i < listaNoduri.size(); i++) {

            if (Math.abs(listaNoduri.get(i).getCoordX() - x) < node_diam
                    && Math.abs(listaNoduri.get(i).getCoordY() - y) < node_diam)
                ok = false;
        }
        if (ok) {
            Node node = new Node(x, y, nodeNr);
            listaNoduri.add(node);
            nodeNr++;

            repaint();
        }
    }

    // se executa atunci cand apelam repaint()
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);// apelez metoda paintComponent din clasa de baza
        g.drawString("This is my Graph!", 10, 20);
        int count=1;
        // deseneaza arcele existente in lista
        for (Arc a : listaArce) {

            if (a.getNodInceput() == 1){

                /*System.out.println( " "+ count + " ------------------------------------------");
                count++;*/
            }

           /* System.out.print(a.getNodInceput());
            System.out.print(" ");
            System.out.print(a.getNodSfarsit());
            System.out.println();*/
            a.drawArc(g, a.getStart(), a.getEnd());
        }

        // deseneaza lista de noduri
       for (int i = 0; i < listaNoduri.size(); i++) {
         listaNoduri.elementAt(i).drawNode(g, node_diam);
        }

    }
}
