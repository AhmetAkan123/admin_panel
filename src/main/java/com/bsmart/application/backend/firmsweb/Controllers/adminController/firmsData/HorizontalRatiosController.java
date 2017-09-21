package com.bsmart.application.backend.firmsweb.Controllers.adminController.firmsData;

import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.HorizontalRatios;
import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Sectors;
import com.bsmart.application.backend.firmsweb.Repository.HorizontalRatiosRepository;
import com.bsmart.application.backend.firmsweb.Repository.SectorRepository;
import com.bsmart.application.backend.firmsweb.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class HorizontalRatiosController {

    @Autowired
    UserService userService;

    @Autowired
    HorizontalRatiosRepository repository;

    @Autowired
    SectorRepository sectorRepository;

    // Yatay Oran Listesi //
    @RequestMapping("/admin/firmsData/horizontalRatios/list")
    public String HorizontalRatiosList(Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        Iterable<HorizontalRatios> horizontalRatios = this.repository.findAll();
        model.addAttribute("horizontalRatios", horizontalRatios);
        Iterable<Sectors> sectors = sectorRepository.findAll();
        model.addAttribute("sectors", sectors);

        // Yeni Yatay Oran Ekle //
        model.addAttribute("horizontalRatio", new HorizontalRatios());
        return "admin/firmsData/horizontalRatios/list";
    }

    // Yeni Yatay Oran - POST //
    @RequestMapping("admin/firmsData/horizontalRatios")
    public String HorizontalRatiosRegister(@Valid HorizontalRatios horizontalRatios, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("horizontalRatioFailure", true);
            return "redirect:/admin/firmsData/horizontalRatios/list";
        } else if (horizontalRatios.getId() == null) {
            redirectAttributes.addFlashAttribute("horizontalRatioRegisterSuccess", true);
        } else {
            redirectAttributes.addFlashAttribute("horizontalRatioUpdateSuccess", true);
        }

        repository.save(horizontalRatios);
        return "redirect:/admin/firmsData/horizontalRatios/list";
    }

    // Yatay Oran Güncelleme //
    @RequestMapping("admin/firmsData/horizontalRatios/update/{id}")
    public String HorizontalRatiosUpdate(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        model.addAttribute("horizontalRatio", repository.findOne(id));
        Iterable<Sectors> sectors = sectorRepository.findAll();
        model.addAttribute("sectors", sectors);
        return "admin/firmsData/horizontalRatios/update";
    }

    // Yatay Oran Sil //
    @RequestMapping("admin/firmsData/horizontalRatios/delete/{id}")
    public String HorizontalRatisDelete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        repository.delete(id);
        redirectAttributes.addFlashAttribute("horizontalRatioDeleteSuccess", true);
        return "redirect:/admin/firmsData/horizontalRatios/list";
    }


}
