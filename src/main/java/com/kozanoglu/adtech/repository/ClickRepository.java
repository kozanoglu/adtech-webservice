package com.kozanoglu.adtech.repository;

import com.kozanoglu.adtech.entity.Click;
import org.springframework.data.repository.CrudRepository;

public interface ClickRepository  extends CrudRepository<Click, String> {
}
