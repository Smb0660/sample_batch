package com.example.samplebatch.batch.writer;

import com.example.samplebatch.model.UserInfo;
import com.example.samplebatch.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserDBWriter implements ItemWriter<UserInfo> {
  private final UserRepository userRepository;
  Logger logger = LoggerFactory.getLogger(UserDBWriter.class);

  public UserDBWriter(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void write(List<? extends UserInfo> users) throws Exception {
    logger.info("Attempt to save date for user: " + users);
    userRepository.saveAll(users);
  }
}