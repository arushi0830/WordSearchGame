package com.game.wordsearch.service;

import com.game.wordsearch.constants.Direction;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Scope("prototype")
@Data
public class Grid {


    private char [][] matrix;

    private static class Coordinate{
        int x,y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public void generateGrid(List<String> words, int gridSize) {
        matrix = new char[gridSize][gridSize];
         List<Coordinate> coordinateList = new ArrayList<>();

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                matrix[i][j] = '_';
                coordinateList.add(new Coordinate(i,j));
            }
        }

        Collections.shuffle(coordinateList);
        for (String word:words) {
            for(Coordinate coordinate : coordinateList) {
                int m=coordinate.x;
                int n=coordinate.y;
                Direction selectedDirection = getDirectionForFit(coordinate,word, gridSize);
                if(selectedDirection!=null) {
                    switch (selectedDirection){
                        case DIAGONAL:
                            for (char c : word.toCharArray()) {
                                matrix[m++][n++] = c;
                            }
                            break;
                        case HORIZONTAL:
                            for (char c : word.toCharArray()) {
                                matrix[m][n++] = c;
                            }
                            break;
                        case VERTICAL:
                            for (char c : word.toCharArray()) {
                                matrix[m++][n] = c;
                            }
                            break;
                        case DIAGONAL_INVERSE:
                            for (char c : word.toCharArray()) {
                                matrix[m--][n--] = c;
                            }
                            break;
                        case HORIZONTAL_INVERSE:
                            for (char c : word.toCharArray()) {
                                matrix[m][n--] = c;
                            }
                            break;
                        case VERTICAL_INVERSE:
                            for (char c : word.toCharArray()) {
                                matrix[m--][n] = c;
                            }
                            break;
                    }
                    break;
                }
            }
        }
    }

    private Direction getDirectionForFit(Coordinate coordinate, String word, int gridSize){
        List<Direction> direction = Arrays.asList(Direction.values());
        Collections.shuffle(direction);
        for(Direction d:direction){
            if(doesWordFitWithDirection(coordinate,word,d, gridSize))
                return d;
        }
        return null;
    }

    private boolean doesWordFitWithDirection(Coordinate coordinate, String word, Direction direction, int gridSize){
        int wordLength = word.length();
        switch (direction){

            case HORIZONTAL:
                    if(coordinate.y+wordLength<=gridSize) {
                        for (int i = 0; i < wordLength; i++) {
                            if (matrix[coordinate.x][coordinate.y + i] != '_')
                                return false;
                        }
                        return true;
                    }
                    return false;

            case VERTICAL:
                if(coordinate.x+wordLength<=gridSize){
                    for(int i=0;i<wordLength;i++){
                        if(matrix[coordinate.x+i][coordinate.y]!='_')
                            return false;
                    }
                    return true;
                }
                return false;

            case DIAGONAL:
                if(coordinate.x+wordLength<=gridSize && coordinate.y+wordLength<=gridSize){
                    for(int i=0;i<wordLength;i++){
                        if(matrix[coordinate.x+i][coordinate.y+i]!='_')
                            return false;
                    }
                    return true;
                }
                return false;

            case HORIZONTAL_INVERSE:
                if(coordinate.y>=wordLength) {
                    for (int i = 0; i < wordLength; i++) {
                        if (matrix[coordinate.x][coordinate.y - i] != '_')
                            return false;
                    }
                    return true;
                }
                return false;

            case VERTICAL_INVERSE:
                if(coordinate.x>=wordLength){
                    for(int i=0;i<wordLength;i++){
                        if(matrix[coordinate.x-i][coordinate.y]!='_')
                            return false;
                    }
                    return true;
                }
                return false;

            case DIAGONAL_INVERSE:
                if(coordinate.x>=wordLength && coordinate.y>=wordLength){
                    for(int i=0;i<wordLength;i++){
                        if(matrix[coordinate.x-i][coordinate.y-i]!='_')
                            return false;
                    }
                    return true;
                }
                return false;

            default: return false;
        }
    }

    public void randomFill(int gridSize){
        String allLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if(matrix[i][j]=='_'){
                    int randomNumber = ThreadLocalRandom.current().nextInt(0, allLetters.length());
                    matrix[i][j]=allLetters.charAt(randomNumber);
                }
            }
        }
    }

    public char[][] printGrid(int gridSize) {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                System.out.print(" " + matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        return matrix;
    }
}
