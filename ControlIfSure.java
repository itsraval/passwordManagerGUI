
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class ControlIfSure {

    private static void y(Stage window){
        // yes button
        mainPage.pass = true;
        mainPage.updateInactive();
        window.close();
    }

    private static void n(Stage window){
        // no button
        mainPage.pass = false;
        mainPage.updateInactive();
        window.close();
    }

    public static void setRow(GridPane layout) {
        // Set row grid
        RowConstraints row = new RowConstraints(70);
        layout.getRowConstraints().add(row);

        row = new RowConstraints(70);
        layout.getRowConstraints().add(row);

        row = new RowConstraints(70);
        layout.getRowConstraints().add(row);
    }

    public static void setColumn(GridPane layout) {
        // Set column grid
        ColumnConstraints column = new ColumnConstraints(15);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(30);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(140);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(15);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(15);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(140);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(30);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(15);
        layout.getColumnConstraints().add(column);
    }

    private static void centralPage(Stage window, GridPane layout, int k){
        //title
        Label title = new Label("Are You Sure?");
        title.getStyleClass().add("title");
        GridPane.setHalignment(title, HPos.CENTER);
        layout.add(title, 2, 0, 4, 1);

    
        Label text = new Label();
        if(k==0){//change credentials
            text.setText("Do you want to change your\n\tlogin credentials?");
        }else if(k==1){//delete all 
            text.setText("Do you want to delete all accounts\n    and credentials permanently?");
        }else if(k==2){//delete one account credentials 
            text.setText("Do you want to delete the account\n\tcredentials permanently?");
        }
        text.setStyle("-fx-font-size: 20px;");
        text.requestFocus();
        GridPane.setHalignment(text, HPos.CENTER);
        layout.add(text, 2, 1, 4, 1);
        

        Button yes = new Button("Yes");
        yes.getStyleClass().add("button-default");
        yes.setOnAction(e -> y(window));
        GridPane.setHalignment(yes, HPos.CENTER);
        layout.add(yes, 1, 2, 2, 1);

        Button no = new Button("No");
        no.getStyleClass().add("button-default");
        no.setOnAction(e -> n(window));
        GridPane.setHalignment(no, HPos.CENTER);
        layout.add(no, 5, 2, 2, 1);
    }
  
    
    public static void display(int k){
        mainPage.updateInactive();
        Stage window = new Stage();
        window.getIcons().add(new Image(ControlIfSure.class.getResourceAsStream("images/icon.png")));
        window.initModality(Modality.APPLICATION_MODAL);
        window.resizableProperty().setValue(false);

        BorderPane page = new BorderPane();
        page.getStyleClass().add("page");
               
        StackPane root = new StackPane(page);
        root.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(root, 420, 260);
        scene.setFill(Color.TRANSPARENT);
        titleBar.topBar(window, scene, page, "Are You Sure?", false);
       
        //grid setup
        GridPane layout = new GridPane();
        centralPage(window, layout, k);
        page.setCenter(layout);
        setRow(layout);
        setColumn(layout);
        //layout.setGridLinesVisible(true);
                 
        scene.getStylesheets().add(ControlIfSure.class.getResource("css/styles.css").toString());
        window.setScene(scene);
        window.showAndWait();
    }
}

