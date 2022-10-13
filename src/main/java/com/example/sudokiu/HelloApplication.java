package com.example.sudokiu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    static List<List<Integer>> rows = new ArrayList<>();
    static List<List<Integer>> columns = new ArrayList<>();
    static List<Integer> row1 = new ArrayList<>(3);
    static List<Integer> row2 = new ArrayList<>(3);
    static List<Integer> row3 = new ArrayList<>(3);
    static List<Integer> column1 = new ArrayList<>(3);
    static List<Integer> column2 = new ArrayList<>(3);
    static List<Integer> column3 = new ArrayList<>(3);
    @Override
    public void start(Stage stage){
        BuildListsOLists();
        Pane board = new Pane(CreateBoard());
        Scene scene = new Scene(board, 490, 490);
        stage.setTitle("Sudoku");
        stage.setScene(scene);
        stage.show();
    }
    public void BuildListsOLists(){
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        columns.add(column1);
        columns.add(column2);
        columns.add(column3);
    }
    public int AddNumber(List<Integer> givenRow, List<Integer> givenColumn){
        int addition = 0;
        List<Integer> testList = new ArrayList<>();
        for (Integer entry : givenRow){
            testList.add(entry);
        }
        for (Integer entry : givenColumn){
            testList.add(entry);
        }
        testList.add(0);
        System.out.println(testList + " Test List");
        while(testList.contains(addition)){
            System.out.println("replaced " + addition);
            addition = (int) (Math.random()*(4-1)+1);
        }
        givenRow.add(addition);
        givenColumn.add(addition);
        return addition;
    }
    public Node CreateBoard(){
        StackPane holder = new StackPane();
        holder.setBackground(new Background(new BackgroundFill((Color.DARKCYAN), null, null)));
        holder.setPadding(new Insets(10, 10, 10, 10));
        VBox holderVBox = new VBox(3);
        holderVBox.setSpacing(10);
        int a = 0;
        while(a < 3){
            HBox row = new HBox();
            row.setSpacing(10);
            holderVBox.getChildren().add(row);
            for(int b = 0; b < 3; b++){
                StackPane Tile = new StackPane();
                Tile.setMinSize(150, 150);
                Tile.setBackground(new Background(new BackgroundFill((Color.BISQUE), null, null)));
                row.getChildren().add(Tile);
                Label number = new Label();
                number.setFont(new Font("Arial", 50));
                Tile.getChildren().add(number);
                number.setText(String.valueOf(AddNumber(rows.get(a), columns.get(b))));
            }
            a++;
        }
        holder.getChildren().add(holderVBox);
        return holder;
    }
    public static void main(String[] args) {
        launch();
    }
}