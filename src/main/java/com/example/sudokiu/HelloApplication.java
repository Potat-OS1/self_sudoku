package com.example.sudokiu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    List<List<Integer>> rows = new ArrayList<>();
    List<List<Integer>> columns = new ArrayList<>();
    List<Integer> row1 = new ArrayList<>(3);
    List<Integer> row2 = new ArrayList<>(3);
    List<Integer> row3 = new ArrayList<>(3);
    List<Integer> column1 = new ArrayList<>(3);
    List<Integer> column2 = new ArrayList<>(3);
    List<Integer> column3 = new ArrayList<>(3);
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
    public int AddNumber(List<Integer> givenRow, List<Integer> givenColumn, int column, int row){
        int addition = 0;
        boolean ContinueLoop = true;
        int z = 0;
        while(ContinueLoop && z > 20){
            addition = (int) (Math.random()*(9-1));
            for (Integer test1 : givenRow){
                if(test1 != addition){
                    System.out.println("Not Inside");
                }
            }
            for (Integer test2 : givenColumn){
                if(test2 == addition){
                    System.out.println("fuckyou");
                }
                else{
                    System.out.println("Not Inside");
                    givenRow.set(row, addition);
                    givenColumn.set(column, addition);
                    ContinueLoop = false;
                }
            }
            z++;
        }
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
                Rectangle Tile = new Rectangle(150, 150);
                Tile.setFill(Color.BISQUE);
                row.getChildren().add(Tile);
                Label number = new Label();
                number.setText(String.valueOf(AddNumber(rows.get(a), columns.get(b), a, b)));
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