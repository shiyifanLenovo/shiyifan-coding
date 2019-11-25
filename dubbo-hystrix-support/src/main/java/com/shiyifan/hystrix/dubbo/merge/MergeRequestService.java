package com.shiyifan.hystrix.dubbo.merge;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.shiyifan.hystrix.dubbo.filter.HystrixCollapserRequest;
import com.shiyifan.hystrix.dubbo.filter.HystrixCollapserResponse;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * @compang 联想懂的通信
 * @Description:  请求合并服务类封装
 * @Author: shiyf
 * @CreateDate: 2019/11/25 10:05
 * @UpdateUser: shiyf
 * @UpdateDate: 2019/11/25 10:05
 * @UpdateRemark: 修改内容
 * @Version: cmp2.0
 */
public class MergeRequestService {
	/**
	 * 每个批次最大的请求数，超过该值，创建新的batch请求, default 50
	 */
	private int  maxRequestsInBatch;
	/**
	 * 等待时间窗口，超过该值，创建新的batch请求     default 100
	 */
	private int timerDelayInMilliseconds;


	public MergeRequestService(){

	}
	public MergeRequestService(int maxRequestsInBatch, int timerDelayInMilliseconds) {
		this.maxRequestsInBatch = maxRequestsInBatch;
		this.timerDelayInMilliseconds = timerDelayInMilliseconds;
	}

	public HystrixCollapserResponse mergeRequest(BatchCommandService batchCommandService,HystrixCollapserRequest request) throws  Exception{
		if(null==batchCommandService){
			throw  new NullPointerException("BatchCommandService  is Null ");
		}
		HystrixRequestContext context = HystrixRequestContext.initializeContext();
		CommandCollapserGetValueForKey commandCollapserGetValueForKey = new CommandCollapserGetValueForKey
				(batchCommandService,request,maxRequestsInBatch==0?50:maxRequestsInBatch,timerDelayInMilliseconds==0?100:timerDelayInMilliseconds);
		Future<HystrixCollapserResponse> queue = commandCollapserGetValueForKey.queue();

		HystrixCollapserRequest request2 = new HystrixCollapserRequest("102");
		HystrixCollapserRequest request3 = new HystrixCollapserRequest("100");
		CommandCollapserGetValueForKey commandCollapserGetValueForKey2 = new CommandCollapserGetValueForKey
				(batchCommandService,request2,maxRequestsInBatch==0?50:maxRequestsInBatch,timerDelayInMilliseconds==0?100:timerDelayInMilliseconds);
		Future<HystrixCollapserResponse> queue2 = commandCollapserGetValueForKey2.queue();


		CommandCollapserGetValueForKey commandCollapserGetValueForKey3 = new CommandCollapserGetValueForKey
				(batchCommandService,request3,maxRequestsInBatch==0?50:maxRequestsInBatch,timerDelayInMilliseconds==0?100:timerDelayInMilliseconds);
		Future<HystrixCollapserResponse> queue3 = commandCollapserGetValueForKey3.queue();
		HystrixCollapserResponse response3 = queue3.get();
		HystrixCollapserResponse response2 = queue2.get();
		HystrixCollapserResponse response = queue.get();
		context.shutdown();
		return response;
	}

}
