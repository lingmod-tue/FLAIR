/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server.taskmanager;

/**
 * The result of a task
 * 
 * @author shadeMe
 */
interface AbstractTaskResult<R>
{
	public TaskType		getType();
	public R			getResult();
}
