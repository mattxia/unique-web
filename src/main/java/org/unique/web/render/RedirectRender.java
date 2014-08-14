package org.unique.web.render;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * RedirectRender with status: 302 Found.
 */
public class RedirectRender implements Render {

    private String url;

    private boolean withQueryString;

    public RedirectRender(String url) {
        this.url = url;
        this.withQueryString = false;
    }

    public RedirectRender(String url, boolean withQueryString) {
        this.url = url;
        this.withQueryString = withQueryString;
    }

    public void render(HttpServletRequest request, HttpServletResponse response, String viewPath) {
        if (request.getContextPath() != null && url.indexOf("://") == -1) url = request.getContextPath() + url;

        if (withQueryString) {
            String queryString = request.getQueryString();
            if (queryString != null) if (url.indexOf("?") == -1)
                url = url + "?" + queryString;
            else
                url = url + "&" + queryString;
        }

        try {
            response.sendRedirect(url); // always 302
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
