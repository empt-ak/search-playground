package org.example.luzene.configuration;

import java.nio.file.FileSystems;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.luzene.configuration.props.LuzeneProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Log4j2
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(LuzeneProperties.class)
public class SearchConfiguration implements HibernatePropertiesCustomizer {

  private static final String TMP_FOLDER = System.getProperty("java.io.tmpdir");
  private static final String DELIMITER = FileSystems.getDefault().getSeparator();

  private final ApplicationContext applicationContext;

  @Override
  public void customize(Map<String, Object> hibernateProperties) {
    final var indexLocation = TMP_FOLDER + DELIMITER + applicationContext.getId();

    log.info("Index location set to {}", indexLocation);

    hibernateProperties.put("hibernate.search.backend.analysis.configurer", new MyLuceneAnalysisConfigurer());
    hibernateProperties.put("hibernate.search.backend.directory.root", indexLocation);
  }
}
