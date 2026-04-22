package com.apmst.serviceIMPL;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apmst.entity.User;
import com.apmst.repository.UserRepository;
import com.apmst.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	public User saveUser(User user) {
		// Encrypt password before saving
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}
	
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@Override
	public User updateUser(User user) {
		return userRepository.save(user);
	}
	
	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
	
	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	@Override
	public void updatePoints(Long userId, int points) {
		Optional<User> user = userRepository.findById(userId);
		user.ifPresent(u ->{
			u.setPoints(u.getPoints() + points);
			userRepository.save(u);
		});
	}
	
	
}
