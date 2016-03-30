package cmabreu.spectral.action;

import com.opensymphony.xwork2.ActionContext;

import cmabreu.spectral.entity.User;

public class BasicActionClass {
	protected User user;
	
	public User getUser() {
		return user;
	}
	
	public String getSagitariiUrl() {
		return (String)ActionContext.getContext().getSession().get("sagitariiUrl");
	}
	
	public BasicActionClass() {
		user = (User)ActionContext.getContext().getSession().get("loggedUser");
	}
	
	public void setMessageText(String messageText) {
		messageText = messageText.replaceAll("[\n\r]", "");
		ActionContext.getContext().getSession().put("messageText", messageText );
	}

	public String getMessageText() {
		String messageText = (String)ActionContext.getContext().getSession().get("messageText");
		setMessageText("");
		return messageText;
	}

}
