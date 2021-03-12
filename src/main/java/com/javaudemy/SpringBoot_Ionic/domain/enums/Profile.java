package com.javaudemy.SpringBoot_Ionic.domain.enums;

public enum Profile {

	ADMIN(1, "ROLE_ADMIN"), CLIENT(2, "ROLE_CLIENT");

	private int code;
	private String description;

	private Profile(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static Profile toIntegerEnum(Integer code) {
		if (code == null) {
			return null;
		}

		for (Profile ct : Profile.values()) {
			if (code.equals(ct.getCode())) {
				return ct;
			}
		}

		throw new IllegalArgumentException("Invalid id" + code);
	}

	public static Profile toStringEnum(String description) {
		if (description == null) {
			return null;
		}

		for (Profile ps : Profile.values()) {
			if (description.equals(ps.getDescription())) {
				return ps;
			}
		}

		throw new IllegalArgumentException("Invalid description" + description);
	}
}
