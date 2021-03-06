package com.codecool.sudokusolver.controller;

import com.codecool.sudokusolver.model.SudokuCellList;
import com.codecool.sudokusolver.service.ISudokuSolver;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Controller
public class WebController {
    private ISudokuSolver sudokuSolver;
    private String fileName;
    private String[][] manualGrid;
    private String[] userGrid;

    @Autowired
    public WebController(ISudokuSolver sudokuSolver) {
        this.sudokuSolver = sudokuSolver;
    }


    @PostMapping("/solver")
    public String handleSolvedSudoku(Model model) throws IOException {
        model.addAttribute("solvedSudoku", sudokuSolver.solve());
        model.addAttribute("fileName", this.fileName);
        model.addAttribute("time", sudokuSolver.elapsedTime());
        return "result";
    }


    @PostMapping("/sudoku")
    public String handleSudokuBoard(@RequestParam MultipartFile file, Model model) throws IOException {
        this.fileName = file.getOriginalFilename();
        model.addAttribute("sudoku", sudokuSolver.uploadBoard(file));
        model.addAttribute("fileName", fileName);
        return "sudoku";
    }

    @PostMapping("/userGrid")
    public String handleManualGrid(HttpEntity<String> request, Model model) throws IOException {

        Gson g = new Gson();
        String json = request.getBody();
        SudokuCellList sudokuCells = g.fromJson(json, SudokuCellList.class);

        String[][] userGrid = sudokuSolver.getUserGrid(sudokuCells);

        sudokuSolver.generateUserGrid(userGrid);
        model.addAttribute("solvedSudoku", sudokuSolver.solve());
        model.addAttribute("time", sudokuSolver.elapsedTime());
        return "result";
    }


    @PostMapping("/example")
    public String handleExampleSudokuBoard(@RequestParam("grid") String grid, Model model) throws IOException {
        File file = null;

        this.fileName = grid;

        if (grid.equals("Grid one")) {
            file = new File("src/main/resources/sudoku/grid1.txt");
        } else if (grid.equals("Grid two")) {
            file = new File("src/main/resources/sudoku/grid2.txt");
        } else if (grid.equals("Grid three")) {
            file = new File("src/main/resources/sudoku/grid3.txt");
        } else if (grid.equals("Grid four")) {
            file = new File("src/main/resources/sudoku/grid4.txt");
        } else if (grid.equals("Worlds hardest sudoku")) {
            file = new File("src/main/resources/sudoku/worlds_hardest_sudoku.txt");
        }

        model.addAttribute("sudoku", sudokuSolver.uploadExampleBoard(file));
        model.addAttribute("fileName", fileName);
        return "example";
    }


    @GetMapping("/home")
    public String handleHome() {
        return "index";
    }

    @GetMapping("/solver")
    public String handleSolver() {
        return "solver";
    }

    @GetMapping("/about")
    public String handleAbout() {
        return "about";
    }

    @GetMapping("/grid")
    public String handleUserGrid() {
        return "grid";
    }

    @GetMapping("/userGrid")
    public String handleManualGrid() {
        return "result";
    }


}
