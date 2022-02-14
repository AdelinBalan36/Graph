import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Graf
{
    private static void initUI() {

        JFrame f = new JFrame("Algoritmica Grafurilor");
        //sa se inchida aplicatia atunci cand inchid fereastra

        final Label text = new Label();
        text.setAlignment(Label.CENTER);
        text.setSize(400, 100);
        Button buton = new Button("Choose the Graph");
        buton.setBounds(320, 150, 100, 21);

        final Choice c = new Choice();
        c.setBounds(100, 150, 200, 250);
        c.add("Graf Neorientat");
        c.add("Graf Orientat");
        c.add("Graf generat aleatoriu");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //adaug in frame varianta de graf orientat/neorientat
        f.add(c);
        f.add(text);
        f.add(buton);

        //imi creez ob GrafNeorientat, este doar de forma pentru a ajunge la fereastra pop Up

        f.add(new GrafNeorientat());

        //setez dimensiunea ferestrei
        f.setSize(750, 750);
        f.setLayout(null);

        //fac fereastra vizibila
        f.setVisible(true);
        buton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                     if(c.getSelectedItem()=="Graf Neorientat" ){

                         JFrame f = new JFrame("Desenare Graf Neorientat");
                         // sa se inchida aplicatia atunci cand inchid fereastra
                         final Label label = new Label();
                         f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                         // imi creez ob MyPanel
                         f.add(new GrafNeorientat() );
                         // setez dimensiunea ferestrei
                         f.setSize(1000, 1000);
                         // fac fereastra vizibila
                         f.setVisible(true);

                     }
                else if(c.getSelectedItem()=="Graf Orientat")
                     {
                         JFrame f = new JFrame("Desenare Graf Orientat");
                         // sa se inchida aplicatia atunci cand inchid fereastra
                         final Label label = new Label();
                         f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                         // imi creez ob MyPanel
                         f.add(new GrafOrientat());
                         // setez dimensiunea ferestrei
                         f.setSize(1000, 1000);
                         // fac fereastra vizibila
                         f.setVisible(true);
                     }

                else if(c.getSelectedItem()=="Graf generat aleatoriu")
                {
                         JFrame f = new JFrame("Graf generat aleatoriu");
                         final Label label = new Label();
                         f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                         f.add(new GenerareGrafAleatoriu() );
                         f.setSize(1000, 1000);
                         f.setVisible(true);

                     }

            }
        });
    }

    public static void main(String[] args)
    {
        //pornesc firul de executie grafic
        //fie prin implementarea interfetei Runnable, fie printr-un ob al clasei Thread
        SwingUtilities.invokeLater(new Runnable() //new Thread()
        {
            public void run()
            {
                initUI();
            }
        });
    }
}
