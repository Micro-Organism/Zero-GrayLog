package com.zero.graylog.service.impl;

import com.zero.graylog.service.GrayLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GrayLogServiceImpl implements GrayLogService {
    @Override
    public void hello() {
        log.info("this is a test service,hello");
    }
}
