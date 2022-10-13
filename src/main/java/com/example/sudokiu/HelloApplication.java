package com.example.sudokiu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    int numRows = 100;
    int numColumns = 100;
    int max = 0;
    int screenx = 1000;
    int screeny = 1000;
    double smallestBound;
    double margins = 0;
    double tileSize = 0;
    static List<List<Integer>> rows = new ArrayList<>();
    static List<List<Integer>> columns = new ArrayList<>();
    Font font;
    @Override
    public void start(Stage stage){
        BuildListsOLists();
        max = numRows;
        if((numRows != numColumns) && (numRows % 6 < numColumns % 6)) {
            max = numColumns;
        }
        smallestBound = screenx;
        if((screenx != screeny) && (screenx % 6 < screeny % 6)){
            smallestBound = screeny;
        }
        System.out.println(smallestBound + " is the smallest dimension and" + max + " is the largest column or row" );
        //divide the smallest bound by 10 and allocate it to the margin total size. the margin is then cut up based on max + 1. the remaining 90% of the smallest bound is divided by max and assigned to tiles.
        margins = (smallestBound * .1) / (max + 1);
        tileSize = (smallestBound * .9) / (max);

        font = Font.font("Arial", FontWeight.BOLD,tileSize / 1.75);
        Pane board = new Pane(CreateBoard());
        Scene scene = new Scene(board, screenx, screeny);
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
        holder.setPadding(new Insets(margins, margins, margins, margins));
        VBox holderVBox = new VBox(3);
        holderVBox.setSpacing(margins);
        int a = 0;
        while(a < numRows){
            HBox row = new HBox();
            row.setSpacing(margins);
            holderVBox.getChildren().add(row);
            for(int b = 0; b < numColumns; b++){
                StackPane Tile = new StackPane();
                Tile.setMinSize(tileSize, tileSize);
                Tile.setBackground(new Background(new BackgroundFill((Color.BISQUE), null, null)));
                row.getChildren().add(Tile);
                Label number = new Label();
                number.setFont(font);
                Tile.getChildren().add(number);
                int num = AddNumber(rows.get(a), columns.get(b));
                double colorRange = (0.5 / max) * (double) num;
                double colorBound = 0.25;
                double finalColor = colorBound + colorRange;
                number.setText(String.valueOf(num));
                number.setTextFill(new Color(finalColor, finalColor, 1.0, 1.0));
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