package com.temple.vazhipadu.offering;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offerings")
public class OfferingController {

    private final OfferingRepository offeringRepository;

    public OfferingController(OfferingRepository offeringRepository) {
        this.offeringRepository = offeringRepository;
    }

    @GetMapping
    public List<Offering> getAllOfferings() {
        return offeringRepository.findAll();
    }

    @GetMapping("/{id}")
    public Offering getOfferingById(@PathVariable Long id) {
        return offeringRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Offering not found: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Offering createOffering(@Valid @RequestBody OfferingRequest request) {
        Offering offering = new Offering(
                request.name(),
                request.price(),
                request.active()
        );
        return offeringRepository.save(offering);
    }
}
