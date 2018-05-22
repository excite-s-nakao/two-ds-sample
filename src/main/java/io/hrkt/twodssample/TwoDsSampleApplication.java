package io.hrkt.twodssample;

import javax.sql.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.val;

@SpringBootApplication
@val
public class TwoDsSampleApplication {

  public static void main(String[] args) {
    val appCxt = SpringApplication.run(TwoDsSampleApplication.class, args);
    val dsMap = appCxt.getBeansOfType(DataSource.class);
    dsMap.entrySet().forEach(System.out::println);
  }
}
