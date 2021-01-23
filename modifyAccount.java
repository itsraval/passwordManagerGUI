

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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class modifyAccount {

    private static void modify(BorderPane page, GridPane layout, File accountFile, ArrayList<Account> cText, Login log, Account a, String site, String url, String user, String email, String password, String pin){
        // modify CALL
        // validate inputs
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

        a.edit(site, url, user, email, password, pin);
        log.accountFileUpdate(accountFile, cText);
        mainPage.updateMainPageEdit(accountFile, page, layout, log, a);
        mainPage.updateInactive();
    }

    private static void editIcon(BorderPane page, GridPane layout, File accountFile, ArrayList<Account> cText, Login log, Account a){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PNG", "*.png"),
                    new FileChooser.ExtensionFilter("JPEG", "*.JPEG"),
                    new FileChooser.ExtensionFilter("BMP", "*.BMP"),
                    new FileChooser.ExtensionFilter("GIF", "*.GIF"));

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile!=null){
            a.setImage(new Image(selectedFile.toURI().toString()));
            log.accountFileUpdate(accountFile, cText);
            mainPage.updateMainPageEdit(accountFile, page, layout, log, a);
        }
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
                                    
    private static void centralPage(BorderPane page, GridPane mainPageGrid, File accountFile, ArrayList<Account> cText, GridPane layout, Login log, Account a){       
        ImageView icon = new ImageView(a.getImage());
        icon.getStyleClass().add("showHand");
        icon.setFitHeight(100);
        icon.setFitWidth(100);
        icon.setOnMouseClicked(e -> Utility.openURL(Utility.accountString(a.getUrl())));
        GridPane.setHalignment(icon, HPos.CENTER);
        layout.add(icon, 2, 1, 3, 1);

        //change icon
        ImageView editIcon = new ImageView(new Image(modifyAccount.class.getResource("images/modify-icon.png").toString()));
        editIcon.getStyleClass().add("showHand");
        editIcon.setFitHeight(26);
        editIcon.setFitWidth(26);
        editIcon.setOnMouseClicked(e -> editIcon(page, mainPageGrid, accountFile, cText, log, a));
        layout.add(editIcon, 6, 1, 1, 1);
        

        //site
        TextField site = new TextField(Utility.accountString(a.getSite()));
        site.setPromptText("site name");
        site.setFocusTraversable(true);
        layout.add(site, 2, 2, 3, 1);    

        //url
        TextField url = new TextField(Utility.accountString(a.getUrl()));
        url.setPromptText("www.example.com");
        url.setFocusTraversable(true);
        layout.add(url, 2, 3, 3, 1);    

        //user
        TextField user = new TextField(Utility.accountString(a.getUser()));
        user.setPromptText("myUser");
        user.setFocusTraversable(true);
        layout.add(user, 2, 4, 3, 1);
        
        //email
        TextField email = new TextField(Utility.accountString(a.getEmail()));
        email.setPromptText("email@example.com");
        email.setFocusTraversable(true);
        layout.add(email, 2, 5, 3, 1);    

        //password
        PasswordField password = new PasswordField();
        password.setText(Utility.accountString(a.getPasswordNotCrypted()));
        TextField passwordText = new TextField(Utility.accountString(a.getPasswordNotCrypted()));
        passwordText.setVisible(false);
        passwordText.setPromptText("password");
        password.setPromptText("password");
        password.setFocusTraversable(true);
        layout.add(password, 2, 6, 3, 1);
        layout.add(passwordText, 2, 6, 3, 1);

        //see password images
        Image i1 = new Image(modifyAccount.class.getResource("images/visibility-OFF-icon.png").toString());
        Image i2 = new Image(modifyAccount.class.getResource("images/visibility-ON-icon.png").toString());

        //see password
        ImageView seePas = new ImageView(i1);
        seePas.getStyleClass().add("showHand");
        seePas.setFitHeight(26);
        seePas.setFitWidth(26);
        seePas.setOnMouseClicked(e -> Utility.changeImageWithText(seePas, i1, i2, password, passwordText));
        layout.add(seePas, 6, 6, 1, 1);

        //pin
        PasswordField pin = new PasswordField();
        pin.setText(String.valueOf(Utility.accountString(a.getPinNotCrypted())));
        TextField pinText = new TextField(Utility.accountString(a.getPinNotCrypted()));
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

        //edit
        Button edit = new Button("Edit");
        edit.setFocusTraversable(true);
        edit.getStyleClass().add("button-default");
        edit.setOnAction(e -> modify(page, mainPageGrid, accountFile, cText, log, a, site.getText(), url.getText(), user.getText(), email.getText(), Utility.chooseInput(password, passwordText), Utility.chooseInput(pin, pinText)));
        layout.add(edit, 1, 8, 2, 1);

        //password generator
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
                    edit.requestFocus();
                    modify(page, mainPageGrid, accountFile, cText, log, a, site.getText(), url.getText(), user.getText(), email.getText(), Utility.chooseInput(password, passwordText), Utility.chooseInput(pin, pinText));
                }
            }
        });
    }
    
    public static GridPane display(BorderPane page, GridPane mainPageGrid, File accountFile, ArrayList<Account> cText, Login log, Account a){
        // Display this page
        mainPage.updateInactive();
        //grid setup
        GridPane layout = new GridPane();
        setRow(layout);
        setColumn(layout);
        centralPage(page, mainPageGrid, accountFile, cText, layout, log, a);
        //layout.setGridLinesVisible(true);

        return layout;
    }
}
