package run.kabuctl.vaulttokenizationdemoui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UiController {
    private final UiService uiService;

    public UiController(UiService uiService) {
        this.uiService = uiService;
    }

    @GetMapping(value = "/")
    public String home(Model model) throws Exception {
        model.addAttribute("eusers", this.uiService.getEncryptedUsers());
        model.addAttribute("tusers", this.uiService.getTransformedUsers());
        model.addAttribute("stusers", this.uiService.getSimpleTransformedUsers());
        model.addAttribute("stestusers", this.uiService.getSimplestTransformedUsers());
        return "ui/index";
    }

    @PostMapping(value = "/encrypt")
    public String encrypt(HttpServletRequest request) {
        String howto = "";

        if(request.getParameter("transit") != null) {
            howto = "transit";
        } else if (request.getParameter("transformation") != null) {
            howto = "transformation";
        } else if (request.getParameter("simple-transformation") != null) {
            howto = "simple-transformation";
        } else if (request.getParameter("simplest-transformation") != null) {
            howto = "simplest-transformation";
        }
        this.uiService.addOneEncryptedUser(
                request.getParameter("username"),
                request.getParameter("password"),
                request.getParameter("email"),
                request.getParameter("creditcard"),
                howto
        );
        return "redirect:/";
    }

    @PostMapping(value = "/decrypt")
    public String decrypt(HttpServletRequest request, Model model) {
        this.uiService.getOneDecryptedUser(request.getParameter("username"));
        String response = this.uiService.getOneDecryptedUser(request.getParameter("username"));
        new UiAppUtil().userSetup(response, model);
        return "ui/userinfo";
    }

    @PostMapping(value = "/decode")
    public String decode(HttpServletRequest request, Model model) {
        String response = this.uiService.getOneDecodedUser(request.getParameter("username"), request.getParameter("flag"));
        new UiAppUtil().userSetup(response, model);
        return "ui/userinfo";
    }
}

