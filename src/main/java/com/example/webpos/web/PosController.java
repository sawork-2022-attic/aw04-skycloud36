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

//    private Cart cart;
    @Autowired
    private HttpSession httpSession;

    public void setCart(Cart cart){
        httpSession.setAttribute("cart", cart);
    }

    public Cart getCart() {
        if(httpSession.getAttribute("cart") == null){
            this.setCart(new Cart());
        }
        return (Cart) httpSession.getAttribute("cart");
    }

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }

    @GetMapping("/")
    public String pos(Model model) {
        if(httpSession.getAttribute("login") == null || (boolean) (httpSession.getAttribute("login")) == false){
            return "redirect:/login";
        }
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", this.getCart());
        return "index";
    }

    @GetMapping("/login")
    String login(Model model){
//        System.out.println("login\t" + httpSession.toString());
        httpSession.setAttribute("login",Boolean.TRUE);
        httpSession.setAttribute("cart", getCart());
        return "redirect:/";
    }

    @GetMapping("/add")
    public String addByGet(@RequestParam(name = "pid") String pid, Model model) {
//        System.out.println("add\t" + pid + "\t" + httpSession.toString());
        if(httpSession.getAttribute("login") == null
                || (boolean) (httpSession.getAttribute("login")) == false){
            return "redirect:/login";
        }
        setCart(posService.add(getCart(), pid,1));
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", getCart());
        return "redirect:/";
    }


    @GetMapping("/delete")
    public String deleteItem(@RequestParam(name = "pid") String pid, Model model) {
//        System.out.println("delete\t" + pid + "\t" + httpSession.toString());
        if(httpSession.getAttribute("login") == null
                || (boolean) (httpSession.getAttribute("login")) == false){
            return "redirect:/login";
        }
        setCart(posService.delete(getCart(), pid));
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", getCart());
        return "redirect:/";
    }

    @GetMapping("/modify")
    public String modifyItem(@RequestParam(name = "pid") String pid,
                             @RequestParam(name = "amount") int amount,
                             Model model) {
//        System.out.println("modify\t" + pid + "\t" + amount + "\t" + httpSession.toString());
        if(httpSession.getAttribute("login") == null
                || (boolean) (httpSession.getAttribute("login")) == false){
            return "redirect:/login";
        }
        setCart(posService.modify(getCart(), pid, amount));
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", getCart());
        return "redirect:/";
    }

    @GetMapping("/empty")
    public String emotyCart(Model model) {
//        System.out.println("empty\t" + httpSession.toString());
        if(httpSession.getAttribute("login") == null
                || (boolean) (httpSession.getAttribute("login")) == false){
            return "redirect:/login";
        }
        setCart(posService.empty(getCart()));
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", getCart());
        return "redirect:/";
    }

}
