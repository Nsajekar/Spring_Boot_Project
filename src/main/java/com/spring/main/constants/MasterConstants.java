package com.spring.main.constants;

public interface MasterConstants {

	public static final int LTW = 1;
	public static final int LTI = 2;
	public static final int LTE = 3;
	public static final int LTD = 4;
	public static final String EMPTY_STRING = "";

	public static class ErrorResponseCodes {

		private ErrorResponseCodes() {
			throw new IllegalStateException();
		}

		public static final String INVALID_API_PSD = "E01";
		public static final String DUP_REF_NO = "E02";
		public static final String REQ_DATA = "E03";
		public static final String INVALID_DATA_TYPE = "E04";
		public static final String INVALID_DATA_LENGTH = "E05";

	}
}
