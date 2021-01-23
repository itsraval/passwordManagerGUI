

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
import javafx.stage.Stage;

public class info {

    private static void setRow(GridPane layout){
        // Set row grid
        RowConstraints row = new RowConstraints(70);
        layout.getRowConstraints().add(row);
        for (int i=0; i<6; i++){
            row = new RowConstraints(40);
            layout.getRowConstraints().add(row);
        }
        row = new RowConstraints(70);
        layout.getRowConstraints().add(row);
    }

    private static void setColumn(GridPane layout){
        // Set column grid
        ColumnConstraints column = new ColumnConstraints(25);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(65);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(170);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(65);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(25);
        layout.getColumnConstraints().add(column);
    }

    private static void centralPage(Stage window, GridPane layout){
        mainPage.updateInactive();
        //title
        Label title = new Label("Credit");
        title.getStyleClass().add("title");
        GridPane.setHalignment(title, HPos.CENTER);
        layout.add(title, 1, 0, 3, 1);

        Label about = new Label("The Password Manager was made using JavaFX.");
        GridPane.setHalignment(about, HPos.CENTER);
        layout.add(about, 1, 1, 3, 1);

        Label linkAzz = new Label("Logo made by Marco Azzari: https://azzari.dev");
        linkAzz.setOnMouseClicked(e -> Utility.openURL("https://azzari.dev"));
        linkAzz.getStyleClass().add("showHand");
        GridPane.setHalignment(linkAzz, HPos.CENTER);
        layout.add(linkAzz, 1, 2, 3, 1);

        Label linkGithub = new Label("\tPassword Manager repository on github:\nhttps://github.com/itsraval/passwordManagerGUI");
        linkGithub.setOnMouseClicked(e -> Utility.openURL("https://github.com/itsraval/passwordManagerGUI"));
        linkGithub.getStyleClass().add("showHand");
        GridPane.setHalignment(linkGithub, HPos.CENTER);
        layout.add(linkGithub, 1, 3, 3, 1);
        
        Label linkSite = new Label("www.alessandro.ravizzotti.tk");
        linkSite.setOnMouseClicked(e -> Utility.openURL("www.alessandro.ravizzotti.tk"));
        linkSite.getStyleClass().add("showHand");
        GridPane.setHalignment(linkSite, HPos.CENTER);
        layout.add(linkSite, 1, 4, 3, 1);

        Label production = new Label("an Alessandro Ravizzotti production");
        GridPane.setHalignment(production, HPos.CENTER);
        layout.add(production, 1, 5, 3, 1);

        Label copyright = new Label("Copyright © 2020");
        GridPane.setHalignment(copyright, HPos.CENTER);
        layout.add(copyright, 1, 6, 3, 1);
        
        //button
        Button ok = new Button("Ok");
        ok.getStyleClass().add("button-default");
        ok.setOnAction(e -> window.close());
        layout.add(ok, 2, 7, 1, 1);
    }

    public static void display(){
        // Display this page
        Stage window = new Stage();
        window.getIcons().add(new Image(info.class.getResourceAsStream("images/icon.png")));
        window.resizableProperty().setValue(false);

        BorderPane page = new BorderPane();
        page.getStyleClass().add("page");
               
        StackPane root = new StackPane(page);
        root.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(root, 370, 440);
        scene.setFill(Color.TRANSPARENT);
        titleBar.topBar(window, scene, page, "Credit", false);

        //grid setup
        GridPane layout = new GridPane();
        centralPage(window, layout);
        page.setCenter(layout);
        setRow(layout);
        setColumn(layout);
        //layout.setGridLinesVisible(true);

        scene.getStylesheets().add(info.class.getResource("css/styles.css").toString());
        window.setScene(scene);
        window.show();
    }
}
