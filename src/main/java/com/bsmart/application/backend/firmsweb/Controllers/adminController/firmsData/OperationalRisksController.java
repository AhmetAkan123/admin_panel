package com.bsmart.application.backend.firmsweb.Controllers.adminController.firmsData;

import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.OperationalRisks;
import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Sectors;
import com.bsmart.application.backend.firmsweb.Repository.OperationalRisksRepository;
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
public class OperationalRisksController {

    @Autowired
    UserService userService;

    @Autowired
    OperationalRisksRepository repository;

    @Autowired
    SectorRepository sectorRepository;

    // İşletme Riskleri Listeleme //
    @RequestMapping("admin/firmsData/operationalRisks/list")
    public String OperationalRisksList(Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        Iterable<OperationalRisks> operationalRisks = this.repository.findAll();
        model.addAttribute("operationalRisks", operationalRisks);
        Iterable<Sectors> sectors = sectorRepository.findAll();
        model.addAttribute("sectors", sectors);

        // Yeni İşletme Riski Ekleme //
        model.addAttribute("operationalRisk", new OperationalRisks());
        return "admin/firmsData/operationalRisks/list";
    }

    // Yeni İşletme Riski Ekleme - POST //
    @RequestMapping("admin/firmsData/operationalRisks")
    public String OperationalRisksRegister(@Valid OperationalRisks operationalRisks, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("operationalRiskFailure", true);
            return "redirect:/admin/firmsData/operationalRisks/list";
        } else if (operationalRisks.getId() == null) {
            redirectAttributes.addFlashAttribute("operationalRiskRegisterSuccess", true);
        } else {
            redirectAttributes.addFlashAttribute("operationalRiskUpdateSuccess", true);
        }

        repository.save(operationalRisks);
        return "redirect:/admin/firmsData/operationalRisks/list";
    }

    // İşletme Riski Güncelleme //
    @RequestMapping("admin/firmsData/operationalRisks/update/{id}")
    public String OperationalRisksUpdate(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        model.addAttribute("operationalRisk", repository.findOne(id));
        Iterable<Sectors> sectors = sectorRepository.findAll();
        model.addAttribute("sectors", sectors);
        return "admin/firmsData/operationalRisks/update";
    }

    // İşletme Riski Sil //
    @RequestMapping("admin/firmsData/operationalRisks/delete/{id}")
    public String OperationalRisksDelete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        repository.delete(id);
        redirectAttributes.addFlashAttribute("operationalRiskDeleteSuccess", true);
        return "redirect:/admin/firmsData/operationalRisks/list";
    }

}
