package co.coinvestor.oauthserver.controller;

import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/custom/oauth")
public class CustomApprovalController {

    @GetMapping("approve")
    public String customApproval(Model model, HttpServletRequest request) {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) request.getSession().getAttribute("authorizationRequest");
        model.addAttribute("authorizationRequest", authorizationRequest);
        return "oauth_approval";
    }
}
