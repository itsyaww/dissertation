package backend.controller;

import backend.model.Firm;
import backend.repository.FirmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

public class FirmController {
    @Autowired
    private final FirmRepository firmRepository;

    public FirmController(FirmRepository firmRepository) {
        this.firmRepository = firmRepository;
    }

    //CRUD OPERATIONS
    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody Firm newFirm) {
        Firm createdFirm = firmRepository.save(newFirm);
        if (createdFirm == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/new")
                    .buildAndExpand(createdFirm.getClass())
                    .toUri();

            return ResponseEntity.created(uri)
                    .body(createdFirm);
        }
    }

    @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFirm() {
        return new ResponseEntity<>(firmRepository.findAll(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateFirm(@PathVariable String id, @RequestBody Firm updatedFirm) {

        if (firmRepository.existsById(id)) {
            firmRepository.findById(id).map(existingFirm -> {

                existingFirm.setBusinessUnits(updatedFirm.getBusinessUnits());
                existingFirm.setFirmName(updatedFirm.getFirmName());

                return firmRepository.save(existingFirm);
            });
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        if (firmRepository.existsById(id)) {
            firmRepository.deleteById(id);
        }
    }
}
