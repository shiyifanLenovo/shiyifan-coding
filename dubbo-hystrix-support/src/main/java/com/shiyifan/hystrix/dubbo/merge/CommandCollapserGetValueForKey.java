package com.shiyifan.hystrix.dubbo.merge;

import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.shiyifan.hystrix.dubbo.filter.HystrixCollapserRequest;
import com.shiyifan.hystrix.dubbo.filter.HystrixCollapserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @compang 联想懂的通信
 * @Description: 请求合并
 *
 * @Author: shiyf
 * @CreateDate: 2019/11/22 14:29
 * @UpdateUser: shiyf
 * @UpdateDate: 2019/11/22 14:29
 * @UpdateRemark: 修改内容
 * @Version: cmp2.0
 */
@Component
public class CommandCollapserGetValueForKey extends HystrixCollapser<List<HystrixCollapserResponse>, HystrixCollapserResponse, HystrixCollapserRequest> {
	private static final Logger LOGGER=LoggerFactory.getLogger(CommandCollapserGetValueForKey.class);

	private static  final String AS_KEY="Collapser";

	private HystrixCollapserRequest key;

	private BatchCommandService batchCommandService;

	private HystrixRequestContext context;

	@PostConstruct
	public void init(){
		context = HystrixRequestContext.initializeContext();

	}



    public CommandCollapserGetValueForKey(BatchCommandService batchCommandService,HystrixCollapserRequest key,int maxRequestsInBatch,int timerDelayInMilliseconds){
	    super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey(AS_KEY))
			    .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter()
					    //maxRequestsInBatch          每个批次最大的请求数，超过该值，创建新的batch请求
					    .withMaxRequestsInBatch(maxRequestsInBatch)
					    //timerDelayInMilliseconds    等待时间窗口，超过该值，创建新的batch请求
					    .withTimerDelayInMilliseconds(timerDelayInMilliseconds)));
	    this.key=key;
	    this.batchCommandService=batchCommandService;

    }


	/**
	 * 返回请求参数
	 * @return
	 */
	@Override
	public HystrixCollapserRequest getRequestArgument() {
		return key;
	}

	/**
	 * 合并条件
	 * @param collection
	 * @return
	 */
	@Override
	protected HystrixCommand<List<HystrixCollapserResponse>> createCommand(Collection<CollapsedRequest<HystrixCollapserResponse, HystrixCollapserRequest>> collection) {
		return new BatchCommand(collection);
	}

	/**
	 * 返回各自的请求结果
	 * @param batchResponse
	 * @param requests
	 */
	@Override
	protected void mapResponseToRequests(List<HystrixCollapserResponse> batchResponse, Collection<CollapsedRequest<HystrixCollapserResponse, HystrixCollapserRequest>> requests) {
		int count = 0;
		for (CollapsedRequest<HystrixCollapserResponse, HystrixCollapserRequest> request : requests) {

			request.setResponse(batchResponse.get(count++));
		}
	}

	private   class BatchCommand extends HystrixCommand<List<HystrixCollapserResponse>> {
		private final Collection<CollapsedRequest<HystrixCollapserResponse, HystrixCollapserRequest>> requests;

		private BatchCommand(Collection<CollapsedRequest<HystrixCollapserResponse, HystrixCollapserRequest>> requests) {
			super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
					.andCommandKey(HystrixCommandKey.Factory.asKey("GetValueForKey")));
			this.requests = requests;
		}

		@Override
		protected List<HystrixCollapserResponse> run() {
			LOGGER.info("batchCommand run  "+requests.size());
			List<HystrixCollapserResponse> responses = batchCommandService.batchCommand(requests);
			return responses;
		}
	}


	public static void main(String[] args) {
        int maxRequestsInBatch=0;
        int timerDelayInMilliseconds=0;
		HystrixRequestContext context = HystrixRequestContext.initializeContext();
		HystrixCollapserRequest request1 = new HystrixCollapserRequest("1");
		HystrixCollapserRequest request2 = new HystrixCollapserRequest("2");
		HystrixCollapserRequest request3 = new HystrixCollapserRequest("3");
		CommandCollapserGetValueForKey commandCollapserGetValueForKey = new CommandCollapserGetValueForKey
				(null,request1,maxRequestsInBatch==0?50:maxRequestsInBatch,timerDelayInMilliseconds==0?100:timerDelayInMilliseconds);
		Future<HystrixCollapserResponse> queue = commandCollapserGetValueForKey.queue();
		CommandCollapserGetValueForKey commandCollapserGetValueForKey2 = new CommandCollapserGetValueForKey
				(null,request2,maxRequestsInBatch==0?50:maxRequestsInBatch,timerDelayInMilliseconds==0?100:timerDelayInMilliseconds);
		Future<HystrixCollapserResponse> queue2 = commandCollapserGetValueForKey2.queue();

		CommandCollapserGetValueForKey commandCollapserGetValueForKey3 = new CommandCollapserGetValueForKey
				(null,request3,maxRequestsInBatch==0?50:maxRequestsInBatch,timerDelayInMilliseconds==0?100:timerDelayInMilliseconds);
		Future<HystrixCollapserResponse> queue3 = commandCollapserGetValueForKey3.queue();

		HystrixCollapserResponse execute1 = null;

		HystrixCollapserResponse execute =null;

		HystrixCollapserResponse execute2 = null;
		try {

			execute = queue.get();
			execute1 = queue2.get();

			Thread.sleep(50);
			execute2 = queue3.get();

			System.out.println(execute.getResponseData() +","+execute1.getResponseData()+","+execute2.getResponseData());


		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}


		context.shutdown();
	}

}
