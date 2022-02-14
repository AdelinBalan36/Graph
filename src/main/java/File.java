
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class File {


    public static void neorientat(int nodeNr, Vector<Arc> listaArce) {

        try {
           FileWriter scriere=new FileWriter("fileOutput.txt");

            scriere.write(nodeNr +"\n");

            for (int i = 1; i <= nodeNr; i++) {
                for (int j = 1; j <= nodeNr; j++) {
                    int ok = 0;
                    for (Arc arc : listaArce) {
                            if( (arc.getNodInceput()==i && arc.getNodSfarsit()==j)  || (arc.getNodInceput()==j && arc.getNodSfarsit()==i) ){
                                   ok=1;
                            }
                    }
                    scriere.write(ok + " ");

                }
                scriere.write("\n");

            scriere.close();
            System.out.println("Successfully wrote to the file.");

            }
        }catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    public static void orientat(int nodeNr, Vector<Arc> listaArce){

        try {

            BufferedWriter bw=new BufferedWriter( new FileWriter("fileOutput.txt"));


            bw.write(nodeNr + "\n");
            for (int i = 1; i <= nodeNr; i++) {
                for (int j = 1; j <= nodeNr; j++) {
                    int ok = 0;
                    for (Arc arc : listaArce) {
                        if (arc.getNodInceput() == i && arc.getNodSfarsit() == j) {
                            ok = 1;
                        }
                    }
                    bw.write(ok + " ");

                }
                bw.write("\n");
            }
            bw.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }
    public static void grafAleator(int nodeNr, ArrayList<ArrayList<Integer>> listArrayList) {

        try {

            BufferedWriter bw=new BufferedWriter( new FileWriter("fileOutput.txt"));


            bw.write(nodeNr + "\n");
          //  bw.write(" "+ listArrayList.size());
            for(int i=1;i<=nodeNr;i++) {
                ArrayList<Integer> rand= listArrayList.get(i);

                bw.write(""+i+" -> ");
                   for (int j = 0; j < rand.size(); j++) {
                       bw.write( ""+rand.get(j)+" ");
                }

                bw.write("\n");
            }
            bw.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

}

