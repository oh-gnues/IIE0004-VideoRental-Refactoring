package src;

import java.util.Date;

public class Rental {
	private Video video ;
	private int status ; // 0 for Rented, 1 for Returned
	private Date rentDate ;
	private Date returnDate ;

	public Rental(Video video) {
		this.video = video ;
		status = 0 ;
		rentDate = new Date() ;
	}
	
	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public int getStatus() {
		return status;
	}

	public void returnVideo() {
		if ( status == 1 ) {
			this.status = 1;
			returnDate = new Date() ;
		}
	}
	public Date getRentDate() {
		return rentDate;
	}

	public void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}
	
	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public int getDaysRentedLimit() {
		int limit = switch (video.getVideoType()) {
            case Video.VHS -> 5;
            case Video.CD -> 3;
            case Video.DVD -> 2;
            default -> 0;
        };
        return limit;
	}

	public int getDaysRented() {
		long diff;
		if (getStatus() == 1) {
			diff = returnDate.getTime() - rentDate.getTime();
		} else {
			diff = new Date().getTime() - rentDate.getTime();
		}
		return ((int) (diff / (1000 * 60 * 60 * 24)) + 1);
	}
}