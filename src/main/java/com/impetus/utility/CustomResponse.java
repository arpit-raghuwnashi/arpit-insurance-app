package com.impetus.utility;

public class CustomResponse {

	private int code;
	private String message;
	private Object data;
	
	public CustomResponse() {
		
	}

	public CustomResponse(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public CustomResponse(int code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "CustomResponse [code=" + code + ", message=" + message + ", data=" + data + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + code;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CustomResponse other = (CustomResponse) obj;
		if (code != other.code) {
			return false;
		}
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data)) {
			return false;
		}
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message)) {
			return false;
		}
		return true;
	}
	

}
