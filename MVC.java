import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MVC extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cinema Booking");
        AppView view = new AppView(primaryStage);

        Scene scene = new Scene(view.asParent(), 300, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}