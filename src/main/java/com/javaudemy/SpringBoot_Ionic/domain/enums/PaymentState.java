package com.javaudemy.SpringBoot_Ionic.domain.enums;

public enum PaymentState {

	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");
	
	private int code;
	private String description;
	
	private PaymentState(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static PaymentState toIntegerEnum(Integer code) {
		if(code == null) {
			return null;
		}
		
		for(PaymentState ps: PaymentState.values()) {
			if(code.equals(ps.getCode())) {
				return ps;
			}
		}
		
		throw new IllegalArgumentException("Invalid id" + code);
	}
	
	public static PaymentState toStringEnum(String description) {
		if(description == null) {
			return null;
		}
		
		for(PaymentState ps: PaymentState.values()) {
			if(description.equals(ps.getDescription())) {
				return ps;
			}
		}
		
		throw new IllegalArgumentException("Invalid description" + description);
	}
}