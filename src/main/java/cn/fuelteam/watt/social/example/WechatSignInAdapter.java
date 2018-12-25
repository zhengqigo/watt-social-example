package cn.fuelteam.watt.social.example;

import org.fuelteam.watt.social.wechat.api.User;
import org.fuelteam.watt.social.wechat.api.Wechat;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

@Component
public class WechatSignInAdapter implements SignInAdapter {

    @Override
    public String signIn(String openId, Connection<?> connection, NativeWebRequest request) {
        ConnectionKey key = connection.getKey();
        if ("wechat".equalsIgnoreCase(key.getProviderId()) || "wechatmp".equalsIgnoreCase(key.getProviderId())) {
            User user = ((Wechat) connection.getApi()).userOperations().getUserProfile(openId);
            System.out.println(user.toString());
        }
        return "/login.htm";
    }
}
