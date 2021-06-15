package com.bkdn.pbl3.controller.admin;

import com.bkdn.pbl3.domain.Equipment;
import com.bkdn.pbl3.domain.Room;
import com.bkdn.pbl3.model.EquipmentDto;
import com.bkdn.pbl3.model.RoomDto;
import com.bkdn.pbl3.service.EquipmentService;
import com.bkdn.pbl3.service.RoomService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin/equipment")
public class EquipmenController {
    @Autowired
    RoomService roomService;
    @Autowired
    EquipmentService equipmentService;

    @ModelAttribute("rooms")
    public List<RoomDto> getZones(){
        return roomService.findAll().stream().map(item ->{
            RoomDto dto = new RoomDto();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("equipment", new EquipmentDto());
        return "admin/equipment/addOrEdit";
    }

    @GetMapping("edit/{equipmentId}")
    public ModelAndView edit(ModelMap model, @PathVariable("equipmentId") String equipmentId){
        Optional<Equipment> opt = equipmentService.findById(equipmentId);
        EquipmentDto dto = new EquipmentDto();
        if(opt.isPresent()){
            Equipment entity = opt.get();
            BeanUtils.copyProperties(entity, dto);
            dto.setIsEdit(true);
            model.addAttribute("equipment", dto);
            return new ModelAndView("admin/equipment/addOrEdit", model);
        }
        model.addAttribute("message", "Equipment is not existed");
        return new ModelAndView("forward:/admin/equipment",model);
    }

    @GetMapping("delete/{equipmentId}")
    public ModelAndView delete(ModelMap model, @PathVariable("equipmentId") String equipmentId){
        equipmentService.deleteById(equipmentId);
        model.addAttribute("message", "Equipment is deleted!");
        return new ModelAndView("forward:/admin/equipment", model);
    }

    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("equipment") EquipmentDto dto, BindingResult result)
    {
        if(result.hasErrors()){
            return new ModelAndView("admin/equipment/addOrEdit");
        }

        Equipment entity = new Equipment();
        BeanUtils.copyProperties(dto,entity);

        Room room = new Room();
        room.setRoomId(dto.getRoomId());
        entity.setRoom(room);

        equipmentService.save(entity);
        model.addAttribute("message", "Equipment is saved!");
        return new ModelAndView("forward:/admin/equipment",model);
    }

    @RequestMapping("")
    public String list(ModelMap model){
        List<Equipment> list = equipmentService.findAll();
        model.addAttribute("equipments", list);
        return "admin/equipment/list";
    }
}
