package deo.raghav.course_project;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@SpringBootApplication
@RestController
public class CourseProjectApplication {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailService mailService;

	public static void main(String[] args) {
		SpringApplication.run(CourseProjectApplication.class, args);
	}
	
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
	        description = "Key value pair",
	        required = true,
	        content = @Content(
	            mediaType = "application/json",
	            examples = {
	                @ExampleObject(
	                    name = "Example 1",
	                    value = "{\"email_id\": \"abc@abc.com\"}"
	                )
	            }
	        )
	    )
	@PostMapping(value = "/login")
	@Operation(summary = "Perform login")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "Login successful",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = User.class),
				examples = @ExampleObject(
					name = "Success example",
					value = "{\"id\":2,\"name\":\"Raghav Deo\",\"email\":\"raghav.1251070849@vit.edu\",\"originCity\":\"Pune\",\"originState\":\"Maharashtra\",\"currentCity\":null,\"currentState\":null,\"otpInstant\":\"2025-11-13T16:47:03.378662923Z\",\"verified\":false,\"otp\":149481,\"phone\":\"0000000000\"}"
				)
			)
		),
		@ApiResponse(
			responseCode = "404",
			description = "User not found"
		)
	})
	public ResponseEntity<User> login(@RequestBody Map<String, Object> data, HttpSession session) {
		
		String email_id = (String) data.get("email_id");
		if (userService.find_by_email_id(email_id).isPresent()) {
			User user = userService.find_by_email_id(email_id).get();
			int otp = 100000 + (new Random()).nextInt(900000);
			user.setOtp(otp);
			user.setOtpInstant(Instant.now());
			user = userService.save_user(user);
			mailService.send_mail(email_id, "OTP for verification", "Hello " + user.getName() + ",\nYour OTP for verification is : " + otp);
			session.setAttribute("email_id", email_id);
			session.setAttribute("verified", false);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
	        description = "Key value pair",
	        required = true,
	        content = @Content(
	            mediaType = "application/json",
	            examples = {
	                @ExampleObject(
	                    name = "Example 1",
	                    value = "{\"name\": \"ABC\", \"phone_no\": \"1234567890\", \"email_id\": \"abc@abc.com\", \"origin_city\": \"pune\", \"origin_state\": \"maharashtra\", \"current_city\": \"bangalore\", \"current_state\": \"karnataka\"}"
	                )
	            }
	        )
	    )
	@PostMapping(value = "/register")
	@Operation(summary = "Register user")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "Registration successful",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = User.class),
				examples = @ExampleObject(
					name = "Success example",
					value = "{\"id\":2,\"name\":\"Raghav Deo\",\"email\":\"raghav.1251070849@vit.edu\",\"originCity\":\"Pune\",\"originState\":\"Maharashtra\",\"currentCity\":null,\"currentState\":null,\"otpInstant\":\"2025-11-13T16:47:03.378662923Z\",\"verified\":false,\"otp\":149481,\"phone\":\"0000000000\"}"
				)
			)
		),
		@ApiResponse(
			responseCode = "409",
			description = "User already exists"
		)
	})
	public ResponseEntity<User> register(@RequestBody Map<String, Object> data, HttpSession session) {
		
		String name = (String) data.get("name");
		String phone_no = (String) data.get("phone_no");
		String email_id = (String) data.get("email_id");
		String origin_city = (String) data.get("origin_city");
		String origin_state = (String) data.get("origin_state");
		String current_city = (String) data.get("current_city");
		String current_state = (String) data.get("current_state");
		if (userService.find_by_email_id(email_id).isPresent()) {
			return new ResponseEntity<User>(HttpStatus.CONFLICT);
		}
		int otp = 100000 + (new Random()).nextInt(900000);
		User user = userService.register_user(name, phone_no, email_id, origin_city, origin_state, current_city, current_state, otp, Instant.now(), 0);
		mailService.send_mail(email_id, "OTP for verification", "Hello " + user.getName() + ",\nYour OTP for verification is : " + otp);
		session.setAttribute("email_id", email_id);
		session.setAttribute("verified", false);
		return new ResponseEntity<User>(user, HttpStatus.OK);
		
	}
	
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
	        description = "Key value pair",
	        required = true,
	        content = @Content(
	            mediaType = "application/json",
	            examples = {
	                @ExampleObject(
	                    name = "Example 1",
	                    value = "{\"email_id\": \"abc@abc.com\", \"otp\": 123}"
	                )
	            }
	        )
	    )
	@PostMapping(value = "/verify")
	@Operation(summary = "Verify user")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "Verification process completed",
			content = @Content(
				mediaType = "text/plain",
				examples = {
					@ExampleObject(
						name = "Success Example",
						value = "true"
					),
					@ExampleObject(
						name = "Fail Example",
						value = "false"
					)
				}
			)
		),
		@ApiResponse(
			responseCode = "400",
			description = "User not found"
		)
	})
	public ResponseEntity<Boolean> verify(@RequestBody Map<String, Object> data, HttpSession session) {
		
		String email_id = (String) session.getAttribute("email_id");
		int otp = (int) data.get("otp");
		if (userService.find_by_email_id(email_id).isPresent()) {
			if (userService.find_by_email_id(email_id).get().getOtp() == otp) {
				session.setAttribute("verified", true);
				userService.find_by_email_id(email_id).get().setVerified(1);
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} else {
				return new ResponseEntity<Boolean>(false, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/view-self")
	@Operation(summary = "View user details",
		parameters = {
	               @Parameter(name = "JSESSIONID",
	                          description = "The required session cookie",
	                          required = true,
	                          in = ParameterIn.COOKIE)
	    }
	)
	@ApiResponses(value = {
	@ApiResponse(
		responseCode = "200",
		description = "View self",
				content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = User.class),				
					examples = {
						@ExampleObject(
							name = "Success example",
							value = "{\"id\":2,\"name\":\"Raghav Deo\",\"email\":\"raghav.1251070849@vit.edu\",\"originCity\":\"Pune\",\"originState\":\"Maharashtra\",\"currentCity\":null,\"currentState\":null,\"otpInstant\":\"2025-11-13T16:47:03.378662923Z\",\"verified\":false,\"otp\":149481,\"phone\":\"0000000000\"}"
						)
					}
				)
	),
	@ApiResponse(
			responseCode = "401",
			description = "User not logged in"
	)
	})
	public ResponseEntity<User> viewSelf(HttpSession session) {
		if (session.getAttribute("email_id") == null || (Boolean)session.getAttribute("verified") != true) {
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
		}
		String email_id = (String) session.getAttribute("email_id");
		Optional<User> user = userService.find_by_email_id(email_id);
		if (user.isPresent()) {
			return new ResponseEntity<User>(user.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	}
	
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
	        description = "Key value pair",
	        required = true,
	        content = @Content(
	            mediaType = "application/json",
	            examples = {
	                @ExampleObject(
	                    name = "Example 1",
	                    value = "{\"origin_city\": \"pune\", \"origin_state\": \"maharashtra\", \"current_city\": \"bangalore\", \"current_state\": \"karnataka\"}"
	                )
	            }
	        )
	    )
	@PostMapping(value = "/search")
	@Operation(summary = "Perform search",
			parameters = {
	                   @Parameter(name = "JSESSIONID",
	                              description = "The required session cookie",
	                              required = true,
	                              in = ParameterIn.COOKIE)
	        }
	)
			
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "Success Example",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = User.class),				
				examples = {
					@ExampleObject(
						name = "Success example",
						value = "[{\"id\":2,\"name\":\"Raghav Deo\",\"email\":\"raghav.1251070849@vit.edu\",\"originCity\":\"Pune\",\"originState\":\"Maharashtra\",\"currentCity\":null,\"currentState\":null,\"otpInstant\":\"2025-11-13T16:47:03.378662923Z\",\"verified\":false,\"otp\":149481,\"phone\":\"0000000000\"},{\"id\":2,\"name\\\":\\\"Raghav Deo\",\"email\":\"raghav.1251070849@vit.edu\",\"originCity\":\"Pune\",\"originState\":\"Maharashtra\",\"currentCity\":null,\"currentState\":null,\"otpInstant\":\"2025-11-13T16:47:03.378662923Z\",\"verified\":false,\"otp\":149481,\"phone\":\"0000000000\"}]"
					)
				}
			)
		),
		@ApiResponse(
				responseCode = "401",
				description = "User not logged in / not verified"
		)
	})
	public ResponseEntity<List<User>> search(@RequestBody Map<String, Object> data, HttpSession session) {
		if (session.getAttribute("email_id") == null || (Boolean)session.getAttribute("verified") != true) {
			return new ResponseEntity<List<User>>(HttpStatus.UNAUTHORIZED);
		}
		Boolean verified = (Boolean) session.getAttribute("verified");
		if (verified == null || verified == false) {
			return new ResponseEntity<List<User>>(HttpStatus.UNAUTHORIZED);
		}
		String origin_city = (String) data.get("origin_city");
		String origin_state = (String) data.get("origin_state");
		String current_city = (String) data.get("current_city");
		String current_state = (String) data.get("current_state");
		if (origin_city.isBlank() || origin_city.isEmpty()) {
			return new ResponseEntity<List<User>>(userService.find_by_current_city_and_current_state_and_origin_state(current_city, current_state, origin_state), HttpStatus.OK);
		}
		return new ResponseEntity<List<User>>(userService.find_by_current_city_and_current_state_and_origin_city_and_origin_state(current_city, current_state, origin_city, origin_state), HttpStatus.OK);
	}
	
	@PostMapping(value = "/logout")
	@Operation(summary = "Peform logout")
	@ApiResponse(
		responseCode = "200",
		description = "Logged out",
		content = @Content(
				mediaType = "text/plain",
				examples = {
					@ExampleObject(
						name = "Success Example",
						value = "true"
					)
				}
			)
		)
	public ResponseEntity<Boolean> logout(HttpSession session) {
		session.invalidate();
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
	        description = "Key value pair",
	        required = true,
	        content = @Content(
	            mediaType = "application/json",
	            examples = {
	                @ExampleObject(
	                    name = "Example 1",
	                    value = "{\"id\": 123}"
	                )
	            }
	        )
	    )
	@PostMapping(value = "/view-user")
	@Operation(summary = "View user details",
			parameters = {
	                   @Parameter(name = "JSESSIONID",
	                              description = "The required session cookie",
	                              required = true,
	                              in = ParameterIn.COOKIE)
	        }
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "View user",
					content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = User.class),				
						examples = {
							@ExampleObject(
								name = "Success example",
								value = "{\"id\":2,\"name\":\"Raghav Deo\",\"email\":\"raghav.1251070849@vit.edu\",\"originCity\":\"Pune\",\"originState\":\"Maharashtra\",\"currentCity\":null,\"currentState\":null,\"otpInstant\":\"2025-11-13T16:47:03.378662923Z\",\"verified\":false,\"otp\":149481,\"phone\":\"0000000000\"}"
							)
						}
					)
		),
		@ApiResponse(
				responseCode = "401",
				description = "User not logged in"
		)
	})
	public ResponseEntity<User> viewUser(@RequestBody Map<String, Object> data, HttpSession session) {
		if (session.getAttribute("email_id") == null || (Boolean)session.getAttribute("verified") != true) {
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
		}
		Long id = ((Number) data.get("id")).longValue();
		if (userService.find_by_id(id).isEmpty()) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(userService.find_by_id(id).get(), HttpStatus.OK);
	}
	
	@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "Key value pair",
			required = true,
			content = @Content(
				mediaType = "application/json",
				examples = {
					@ExampleObject(
						name = "Example 1",
						value = "{\"name\": \"ABC\", \"phone_no\": \"1234567890\", \"email_id\": \"abc@abc.com\", \"origin_city\": \"pune\", \"origin_state\": \"maharashtra\", \"current_city\": \"bangalore\", \"current_state\": \"karnataka\"}"
					)
				}
			)
		)
	@PostMapping(value = "update-user")
	@Operation(summary = "Update existing user",
			parameters = {
	                   @Parameter(name = "JSESSIONID",
	                              description = "The required session cookie",
	                              required = true,
	                              in = ParameterIn.COOKIE)
	        }
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "Updated user",
			content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = User.class),				
					examples = {
						@ExampleObject(
							name = "Success example",
							value = "{\"id\":2,\"name\":\"Raghav Deo\",\"email\":\"raghav.1251070849@vit.edu\",\"originCity\":\"Pune\",\"originState\":\"Maharashtra\",\"currentCity\":null,\"currentState\":null,\"otpInstant\":\"2025-11-13T16:47:03.378662923Z\",\"verified\":false,\"otp\":149481,\"phone\":\"0000000000\"}"
						)
					}
			)
		),
		@ApiResponse(
				responseCode = "401",
				description = "User not logged in"
		)
	})
	public ResponseEntity<User> updateUser(@RequestBody Map<String, Object> data, HttpSession session) {
		if (session.getAttribute("email_id") == null || (Boolean)session.getAttribute("verified") != true) {
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
		}
		String email_id = (String) session.getAttribute("email_id");
		Optional<User> optionalUser = userService.find_by_email_id(email_id);
		if (optionalUser.isEmpty()) {
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
		}
		User user = optionalUser.get();
		if (data.get("name") != null) {
			user.setName((String) data.get("name"));
		}
		if (data.get("phone_no") != null) {
			user.setPhone((String) data.get("phone_no"));
		}
		if (data.get("origin_city") != null) {
			user.setOriginCity((String) data.get("origin_city"));
		}
		if (data.get("origin_state") != null) {
			user.setOriginState((String) data.get("origin_state"));
		}
		if (data.get("current_city") != null) {
			user.setCurrentCity((String) data.get("current_city"));
		}
		if (data.get("current_state") != null) {
			user.setCurrentState((String) data.get("current_state"));
		}
		userService.save_user(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
}