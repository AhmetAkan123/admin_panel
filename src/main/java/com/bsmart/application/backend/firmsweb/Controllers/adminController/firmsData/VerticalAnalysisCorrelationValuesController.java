package com.bsmart.application.backend.firmsweb.Controllers.adminController.firmsData;

import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Sectors;
import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.VerticalAnalysisCorrelationValues;
import com.bsmart.application.backend.firmsweb.Repository.SectorRepository;
import com.bsmart.application.backend.firmsweb.Repository.VerticalAnalysisCorrelationValuesRepository;
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
public class VerticalAnalysisCorrelationValuesController {

    @Autowired
    UserService userService;

    @Autowired
    VerticalAnalysisCorrelationValuesRepository repository;

    @Autowired
    SectorRepository sectorRepository;

    // Vertical Analysis Listesi //
    @RequestMapping("admin/firmsData/verticalAnalysis/list")
    public String VerticalAnalysisList(Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        Iterable<VerticalAnalysisCorrelationValues> verticalAnalysis = this.repository.findAll();
        model.addAttribute("verticalAnalysis", verticalAnalysis);
        Iterable<Sectors> sectors = sectorRepository.findAll();
        model.addAttribute("sectors", sectors);

        // Yeni Vertical Analysis Ekle //
        model.addAttribute("verticalAnalys", new VerticalAnalysisCorrelationValues());
        return "admin/firmsData/verticalAnalysis/list";
    }

    // Yeni Vertical Analysis Ekleme - POST //
    @RequestMapping("admin/firmsData/verticalAnalysis")
    public String VerticalAnalysRegister(@Valid VerticalAnalysisCorrelationValues verticalAnalysis, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("verticalAnalysisFailure", true);
            return "redirect:/admin/firmsData/verticalAnalysis/list";
        } else if (verticalAnalysis.getId() == null) {
            redirectAttributes.addFlashAttribute("verticalAnalysisRegisterSuccess", true);
        } else {
            redirectAttributes.addFlashAttribute("verticalAnalysisUpdateSuccess", true);
        }
        repository.save(verticalAnalysis);
        return "redirect:/admin/firmsData/verticalAnalysis/list";
    }

    // Vertical Analysis Güncelleme //
    @RequestMapping("admin/firmsData/verticalAnalysis/update/{id}")
    public String VerticalAnalysisUpdate(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        model.addAttribute("verticalAnalys", repository.findOne(id));
        Iterable<Sectors> sectors = sectorRepository.findAll();
        model.addAttribute("sectors", sectors);
        return "admin/firmsData/verticalAnalysis/update";
    }

    // Vertical Analysis Sil //
    @RequestMapping("/admin/firmsData/verticalAnalysis/delete/{id}")
    public String VerticalAnalysisDelete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        repository.delete(id);
        redirectAttributes.addFlashAttribute("verticalAnalysisDeleteSuccess", true);
        return "redirect:/admin/firmsData/verticalAnalysis/list";
    }

}
