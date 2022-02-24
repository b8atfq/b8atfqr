package com.transfers.payments.db.enumeration;

public enum Status {
	PREPARED, PAID;

//	public static Status getStatus(Payment payment) {
//		int statusId = payment.getStatusId();
//		return Status.values()[statusId];
//	}

	public String getName() {
		return name().toLowerCase();
	}
}