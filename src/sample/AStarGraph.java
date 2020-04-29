package sample;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class AStarGraph {
    private ArrayList<Vertex> vertices;

    public AStarGraph() {

        setVertices(new ArrayList<Vertex>());
    }

    public void addvertex(Vertex v) {           // bliver kald fra Controller class ved Mymaze.addvertex
        getVertices().add(v);
    }

    // bliver kald fra Controller class. Den tilføjer naboer til de forskellige vertex's
    // newconnection bliver sendt til addOutEdge hvor listerne med Neighbors er
    public void newconnection(Vertex v1, Vertex v2, Double dist) {
        v1.addOutEdge(v2,dist);
        v2.addOutEdge(v1,dist);
    }

    // Vertex start vælger start vertex. Vertex destination slut destination.
    // choise vælger imellem Manhatte og Euclidean
    public boolean A_Star(Vertex start, Vertex destination, String choice) {
        if (start==null || destination==null)
          return false;

        PriorityQueue<Vertex> Openlist = new PriorityQueue<Vertex>();       // Openlist er de vertex's der er igangværende
        ArrayList<Vertex> Closedlist = new ArrayList<Vertex>();              // Closedlist er  vertex's programmet har passeret
        Vertex Current;                                                 // den active vertex
        ArrayList<Vertex> CurrentNeighbors;
        Vertex Neighbor;

        // estimeret afstand fra start til destination via choice
        for (int i = 0; i< getVertices().size(); i++) {
            if(choice == "Manhattan")
                System.out.println("Manhattan is running");
            getVertices().get(i).seth(Manhattan(getVertices().get(i), destination));
            if (choice == "Euclidean") {
                System.out.println("Euclidean is running");
                getVertices().get(i).seth(Euclidean(getVertices().get(i), destination));
            }
        }

        start.setg(0.0);           // sætter afstanden fra sig selv til 0
        start.calculatef();
        Openlist.offer(start);      // insætter start vertex hvis der er plads i Openlist

        //Start algorithm
        System.out.println("Start Algorithm");

        // implementer Astar algoritme
        // så længe der er indhold i Openlist kør while loop
        while(!Openlist.isEmpty()) {
            Current = Openlist.remove();     // igangværende vertex fjernes fra Openlist da den er aktiv
            Closedlist.add(Current);        // tilføjer aktive vertex til Closedlist og går igennem Nabo vertex's

            if(Current == destination){
                return true;
            }

            for (int i = 0; i < Current.getNeighbours().size(); i++) {
                double weight = Current.getNeighbourDistance().get(i);     // udregner fra igangværende vertex, destinationen til vertex(i)
                double tempGofV = Current.getg() + weight;

                if(tempGofV < Current.getNeighbours().get(i).getg()) {     // tjekker hvilken nabo vertex har den laveste g værdi
                    Current.getNeighbours().get(i).setPrev(Current);       // sætter aktive vertex til at være passeret vertex
                    Current.getNeighbours().get(i).setg(tempGofV);         // sætter den laveste tempGofV til den nuværende g værdi
                    Current.getNeighbours().get(i).calculatef();           // udregner f værdi ved g + h(valgte metode Manhatten/Euclidean)
                    System.out.println();

                    // hvis nabo vertix(i) ikke er i Closedlist og ikke er i Openlist, tilføj den til Openlist
                    if((!Closedlist.contains(Current.getNeighbours().get(i))) &&
                            (!Openlist.contains(Current.getNeighbours().get(i)))) {
                        Openlist.offer(Current.getNeighbours().get(i));

                    // hvis Openlist indeholder nabo vertex(i), fjern og tilføj den igen
                    } else if(Openlist.contains(Current.getNeighbours().get(i))){
                        Openlist.remove(Current.getNeighbours().get(i));
                        Openlist.offer(Current.getNeighbours().get(i));
                    }
                }
            }
        }
        return false;   // printer "DID NOT FIND A PATH!!" fra Controller class
    }

    // vertikal afstand + horizontal afstand udregnet i positive tal
    public Double Manhattan(Vertex from,Vertex goal){
        double distance = Math.abs(goal.getx()-from.getx()) + Math.abs(goal.gety()-from.gety());
        return distance;
    }

    // den direkte diagotale afstand
    public Double Euclidean( Vertex from,Vertex to){
        double x = to.getx()-from.getx();
        double y = to.gety()-from.gety();
        double distance = Math.sqrt((x*x)+(y*y));
        return distance; }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertex> vertices) {
        this.vertices = vertices;
    }
}

// data en vertex indeholder
class Vertex implements Comparable<Vertex> {
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

    // constructor kaldt fra controller
    public Vertex(String id, int x_cor,int y_cor){
        this.id = id;
        this.x = x_cor;
        this.y = y_cor;

        // den aktive vertex vej til vertex(i) vil altid være uendelig
        // hvis der er mere end 1 edge i rækken ud til vertex(i)
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
    public void calculatef() { f = g + h; }
    public Double getg() { return g; }
    public void setg(Double newg){ g = newg; }
    public Double geth(){ return h; }
    public void seth(Double estimate){ h = estimate; }
    public Vertex getPrev() { return prev; }
    public void setPrev(Vertex v) { prev = v; }
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

    @Override
    public String toString() {
        return id;
    }
}