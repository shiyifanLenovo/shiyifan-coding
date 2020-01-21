package com.pinyg.sellergoods.service.impl;

import com.netflix.hystrix.HystrixCollapser;
import com.shiyifan.hystrix.dubbo.filter.HystrixCollapserRequest;
import com.shiyifan.hystrix.dubbo.filter.HystrixCollapserResponse;
import com.shiyifan.hystrix.dubbo.merge.BatchCommandService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class ABatchCommandServiceImpl implements BatchCommandService {


	@Override
	public List<HystrixCollapserResponse> batchCommand(Collection<HystrixCollapser.CollapsedRequest<HystrixCollapserResponse, HystrixCollapserRequest>> requests) {

		List<HystrixCollapserResponse> responses = new ArrayList<>();
		requests.forEach((HystrixCollapser.CollapsedRequest<HystrixCollapserResponse, HystrixCollapserRequest> request)->{
			HystrixCollapserRequest argument = request.getArgument();
			String requestKey = argument.getRequestKey();
			HystrixCollapserResponse<String> response = new HystrixCollapserResponse<>();
			response.setResponseData("response "+requestKey);
			responses.add(response);
		});
		return responses;
	}
}
