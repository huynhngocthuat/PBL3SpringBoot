package com.bkdn.pbl3.controller;

import com.bkdn.pbl3.domain.Account;
import com.bkdn.pbl3.model.LoginDto;
import com.bkdn.pbl3.model.ReportShow;
import com.bkdn.pbl3.service.AccountService;
import com.bkdn.pbl3.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class homeController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/login")
    public String login(ModelMap model){
        return "/login";
    }

    @GetMapping(value = "/logoutSuccessful")
    public String logoutSuccessfulPage(ModelMap model,
                                       @RequestParam("page") Optional<Integer> page,
                                       @RequestParam("size") Optional<Integer> size) {
        //Thiệt lập lại dữ liệu của form main sau khi log out
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        PagedListHolder<ReportShow> resultPage = new PagedListHolder<>(reportService.getReportShow());
        resultPage.setPage(currentPage-1);
        resultPage.setPageSize(pageSize);
        MutableSortDefinition x = new MutableSortDefinition ("1", true, false);
        resultPage.setSort(x);
        int totalPages = resultPage.getPageCount();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);
            if (totalPages > 5) {
                if (end == totalPages) start = end - 5;
                else if (start == 1) end = start + 5;
            }
            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        boolean hasContent = true;
        if(resultPage.getPageCount() >= currentPage){
            hasContent = false;
        }
        model.addAttribute("hasContent", hasContent);
        model.addAttribute("listShow", resultPage);
        model.addAttribute("message", "Bạn cần đăng nhập để báo cáo !");
        return "user/report/listShow";
    }


}
