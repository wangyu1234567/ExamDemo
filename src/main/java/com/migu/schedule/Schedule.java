package com.migu.schedule;


import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.CallableTask;
import com.migu.schedule.info.TaskInfo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
*类名和方法不能修改
 */
public class Schedule {

	/**
	 * 服务节点集合
	 */
	private Map<Integer,List<CallableTask>> serverNodes = new HashMap<Integer,List<CallableTask>>();
	
	/**
	 * 任务挂起队列
	 */
    public List<CallableTask>  runTasks = new ArrayList<CallableTask>();
    
    Comparator<CallableTask> comparator = new Comparator<CallableTask>(){
		public int compare(CallableTask o1, CallableTask o2) {

			return -(o1.getConsumption()-o2.getConsumption());
		}
	};
	
	Comparator<TaskInfo> comparator1 = new Comparator<TaskInfo>(){
		public int compare(TaskInfo t1, TaskInfo t2) {

			return t1.getTaskId() - t2.getTaskId();
		}
	};
	
    public int init() {
        // TODO 方法未实现
    	if(runTasks != null)
    	{
    		runTasks.clear();
    	}
    	
    	if(serverNodes != null)
    	{
    		serverNodes.clear();
    	}
    	
        return ReturnCodeKeys.E001;
    }


    public int registerNode(int nodeId) {
        // 节点id小于0不合法
    	if(nodeId <= 0)
    	{
    		return ReturnCodeKeys.E004;
    	}
    	if(serverNodes != null && serverNodes.size() > 0)
    	{
    		List<CallableTask> runningTask = serverNodes.get(nodeId);
    		//如果该节点已存在则返回5
    		if(runningTask != null && runningTask.size() > 0)
    		{
    			return ReturnCodeKeys.E005;
    		}
    	}
    	else
    	{
    		List<CallableTask> rnewTask = new ArrayList<CallableTask>();
    		CallableTask task = new CallableTask(nodeId);
    		rnewTask.add(task);
    		serverNodes.put(nodeId, rnewTask);
    	}
        return ReturnCodeKeys.E003;
    }

    public int unregisterNode(int nodeId) {
        if(serverNodes != null && serverNodes.size() > 0)
        {
    		if(nodeId < 0)
    		{
    			return ReturnCodeKeys.E004;
    		}
    		List<CallableTask> lists = serverNodes.get(nodeId);
    		if(lists != null && lists.size() > 0)
    		{
    			for(CallableTask callableTask : lists)
    			{
    				if(callableTask == null)
    				{
    					continue;
    				}
    				//仍在运行中的任务放入挂载队列
    				if(callableTask.getStatus() > 0)
    				{
    					runTasks.add(callableTask);
    					lists.remove(callableTask);
    				}
    			}
    		}
    		else
    		{
    			return ReturnCodeKeys.E007;
    		}
        	serverNodes.clear();
        }
        
        if(serverNodes.isEmpty())
        {
        	return ReturnCodeKeys.E006;
        }
        else{
        	return ReturnCodeKeys.E000;
        }
    }


    public int addTask(int taskId, int consumption) {
    	if(taskId <= 0)
    	{
    		return ReturnCodeKeys.E009;
    	}
    	if(runTasks != null && runTasks.size() > 0)
    	{
    		for(CallableTask ctask : runTasks)
    		{
    			if(ctask != null)
    			{
    				if(taskId == ctask.getTaskId())
    				{
    					return ReturnCodeKeys.E010;
    				}
    			}
    		}
    	}
    	CallableTask task = new CallableTask();
    	task.setTaskId(taskId);
    	task.setConsumption(consumption);
    	task.setStatus(0);
    	runTasks.add(task);
        return ReturnCodeKeys.E008;
    }


    public int deleteTask(int taskId) {
    	boolean flag = false;
    	if(taskId <= 0)
    	{
    		return ReturnCodeKeys.E009;
    	}
    	if(serverNodes != null && serverNodes.size() > 0)
        {
    		for(Integer i : serverNodes.keySet())
    		{
    			List<CallableTask> lists = serverNodes.get(i);
        		if(lists != null && lists.size() > 0)
        		{
        			for(CallableTask callableTask : lists)
        			{
        				if(callableTask == null)
        				{
        					continue;
        				}
        				//仍在运行中的任务放入挂载队列
        				if(callableTask.getTaskId() == taskId)
        				{
        					//删除该任务
        					lists.remove(callableTask);
        					flag = true;
        				}
        			}
        			serverNodes.put(i, lists);
        		}
    		}
        }
    	if(runTasks != null && runTasks.size() > 0)
    	{
    		for(CallableTask ctask : runTasks)
    		{
    			if(ctask != null)
    			{
    				if(taskId == ctask.getTaskId())
    				{
    					runTasks.remove(ctask);
    					flag = true;
    					break;
    				}
    			}
    		}
    	}
    	
    	if(flag)
    	{
    		return ReturnCodeKeys.E011;
    	}
    	else{
    		return ReturnCodeKeys.E012;
    	}
    }


    public int scheduleTask(int threshold) {
        if(threshold < 0)
        {
        	return ReturnCodeKeys.E002;
        }
        if(serverNodes != null && serverNodes.size() > 0)
        {
        	for (Map.Entry<Integer,List<CallableTask>> entry : serverNodes.entrySet()) 
            {
            	if(!isEmpty(runTasks))
                {
                	for(CallableTask atask : entry.getValue())
                	{
                		//写不下去了。。。
                	}
                }
              System.out.println("key= " + entry.getKey() + " and value= "
                    + entry.getValue());
            }
        }
        
        
        return ReturnCodeKeys.E013;
    }


    public int queryTaskStatus(List<TaskInfo> tasks) {
    	if(serverNodes != null && serverNodes.size() > 0)
        {
    		for(Integer i : serverNodes.keySet())
    		{
    			List<CallableTask> lists = serverNodes.get(i);
        		if(lists != null && lists.size() > 0)
        		{
        			for(CallableTask callableTask : lists)
        			{
        				if(callableTask == null)
        				{
        					continue;
        				}
        				if(callableTask.getTaskId() <= 0)
        				{
        					continue;
        				}
        				TaskInfo taskInfo = new TaskInfo();
        				taskInfo.setNodeId(i);
        				taskInfo.setTaskId(callableTask.getTaskId());
        				tasks.add(taskInfo);
        			}
        		}
    		}
        }
    	if(runTasks != null && runTasks.size() > 0)
    	{
    		for(CallableTask ctask : runTasks)
    		{
    			if(ctask != null)
    			{
    				if(ctask.getTaskId() <= 0)
    				{
    					continue;
    				}
    				TaskInfo taskInfo = new TaskInfo();
    				taskInfo.setNodeId(-1);
    				taskInfo.setTaskId(ctask.getTaskId());
    				tasks.add(taskInfo);
    			}
    		}
    	}
    	if(tasks != null && tasks.size() > 0)
    	{
    		Collections.sort(tasks, comparator1);
//    		System.out.println(tasks);
    		return ReturnCodeKeys.E015;
    	}
    	else{
    		return ReturnCodeKeys.E016;
    	}  
    }

    /**
     * 验证是否为null或为空集
     * 
     * @param c Collection<?>
     * @return boolean true 为null或为空集
     */
    public static boolean isEmpty(Collection<?> c)
    {
        return (c == null) || c.isEmpty();
    }
}
