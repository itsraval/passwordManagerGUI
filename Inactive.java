

import java.time.LocalDateTime;

import javafx.stage.Stage;


public class Inactive implements Runnable {
    private boolean i;
    private LocalDateTime inactiveTime;
    private Stage window;

    public Inactive(Stage window){
        this.window = window;
    }

    public void setI(boolean i) {
        this.i = i;
    }

    public void setInactiveTime(LocalDateTime inactiveTime) {
        this.inactiveTime = inactiveTime;
    }

    @Override
    public void run() {
        if(!window.isShowing()) System.exit(0);
        LocalDateTime now = LocalDateTime.now();
        //System.out.println(inactiveTime + " - " + now);
        if (i){
            if(now.compareTo(inactiveTime) > 0){
                System.exit(0);
            }
        }
    }
    
}
