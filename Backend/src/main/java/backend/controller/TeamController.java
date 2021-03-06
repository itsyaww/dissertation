package backend.controller;

import backend.model.Handbook;
import backend.model.Module;
import backend.model.Team;
import backend.repository.HandbookRepository;
import backend.repository.ModuleRepository;
import backend.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@CrossOrigin//(origins = {"http://localhost:3000", "http://localhost:5000"})
@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private final TeamRepository teamRepository;

    @Autowired
    private final HandbookRepository handbookRepository;

    /*@Autowired
    private final ModuleRepository moduleRepository;*/

    public TeamController(TeamRepository teamRepository, HandbookRepository handbookRepository/*, ModuleRepository moduleRepository*/) {
        this.teamRepository = teamRepository;
        this.handbookRepository = handbookRepository;
        /*this.moduleRepository = moduleRepository;*/
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
                existingTeam.setModules(updatedTeam.getModules());
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

    @PutMapping(value = "{teamId}/add/module", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>  addModule(@PathVariable String teamId, @RequestBody Module module)
    {
        teamRepository.findByTeamName(teamId).ifPresent(team -> {

            module.setModuleName(module.getModuleName()); //hardcoded
            module.setSupervisoryCountry("United Kingdom"); //hardcoded
            module.setSupervisoryBody("FCA"); //hardcoded

            team.addModule(module);
            Handbook handbook = handbookRepository.findById("FCA Handbook").get();
            handbook.addModule(module);
            handbookRepository.save(handbook);
            teamRepository.save(team);
        });

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(value = "{teamId}/delete/module/{moduleCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeModule(@PathVariable Long teamId, @PathVariable String moduleCode)
    {

        teamRepository.findById(teamId).ifPresent(team -> {
            team.removeModule(moduleCode);
            teamRepository.save(team);
        });

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /*@PutMapping(value = "{teamId}/module/setComplianceRisk/{atRisk}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>  setModuleComplianceRisk(@PathVariable String teamId, @PathVariable Boolean atRisk, @RequestBody Module module)
    {
        teamRepository.findByTeamName(teamId).ifPresent(team -> {

            if (moduleRepository.findByModuleCode(module.getModuleCode()).isPresent())
            {
                Module existingModule = moduleRepository.findByModuleCode(module.getModuleCode()).get();
                team.setModuleRegulationRisk(module, atRisk);

                moduleRepository.save(existingModule);
                teamRepository.save(team);
            }

        });

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value = "{teamId}/module/getComplianceRisk", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?>  getModuleComplianceRisk(@PathVariable String teamId, @RequestBody Module module)
    {
        Boolean risk = null;
        if (teamRepository.findByTeamName(teamId).isPresent())
        {
            Team team = teamRepository.findByTeamName(teamId).get();
            if (moduleRepository.findByModuleCode(module.getModuleCode()).isPresent())
            {
                Module existingModule = moduleRepository.findByModuleCode(module.getModuleCode()).get();
                risk = team.getModuleRegulationRisk(module);

                return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(risk);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }*/
}
