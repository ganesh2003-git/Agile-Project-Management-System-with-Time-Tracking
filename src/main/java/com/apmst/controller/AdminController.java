package com.apmst.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apmst.dto.UserDTO;
import com.apmst.entity.User;
import com.apmst.enums.Role;
import com.apmst.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;

	// show all user
	@GetMapping("/users")
	public String getAllUser(Model model) {
		model.addAttribute("user", userService.getAllUsers());
		return "admin/users";

	}

	// show create user form
	@GetMapping("/users/create")
	public String showCreateUserForm(Model model) {
		model.addAttribute("userDTO", new UserDTO());
		model.addAttribute("roles", Role.values());
		return "admin/create-user";
	}

	// Handle create user form submission
	@PostMapping("/users/create")
	public String createUser(@ModelAttribute UserDTO userDTO, Model model) {

		// Check if email already exists
		if (userService.existsByEmail(userDTO.getEmail())) {
			model.addAttribute("error", "Email already exists!");
			model.addAttribute("roles", Role.values());
			return "admin/create-user";
		}

		// Create new user from DTO
		User user = new User();
		user.setFullName(userDTO.getFullName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setRole(userDTO.getRole());
		user.setActive(true);
		user.setPoints(0);

		userService.saveUser(user);
		return "redirect:/admin/users?success=true";
	}

	// Toggle user active/inactive
	@GetMapping("/users/toggle/{id}")
	public String toggleUserStatus(@PathVariable Long id) {
		userService.findById(id).ifPresent(user -> {
			user.setActive(!user.isActive());
			userService.updateUser(user);
		});
		return "redirect:/admin/users";
	}

	// Delete user
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return "redirect:/admin/users";
	}
}
