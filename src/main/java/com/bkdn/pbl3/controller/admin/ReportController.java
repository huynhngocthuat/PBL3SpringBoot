package com.bkdn.pbl3.controller.admin;

import com.bkdn.pbl3.domain.*;
import com.bkdn.pbl3.model.*;
import com.bkdn.pbl3.service.*;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("user/report")
public class ReportController {
//    --0: chưa được nhận tin, 1: chưa xử lý, 2: đã xử lý, 3: báo cáo sai

    @Autowired
    ReportService reportService;
    @Autowired
    ZoneService zoneService;
    @Autowired
    RoomService roomService;
    @Autowired
    EquipmentService equipmentService;
    @Autowired
    StatusService statusService;
    @Autowired
    StorageService storageService;

    @ModelAttribute("zones")
    public List<ZoneDto> getZones(){
        return zoneService.findAll().stream().map(item ->{
            ZoneDto dto = new ZoneDto();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).collect(Collectors.toList());
    }
    @ModelAttribute("rooms")
    public List<RoomDto> getRooms(){
        return roomService.findAll().stream().map(item ->{
            RoomDto dto = new RoomDto();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).collect(Collectors.toList());
    }
    @ModelAttribute("equipments")
    public List<EquipmentDto> getEquipments(){
        return equipmentService.findAll().stream().map(item ->{
            EquipmentDto dto = new EquipmentDto();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).collect(Collectors.toList());
    }
    @ModelAttribute("statuses")
    public List<StatusDto> getStatuses(){
        return statusService.findAll().stream().map(item ->{
            StatusDto dto = new StatusDto();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("report", new ReportDto());
        return "user/report/addOrEdit";
    }

    @GetMapping("edit/{reportId}")
    public ModelAndView edit(ModelMap model, @PathVariable("reportId") Long reportId){
        Optional<Report> opt = reportService.findById(reportId);
        ReportDto dto = new ReportDto();
        if(opt.isPresent()){
            Report entity = opt.get();
            BeanUtils.copyProperties(entity, dto);
            dto.setEditing(true);
            model.addAttribute("report", dto);
            return new ModelAndView("user/report/addOrEdit", model);
        }
        model.addAttribute("message", "Report is not existed");
        return new ModelAndView("forward:/user/report",model);
    }

    @GetMapping("delete/{reportId}")
    public ModelAndView delete(ModelMap model, @PathVariable("reportId") Long reportId){
        reportService.deleteById(reportId);
        model.addAttribute("message", "Report is deleted!");
        return new ModelAndView("forward:/user/report", model);
    }

    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("report") ReportDto dto, BindingResult result)
    {
        if(result.hasErrors()){
            return new ModelAndView("user/report/addOrEdit");
        }
        long miliSeconds = System.currentTimeMillis();
        Date date = new Date(miliSeconds);
        dto.setReportedDate(date);
        dto.setReportStatus(0);
        dto.setAccountId(2);

        Report entity = new Report();
        BeanUtils.copyProperties(dto,entity);

        Account account = new Account();
        account.setAccountId(dto.getAccountId());
        entity.setAccount(account);

        Equipment equipment = new Equipment();
        equipment.setEquipmentId(dto.getEquipmentId());
        entity.setEquipment(equipment);

        Status status = new Status();
        status.setStatusId(dto.getStatusId());
        entity.setStatus(status);

        if(!dto.getImageFile().isEmpty()){
            UUID uuid = UUID.randomUUID();
            String uuString = uuid.toString();
            entity.setImage(storageService.getStoredFilename(dto.getImageFile(), uuString));
            storageService.store(dto.getImageFile(), entity.getImage());
        }

        reportService.save(entity);
        model.addAttribute("message", "Report is saved!");
        return new ModelAndView("forward:/user/report",model);
    }

    @RequestMapping("")
    public String list(ModelMap model){
        List<Report> list = reportService.findAll();
        model.addAttribute("reports", list);
        return "/admin/report/list";
    }
}
