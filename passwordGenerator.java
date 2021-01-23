

import java.util.Random;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

public class passwordGenerator {
    // generate strong password

    static private TextField password;

    private static char simbMake(){
        // make the array of simbols
        Random rand = new Random();
        char simb[] = new char[4];
        simb[0] = (char)(rand.nextInt(15)+33);// 33 48
        simb[1] = (char)(rand.nextInt(7)+58);// 58 65  
        simb[2] = (char)(rand.nextInt(6)+91);// 91 97 
        simb[3] = (char)(rand.nextInt(4)+123);// 123 127
        int k = rand.nextInt(4);
        return simb[k];    
    }

    private static void generatePassword(boolean uppercase, boolean numbers, boolean simbols, TextField tField) {
        // generate the random password
        String dim = tField.getText();
        for (int i = 0; i<dim.length(); i++) {
            // check if in string there are just numbers
            password.setText("");
            if(dim.charAt(i) < 48 || dim.charAt(i) > 57) return;
        }
        int len = (int)Integer.parseInt(dim);
        if (len < 1 || len > 32) {
            tField.getStyleClass().add("wrong-password");
            return;
        } else{
            tField.getStyleClass().clear();
            tField.getStyleClass().add("text-field");
        }
        
        char[] sol = new char[len];
        Random rand = new Random();
        int quantity = 1;
        if(uppercase) quantity++;
        if(numbers) quantity++;
        if(simbols) quantity++;

        for (int i=0; i<len; i++){
            sol[i] = (char)(rand.nextInt(26)+97);// 97 123
        }
        if (uppercase){
            for (int i=0; i<len/quantity; i++){
                int k = rand.nextInt(len);
                sol[k] = (char)(rand.nextInt(26)+65);// 65 91
            }
        }
        if (numbers){
            for (int i=0; i<len/quantity; i++){
                int k = rand.nextInt(len);
                sol[k] = (char)(rand.nextInt(10)+48);// 48 58
            }
        }
        if (simbols){
            for (int i=0; i<len/quantity; i++){
                int k = rand.nextInt(len);
                sol[k] = simbMake();
            }
        }
        String pGen = new String(sol);
        password.setText(pGen);
        mainPage.updateInactive();
    }

    private static void setRow(GridPane layout){
        // Set row grid
        RowConstraints row = new RowConstraints(70);
        layout.getRowConstraints().add(row);
        for (int i = 0; i < 4; i++) {
            row = new RowConstraints(40);
            layout.getRowConstraints().add(row);
        }
        for (int i = 0; i < 2; i++) {
            row = new RowConstraints(70);
            layout.getRowConstraints().add(row);
        }
    }

    private static void setColumn(GridPane layout){
        // Set column grid
        ColumnConstraints column = new ColumnConstraints(15);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(15);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(50);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(105);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(30);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(105);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(50);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(15);
        layout.getColumnConstraints().add(column);

        column = new ColumnConstraints(15);
        layout.getColumnConstraints().add(column);
    }

    private static void centralPage(GridPane layout){
        //title
        Label title = new Label("Password Generator");
        title.getStyleClass().add("title");
        layout.add(title, 3, 0, 3, 1);

        //uppercase
        Label uText = new Label("Uppercase");
        CheckBox uppercase = new CheckBox();
        uppercase.setSelected(true);
        GridPane.setHalignment(uppercase, HPos.CENTER);
        layout.add(uText, 2, 1, 3, 1);
        layout.add(uppercase, 6, 1, 1, 1);

        //numbers
        Label nText = new Label("Numbers");
        CheckBox numbers = new CheckBox();
        numbers.setSelected(true);
        GridPane.setHalignment(numbers, HPos.CENTER);
        layout.add(nText, 2, 2, 3, 1);
        layout.add(numbers, 6, 2, 1, 1);

        //simbols
        Label sText = new Label("Simbols: ");
        CheckBox simbols = new CheckBox();
        simbols.setSelected(true);
        GridPane.setHalignment(simbols, HPos.CENTER);
        layout.add(sText, 2, 3, 3, 1);
        layout.add(simbols, 6, 3, 1, 1);

        //password length
        Label lengthText = new Label("Length (Min: 1 Max: 32): ");
        TextField length = new TextField("12");
        length.setAlignment(Pos.CENTER);
        layout.add(lengthText, 2, 4, 4, 1);
        layout.add(length, 6, 4, 1, 1);
        
        //password
        password = new TextField();
        password.setPromptText("password");
        password.setAlignment(Pos.CENTER);
        layout.add(password, 3, 5, 3, 1);

        //button generate
        Button generate = new Button("Generate");
        generate.getStyleClass().add("button-default");
        generate.setOnAction(e -> generatePassword(uppercase.isSelected(), numbers.isSelected(), simbols.isSelected(), length));
        layout.add(generate, 1, 6, 3, 1);

        //button copy
        Button copy = new Button("Copy");
        copy.getStyleClass().add("button-default");
        copy.setOnAction(e -> Utility.copyToClipboard(password.getText()));
        layout.add(copy, 5, 6, 3, 1);
    }

    public static void display(){
        // Display this page
        mainPage.updateInactive();
        Stage window = new Stage();
        window.getIcons().add(new Image(passwordGenerator.class.getResourceAsStream("images/icon.png")));
        window.resizableProperty().setValue(false);

        BorderPane page = new BorderPane();
        page.getStyleClass().add("page");
               
        StackPane root = new StackPane(page);
        root.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(root, 420, 430);
        scene.setFill(Color.TRANSPARENT);
        titleBar.topBar(window, scene, page, "Password Generator", false);
       
        //grid setup
        GridPane layout = new GridPane();
        centralPage(layout);
        page.setCenter(layout);
        setRow(layout);
        setColumn(layout);
        //layout.setGridLinesVisible(true);
               
        scene.getStylesheets().add(passwordGenerator.class.getResource("css/styles.css").toString());
        window.setScene(scene);
        window.show();
    }
}
