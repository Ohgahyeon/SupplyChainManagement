package kr.happyjob.study.epc.model;

public class ShoppingCartItemDTO {

	int pur_id;
	int sales_id;
	String photo;
	String model_nm;
	int price;
	int pur_cnt;
	String wanted_date;
	String loginID;
	String purcYN;
	String type;
	
	public int getSales_id() {
		return sales_id;
	}
	public String getPhoto() {
		return photo;
	}
	public String getModel_nm() {
		return model_nm;
	}
	public int getPrice() {
		return price;
	}
	public int getPur_cnt() {
		return pur_cnt;
	}
	public String getWanteddate() {
		return wanted_date;
	}
	public String getLoginID() {
		return loginID;
	}
	public String getPurcYN() {
		return purcYN;
	}
	public String getWanted_date() {
		return wanted_date;
	}
	public void setWanted_date(String wanted_date) {
		this.wanted_date = wanted_date;
	}
	public int getPur_id() {
		return pur_id;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setPur_id(int pur_id) {
		this.pur_id = pur_id;
	}
	public void setSales_id(int sales_id) {
		this.sales_id = sales_id;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public void setModel_nm(String model_nm) {
		this.model_nm = model_nm;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public void setPur_cnt(int pur_cnt) {
		this.pur_cnt = pur_cnt;
	}
	public void setWanteddate(String wanteddate) {
		this.wanted_date = wanteddate;
	}
	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}
	public void setPurcYN(String purcYN) {
		this.purcYN = purcYN;
	}
	@Override
	public String toString() {
		return "ShoppingCartItemDTO [pur_id=" + pur_id + ", sales_id=" + sales_id + ", photo=" + photo + ", model_nm="
				+ model_nm + ", price=" + price + ", pur_cnt=" + pur_cnt + ", wanted_date=" + wanted_date + ", loginID="
				+ loginID + ", purcYN=" + purcYN + ", type=" + type + "]";
	}

	
	
	
}
