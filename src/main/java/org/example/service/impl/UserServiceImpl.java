package org.example.service.impl;

import java.util.List;
import java.util.Optional;

import org.example.exception.NotFoundException;
import org.example.model.User;
import org.example.repository.Repository;
import org.example.service.UserService;

public class UserServiceImpl implements UserService {
    private final Repository<User, Long> userRepository;

    public UserServiceImpl(Repository<User, Long> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUser(Long id) throws NotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }

    @Override
    public void updateUser(Long userId, User user) {
        Optional<User> userToUpdate = userRepository.findById(userId);
        if (userToUpdate.isPresent()) {
            User updatedUser = new User(userId, user.login(), user.password());
            userRepository.update(updatedUser);
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}