package com.example.backendproject.service;

import com.example.backendproject.domain.dto.forcreate.CCustomer;
import com.example.backendproject.domain.dto.forlist.DiscountDTO;
import com.example.backendproject.domain.dto.forlist.UserDTO;
import com.example.backendproject.domain.dto.forlist.UserListDTO;
import com.example.backendproject.domain.model.Authority;
import com.example.backendproject.domain.model.User;
import com.example.backendproject.domain.model.enums.ERole;
import com.example.backendproject.repository.AuthorityRepository;
import com.example.backendproject.repository.UserRepository;
import com.example.backendproject.service.templates.IUserService;
import com.example.backendproject.util.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomPasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private EmailServiceImpl emailService;

    public Page<UserListDTO> getAllUsers(int page, int size) {
        return userRepository.findAllUsers(PageRequest.of(page, size));
    }


    @Override
    public User add(CCustomer cCustomer) {
        User customer = new User();
        customer.setUsername(cCustomer.getUsername());
        customer.setPassword(passwordEncoder.getPasswordEncoder().encode(cCustomer.getPassword()));
        customer.setFirstName(cCustomer.getFirstName());
        customer.setLastName(cCustomer.getLastName());
//        customer.setAddress(cCustomer.getAddress());
        customer.setEmail(cCustomer.getEmail());
        customer.setBirthday(cCustomer.getBirthday());
        customer.setPhone(cCustomer.getPhone());
        customer.setGender((cCustomer.getGender()));
        emailService.sendSuccessRegister(cCustomer.getEmail(), cCustomer.getPassword());
        userRepository.save(customer);
        return customer;
    }


    @Transactional
    public User createUser(UserDTO userDTO) {
        // Kiểm tra xem username đã tồn tại hay chưa
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists!");
        }

        // Tạo đối tượng User từ UserDTO
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.getPasswordEncoder().encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setGender(userDTO.getGender());
        user.setBirthday(userDTO.getBirthday());

        // Lưu User vào cơ sở dữ liệu
        User savedUser = userRepository.save(user);

        // Tạo Authority từ roles trong UserDTO
        Authority authority = new Authority();
        authority.setAuthority(userDTO.getRoles()); // Gán role (tên authority)
        authority.setUser(savedUser);
        authorityRepository.save(authority);

        return savedUser;
    }


    // Cập nhật thông tin User và quyền
    public User updateUser(Long id, UserDTO userDTO, Set<ERole> roles) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setPhone(userDTO.getPhone());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setGender(userDTO.getGender());
            user.setBirthday(userDTO.getBirthday());

            // Fetch and delete existing authorities
            Set<Authority> currentAuthorities = authorityRepository.findByUserId(user.getId());
            authorityRepository.deleteAll(currentAuthorities);

            // Map roles to new authorities and save them
            List<Authority> authorities = roles.stream()
                    .map(role -> new Authority(role, user, null))
                    .collect(Collectors.toList());
            authorityRepository.saveAll(authorities);

            user.setAuthorities(new HashSet<>(authorities));
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // Xóa User và quyền liên quan
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        // Xóa quyền liên quan
        Set<Authority> authorities = authorityRepository.findByUserId(user.getId());
        authorityRepository.deleteAll(authorities);

        userRepository.delete(user);
    }

    // Lấy thông tin User theo ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    private User mapToEntity(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setGender(userDTO.getGender());
        user.setBirthday(userDTO.getBirthday());
        return user;
    }

    public long getTotalUsers() {
        return userRepository.count(); // Lấy tổng số sản phẩm
    }
}
