package com.flair.client.utilities;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

/*
 * Utility class for GWT-related tasks
 */
public class GwtUtil
{
	private static int			SMALL_SCREEN_WIDTH = 450;
	
	public static boolean isScrolledIntoView(Widget widget, boolean noClip)
	{
	    if (widget != null)
	    {
	        int docViewTop = Window.getScrollTop();
	        int docViewBottom = docViewTop + Window.getClientHeight();
	        int elemTop = widget.getAbsoluteTop();
	        int elemBottom = elemTop + widget.getOffsetHeight();
	        
	        if (noClip)
	        	return (elemBottom <= docViewBottom) && (elemTop >= docViewTop);
	        else
	        	return ((elemTop >= docViewTop) && (elemTop <= docViewBottom) ||
	        			(elemBottom >= docViewTop) && (elemBottom <= docViewBottom));
	    }
	    
	    return false;
	}
	
	public static boolean isSmallScreen() {
		return Window.getClientWidth() < SMALL_SCREEN_WIDTH;
	}
	
	public static native boolean isTouch() /*-{
		return (('ontouchstart' in $wnd) || ($wnd.navigator.msMaxTouchPoints > 0));
	}-*/;
}
