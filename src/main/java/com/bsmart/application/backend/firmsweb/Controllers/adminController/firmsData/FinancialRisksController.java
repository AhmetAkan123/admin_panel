package com.bsmart.application.backend.firmsweb.Controllers.adminController.firmsData;


import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.FinancialRisks;
import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Sectors;
import com.bsmart.application.backend.firmsweb.Repository.FinancialRisksRepository;
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
public class FinancialRisksController {

    @Autowired
    private UserService userService;

    @Autowired
    private FinancialRisksRepository financialRisksRepository;

    @Autowired
    private SectorRepository sectorRepository;

    // Tüm Finansal Riskleri Listeleme //
    @RequestMapping("/admin/firmsData/financialRisks/list")
    public String financialRisksList(Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        Iterable<FinancialRisks> risks = this.financialRisksRepository.findAll();
        model.addAttribute("risks", risks);
        Iterable<Sectors> sectors = sectorRepository.findAll();
        model.addAttribute("sectors", sectors);

        // Yeni Finansal Risk Ekleme //
        model.addAttribute("financialRisk", new FinancialRisks());
        return "admin/firmsData/financialRisks/list";
    }

    // Yeni Finansal Risk Ekleme - POST //
    @RequestMapping("admin/firmsData/financialRisks")
    public String financialRisksRegister(@Valid FinancialRisks financialRisks, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("financialRiskFailure", true);
            return "redirect:/admin/firmsData/financialRisks/list";
        } else if (financialRisks.getId() == null) {
            redirectAttributes.addFlashAttribute("financialRiskRegisterSuccess", true);
        } else {
            redirectAttributes.addFlashAttribute("financialRiskUpdateSuccess", true);
        }

        financialRisksRepository.save(financialRisks);
        return "redirect:/admin/firmsData/financialRisks/list";
    }

    // Finansal Risk Güncelleme //
    @RequestMapping("/admin/firmsData/financialRisks/update/{id}")
    public String financialRiskUpdate(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        model.addAttribute("financialRisk", financialRisksRepository.findOne(id));
        Iterable<Sectors> sectors = sectorRepository.findAll();
        model.addAttribute("sectors", sectors);
        return "admin/firmsData/financialRisks/update";
    }

    // Finansal Risk Sil //
    @RequestMapping("/admin/firmsData/financialRisks/delete/{id}")
    public String financialRisksDelete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        financialRisksRepository.delete(id);
        redirectAttributes.addFlashAttribute("financialRiskDeleteSuccess", true);
        return "redirect:/admin/firmsData/financialRisks/list";
    }

}
