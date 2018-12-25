package cn.fuelteam.watt.social.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class WechatSignatureController {

    private String token = "ehgtrykuyiluer1231dfvsdtq";

    @RequestMapping("")
    public String check(HttpServletRequest request, HttpServletResponse response) {
        boolean get = request.getMethod().toLowerCase().equals("get");
        if (!get) return null;
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if (signature != null && check(token, signature, timestamp, nonce)) return echostr;
        return null;
    }

    private static boolean check(String token, String signature, String timestamp, String nonce) {
        String[] array = new String[] { token, timestamp, nonce };
        // 字典排序token, timestamp, nonce
        Arrays.sort(array);

        String concated = array[0].concat(array[1]).concat(array[2]);

        String calculated = null;
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            calculated = byteToHex(sha1.digest(concated.getBytes()));
        } catch (NoSuchAlgorithmException nae) {
            return false;
        }
        // 与signature对比
        return calculated.equals(signature.toUpperCase());
    }

    private static String byteToHex(byte[] array) {
        String digest = "";
        for (int i = 0; i < array.length; i++) {
            digest += byteToHex(array[i]);
        }
        return digest;
    }

    private static String byteToHex(byte byt) {
        char[] hex = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] temp = new char[2];
        temp[0] = hex[(byt >>> 4) & 0X0F];
        temp[1] = hex[byt & 0X0F];
        return new String(temp);
    }
}