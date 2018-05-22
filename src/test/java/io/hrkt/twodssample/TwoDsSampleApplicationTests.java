package io.hrkt.twodssample;

import static org.assertj.core.api.Assertions.assertThat;
import javax.sql.DataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import lombok.val;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TwoDsSampleApplicationTests {

  @Autowired
  ApplicationContext appCxt;
  
  @Test
  public void contextLoads() {}

  @Test
  public void lookup_ds_success() {
    val dsMap = appCxt.getBeansOfType(DataSource.class);
    assertThat(dsMap.size()).isEqualTo(2);
    assertThat(dsMap.get("datasource1")).isNotNull();
    assertThat(dsMap.get("datasource2")).isNotNull();
  }
}
