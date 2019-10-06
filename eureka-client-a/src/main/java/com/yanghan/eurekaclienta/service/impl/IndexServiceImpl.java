package com.yanghan.eurekaclienta.service.impl;

import com.yanghan.eurekaclienta.clientb.IndexCommand;
import com.yanghan.eurekaclienta.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yanghan on 2019-10-05.
 */
@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private IndexCommand indexCommand;

    @Override
    public String clientb() {
        return indexCommand.execute();
    }
}
