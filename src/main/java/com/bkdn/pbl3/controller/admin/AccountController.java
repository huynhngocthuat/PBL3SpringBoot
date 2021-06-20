package com.bkdn.pbl3.controller.admin;

import com.bkdn.pbl3.domain.Account;
import com.bkdn.pbl3.model.AccountDto;
import com.bkdn.pbl3.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("account", new AccountDto());
        return "admin/account/addOrEdit";
    }

    @GetMapping("edit/{accountId}")
    public ModelAndView edit(ModelMap model, @PathVariable("accountId") Long accountId){
        Optional<Account> opt = accountService.findById(accountId);
        AccountDto dto = new AccountDto();
        if(opt.isPresent()){
            Account entity = opt.get();
            BeanUtils.copyProperties(entity, dto);  
            dto.setIsEdit(true);
            model.addAttribute("account", dto);
            return new ModelAndView("admin/account/addOrEdit", model);
        }
        model.addAttribute("message", "Account is not existed");
        return new ModelAndView("forward:/admin/account",model);
    }

    @GetMapping("delete/{accountId}")
    public ModelAndView delete(ModelMap model, @PathVariable("accountId") Long accountId){
        accountService.deleteById(accountId);
        model.addAttribute("message", "Account is deleted!");
        return new ModelAndView("forward:/admin/account", model);
    }

    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model, @RequestParam(name = "rePassWord", required = false) String rePassWord, @Valid @ModelAttribute("account") AccountDto dto, BindingResult result)
    {
        String password = dto.getPassWord();
        if(!rePassWord.equals(password)){
            return new ModelAndView("admin/account/addOrEdit");
        }
        if(result.hasErrors()){
            return new ModelAndView("admin/account/addOrEdit");
        }
        dto.setRole(0);
        Account entity = new Account();
        BeanUtils.copyProperties(dto,entity);
        accountService.save(entity);
        model.addAttribute("message", "Account is saved!");
        return new ModelAndView("forward:/admin/account",model);
    }

    @RequestMapping("")
    public String list(ModelMap model){
        List<Account> list = accountService.findAll();
        model.addAttribute("accounts", list);
        return "admin/account/list";
    }
}
