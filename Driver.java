package Project4;

import java.io.File;
import java.util.Scanner;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Driver extends Application {

	private static int check = 0;
	private static Stage stage;
	static TextField[][] gameBoard = new TextField[9][9];
	static String[][] temp = new String[9][9];
	static GridPane pane = new GridPane();
	static int numBoard[][] = new int[9][9];
	static int numBoard2[][] = new int[9][9];
	static int temp1 = 0;
	static int temp2 = 0;
	static int temp3 = 0;
	private Duration timer = Duration.ZERO;
	private Timeline timeline;
	private static RadioButton easy = new RadioButton("Easy");
	private static RadioButton medium = new RadioButton("Medium");
	private static RadioButton hard = new RadioButton("Hard");

	@SuppressWarnings("exports")
	@Override
	public void start(Stage s) throws Exception {

		Label Label1 = new Label("Sudoku");
		Label Label2 = new Label("Timer :-");
		Label Label3 = new Label("Choose Level");
		Label1.setAlignment(Pos.CENTER);
		Label1.setTextFill(Color.BLACK);
		Label1.setFont(new Font("Forte", 100));
		Label2.setFont(new Font("Forte", 20));
		Label2.setTextFill(Color.BLACK);
		Label3.setFont(new Font("Forte", 20));
		Label3.setTextFill(Color.BLACK);

		ToggleGroup toggle = new ToggleGroup();
		easy.setToggleGroup(toggle);
		medium.setToggleGroup(toggle);
		hard.setToggleGroup(toggle);

		easy.setFont(new Font(20));
		medium.setFont(new Font(20));
		hard.setFont(new Font(20));

		Button B1 = new Button("Start Game");
		Button B2 = new Button("Exit");
		Button B3 = new Button("New Game");
		Button B4 = new Button("Solve Game");
		Button B5 = new Button("Check Attempt");
		Button B6 = new Button("Exit");

		B1.setMinSize(120, 50);
		B2.setMinSize(120, 50);
		B3.setMinSize(100, 50);
		B4.setMinSize(100, 50);
		B5.setMinSize(100, 50);
		B6.setMinSize(100, 50);
		pane.setVgap(2);
		pane.setHgap(2);

		
		
		
		
		DoubleProperty timeSeconds = new SimpleDoubleProperty();
		Label TimeLabel = new Label();
		TimeLabel.textProperty().bind(timeSeconds.asString());
		TimeLabel.setFont(new Font(20));
		HBox H1 = new HBox(B1, B2);
		HBox H2 = new HBox(Label2, TimeLabel, B3, B4, B5, B6);
		HBox H3 = new HBox(easy, medium, hard);
		VBox V1 = new VBox(Label1, Label3, H3, H1);
		VBox V2 = new VBox(pane, H2);

		H1.setAlignment(Pos.CENTER);
		H1.setSpacing(20);
		H2.setAlignment(Pos.CENTER);
		H2.setSpacing(20);
		H3.setAlignment(Pos.CENTER);
		H3.setSpacing(10);
		V1.setAlignment(Pos.CENTER);
		V1.setSpacing(40);
		V2.setSpacing(10);
		V2.setPadding(new Insets(10, 10, 10, 10));

		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				gameBoard[i][j] = new TextField("");
				gameBoard[i][j].setFont(new Font(20));
				gameBoard[i][j].setAlignment(Pos.CENTER);
				gameBoard[i][j].setMinSize(50, 50);
				gameBoard[i][j].setMaxSize(50, 50);
			}
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				pane.add(gameBoard[i][j], i, j);

		colorBoard();

		B1.setOnAction(E -> {

			if (!easy.isSelected() && !medium.isSelected() && !hard.isSelected()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Level");
				alert.setContentText("Please Select A Level");
				alert.showAndWait();
			}

			else {

				readExamples();
				s.close();

				// The Main Look Of the Game
				if (check == 0) {
					pane.setPadding(new Insets(14, 14, 14, 14));
					pane.setAlignment(Pos.CENTER);
					Scene scene = new Scene(V2, 650, 600);
					stage = new Stage();
					stage.setScene(scene);
					stage.setTitle("Sudoku Game");
					timeline = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent t) {
							Duration duration = ((KeyFrame) t.getSource()).getTime();
							timer = timer.add(duration);
							timeSeconds.set(timer.toSeconds());
						}
					}));
					check++;
				}
				timer = Duration.ZERO;
				stage.show();
				timeline.setCycleCount(Timeline.INDEFINITE);
				timeline.playFromStart();

				// Solve The Game
				B4.setOnAction(e -> {

					timeline.stop();

					for (int i = 0; i < 9; i++)
						for (int j = 0; j < 9; j++)
							if (gameBoard[i][j].getText().length() == 0)
								temp[i][j] = "0";
							else
								temp[i][j] = gameBoard[i][j].getText();

					for (int i = 0; i < 9; i++)
						for (int j = 0; j < 9; j++)
							numBoard[i][j] = Integer.parseInt(temp[i][j]);

					backTracking track = new backTracking();

					if (track.solveSudoku(numBoard)) {

						for (int i = 0; i < 9; i++)
							for (int j = 0; j < 9; j++)
								gameBoard[i][j].setText(numBoard[i][j] + "");
					} else {
						track.solveSudoku(numBoard2);
						for (int i = 0; i < 9; i++)
							for (int j = 0; j < 9; j++)
								gameBoard[i][j].setText(numBoard2[i][j] + "");
					}

				});
				// Check the Game
				B5.setOnAction(e -> {

					if (!isFilled()) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Empty Cells");
						alert.setContentText("Must Fill All Empty Cells");
						alert.showAndWait();
					}

					else {
						Pane p = new Pane();
						if (isUnique() == true) {
							Label win = new Label("Congratulation\n   You Won !!!");
							win.setFont(new Font("Forte", 100));
							win.setAlignment(Pos.CENTER);
							win.setTextFill(Color.GREEN);
							p.getChildren().add(win);
							p.setPadding(new Insets(12, 12, 12, 12));
							Scene sc1 = new Scene(p);
							Stage st1 = new Stage();
							st1.setScene(sc1);
							st1.setTitle("Win Game");
							st1.show();
						} else {
							Label lose = new Label("    Sorry\n You Lost :( ");
							lose.setFont(new Font("Forte", 100));
							lose.setAlignment(Pos.CENTER);
							lose.setTextFill(Color.BLACK);
							p.getChildren().add(lose);
							Scene sc1 = new Scene(p);
							Stage st1 = new Stage();
							st1.setScene(sc1);
							st1.setTitle("Lose Game");
							st1.show();
						}

					}

				});

				// Exit The Game
				B6.setOnAction(e -> {

					Label l1 = new Label("Are you sure you want to exit game");
					Button btYes = new Button("Yes");
					Button btNo = new Button("No");

					HBox bt = new HBox(btYes, btNo);
					VBox lb = new VBox(l1, bt);
					bt.setAlignment(Pos.CENTER);
					bt.setSpacing(10);
					lb.setAlignment(Pos.CENTER);
					lb.setSpacing(10);

					Scene sc = new Scene(lb, 240, 100);
					Stage st = new Stage();
					st.setTitle("Exit Game");
					st.setScene(sc);
					st.show();

					btYes.setOnAction(X -> {
						System.exit(0);
					});
					btNo.setOnAction(X -> {
						st.close();
					});

				});

				// Start New Game
				B3.setOnAction(e -> {
					Label l1 = new Label("Are you sure you want to start a new game");
					Button btYes = new Button("Yes");
					Button btNo = new Button("No");

					HBox bt = new HBox(btYes, btNo);
					VBox lb = new VBox(l1, bt);
					bt.setAlignment(Pos.CENTER);
					bt.setSpacing(10);
					lb.setAlignment(Pos.CENTER);
					lb.setSpacing(10);

					Scene sc = new Scene(lb, 240, 100);
					Stage st = new Stage();
					st.setTitle("New Game");
					st.setScene(sc);
					st.show();

					btYes.setOnAction(X -> {
						for (int i = 0; i < 9; i++)
							for (int j = 0; j < 9; j++)
								gameBoard[i][j].setText("");

						stage.close();
						s.show();
						st.close();
						readExamples();
					});
					btNo.setOnAction(X -> {
						timeline.play();
						st.close();
					});

				});
			}
		});
		// End of StartGame SetOnAction
		B2.setOnAction(e -> {
			Label l1 = new Label("Are you sure you want to exit game");
			Button btYes = new Button("Yes");
			Button btNo = new Button("No");

			HBox bt = new HBox(btYes, btNo);
			VBox lb = new VBox(l1, bt);
			bt.setAlignment(Pos.CENTER);
			bt.setSpacing(10);
			lb.setAlignment(Pos.CENTER);
			lb.setSpacing(10);

			Scene sc = new Scene(lb, 240, 100);
			Stage st = new Stage();
			st.setTitle("Exit Game");
			st.setScene(sc);
			st.show();

			btYes.setOnAction(X -> {
				System.exit(0);
			});
			btNo.setOnAction(X -> {
				st.close();
			});
		});
		Scene scene = new Scene(V1, 500, 500);
		s.setScene(scene);
		s.setTitle("Sudoku");
		s.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void readExamples() {

		Scanner scan;

		try {
			if (easy.isSelected()) {
				if(temp1 == 0) {
				File File1 = new File("C:\\Users\\ryze\\eclipse-workspace\\Algorithm-Project#4\\src\\Easy1.txt");
				scan = new Scanner(File1);
				temp1++;
				}
				else if(temp1 == 1) {
					File File1 = new File("C:\\Users\\ryze\\eclipse-workspace\\Algorithm-Project#4\\src\\Easy2.txt");
					scan = new Scanner(File1);
					temp1++;
					}
				else {
					File File1 = new File("C:\\Users\\ryze\\eclipse-workspace\\Algorithm-Project#4\\src\\Easy3.txt");
					scan = new Scanner(File1);
					temp1 = 0;
					}
			} else if (medium.isSelected()) {
				if(temp2 == 0) {
				File File1 = new File("C:\\Users\\ryze\\eclipse-workspace\\Algorithm-Project#4\\src\\Medium1.txt");
				scan = new Scanner(File1);
				temp2++;
				}
				else if(temp2 == 1) {
					File File1 = new File("C:\\Users\\ryze\\eclipse-workspace\\Algorithm-Project#4\\src\\Medium2.txt");
					scan = new Scanner(File1);
					temp2++;
					}
				else {
					File File1 = new File("C:\\Users\\ryze\\eclipse-workspace\\Algorithm-Project#4\\src\\Medium3.txt");
					scan = new Scanner(File1);
					temp2=0;
					}
			} else if (hard.isSelected()) {
				if(temp3 == 0) {
				File File1 = new File("C:\\Users\\ryze\\eclipse-workspace\\Algorithm-Project#4\\src\\Hard1.txt");
				scan = new Scanner(File1);
				temp3++;
				}
				else if(temp3 == 1) {
					File File1 = new File("C:\\Users\\ryze\\eclipse-workspace\\Algorithm-Project#4\\src\\Hard2.txt");
					scan = new Scanner(File1);
					temp3++;
					}
				else {
					File File1 = new File("C:\\Users\\ryze\\eclipse-workspace\\Algorithm-Project#4\\src\\Hard3.txt");
					scan = new Scanner(File1);
					temp3 = 0;
					}
			}

			else {
				scan = new Scanner("");
				scan.close();
				return;
			}

			String[] str = new String[81];
			int counter = 0;
			while (scan.hasNextInt()) {

				str[counter] = scan.nextInt() + "";
				counter++;
			}

			scan.close();

			int counter2 = 0;
			for (int i = 0; i < 9; i++)
				for (int j = 0; j < 9; j++) {
					if (str[counter2].compareTo("0") == 0) {
						gameBoard[j][i].setText("");
						numBoard2[j][i] = 0;
						counter2++;
					} else {
						gameBoard[j][i].setText(str[counter2]);
						gameBoard[j][i].setEditable(false);
						gameBoard[j][i].setStyle("-fx-background-color:lightblue");
						numBoard2[j][i] = Integer.parseInt(str[counter2]);
						counter2++;
					}

				}
		} catch (Exception x) {
		}
	}

	private static void colorBoard() {

		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				if (i < 3 && j < 3)
					gameBoard[i][j].setStyle("-fx-background-color:lightblue");
				if (i < 3 && j >= 6)
					gameBoard[i][j].setStyle("-fx-background-color:lightblue");
				if (i < 9 && i >= 6 && j >= 6)
					gameBoard[i][j].setStyle("-fx-background-color:lightblue");
				if (j < 3 && i >= 6 && i < 9)
					gameBoard[i][j].setStyle("-fx-background-color:lightblue");
				if (j >= 3 && j < 6 && i >= 3 && i < 6)
					gameBoard[i][j].setStyle("-fx-background-color:lightblue");
			}
	}

	private boolean isUnique() {

		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				if ((uniqueRow(Integer.parseInt(gameBoard[i][j].getText()), i) == false)
						|| (uniqueColumn(Integer.parseInt(gameBoard[i][j].getText()), j) == false)
						|| (uniqueSquare(Integer.parseInt(gameBoard[i][j].getText()), i, j) == false))
					return false;
		return true;
	}

	private boolean uniqueRow(int num, int row) {

		int counter = 0;
		for (int i = 0; i < 9; i++)
			if (Integer.parseInt(gameBoard[row][i].getText()) == num)
				counter++;

		if (counter == 1)
			return true;
		return false;
	}

	private boolean uniqueColumn(int num, int column) {
		int counter = 0;
		for (int i = 0; i < 9; i++)
			if (Integer.parseInt(gameBoard[i][column].getText()) == num)
				counter++;

		if (counter == 1)
			return true;
		return false;
	}

	private boolean uniqueSquare(int num, int row, int column) {

		row = row - row % 3;
		column = column - column % 3;
		int counter = 0;

		for (int i = row; i < row + 3; i++)
			for (int j = column; j < column + 3; j++)
				if (Integer.parseInt(gameBoard[i][j].getText()) == num)
					counter++;
		if (counter == 1)
			return true;
		return false;

	}

	private boolean isFilled() {
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				if (gameBoard[i][j].getText().length() == 0)
					return false;
		return true;
	}

}
