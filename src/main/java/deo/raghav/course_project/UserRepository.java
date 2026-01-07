package deo.raghav.course_project;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findById(Long id);
	List<User> findByOriginCity(String origin_city);
	List<User> findByOriginCityAndOriginState(String originCity, String originState);
	List<User> findByEmail(String email);
	List<User> findByCurrentCityAndCurrentStateAndOriginState(String currentCity, String currentState, String originState);
	List<User> findByCurrentCityAndCurrentStateAndOriginCityAndOriginState(String currentCity, String currentState, String originCity, String originState);
}