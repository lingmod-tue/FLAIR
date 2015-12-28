/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRTaskManager;

/**
 * The result of a task
 * @author shadeMe
 */
abstract class AbstractTaskResult
{
    private final TaskType		type;
    
    public AbstractTaskResult(TaskType type)
    {
	this.type = type;
    }
    
    public TaskType getType() {
	return type;
    }
}
