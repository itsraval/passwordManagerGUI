
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

public class PStatement {
    
    private static void setRow(GridPane layout){
        // Set row grid
        RowConstraints row = new RowConstraints(70);
        layout.getRowConstraints().add(row);

        for (int i=0; i<2; i++){
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
        //title
        Label title = new Label("Privacy Statement");
        title.getStyleClass().add("title");
        GridPane.setHalignment(title, HPos.CENTER);
        layout.add(title, 1, 0, 3, 1);

        Label line1 = new Label("All your DATA are on your own device.");
        GridPane.setHalignment(line1, HPos.CENTER);
        layout.add(line1, 1, 1, 3, 1);

        Label line2 = new Label("We have no access to your data.");
        GridPane.setHalignment(line2, HPos.CENTER);
        layout.add(line2, 1, 2, 3, 1);
        
        //button
        Button ok = new Button("Ok");
        ok.getStyleClass().add("button-default");
        ok.setOnAction(e -> window.close());
        layout.add(ok, 2, 3, 1, 1);
    }

    public static void display(){
        // Display this page
        mainPage.updateInactive();
        Stage window = new Stage();
        window.getIcons().add(new Image(PStatement.class.getResourceAsStream("images/icon.png")));
        window.resizableProperty().setValue(false);
        
        BorderPane page = new BorderPane();
        page.getStyleClass().add("page");
               
        StackPane root = new StackPane(page);
        root.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(root, 370, 280);
        scene.setFill(Color.TRANSPARENT);
        titleBar.topBar(window, scene, page, "Privacy Statement", false);

        //grid setup
        GridPane layout = new GridPane();
        centralPage(window, layout);
        page.setCenter(layout);
        setRow(layout);
        setColumn(layout);
        //layout.setGridLinesVisible(true);

        scene.getStylesheets().add(PStatement.class.getResource("css/styles.css").toString());
        window.setScene(scene);
        window.show();
    }
}
