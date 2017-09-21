package com.bsmart.application.backend.firmsweb.Controllers.adminController;

import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Firms;
import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Role;
import com.bsmart.application.backend.firmsweb.Entity.FirmsBackEndDbEntities.Sectors;
import com.bsmart.application.backend.firmsweb.Entity.User;
import com.bsmart.application.backend.firmsweb.Repository.FirmsRepository;
import com.bsmart.application.backend.firmsweb.Repository.SectorRepository;
import com.bsmart.application.backend.firmsweb.Repository.UserRepository;
import com.bsmart.application.backend.firmsweb.Services.AdminService;
import com.bsmart.application.backend.firmsweb.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class AdminController {

    private Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private FirmsRepository firmsRepository;

    // Admin Ana Sayfa //
    @RequestMapping("/admin/home")
    public String adminHome(Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());

        // TEST //
        ArrayList<User> metalRole = userRepository.findAllByRole(Role.ROLE_METAL);
        ArrayList<User> bronzeRole = userRepository.findAllByRole(Role.ROLE_BRONZE);
        ArrayList<User> silverRole = userRepository.findAllByRole(Role.ROLE_SILVER);
        ArrayList<User> goldRole = userRepository.findAllByRole(Role.ROLE_GOLD);

        model.addAttribute("roleMetal", metalRole.size());
        model.addAttribute("roleBronze", bronzeRole.size());
        model.addAttribute("roleSilver", silverRole.size());
        model.addAttribute("roleGold", goldRole.size());

        // TEST 2 //


        return "admin/home";
    }

    // ---------- ÜYE METHODLARI --------- //

    // Tüm Üyeleri Göster //
    @RequestMapping("/admin/user/list")
    public String userList(ModelMap map) {
        map.addAttribute("currentUser", userService.getLoggedInUser());
        Iterable<User> users = this.userRepository.findAll();
        map.addAttribute("users", users);
        return "admin/user-list";
    }

    // Üye Profil Sayfası //
    @RequestMapping("admin/user/show/{id}")
    public String userShow(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        model.addAttribute("user", userRepository.findOneById(id));
        return "admin/user-show";
    }

    // Üye Kayıt Formu //
    @RequestMapping("/admin/user/new")
    public String register(Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        model.addAttribute("localDateTime", LocalDateTime.now());
        model.addAttribute("user", new User());
        return "admin/register";
    }

    // Üye Kayıt POST //
    @RequestMapping(value = "admin/user", method = RequestMethod.POST)
    public String registerPost(@Valid User user, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "redirect:/admin/user/new";
        }

        User registeredUser = adminService.register(user);
        if (registeredUser != null) {
            redirectAttributes.addFlashAttribute("userRegisterSuccess", true);
            return "redirect:/admin/user/list";
        } else {
            log.error("Üye zaten kayıtlı: " + user.getUserName());
            model.addAttribute("currentUser", userService.getLoggedInUser());
            result.rejectValue("email", "error.alreadyExists", "Üye adı veya mail adresi zaten kayıtlı");
            return "admin/register";
        }
    }

    // Üye Düzenleme //
    @RequestMapping("/admin/user/update/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        User findingUser = userRepository.findOne(id);
        model.addAttribute("user", findingUser);
        return "/admin/edit";
    }

    // Üye Düzenleme Post //
    @RequestMapping(value = "admin/user/update", method = RequestMethod.POST)
    public String updatePost(User user, RedirectAttributes redirectAttributes) {
        adminService.update(user);
        redirectAttributes.addFlashAttribute("userUpdateSuccess", true);
        return "redirect:/admin/user/show/" + user.getId();
    }

    // Üyeyi Sil //
    @RequestMapping("/admin/user/delete/{id}")
    public String deleteUser(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        userService.delete(id);
        redirectAttributes.addFlashAttribute("userDeleteSuccess", true);
        return "redirect:/admin/user/list";
    }

    // --------- FİRMA METHODLARI --------- //

    // Yeni Firma Ekle //
    @RequestMapping("admin/user/{id}/firm/new")
    public String newFirm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        model.addAttribute("user", userRepository.findOne(id));
        Iterable<Sectors> sectors = sectorRepository.findAll();
        model.addAttribute("sectors", sectors);
        model.addAttribute("firm", new Firms());
        return "admin/new-firm";
    }

    // Yeni Firma Ekle - POST //
    @RequestMapping(value = "admin/user/{id}/firm", method = RequestMethod.POST)
    public String newFirmPost(@PathVariable("id") Integer id, Firms firms, RedirectAttributes redirectAttributes) {
        User user = userRepository.findOne(id);
        firms.setUser(user);
        firms.setId(null);
        firmsRepository.save(firms);
        redirectAttributes.addFlashAttribute("firmRegisterSuccess", true);
        return "redirect:/admin/user/show/" + id;
    }

    // Firma Düzenleme Sayfası //
    @RequestMapping("admin/user/update/{user_id}/firm/{firm_id}")
    public String updateFirm(@PathVariable("firm_id") Integer firm_id, @PathVariable("user_id") Integer user_id, Model model) {
        model.addAttribute("currentUser", userService.getLoggedInUser());
        User selectedUser = userRepository.findOne(user_id);
        model.addAttribute("selectedUser", selectedUser);
        Firms selectedFirm = firmsRepository.findByIdAndUser(firm_id, selectedUser);
        model.addAttribute("firm", selectedFirm);
        Iterable<Sectors> sectors = sectorRepository.findAll();
        model.addAttribute("sectors", sectors);
        return "admin/edit-firm";
    }

    // Firma Düzenleme - POST //
    @RequestMapping(value = "admin/user/updateFirm/{user_id}", method = RequestMethod.POST)
    public String updateFirmPost(@PathVariable Integer user_id, @Valid Firms firm, RedirectAttributes redirectAttributes) {
        this.adminService.updateFirm(firm, user_id);
        redirectAttributes.addFlashAttribute("firmUpdateSuccess", true);
        return "redirect:/admin/user/show/" + user_id;
    }

    // Firma Sil //
    @RequestMapping("admin/user/delete/{user_id}/firm/{firm_id}")
    public String deleteFirm(@PathVariable("firm_id") Integer firm_id, @PathVariable("user_id") Integer user_id, Model model, RedirectAttributes redirectAttributes) {
        firmsRepository.delete(firm_id);
        redirectAttributes.addFlashAttribute("firmDeleteSuccess", true);
        return "redirect:/admin/user/show/" + user_id;
    }

}
