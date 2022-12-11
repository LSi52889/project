package com.example.lovepreet300352889.web;

import com.example.lovepreet300352889.entities.salesman;
import com.example.lovepreet300352889.repositories.salesmanRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@SessionAttributes({"a","e"})
@Controller
@AllArgsConstructor
public class salesmanController {
    List<String> seatssalesman = new ArrayList<String>();

    @Autowired
    private salesmanRepository salesmanRepository;

    static int num = 0;

    @GetMapping("/delete")
    public String delete(Long id){
        salesmanRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/editsalesman")
    public String editSalesman(Model model, Long id, HttpSession session){
        num = 2;
        session.setAttribute("info", 0);
        salesman salesman = salesmanRepository.findById(id).orElse(null);
        if(salesman ==null) throw new RuntimeException("salesman does not exist");
        model.addAttribute("salesman", salesman);
        return "editsalesman";
    }
}
