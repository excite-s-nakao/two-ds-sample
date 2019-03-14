package io.hrkt.twodssample;

import static org.assertj.core.api.Assertions.assertThat;
import javax.sql.DataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import io.hrkt.twodssample.infrastructure.ds1.DataSource1Config;
import io.hrkt.twodssample.infrastructure.ds2.DataSource2Config;
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
    assertThat(dsMap.get(DataSource1Config.DATA_SOURCE_1)).isNotNull();
    assertThat(dsMap.get(DataSource2Config.DATA_SOURCE_2)).isNotNull();
    assertThat(dsMap.get(DataSource1Config.DATA_SOURCE_1)).isNotEqualTo(dsMap.get(DataSource2Config.DATA_SOURCE_2));
  }
}
