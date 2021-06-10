package com.flair.server.interop;

import com.flair.server.interop.messaging.ServerMessagingSwitchboard;
import com.flair.shared.exceptions.InvalidClientIdentificationTokenException;
import com.flair.shared.interop.ClientIdToken;
import com.flair.shared.interop.InteropService;
import com.flair.shared.interop.messaging.Message;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class InteropServiceImpl extends RemoteServiceServlet implements InteropService {
	private void validateClientId(ClientIdToken clientId) throws InvalidClientIdentificationTokenException {
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession(false);
		ClientSessionManager.get().validateClientId(clientId, session);
	}

	@Override
	public ClientIdToken SessionInitialize() {
		HttpSession session = getThreadLocalRequest().getSession(true);
		return ClientSessionManager.get().addSession(session);
	}

	@Override
	public void SessionTeardown(ClientIdToken clientId) throws InvalidClientIdentificationTokenException {
		validateClientId(clientId);
		ClientSessionManager.get().removeSession(clientId);
	}

	@Override
	public void MessagingSend(Message<? extends Message.Payload> message) throws InvalidClientIdentificationTokenException {
		validateClientId(message.getClientId());
		ServerMessagingSwitchboard.get().onPushFromClient(message);
	}

	@Override
	public ArrayList<Message<? extends Message.Payload>> MessagingReceive(ClientIdToken clientId) throws InvalidClientIdentificationTokenException {
		validateClientId(clientId);
		return ServerMessagingSwitchboard.get().onPullToClient(clientId);
	}
	
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
    	Thread currentThread = Thread.currentThread();
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
      	  @Override
      	  public void run() {
      		  try {
      			  currentThread.interrupt();
      			  currentThread.stop();
	        	  //currentThread.destroy();
      		  } catch(Exception e) {}
      	  }
      	};
      	
      	// Kill the thread after 5mins
      	// The methods are deprecated, but the RCP implementation doesn't care for interrupts, so we have to kill the thread the hard way
        timer.schedule(timerTask, (long)(5000*60));
        
        super.service(req, res);
        
        timerTask.cancel();
        timer = null;
    }
}