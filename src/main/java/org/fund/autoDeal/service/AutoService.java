package org.fund.autoDeal.service;

import org.fund.common.Result;

public interface AutoService {
    Result doPerchase(Integer userId, String loginCS, String fundCode, String amount, String type, String dealPassword) throws Exception;

}
