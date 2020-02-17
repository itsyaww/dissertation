package backend.controller;

import backend.model.Division;
import backend.repository.DivisionRepository;
import backend.repository.DivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/division")
public class DivisionController {

    @Autowired
    private final DivisionRepository divisionRepository;

    public DivisionController(DivisionRepository divisionRepository) {
        this.divisionRepository = divisionRepository;
    }

    //CRUD OPERATIONS
    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody Division newDivision) {
        Division createdDivision = divisionRepository.save(newDivision);
        if (createdDivision == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/new")
                    .buildAndExpand(createdDivision.getDivisionID())
                    .toUri();

            return ResponseEntity.created(uri)
                    .body(createdDivision);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOneDivision(@PathVariable Long id) {
        Optional<Division> Division = divisionRepository.findById(id);
        if (Division.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Division.get());
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllDivisions() {
        return new ResponseEntity<>(divisionRepository.findAll(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDivision(@PathVariable Long id, @RequestBody Division updatedDivision) {

        if (divisionRepository.existsById(id)) {
            divisionRepository.findById(id).map(existingDivision -> {

                existingDivision.setBusinessUnits(updatedDivision.getBusinessUnits());
                existingDivision.setDivisionName(existingDivision.getDivisionName());
                existingDivision.setDivisionID(existingDivision.getDivisionID());

                return divisionRepository.save(existingDivision);
            });
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        if (divisionRepository.existsById(id)) {
            divisionRepository.deleteById(id);
        }
    }
}
    