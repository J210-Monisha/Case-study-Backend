package com.cozyhaven.controller;

import com.cozyhaven.entity.Hotel;
import com.cozyhaven.entity.User;
import com.cozyhaven.repository.UserRepository;
import com.cozyhaven.service.HotelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private UserRepository userRepository;

    // 📌 PUBLIC: Get all hotels (accessible to everyone)
    @GetMapping
    public ResponseEntity<List<Hotel>> list() {
        return ResponseEntity.ok(hotelService.findAll());
    }

    // 📌 PUBLIC: Get hotel by ID
    @GetMapping("/{id}")
    public ResponseEntity<Hotel> get(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.findById(id));
    }

    // ✅ Only OWNER or ADMIN can add a hotel
    @PostMapping
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
    public ResponseEntity<Hotel> add(@RequestBody Hotel hotel) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        hotel.setOwner(owner);
        return ResponseEntity.ok(hotelService.save(hotel));
    }

    // ✅ Only OWNER or ADMIN can delete a hotel
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        hotelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
