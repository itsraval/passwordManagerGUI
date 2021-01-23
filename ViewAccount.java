

import java.io.File;
import java.util.ArrayList;

import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class ViewAccount {

    private static String cryptPassword (String s){
        // change password or pin with same length but only • characters
        String text = "";
        for (int i=0; i<s.length(); i++){
            text = text + "•";
        }
        return text;
    }

    private static void changeText(Label l, String s, ImageView iw, Image i1, Image i2){
        // change the password or pin from • to the real string
        Utility.changeImage(iw, i1, i2);
        if (iw.getImage().equals(i1)){
            l.setText(cryptPassword(s));
        }else{
            l.setText(s);
        }
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
        icon.setFitHeight(110);
        icon.setFitWidth(110);
        icon.setOnMouseClicked(e -> Utility.urlImage(a.getUrl()));
        GridPane.setHalignment(icon, HPos.CENTER);
        layout.add(icon, 2, 1, 3, 1);

        //site
        Label site = new Label(Utility.accountString(a.getSite()));
        site.requestFocus();
        site.getStyleClass().add("title");
        GridPane.setHalignment(site, HPos.CENTER);
        layout.add(site, 2, 2, 3, 1);    

        //url
        Label url = new Label(Utility.accountString(a.getUrl()));
        url.getStyleClass().add("account-text");
        url.setOnMouseClicked(e -> Utility.openURL(Utility.accountString(a.getUrl())));
        layout.add(url, 2, 3, 3, 1);    

        //user
        Label user = new Label(Utility.accountString(a.getUser()));
        user.getStyleClass().add("account-text");
        layout.add(user, 2, 4, 3, 1);
        
        //email
        Label email = new Label(Utility.accountString(a.getEmail()));
        email.getStyleClass().add("account-text");
        layout.add(email, 2, 5, 3, 1);    

        //password
        Label password = new Label(cryptPassword(Utility.accountPasswordString(a.getPasswordNotCrypted())));
        password.getStyleClass().add("account-text");
        layout.add(password, 2, 6, 3, 1);

        //see password
        Image i1 = new Image(addAccount.class.getResource("images/visibility-OFF-icon.png").toString());// 
        Image i2 = new Image(addAccount.class.getResource("images/visibility-ON-icon.png").toString());// 

        if (!a.getPasswordNotCrypted().equals("!")){// if password != empty
            ImageView seePas = new ImageView(i1);
            seePas.getStyleClass().add("showHand");
            seePas.setFitHeight(26);
            seePas.setFitWidth(26);
            seePas.setOnMouseClicked(e -> changeText(password, Utility.accountPasswordString(a.getPasswordNotCrypted()), seePas, i1, i2));
            layout.add(seePas, 6, 6, 1, 1);
        }

        //pin
        Label pin = new Label(cryptPassword(Utility.accountPasswordString(a.getPinNotCrypted())));
        pin.getStyleClass().add("account-text");
        layout.add(pin, 2, 7, 3, 1);

        if (!a.getPinNotCrypted().equals("!")){// if pin != empty
            //see pin
            ImageView seePin = new ImageView(i1);
            seePin.getStyleClass().add("showHand");
            seePin.setFitHeight(26);
            seePin.setFitWidth(26);
            seePin.setOnMouseClicked(e -> changeText(pin, Utility.accountPasswordString(a.getPinNotCrypted()), seePin, i1, i2));
            layout.add(seePin, 6, 7, 1, 1);
        }

        //add
        Button edit = new Button("Edit");
        edit.setFocusTraversable(true);
        edit.getStyleClass().add("button-default");
        edit.setOnAction(e -> mainPage.modifyA(page, accountFile, log, a));
        layout.add(edit, 1, 8, 2, 1);

        //add
        Button close = new Button("Close");
        close.setFocusTraversable(true);
        close.getStyleClass().add("button-default");
        close.setOnAction(e -> page.setRight(RightPageNoAccount.display()));
        layout.add(close, 4, 8, 4, 1);
    }

    public static GridPane display(BorderPane page, GridPane mainPageGrid, File accountFile, ArrayList<Account> cText, Login log, Account a){
        // Display this page
      
        //grid setup
        GridPane layout = new GridPane();
        centralPage(page, mainPageGrid, accountFile, cText, layout, log, a);
        setRow(layout);
        setColumn(layout);
        //layout.setGridLinesVisible(true);

        return layout;
    }
}

