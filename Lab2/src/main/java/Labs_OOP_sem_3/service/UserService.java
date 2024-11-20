package Labs_OOP_sem_3.service;

import Labs_OOP_sem_3.entities.UserEntity;
import Labs_OOP_sem_3.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsManager
{
    private final UserRepository userRepository;
    @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Override
    public void createUser(UserDetails user) {
        userRepository.createUser(user.getUsername(), user.getPassword());
    }

    @Override
    public void updateUser(UserDetails user) {
        userRepository.updateByUsername(user.getUsername(), user.getPassword());
    }
    @Override
    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    public boolean userExists(String username) {
        return userRepository.findByUsername(username) != null;
    }
}
