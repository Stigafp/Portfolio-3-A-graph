package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import javax.swing.*;
import java.util.Stack;

public class Controller {

    AStarGraph graphModel = CreateGraph();

    public AStarGraph CreateGraph() {

        System.out.println("creating graph");

        AStarGraph MyMaze = new AStarGraph();
        // Make the graph provided to you in the diagram and table
        //The vertices must be constructed like A
        Vertex A = new Vertex ("A",0,4);
        Vertex B = new Vertex ("B", 1,7);
        Vertex C = new Vertex ("C", 4,0);
        Vertex D = new Vertex ("D", 3,7);
        Vertex G = new Vertex ("G", 7,2);
        Vertex I = new Vertex ("I", 9,2);
        Vertex E = new Vertex ("E", 3,3);
        Vertex F = new Vertex ("F", 6,6);
        Vertex H = new Vertex ("H", 8,7);
        Vertex J = new Vertex ("J", 11,5);

        MyMaze.addvertex(A);
        MyMaze.addvertex(B);
        MyMaze.addvertex(C);
        MyMaze.addvertex(D);
        MyMaze.addvertex(G);
        MyMaze.addvertex(I);
        MyMaze.addvertex(E);
        MyMaze.addvertex(F);
        MyMaze.addvertex(H);
        MyMaze.addvertex(J);

        MyMaze.newconnection(A,B, 3.41);
        MyMaze.newconnection(A,C, 6.82);
        MyMaze.newconnection(B,D, 2.0);
        MyMaze.newconnection(C,G,4.41);
        MyMaze.newconnection(C,I,4.82);
        MyMaze.newconnection(D,E,4.0);
        MyMaze.newconnection(E,F,6.23);
        MyMaze.newconnection(F,G,4.41);
        MyMaze.newconnection(F,H,3.82);
        MyMaze.newconnection(G,H,5.41);
        MyMaze.newconnection(G,I,2.82);
        MyMaze.newconnection(H,J,4.41);
        MyMaze.newconnection(I,J,3.82);

        return MyMaze;

        //Vertex J=null; //This must be changed

        // choise 1 skal være bruger input i GUI
        // A og J skal også være bruger input i GUI

        /*
        if(MyMaze.A_Star(null, null, null))
        {
            System.out.println("Found a path");
            Vertex pvertex = J;
            Stack<Vertex> Path = new Stack<>();
            int limit=0;
            while (pvertex!=null)
            {
                Path.push(pvertex);
                pvertex=pvertex.getPrev();
            }
            if(!Path.isEmpty())
                limit =Path.size();
            for(int i=0;i<limit-1;i++)
                System.out.print(Path.pop().getid() +" - > ");
            if (limit>0)
                System.out.println(Path.pop().getid());

        }
        else
            System.out.println("DID NOT FIND A PATH!!");

        return MyMaze;
        */
    }

    public String printMyMaze(AStarGraph MyMaze, Vertex start, Vertex destination, String method) {

        if(MyMaze.A_Star(start, destination, method))
        {
            System.out.println("Found a path");
            Vertex pvertex = destination;
            Stack<Vertex> Path = new Stack<>();
            int limit=0;
            String temp = Path.pop().getid();

            while (pvertex! = null)
            {
                Path.push(pvertex);
                pvertex=pvertex.getPrev();

            }
            if(!Path.isEmpty())
                limit = Path.size();
            for(int i=0 ; i<limit-1 ; i++)
                System.out.print(Path.pop().getid() +" - > ");
                printArea.appendText(Path.pop().getid());

            if (limit>0) {
                System.out.println(Path.pop().getid());

            }

        } else {
            return "DID NOT FIND A PATH!!";
        }




    }


    @FXML
    ComboBox comboStart;

    @FXML
    ComboBox comboDestination;

    @FXML
    ComboBox comboEstimation;

    @FXML
    TextArea printArea;






    public void initialize() {
        System.out.println();
        comboStart.getItems().addAll(graphModel.getVertices());
        comboDestination.getItems().addAll(graphModel.getVertices());
        ObservableList<String> estimationMethod = FXCollections.observableArrayList("Manhattan", "Euclidean");
        comboEstimation.getItems().addAll(estimationMethod);

    }

    @FXML
    void startVertexChoice(ActionEvent event) {
        System.out.println("Start vertex: " + comboStart.getValue());

    }

    @FXML
    void destinationVertexChoice(ActionEvent event) {
        System.out.println("Destination vertex: " + comboDestination.getValue());

    }

    @FXML
    void estimationChoice(ActionEvent event) {
        System.out.println("Estimation method: " + comboEstimation.getValue());

    }

    @FXML
    void startPathfinder(ActionEvent event) {
        graphModel.A_Star((Vertex)comboStart.getValue(), (Vertex)comboDestination.getValue(), (String) comboEstimation.getValue());
    }

    @FXML
    void printPath(ActionEvent event) {
        System.out.println("test print path");
        printArea.appendText("test");
        printArea.appendText(printMyMaze(graphModel, (Vertex)comboStart.getValue(), (Vertex)comboDestination.getValue(), (String) comboEstimation.getValue()));

    }


    @FXML
    void exitButton(ActionEvent event) {
        System.out.println("test");
        Platform.exit();
    }
}
