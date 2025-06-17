//package com.nkm.Testapp.ServiceImpl;
//
//import com.nkm.Testapp.DTO.UserDTO;
//import com.nkm.Testapp.Entity.User;
//import com.nkm.Teastapp.Repository.UserRepository;
//import com.nkm.Testapp.Service.UserService;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class UserServiceImpl implements UserService {
//
//    private final UserRepository userRepo;
//    private final ModelMapper modelMapper;
//
//    public UserServiceImpl(UserRepository userRepo, ModelMapper modelMapper) {
//        this.userRepo = userRepo;
//        this.modelMapper = modelMapper;
//    }
//
//    @Override
//    public UserDTO createUser(UserDTO userDTO) {
//        User user = modelMapper.map(userDTO, User.class);
//        user = userRepo.save(user);
//        return modelMapper.map(user, UserDTO.class);
//    }
//
//    @Override
//    public List<UserDTO> getAllUsers() {
//        return userRepo.findAll()
//                .stream()
//                .map(user -> modelMapper.map(user, UserDTO.class))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public UserDTO getUserById(Long id) {
//        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
//        return modelMapper.map(user, UserDTO.class);
//    }
//
//    @Override
//    public void deleteUser(Long id) {
//        userRepo.deleteById(id);
//    }
//}
