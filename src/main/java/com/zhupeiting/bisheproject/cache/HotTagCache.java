package com.zhupeiting.bisheproject.cache;

import com.zhupeiting.bisheproject.dto.HotTagDto;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
public class HotTagCache {
    private List<HotTagDto> hots = new ArrayList<>();
    public void updateTags(Map<String, HotTagDto> tags){
        int max = 3;
        PriorityQueue<HotTagDto> priorityQueue = new PriorityQueue<>(max);
        tags.forEach((name,dto)->{
            HotTagDto hotTagDto = new HotTagDto();
            hotTagDto.setName(name);
            hotTagDto.setQuestionCount(dto.getQuestionCount());
            hotTagDto.setViewCount(dto.getViewCount());
            hotTagDto.setPriority(dto.getPriority());
            if(priorityQueue.size()<max){
                priorityQueue.add(hotTagDto);
            }else {
                HotTagDto minHot = priorityQueue.peek();
                if(hotTagDto.compareTo(minHot)>0){
                    priorityQueue.poll();
                    priorityQueue.add(hotTagDto);
                }

            }
        });
        List<HotTagDto> sortedTags = new ArrayList<>();

        HotTagDto poll = priorityQueue.poll();
        while (poll != null) {
            sortedTags.add(0,poll);
            poll = priorityQueue.poll();
        }
        hots = sortedTags;
    }
}
