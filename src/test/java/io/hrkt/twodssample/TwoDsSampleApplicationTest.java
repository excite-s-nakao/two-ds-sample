package io.hrkt.twodssample;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"test"})
public class TwoDsSampleApplicationTest {

  @Test
  public void test() {
    TwoDsSampleApplication.main(new String[0]);
  }

}
