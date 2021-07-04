package com.bkdn.pbl3.controller.admin;

import com.bkdn.pbl3.domain.*;
import com.bkdn.pbl3.model.ResponseDto;
import com.bkdn.pbl3.service.ReportService;
import com.bkdn.pbl3.service.ResponseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.sql.Date;
import java.util.Optional;

@Controller
@RequestMapping("admin/response")
public class ResponseController {
//    1: xac nha thong tin, 2: đã xử lý, 3: báo cáo sai
    @Autowired
    private ResponseService responseService;
    @Autowired
    private ReportService reportService;
    @GetMapping("/add/{reportId}")
    public String add(Model model, @PathVariable("reportId") Long reportId){
        Optional<Report> report = reportService.findById(reportId);
        model.addAttribute("report", report.get());
        model.addAttribute("response", new ResponseDto());
        return "admin/response/addOrEdit";
    }


    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model,  @RequestParam(name = "reportId", required = false) Long reportId,
                                     @Valid @ModelAttribute("response") ResponseDto dto, BindingResult result)
    {
        if(result.hasErrors()){
            Optional<Report> report = reportService.findById(reportId);
            model.addAttribute("report", report.get());
            return new ModelAndView("admin/response/addOrEdit");
        }
        long miliSeconds = System.currentTimeMillis();
        Date date = new Date(miliSeconds);
        dto.setResponsedDate(date);
        dto.setAccountId(1);

        Response entity = new Response();
        BeanUtils.copyProperties(dto,entity);

        Report report = new Report();
        report.setReportId(reportId);
        entity.setReport(report);

        Account account = new Account();
        account.setAccountId(dto.getAccountId());
        entity.setAccount(account);

        responseService.save(entity);
        model.addAttribute("message", "Response is saved!");
        return new ModelAndView("redirect:/admin",model);
    }

}
