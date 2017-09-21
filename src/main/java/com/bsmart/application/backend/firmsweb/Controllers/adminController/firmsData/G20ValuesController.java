package com.bsmart.application.backend.firmsweb.Controllers.adminController.firmsData;

import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.G20Values;
import com.bsmart.application.backend.firmsweb.Repository.G20ValuesRepository;
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
public class G20ValuesController {

    @Autowired
    private UserService userService;

    @Autowired
    private G20ValuesRepository repository;

    // Tüm G20 Değerlerini Listeleme //
    @RequestMapping("admin/firmsData/G20Values/list")
    public String G20ValuesList(Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        Iterable<G20Values> values = this.repository.findAll();
        model.addAttribute("values", values);

        // Yeni G20 Değeri Ekleme //
        model.addAttribute("G20Values", new G20Values());
        return "admin/firmsData/G20Values/list";
    }

    // Yeni G20 Değeri Ekleme - POST //
    @RequestMapping("admin/firmsData/G20Values")
    public String G20ValuesRegister(@Valid G20Values g20Values, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("G20ValueFailure", true);
            return "redirect:/admin/firmsData/G20Values/list";
        } else if (g20Values.getId() == null) {
            redirectAttributes.addFlashAttribute("G20ValueRegisterSuccess", true);
        } else {
            redirectAttributes.addFlashAttribute("G20ValueUpdateSuccess", true);
        }
        repository.save(g20Values);
        return "redirect:/admin/firmsData/G20Values/list";
    }

    // G20 Değeri Güncelleme //
    @RequestMapping("admin/firmsData/G20Values/update/{id}")
    public String G20ValuesUpdate(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        model.addAttribute("G20Value", repository.findOne(id));
        return "admin/firmsData/G20Values/update";
    }

    // G20 Değeri Sil //
    @RequestMapping("admin/firmsData/G20Values/delete/{id}")
    public String G20ValuesDelete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        repository.delete(id);
        redirectAttributes.addFlashAttribute("G20ValueDeleteSuccess", true);
        return "redirect:/admin/firmsData/G20Values/list";
    }

}
