package sample;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class AStarGraph {
    private ArrayList<Vertex> vertices;

    public AStarGraph() {

        vertices=new ArrayList<Vertex>();
    }

    public void addvertex(Vertex v) {

        vertices.add(v);
    }

    public void newconnection(Vertex v1, Vertex v2, Double dist) {
        v1.addOutEdge(v2,dist);
        v2.addOutEdge(v1,dist);
    }

    //input estimation choice(int)
    public boolean A_Star(Vertex start, Vertex destination) {
        if (start==null || destination==null)
          return false;
        PriorityQueue<Vertex> Openlist = new PriorityQueue<Vertex>();
        ArrayList<Vertex> Closedlist = new ArrayList<Vertex>();
        Vertex Current;
        ArrayList<Vertex> CurrentNeighbors;
        Vertex Neighbor;
        //Initialize h with chosen heuristic
        for (int i =0; i<vertices.size();i++)
        {
            if(estimation = 1)
            vertices.get(i).seth(Manhattan(vertices.get(i),destination));
            else
        }



        start.setg(0.0);
        start.calculatef();
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

        while(!Openlist.isEmpty()){
            Current = Openlist.remove();

            if(Current == destination){
                return true;
            }

            Closedlist.add(Current);
            for (int i = 0; i < Current.getNeighbours().size(); i++) {

                double weight = Current.getNeighbourDistance().get(i);
                double tempGofV = Current.getg() + weight;
                if(tempGofV < Current.getNeighbours().get(i).getg()){
                    Current.getNeighbours().get(i).setPrev(Current);

                    Current.getNeighbours().get(i).setg(tempGofV);

                    Current.getNeighbours().get(i).calculatef();

                    //Current.getNeighbours().get(i).calculatef() = Current.getNeighbours().get(i).getg() +  Current.getNeighbours().get(i).geth();




                    //lav færdig

                    if((!Closedlist.contains(Current.getNeighbours().get(i)))&&(!Openlist.contains(Current.getNeighbours().get(i)))){
                        Openlist.offer(Current.getNeighbours().get(i));
                    } else if(Openlist.contains(Current.getNeighbours().get(i))){
                        Openlist.remove(Current.getNeighbours().get(i));
                        Openlist.offer(Current.getNeighbours().get(i));
                    }
                }
            }
        }



        //hint fra Line
        //tempgofv = Current.g + weight(current,v)
        //weight = current.getNeighborDistance.get(i)



        return false;
    }

    public Double Manhattan(Vertex from,Vertex goal){
        //Implement this

        double distance = Math.abs(goal.getx()-from.getx()) + Math.abs(goal.gety()-from.gety());
        return distance;
    }

    public Double Euclidean( Vertex from,Vertex to){
        //Implement this

        double x = to.getx()-from.getx();
        double y = to.gety()-from.gety();

        double distance = Math.sqrt((x*x)+(y*y));
        return distance;
    }
}

class Vertex implements Comparable<Vertex>{
    private String id;
    private ArrayList<Vertex> Neighbours=new ArrayList<Vertex>();
    private ArrayList<Double> NeighbourDistance =new ArrayList<Double>();
    private Double f;
    private Double g;
    private Double h;
    private Integer x;
    private Integer y;
    private Vertex prev =null;

    public Vertex(String id, int x_cor,int y_cor){
        this.id = id;
        this.x = x_cor;
        this.y = y_cor;
        f = Double.POSITIVE_INFINITY;
        g = Double.POSITIVE_INFINITY;
        h = 0.0;
    }
    public void addOutEdge(Vertex toV, Double dist){
        Neighbours.add(toV);
        NeighbourDistance.add(dist);
    }
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
        f=g+h; }

    public Double getg() { return g; }

    public void setg(Double newg){ g=newg; }
    public Double geth(){ return h; }
    public void seth(Double estimate){ h=estimate; }
    public Vertex getPrev() { return prev; }
    public void setPrev(Vertex v){prev=v;}
    public void printVertex(){
        System.out.println(id + " g: "+g+ ", h: "+h+", f: "+f);
    }

    @Override
    public int compareTo(Vertex vertex) {
        if (this.getf() > vertex.getf())
            return 1;
        if (this.getf() < vertex.getf())
            return -1;
        return 0;
//Implement this
//compare f
    }
}
