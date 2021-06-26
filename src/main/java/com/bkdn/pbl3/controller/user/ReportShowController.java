package com.bkdn.pbl3.controller.user;

import com.bkdn.pbl3.model.ReportShow;
import com.bkdn.pbl3.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.util.List;

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
