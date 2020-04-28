package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import java.util.Stack;

public class Controller {

    AStarGraph graphModel = CreateGraph();
    Vertex vertex;

    public AStarGraph CreateGraph() {
        System.out.println("creating graph");

        AStarGraph MyMaze = new AStarGraph();

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
    }

    // print vertex række og afstand i GUI text felt
    public void printMyMaze(Vertex destination) {
            Vertex pvertex = destination;
            printArea.appendText("To " + destination.getid() + " Shortest length: " + destination.getf() + "\n");
            Stack<Vertex> Path = new Stack<>();
            int limit=0;

            while (pvertex!=null) {
                Path.push(pvertex);
                pvertex=pvertex.getPrev();
            }
            if(!Path.isEmpty())
                limit = Path.size();
            for(int i=0 ; i<limit-1 ; i++)
                printArea.appendText(Path.pop().getid() +" - > ");
            if (limit>0) {
                printArea.appendText(Path.pop().getid() + "\n");
            }
        }

    public void initialize() {
        System.out.println();
        comboStart.getItems().addAll(graphModel.getVertices());
        comboDestination.getItems().addAll(graphModel.getVertices());

        // tilføjer "Manhattan" og "Euclidean" til listen som derefter bruges i Choose method knappen (comboEstimation)
        ObservableList<String> estimationMethod = FXCollections.observableArrayList("Manhattan", "Euclidean");
        comboEstimation.getItems().addAll(estimationMethod);
    }

    @FXML
    ComboBox<Vertex> comboStart;

    @FXML
    ComboBox<Vertex> comboDestination;

    @FXML
    ComboBox<String> comboEstimation;

    @FXML
    TextArea printArea;

    @FXML
    // choose vertex start button
    void startVertexChoice(ActionEvent event) {
        System.out.println("Start vertex: " + comboStart.getValue());
    }

    @FXML
    // choose vertex destination button
    void destinationVertexChoice(ActionEvent event) {
        System.out.println("Destination vertex: " + comboDestination.getValue());
    }

    @FXML
    // choose method button
    void estimationChoice(ActionEvent event) {
        System.out.println("Estimation method: " + comboEstimation.getValue());
    }

    @FXML
    // start pathfinder button
    void startPathfinder(ActionEvent event) {
        graphModel.A_Star(comboStart.getValue(), comboDestination.getValue(), comboEstimation.getValue());
    }

    @FXML
    // print path button
    void printPath(ActionEvent event) {
        printMyMaze(comboDestination.getValue());
    }

    @FXML
    // exit button
    void exitButton(ActionEvent event) {
        System.out.println("EXIT");
        Platform.exit();
    }
}
