package by.bntu.diagram.web;

import by.bntu.diagram.domain.Diagram;
import by.bntu.diagram.service.DiagramService;
import by.bntu.diagram.web.dto.DiagramDTO;
import by.bntu.diagram.web.dto.mapper.Mapper;
import by.bntu.diagram.web.util.Format;
import by.bntu.diagram.web.util.converter.format.DiagramDtoConverter;
import by.bntu.diagram.web.util.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/diagram/")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FormatController {

    private DiagramService diagramService;
    private Map<Format, DiagramDtoConverter> formatConverters;
    private Mapper<Diagram, DiagramDTO> diagramMapper;

    @GetMapping("/{uuid}/export")
    public String exportDiagram(@PathVariable(name = "uuid") String diagramUuid, @RequestParam("format") Format format) {
        Diagram diagram = diagramService.findDiagramByUuid(diagramUuid);
        if (diagram == null) {
            throw new NotFoundException("Diagram[" + diagramUuid + "] not found.");
        }
        DiagramDTO diagramDto = diagramMapper.toDTO(diagram);
        return formatConverters.get(format).to(diagramDto);
    }


    @PostMapping("/import")
    public DiagramDTO importDiagram(@RequestBody String data, @RequestParam("format") Format format) {
        DiagramDTO diagramDto = formatConverters.get(format).from(data);
        Diagram diagram = diagramMapper.fromDTO(diagramDto);
        diagram = diagramService.saveExternalDiagram(diagram);
        return diagramMapper.toDTO(diagram);
    }


}
