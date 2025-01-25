package com.mycompany.sistema_cinemaseat;
import java.util.List;
import java.util.Scanner;

public class ContextoAccion {

    private Scanner scanner;
    private IGestorFunciones gestorFunciones;
    private List<Usuario> usuarios;
    private IPersistenciaUsuarios repoUsuarios;

    public ContextoAccion(Scanner scanner, IGestorFunciones gestorFunciones, List<Usuario> usuarios, IPersistenciaUsuarios repoUsuarios) {
        this.scanner = scanner;
        this.gestorFunciones = gestorFunciones;
        this.usuarios = usuarios;
        this.repoUsuarios = repoUsuarios;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public IGestorFunciones getGestorFunciones() {
        return gestorFunciones;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public IPersistenciaUsuarios getRepoUsuarios() {
        return repoUsuarios;
    }
}
