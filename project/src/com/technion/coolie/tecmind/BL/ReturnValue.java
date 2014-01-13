package com.technion.coolie.tecmind.BL;

public enum ReturnValue {
		 SUCCESS_TO_WRITE("SUCCESS_TO_WRITE"), SUCCESS_TO_READ("SUCCESS_TO_READ"), FAIL_TO_WRITE(
		      "FAIL_TO_WRITE"), FAIL_TO_READ("FAIL_TO_READ"), SUCCESS_FROM_SERVER(
		      "SUCCESS_FROM_SERVER"), SUCCESS_TO_SERVER("SUCCESS_TO_SERVER"), FAIL_FROM_SERVER(
				      "FAIL_FROM_SERVER"), FAIL_TO_SERVER("FAIL_TO_SERVER"), ADD_USER_TO_SERVER("ADD_USER_TO_SERVER"), 
				      USER_DOESNT_EXIST_IN_SERVER("USER_DOESNT_EXIST_IN_SERVER");

		  private final String value;

		  private ReturnValue(String s) {
		    value = s;
		  }

		  public String value() {
		    return value;
		  }
		  
}
