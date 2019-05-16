package by.bntu.diploma.diagram.web.controller;

import by.bntu.diploma.diagram.domain.Diagram;
import by.bntu.diploma.diagram.service.DiagramService;
import by.bntu.diploma.diagram.web.dto.DiagramDTO;
import by.bntu.diploma.diagram.web.dto.ViewDiagramDTO;
import by.bntu.diploma.diagram.web.dto.mapper.Mapper;
import by.bntu.diploma.diagram.web.exception.RestException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/diagram")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DiagramController {

    private DiagramService diagramService;
    private Mapper<Diagram, DiagramDTO> diagramMapper;
    private Mapper<Diagram, ViewDiagramDTO> viewSchemaMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{uuid}")
    public DiagramDTO updateDiagram(@PathVariable(name = "uuid") String diagramUuid,
                                    @RequestBody DiagramDTO diagramDTO) {
        Diagram diagram = diagramMapper.fromDTO(diagramDTO);
        diagram.setUuid(diagramUuid);
        diagram = diagramService.updateDiagram(diagram);
        return diagramMapper.toDTO(diagram);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{uuid}")
    public DiagramDTO findDiagramByUuid(@PathVariable(name = "uuid") String diagramUuid) {
        Diagram diagram = diagramService.findDiagramByUuid(diagramUuid);
        if (diagram == null) {
            throw new RestException(HttpStatus.NOT_FOUND, "Diagram[" + diagramUuid + "] not found.");
        }
        return diagramMapper.toDTO(diagram);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ViewDiagramDTO> getDiagramsInfo() {
        return diagramService.findAllDiagrams()
                .stream()
                .map(viewSchemaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping
    public DiagramDTO saveDiagram(@RequestParam(name = "external_source", required = false, defaultValue = "false") Boolean isExternal,
                                  @RequestBody DiagramDTO diagramDTO) {
        Diagram diagram;
        diagram = diagramMapper.fromDTO(diagramDTO);

        if (isExternal) {
            diagram = diagramService.saveExternalDiagram(diagram);
        } else {
            diagram = diagramService.saveDiagram(diagram);
        }

        return diagramMapper.toDTO(diagram);
    }

}
