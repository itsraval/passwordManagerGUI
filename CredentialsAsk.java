
import java.io.File;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.stage.Modality;
import javafx.stage.Stage;
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

public class CredentialsAsk {
    
    private static void checkCredentials(Stage window, File accountFile, ArrayList<Account> cText, Login log, TextField user, PasswordField password){
        // Checks if the credentials are right
        if (log.checkPassword(password.getText()) && log.checkUser(user.getText())) {
            window.close();
            NewLogin.display(accountFile, cText, log, 1);
        }else{
            user.setId("wrong-password");
            user.setText("");
            password.setId("wrong-password");
            password.setText("");
        }
        mainPage.updateInactive();
    }

    private static void setRow(GridPane layout) {
        // Set row grid
        for (int i = 0; i < 4; i++) {
            RowConstraints row = new RowConstraints(70);
            layout.getRowConstraints().add(row);
        }
    }

    private static void setColumn(GridPane layout) {
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

    private static void centralPage(Stage window, GridPane layout, File accountFile, ArrayList<Account> cText, Login log){
        // title
        Label title = new Label("Check Credetials");
        title.getStyleClass().add("title");
        GridPane.setHalignment(title, HPos.CENTER);
        layout.add(title, 1, 0, 4, 1);

        //user
        TextField userInput = new TextField();
        userInput.setPromptText("email@example.com");
        userInput.setFocusTraversable(false);
        layout.add(userInput, 1, 1, 4, 1);

        //password
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("password");
        passwordInput.setFocusTraversable(false);
        layout.add(passwordInput, 1, 2, 4, 1);

        //login
        Button check = new Button("Submit");
        check.getStyleClass().add("button-default");
        check.setOnAction(e -> checkCredentials(window, accountFile, cText, log, userInput, passwordInput));
        layout.add(check, 2, 3, 2, 1);

        userInput.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    passwordInput.requestFocus();
                }
            }
        });

        passwordInput.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    check.requestFocus();
                    checkCredentials(window, accountFile, cText, log, userInput, passwordInput);
                }
            }
        });
    }

    public static void display(File accountFile, ArrayList<Account> cText, Login log){
        mainPage.updateInactive();
        Stage window = new Stage();
        window.getIcons().add(new Image(CredentialsAsk.class.getResourceAsStream("images/icon.png")));
        window.initModality(Modality.APPLICATION_MODAL);
        window.resizableProperty().setValue(false);

        BorderPane page = new BorderPane();
        page.getStyleClass().add("page");
               
        StackPane root = new StackPane(page);
        root.setPadding(new Insets(10, 10, 10, 10));
        
        Scene scene = new Scene(root, 320, 340);
        scene.setFill(Color.TRANSPARENT);       
        titleBar.topBar(window, scene, page, "Check Credetials", false);

        //grid setup
        GridPane layout = new GridPane();
        centralPage(window, layout, accountFile, cText, log);
        page.setCenter(layout);
        setRow(layout);
        setColumn(layout);
        //layout.setGridLinesVisible(true);
        
        // scene setup
        scene.getStylesheets().add(CredentialsAsk.class.getResource("css/styles.css").toString());
        window.setScene(scene);
        window.show();
    }
}
