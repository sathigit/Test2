package com.atpl.mmg.utils;

public enum BookedGoodsTypes {
	
	USED_GOODS(1), NEW_GOODS(2);

	private int code;

	private BookedGoodsTypes(int c) {
		code = c;
	}

	public int getCode() {
		return code;
	}

}
