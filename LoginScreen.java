import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class LoginScreen extends Application{
    final public static String filePath = "C:\\passwordManagerGUI\\accountsPasswordManger.txt"; // account file path
    final public static String fileSettingsPath = "C:\\passwordManagerGUI\\Settings.txt"; // account file path
    private static ArrayList<Account> cText = new ArrayList<Account>();// account array
    private static int numerOfLogin = 0;// number of try to login with no success

    private static boolean check (TextField userInput, PasswordField passwordInput, String cUser, String cPassword){
        // check inside checkCredetials only for login
        if (SHA256.hash(userInput.getText()).equals(cUser) && SHA256.hash(passwordInput.getText()).equals(cPassword)){
            return true;
        }
        else {
            // if credentials are wrong it will set the textfield red
            userInput.setId("wrong-password");
            userInput.setText("");
            passwordInput.setId("wrong-password");
            passwordInput.setText("");
            return false;
        }
    }

    private static void checkCredentials(File accountFile, File settingsFile, ArrayList<Account> cText, Stage window, TextField userInput, PasswordField passwordInput, String cUser, String cPassword) {
        // Checks if the credentials for the login are right
        if (numerOfLogin == 5) {
            // qui 
            try{
                Scanner scan = new Scanner(settingsFile);
                for (int i=0; i<3; i++){
                    scan.nextLine();
                }
                String line = scan.nextLine();
                String[] lineParts = line.split(" ");
                if (lineParts[2].equals("ON")){
                    // send EMAIL
                    Email.sendEmail(lineParts[3]);
                }
                scan.close();
            }catch (Exception e){
                //e.printStackTrace();
            }

            window.close();
            System.exit(0);
        }
        if (check(userInput, passwordInput, cUser, cPassword)){
            Login log = new Login(userInput.getText(), passwordInput.getText());
            window.close();
            mainPage.display(accountFile, settingsFile, log, cText);// open main page
        }else numerOfLogin++;
    }

    private static void loginState(Stage window, TextField userInput, PasswordField passwordInput){
        // first login CALL
        try{
            File accountFile = new File(filePath);  
            if (!accountFile.exists()) {
                if (!accountFile.getParentFile().exists()) accountFile.getParentFile().mkdir();
                accountFile.createNewFile();
                Utility.createReadme();
                Utility.createSettingsFile();
                new File("C:\\passwordManagerGUI\\Icons").mkdir();
                userInput.setText("");
                passwordInput.setText("");
                firstLoginState();
                return;
            }
            Scanner fileScan = new Scanner(accountFile);
            if(!fileScan.hasNext()) {
                fileScan.close();
                userInput.setText("");
                passwordInput.setText("");
                firstLoginState();
                return;
            }
            String cUser = fileScan.nextLine();// setup user
            String cPassword = fileScan.nextLine();// setup password

            if (numerOfLogin==0){// here to solve a bug
                while (fileScan.hasNextLine()) {
                    String line = fileScan.nextLine();
                    if (!line.equals("")) {
                        // add the account from the file to the array
                        String accountFields[] = line.split("\\s+");
                        cText.add(new Account(accountFields[0], accountFields[1], accountFields[2], accountFields[3], accountFields[4], accountFields[5], accountFields[6]));
                    }              
                }
            }
            fileScan.close();
            File settingsFile = new File(fileSettingsPath); 
            
            checkCredentials(accountFile, settingsFile, cText, window, userInput, passwordInput, cUser, cPassword);            
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    private static void firstLoginState(){
        try{
            File accountFile = new File(filePath);  
            if (!accountFile.exists()) {
                if (!accountFile.getParentFile().exists()) accountFile.getParentFile().mkdir();
                accountFile.createNewFile();
                Utility.createReadme();
                Utility.createSettingsFile();
                new File("C:\\passwordManagerGUI\\Icons").mkdir();
                // FIRST LOGIN
                NewLogin.display(accountFile, null, null, 0);
            }
            Scanner fileScan = new Scanner(accountFile);
            if(!fileScan.hasNext()) {
                // FIRST LOGIN
                NewLogin.display(accountFile, null, null, 0);
            }
            fileScan.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    private static void setRow(GridPane layout) {
        // Set row grid
        RowConstraints row = new RowConstraints(95);
        layout.getRowConstraints().add(row);

        for (int i = 0; i < 2; i++) {
            row = new RowConstraints(70);
            layout.getRowConstraints().add(row);
        }

        row = new RowConstraints(75);
        layout.getRowConstraints().add(row);

        row = new RowConstraints(35);
        layout.getRowConstraints().add(row);
    }

    private static void setColumn(GridPane layout) {
        // Set column grid
        ColumnConstraints column = new ColumnConstraints(30);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(35);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(54);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(31);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(31);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(54);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(35);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(30);
        layout.getColumnConstraints().add(column);
    }

    private static void centralPage(Stage window, GridPane layout) {
        Image image = new Image(LoginScreen.class.getResource("images/icon2.png").toString());
        ImageView iw = new ImageView(image);
        iw.setFitHeight(80);
        iw.setFitWidth(63);
        layout.add(iw, 3, 0, 2, 1);

        // user
        TextField userInput = new TextField();
        userInput.setPromptText("email@example.com");
        userInput.setFocusTraversable(false);
        layout.add(userInput, 1, 1, 6, 1);

        // password
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("password");
        passwordInput.setFocusTraversable(false);
        layout.add(passwordInput, 1, 2, 6, 1);

        // login
        Button login = new Button("Login");
        login.getStyleClass().add("button-default");
        login.setOnAction(e -> loginState(window, userInput, passwordInput));
        layout.add(login, 2, 3, 4, 1);

        // first Login
        Label firstLogin = new Label("You don't have an account? Sign-In!");
        firstLogin.getStyleClass().add("first-login");      
        firstLogin.getStyleClass().add("showHand");      
        GridPane.setHalignment(firstLogin, HPos.CENTER);
        layout.add(firstLogin, 0, 4, 8, 1);

        firstLogin.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                firstLoginState();
            }
        });

        userInput.setOnKeyPressed((EventHandler<? super KeyEvent>) new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    passwordInput.requestFocus();
                }
            }
        });

        passwordInput.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    login.requestFocus();
                    loginState(window, userInput, passwordInput);
                }
            }
        });
    }

    @Override
    public void start(Stage window) throws Exception {
        window.getIcons().add(new Image(LoginScreen.class.getResourceAsStream("images/icon.png")));
        window.resizableProperty().setValue(false);

        BorderPane page = new BorderPane();
        page.getStyleClass().add("page");
               
        StackPane root = new StackPane(page);
        root.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(root, 320, 410);
        scene.setFill(Color.TRANSPARENT);
        titleBar.topBar(window, scene, page, "Login", false);

        //grid setup
        GridPane layout = new GridPane();
        centralPage(window, layout);
        page.setCenter(layout);
        setRow(layout);
        setColumn(layout);
        //layout.setGridLinesVisible(true);
        scene.getStylesheets().add(LoginScreen.class.getResource("css/styles.css").toString());
        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
