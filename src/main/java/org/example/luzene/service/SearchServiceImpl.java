package org.example.luzene.service;

import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.luzene.configuration.props.LuzeneProperties;
import org.example.luzene.entity.MyEntity;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Log4j2
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService, InitializingBean {

  private final EntityManager entityManager;
  private final TransactionTemplate transactionTemplate;
  private final LuzeneProperties luzeneProperties;

  @Override
  public void afterPropertiesSet() throws Exception {
    transactionTemplate.executeWithoutResult(ts -> {
      try {
        Search.session(entityManager)
            .massIndexer(MyEntity.class)
            .startAndWait();
      } catch (InterruptedException ex) {
        Thread.currentThread().interrupt();

        throw new BeanInitializationException("Failed to init indexer", ex);
      }
    });
  }


  @Override
  @Transactional(readOnly = true)
  public List<MyEntity> search(String input) {
    final var search = Search.session(entityManager);
    final var scope = search.scope(MyEntity.class);

    final var query = search.search(scope)
        .where(f -> f.match()
            .field("path")
            .matching(input)
            .fuzzy(luzeneProperties.mismatchDistance()));

    log.info("Lucene query to be executed >>{}<<", query.toQuery().queryString());

    return query.fetchAllHits();
  }
}
