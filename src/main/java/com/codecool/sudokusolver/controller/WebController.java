package com.codecool.sudokusolver.controller;

import com.codecool.sudokusolver.service.ISudokuSolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class WebController {
    private ISudokuSolver sudokuSolver;
    private String fileName;

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
    public String handleSudokuBoard(@RequestParam MultipartFile file, Model model) throws IOException{
        this.fileName = file.getOriginalFilename();
        model.addAttribute("sudoku", sudokuSolver.uploadBoard(file));
        model.addAttribute("fileName", fileName);
        return "sudoku";
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


}
