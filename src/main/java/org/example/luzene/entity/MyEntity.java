package org.example.luzene.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.example.luzene.configuration.MyLuceneAnalysisConfigurer;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Entity
@Data
@Indexed
public class MyEntity {

  @Id
  private Long id;

  @FullTextField(
      analyzer = MyLuceneAnalysisConfigurer.INDEX_ANALYZER,
      searchAnalyzer = MyLuceneAnalysisConfigurer.INDEX_ANALYZER
  )
  private String path;
}
