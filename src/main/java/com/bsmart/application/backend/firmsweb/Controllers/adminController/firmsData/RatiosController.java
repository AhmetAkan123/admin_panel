package com.bsmart.application.backend.firmsweb.Controllers.adminController.firmsData;

import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Ratios;
import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Sectors;
import com.bsmart.application.backend.firmsweb.Repository.RatiosRepository;
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
public class RatiosController {

    @Autowired
    UserService userService;

    @Autowired
    RatiosRepository repository;

    @Autowired
    SectorRepository sectorRepository;

    // Rasyolar Listesi //
    @RequestMapping("admin/firmsData/ratios/list")
    public String RatiosList(Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        Iterable<Ratios> ratios = this.repository.findAll();
        model.addAttribute("ratios", ratios);
        Iterable<Sectors> sectors = sectorRepository.findAll();
        model.addAttribute("sectors", sectors);

        // Yeni Rasyo Ekle //
        model.addAttribute("ratio", new Ratios());
        return "admin/firmsData/ratios/list";
    }

    // Yeni Rasyo Ekleme - POST //
    @RequestMapping("admin/firmsData/ratios")
    public String RatiosRegister(@Valid Ratios ratios, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("ratioFailure", true);
            return "redirect:/admin/firmsData/ratios/list";
        } else if (ratios.getId() == null) {
            redirectAttributes.addFlashAttribute("ratioRegisterSuccess", true);
        } else {
            redirectAttributes.addFlashAttribute("ratioUpdateSuccess", true);
        }
        repository.save(ratios);
        return "redirect:/admin/firmsData/ratios/list";
    }

    // Rasyo Güncelleme //
    @RequestMapping("admin/firmsData/ratios/update/{id}")
    public String RatioUpdate(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        model.addAttribute("ratio", repository.findOne(id));
        Iterable<Sectors> sectors = sectorRepository.findAll();
        model.addAttribute("sectors", sectors);
        return "admin/firmsData/ratios/update";
    }

    // Rasyon Sil //
    @RequestMapping("admin/firmsData/ratios/delete/{id}")
    public String RatioDelete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("ratioDeleteSuccess", true);
        repository.delete(id);
        return "redirect:/admin/firmsData/ratios/list";
    }

}
