package com.zhupeiting.bisheproject.dto;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class HotTagDto implements Comparable{
    private String name;
    private Integer priority;
    private Integer questionCount;
    private Integer viewCount;
    @Override
    public int compareTo(@NotNull Object o) {
        return this.getPriority() - ((HotTagDto) o).getPriority();
    }
}
