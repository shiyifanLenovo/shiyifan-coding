package com.shiyifan.producer;

import javafx.collections.transformation.TransformationList;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @compang 联想懂的通信
 * @Description: java类作用描述
 * @Author: shiyf
 * @CreateDate: 2020/4/18 0:40
 * @UpdateUser: shiyf
 * @UpdateDate: 2020/4/18 0:40
 * @UpdateRemark: 修改内容
 * @Version: cmp2.0
 */
public class OrderPayListener implements TransactionListener {


	@Override
	public LocalTransactionState executeLocalTransaction(Message message, Object o) {
		System.out.println(o);
		String topic = message.getTopic();
		System.out.println("topic="+topic);
		//执行业务逻辑代码
		try {
			//更新库存，更新订单
		}catch (Exception e){
			return  LocalTransactionState.ROLLBACK_MESSAGE;
		}
		return LocalTransactionState.COMMIT_MESSAGE;
	}

	@Override
	public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {

		return null;
	}
}
