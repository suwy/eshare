package com.yunde.website.allpay.web;

import com.jfinal.aop.Before;
import com.jfinal.base.TemplateController;
import com.jfinal.ext.kit.ControllerKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;

import java.util.regex.Pattern;

@Before(WebInterceptor.class)
public class BaseTempleteController<M extends Model<M>> extends TemplateController<M> {

    @Override
    public void showPage() {
        this.setAttr("_dataUrl", ControllerKit.controlerUrl(this));
        if (!StrKit.isBlank(this.getPara("id"))) {
            this.setAttr("_id", this.getPara("id"));
        }

        this.render(ControllerKit.showPage(this));
    }

    public void renderForMobileAndPC(String view) {
        String userAgent = getRequest().getHeader("user-agent");
        Boolean android = Pattern.matches(".*Android.*",userAgent);
        Boolean iphone = Pattern.matches(".*iPhone.*",userAgent);
        if (android || iphone) {
            super.render("../mobile/"+view);
            return ;
        }
        super.render(view);
    }
}
