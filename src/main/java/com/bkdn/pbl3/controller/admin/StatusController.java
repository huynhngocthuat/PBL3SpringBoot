package com.bkdn.pbl3.controller.admin;

import com.bkdn.pbl3.domain.Equipment;
import com.bkdn.pbl3.domain.Status;
import com.bkdn.pbl3.model.EquipmentDto;
import com.bkdn.pbl3.model.StatusDto;
import com.bkdn.pbl3.service.EquipmentService;
import com.bkdn.pbl3.service.StatusService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin/status")
public class StatusController {
    @Autowired
    EquipmentService equipmentService;
    @Autowired
    StatusService statusService;
    @ModelAttribute("equipments")
    public List<EquipmentDto> getEquipments(){
        return equipmentService.findAll().stream().map(item ->{
            EquipmentDto dto = new EquipmentDto();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).collect(Collectors.toList());
    }
    @GetMapping("/add/{equipmentId}")
    public String add(Model model, @PathVariable("equipmentId") String equipmentId){
        model.addAttribute("status", new StatusDto());
        model.addAttribute("equipmentId", equipmentId);
        return "admin/status/addOrEdit";
    }

    @GetMapping("edit/{statusId}")
    public ModelAndView edit(ModelMap model, @PathVariable("statusId") String statusId){
        Optional<Status> opt = statusService.findById(statusId);
        String equipmentId = opt.get().getEquipment().getEquipmentId();
        StatusDto dto = new StatusDto();
        if(opt.isPresent()){
            Status entity = opt.get();
            BeanUtils.copyProperties(entity, dto);
            dto.setIsEdit(true);
            model.addAttribute("status", dto);
            model.addAttribute("equipmentId", equipmentId);
            return new ModelAndView("admin/status/addOrEdit", model);
        }
        model.addAttribute("message", "Status is not existed");
        //model.addAttribute("equipmentId", dto.getEquipmentId());
        return new ModelAndView("forward:/admin/status/"+equipmentId,model);
    }

    @GetMapping("delete/{statusId}")
    public ModelAndView delete(ModelMap model, @PathVariable("statusId") String statusId){
        Optional<Status> opt = statusService.findById(statusId);
        String equipmentId = opt.get().getEquipment().getEquipmentId();
        statusService.deleteById(statusId);
        model.addAttribute("message", "Status is deleted!");
        return new ModelAndView("forward:/admin/status/" + equipmentId, model);
    }

    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("status") StatusDto dto, BindingResult result)
    {
        if(result.hasErrors()){
            return new ModelAndView("admin/status/addOrEdit");
        }

        Status entity = new Status();
        BeanUtils.copyProperties(dto,entity);

        Equipment equipment = new Equipment();
        equipment.setEquipmentId(dto.getEquipmentId());
        entity.setEquipment(equipment);

        statusService.save(entity);
        model.addAttribute("message", "Status is saved!");
        return new ModelAndView("forward:/admin/status/" + dto.getEquipmentId(),model);
    }

    @RequestMapping("{equipmentId}")
    public String list(ModelMap model, @PathVariable("equipmentId") String equipmentId){
        Equipment equipment = equipmentService.getById(equipmentId);
        List<Status> list = statusService.findStatusByEquipment(equipment);
        model.addAttribute("statuses", list);
        model.addAttribute("equipmentId", equipmentId);
        return "admin/status/list";
    }
}
