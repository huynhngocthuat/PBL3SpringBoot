package com.bkdn.pbl3.controller.user;


import com.bkdn.pbl3.model.ReportShow;
import com.bkdn.pbl3.model.ZoneDto;
import com.bkdn.pbl3.service.ReportService;
import com.bkdn.pbl3.service.ZoneService;
import com.bkdn.pbl3.utils.WebUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ReportShowController {
    @Autowired
    ReportService reportService;
    @Autowired
    ZoneService zoneService;

//    @RequestMapping("")
//    public String listShow(ModelMap modelMap) throws ParseException {
//        List<ReportShow> listShow = reportService.getReportShow();
//        modelMap.addAttribute("listShow", listShow);
//        return "/user/report/listShow";
//    }

    @ModelAttribute("zones")
    public List<ZoneDto> getZones(){
        return zoneService.findAll().stream().map(item ->{
            ZoneDto dto = new ZoneDto();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).collect(Collectors.toList());
    }
    @GetMapping()
    public String paginated(ModelMap model,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size,
                            Principal principal) {
        if(principal!=null){
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String role = WebUtils.toString(loginedUser);
        boolean checkRole = false;
        if(role.equals("ROLE_ADMIN")){
            checkRole = true;
        }
        model.addAttribute("role", checkRole);
        model.addAttribute("message", "Xin chào " + loginedUser.getUsername() + ", bạn đăng nhập với quyền là " + role);
        }
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
