/**
 * 
 */
package deo.raghav.course_project;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * @author raghav
 *
 */

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private String phoneNo;
	
	private String email;
	
	private String originCity;
	
	private String originState;
	
	private String currentCity;
	
	private String currentState;
	
	private int lastOtp;
	
	//Stores date and time of creation of otp. Time in GMT format
	private Instant otpInstant;
	
	private int verified;
	
	// The no-argument constructor required by frameworks
    // Make it protected to restrict direct use outside the framework
    protected User() {
    }
	
	public User(String name, String phone_no, String email_id, String origin_city, String origin_state, String current_city, String current_state, int last_otp, Instant otp_instant, int verified) {
		this.name = name;
		this.phoneNo = phone_no;
		this.email = email_id;
		this.originCity = origin_city;
		this.originState = origin_state;
		this.lastOtp = last_otp;
		this.otpInstant = otp_instant;
		this.verified = verified;
		this.currentCity = current_city;
		this.currentState = current_state;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setOriginCity(String origin_city) {
		this.originCity = origin_city;
	}
	
	public String getOriginCity() {
		return originCity;
	}
	
	public void setOriginState(String origin_state) {
		this.originState = origin_state;
	}
	
	public String getOriginState() {
		return originState;
	}
	
	public void setCurrentCity(String current_city) {
		this.currentCity = current_city;
	}
	
	public String getCurrentCity() {
		return currentCity;
	}
	
	public void setCurrentState(String current_state) {
		this.currentState = current_state;
	}
	
	public String getCurrentState() {
		return currentState;
	}
	
	public void setPhone(String phone_no) {
		this.phoneNo = phone_no;
	}
	
	public String getPhone() {
		return phoneNo;
	}
	
	public void setEmail(String email_id) {
		this.email = email_id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setOtp(int otp) {
		this.lastOtp = otp;
	}
	
	public int getOtp() {
		return lastOtp;
	}
	
	public void setOtpInstant(Instant otp_instant) {
		this.otpInstant = otp_instant;
	}
	
	public Instant getOtpInstant() {
		return otpInstant;
	}
	
	public void setVerified(int verified) {
		this.verified = verified;
	}
	
	public int getVerified() {
		return verified;
	}
	
}
