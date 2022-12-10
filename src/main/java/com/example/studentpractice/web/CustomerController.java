package com.example.studentpractice.web;

import com.example.studentpractice.entities.Customer;
import com.example.studentpractice.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SessionAttributes({"a","e"})
@Controller
@AllArgsConstructor
public class CustomerController {
    List<String> seatsCustomer = new ArrayList<String>();

    @Autowired
    private CustomerRepository customerRepository;

    static int num = 0;

    @GetMapping(path = "/")
    public String customers(Model model) {
        seatsCustomer.clear();
        seatsCustomer.add("1A");
        seatsCustomer.add("1B");
        seatsCustomer.add("1C");
        seatsCustomer.add("1D");
        seatsCustomer.add("1E");
        seatsCustomer.add("2A");
        seatsCustomer.add("2B");
        seatsCustomer.add("2C");
        seatsCustomer.add("2D");
        seatsCustomer.add("2E");
        seatsCustomer.add("3A");
        seatsCustomer.add("3B");
        seatsCustomer.add("3C");
        seatsCustomer.add("3D");
        seatsCustomer.add("3E");
        seatsCustomer.add("4A");
        seatsCustomer.add("4B");
        seatsCustomer.add("4C");
        seatsCustomer.add("4D");
        seatsCustomer.add("4E");

        List<Customer> customers;
        int seatCount = 20;

        customers = customerRepository.findAll();
        model.addAttribute("listCustomer", customers);

        for (int i = 0; i < seatsCustomer.size(); i++) {
            for (int j = 0; j < customers.size(); j++) {
                if (customers.get(j).getSeatno().toString().equals(seatsCustomer.get(i).toString())) {
                    seatsCustomer.set(i, customers.get(j).getName().toString());
                    seatCount -= 1;
                }
            }
        }

        model.addAttribute("listSeats", seatsCustomer);
        model.addAttribute("seatCount", seatCount);
        model.addAttribute("date", new Date());

        return "customer";
    }

    @GetMapping("/delete")
    public String delete(Long id){
        customerRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping(path="/save")
    public String save(Model model, Customer customer, BindingResult bindingResult, ModelMap mm, HttpSession session) {
        List<String> seats = new ArrayList<String>();
        seats.add("1A");
        seats.add("1B");
        seats.add("1C");
        seats.add("1D");
        seats.add("1E");
        seats.add("2A");
        seats.add("2B");
        seats.add("2C");
        seats.add("2D");
        seats.add("2E");
        seats.add("3A");
        seats.add("3B");
        seats.add("3C");
        seats.add("3D");
        seats.add("3E");
        seats.add("4A");
        seats.add("4B");
        seats.add("4C");
        seats.add("4D");
        seats.add("4E");

        if (customer.getName().equals("") || customer.getSeatno().equals("") || customer.getTdate().equals("")) {
            mm.put("a", 3);
        } else {
            if (!bindingResult.hasErrors()) {
                boolean found = false;
                boolean taken = false;

                for (int i = 0; i < seats.size(); i++) {
                    if (customer.getSeatno().toString().equals(seats.get(i))) {
                        if (!customer.getSeatno().toString().equals(seatsCustomer.get(i))) {
                            taken = true;
                            break;
                        }

                        found = true;
                        break;
                    }
                }

                if (taken) {
                    mm.put("a", 5);
                } else {
                    if (found) {
                        customerRepository.save(customer);

                        if (num == 0) {
                            mm.put("a", 1);
                        } else {
                            num = 0;
                            mm.put("a", 6);
                        }
                    } else {
                        mm.put("a", 4);
                    }
                }
            }
        }

        return "redirect:/";
    }

    @GetMapping("/editCustomer")
    public String editCustomer(Model model, Long id, HttpSession session){
        num = 2;
        session.setAttribute("info", 0);
        Customer customer = customerRepository.findById(id).orElse(null);
        if(customer ==null) throw new RuntimeException("Customer does not exist");
        model.addAttribute("customer", customer);
        return "editCustomer";
    }
}
