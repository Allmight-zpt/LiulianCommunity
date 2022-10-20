package com.zhupeiting.bisheproject.cache;

import com.zhupeiting.bisheproject.dto.TagDto;
import org.h2.util.StringUtils;

import javax.swing.text.html.HTML;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDto> get(){
        List<TagDto> tagDtoList = new ArrayList<>();
        TagDto frontEnd = new TagDto();
        frontEnd.setCategoryName("前端");
        frontEnd.setTags(Arrays.asList("前端" ,"javascript" ,"typescript" ,"ecmascript-6" ,"css" ,"css3" ,"html" ,"html5" ,"node.js" ,"npm" ,"react.js" ,"vue.js" ,"angular" ,"chrome" ,"chrome-dev" ,"tools" ,"safari" ,"webkit" ,"edge" ,"bootstrap" ,"tailwind-css" ,"antd" ,"sass" ,"less" ,"postcss" ,"yarn" ,"postman" ,"fiddler"));
        tagDtoList.add(frontEnd);

        TagDto backEnd = new TagDto();
        backEnd.setCategoryName("后端");
        backEnd.setTags(Arrays.asList("后端" ,"go" ,"php" ,"lavarel" ,"swoole" ,"java" ,"spring" ,"springboot" ,"node.js" ,"express" ,"python" ,"flask" ,"django" ,"fastapi" ,"c" ,"c++" ,"c#" ,"ruby" ,"ruby-on-rails" ,"asp.net" ,"scala" ,"rust" ,"爬虫"));
        tagDtoList.add(backEnd);

        TagDto mobileTerminal = new TagDto();
        mobileTerminal.setCategoryName("移动端");
        mobileTerminal.setTags(Arrays.asList("android" ,"android-studio" ,"java" ,"kotlin" ,"ios" ,"swift" ,"objective-c" ,"xcode" ,"flutter" ,"react-native" ,"webapp" ,"小程序"));
        tagDtoList.add(mobileTerminal);

        TagDto database = new TagDto();
        database.setCategoryName("数据库");
        database.setTags(Arrays.asList("数据库" ,"mysql" ,"mariadb" ,"postgresql" ,"sqlite" ,"sql" ,"nosql" ,"redis" ,"mongodb" ,"json" ,"yaml" ,"xml" ,"elasticsear" ,"chmemcached"));
        tagDtoList.add(database);

        TagDto operationMaintenance = new TagDto();
        operationMaintenance.setCategoryName("运维");
        operationMaintenance.setTags(Arrays.asList("运维" ,"微服务" ,"服务器" ,"linux" ,"ubuntu" ,"debian" ,"nginx" ,"apache" ,"docker" ,"容器" ,"kubernetes" ,"devops" ,"serverless" ,"负载均衡" ,"ssh" ,"jenkins" ,"vagrant"));
        tagDtoList.add(operationMaintenance);

        TagDto AI = new TagDto();
        AI.setCategoryName("人工智能");
        AI.setTags(Arrays.asList("算法" ,"机器学习" ,"人工智能" ,"深度学习" ,"数据挖掘" ,"tensorflow" ,"pytorch" ,"神经网络" ,"图像识别" ,"人脸识别" ,"自然语言处理" ,"机器人" ,"自动驾驶"));
        tagDtoList.add(AI);

        TagDto tool = new TagDto();
        tool.setCategoryName("工具");
        tool.setTags(Arrays.asList("编辑器" ,"git" ,"github" ,"visual-studio-code" ,"visual-studio" ,"intellij-idea" ,"sublime-text" ,"webstorm" ,"pycharm" ,"goland" ,"phpstorm" ,"vim" ,"emacs"));
        tagDtoList.add(tool);

        TagDto other = new TagDto();
        other.setCategoryName("其他");
        other.setTags(Arrays.asList("程序员" ,"segmentfault" ,"restful" ,"graphql" ,"安全" ,"xss" ,"csrf" ,"rpc" ,"http" ,"https" ,"udp" ,"websocket" ,"比特币" ,"以太坊" ,"智能合约" ,"区块链" ,"leetcode"));
        tagDtoList.add(other);
        return tagDtoList;
    }

    public static String filterInvalid(String tags){
        String[] split = tags.split(",");
        List<TagDto> tagDtoList = get();
        List<String> tagList = tagDtoList.stream().flatMap(tag->tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t->!tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}