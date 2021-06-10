package com.bkdn.pbl3.controller.admin;

import com.bkdn.pbl3.domain.Zone;
import com.bkdn.pbl3.model.ZoneDto;
import com.bkdn.pbl3.service.ZoneService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/zone")
public class ZoneController {
    @Autowired
    ZoneService zoneService;

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("zone", new ZoneDto());
        return "admin/zone/addOrEdit";
    }
    @GetMapping("edit/{zoneId}")
    public ModelAndView edit(ModelMap model, @PathVariable("zoneId") String zoneId){
        Optional<Zone> opt = zoneService.findById(zoneId);
        ZoneDto dto = new ZoneDto();
        if(opt.isPresent()){
            Zone entity = opt.get();
            BeanUtils.copyProperties(entity, dto);
            dto.setIsEdit(true);
            model.addAttribute("zone", dto);
            return new ModelAndView("admin/zone/addOrEdit", model);
        }
        model.addAttribute("message", "Zone is not existed");
        return new ModelAndView("foward:/admin/zone",model);
    }
    @GetMapping("delete/{zoneId}")
    public String delete(){
        return "redirect:/admin/zone";
    }

    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model, ZoneDto dto){
        Zone entity = new Zone();
        BeanUtils.copyProperties(dto,entity);
        zoneService.save(entity);
        model.addAttribute("message", "Zone is saved!");
        return new ModelAndView("forward:/admin/zone",model);
    }

    @RequestMapping("")
    public String list(ModelMap model){
        List<Zone> list = zoneService.findAll();
        model.addAttribute("zone", list);
        return "admin/zone/list";
    }
    @GetMapping("search")
    public String search(){
        return "admin/zone/search";
    }

}
