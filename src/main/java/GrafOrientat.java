import javafx.util.Pair;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.*;
import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class GrafOrientat extends JPanel {
    private int nodeNr = 1;
    private int node_diam = 30;
    private Vector<Node> listaNoduri;
    private Vector<Arc> listaArce;
    private int root;
    Point pointStart = null;
    Point pointEnd = null;
    boolean isDragging = false;
    private  ArrayList<ArrayList<Integer>> listaAdiacenta;
    public GrafOrientat() {

        listaNoduri = new Vector<Node>();
        listaArce = new Vector<Arc>();
        listaAdiacenta=new ArrayList<>();

        // borderul panel-ului
        setBorder(BorderFactory.createLineBorder(Color.black));
        addMouseListener(new MouseAdapter() {
            // evenimentul care se produce la apasarea mousse-ului
            public void mousePressed(MouseEvent e) {
                pointStart = e.getPoint();
            }

            // evenimentul care se produce la eliberarea mousse-ului
            public void mouseReleased(MouseEvent e) {
                if (!isDragging) {
                    addNode(e.getX(), e.getY());
                } else {
                    boolean start = false, end = false;
                    int nodInceput = 0, nodSfarsit = 0;
                    for (int i = 0; i < listaNoduri.size(); i++) {
                        if (Math.abs(listaNoduri.get(i).getCoordX() - pointStart.x) < node_diam
                                && Math.abs(listaNoduri.get(i).getCoordY() - pointStart.y) < node_diam) {
                            start = true;
                            nodInceput = listaNoduri.get(i).getNumber();
                        }
                        if (Math.abs(listaNoduri.get(i).getCoordX() - pointEnd.x) < node_diam
                                && Math.abs(listaNoduri.get(i).getCoordY() - pointEnd.y) < node_diam) {
                            end = true;
                            nodSfarsit = listaNoduri.get(i).getNumber();
                        }

                    }

                    if (start && end) {
                        Arc arc = new Arc(pointStart, pointEnd, nodInceput, nodSfarsit,0);
                        boolean ok = false;
                        for (Arc arcIterator : listaArce) {
                            if (arcIterator.getNodInceput() == nodInceput && arcIterator.getNodSfarsit() == nodSfarsit)
                                ok = true;

                        }
                        if(ok==false)
                        {
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
            // evenimentul care se produce la drag&drop pe mousse
            public void mouseDragged(MouseEvent e) {
                pointEnd = e.getPoint();
                isDragging = true;
                repaint();
            }
        });


    }
    /*void DFSUtil(int v, boolean vizitat[])
    {

        vizitat[v] = true;

        Iterator<Integer> i = listaAdiacenta[v].listIterator();
        while (i.hasNext()) {
            int n = i.next();
            if (!vizitat[n])
                DFSUtil(n, visited);
        }
    }*/
    private void getMyRoot(ArrayList<ArrayList<Integer>> listaADiacenta) {
        listaAdiacenta=new ArrayList<>(nodeNr);
        for(int i=0;i<nodeNr;i++)
            listaAdiacenta.add(new ArrayList<>());
        for (Arc arc : listaArce) {
            listaAdiacenta.get(arc.getNodInceput()).add(arc.getNodSfarsit());
        }

        for (int i = 0; i <=listaNoduri.size(); i++) {
            System.out.print("For node "+ i+ " we have the nodes : ");
            System.out.println(listaAdiacenta.get(i));
            System.out.println();
        }
        int[] degree= new int[nodeNr];
        if(listaAdiacenta.size()>0)
            for (int i = 0; i <listaAdiacenta.size(); i++) {
                for(Integer j: listaAdiacenta.get(i) )
                    degree[j]++;
            }
        for(int i:degree){
            System.out.print(degree[i]+" ");
        }
        int count0=0;
        boolean count=true;
        int root=-1;
        for(int i=1;i<nodeNr;i++){
             if(degree[i]==0) {
                 count0++;
                 root=i;
              }
             if(degree[i]>1){
                 count=false;
             }
        }
        if(count0>1 && count==false)
            System.out.println("Nu e arbore");
        else
        {
            System.out.println("Este arbore cu radacina "+ root );
        }

    }

   // metoda care se apeleaza la eliberarea mouse-ului
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
            File.orientat(listaNoduri.size(), listaArce);
            repaint();
        }
    }

    // se executa atunci cand apelam repaint()
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);// apelez metoda paintComponent din clasa de baza
        g.drawString("This is my Graph!", 10, 20);

        // deseneaza arcele existente in lista

       getMyRoot(listaAdiacenta);

         listaAdiacenta.clear();

        for (Arc a : listaArce) {
            a.drawArc(g, a.getStart(), a.getEnd());
        }
        /*for (Arc a : listaArce) {
            System.out.println(a.getNodInceput()+" "+ a.getNodSfarsit());
        }*/
        // deseneaza arcul curent; cel care e in curs de desenare
        if (pointStart != null) {
            g.setColor(Color.BLUE);
            g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
        }
        // deseneaza lista de noduri
        for (int i = 0; i < listaNoduri.size(); i++) {
            listaNoduri.elementAt(i).drawNode(g, node_diam);
        }

    }
}