package org.example.luzene;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.example.luzene.entity.MyEntity;
import org.example.luzene.service.SearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LuzeneApplicationIT {
  /*
   (1, '/jablko/banan/hruska'),
   (2, '/banan/jablko/hruska'),
   (3, '/jablko/hruska/banan'),
   (4, '/jablko/hruska/pomaranc');
   */

  @Autowired
  private SearchService searchService;

  @Test
  void distance_2() {
    final var result = searchService.search("jabko");

    assertThat(result).hasSize(3);
  }

  @Test
  void distance_3() {
    final var result = searchService.search("jako");

    assertThat(result).isEmpty();
  }

  @Test
  void jablkoPrefix() {
    final var result = searchService.search("/jablko");

    assertThat(toIds(result)).containsAll(List.of(1L, 3L, 4L));
  }

  @Test
  void jablkoInde() {
    final var result = searchService.search("/aa/jablko");

    assertThat(toIds(result)).isEmpty();
  }

  @Test
  void hruska() {
    final var result = searchService.search("/hruska");

    assertThat(result).isEmpty();
  }

  private Collection<Long> toIds(List<MyEntity> source) {
    if (source == null || source.isEmpty()) {
      return Collections.emptyList();
    }

    return source.stream()
        .map(MyEntity::getId)
        .collect(Collectors.toSet());
  }

}
