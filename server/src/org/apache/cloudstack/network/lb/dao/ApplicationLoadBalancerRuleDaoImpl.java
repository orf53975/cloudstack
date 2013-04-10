// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package org.apache.cloudstack.network.lb.dao;

import java.util.List;

import javax.ejb.Local;

import org.apache.cloudstack.network.lb.ApplicationLoadBalancerRuleVO;
import org.springframework.stereotype.Component;

import com.cloud.network.rules.LoadBalancerContainer.Scheme;
import com.cloud.utils.db.GenericDaoBase;
import com.cloud.utils.db.SearchBuilder;
import com.cloud.utils.db.SearchCriteria;
import com.cloud.utils.net.Ip;

@Component
@Local(value = { ApplicationLoadBalancerRuleDao.class })
public class ApplicationLoadBalancerRuleDaoImpl extends GenericDaoBase<ApplicationLoadBalancerRuleVO, Long> implements ApplicationLoadBalancerRuleDao{
    protected final SearchBuilder<ApplicationLoadBalancerRuleVO> AllFieldsSearch;
    
    protected ApplicationLoadBalancerRuleDaoImpl() {
        AllFieldsSearch = createSearchBuilder();
        AllFieldsSearch.and("sourceIp", AllFieldsSearch.entity().getSourceIp(), SearchCriteria.Op.EQ);
        AllFieldsSearch.and("sourceIpNetworkId", AllFieldsSearch.entity().getSourceIpNetworkId(), SearchCriteria.Op.EQ);
        AllFieldsSearch.and("networkId", AllFieldsSearch.entity().getNetworkId(), SearchCriteria.Op.EQ);
        AllFieldsSearch.and("scheme", AllFieldsSearch.entity().getScheme(), SearchCriteria.Op.EQ);
        AllFieldsSearch.done();
    }

    @Override
    public List<ApplicationLoadBalancerRuleVO> listBySrcIpSrcNtwkIdAndScheme(Ip sourceIp, long sourceNetworkId, Scheme scheme) {
        SearchCriteria<ApplicationLoadBalancerRuleVO> sc = AllFieldsSearch.create();
        sc.setParameters("sourceIp", sourceIp);
        sc.setParameters("sourceIpNetworkId", sourceNetworkId);
        sc.setParameters("scheme", scheme);
        return listBy(sc);
    }

}
