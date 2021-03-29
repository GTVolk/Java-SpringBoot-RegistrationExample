package ru.devvault.skilltest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.devvault.skilltest.dto.CheckProfileRequestMessage;
import ru.devvault.skilltest.dto.MessageId;
import ru.devvault.skilltest.dto.RegistrationForm;
import ru.devvault.skilltest.entity.User;
import ru.devvault.skilltest.service.MessagingService;
import ru.devvault.skilltest.service.UserService;
import ru.devvault.skilltest.validator.UserValidator;

@Slf4j
@Controller
public final class RegistrationController {
    private final UserService userService;
    private final UserValidator userValidator;
    private final MessagingService messagingService;

    // Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        // Form target
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        log.debug("Target: " + target);

        if (target.getClass() == RegistrationForm.class) {
            dataBinder.setValidator(userValidator);
        }
    }

    @Autowired
    public RegistrationController(UserService userService, UserValidator userValidator, MessagingService messagingService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.messagingService = messagingService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        RegistrationForm registrationForm = new RegistrationForm();

        model.addAttribute("registrationForm", registrationForm);

        return "register";
    }

    @GetMapping("/register/success")
    public String registerSuccess() {
        return "success";
    }

    @PostMapping("/register")
    public String registerUserAccount(
        Model model,
        @ModelAttribute("registrationForm") @Validated RegistrationForm registrationForm,
        BindingResult result,
        final RedirectAttributes redirectAttributes
    ) {
        // Validate result
        if (result.hasErrors()) {
            return "register";
        }

        User newUser;
        try {
            newUser = userService.save(registrationForm);
            if (newUser == null) {
                throw new RuntimeException("Empty user data");
            }

            MessageId messageId = messagingService.send(new CheckProfileRequestMessage(newUser));
            if (messageId == null) {
                throw new RuntimeException("Message not sent.");
            }

            redirectAttributes.addFlashAttribute("newUser", newUser);

            return "redirect:/register/success";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());

            return "register";
        }


    }
}
