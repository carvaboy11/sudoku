import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class SudokuUI {

    private static final int SIZE = 9;
    private TextField[][] cells = new TextField[SIZE][SIZE];

    private final int[][] initialBoard = {
        {5, 3, 0, 0, 7, 0, 0, 0, 0},
        {6, 0, 0, 1, 9, 5, 0, 0, 0},
        {0, 9, 8, 0, 0, 0, 0, 6, 0},
        {8, 0, 0, 0, 6, 0, 0, 0, 3},
        {4, 0, 0, 8, 0, 3, 0, 0, 1},
        {7, 0, 0, 0, 2, 0, 0, 0, 6},
        {0, 6, 0, 0, 0, 0, 2, 8, 0},
        {0, 0, 0, 4, 1, 9, 0, 0, 5},
        {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };

    public Parent createContent() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setGridLinesVisible(true);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                TextField cell = new TextField();
                cell.setPrefSize(50, 50);
                cell.setFont(Font.font(18));
                cell.setAlignment(Pos.CENTER);

                int value = initialBoard[row][col];
                if (value != 0) {
                    cell.setText(String.valueOf(value));
                    cell.setEditable(false);
                    cell.setStyle("-fx-background-color: lightgray;");
                } else {
                    final int r = row;
                    final int c = col;
                    cell.textProperty().addListener((obs, old, newValue) -> {
                        if (!newValue.matches("[1-9]?")) {
                            cell.setText("");
                        } else {
                            if (!newValue.isEmpty() && !isValidMove(r, c, Integer.parseInt(newValue))) {
                                showAlert("Movimento inválido.");
                                cell.setText("");
                            } else if (isComplete()) {
                                showAlert("Parabéns! Você completou o Sudoku!");
                            }
                        }
                    });
                }

                cells[row][col] = cell;
                StackPane cellContainer = new StackPane(cell);
                grid.add(cellContainer, col, row);
            }
        }

        return grid;
    }

    private boolean isValidMove(int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (i != col && getCellValue(row, i) == num) return false;
            if (i != row && getCellValue(i, col) == num) return false;
        }

        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if ((i != row || j != col) && getCellValue(i, j) == num) return false;
            }
        }

        return true;
    }

    private int getCellValue(int row, int col) {
        String text = cells[row][col].getText();
        return text.isEmpty() ? 0 : Integer.parseInt(text);
    }

    private boolean isComplete() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                String text = cells[row][col].getText();
                if (text.isEmpty()) return false;
            }
        }
        return true;
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
