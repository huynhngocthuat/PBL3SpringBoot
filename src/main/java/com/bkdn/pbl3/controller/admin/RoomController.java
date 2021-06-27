package com.bkdn.pbl3.controller.admin;

import com.bkdn.pbl3.domain.Room;
import com.bkdn.pbl3.domain.Zone;
import com.bkdn.pbl3.model.RoomDto;
import com.bkdn.pbl3.model.ZoneDto;
import com.bkdn.pbl3.service.EquipmentService;
import com.bkdn.pbl3.service.RoomService;
import com.bkdn.pbl3.service.ZoneService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("admin/room")
public class RoomController {
    @Autowired
    ZoneService zoneService;

    @Autowired
    RoomService roomService;

    @Autowired
    EquipmentService equipmentService;

    @ModelAttribute("zones")
    public List<ZoneDto> getZones(){
        return zoneService.findAll().stream().map(item ->{
            ZoneDto dto = new ZoneDto();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("room", new RoomDto());
        return "admin/room/addOrEdit";
    }

    @GetMapping("edit/{roomId}")
    public ModelAndView edit(ModelMap model, @PathVariable("roomId") String roomId){
        Optional<Room> opt = roomService.findById(roomId);
        RoomDto dto = new RoomDto();
        if(opt.isPresent()){
            Room entity = opt.get();
            BeanUtils.copyProperties(entity, dto);
            dto.setIsEdit(true);
            model.addAttribute("room", dto);
            return new ModelAndView("admin/room/addOrEdit", model);
        }
        model.addAttribute("message", "Room is not existed");
        return new ModelAndView("forward:/admin/room",model);
    }

    @GetMapping("delete/{roomId}")
    public ModelAndView delete(ModelMap model, @PathVariable("roomId") String roomId){
        roomService.deleteById(roomId);
        model.addAttribute("message", "Room is deleted!");
        return new ModelAndView("forward:/admin/room", model);
    }

    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("room") RoomDto dto, BindingResult result)
    {
        if(result.hasErrors()){
            return new ModelAndView("admin/room/addOrEdit");
        }

        if(dto.getIsEdit()){
            roomService.updateRoom(dto.getRoomFunction(), dto.getZoneId(), dto.getRoomId());
        }
        else {
            Room entity = new Room();
            BeanUtils.copyProperties(dto,entity);

            Zone zone = new Zone();
            zone.setZoneId(dto.getZoneId());
            entity.setZone(zone);

            roomService.save(entity);
        }
        model.addAttribute("message", "Room is saved!");
        return new ModelAndView("redirect:/admin/room",model);
    }

//    @RequestMapping("")
//    public String list(ModelMap model){
//        List<Room> list = roomService.findAll();
//        model.addAttribute("rooms", list);
//        return "admin/room/list";
//    }
    @GetMapping("")
    public String searchpaginated(ModelMap model,
                                  @RequestParam(name = "roomId", required = false) String roomId,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("roomId"));
        Page<Room> resultPage;
        if (StringUtils.hasText(roomId)) {
            resultPage = roomService.findByRoomIdContaining(roomId, pageable);
            model.addAttribute("roomId", roomId);
        } else {
            resultPage = roomService.findAll(pageable);
        }
        int totalPages = resultPage.getTotalPages();
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
        model.addAttribute("roomPage", resultPage);
        return "admin/room/list";
    }
}
