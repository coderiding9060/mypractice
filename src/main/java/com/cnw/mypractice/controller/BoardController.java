package com.cnw.mypractice.controller;

import com.cnw.mypractice.domain.Board;
import com.cnw.mypractice.repository.BoardRepository;
import com.cnw.mypractice.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;

    @GetMapping(value = "/list")
    public String list(Model model) {
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards", boards);
        return "board/list";
    }

    @GetMapping(value = "/form")
    public String form(Model model, @RequestParam(value = "no", required = false) Long no) {
        if(no==null){
            model.addAttribute("board", new Board());
        } else {
            Board board = boardRepository.findById(no).orElse(null);
            model.addAttribute("board", board);
        }
        return "board/form";
    }

    @PostMapping(value = "/form")
    public String postForm(@Valid Board board, BindingResult bindingResult) {
        boardValidator.validate(board, bindingResult);
        if (bindingResult.hasErrors()) {
            return "board/form";
        }
        boardRepository.save(board);
        return "redirect:/board/list";
    }


}
