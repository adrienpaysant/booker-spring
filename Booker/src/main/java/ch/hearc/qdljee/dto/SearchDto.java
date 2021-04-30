package ch.hearc.qdljee.dto;

public class SearchDto {
	private String searchValue;
	private String searchCriter;

	public String getSearchCriter() {
		return searchCriter;
	}

	public void setSearchCriter(String searchCriter) {
		this.searchCriter = searchCriter;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public SearchDto() {

	}

	public SearchDto(String searchValue, String searchCriter) {
		super();
		this.searchValue = searchValue;
		this.searchCriter = searchCriter;
	}

}
