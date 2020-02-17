package backend.controller;

import backend.model.BusinessUnit;
import backend.repository.BusinessUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

public class BusinessUnitController {
    
    @Autowired
    private final BusinessUnitRepository businessUnitRepository;

    public BusinessUnitController(BusinessUnitRepository businessUnitRepository) {
        this.businessUnitRepository = businessUnitRepository;
    }

    //CRUD OPERATIONS
    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody BusinessUnit newBusinessUnit) {
        BusinessUnit createdBusinessUnit = businessUnitRepository.save(newBusinessUnit);
        if (createdBusinessUnit == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/new")
                    .buildAndExpand(createdBusinessUnit.getBuID())
                    .toUri();

            return ResponseEntity.created(uri)
                    .body(createdBusinessUnit);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOneBusinessUnit(@PathVariable Long id) {
        Optional<BusinessUnit> BusinessUnit = businessUnitRepository.findById(id);
        if (BusinessUnit.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(BusinessUnit.get());
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllBusinessUnits() {
        return new ResponseEntity<>(businessUnitRepository.findAll(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateBusinessUnit(@PathVariable Long id, @RequestBody BusinessUnit updatedBusinessUnit) {

        if (businessUnitRepository.existsById(id)) {
            businessUnitRepository.findById(id).map(existingBusinessUnit -> {

                existingBusinessUnit.setBuID(existingBusinessUnit.getBuID());
                existingBusinessUnit.setBuComplianceOfficer(existingBusinessUnit.getBuComplianceOfficer());
                existingBusinessUnit.setBuDivision(existingBusinessUnit.getBuDivision());
                existingBusinessUnit.setBuName(existingBusinessUnit.getBuName());
                existingBusinessUnit.setBuSeniorLead(existingBusinessUnit.getBuSeniorLead());
                existingBusinessUnit.setTeams(existingBusinessUnit.getTeams());

                return businessUnitRepository.save(existingBusinessUnit);
            });
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        if (businessUnitRepository.existsById(id)) {
            businessUnitRepository.deleteById(id);
        }
    }
}
