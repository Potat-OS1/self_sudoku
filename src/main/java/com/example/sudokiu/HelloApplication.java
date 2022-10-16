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
import java.util.Collections;
import java.util.List;

public class HelloApplication extends Application {
    static int numRows = 10;
    static int numColumns = 10;
    int max = 0;
    int screenx = 1000;
    int screeny = 1000;
    double smallestBound;
    double margins = 0;
    double tileSize = 0;
    static List<List<Integer>> rows = new ArrayList<>(numRows);
    static List<List<Integer>> columns = new ArrayList<>(numColumns);
    static List<Integer> breakouts = new ArrayList<>();
    static VBox holderVBox = new VBox(3);
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
        //divide the smallest bound by 10 and allocate it to the margin total size. the margin is then cut up based on max + 1. the remaining 90% of the smallest bound is divided by max and assigned to tiles.
        margins = (smallestBound * .1) / (max + 1);
        tileSize = (smallestBound * .9) / (max);

        font = Font.font("Arial", FontWeight.BOLD,tileSize / 1.75);
        Pane board = new Pane(CreateBoard());
        Scene scene = new Scene(board, screenx, screeny);
        stage.setTitle("Sudoku");
        stage.setScene(scene);
        stage.show();
        for(int d = 0; d < breakouts.size(); d+=2){
            System.out.println(breakouts.get(d) + " " + breakouts.get(d+1));
        }
        int u = 0;
        while(!breakouts.isEmpty() && u < 100){
            System.out.println("-----------");
            CorrectRows();
            for(int d = 0; d < breakouts.size(); d+=2){
                System.out.println(breakouts.get(d) + " " + breakouts.get(d+1));
            }
            u++;
        }
    }
    public void BuildListsOLists(){
        for (int y = 0; y < numRows; y++){
            List<Integer> row = new ArrayList<>(numColumns);
            rows.add(row);
        }
        for (int x = 0; x < numColumns; x++){
            List<Integer> column = new ArrayList<>(numRows);
            columns.add(column);
        }
    }
    public int AddNumber(List<Integer> givenRow, List<Integer> givenColumn, int row, int column, Pane Tile){
        int addition = 0;
        List<Integer> testList = new ArrayList<>();
        testList.addAll(givenColumn);
        testList.addAll(givenRow);
        testList.add(0);
        int breakout = 0;
        while(testList.contains(addition) && breakout < 100){
            addition = (int) (Math.random()*((max + 1)-1)+1);
            breakout++;
        }
        if (breakout == 100){
            breakouts.add(row + 1);
            breakouts.add(column + 1);
            Tile.setBackground(new Background(new BackgroundFill((Color.RED), null, null)));
        }
        givenRow.add(addition);
        givenColumn.add(addition);

        return addition;
    }
    public Node CreateBoard(){
        StackPane holder = new StackPane();
        holder.setBackground(new Background(new BackgroundFill((Color.DARKCYAN), null, null)));
        holder.setPadding(new Insets(margins, margins, margins, margins));
        holderVBox.setSpacing(margins);
        for(int a = 0; a < numRows; a++){
            HBox row = new HBox();
            holderVBox.getChildren().add(row);
            for(int b = 0; b < numColumns; b++){
                TileCreate(row, a, b);
            }
        }
        holder.getChildren().add(holderVBox);
        return holder;
    }
    public void TileCreate(HBox row, int a, int b){
        row.setSpacing(margins);
        StackPane Tile = new StackPane();
        Tile.setMinSize(tileSize, tileSize);
        Tile.setBackground(new Background(new BackgroundFill((Color.BISQUE), null, null)));
        row.getChildren().add(Tile);
        Label number = new Label();
        number.setFont(font);
        Tile.getChildren().add(number);
        int num = AddNumber(rows.get(a), columns.get(b), a, b, Tile);
        double colorRange = (0.5 / max) * (double) num;
        double colorBound = 0.25;
        double finalColor = colorBound + colorRange;
        number.setText(String.valueOf(num));
        number.setTextFill(new Color(finalColor, finalColor, 1.0, 1.0));
    }
    public void CorrectRows(){
        //this collects the rows that need to be replaced.
        List<Integer> breakoutsRow = new ArrayList<>();
        for(int v = 0; v < breakouts.size(); v+=2){
            if(!breakoutsRow.contains(breakouts.get(v))){
                breakoutsRow.add(breakouts.get(v));
            }
        }
        breakouts.clear();
        Collections.sort(breakoutsRow);
        Collections.reverse(breakoutsRow);
        //now with the list in reverse order, we can can properly remove the entries of rows we are going to remove.
        for(Integer removal : breakoutsRow){
            for(List<Integer> column : columns){
                column.remove((removal - 1));
            }
            rows.remove((removal - 1));
            holderVBox.getChildren().remove((removal - 1));
        }
        //replaces.
        while(rows.size() < 10){
            List<Integer> row = new ArrayList<>();
            rows.add(row);
            HBox replacedRow = new HBox();
            holderVBox.getChildren().add(replacedRow);
            for(int b = 0; b < numColumns; b++){
                TileCreate(replacedRow, (rows.size() -1), b);
            }
        }
    }
    public static void main(String[] args) {
        launch();
    }
}