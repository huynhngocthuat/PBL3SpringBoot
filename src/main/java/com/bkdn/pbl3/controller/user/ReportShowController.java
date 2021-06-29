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
}
