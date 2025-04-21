package com.alexander.controller.controllers.data;

import com.alexander.controller.model.response.PagedResponse;
import com.alexander.controller.model.response.TableResponseDTO;
import com.alexander.service.data.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/tables")
public class TableController {
    @Autowired
    private TableService tableService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<PagedResponse> getTables(@RequestHeader("Authorization") String accessToken, Pageable pageable) {
        PagedResponse<TableResponseDTO> tablePage = tableService.getUserTables(accessToken, pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tablePage);
    }

    @RequestMapping(value = "/rows", method = RequestMethod.GET)
    public ResponseEntity<String> getRows(@RequestHeader("Authorization") String accessToken,
                                          @RequestParam("container") String container,
                                          @RequestParam("database") String database,
                                          @RequestParam("table") String table,
                                          Pageable pageable) {
        String rows = tableService.getRows(accessToken, container, database, table, pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(rows);
    }
}
