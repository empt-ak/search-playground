package org.example.luzene.service;

import java.util.List;
import org.example.luzene.entity.MyEntity;

public interface SearchService {

  List<MyEntity> search(String input);

}
