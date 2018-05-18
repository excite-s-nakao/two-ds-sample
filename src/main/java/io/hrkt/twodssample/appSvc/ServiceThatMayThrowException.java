package io.hrkt.twodssample.appSvc;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@val
public class ServiceThatMayThrowException {
    private AtomicLong counter = new AtomicLong();

    public String doSomeWork() {
        val times = counter.incrementAndGet();
        log.info("didWork:" + times + "times.");
        return "" + times;
    }
}
