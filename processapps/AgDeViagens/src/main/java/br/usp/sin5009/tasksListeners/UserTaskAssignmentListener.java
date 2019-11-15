package br.usp.sin5009.tasksListeners;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.context.Context;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
/**
 * 
 * https://blog.camunda.com/post/2013/10/how-to-send-email-when-usertask-is/
 */
public class UserTaskAssignmentListener implements TaskListener {

	  private static final String HOST = "mail.google.com";
	  private static final String USER = "wagnerdocri";
	  private static final String PWD = "GooMt23-910Email";
	
	  private final static Logger LOGGER = Logger.getLogger(UserTaskAssignmentListener.class.getName());
	
	@Override
	public void notify(DelegateTask delegateTask) {
		
		String assignee = delegateTask.getAssignee();
	    String taskId = delegateTask.getId();

	    if (assignee != null) {
	   
	      // Get User Profile from User Management
	      IdentityService identityService = Context.getProcessEngineConfiguration().getIdentityService();
	      User user = identityService.createUserQuery().userId(assignee).singleResult();

	      if (user != null) {
	     
	        // Get Email Address from User Profile
	        String recipient = user.getEmail();	     
	        if (recipient != null && !recipient.isEmpty()) {

	          Email email = new SimpleEmail();
	          email.setHostName(HOST);
	          email.setAuthentication(USER, PWD);

	          try {
	            email.setFrom("wagnerdocri@gmail.com");
	            email.setSubject("Task assigned: " + delegateTask.getName());
	            email.setMsg("Click para realizar a tarefa: http://localhost:8080/camunda/app/tasklist/default/#/task/" + taskId);

	            email.addTo(recipient);

	            email.send();
	            LOGGER.info("Email notificando usuario do Assingmento da tarefa foi enviado com sucesso!!! '" + assignee + "' com endereco '" + recipient + "'.");          

	          } catch (Exception e) {
	            LOGGER.log(Level.WARNING, ":( Nao foi possivel enviar o email", e);
	          }

	        } else {
	          LOGGER.warning("Email nao enviado para o usuario " + assignee + "' porque usuario nao informou endereco de email.");
	        }

	      } else {
	        LOGGER.warning("Email nao enviado para o usuario " + assignee + "', porque usuario nao faz parte do servico de identidade do camunda.");
	      }

	    }
	}

}
