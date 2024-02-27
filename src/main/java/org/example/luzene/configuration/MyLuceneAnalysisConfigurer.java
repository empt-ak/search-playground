package org.example.luzene.configuration;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.path.PathHierarchyTokenizerFactory;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;

public class MyLuceneAnalysisConfigurer implements LuceneAnalysisConfigurer {

  public static final String INDEX_ANALYZER = "index_analyzer";

  @Override
  public void configure(LuceneAnalysisConfigurationContext luceneAnalysisConfigurationContext) {
    luceneAnalysisConfigurationContext.analyzer(INDEX_ANALYZER)
        .custom()
        .tokenizer(PathHierarchyTokenizerFactory.class)
        .tokenFilter(LowerCaseFilterFactory.class);
    // HTMLStripCharFilterFactory
    // ASCIIFoldingFilterFactory
  }

}
