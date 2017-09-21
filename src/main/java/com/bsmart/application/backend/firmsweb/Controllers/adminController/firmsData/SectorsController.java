package com.bsmart.application.backend.firmsweb.Controllers.adminController.firmsData;

import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Sectors;
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
public class SectorsController {

    @Autowired
    UserService userService;

    @Autowired
    SectorRepository repository;

    // Sektör Listesi //
    @RequestMapping("/admin/firmsData/sectors/list")
    public String SectorsList(Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        Iterable<Sectors> sectors = repository.findAll();
        model.addAttribute("sectors", sectors);

        // Yeni Sektör Ekleme //
        model.addAttribute("sector", new Sectors());
        return "admin/firmsData/sectors/list";
    }

    // Yeni Sektör - POST //
    @RequestMapping("admin/firmsData/sectors")
    public String SectorRegister(@Valid Sectors sectors, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("sectorFailure", true);
            return "redirect:/admin/firmsData/sectors/list";
        } else if (sectors.getId() == null) {
            redirectAttributes.addFlashAttribute("sectorRegisterSuccess", true);
        } else {
            redirectAttributes.addFlashAttribute("sectorUpdateSuccess", true);
        }

        repository.save(sectors);
        return "redirect:/admin/firmsData/sectors/list";
    }

    // Sektör Güncelleme //
    @RequestMapping("admin/firmsData/sectors/update/{id}")
    public String SectorsUpdate(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        model.addAttribute("sector", repository.findOne(id));
        return "admin/firmsData/sectors/update";
    }

    // Sektör Sil //
    @RequestMapping("admin/firmsData/sectors/delete/{id}")
    public String SectorDelete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("sectorDeleteSuccess", true);
        repository.delete(id);
        return "redirect:/admin/firmsData/sectors/list";
    }


}
