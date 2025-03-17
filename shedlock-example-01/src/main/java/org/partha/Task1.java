package org.partha;

import lombok.extern.log4j.Log4j2;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Log4j2
@Component
public class Task1 {

    @Scheduled(cron="0 53 19 * * ?")
    @SchedulerLock(name = "TASK1", lockAtLeastFor = "PT1M", lockAtMostFor = "PT5M")
    public void run(){
        log.info("task1 started");

        try {
            log.info("performing task1");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("task1 completed");
    }
}
