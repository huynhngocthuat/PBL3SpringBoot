package com.bkdn.pbl3.controller;

import com.bkdn.pbl3.domain.Account;
import com.bkdn.pbl3.model.LoginDto;
import com.bkdn.pbl3.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class homeController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private HttpSession session;

    @GetMapping("login")
    public String login(ModelMap model){
        model.addAttribute("account", new LoginDto());
        return "/login";
    }
    @PostMapping("login")
    public ModelAndView login(ModelMap model, @Valid @ModelAttribute("account") LoginDto dto, BindingResult result){
        if(result.hasErrors()){
            return new ModelAndView("/login", model);
        }
        Account account = accountService.login(dto.getUserName(), dto.getPassWord());
        if(account == null){
            model.addAttribute("message", "Invalid username or password");
            return new ModelAndView("/login", model);
        }
        session.setAttribute("username", account.getUserName());
        return new ModelAndView("forward:/admin/room", model);
    }
}
