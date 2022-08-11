package com.example.samplebatch.batch.processor;

import com.example.samplebatch.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserProcessor implements ItemProcessor<UserInfo, UserInfo> {

  private static final Map<String, String> DEPARTMENT_NAMES =
      new HashMap<>();
  Logger logger = LoggerFactory.getLogger(UserProcessor.class);
  public UserProcessor(){
    DEPARTMENT_NAMES.put("001","Technology");
    DEPARTMENT_NAMES.put("002","Operations");
    DEPARTMENT_NAMES.put("003","Accounts");
  }

  @Override
  public UserInfo process(UserInfo user) throws Exception {
    String departmentCode = user.getDepartment();
    String department = DEPARTMENT_NAMES.get(departmentCode);
    logger.info("Converted from " + departmentCode + "to" + department);
    user.setDepartment(department);
    user.setTime(LocalDate.now());
    return user;
  }
}