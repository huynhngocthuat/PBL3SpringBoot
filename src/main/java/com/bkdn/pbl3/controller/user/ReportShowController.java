package com.bkdn.pbl3.controller.user;

import com.bkdn.pbl3.domain.Account;
import com.bkdn.pbl3.domain.Equipment;
import com.bkdn.pbl3.domain.Report;
import com.bkdn.pbl3.model.ReportShow;
import com.bkdn.pbl3.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.*;
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

//    @RequestMapping("")
//    public String listShow(ModelMap modelMap) throws ParseException {
//        List<ReportShow> listShow = reportService.getReportShow();
//        modelMap.addAttribute("listShow", listShow);
//        return "/user/report/listShow";
//    }

    @GetMapping("")
    public String paginated(ModelMap model,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size) {
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
        return "user/report/listShow";
    }
}
