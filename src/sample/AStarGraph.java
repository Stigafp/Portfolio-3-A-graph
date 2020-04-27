package sample;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class AStarGraph {
    private ArrayList<Vertex> vertices;

    public AStarGraph() {

        vertices=new ArrayList<Vertex>();
    }

    // bliver kald fra main ved Mymaze.addvertex
    public void addvertex(Vertex v) {
        vertices.add(v);
    }

    // bliver kald fra main. Den tilføjer naboer til de forskellige vertex's
    // newconnection bliver sendt til addOutEdge hvor listerne med Neighbors er
    public void newconnection(Vertex v1, Vertex v2, Double dist) {
        v1.addOutEdge(v2,dist);
        v2.addOutEdge(v1,dist);
    }

    // Vertex start vælger start vertex. Vertex destination slut destination.
    // int choise vælger imellem Manhatte og Euclidean
    public boolean A_Star(Vertex start, Vertex destination, int choice) {
        if (start==null || destination==null)
          return false;

        // Openlist er de vertex's der er igangværende
        PriorityQueue<Vertex> Openlist = new PriorityQueue<Vertex>();
        // Closedlist er  vertex's programmet har passeret
        ArrayList<Vertex> Closedlist = new ArrayList<Vertex>();
        // den active vertex
        Vertex Current;
        ArrayList<Vertex> CurrentNeighbors;
        Vertex Neighbor;
        //Initialize h with chosen heuristic
        // estimeret afstand fra start til destination via choice
        for (int i =0; i<vertices.size();i++)
        {
            if(choice == 1)
            vertices.get(i).seth(Manhattan(vertices.get(i),destination));
            else {
                vertices.get(i).seth(Euclidean(vertices.get(i),destination));
            }

        }

        // sætter afstanden fra sig selv til 0
        start.setg(0.0);
        start.calculatef();
        // insætter start vertex hvis der er plads i Openlist
        Openlist.offer(start);

        //Start algorithm
        System.out.println("Start Algorithm");
        //Implement the Astar algorithm

        /*
        openlist.add(startvertex)
        closedlist = empty Current =null
        while ( !openlist.isempty):
            Current = remove vertex with min f from openlist
            if (Current = goal)
                return 1;
            closedlist.add(Current)
            for (each vertex v in outedges of Current ):
                tempgofv = Current.g + weight(current,v)
                if (tempgofv < v.g )
                    v.prev = Current

                    v.g = tempgofv
                    v.f = v.g + v.h

                    if v not in closedlist && v not in openlist
                        openlist.add(v)
                    if (in open list you might want to remove and add again
                Return -1;
         */

        // så længe der er indhold i Openlist kør while loop
        while(!Openlist.isEmpty()){
            // igangværende vertex fjernes fra Openlist da den er aktiv
            Current = Openlist.remove();

            if(Current == destination){
                return true;
            }

            // tilføjer aktive vertex til Closedlist og går igennem Nabo vertex's
            Closedlist.add(Current);
            for (int i = 0; i < Current.getNeighbours().size(); i++) {

                // udregner fra igangværende vertex, destinationen til vertex(i)
                double weight = Current.getNeighbourDistance().get(i);
                double tempGofV = Current.getg() + weight;
                // tjekker hvilken nabo vertex har den laveste g værdi
                if(tempGofV < Current.getNeighbours().get(i).getg()){
                    // sætter aktive vertex til at være passeret vertex
                    Current.getNeighbours().get(i).setPrev(Current);
                    // sætter den laveste tempGofV til den nuværende g værdi
                    Current.getNeighbours().get(i).setg(tempGofV);
                    // udregner f værdi ved g + h(valgte metode Manhatten/Euclidean)
                    Current.getNeighbours().get(i).calculatef();
                    // hvis nabo vertix(i) ikke er i Closedlist og ikke er i Openlist, tilføj den til Openlist
                    if((!Closedlist.contains(Current.getNeighbours().get(i)))&&(!Openlist.contains(Current.getNeighbours().get(i)))){
                        Openlist.offer(Current.getNeighbours().get(i));
                    // hvis Openlist indeholder nabo vertex(i), fjern og tilføj den igen
                    } else if(Openlist.contains(Current.getNeighbours().get(i))){
                        Openlist.remove(Current.getNeighbours().get(i));
                        Openlist.offer(Current.getNeighbours().get(i));
                    }
                }
            }
        }
        // printer "DID NOT FIND A PATH!!" fra main
        return false;
    }

    // vertikal afstand + horizontal afstand udregnede i positive tal
    public Double Manhattan(Vertex from,Vertex goal){
        double distance = Math.abs(goal.getx()-from.getx()) + Math.abs(goal.gety()-from.gety());
        return distance;
    }

    // den direkte diagotale afstand
    public Double Euclidean( Vertex from,Vertex to){
        double x = to.getx()-from.getx();
        double y = to.gety()-from.gety();
        double distance = Math.sqrt((x*x)+(y*y));
        return distance;
    }
}

// data en vertex indeholder
class Vertex implements Comparable<Vertex>{
    private ArrayList<Vertex> Neighbours = new ArrayList<Vertex>();
    private ArrayList<Double> NeighbourDistance = new ArrayList<Double>();
    // variabler for vertex
    private String id;
    private Double f;
    private Double g;
    private Double h;
    private Integer x;
    private Integer y;
    private Vertex prev = null;

    // constructor kaldt fra main
    public Vertex(String id, int x_cor,int y_cor){
        this.id = id;
        this.x = x_cor;
        this.y = y_cor;
        // den aktive vertex vej til vertex(i) vil altid være uendelig
        // hvis der er mere en kæde i rækken ud til vertex(i)
        f = Double.POSITIVE_INFINITY;
        g = Double.POSITIVE_INFINITY;
        h = 0.0;
    }
    // tilføjer vertex nabo til Naboliste for de forskellige vertex's
    public void addOutEdge(Vertex toV, Double dist){
        Neighbours.add(toV);
        NeighbourDistance.add(dist);
    }

    // getters and setters
    public ArrayList<Vertex> getNeighbours() {
        return Neighbours;
    }
    public ArrayList<Double> getNeighbourDistance(){
        return NeighbourDistance;
    }
    public String getid(){ return id;};
    public Integer getx(){ return x; }
    public Integer gety(){return y; }
    public Double getf() { return f; }
    public void calculatef() {
        f = g + h; }

    public Double getg() { return g; }

    public void setg(Double newg){ g = newg; }
    public Double geth(){ return h; }
    public void seth(Double estimate){ h = estimate; }
    public Vertex getPrev() { return prev; }
    public void setPrev(Vertex v){
        prev = v;}
    public void printVertex(){
        System.out.println(id + " g: "+g+ ", h: "+h+", f: "+f);
    }

    //
    @Override
    public int compareTo(Vertex vertex) {
        if (this.getf() > vertex.getf())
            return 1;
        if (this.getf() < vertex.getf())
            return -1;
        return 0;
    }
}
