package web.service;
/**
 * This description my service methods
 * @author Eugene Kashitsyn
 */

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.User;
import web.repository.RoleRepository;
import web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    BCryptPasswordEncoder cryptPasswordEncoder;

    @Autowired
    public void setCryptPasswordEncoder(BCryptPasswordEncoder cryptPasswordEncoder) {
        this.cryptPasswordEncoder = cryptPasswordEncoder;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '$s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

              /* /**Next following my users methods**/


    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }


   /* public List<User> findAll() {
        return userRepository.findAll();
    }*/


    public User findById(Integer id) {
        Optional<User> userFromDB = userRepository.findById(id);
        return userFromDB.get();
    }


    public Role findByIdRole(Integer id){
        Optional<Role> roleFromDB=roleRepository.findById(id);
        return roleFromDB.get();
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }


    public void update(User user, String[] role) {
        Set<Role> rol = new HashSet<>();
        for (String s : role) {
            if (s.equals("ROLE_ADMIN")) {
                rol.add(findByIdRole(2));
            } else {
                rol.add(findByIdRole(1));
            }
        }
        user.setRoles(rol);
        user.setPassword(cryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void deleteById(Integer userId) {
        userRepository.deleteById(userId);
    }


    public List<User> allUsers() {
        return new ArrayList<>(userRepository.findAll());
    }


    public void saveUser(User user) {
        user.setPassword(cryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

}