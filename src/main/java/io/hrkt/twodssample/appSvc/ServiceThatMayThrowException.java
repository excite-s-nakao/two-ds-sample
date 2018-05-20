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
  private int throwExceptionAt = -1;
  private boolean canThrowException = false;

  public void setThrowExceptionAt(int throwExceptionAt) {
    this.throwExceptionAt = throwExceptionAt;
  }

  public void resetCounter() {
    counter.set(0);
  }

  public void enableThrowingExcpetion() {
    this.canThrowException = true;
  }

  public void disableThrowingExcpetion() {
    this.canThrowException = false;
  }

  public void doSomeWork() {
    val times = counter.incrementAndGet();
    log.info("didWork:" + times + "times.");
    if (canThrowException && times == throwExceptionAt) {
      throw new RuntimeException("BOOMB!");
    }
  }
}
