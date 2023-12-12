package com.quickex.worker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Timed task
 */
@Slf4j
@Component
@EnableScheduling
public class TimedTask {

    /**
     * test
     */
//    @Scheduled(fixedRate = 3000)
//    public void testJob() {
//         log.info("==== test job ========");
//    }
}
