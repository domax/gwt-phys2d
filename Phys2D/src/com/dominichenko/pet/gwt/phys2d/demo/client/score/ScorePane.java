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
package com.dominichenko.pet.gwt.phys2d.demo.client.score;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.dominichenko.pet.gwt.phys2d.client.utils.Messenger;
import com.dominichenko.pet.gwt.phys2d.client.utils.Utils;
import com.dominichenko.pet.gwt.phys2d.demo.client.DemoPane;
import com.dominichenko.pet.gwt.phys2d.demo.client.DemoPhys2DApp;
import com.dominichenko.pet.gwt.phys2d.shared.ScoreItem;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;

public class ScorePane extends Composite {
	
	@UiField SimplePager pager;
	@UiField(provided=true) DataGrid<ScoreItem> table = new DataGrid<ScoreItem>();
	@UiField DockLayoutPanel dockLayoutPanel;
	@UiField TextBox scoreName;
	@UiField IntegerBox scoreValue;
	@UiField Button scoreBtn;
	@UiField Label labelValue;
	@UiField Label labelName;
	@UiField FormPanel formScore;
	@UiField Label labelValidation;
	@UiField RadioButton validationClient;
	@UiField RadioButton validationServer;

	private ScoreDataProvider scoreDataProvider;
	private TextColumn<ScoreItem> placeColumn;
	private TextColumn<ScoreItem> dateColumn;
	private TextColumn<ScoreItem> nameColumn;
	private TextColumn<ScoreItem> scoreColumn;
	private HandlerRegistration firtsStartHR;
	private ValidatorFactory validatorFactory;
	
	public static final DateTimeFormat dtf = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
	
	private static ScorePaneUiBinder uiBinder = GWT.create(ScorePaneUiBinder.class);

	interface ScorePaneUiBinder extends UiBinder<DockLayoutPanel, ScorePane> {}
	
	private class ScoreDataProvider extends AsyncDataProvider<ScoreItem> {
		@Override
		protected void onRangeChanged(HasData<ScoreItem> display) {
			final Range range = display.getVisibleRange();
			GWT.log("ScorePane$ScoreDataProvider.onRangeChanged(HasData): "
					+ "start=" + range.getStart() + "; length=" + range.getLength());
			DemoPhys2DApp.getApp().getCommunicator().getTopScore(range.getStart(), range.getLength(),
					new AsyncCallback<ScoreItem[]>() {

						@Override
						public void onFailure(Throwable caught) {
							GWT.log(caught.getMessage(), caught);
							Messenger.say("Score Requesting Failed", "Server returned following error:<br/>" + caught.getMessage());
						}

						@Override
						public void onSuccess(ScoreItem[] result) {
							if (result == null || result.length == 0)
								result = new ScoreItem[] {new ScoreItem("No data", null, null, null)};
							updateRowData(range.getStart(), Arrays.asList(result));
						}
					});
		}
	}

	public ScorePane() {
		validatorFactory = Validation.byDefaultProvider().configure().buildValidatorFactory();
		initWidget(uiBinder.createAndBindUi(this));
		
		placeColumn = new TextColumn<ScoreItem>() {
			@Override
			public String getValue(ScoreItem item) {
				return item.getPlace() != null ? item.getPlace().toString() : "";
			}
		};
		table.addColumn(placeColumn, "Place");
		
		dateColumn = new TextColumn<ScoreItem>() {
			@Override
			public String getValue(ScoreItem item) {
				return item.getDate() != null ? dtf.format(item.getDate()) : "";
			}
		};
		table.addColumn(dateColumn, "Date");
		
		nameColumn = new TextColumn<ScoreItem>() {
			@Override
			public String getValue(ScoreItem item) {
				return item.getName();
			}
		};
		table.addColumn(nameColumn, "Name");
		
		scoreColumn = new TextColumn<ScoreItem>() {
			@Override
			public String getValue(ScoreItem item) {
				return item.getScore() != null ? item.getScore().toString() : "";
			}
		};
		table.addColumn(scoreColumn, "Score");
		
		pager.setDisplay(table);
		scoreDataProvider = new ScoreDataProvider();
		scoreDataProvider.addDataDisplay(table);
	}
	
