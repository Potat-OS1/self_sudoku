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
    int numRows = 9;
    int numColumns = 9;
    int max = 0;
    static List<List<Integer>> rows = new ArrayList<>();
    static List<List<Integer>> columns = new ArrayList<>();
    @Override
    public void start(Stage stage){
        BuildListsOLists();
        max = numRows;
        if(numRows != numColumns) {
            if (numRows % 6 < numColumns % 6) {
                max = numColumns;
            }
        }
        Pane board = new Pane(CreateBoard());
        Scene scene = new Scene(board, 1000, 1000);
        stage.setTitle("Sudoku");
        stage.setScene(scene);
        stage.show();
    }
    public void BuildListsOLists(){
        for (int y = 0; y < numRows; y++){
            List<Integer> row = new ArrayList<>();
            rows.add(row);
        }
        for (int x = 0; x < numColumns; x++){
            List<Integer> column = new ArrayList<>();
            columns.add(column);
        }
    }
    public int AddNumber(List<Integer> givenRow, List<Integer> givenColumn){
        int addition = 0;
        List<Integer> testList = new ArrayList<>();
        testList.addAll(givenRow);
        testList.addAll(givenColumn);
        testList.add(0);
        int breakout = 0;
        while(testList.contains(addition) && breakout < 100){
            addition = (int) (Math.random()*((max + 1)-1)+1);
            breakout++;
        }
        if (breakout > 100){
            System.out.println(addition + "broke out");
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
        while(a < numRows){
            HBox row = new HBox();
            row.setSpacing(10);
            holderVBox.getChildren().add(row);
            for(int b = 0; b < numColumns; b++){
                StackPane Tile = new StackPane();
                Tile.setMinSize(100, 100);
                Tile.setBackground(new Background(new BackgroundFill((Color.BISQUE), null, null)));
                row.getChildren().add(Tile);
                Label number = new Label();
                number.setFont(new Font("Arial", 30));
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