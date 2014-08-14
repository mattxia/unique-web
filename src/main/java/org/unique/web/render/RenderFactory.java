package org.unique.web.render;

import org.unique.web.core.Const;
import org.unique.web.render.impl.BeetlRender;
import org.unique.web.render.impl.ErrorRender;
import org.unique.web.render.impl.HtmlRender;
import org.unique.web.render.impl.JavascriptRender;
import org.unique.web.render.impl.JetRender;
import org.unique.web.render.impl.JsonRender;
import org.unique.web.render.impl.JspRender;
import org.unique.web.render.impl.Redirect301Render;
import org.unique.web.render.impl.TextRender;

/**
 * RenderFactory.
 */
public class RenderFactory {

    private RenderFactory() {
    }

    public static RenderFactory single() {
        return RenderFactoryHoder.instance;
    }

    private static class RenderFactoryHoder {

        private static final RenderFactory instance = new RenderFactory();
    }

    /**
     * 默认渲染器
     * 
     * @param view
     * @return
     */
    public Render getRender(String view) {
        return getDefaultRender(view);
    }

    public Render getJspRender(String view) {
        return new JspRender(view);
    }

    public Render getJsonRender() {
        return new JsonRender();
    }

    public Render getJsonRender(String key, Object value) {
        return new JsonRender(key, value);
    }

    public Render getJsonRender(String[] attrs) {
        return new JsonRender(attrs);
    }

    public Render getJsonRender(String jsonText) {
        return new JsonRender(jsonText);
    }

    public Render getJsonRender(Object object) {
        return new JsonRender(object);
    }

    public Render getTextRender(String text) {
        return new TextRender(text);
    }

    public Render getTextRender(String text, String contentType) {
        return new TextRender(text, contentType);
    }

    public Render getDefaultRender(String view) {
        if(view.endsWith(Const.URL_EXT)){
            view = view.replaceAll(Const.URL_EXT, "");
        }
        if (Const.RENDER_TYPE == RenderType.BEETL) {
            return new BeetlRender(view);
        } else if (Const.RENDER_TYPE == RenderType.JET) {
            return new JetRender(view);
        } else {
            return new JspRender(view);
        }
    }

    public Render getErrorRender(int errorCode, String view) {
        return new ErrorRender(errorCode, view);
    }

    public Render getErrorRender(int errorCode) {
        return new ErrorRender(errorCode, Const.getErrorView(errorCode));
    }

    public Render getRedirectRender(String url) {
        return new RedirectRender(url);
    }

    public Render getRedirectRender(String url, boolean withQueryString) {
        return new RedirectRender(url, withQueryString);
    }

    public Render getRedirect301Render(String url) {
        return new Redirect301Render(url);
    }

    public Render getRedirect301Render(String url, boolean withQueryString) {
        return new Redirect301Render(url, withQueryString);
    }

    public Render getJavascriptRender(String jsText) {
        return new JavascriptRender(jsText);
    }

    public Render getHtmlRender(String htmlText) {
        return new HtmlRender(htmlText);
    }

}
