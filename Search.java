
import java.io.File;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Search {
    private static ArrayList<Account> accountSearchList;

    private static void searchCall(ArrayList<Account> cText, String searchText){
        accountSearchList = new ArrayList<Account>();
        for (int i=0; i<cText.size(); i++){
            if (cText.get(i).getSite().toLowerCase().contains(searchText.toLowerCase())){
                accountSearchList.add(cText.get(i));
            }
        } 
        mainPage.updateInactive();
    }

    private static void newAccountLine(VBox v, ScrollPane scrollPane, BorderPane page, GridPane layout, File accountFile, Login log, ArrayList<Account> cText, Account a){
        HBox h = new HBox();
        h.setAlignment(Pos.CENTER_LEFT);
        ImageView icon = new ImageView(a.getImage());
        icon.setFitHeight(30);
        icon.setFitWidth(30);

        Label text = new Label(" " + Utility.accountString(a.getSite()));
        h.getChildren().addAll(icon, text);
        v.getChildren().add(h);

        
        h.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(mainPage.inTime()){
                    page.setRight(ViewAccount.display(page, layout, accountFile, cText, log, a));
                    double n = 120 * cText.indexOf(a);
                    double d = 36 + 120 * (cText.size() - 6);
                    double v = n / d;
                    if (v > 1) v = 1;
                    scrollPane.setVvalue(v);
                }else{
                    passwordAsk.display(log);
                    if(mainPage.inTime()){
                        page.setRight(ViewAccount.display(page, layout, accountFile, cText, log, a));
                        double n = 120 * cText.indexOf(a);
                        double d = 36 + 120 * (cText.size() - 6);
                        double v = n / d;
                        if (v > 1) v = 1;
                        scrollPane.setVvalue(v);
                    }
                }
                mainPage.pass = false;
            }
        });
    }
    
    private static void displayAccounts(VBox v, ScrollPane scrollPane, BorderPane page, GridPane layout, File accountFile, Login log, ArrayList<Account> cText, String searchText){
        searchCall(cText, searchText);
        for (int i=0; i<accountSearchList.size(); i++){
            newAccountLine(v, scrollPane, page, layout, accountFile, log, cText, accountSearchList.get(i));
        }
    }

    public static void display(ScrollPane pane, ScrollPane scrollPane, BorderPane page, GridPane layout, File accountFile, Login log, ArrayList<Account> cText, String searchText){
        pane.setVisible(true);
        VBox v = new VBox(5);
        displayAccounts(v, scrollPane, page, layout, accountFile, log, cText, searchText);
        pane.setContent(v);
    }
}
