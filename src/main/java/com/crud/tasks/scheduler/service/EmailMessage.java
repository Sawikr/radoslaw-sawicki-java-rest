package com.crud.tasks.scheduler.service;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailMessage {

    public String taskCounter(long size) {
        if (size > 1)
            return "tasks";
        return "task";
    }
}
