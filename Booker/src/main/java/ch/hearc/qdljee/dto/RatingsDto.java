package ch.hearc.qdljee.dto;

public class RatingsDto {
	private Integer value;
	
	public RatingsDto() {}
	
	public RatingsDto(Integer value) {
		this.value = value;
	}

	public final Integer getValue() {
		return value;
	}

	public final void setValue(Integer value) {
		this.value = value;
	}
}
