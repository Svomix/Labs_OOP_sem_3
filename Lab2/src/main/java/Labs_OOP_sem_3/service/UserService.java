package Labs_OOP_sem_3.service;

import Labs_OOP_sem_3.entities.UserEntity;
import Labs_OOP_sem_3.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class UserService implements UserDetailsManager {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).map(user -> new User(user.getUsername(),
                user.getPassword(),
                new ArrayList<>())).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }


    @Override
    public void createUser(UserDetails user) {
        userRepository.save((UserEntity) user);
    }

    @Override
    public void updateUser(UserDetails user) {
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.delete(userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username)));
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public UserEntity findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public UserEntity findUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found: " + id));
    }
    public void updateSequence()
    {
       userRepository.restartSeq();
    }
}
