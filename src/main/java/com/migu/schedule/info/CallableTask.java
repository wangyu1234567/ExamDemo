package com.migu.schedule.info;

import java.util.concurrent.Callable;

public class CallableTask implements Callable<String> {

	private Object lock = new Object();
	
	public String call() throws Exception {
		
		synchronized (lock) 
		{
	    	//模拟任务正在运行
		    Thread.sleep(1000);
		    if(status > 0)
		    {
		      System.out.println("已经任务调度..");
		      lock.notify();
		    }
		}
		return null;
	}
	
	public CallableTask(int nodeId)
	{
		this.nodeId = nodeId;
		
	}
	
	public CallableTask()
	{
		
	}
	
    private int nodeId;
	
	/**
	 * 任务id
	 */
	private int taskId;
	
	/**
	 * 资源消耗占用率
	 */
	private int consumption;
	
	/**
	 * 运行状态 0:未运行，1：正在运行
	 */
	private int status = 0;

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getConsumption() {
		return consumption;
	}

	public void setConsumption(int consumption) {
		this.consumption = consumption;
	}

	@Override
	public String toString() {
		return "ServerNode [nodeId=" + nodeId + ", taskId=" + taskId + ", status=" + status + "]";
	}
	
}
