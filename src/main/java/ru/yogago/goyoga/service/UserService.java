package ru.yogago.goyoga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.yogago.goyoga.data.ClientEntity;
import ru.yogago.goyoga.data.RegisterUserInit;
import ru.yogago.goyoga.data.RoleEntity;
import ru.yogago.goyoga.data.UserEntity;
import ru.yogago.goyoga.repo.ClientRepository;
import ru.yogago.goyoga.repo.RoleRepository;
import ru.yogago.goyoga.repo.UserRepository;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.*;
import java.util.logging.Logger;

@Service
public class UserService implements UserDetailsService {

	private final static Logger logger = Logger.getLogger("userService");
	private final ClientRepository clientRepository;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	@Autowired
	public UserService(ClientRepository clientRepository, UserRepository userRepository, RoleRepository roleRepository) {
		this.clientRepository = clientRepository;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	public void deleteUserById(Long id) {
		this.userRepository.deleteById(id);
	}

	public Optional<UserEntity> findUserById(Long id) {
		return this.userRepository.findById(id);
	}

	public List<UserEntity> findAllUsers() {
		return this.userRepository.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails userDetails = userRepository.findByUsername(username);
		if (userDetails == null)
			return null;

		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		for (GrantedAuthority role : userDetails.getAuthorities()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}

		return new org.springframework.security.core.userdetails.User(userDetails.getUsername(),
				userDetails.getPassword(), userDetails.getAuthorities());
	}

	@Transactional
	@Secured(value = "ROLE_ANONYMOUS")
	public UserEntity registerUser(RegisterUserInit init) {

		UserEntity userLoaded = userRepository.findByUsername(init.getUserName());

		if (userLoaded == null) {
			UserEntity userEntity = new UserEntity();
			userEntity.setUsername(init.getUserName());

			userEntity.setRoles(getUserRoles());
			// TODO firebase users should not be able to login via username and password so for now generation of password is OK
			userEntity.setPassword(UUID.randomUUID().toString());
			userRepository.save(userEntity);
			ClientEntity client = new ClientEntity(userEntity);
			clientRepository.save(client);
			logger.info("registerUser -> user created");
			return userEntity;
		} else {
			logger.info("registerUser -> user exists");
			return userLoaded;
		}
	}

	@PostConstruct
	public void init() {

		if (userRepository.count() == 0) {
			UserEntity adminEntity = new UserEntity();
			adminEntity.setUsername("admin");
			adminEntity.setPassword("qwerty");

			adminEntity.setRoles(getAdminRoles());
			userRepository.save(adminEntity);

			UserEntity userEntity = new UserEntity();
			userEntity.setUsername("user1");
			userEntity.setPassword("user1");
			userEntity.setRoles(getUserRoles());

			userRepository.save(userEntity);
		}
	}

	private List<RoleEntity> getAdminRoles() {
		return Collections.singletonList(getRole("ROLE_ADMIN"));
	}

	private List<RoleEntity> getUserRoles() {
		return Collections.singletonList(getRole("ROLE_USER"));
	}

	/**
	 * Get or create role
	 * @param authority
	 * @return
	 */
	private RoleEntity getRole(String authority) {
		RoleEntity adminRole = roleRepository.findByName(authority);
		if (adminRole == null) {
			return new RoleEntity(authority);
		} else {
			return adminRole;
		}
	}

}
