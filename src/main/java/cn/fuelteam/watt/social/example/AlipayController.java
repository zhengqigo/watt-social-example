package cn.fuelteam.watt.social.example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fuelteam.watt.social.alipay.AlipaySocialService;
import org.fuelteam.watt.social.alipay.AlipaySocialUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/alipay")
public class AlipayController {

    @Autowired
    private AlipaySocialService alipaySocialService;

    @RequestMapping(value = "/authorize", method = RequestMethod.GET)
    public ModelAndView alipay(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("redirect:" + alipaySocialService.authorizeUri());
    }

    @RequestMapping("/callback")
    public ModelAndView getAuthCode(HttpServletRequest request, HttpServletResponse response) {
        AlipaySocialUser alipayUser = alipaySocialService.user(request, response);
        System.out.println(alipayUser.toString());
        return new ModelAndView("redirect:/login");
    }
}