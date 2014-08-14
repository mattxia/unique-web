package org.unique.plugin.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

import org.unique.common.tools.StringUtils;

public class I18N {

    private Locale locale;

    private String i18nName = "i18n";

    public I18N(String messageName, Locale locale) {
        if (StringUtils.isNotBlank(messageName)) this.i18nName = messageName;
        this.locale = locale;
    }

    public String getMessage(String key) {
        if (key == null) {
            throw new I18NException("I18N message.key.required");
        }
        if (locale == null) {
            locale = Locale.getDefault();
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle(i18nName, locale);
        if (resourceBundle == null) throw new I18NException("I18n resource.bundle.required");
        try {
            String msg = resourceBundle.getString(key);
            if (msg == null) {
                throw new I18NException("can't load the message for the I18N key");
            }
            return msg;
        } catch (java.util.MissingResourceException e) {
            throw new I18NException("can't load the message for the I18N key", e);
        }
    }
}
