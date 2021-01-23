

import java.io.File;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class addAccount {

    private static String deafult = "file:/C:/Users/user/AppData/Roaming/Code/User/workspaceStorage/890da051a17d40c7eba4908a196c4e42/redhat.java/jdt_ws/Prog_JAVA_d5676c0f/bin/src/passwordManagerGUI/images/image-default.png";
    // default logo image file path

    private static void add(BorderPane page, GridPane layout, File accountFile, ArrayList<Account> cText, Login log, String site, String url, String user, String email, String password, String pin){
        // add CALL
        // validate input
        if (site.equals("")) site = "!";
        if (url.equals("")) url = "!";
        if (user.equals("")) user = "!";
        if (email.equals("")) email = "!";
        if (password.equals("")) password = "!";
        if (pin.equals("")) pin = "!";
        site = site.replace(' ', '~');
        url = url.replace(' ', '~');
        user = user.replace(' ', '~');
        password = AES.encrypt(password);
        pin = AES.encrypt(pin);

        String newUrlp2 = IconCreator.imageUrl(url, site);
        String newUrl = "file:/" + newUrlp2;
        
        newUrl = newUrl.replace('\\', '/');
        Account a;
        if (newUrlp2 == null) a = new Account(site, deafult, url, user, email, password, pin);
        else a = new Account(site, newUrl, url, user, email, password, pin);

        cText.add(a);
        log.accountFileUpdate(accountFile, cText);
        mainPage.updateMainPageAdd(accountFile, page, layout, log, a);
        mainPage.updateInactive();
    }

    private static void setRow(GridPane layout){
        // Set row grid
        RowConstraints row = new RowConstraints(60);
        layout.getRowConstraints().add(row);

        row = new RowConstraints(110);
        layout.getRowConstraints().add(row);

        for (int i = 0; i < 7; i++) {
            row = new RowConstraints(70);
            layout.getRowConstraints().add(row);
        }
    }

    private static void setColumn(GridPane layout){
        // Set column grid
        ColumnConstraints column = new ColumnConstraints(15);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(65);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(105);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(30);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(105);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(15);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(35);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(15);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(15);
        layout.getColumnConstraints().add(column);
    }

    private static void centralPage(BorderPane page, GridPane mainPageGrid, File accountFile, ArrayList<Account> cText, GridPane layout, Login log){   
        //title
        Label title = new Label("Add an Account");
        title.requestFocus();
        title.getStyleClass().add("title");
        GridPane.setHalignment(title, HPos.CENTER);
        layout.add(title, 2, 1, 3, 1);

        //site
        TextField site = new TextField();
        site.setPromptText("site name");
        site.setFocusTraversable(true);
        layout.add(site, 2, 2, 3, 1);    

        //url
        TextField url = new TextField();
        url.setPromptText("www.example.com");
        url.setFocusTraversable(true);
        layout.add(url, 2, 3, 3, 1);    

        //user
        TextField user = new TextField();
        user.setPromptText("myUser");
        user.setFocusTraversable(true);
        layout.add(user, 2, 4, 3, 1);
        
        //email
        TextField email = new TextField();
        email.setPromptText("email@example.com");
        email.setFocusTraversable(true);
        layout.add(email, 2, 5, 3, 1);    

        //password
        PasswordField password = new PasswordField();
        TextField passwordText = new TextField();
        passwordText.setVisible(false);
        passwordText.setPromptText("password");
        password.setPromptText("password");
        password.setFocusTraversable(true);
        layout.add(password, 2, 6, 3, 1);
        layout.add(passwordText, 2, 6, 3, 1);

        //see password images
        Image i1 = new Image(addAccount.class.getResource("images/visibility-OFF-icon.png").toString());
        Image i2 = new Image(addAccount.class.getResource("images/visibility-ON-icon.png").toString());

        //see password
        ImageView seePas = new ImageView(i1);
        seePas.getStyleClass().add("showHand");
        seePas.setFitHeight(26);
        seePas.setFitWidth(26);
        seePas.setOnMouseClicked(e -> Utility.changeImageWithText(seePas, i1, i2, password, passwordText));
        layout.add(seePas, 6, 6, 1, 1);

        //pin
        PasswordField pin = new PasswordField();
        TextField pinText = new TextField();
        pinText.setVisible(false);
        pinText.setPromptText("pin");
        pin.setPromptText("pin");
        pin.setFocusTraversable(true);
        layout.add(pin, 2, 7, 3, 1);
        layout.add(pinText, 2, 7, 3, 1);

        //see pin
        ImageView seePin = new ImageView(i1);
        seePin.getStyleClass().add("showHand");
        seePin.setFitHeight(26);
        seePin.setFitWidth(26);
        seePin.setOnMouseClicked(e -> Utility.changeImageWithText(seePin, i1, i2, pin, pinText));
        layout.add(seePin, 6, 7, 1, 1);

        //add
        Button add = new Button("Add");
        add.setFocusTraversable(true);
        add.getStyleClass().add("button-default");
        add.setOnAction(e -> add(page, mainPageGrid, accountFile, cText, log, site.getText(), url.getText(), user.getText(), email.getText(), Utility.chooseInput(password, passwordText), Utility.chooseInput(pin, pinText)));
        layout.add(add, 1, 8, 2, 1);

        //add
        Button passGen = new Button("Password Generator");
        passGen.setFocusTraversable(true);
        passGen.getStyleClass().add("button-default");
        passGen.setOnAction(e -> passwordGenerator.display());
        layout.add(passGen, 4, 8, 4, 1);

        site.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    url.requestFocus();
                }
            }
        });

        url.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    user.requestFocus();
                }
            }
        });

        user.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    email.requestFocus();
                }
            }
        });

        email.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    password.requestFocus();
                }
            }
        });

        password.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    pin.requestFocus();
                }
            }
        });

        pin.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    add.requestFocus();
                    add(page, mainPageGrid, accountFile, cText, log, site.getText(), url.getText(), user.getText(), email.getText(), Utility.chooseInput(password, passwordText), Utility.chooseInput(pin, pinText));
                }
            }
        });
    }

    public static GridPane display(BorderPane page, GridPane mainPageGrid, File accountFile, ArrayList<Account> cText, Login log){
        // Display this page
      
        //grid setup
        GridPane layout = new GridPane();
        centralPage(page, mainPageGrid, accountFile, cText, layout, log);
        setRow(layout);
        setColumn(layout);
        //layout.setGridLinesVisible(true);

        return layout;
    }
}
