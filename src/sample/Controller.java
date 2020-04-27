package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Controller {

    private AStarGraph graphmodel;

    public Controller(AStarGraph graphmodel) {
        this.graphmodel = graphmodel;
    }

    @FXML
    void startVertexChoice(ActionEvent event) {
              

    }

    @FXML
    void destinationVertexChoice(ActionEvent event) {

    }

    @FXML
    void exitButton(ActionEvent event) {
        System.out.println("test");
        Platform.exit();
    }

}
