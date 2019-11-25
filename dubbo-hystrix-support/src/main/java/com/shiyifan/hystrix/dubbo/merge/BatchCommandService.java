package com.shiyifan.hystrix.dubbo.merge;

import com.netflix.hystrix.HystrixCollapser;
import com.shiyifan.hystrix.dubbo.filter.HystrixCollapserRequest;
import com.shiyifan.hystrix.dubbo.filter.HystrixCollapserResponse;

import java.util.Collection;
import java.util.List;

public interface BatchCommandService {


	List<HystrixCollapserResponse>   batchCommand(Collection<HystrixCollapser.CollapsedRequest<HystrixCollapserResponse, HystrixCollapserRequest>> requests);
}
