/*
 * Copyright 2011-2012 Maxim Dominichenko
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.dominichenko.pet.gwt.phys2d.client.utils;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Class that implements message box.<br/>
 * Static methods use private singleton instance of this class,
 * so any new invocation of static functions brings <em>the same</em>
 * window. It means that 2 consecutive calls of, e.g.
 * <pre><code>
 * Messenger.say("Title 1", "Message 1"); // You won't see that.
 * Messenger.say("Title 2", "Message 2"); // You will see this instead.
 * </code></pre>
 * will show only second (last one).<br/>
 * So, if you want to have 2 or more {@link Messenger} windows in screen at once,
 * you have to instantiate them instead:
 * <pre><code>
 * new Messenger().sayThat("Title 1", "Message 1");
 * new Messenger().sayThat("Title 2", "Message 2");
 * </code></pre>
 * 
 * @author <a href="mailto:max@dominichenko.com">Maxim Dominichenko</a>
 */
public class Messenger {
	
	private DialogBox dialogBox;
	private HTML content;
	private Button closeButton;
	private HandlerRegistration closeHandlerRegistration;
	private static Messenger instance = new Messenger();
	
	/**
	 * Default constructor of message box.
	 */
	public Messenger() {
		dialogBox = new DialogBox();
		dialogBox.setAnimationEnabled(true);
		closeButton = new Button("Close");
		closeButton.getElement().setId("closeButton");
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		content = new HTML();
		dialogVPanel.add(content);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
		});
	}
	
	/**
	 * Show up information message box, that have a title, HTML-based message
	 * and one "Close" button.
	 * Box is centered automatically.
	 * 
	 * @param title A caption of message box: "Info", "Error", whatever.
	 * @param content Some custom text. HTML is supported.
	 * @param closeClickHandler optional handler for "Close" button.
	 *        (Handler for closing window is defined already - no need to close box manually).
	 */
	public void sayThat(String title, String content, ClickHandler closeClickHandler) {
		dialogBox.setText(title);
		this.content.setHTML(content);
		if (closeHandlerRegistration != null) {
			closeHandlerRegistration.removeHandler();
			closeHandlerRegistration = null;
		}
		if (closeClickHandler != null)
			closeHandlerRegistration = closeButton.addClickHandler(closeClickHandler);
		dialogBox.center();
	}

	/**
	 * Show up information message box.<br/>
	 * The same as {@link #sayThat(String, String, ClickHandler)} 
	 * but with {@code null} for 3rd (closeClickHandler) parameter.
	 * Box is centered automatically.
	 * 
	 * @param title A caption of message box: "Info", "Error", whatever.
	 * @param content Some custom text. HTML is supported.
	 * @see #sayThat(String, String, ClickHandler)
	 */
	public void sayThat(String title, String content) {
		sayThat(title, content, null);
	}

	/**
	 * The static implementation of showing up information message box, 
	 * that have a title, HTML-based message and one "Close" button.<br/>
	 * Uses {@link #sayThat(String, String, ClickHandler)} method of inner
	 * singleton instance.
	 * Box is centered automatically.
	 * 
	 * @param title A caption of message box: "Info", "Error", whatever.
	 * @param content Some custom text. HTML is supported.
	 * @param closeClickHandler optional handler for "Close" button.
	 *        (Handler for closing window is defined already - no need to close box manually).
	 * @see #sayThat(String, String, ClickHandler)
	 */
	public static void say(String title, String content, ClickHandler closeClickHandler) {
		instance.sayThat(title, content, closeClickHandler);
	}
	
	/**
	 * The static implementation of showing up information message box.<br/>
	 * Uses {@link #sayThat(String, String)} method of inner singleton instance.
	 * Box is centered automatically.
	 * 
	 * @param title A caption of message box: "Info", "Error", whatever.
	 * @param content Some custom text. HTML is supported.
	 * @see #sayThat(String, String)
	 */
	public static void say(String title, String content) {
		instance.sayThat(title, content);
	}
}
