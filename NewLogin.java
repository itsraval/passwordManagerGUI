
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

public class NewLogin {

    private static void createCredentials(Stage window, File accountFile, TextField user, PasswordField password1, PasswordField password2){
        // Checks if the 2 passwords are equal
        if (password1.getText().equals(password2.getText())){
            Login.createCredentials(accountFile, user.getText(), password1.getText());
            window.close();
        } else{
            password1.setText("");
            password1.setId("wrong-password");
            password2.setText("");
            password2.setId("wrong-password");
        }
        
    }

    private static void newCredentials(Stage window, File accountFile, ArrayList<Account> cText, Login log, TextField user, PasswordField password1, PasswordField password2){
        // Checks if the 2 passwords are equal
        if (password1.getText().equals(password2.getText())){
            log.changeLoginUserPassword(accountFile, cText, user.getText(), password1.getText());
            window.close();
        } else{
            password1.setText("");
            password1.setId("wrong-password");
            password2.setText("");
            password2.setId("wrong-password");
        }
        
    }

    private static void setRow(GridPane layout) {
        // Set row grid
        for (int i = 0; i < 5; i++) {
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

    private static void centralPage(Stage window, GridPane layout, File accountFile, ArrayList<Account> cText, Login log, int k){
        // title
        Label title = new Label("New Credetials");
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

        //password re-type
        PasswordField passwordInput2 = new PasswordField();
        passwordInput2.setPromptText("password (re-type)");
        passwordInput2.setFocusTraversable(false);
        layout.add(passwordInput2, 1, 3, 4, 1);

        //login
        Button submit = new Button("Submit");
        submit.getStyleClass().add("button-default");
        // create new credentials (first login)
        if (k==0) submit.setOnAction(e -> createCredentials(window, accountFile, userInput, passwordInput, passwordInput2));
        // edit current credentials
        else if (k==1) submit.setOnAction(e -> newCredentials(window, accountFile, cText, log, userInput, passwordInput, passwordInput2));
        
        layout.add(submit, 2, 4, 2, 1);

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
                    passwordInput2.requestFocus();
                }
            }
        });

        passwordInput2.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    submit.requestFocus();
                    if (k==0) createCredentials(window, accountFile, userInput, passwordInput, passwordInput2);
                    // edit current credentials
                    else if (k==1) newCredentials(window, accountFile, cText, log, userInput, passwordInput, passwordInput2);
                }
            }
        });
    }

    public static void display(File accountFile, ArrayList<Account> cText, Login log, int k){
        Stage window = new Stage();
        window.getIcons().add(new Image(NewLogin.class.getResourceAsStream("images/icon.png")));
        window.initModality(Modality.APPLICATION_MODAL);
        window.resizableProperty().setValue(false);

        BorderPane page = new BorderPane();
        page.getStyleClass().add("page");
               
        StackPane root = new StackPane(page);
        root.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(root, 320, 410);       
        scene.setFill(Color.TRANSPARENT);
        titleBar.topBar(window, scene, page, "New Credetials", false);

        //grid setup
        GridPane layout = new GridPane();
        centralPage(window, layout, accountFile, cText, log, k);
        page.setCenter(layout);
        setRow(layout);
        setColumn(layout);
        //layout.setGridLinesVisible(true);
        
        // scene setup
        scene.getStylesheets().add(NewLogin.class.getResource("css/styles.css").toString());
        window.setScene(scene);
        window.show();
    }
}
