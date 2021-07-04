package com.bkdn.pbl3.controller.user;

import com.bkdn.pbl3.domain.*;
import com.bkdn.pbl3.model.*;
import com.bkdn.pbl3.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
import java.io.IOException;
import java.security.Principal;
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

    @Autowired
    ResponseService responseService;

    @Autowired
    AccountService accountService;
    //Xét các giá trị trong cbb để tạo mới một report
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
            dto.setZoneId(item.getZone().getZoneId());
            return dto;
        }).collect(Collectors.toList());
    }
    @ModelAttribute("equipments")
    public List<EquipmentDto> getEquipments(){
        return equipmentService.findAll().stream().map(item ->{
            EquipmentDto dto = new EquipmentDto();
            BeanUtils.copyProperties(item, dto);
            dto.setRoomId(item.getRoom().getRoomId());
            return dto;
        }).collect(Collectors.toList());
    }
    @ModelAttribute("statuses")
    public List<StatusDto> getStatuses(){
        return statusService.findAll().stream().map(item ->{
            StatusDto dto = new StatusDto();
            BeanUtils.copyProperties(item, dto);
            dto.setEquipmentId(item.getEquipment().getEquipmentId());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("report", new ReportDto());
        return "user/report/addOrEdit";
    }

    @GetMapping("edit/{reportId}")
    public ModelAndView edit(ModelMap model, @PathVariable("reportId") Long reportId, Principal principal){
        //Kiểm tra xem report đã được phản hồi hay chưa
        Optional<Report> opt = reportService.findById(reportId);
        if(opt.get().getIsEdit()==false){
            User loginedUser = (User) ((Authentication)principal).getPrincipal();
            String userName = loginedUser.getUsername();
            Account account = accountService.findByUserName(userName);
            List<Report> list = reportService.findReportByAccount(account);
            model.addAttribute("name", account.getFullName());
            model.addAttribute("reports", list);
            return new ModelAndView("user/report/listReportByAccount");
        }
        //Nếu chưa phản hồi có thể edit
        ReportDto dto = new ReportDto();
        if(opt.isPresent()){
            Report entity = opt.get();
            BeanUtils.copyProperties(entity, dto);
            dto.setAccountId(entity.getAccount().getAccountId());
            dto.setEquipmentId(entity.getEquipment().getEquipmentId());
            dto.setStatusId(entity.getStatus().getStatusId());
            dto.setEditing(true);
            model.addAttribute("report", dto);
            return new ModelAndView("user/report/addOrEdit", model);
        }
        model.addAttribute("message", "Report is not existed");
        return new ModelAndView("forward:/user/report",model);
    }

    //Load image trong report
    @GetMapping("images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename){
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("delete/{reportId}")
    public ModelAndView delete(ModelMap model, @PathVariable("reportId") Long reportId) throws IOException {
        Optional<Report> opt = reportService.findById(reportId);
        responseService.deleteResponseByReport(opt.get());
        if(opt.isPresent()){
            if(!StringUtils.isEmpty(opt.get().getImage())){
                storageService.delete(opt.get().getImage());
            }
            reportService.deleteById(reportId);
            model.addAttribute("massage", "Report is deleted!");
        }else {
            model.addAttribute("message", "Report is not found!");
        }

        return new ModelAndView("forward:/user/report", model);
    }

    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model, @RequestParam(name = "roomId", required = false) String roomId,
                                     @Valid @ModelAttribute("report") ReportDto dto, BindingResult result,
                                    Principal principal) throws IOException {
        //Nếu không đúng định dạng trong file DTO thì sẽ báo lỗi
        if(result.hasErrors()){
            return new ModelAndView("user/report/addOrEdit");
        }
        //Tìm kiếm account nào đang create report
        User loginedUser =(User) ((Authentication)principal).getPrincipal();
        Account accountReport = accountService.findByUserName(loginedUser.getUsername());
        long miliSeconds = System.currentTimeMillis();
        Date date = new Date(miliSeconds);
        dto.setReportedDate(date);
        dto.setReportStatus(0);
        dto.setAccountId(accountReport.getAccountId());
        //Nếu editing = true thì sẽ hiểu là đang edit report
        if(dto.getEditing()){
            String imageUpdate = null;
            if(!dto.getImageFile().isEmpty()){
                UUID uuid = UUID.randomUUID();
                String uuString = uuid.toString();
                imageUpdate = storageService.getStoredFilename(dto.getImageFile(), uuString);
                storageService.store(dto.getImageFile(), imageUpdate);
                Optional<Report> report = reportService.findById(dto.getReportId());
                if(!StringUtils.isEmpty(report.get().getImage())){
                    storageService.delete(report.get().getImage());
                }
            }else {
                Optional<Report> report = reportService.findById(dto.getReportId());
                if (report.isPresent()) {
                    imageUpdate = report.get().getImage();
                }
            }
            reportService.updateReport(imageUpdate, dto.getNote(), dto.getReportedDate(), dto.getEquipmentId(), dto.getStatusId(), dto.getReportId());
        }
        // false thì sẽ tạo một report mới
        else {
            Report entity = new Report();
            BeanUtils.copyProperties(dto,entity);

            Account account = new Account();
            account.setAccountId(dto.getAccountId());
            entity.setAccount(account);

            Equipment equipment = new Equipment();
            equipment.setEquipmentId(dto.getEquipmentId());
            Optional<Room> room = roomService.findById(roomId);
            if(room.isPresent()){
                equipment.setRoom(room.get());
                equipment.setEquipmentName(equipmentService.getById(dto.getEquipmentId()).getEquipmentName());
            }
            entity.setEquipment(equipment);

            Status status = new Status();
            status.setStatusId(dto.getStatusId());
            status.setEquipmentStatus(statusService.findById(dto.getStatusId()).get().getEquipmentStatus());
            entity.setStatus(status);

            //xu ly image
            if(!dto.getImageFile().isEmpty()){
                UUID uuid = UUID.randomUUID();
                String uuString = uuid.toString();
                entity.setImage(storageService.getStoredFilename(dto.getImageFile(), uuString));
                storageService.store(dto.getImageFile(), entity.getImage());
            }else {
                System.out.println("No image");
            }

            reportService.save(entity);
        }

        model.addAttribute("message", "Report is saved!");
        return new ModelAndView("redirect:/user/report",model);
    }

    @RequestMapping("")
    public String list(ModelMap model, Principal principal){
        User loginedUser = (User) ((Authentication)principal).getPrincipal();
        String userName = loginedUser.getUsername();
        Account account = accountService.findByUserName(userName);
        List<Report> list = reportService.findReportByAccount(account);
        model.addAttribute("name", account.getFullName());
        model.addAttribute("reports", list);
        return "/user/report/listReportByAccount";
    }

}
