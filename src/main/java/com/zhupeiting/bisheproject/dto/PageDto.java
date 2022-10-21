package com.zhupeiting.bisheproject.dto;

import lombok.Data;
import sun.font.TrueTypeFont;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageDto<T> {
    private List<T> data;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer>pages = new ArrayList<>();
    private Integer totalPage;
    public void setPageDto(Integer totalPage,Integer page) {
        this.page = page;
        this.totalPage = totalPage;
        //向前向后各数不超过三页作为可以直接跳转的页面
        pages.add(page);
        for (int i = 1; i <=3; i++) {
            if(page-i>=1){
                pages.add(0,page-i);
            }
            if(page+i<=totalPage){
                pages.add(page+i);
            }
        }
        //是否显示跳转上一页的图标
        if(page == 1){
            showPrevious = false;
        }else{
            showPrevious = true;
        }
        //是否显示跳转下一页的图标
        if(page == totalPage){
            showNext = false;
        }else{
            showNext = true;
        }
        //是否显示跳转第一页的图标
        if(pages.contains(1)){
            showFirstPage = false;
        }else{
            showFirstPage = true;
        }
        //是否显示跳转最后一页的图标
        if(pages.contains(totalPage)){
            showEndPage = false;
        }else{
            showEndPage = true;
        }
    }
}
