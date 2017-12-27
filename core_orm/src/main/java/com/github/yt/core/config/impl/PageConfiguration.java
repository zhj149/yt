package com.github.yt.core.config.impl;


import com.github.yt.core.config.PageConvert;

public class PageConfiguration {

    private static PageConvert pageConvert;

    public static PageConvert create() {
        if (pageConvert != null) {
            return pageConvert;
        }
        pageConvert = new CommonPageService();
        return pageConvert;
    }

}
