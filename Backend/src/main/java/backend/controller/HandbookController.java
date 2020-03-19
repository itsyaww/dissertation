package backend.controller;

import backend.model.Handbook;
import backend.model.Module;
import backend.repository.HandbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/handbook")
public class HandbookController {
    @Autowired
    private final HandbookRepository handbookRepository;

    public HandbookController(HandbookRepository handbookRepository) {
        this.handbookRepository = handbookRepository;
    }

    //CRUD OPERATIONS
    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody Handbook newHandbook) {
        Handbook createdHandbook = handbookRepository.save(newHandbook);
        if (createdHandbook == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/new")
                    .buildAndExpand(createdHandbook.getClass())
                    .toUri();

            return ResponseEntity.created(uri)
                    .body(createdHandbook);
        }
    }

    @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getHandbook() {
        return new ResponseEntity<>(handbookRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = {"/count"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getHandbookCount() {
        return new ResponseEntity<>(handbookRepository.count(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateHandbook(@PathVariable String id, @RequestBody Handbook updatedHandbook) {

        if (handbookRepository.existsById(id)) {
            handbookRepository.findById(id).map(existingHandbook -> {

                existingHandbook.setRegulator(updatedHandbook.getRegulator());
                existingHandbook.setModules(updatedHandbook.getModules());

                return handbookRepository.save(existingHandbook);
            });
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        if (handbookRepository.existsById(id)) {
            handbookRepository.deleteById(id);
        }
    }

    @PutMapping(value = "/add/module", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addModule(@RequestBody String handbookId, @RequestBody Module module) {

        handbookRepository.findById(handbookId).ifPresent(handbook -> {
            handbook.addModule(module);
            handbookRepository.save(handbook);
        });

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(value = "{handbookId}/delete/module/{buId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeModule(@PathVariable String handbookId, @PathVariable Long buId) {

        handbookRepository.findById(handbookId).ifPresent(handbook -> {
            handbook.removeModule(buId);
            handbookRepository.save(handbook);
        });

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
