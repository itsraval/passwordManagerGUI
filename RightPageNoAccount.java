
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;


public class RightPageNoAccount {
    // Right page

    public static VBox display(){
        // Display this page
        mainPage.updateInactive();
        VBox page = new VBox();
        page.setMaxSize(400, 725);
        page.setMinSize(400, 725);
        page.setAlignment(Pos.CENTER);

        Label text = new Label("No selected account.");
        text.setStyle("-fx-font-size: 20px;");
        page.getChildren().add(text);

        return page;
    }
}


