package com.zhupeiting.bisheproject.schedule;

import com.zhupeiting.bisheproject.cache.HotTagCache;
import com.zhupeiting.bisheproject.dto.HotTagDto;
import com.zhupeiting.bisheproject.mapper.QuestionMapper;
import com.zhupeiting.bisheproject.model.Question;
import com.zhupeiting.bisheproject.model.QuestionExample;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class HotTagTasks {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 10000)
    public void hotTagSchedule(){
        int offset = 0;
        int limit =5;
        log.info("hotTagSchedule start {}",new Date());
        List<Question> list = new ArrayList<>();
        Map<String, HotTagDto> priorities = new HashMap<>();
        while(offset == 0 || list.size() == limit){
            list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,limit));
            for(Question question : list){
                String[] tags = question.getTag().split(",");
                for (String tag : tags) {
                    HotTagDto old = priorities.get(tag);
                    HotTagDto hotTagDto = new HotTagDto();
                    if(old !=null){
                        Integer priority = old.getPriority();
                        Integer viewCount = old.getViewCount();
                        hotTagDto.setViewCount(viewCount + question.getViewCount());
                        hotTagDto.setQuestionCount(old.getQuestionCount() + 1);
                        hotTagDto.setPriority(priority + 10 + question.getCommentCount() * 3 + question.getViewCount());
                        priorities.put(tag,hotTagDto);
                    }else{
                        hotTagDto.setPriority(5 + question.getCommentCount() * 3 + question.getViewCount());
                        hotTagDto.setQuestionCount(1);
                        hotTagDto.setViewCount(question.getViewCount());
                        priorities.put(tag,hotTagDto);
                    }

                }
            }
            offset += limit;
        }
        hotTagCache.updateTags(priorities);
        log.info("hotTagSchedule end {}",new Date());
    }
}
