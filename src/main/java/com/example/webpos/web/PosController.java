package com.example.webpos.web;

import com.example.webpos.biz.PosService;
import com.example.webpos.model.Cart;
import com.example.webpos.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class PosController {

    private PosService posService;

    private Cart cart;

    @Autowired
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }

    @GetMapping("/")
    public String pos(Model model, HttpSession session) {
        if(session.getAttribute("login") == null || (boolean) (session.getAttribute("login")) == false){
            return "redirect:/login";
        }
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", cart);
        return "index";
    }

    @GetMapping("/login")
    String login(HttpSession session, Model model){
        session.setAttribute("login",Boolean.TRUE);
        return "redirect:/";
    }

    @GetMapping("/add")
    public String addByGet(@RequestParam(name = "pid") String pid, Model model, HttpSession session) {
        System.out.println("add\t" + session);
        if(session.getAttribute("login") == null || (boolean) (session.getAttribute("login")) == false){
            System.out.println(session.getAttribute("login"));
            return "redirect:/login";
        }
        posService.add(cart, pid, 1);
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", cart);
        return "redirect:/";
    }


    @GetMapping("/delete")
    public String deleteItem(@RequestParam(name = "pid") String pid, Model model, HttpSession session) {
        System.out.println("delete\t" + session);
        if(session.getAttribute("login") == null || (boolean) (session.getAttribute("login")) == false){
            System.out.println(session.getAttribute("login"));
            return "redirect:/login";
        }
        posService.delete(cart, pid);
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", cart);
        return "redirect:/";
    }

    @GetMapping("/modify")
    public String modifyItem(@RequestParam(name = "pid") String pid,
                             @RequestParam(name = "amount") int amount,
                             Model model, HttpSession session) {
        System.out.println("modify\t" + session);
        if(session.getAttribute("login") == null || (boolean) (session.getAttribute("login")) == false){
            System.out.println(session.getAttribute("login"));
            return "redirect:/login";
        }
        posService.modify(cart, pid, amount);
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", cart);
        return "redirect:/";
    }

    @GetMapping("/empty")
    public String emotyCart(Model model, HttpSession session) {
        System.out.println("empty\t" + session);
        if(session.getAttribute("login") == null || (boolean) (session.getAttribute("login")) == false){
            System.out.println(session.getAttribute("login"));
            return "redirect:/login";
        }
        posService.empty(cart);
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", cart);
        return "redirect:/";
    }

}
