package org.unique.plugin.patchca;

import java.awt.Color;
import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.patchca.color.ColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.filter.predefined.DiffuseRippleFilterFactory;
import org.patchca.filter.predefined.DoubleRippleFilterFactory;
import org.patchca.filter.predefined.MarbleRippleFilterFactory;
import org.patchca.filter.predefined.WobbleRippleFilterFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;
import org.unique.web.core.Const;

/**
 * patchca生成多彩验证码
 * 
 * @author rex
 */
public class PatchcaPlugin {

    private static Logger logger = Logger.getLogger(PatchcaPlugin.class);

    private static ConfigurableCaptchaService cs = new ConfigurableCaptchaService();

    private static Random random = new Random();

    static {
        cs.setColorFactory(new ColorFactory() {

            @Override
            public Color getColor(int x) {
                int[] c = new int[3];
                int i = random.nextInt(c.length);
                for (int fi = 0; fi < c.length; fi++) {
                    if (fi == i) {
                        c[fi] = random.nextInt(71);
                    } else {
                        c[fi] = random.nextInt(256);
                    }
                }
                return new Color(c[0], c[1], c[2]);
            }
        });
        RandomWordFactory wf = new RandomWordFactory();
        wf.setCharacters("23456789abcdefghigkmnpqrstuvwxyzABCDEFGHIGKLMNPQRSTUVWXYZ");
        wf.setMaxLength(4);
        wf.setMinLength(4);
        cs.setWordFactory(wf);
    }

    public static void crimg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        switch (random.nextInt(5)) {
        case 0:
            cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
            break;
        case 1:
            cs.setFilterFactory(new MarbleRippleFilterFactory());
            break;
        case 2:
            cs.setFilterFactory(new DoubleRippleFilterFactory());
            break;
        case 3:
            cs.setFilterFactory(new WobbleRippleFilterFactory());
            break;
        case 4:
            cs.setFilterFactory(new DiffuseRippleFilterFactory());
            break;
        }
        HttpSession session = request.getSession(false);
        if (session == null) {
            session = request.getSession();
        }
        setResponseHeaders(response);
        String token = EncoderHelper.getChallangeAndWriteImage(cs, "png", response.getOutputStream());
        session.setAttribute(Const.SESSION_CAPTCH_TOKEN, token);
        logger.debug("当前的SessionID=" + session.getId() + "，验证码=" + token);
    }

    protected static void setResponseHeaders(HttpServletResponse response) {
        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        long time = System.currentTimeMillis();
        response.setDateHeader("Last-Modified", time);
        response.setDateHeader("Date", time);
        response.setDateHeader("Expires", time);
    }
}