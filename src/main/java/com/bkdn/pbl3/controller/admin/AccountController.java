package com.bkdn.pbl3.controller.admin;

import com.bkdn.pbl3.domain.Account;
import com.bkdn.pbl3.domain.Room;
import com.bkdn.pbl3.model.AccountDto;
import com.bkdn.pbl3.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("admin/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("account", new AccountDto());
        return "admin/account/addOrEdit";
    }

    @GetMapping("edit")
    public ModelAndView edit(ModelMap model, Principal principal) {
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        Account account = accountService.findByUserName(loginedUser.getUsername());
        Optional<Account> opt = accountService.findById(account.getAccountId());
        AccountDto dto = new AccountDto();
        if (opt.isPresent()) {
            Account entity = opt.get();
            BeanUtils.copyProperties(entity, dto);
            dto.setIsEdit(true);
            model.addAttribute("account", dto);
            return new ModelAndView("admin/account/addOrEdit", model);
        }
        model.addAttribute("message", "Account is not existed");
        return new ModelAndView("forward:/admin/account/edit", model);
    }

    @GetMapping("delete/{accountId}")
    public ModelAndView delete(ModelMap model, @PathVariable("accountId") Long accountId) {
        accountService.deleteById(accountId);
        model.addAttribute("message", "Account is deleted!");
        return new ModelAndView("forward:/admin/account", model);
    }

    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model, @RequestParam(name = "rePassWord", required = false) String rePassWord,
                                     @Valid @ModelAttribute("account") AccountDto dto, BindingResult result) {
        String newUserName = dto.getUserName();
        if (result.hasErrors()) {
            return new ModelAndView("admin/account/addOrEdit");
        }
        //nêu isEdit = true thì hiểu là đang edit
        if (dto.getIsEdit()) {
            accountService.updateAccount(dto.getClasss(), dto.getFaculty(), dto.getFullName(), dto.getAccountId());
        } else {
            // Nêu add new account mà trùng username thì sẽ thông báo tới người dùng
            List<Account> list = accountService.findAll();
            for(Account account : list){
                String userName = account.getUserName();
                if(userName.equals(newUserName)){
                    model.addAttribute("message", "Tài khoản đã tồn tại !!!");
                    return new ModelAndView("admin/account/addOrEdit");
                }
            }
            String password = dto.getPassWord();
            if (!rePassWord.equals(password)) {
                return new ModelAndView("admin/account/addOrEdit");
            }
            dto.setRole(0);
            Account entity = new Account();
            BeanUtils.copyProperties(dto, entity);
            accountService.save(entity);
        }
        model.addAttribute("message", "Account is saved!");
        return new ModelAndView("redirect:/user/report", model);
    }

//    @RequestMapping("")
//    public String list(ModelMap model){
//        List<Account> list = accountService.findAll();
//        model.addAttribute("accounts", list);
//        return "admin/account/list";
//    }

    @GetMapping("")
    public String searchpaginated(ModelMap model,
                                  @RequestParam(name = "fullName", required = false) String fullName,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size) {
        //Nếu k có giá trị thì sẽ lấy giá trị mặc định current page = 1 và số record sẽ là 5
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        //Thêm các thông số vào pageable
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("fullName"));
        Page<Account> resultPage;
        if (StringUtils.hasText(fullName)) {
            resultPage = accountService.findByFullNameContaining(fullName, pageable);
            model.addAttribute("fullName", fullName);
        } else {
            //File theo các giá trị mà pageable đã có
            resultPage = accountService.findAll(pageable);
        }
        //tìm những trang gần kế trang được chọn để hiện thị trong phạm vi là 2
        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);
            if (totalPages > 5) {
                if (end == totalPages) start = end - 5;
                else if (start == 1) end = start + 5;
            }
            //Lấy list number những trang liền từ start -> end bao gồm cả trang đang chọn là số đứng giữa
            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("accountPage", resultPage);
        return "admin/account/list";
    }
}
