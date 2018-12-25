package cn.fuelteam.watt.social.example;

import org.fuelteam.watt.social.alipay.autoconfigurer.AlipayAutoConfiguration;
import org.fuelteam.watt.social.wechat.autoconfigurer.WechatAutoConfiguration;
import org.fuelteam.watt.social.wechat.autoconfigurer.WechatMpAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;

@SpringBootApplication(scanBasePackages = { "org.fuelteam", "cn.fuelteam" })
@EnableSocial
@Import({ WechatAutoConfiguration.class, WechatMpAutoConfiguration.class, AlipayAutoConfiguration.class })
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ProviderSignInController wechat(ConnectionFactoryLocator connectionFactoryLocator,
            UsersConnectionRepository usersConnectionRepository, WechatSignInAdapter wechatSignInAdapter) {
        ((InMemoryUsersConnectionRepository) usersConnectionRepository)
                .setConnectionSignUp((Connection<?> connection) -> connection.getKey().getProviderUserId());
        return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, wechatSignInAdapter);
    }
}
