package com.migu.schedule.info;

/**
 * 服务节点
 *
 */
public class ServerNode {

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
	 * 状态
	 */
	private int status;

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

	@Override
	public String toString() {
		return "ServerNode [nodeId=" + nodeId + ", taskId=" + taskId + ", status=" + status + "]";
	}
	
	
}
