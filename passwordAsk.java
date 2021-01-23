
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDateTime;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


public class passwordAsk {

    private static void check(Stage window, PasswordField passwordInput, Login log){
        // check CALL
        if (log.checkPassword(passwordInput.getText())){
            mainPage.maxTime = LocalDateTime.now().plusMinutes(mainPage.accessTime);
            mainPage.pass = true;
            window.close();
        }else{
            // if credentials are wrong it will set the textfield red
            passwordInput.setId("wrong-password");
            passwordInput.setText("");
        }    
        mainPage.updateInactive();  
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
        ColumnConstraints column = new ColumnConstraints(30);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(35);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(85);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(85);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(35);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(30);
        layout.getColumnConstraints().add(column);
    }

    private static void centralPage(Stage window, GridPane layout, Login log){
        //title
        Label title = new Label("Password Checking");
        title.getStyleClass().add("title");
        GridPane.setHalignment(title, HPos.CENTER);
        layout.add(title, 1, 0, 4, 1);
        
        //password
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("password");
        layout.add(passwordInput, 1, 1, 4, 1);

        //login
        Button submit = new Button("Submit");
        submit.setOnAction(e -> check(window, passwordInput, log));
        GridPane.setHalignment(submit, HPos.CENTER);
        layout.add(submit, 2, 2, 2, 1);

        passwordInput.requestFocus();

        passwordInput.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    submit.requestFocus();
                    check(window, passwordInput, log);
                }
            }
        });
    }
    
    public static void display(Login log){
        mainPage.updateInactive();
        Stage window = new Stage();
        window.getIcons().add(new Image(passwordAsk.class.getResourceAsStream("images/icon.png")));
        window.initModality(Modality.APPLICATION_MODAL);
        window.resizableProperty().setValue(false);

        BorderPane page = new BorderPane();
        page.getStyleClass().add("page");
               
        StackPane root = new StackPane(page);
        root.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(root, 320, 260);
        scene.setFill(Color.TRANSPARENT);
        titleBar.topBar(window, scene, page, "Password Checking", false);
       
        //grid setup
        GridPane layout = new GridPane();
        centralPage(window, layout, log);
        page.setCenter(layout);
        setRow(layout);
        setColumn(layout);
        //layout.setGridLinesVisible(true);
                 
        scene.getStylesheets().add(passwordAsk.class.getResource("css/styles.css").toString());
        window.setScene(scene);
        window.showAndWait();
    }
}
