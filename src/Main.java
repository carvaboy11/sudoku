import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        SudokuUI sudokuUI = new SudokuUI();
        Scene scene = new Scene(sudokuUI.createContent());
        primaryStage.setTitle("Sudoku FX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
