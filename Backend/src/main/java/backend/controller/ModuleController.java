package backend.controller;

import backend.model.Module;
import backend.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

public class ModuleController {
    @Autowired
    private final ModuleRepository moduleRepository;

    public ModuleController(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    //CRUD OPERATIONS
    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody Module newModule) {
        Module createdModule = moduleRepository.save(newModule);
        if (createdModule == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/new")
                    .buildAndExpand(createdModule.getModuleCode())
                    .toUri();

            return ResponseEntity.created(uri)
                    .body(createdModule);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOneModule(@PathVariable String id) {
        Optional<Module> Module = moduleRepository.findById(id);
        if (Module.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Module.get());
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllModules() {
        return new ResponseEntity<>(moduleRepository.findAll(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateModule(@PathVariable String id, @RequestBody Module updatedModule) {

        if (moduleRepository.existsById(id)) {
            moduleRepository.findById(id).map(existingModule -> {

                existingModule.setModuleCode(updatedModule.getModuleCode());
                existingModule.setModuleName(updatedModule.getModuleName());
                existingModule.setRegulations(updatedModule.getRegulations());
                existingModule.setSupervisoryBody(updatedModule.getSupervisoryBody());
                existingModule.setSupervisoryCountry(updatedModule.getSupervisoryCountry());

                return moduleRepository.save(existingModule);
            });
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        if (moduleRepository.existsById(id)) {
            moduleRepository.deleteById(id);
        }
    }
}
