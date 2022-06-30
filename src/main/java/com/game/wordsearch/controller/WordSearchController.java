package com.game.wordsearch.controller;

import com.game.wordsearch.service.Grid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/wordsearchgame")
public class WordSearchController {

    @Autowired
    public Grid grid ;

    @GetMapping(value="/getgrid", consumes = "application/json")
    public List<char[]> createWordSearch(@RequestBody Map<String,Object> gridInput){
        int gridSize = Integer.parseInt(gridInput.get("gridSize").toString());
        List<String> words = Arrays.asList(gridInput.get("words").toString().split(","));
        if(gridSize<=0)
            return Collections.singletonList("Grid size is invalid".toCharArray());
        if(words.size()==0)
            return Collections.singletonList("Words are invalid".toCharArray());
        grid.generateGrid(words,gridSize);
        grid.randomFill(gridSize);
       return Arrays.asList(grid.printGrid(gridSize));
    }

    @GetMapping("/health")
    public String healthCheck(){
        return "Healthy";
    }

}
