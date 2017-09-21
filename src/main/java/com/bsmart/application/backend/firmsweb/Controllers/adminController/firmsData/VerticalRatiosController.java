package com.bsmart.application.backend.firmsweb.Controllers.adminController.firmsData;

import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Sectors;
import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.VerticalRatios;
import com.bsmart.application.backend.firmsweb.Repository.SectorRepository;
import com.bsmart.application.backend.firmsweb.Repository.VerticalRatiosRepository;
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
public class VerticalRatiosController {

    @Autowired
    UserService userService;

    @Autowired
    VerticalRatiosRepository repository;

    @Autowired
    SectorRepository sectorRepository;

    // Dikey Oranlar Listesi //
    @RequestMapping("admin/firmsData/verticalRatios/list")
    public String VerticalRatiosList(Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        Iterable<VerticalRatios> verticalRatios = this.repository.findAll();
        model.addAttribute("verticalRatios", verticalRatios);
        Iterable<Sectors> sectors = sectorRepository.findAll();
        model.addAttribute("sectors", sectors);

        // Yeni Dikey Oranlar Ekle //
        model.addAttribute("verticalRatio", new VerticalRatios());
        return "admin/firmsData/verticalRatios/list";
    }

    // Yeni Dikey Oran Ekle - POST //
    @RequestMapping("admin/firmsData/verticalRatios")
    public String VerticalRatiosRegister(@Valid VerticalRatios verticalRatios, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("verticalRatioFailure", true);
            return "redirect:/admin/firmsData/verticalRatios/list";
        } else if (verticalRatios.getId() == null) {
            redirectAttributes.addFlashAttribute("verticalRatioRegisterSuccess", true);
        } else {
            redirectAttributes.addFlashAttribute("verticalRatioUpdateSuccess", true);
        }
        repository.save(verticalRatios);
        return "redirect:/admin/firmsData/verticalRatios/list";
    }

    // Dikey Oran Güncelleme //
    @RequestMapping("admin/firmsData/verticalRatios/update/{id}")
    public String VerticalRatiosUpdate(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        model.addAttribute("verticalRatio", repository.findOne(id));
        Iterable<Sectors> sectors = sectorRepository.findAll();
        model.addAttribute("sectors", sectors);
        return "admin/firmsData/verticalRatios/update";
    }

    // Dikey Oran Sil //
    @RequestMapping("/admin/firmsData/verticalRatios/delete/{id}")
    public String VerticalRatiosDelete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        repository.delete(id);
        redirectAttributes.addFlashAttribute("verticalRatioDeleteSuccess", true);
        return "redirect:/admin/firmsData/verticalRatios/list";
    }
}
