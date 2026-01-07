/**
 * 
 */
package deo.raghav.course_project;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author raghav
 *
 */

@Service
public class UserService {

		@Autowired
		private UserRepository userRepository;
		
		public Optional<User> find_by_id(Long id) {
			return userRepository.findById(id);
		}
		
		public Optional<User> find_by_email_id(String email_id) {
			if (userRepository.findByEmail(email_id).size() == 0) {
				return Optional.empty();
			}
			return Optional.of(userRepository.findByEmail(email_id).get(0));
		}
		
		public List<User> find_by_current_city_and_current_state_and_origin_city_and_origin_state(String current_city, String current_state, String origin_city, String origin_state) {
			return userRepository.findByCurrentCityAndCurrentStateAndOriginCityAndOriginState(current_city, current_state, origin_city, origin_state);
		}
		
		public List<User> find_by_current_city_and_current_state_and_origin_state(String current_city, String current_state, String origin_state) {
			return userRepository.findByCurrentCityAndCurrentStateAndOriginState(current_city, current_state, origin_state);
		}
		
		public List<User> find_by_origin_city(String origin_city) {
			return userRepository.findByOriginCity(origin_city);
		}
		
		public List<User> find_by_origin_city_and_origin_state(String origin_city, String origin_state) {
			return userRepository.findByOriginCityAndOriginState(origin_city, origin_state);
		}
		
		public User register_user(String name, String phone_no, String email_id, String origin_city, String origin_state, String current_city, String current_state, int last_otp, Instant otp_instant, int verified) {
			User user = new User(name, phone_no, email_id, origin_city, origin_state, current_city, current_state, last_otp, otp_instant, verified);
			return save_user(user);
		}
		
		public User save_user(User user) {
			return userRepository.save(user);
		}
	
}
