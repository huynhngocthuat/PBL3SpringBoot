package com.bkdn.pbl3.controller.user;

import com.bkdn.pbl3.domain.Account;
import com.bkdn.pbl3.model.ReportShow;
import com.bkdn.pbl3.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("user")
public class ReportShowController {
    @Autowired
    ReportService reportService;

    @RequestMapping("")
    public String listShow(ModelMap modelMap) throws ParseException {
        List<ReportShow> listShow = reportService.getReportShow();
        modelMap.addAttribute("listShow", listShow);
        return "/user/report/listShow";
    }

//    @GetMapping("")
//    public String searchpaginated(ModelMap model,
//
//                                  @RequestParam("page") Optional<Integer> page,
//                                  @RequestParam("size") Optional<Integer> size) {
//        int currentPage = page.orElse(1);
//        int pageSize = size.orElse(5);
//        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("fullName"));
//        Page<Account> resultPage;
//            resultPage = reportService.getReportShow(pageable);
//        int totalPages = resultPage.getTotalPages();
//        if (totalPages > 0) {
//            int start = Math.max(1, currentPage - 2);
//            int end = Math.min(currentPage + 2, totalPages);
//            if (totalPages > 5) {
//                if (end == totalPages) start = end - 5;
//                else if (start == 1) end = start + 5;
//            }
//            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
//                    .boxed()
//                    .collect(Collectors.toList());
//            model.addAttribute("pageNumbers", pageNumbers);
//        }
//        model.addAttribute("accountPage", resultPage);
//        return "admin/account/list";
//    }
}
