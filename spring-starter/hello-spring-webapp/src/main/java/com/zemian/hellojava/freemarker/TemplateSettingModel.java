package com.zemian.hellojava.freemarker;

import com.zemian.hellojava.data.domain.Setting;
import freemarker.template.TemplateModel;


/*
A silly template model that doesn't do anything. You can't even convert into string since Freemarker code is not
extendable this way. One would have to implements one of the pre-defined sub TemplateModel to be effective.
 */
public class TemplateSettingModel implements TemplateModel {
    public Setting getAsSetting() {
        Setting ret = new Setting("TEST", "foo", "bar");
        return ret;
    }
}
