/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.taskmanager;

/**
 * The result of a task
 * @author shadeMe
 */
abstract class AbstractTaskResult
{
    private final TaskType		type;
    
    public AbstractTaskResult(TaskType type) {
	this.type = type;
    }
    
    public TaskType getType() {
	return type;
    }
}
