package com.boren.bicycle.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class RequestResult {
	
	private boolean resultOk;
	private String resultMessage;
	private JSONObject resultDataObject;
	private JSONArray  resultDataArray;
	
	public RequestResult(boolean resultOk, String resultMessage) {
		super();
		this.resultOk = resultOk;
		this.resultMessage = resultMessage;
	}
	public RequestResult(boolean resultOk, String resultMessage,
			JSONArray resultDataArray) {
		super();
		this.resultOk = resultOk;
		this.resultMessage = resultMessage;
		this.resultDataArray = resultDataArray;
	}
	public RequestResult(boolean resultOk, String resultMessage,
			JSONObject resultDataObject) {
		super();
		this.resultOk = resultOk;
		this.resultMessage = resultMessage;
		this.resultDataObject = resultDataObject;
	}
	@Override
	public String toString() {
		return "RequestResult [resultOk=" + resultOk + ", resultMessage="
				+ resultMessage + ", resultDataObject=" + resultDataObject
				+ ", resultDataArray=" + resultDataArray + "]";
	}
	public boolean isResultOk() {
		return resultOk;
	}
	public void setResultOk(boolean resultOk) {
		this.resultOk = resultOk;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public JSONObject getResultDataObject() {
		return resultDataObject;
	}
	public void setResultDataObject(JSONObject resultDataObject) {
		this.resultDataObject = resultDataObject;
	}
	public JSONArray getResultDataArray() {
		return resultDataArray;
	}
	public void setResultDataArray(JSONArray resultDataArray) {
		this.resultDataArray = resultDataArray;
	}
	
}
