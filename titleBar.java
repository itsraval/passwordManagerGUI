

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class titleBar {
    // Set the titleBar

    private static double x;// Mouse click x
    private static double y;// Mouse click y

    private static void draged(MouseEvent event){
        // Move the window when the title bar is dragged
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setX(event.getScreenX()-x);
        window.setY(event.getScreenY()-y);
    }

    private static void pressed(MouseEvent event){
        // Set x and y of mouse click
        x = event.getSceneX();
        y = event.getSceneY();
    }


    private static void newCredentials(Login log, File accountFile, ArrayList<Account> cText){
        // new credentials call
        mainPage.updateInactive();
        ControlIfSure.display(0);
        if (mainPage.pass){
            // aprire controllo login
            CredentialsAsk.display(accountFile, cText, log);
        }
        mainPage.pass = false;
    }

    private static void export(ArrayList<Account> cText){
        // export the given array
        String path = "C:\\Users\\user\\Downloads\\Decrypted_Passwords.txt";// file exported location
        File accounts = new File(path);

        String text = "";
        for (Account account : cText) {// makes text
            text = text + account.printExport();
        }
        try{
            // write to file
            if (!accounts.exists()) {
                if (!accounts.getParentFile().exists()) accounts.getParentFile().mkdir();
                accounts.createNewFile();
            }
            FileWriter fWriter = new FileWriter(accounts);
            fWriter.write(text);
            fWriter.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    private static void exportState(Login log, ArrayList<Account> cText){
        // export CALL
        mainPage.updateInactive();
        passwordAsk.display(log);
        if (mainPage.pass){
            export(cText);
        }
        mainPage.pass = false;
    }
    
    private static void deleteAll(Stage window, Login log, File fileAccount){
        // delete all CALL
        mainPage.updateInactive();
        ControlIfSure.display(1);
        if (mainPage.pass){
            passwordAsk.display(log);
            if (mainPage.pass){
                window.close();
                fileAccount.delete();// delete file
            }
        }
        mainPage.pass = false;
    }

    private static void settingsState(Login log, File settingsFile, int accessTime, boolean sendEmail, boolean inactive){
        mainPage.updateInactive();
        if (mainPage.inTime()) {
            Settings.display(settingsFile, accessTime, sendEmail, inactive);
        } else {
            passwordAsk.display(log);
            if (mainPage.inTime()) {
                Settings.display(settingsFile, accessTime, sendEmail, inactive);
            }
        }
        mainPage.pass = false;
    }

    public static void topBar(Stage window, Scene scene, BorderPane page, String title, boolean maximized){
        // Default title bar
        window.initStyle(StageStyle.TRANSPARENT);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.SOMETIMES);
        scene.getStylesheets().add(titleBar.class.getResource("css/styles-titleBar.css").toString());

        Label paddingIcon = new Label(" ");
        paddingIcon.getStyleClass().add("label-titleBar");
        Image image = new Image(titleBar.class.getResource("images/titleBar-icon.png").toString());
        ImageView icon = new ImageView(image);// logo icon
        icon.setFitHeight(25);
        icon.setFitWidth(20);

        Label titlePage = new Label("  " + title);// window title
        titlePage.getStyleClass().add("label-titleBar");
        titlePage.setMinHeight(27);

        Button mini = new Button("―");// minimize
        mini.setFocusTraversable(false);
        mini.getStyleClass().add("button-titleBar");
        mini.setOnAction(e -> window.setIconified(true));


        Button close = new Button("✕");// close
        close.setFocusTraversable(false);
        close.getStyleClass().add("button-titleBar");
        close.setOnAction(e -> window.close());
        
        HBox topBar = new HBox(paddingIcon, icon, titlePage, spacer, mini, close);
        topBar.setFocusTraversable(false);
        topBar.setStyle("-fx-background-color: #363636;");
        page.setTop(topBar);

        topBar.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                draged(event);
            }
        });
       
        topBar.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                pressed(event);
            }
        });
    }

    public static void topBarMenu(Stage window, Scene scene, BorderPane page, ArrayList<Account> cText, Login log, File accountFile, File settingsFile, boolean maximized){
        // Title bar with menu bar
        window.initStyle(StageStyle.TRANSPARENT);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.SOMETIMES);
        scene.getStylesheets().add(titleBar.class.getResource("css/styles-titleBar.css").toString());

        Label paddingIcon = new Label(" ");
        paddingIcon.getStyleClass().add("label-titleBar");
        Image image = new Image(titleBar.class.getResource("images/titleBar-icon.png").toString());
        Label paddingIcon2 = new Label(" ");
        paddingIcon2.getStyleClass().add("label-titleBar");
        ImageView icon = new ImageView(image);// logo icon
        icon.setFitHeight(25);
        icon.setFitWidth(20);

        MenuBar leftBar = new MenuBar();

        Button mini = new Button("―");// minimize
        mini.setFocusTraversable(false);
        mini.getStyleClass().add("button-titleBar");
        mini.setOnAction(e -> window.setIconified(true));

        Button close = new Button("✕");// close
        close.setFocusTraversable(false);
        close.getStyleClass().add("button-titleBar");
        close.setOnAction(e -> window.close());
        
        HBox topBar = new HBox(paddingIcon, icon, paddingIcon2, leftBar, spacer, mini, close);
        topBar.setFocusTraversable(false);
        topBar.setStyle("-fx-background-color: #363636;");
        page.setTop(topBar);

        // File BAR ----------------------------------
        Menu fileMenu = new Menu("File");
        leftBar.getMenus().add(fileMenu);

        MenuItem editCredential = new MenuItem("Edit Credentials");
        editCredential.setOnAction(e -> newCredentials(log, accountFile, cText));

        MenuItem exportFile = new MenuItem("Export Passoword File");
        exportFile.setOnAction(e -> exportState(log, cText));

        MenuItem deleteAll = new MenuItem("Delete All Your Data");      
        deleteAll.setOnAction(e -> deleteAll(window, log, accountFile));

        MenuItem settings = new MenuItem("Settings");
        settings.setOnAction(e -> settingsState(log, settingsFile, mainPage.accessTime, mainPage.sendEmail, mainPage.inactive));

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e -> window.close());
        
        fileMenu.getItems().addAll(editCredential, exportFile, deleteAll, settings, exit);

        // Help BAR ----------------------------------
        Menu helpMenu = new Menu("Help");
        leftBar.getMenus().add(helpMenu);

        MenuItem welcome = new MenuItem("Welcome");      
        welcome.setOnAction(e -> Utility.openURL("https://pswmanager.tk"));

        MenuItem documentation = new MenuItem("Documentation");
        documentation.setOnAction(e -> Utility.openURL("https://github.com/itsraval/passwordManagerGUI"));

        MenuItem reportIssue = new MenuItem("Report Issue");      
        reportIssue.setOnAction(e -> Utility.openURL("https://github.com/itsraval/passwordManagerGUI/issues"));

        MenuItem privacyStatement = new MenuItem("Privacy Statement");      
        privacyStatement.setOnAction(e -> PStatement.display());

        MenuItem credit = new MenuItem("Credit");
        credit.setOnAction(e -> info.display());

        MenuItem about = new MenuItem("About");
        about.setOnAction(e -> About.display());

        helpMenu.getItems().addAll(welcome, documentation, reportIssue, privacyStatement, credit, about);

        topBar.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                draged(event);
            }
        });
       
        topBar.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                pressed(event);
            }
        });
    }
}