	/**
	 * This event handler was created to make sure that first time pane opening
	 * will bring some data to screen - there are no rendered records in screen
	 * sometimes, so I force to redraw table when user opens this pane first time.
	 * <p>So, this method does following:</p>
	 * <ol>
	 * <li>It is fired when pane is attached to parent - it's needed to make
	 *     sure that all parent's structures are initialized already.</li>
	 * <li>Creates {@link SelectionHandler} and registers it in {@link DemoPane}.
	 *     Registration ticket - {@link HandlerRegistration} - is kept in private field {@link #firtsStartHR}.</li>
	 * <li>When selection handler fires, it just redraws data grid and unregisters itself
	 *     by using kept ticket {@link #firtsStartHR}.</li>
	 * </ol>
	 * <p>Well, I understand that it looks like ugly hack, but I didn't find proper way to fix this bug
	 * using standard GWT functionality.</p>
	 * 
	 * @param event {@link AttachEvent} object instance that contains info about attaching/detaching pane. 
	 */
	@UiHandler("dockLayoutPanel")
	void onDockLayoutPanelAttachOrDetach(AttachEvent event) {
		if (event.isAttached()) {
			firtsStartHR = DemoPhys2DApp.getApp().getDemoPane().addSelectionHandler(
					new SelectionHandler<Composite>() {
						@Override
						public void onSelection(SelectionEvent<Composite> event) {
							GWT.log("ScorePane/SelectionHandler.onSelection(SelectionEvent): selectedItem="
									+ event.getSelectedItem().getClass().getName());
							if (event.getSelectedItem() == ScorePane.this) {
								table.redraw();
								firtsStartHR.removeHandler();
							}
						}
					});
		}
	}
	
	@UiHandler("labelName")
	void onLabelNameClick(ClickEvent event) {
		scoreName.setFocus(true);
	}
	
	@UiHandler("labelValue")
	void onLabelValueClick(ClickEvent event) {
		scoreValue.setFocus(true);
	}
	
	@UiHandler("scoreBtn")
	void onScoreBtnClick(ClickEvent event) {
		formScore.submit();
	}
	
	@UiHandler(value={"scoreName", "scoreValue"})
	void onScoreFieldKeyDown(KeyDownEvent event) {
		GWT.log("ScorePane.onScoreFieldKeyDown(KeyDownEvent): nativeKeyCode=" + event.getNativeKeyCode());
		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
			formScore.submit();
	}
		
	@UiHandler("labelValidation")
	void onLabelValidationClick(ClickEvent event) {
		if (validationClient.getValue())
			validationClient.setFocus(true);
		else if (validationServer.getValue())
			validationServer.setFocus(true);
	}
	
	@UiHandler("formScore")
	void onFormScoreSubmit(SubmitEvent event) {
		event.cancel();
		final ScoreItem scoreItem = new ScoreItem(
				Utils.isHollow(scoreName.getValue()) ? null : scoreName.getValue().trim(),
				new Date(),
				scoreValue.getValue(),
				null);
		if (validationClient.getValue()) {
			// This trick with timer is only to avoid failing validation with data in future
			new Timer() {
				@Override
				public void run() {
					Validator validator = validatorFactory.getValidator();
					Set<ConstraintViolation<ScoreItem>> violations = validator.validate(scoreItem);
					if (!violations.isEmpty()) {
						StringBuilder errors = new StringBuilder("Please fix following error(s):<ul>");
						for (ConstraintViolation<ScoreItem> violation : violations) {
							errors.append("<li>");
							errors.append(violation.getMessage());
							errors.append("</li>");
//							errors.append(violation.getPropertyPath().toString());
						}
						errors.append("</ul>");
						Messenger.say("Validation Failed", errors.toString());
					} else
						sendScore(scoreItem);
				}
			}.schedule(100);
		} else
			sendScore(scoreItem);
	}
	
	private void sendScore(ScoreItem scoreItem) {
		DemoPhys2DApp.getApp().getCommunicator().sendScoreItem(scoreItem, new AsyncCallback<ScoreItem> () {

			@Override
			public void onFailure(Throwable caught) {
				Messenger.say("Sending Score Failed", caught.getMessage().replaceAll("\n", "<br/>"));
			}

			@Override
			public void onSuccess(ScoreItem result) {
				scoreDataProvider.onRangeChanged(table);
			}
		});
	}
}
