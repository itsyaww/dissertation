package backend.controller;

import backend.model.Team;
import backend.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

public class TeamController {
    @Autowired
    private final TeamRepository teamRepository;

    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    //CRUD OPERATIONS
    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody Team newTeam) {
        Team createdTeam = teamRepository.save(newTeam);
        if (createdTeam == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/new")
                    .buildAndExpand(createdTeam.getTeamID())
                    .toUri();

            return ResponseEntity.created(uri)
                    .body(createdTeam);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOneTeam(@PathVariable Long id) {
        Optional<Team> Team = teamRepository.findById(id);
        if (Team.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Team.get());
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllTeams() {
        return new ResponseEntity<>(teamRepository.findAll(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateTeam(@PathVariable Long id, @RequestBody Team updatedTeam) {

        if (teamRepository.existsById(id)) {
            teamRepository.findById(id).map(existingTeam -> {

                existingTeam.setTeamID(updatedTeam.getTeamID());
                existingTeam.setTeamName(updatedTeam.getTeamName());
                existingTeam.setRegulationList(updatedTeam.getRegulationList());
                existingTeam.setRiskLevel(updatedTeam.getRiskLevel());
                existingTeam.setTeamPrimaryManager(updatedTeam.getTeamPrimaryManager());
                existingTeam.setTeamSecondaryManager(updatedTeam.getTeamSecondaryManager());


                return teamRepository.save(existingTeam);
            });
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        if (teamRepository.existsById(id)) {
            teamRepository.deleteById(id);
        }
    }
}
