package com.projetoa3.api.controller;

import com.projetoa3.api.entities.Desaparecido;
import com.projetoa3.api.service.DesaparecidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DesaparecidoService desaparecidoService;

    // Método de teste para verificar a conexão com o banco de dados
    @GetMapping("/test-connection")
    public String testeConexao() {
        StringBuilder response = new StringBuilder();
        try {
            if (dataSource != null && dataSource.getConnection() != null) {
                response.append("Conexão com o banco de dados: OK\n");
            } else {
                response.append("Não foi possível estabelecer conexão com o banco de dados\n");
            }
        } catch (SQLException e) {
            response.append("Erro ao obter conexão com o banco de dados: ").append(e.getMessage()).append("\n");
        }

        try {
            DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
            ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});
            response.append("Tabelas encontradas no banco de dados:\n");
            while (tables.next()) {
                response.append(tables.getString("TABLE_NAME")).append("\n");
            }
        } catch (SQLException e) {
            response.append("Erro ao obter lista de tabelas: ").append(e.getMessage()).append("\n");
        }

        return response.toString();
    }

    // Método de teste para verificar se a API está funcionando
    @PostMapping("/desaparecidos")
    public Desaparecido createDesaparecido(@RequestBody Desaparecido desaparecido) {
        return desaparecidoService.createDesaparecido(desaparecido);
    }

    @GetMapping("/desaparecidos")
    public List<Desaparecido> getAllDesaparecidos() {
        return desaparecidoService.getAllDesaparecidos();
    }

    @GetMapping("/desaparecidos/{id}")
    public Desaparecido getDesaparecidoById(@PathVariable int id) {
        return desaparecidoService.getDesaparecidoById(id);
    }

    @PutMapping("/desaparecidos/{id}")
    public Desaparecido updateDesaparecido(@PathVariable int id, @RequestBody Desaparecido desaparecido) {
        return desaparecidoService.updateDesaparecido(id, desaparecido);
    }

    @DeleteMapping("/desaparecidos/{id}")
    public boolean deleteDesaparecido(@PathVariable int id) {
        return desaparecidoService.deleteDesaparecido(id);
    }
}