package com.bsmart.application.backend.firmsweb.Controllers.adminController.firmsData;

import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Sectors;
import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.TrendAnalysis;
import com.bsmart.application.backend.firmsweb.Repository.SectorRepository;
import com.bsmart.application.backend.firmsweb.Repository.TrendAnalysisRepository;
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
public class TrendAnalysisController {

    @Autowired
    UserService userService;

    @Autowired
    TrendAnalysisRepository repository;

    @Autowired
    SectorRepository sectorRepository;

    // Trend Analiz Listesi //
    @RequestMapping("/admin/firmsData/trendAnalysis/list")
    public String TrendAnalysisList(Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        Iterable<TrendAnalysis> trendAnalysis = repository.findAll();
        model.addAttribute("trendAnalysis", trendAnalysis);
        Iterable<Sectors> sectors = sectorRepository.findAll();
        model.addAttribute("sectors", sectors);

        // Yeni Trend Analiz //
        model.addAttribute("trendAnalys", new TrendAnalysis());
        return "admin/firmsData/trendAnalysis/list";
    }

    // Yeni Trend Analiz - POST //
    @RequestMapping("admin/firmsData/trendAnalysis")
    public String TrendAnalysisRegister(@Valid TrendAnalysis trendAnalysis, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("trendAnalysisFailure", true);
            return "redirect:/admin/firmsData/trendAnalysis/list";
        } else if (trendAnalysis.getId() == null) {
            redirectAttributes.addFlashAttribute("trendAnalysisRegisterSuccess", true);
        } else {
            redirectAttributes.addFlashAttribute("trendAnalysisUpdateSuccess", true);
        }

        repository.save(trendAnalysis);
        return "redirect:/admin/firmsData/trendAnalysis/list";
    }

    // Trend Analiz Güncelleme //
    @RequestMapping("admin/firmsData/trendAnalysis/update/{id}")
    public String TrendAnalysisUpdate(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        model.addAttribute("trendAnalys", repository.findOne(id));
        Iterable<Sectors> sectors = sectorRepository.findAll();
        model.addAttribute("sectors", sectors);
        return "admin/firmsData/trendAnalysis/update";
    }

    // Trend Analiz Sil //
    @RequestMapping("admin/firmsData/trendAnalysis/delete/{id}")
    public String TrendAnalysisDelete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        repository.delete(id);
        redirectAttributes.addFlashAttribute("trendAnalysisDeleteSuccess", true);
        return "redirect:/admin/firmsData/trendAnalysis/list";
    }

}
