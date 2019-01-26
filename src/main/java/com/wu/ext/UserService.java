package com.wu.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @EventListener(ApplicationEvent.class)
    public void listener(ApplicationEvent event){
        System.out.println("UserService.....event:"+event);
    }

}
