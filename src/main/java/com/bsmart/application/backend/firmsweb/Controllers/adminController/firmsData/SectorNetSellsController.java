package com.bsmart.application.backend.firmsweb.Controllers.adminController.firmsData;

import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.SectorNetSells;
import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Sectors;
import com.bsmart.application.backend.firmsweb.Repository.SectorNetSellsRepository;
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
public class SectorNetSellsController {

    @Autowired
    UserService userService;

    @Autowired
    SectorNetSellsRepository repository;

    @Autowired
    SectorRepository sectorRepository;

    // Sektör Net Satış Listesi //
    @RequestMapping("admin/firmsData/sectorNetSells/list")
    public String SectorNetSellsList(Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        Iterable<SectorNetSells> sells = this.repository.findAll();
        model.addAttribute("sells", sells);
        Iterable<Sectors> sectors = sectorRepository.findAll();
        model.addAttribute("sectors", sectors);

        // Yeni Satış Ekle //
        model.addAttribute("sell", new SectorNetSells());
        return "admin/firmsData/sectorNetSells/list";
    }

    // Yeni Sektör Net Satışı Ekleme - POST //
    @RequestMapping("admin/firmsData/sectorNetSells")
    public String SectorNetSellRegister(@Valid SectorNetSells sells, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("sectorNetSellFailure", true);
            return "redirect:/admin/firmsData/sectorNetSells/list";
        } else if (sells.getId() == null) {
            redirectAttributes.addFlashAttribute("sectorNetSellRegisterSuccess", true);
        } else {
            redirectAttributes.addFlashAttribute("sectorNetSellUpdateSuccess", true);
        }
        repository.save(sells);
        return "redirect:/admin/firmsData/sectorNetSells/list";
    }

    // Sektör Net Satış Güncelleme //
    @RequestMapping("admin/firmsData/sectorNetSells/update/{id}")
    public String SectorNetSellUpdate(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        model.addAttribute("sell", repository.findOne(id));
        Iterable<Sectors> sectors = sectorRepository.findAll();
        model.addAttribute("sectors", sectors);
        return "admin/firmsData/sectorNetSells/update";
    }

    // Sektör Net Satış Sil //
    @RequestMapping("/admin/firmsData/sectorNetSells/delete/{id}")
    public String SectorNetSellsDelete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("sectorNetSellDeleteSuccess", true);
        repository.delete(id);
        return "redirect:/admin/firmsData/sectorNetSells/list";
    }

}
