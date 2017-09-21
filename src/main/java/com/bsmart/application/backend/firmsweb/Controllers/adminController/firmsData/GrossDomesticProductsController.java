package com.bsmart.application.backend.firmsweb.Controllers.adminController.firmsData;

import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.GrossDomesticProducts;
import com.bsmart.application.backend.firmsweb.Repository.GrossDomesticProductsRepository;
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
public class GrossDomesticProductsController {

    @Autowired
    private UserService userService;

    @Autowired
    private GrossDomesticProductsRepository repository;

    // Tüm Gross Domestic Products Listesi //
    @RequestMapping("admin/firmsData/grossDomestic/list")
    public String GrossDomesticList(Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        Iterable<GrossDomesticProducts> products = this.repository.findAll();
        model.addAttribute("products", products);

        // Yeni Gross Domestic Product Ekleme //
        model.addAttribute("grossDomesticProduct", new GrossDomesticProducts());
        return "admin/firmsData/grossDomestic/list";
    }

    // Yeni Gross Domestic Product Ekleme - POST //
    @RequestMapping("admin/firmsData/grossDomestic")
    public String GrossDomesticRegister(@Valid GrossDomesticProducts grossDomesticProducts, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("grossDomesticFailure", true);
            return "redirect:/admin/firmsData/grossDomestic/list";
        } else if (grossDomesticProducts.getId() == null) {
            redirectAttributes.addFlashAttribute("grossDomesticRegisterSuccess", true);
        } else {
            redirectAttributes.addFlashAttribute("grossDomesticUpdateSuccess", true);
        }

        repository.save(grossDomesticProducts);
        return "redirect:/admin/firmsData/grossDomestic/list";
    }

    // Gross Domestic Product Güncelleme //
    @RequestMapping("admin/firmsData/grossDomestic/update/{id}")
    public String GrossDomesticUpdate(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        model.addAttribute("grossDomesticProduct", repository.findOne(id));
        return "admin/firmsData/grossDomestic/update";
    }

    // Gross Domestic Product Sil //
    @RequestMapping("admin/firmsData/grossDomestic/delete/{id}")
    public String GrossDomesticDelete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        repository.delete(id);
        redirectAttributes.addFlashAttribute("grossDomesticDeleteSuccess", true);
        return "redirect:/admin/firmsData/grossDomestic/list";
    }
}
