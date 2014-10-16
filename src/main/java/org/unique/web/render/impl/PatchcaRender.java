package org.unique.web.render.impl;

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
import org.unique.web.render.Render;

/**
 * patchca生成多彩验证码
 * @author:rex
 * @date:2014年10月16日
 * @version:1.0
 */
public class PatchcaRender implements Render {
	
	private static Logger logger = Logger.getLogger(PatchcaRender.class);
	
	private static final String RANDOM_CHAR = "23456789abcdefghigkmnpqrstuvwxyzABCDEFGHIGKLMNPQRSTUVWXYZ";
	private static int MIN_LEN = 4;
	private static int MAX_LEN = 4;
	
	private ConfigurableCaptchaService cs = new ConfigurableCaptchaService();

    private Random random = new Random();
    
    public PatchcaRender() {
		init(MIN_LEN, MAX_LEN);
	}
    
	public PatchcaRender(int len) {
		init(len, len);
	}
	
	public PatchcaRender(int min, int max) {
		init(min, max);
	}
	
	private void init(int min, int max){
		MIN_LEN = min;
		MAX_LEN = max;
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
        wf.setCharacters(RANDOM_CHAR);
        wf.setMaxLength(MIN_LEN);
        wf.setMinLength(MAX_LEN);
        cs.setWordFactory(wf);
	}
	
	private String createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        
        String token = null;
		token = EncoderHelper.getChallangeAndWriteImage(cs, "png", response.getOutputStream());
		if(null != token){
			session.setAttribute(Const.SESSION_CAPTCH_TOKEN, token);
		}
		logger.debug("当前的SessionID=" + session.getId() + "，验证码=" + token);
        return token;
    }
	
	@Override
	public void render(HttpServletRequest request, HttpServletResponse response, String viewPath) {
		try {
			response.setContentType("image/png");
	        response.setHeader("Cache-Control", "no-cache, no-store");
	        response.setHeader("Pragma", "no-cache");
	        long time = System.currentTimeMillis();
	        response.setDateHeader("Last-Modified", time);
	        response.setDateHeader("Date", time);
	        response.setDateHeader("Expires", time);
			this.createCode(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
